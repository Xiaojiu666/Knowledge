### 什么是Dagger2
  在我看来Dagger2是一个又高级又低级的的框架，在Java开发中，对象无处不在，无论是从初级开发，还是高级架构师，都离不开`Objec object =new Objec();`,每个java程序员每天都需要new 很多对象，为什么简简单单的new对象，却被Dagger2搞得如此复杂呢？

##### 什么是依赖注入
  在了解Dagge2之前，我们先简单了解一下，什么是依赖注入。当我们有一个Person类 需要去实例化，通常手段都是`Computer computer = new Computer()`, 如果 Computer 中需要依赖一个CPU的实例，一般情况下都是通过构造传入`new Computer(new CPU)`，或者 set 方法 传入，这其实就是依赖注入最常见的两种方式。Dagger2 就是通过`注解+JavaPoet`等手段，在编译期间，动态生成（通过module)的方式提供依赖`提供方`，并将通过容器 将`需求方`进行赋值绑定。
![](/image/Dagger2.png)


### Dagger2-基础

#### 基础介绍
围绕着上面的问题，我们尝试使用Dagger来试一下效果，并了解下其中原理

######  @Inject
  自动生成提供当前实例的工厂类 主要注释在两个地方(构造函数和全局变量)

![](/image/Dagger2-@inject-构造.png)

  - 空构造函数:

  被注解的类，Dagger会自动帮我们实例化对象，也就是 帮助我们做了`new Computer()`的动作，那让我们试一下，被`@Inject`注释的类会发生什么变化。
```Java
//CPU类，@Inject 后，Rebuid一下项目
class CPU {
    fun getCpuName() = "I9-10900X"
}
```
上面讲过 ，Dagger2会在编译期生成代码，Rebuid一下项目，我们就会看到Dagger帮我们生成的代码，让我们查看一下
```Java
  //省略..
  public final class CPU_Factory implements Factory<CPU> {
    @Override
    public CPU get() {
      return newInstance();
    }
    public static CPU_Factory create() {
      return InstanceHolder.INSTANCE;
    }
    public static CPU newInstance() {
      return new CPU();
    }
      private static final CPU_Factory INSTANCE = new CPU_Factory();
    }
  }
```
分析下生成的`CPU_Factory`类 首先不可修改 实现Factory接口，重写get()方法，主要就是做了两件事：
- 重写get方法，返回我们@Inject 构造的对象，其实就是帮我们`new 了一个 Person()`。
- 生成一个`CPU_Factory`的单例(注意，是Phone_Factory，而不是Person单例)。

  那我们是不是可以通过`CPU_Factory.create().get` 就能拿到CPU的实例了呢，确实可以，但这样做完全没意义，直接new 不香么，没人会傻到这么做，我们记下来继续看下带参构造。
  - 带参构造:

  ```Java
  class Computer @Inject constructor(var cpu: CPU) {
      var phoneName = "Mac"
  }
  ```
  Rebuild一下项目
  ```Java
  public final class Computer_Factory implements Factory<Computer> {
    private final Provider<CPU> cpuProvider;

    public Computer_Factory(Provider<CPU> cpuProvider) {
      this.cpuProvider = cpuProvider;
    }

    @Override
    public Computer get() {
      return newInstance(cpuProvider.get());
    }

    public static Computer_Factory create(Provider<CPU> cpuProvider) {
      return new Computer_Factory(cpuProvider);
    }

    public static Computer newInstance(CPU cpu) {
      return new Computer(cpu);
    }
  }
  ```
  对比无参构造发现，你会发现一个区别：
    - 1、空参的Factory类是单例，而带参的Factory会需要一个Provider<参数> 作为入参
    - 2、Computer newInstance() 的时候需要一个参数，而这个参数，则是通过CPU_Factory.get()获取到的，其实这时候就关联起来了，这样我们就通过2、Computer_Factory.get()方法拿到了一个带着CPU对象的Computer引用。

  但这还是不够，因为没人会这样用，简直就是多此一举，接下来我们配合其他注解，继续完善代码。


  - 成员变量:

  在开发的过程中，并不是所有类都有构造，例如Android中，常见的Activity，全部是通过系统源码创建，那我们如何像Activity中注入所需要的对象呢，Dagger 为我们提供了一种方法，将@Inject 注解在成员变量上，通过容器类，将依赖方与需求方进行绑定。

  我们依旧将CPU注入给Computer

  1、声明容器
  ```java
  //@Component下面会说到用法
  @Component
  interface ComputerComponent {
      fun inject(computer: Computer)
  }
  ```
  2、调用并注入
  ```Java
  class Computer {
      @Inject
      lateinit var cpu: CPU

      init {
          DaggerComputerComponent.create().inject(this)
      }
  }

  ```
  rebuild项目生成代码

  容器接口暂时省略..
  下面讲到在细说

  根据依赖需求方 Person 中 被@Inject修饰的变量 生成此类
```Java
  public final class Computer_MembersInjector implements MembersInjector<Computer> {
    private final Provider<CPU> cpuProvider;

    private final Provider<Disk> diskProvider;
    // 1
    public Computer_MembersInjector(Provider<CPU> cpuProvider, Provider<Disk> diskProvider) {
      this.cpuProvider = cpuProvider;
      this.diskProvider = diskProvider;
    }

    public static MembersInjector<Computer> create(Provider<CPU> cpuProvider,
        Provider<Disk> diskProvider) {
      return new Computer_MembersInjector(cpuProvider, diskProvider);
    }
    //2
    @Override
    public void injectMembers(Computer instance) {
      injectCpu(instance, cpuProvider.get());
      injectDisk(instance, diskProvider.get());
    }
    //3
    @InjectedFieldSignature("com.gx.task.di.demo.Computer.cpu")
    public static void injectCpu(Computer instance, CPU cpu) {
      instance.cpu = cpu;
    }

    @InjectedFieldSignature("com.gx.task.di.demo.Computer.disk")
    public static void injectDisk(Computer instance, Disk disk) {
      instance.disk = disk;
    }
  }

  ```
  我们继续分析源码
  来看被我们注释的成员变量:
    - 1、Dagger检测到 Person类中有变量存在@Inject注释，自动根据Computer(需求方的名字)+_MembersInjector 实现 MembersInjector生成一个类。
    - 2、构造根据被@Inject 注释变量的个数 传入多个`Provider<T>`。
    - 3、重写接口中`injectMembers()`方法，但暂时没用到
    - 4、核心关键`injectCpu()`方法，将需求方和依赖方进行绑定。

最后我们总结一下@Inject注释：
- 注解在构造:1、被注解的类，自动实现`Provider<T>`接口生成工厂类，通过实现接口中`get()`方法，主要用于提供注解类的对象。2、如果被注解的构造有参数，则会通过参数的`Provider<T>`的`get()`方法，注入到需求方。
- 注解在变量:2、被注解变量所在的类，自动实现`MembersInjector<T>`接口生成帮助类，通过实现接口中的`injectMembers()`方法，进行赋值绑定。

######  @Component
  我们上面说过，整个依赖注入里面可以包括三个模块，被注入方，依赖提供方，容器。上面我们已经通过@Injected注释，讲了被注入方和提供方，接下来我们看看被@Component注释的接口。上面在注解成员变量的时候，我们有用过@Component注释的接口。让我们继续看下源码。
![](/image/Dagger2-@inject-成员变量.png)

  @Component相对来说比较简单，只能用在接口上
  - 简单对外提供实例

  ```Java
  @Component
  interface ComputerComponent {
      fun inject(computer: Computer)
  }
  ```
  依旧是Build一下项目

  ```Java
    // 1 调用DaggerComputerComponent.create().inject(this)
    public final class DaggerComputerComponent implements ComputerComponent {
      private DaggerComputerComponent() {

      }

      public static Builder builder() {
        return new Builder();
      }

      public static ComputerComponent create() {
        return new Builder().build();
      }

      @Override
      public void inject(Computer computer) {
        injectComputer(computer);
      }

      private Computer injectComputer(Computer instance) {
        Computer_MembersInjector.injectCpu(instance, new CPU());
        Computer_MembersInjector.injectDisk(instance, new Disk());
        return instance;
      }

      public static final class Builder {
        private Builder() {
        }

        public ComputerComponent build() {
          return new DaggerComputerComponent();
        }
      }
    }

  ```
分析下容器代码:
-  1、被@Component 注释的接口 会自动以`Dagger+接口名`生成实现类,
-  2、通过一个Builder模式提供当容器类的实例(为什么用Builder模式后面会讲到)
-  3、重写接口方法，根据传入的需求方，进行赋值绑定。

简单总结一下@Component 注释:
- 只能用于接口上，会根据接口自动生成容器类，通过Builder绑定。
- 会重写接口里面的方法，将需求方传入，与提供方进行赋值绑定。

在简单的使用过程中，有几个疑问
- 被@Inject修饰构造，所生产的Factory，其实并没有应用到
- 为什么被@Component修饰的接口所生成的类 会通过Builder模式提供实例？

### Dagger2-模块
在使用模块的时候，我们先考虑一个问题，我们上面的所讲的Demo中 `依赖提供方`都是可以
通过new去实例化的类，但是在实际开发过程中，我们有很多实例，是无法直接 new 出来的，例如，现在有一台电脑，只能有一块主板，一块CPU，所以主板和CPU均是通过单例提供，这个时候容器类，就无法帮我们new出来我们想要的实例，那主板如何给Computer提供依赖呢？这个时候就需要我们手动提供给容器接口，由他来进行绑定。

Dagger2给我们提供了两个新的注释 @Module 和 @Provides 配合@Component使用
![](/image/Dagger2-@module.png)

#### 模块基础使用

假设我们现在有一台电脑，需要两个配件，一个主板，一个CPU，但在整个电脑里，主板和CPU 都只能有一个。
```Java
//模块
@Module
class PartsModule() {
  //...
  @Provides
  fun provideCPU(): CPU {
      return CPU.create()!!
  }
}
```

```java
//容器
@Component(modules = [PartsModule::class])
interface ComputerComponent {
    fun inject(computer: Computer)
}
```

```Java
class Computer() {
    @Inject
    lateinit var cpu: CPU

    @Inject
    lateinit var mainBoard: MainBoard

    @Inject
   lateinit var disk: Disk

    init {
      // 调用
       DaggerComputerComponent.builder()
       .computerModule(PartsModule()).build().inject(this)
    }
}
```
我们来看一下容器的源码
```java
public final class DaggerComputerComponent implements ComputerComponent {
  private final PartsModule partsModule;

  private DaggerComputerComponent(PartsModule partsModuleParam) {
    this.partsModule = partsModuleParam;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ComputerComponent create() {
    return new Builder().build();
  }

  @Override
  public void inject(Computer computer) {
    injectComputer(computer);
  }

  private Computer injectComputer(Computer instance) {
    Computer_MembersInjector.injectCpu(instance, PartsModule_ProvideCPUFactory.provideCPU(partsModule));
    Computer_MembersInjector.injectDisk(instance, new Disk());
    Computer_MembersInjector.injectMainBoard(instance, PartsModule_ProvideMainBoardFactory.provideMainBoard(partsModule));
    return instance;
  }

  public static final class Builder {
    private PartsModule partsModule;

    private Builder() {
    }

    public Builder partsModule(PartsModule partsModule) {
      this.partsModule = Preconditions.checkNotNull(partsModule);
      return this;
    }

    public ComputerComponent build() {
      if (partsModule == null) {
        this.partsModule = new PartsModule();
      }
      return new DaggerComputerComponent(partsModule);
    }
  }
}
```
大致流程和之前的容器代码一样
  - 1、被@Component 注释的接口 会自动以Dagger+接口名生成实现类,
  - 2、通过一个Builder模式提供当容器类的实例(为什么用Builder模式后面会讲到)
  - 3、重写接口方法，根据传入的需求方，进行赋值绑定。

差异化的是
  - 1、通过builder 注入 @Component 中的modules
  - 2、绑定时，可以直接new的类，直接 new ，不能 new的类， 通过module中提供的方法获取。

我们来看一下被module修饰的类会帮我们做哪些事情
被@module中@Provides修饰的方法，和最开始提及的被@Inject修饰的构造一样，均会实现Factory接口，重写get方法，用于提供实例。
```java
public final class PartsModule_ProvideMainBoardFactory implements Factory<MainBoard> {
  private final PartsModule module;

  public PartsModule_ProvideMainBoardFactory(PartsModule module) {
    this.module = module;
  }
  @Override
  public MainBoard get() {
    return provideMainBoard(module);
  }

  public static PartsModule_ProvideMainBoardFactory create(PartsModule module) {
    return new PartsModule_ProvideMainBoardFactory(module);
  }

  public static MainBoard provideMainBoard(PartsModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideMainBoard());
  }
}
```
总结:
  - 1、@module+@Provides 和 @Injcet在构造上的作用一样，均是为了提供实例，前者通过module类中的方法，获取实例。
  - 2、容器中的builder模式，就是为了应对多个module时，用于产生容器对象，协助绑定需求方。

#### 模块传递参数
假设我们电脑中的部件，主板和显卡都需要供电，那我们在module中需要电的引用， 一般有几种方法 1、通过module的构造传入 2、使用@Provides提供实例。 我们围绕第二种来试一下，修改上面的代码。

一块需要供电的主板。
```Java
class MainBoard private constructor(var electric: Electric) {
    companion object {
        fun create(electric: Electric): MainBoard? {
            return InstanceHolder(electric).INSTANCE
        }

        private class InstanceHolder(electric: Electric) {
            val INSTANCE = MainBoard(electric)
        }
    }
}
```
module类
```java
@Module
class PartsModule() {
    @Provides
    fun provideElectric() = Electric()

    @Provides
    fun provideMainBoard(electric: Electric): MainBoard {
        return MainBoard.create(electric)!!
    }
}
```

`provideElectric()`方法也是被@Provides修饰，所以会生成一个工厂类，用来提供Electric的实例。
接下里开下容器类，依旧是`inject()`方法。
```Java
  //省略....
  private Computer injectComputer(Computer instance) {
      Computer_MembersInjector.injectCpu(instance, PartsModule_ProvideCPUFactory.provideCPU(partsModule));
      Computer_MembersInjector.injectDisk(instance, new Disk());
      Computer_MembersInjector.injectMainBoard(instance, mainBoard());
      // Computer_MembersInjector.injectMainBoard(instance, PartsModule_ProvideMainBoardFactory.provideMainBoard(partsModule));
      return instance;
  }
  private MainBoard mainBoard() {
     return PartsModule_ProvideMainBoardFactory.provideMainBoard(partsModule, PartsModule_ProvideElectricFactory.provideElectric(partsModule));
 }
```
可以看出，在进行绑定的时候，injectMainBoard()方法里面，MainBoard实例获取的方式不一样了，但其根本依旧是通过`PartsModule_ProvideMainBoardFactory.provideElectric()`获取到，只是多了一个参数`PartsModule_ProvideElectricFactory.provideElectric(partsModule)`,其实这个参数就是MainBorad所需的Electric实例。
至此，传递参数，我们也已经很清晰了。

### Dagger2-范围（单例）
我们上面提到过的主板和CPU等部件，都是整个对象单例，但实际情况并不是所有电脑都用一个CPU，而是每一台电脑使用一个CPU，这个时候我们就需要优化我们的代码，使我们的单例仅针对这一台Computer，也就是模块的范围。

##### @Singleton
```Java
    //1 module
    @Singleton
    @Provides
    fun provideCPU(): CPU {
        return CPU()
    }

    //2 提供方
    @Singleton
    class CPU @Inject constructor()  {
      fun getCpuName() = "I9-10900X"
    }
    //3 容器
    @Singleton
    @Component(modules = [PartsModule::class])
    interface ComputerComponent {
        fun inject(computer: Computer)
    }
```
我们在外面new两个Computer的实例，看下日志
```java
  class Computer(var context: Context) {
      @Inject
      lateinit var cpu: CPU

      @Inject
      lateinit var cpu1: CPU

      init {
          DaggerComputerComponent.builder().partsModule(PartsModule()).build().inject(this)
          Log.e("Computer cpu" ,cpu.toString())
          Log.e("Computer cpu1" ,cpu1.toString())
          // 第一个
          //cpu: [BaseActivity.kt, onCreate, 42]:com.gx.task.di.computer.CPU@ad51322
          //cpu1: [BaseActivity.kt, onCreate, 42]:com.gx.task.di.computer.CPU@ad51322

          //第二次输出
          //[BaseActivity.kt, onCreate, 43]:com.gx.task.di.computer.CPU@6d9b1b3
          //[BaseActivity.kt, onCreate, 43]:com.gx.task.di.computer.CPU@6d9b1b3
      }
  }
```
那Dagger是如何做到的，其实玄机就在容器类里面，我们只需要关注一下绑定时，`依赖提供方`的实例如何获取的，就知道Dagger是如何做到在module中单例。

```java
  //1
  private Computer injectComputer(Computer instance) {
    Computer_MembersInjector.injectCpu(instance, provideCPUProvider.get());
    Computer_MembersInjector.injectCpu1(instance, provideCPUProvider.get());
    return instance;
  }
  //2
  private void initialize(final PartsModule partsModuleParam) {
    this.provideCPUProvider = DoubleCheck.provider(PartsModule_ProvideCPUFactory.create(partsModuleParam));
  }

  //3 DoubleCheck.class
  //核心单例
  @Override
  public T get() {
     Object result = instance;
     if (result == UNINITIALIZED) {
       synchronized (this) {
         result = instance;
         if (result == UNINITIALIZED) {
           result = provider.get();
           instance = reentrantCheck(instance, result);
           /* Null out the reference to the provider. We are never going to need it again, so we
            * can make it eligible for GC. */
           provider = null;
         }
       }
     }
     return (T) result;
   }
```
分析:
  - 1、在容器中绑定时，两个变量，都是通过provideCPUProvider.get()获取实例
  - 2、provideCPUProvider  根据 DoubleCheck.provider()代理所得，将PartsModule_ProvideCPUFactory实例转为DoubleCheck的实例,因为他们俩均实现了Provider接口
  - 3、最终provideCPUProvider已经被转换为DoubleCheck 所以调用的DoubleCheck重的get()方法，这里通过双重检查单例，返回CPU对象，这样就保证了容器中，所有的CPU都是同一个实例，所以注入到需求方，赋值绑定时，get()拿到的都是同一个实例。

### Dagger2进阶-子组件
##### 子组件继承
在实际项目中，为了方便管理，我们通常会将各个容器进行统一管理，这样有一个好处，父容器所提供的实例，子容器也可以用，并且子容器的生命周期是可控的。

上面我们只是讲了一台电脑，假如我们项目中 除了电脑 还有一部手机，这样的话，无论电脑还是手机都需要供电，那我们就需要把provideElectric()放在最顶层，
那我们就需要两个容器分别来提供 不同的实例
![](/image/Dagger2-子容器.png)
通过上面看出，在AppComponent中，通过AppMoudle 提供`Electric`实例，提供一个接口返回一个`Electric`实例。

使用
```Java

@Inject
lateinit var cpu: CPU
@Inject
lateinit var electric: Electric
@Inject
lateinit var otherPart: OtherPart
@Inject
lateinit var mainBoard: MainBoard

// 先构建App的容器
val appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()
//由于 dependencies 关系，会在Buidler中 提供方法，注入appComponent
DaggerComputerComponent.builder().appComponent(appComponent).partsModule(PartsModule())
```

分析：DaggerAppComponent.class
```java
public final class DaggerAppComponent implements AppComponent {
  private final AppModule appModule;

  private DaggerAppComponent(AppModule appModuleParam) {
    this.appModule = appModuleParam;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static AppComponent create() {
    return new Builder().build();
  }

  @Override
  public Electric provideElectric() {
    return AppModule_ProvideElectricFactory.provideElectric(appModule);
  }

  public static final class Builder {
    private AppModule appModule;

    private Builder() {
    }

    public Builder appModule(AppModule appModule) {
      this.appModule = Preconditions.checkNotNull(appModule);
      return this;
    }

    public AppComponent build() {
      if (appModule == null) {
        this.appModule = new AppModule();
      }
      return new DaggerAppComponent(appModule);
    }
  }
}
```
大致流程和之前的容器代码一样
  - 1、被@Component 注释的接口 会自动以Dagger+接口名生成实现类,
  - 2、重写接口方法，之前都是通过传入的需求方，进行赋值绑定，这次是通过一个带返回值的抽象方法方法，来获取实例。
  - 3、接口方法，会通过AppModule 进行回去实例。

分析:DaggerComputerComponent.class
```Java
public final class DaggerComputerComponent implements ComputerComponent {
  private final PartsModule partsModule;

  private final AppComponent appComponent;

  private DaggerComputerComponent(PartsModule partsModuleParam, AppComponent appComponentParam) {
    this.partsModule = partsModuleParam;
    this.appComponent = appComponentParam;
  }

  public static Builder builder() {
    return new Builder();
  }

  private MainBoard mainBoard() {
    return PartsModule_ProvideMainBoardFactory.provideMainBoard(partsModule, Preconditions.checkNotNullFromComponent(appComponent.provideElectric()));
  }

  @Override
  public void inject(Computer computer) {
    injectComputer(computer);
  }

  private Computer injectComputer(Computer instance) {
    Computer_MembersInjector.injectCpu(instance, PartsModule_ProvideCPUFactory.provideCPU(partsModule));
    Computer_MembersInjector.injectCpu1(instance, PartsModule_ProvideCPUFactory.provideCPU(partsModule));
    Computer_MembersInjector.injectElectric(instance, Preconditions.checkNotNullFromComponent(appComponent.provideElectric()));
    Computer_MembersInjector.injectOtherPart(instance, new OtherPart());
    Computer_MembersInjector.injectMainBoard(instance, mainBoard());
    return instance;
  }

  public static final class Builder {
    private PartsModule partsModule;

    private AppComponent appComponent;

    private Builder() {
    }

    public Builder partsModule(PartsModule partsModule) {
      this.partsModule = Preconditions.checkNotNull(partsModule);
      return this;
    }

    public Builder appComponent(AppComponent appComponent) {
      this.appComponent = Preconditions.checkNotNull(appComponent);
      return this;
    }

    public ComputerComponent build() {
      if (partsModule == null) {
        this.partsModule = new PartsModule();
      }
      Preconditions.checkBuilderRequirement(appComponent, AppComponent.class);
      return new DaggerComputerComponent(partsModule, appComponent);
    }
  }
}
```
  - 1、Builder 中新增 appComponent(),用于传入appComponent，
  - 2、问题在于，injectMainBoard()时，构造参数由何而来，是通过appComponent.provideElectric()获取，这样就和上面的关联起来了。
  - 3、如果在appComponent中 不提供实例，例如OtherPart(),那么injectOtherPart()则会帮忙new出一个实例，防止崩溃。

##### 子组件范围@Subcomponent
  日常开发时，登录流程（由单个 LoginActivity 管理）由多个 Fragment 组成，您应在所有 Fragment 中重复使用 LoginViewModel 的同一实例。@Singleton 无法为 LoginViewModel 添加注释以重复使用该实例，原因如下：
  - 流程结束后，LoginViewModel 的实例将继续保留在内存中。
  - 您希望为每个登录流程使用不同的 LoginViewModel 实例。例如，如果用户退出，您希望使用不同的 LoginViewModel 实例，而不是用户首次登录时的实例。

所以我们需要将loginViewModel的生命周期依赖于LoginActivity。




### 总结
我们在开头的时候说了Dagger又高级，又低级，在真的搞明白了Dagger后，你会发现Dagger会让项目中实例的依赖更加清晰，减少很多重复工作，但一个简简单单的new()就可以解决的问题，却被搞得如此复杂，还是仁者见仁。

有些朋友接手Dagger或者Hilt项目后，甚至找不到对象由何而来，希望这篇文章可以帮助到你。


### 参考资料
- [官网](https://dagger.dev)
- [androidGoogle官网](https://developer.android.google.cn/training/dependency-injection)
