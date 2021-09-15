### 什么是Dagger2
  当我们有一个Person类 需要去实例化，通常手段都是`Computer computer = new Computer()`, 如果 Computer 中需要依赖一个Phone对象，一般情况下都是通过构造传入`new Computer(new CPU)`，或者 set 方法 传入，这其实就是依赖注入最常见的两种方式，Dagger2 就是此两种方式上，通过`注解+JavaPoet`等手段，在编译期间，动态的生成 依赖方 所需要的对象。
![](/image/Dagger2.png)

### 使用Dagger2

#### 基础介绍

围绕着上面的问题，我们尝试使用Dagger来试一下效果，并了解下其中原理

######  @Inject
  最核心的注释，自动生成创建对象的工厂 可以说其他的注释均为此类提供服务，主要注释在两个地方(构造函数和全局变量)

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

  在开发的过程中，并不是所有类都有构造，例如Android中，常见的Activity，全部是通过系统源码创建，那我们如何像Activity中注入所需要的对象呢，Dagger 为我们提供了一种方法，将@Inject 注解在成员变量上。

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

      @Inject
      lateinit var disk: Disk

      var phoneName = "Mac"

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
    - 3、重写接口中`injectMembers()`方法，
    - 4、核心关键`injectPhone()`方法，把phone的实例，赋值给person中的 phone变量,用于绑定。

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
- @Inject 注释的类，已经可以提供实例了，为什么还需要容器类？而且容器类和Factory类也毫无关系？
- 为什么@Component注释生成的类 会通过Builder模式，提供实例？

随着问题，我们继续往下进阶研究

### Dagger2进阶-模块
在使用模块的时候，我们先考虑一个问题，我们上面的所有的demo `依赖提供方`都是可以
通过new去实例化的类，但是在实际开发过程中，我们有很多类，是通过各种设计模式创建的(例如上面的Builder模式，单例等)，那这些类，如何提供依赖呢？由于@Component 修饰的容器只能是接口，Dagger2给我们提供了两个新的接口 @Module 和 @Provides 配合@Component使用

假设我们现在有一个View，需要提供给某个Activity使用

同时 我们在给Activty注入一个正常的Clothersd实例
```Java
//模块
  @Module
  class BaseModule constructor(var context: Context){
      @Provides
      fun getView(): View {
          return LayoutInflater.from(context).inflate(R.layout.fragment_task_home, null)
      }

      @Provides
      fun....
  }
```

```java
//容器
  @Component(modules = [BaseModule::class])
  interface BaseContainer {
      fun inject(baseActivity: BaseActivity)
  }
```

```Java
// 调用
class BaseActivity : AppCompatActivity() {
    @Inject
    lateinit var view: View

    @Inject
    lateinit var clothes: Clothes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        DaggerBaseContainer.builder().baseModule(BaseModule(baseContext)).build().inject(this)
        LogUtil.e("BaseActivity", view.toString())
    }
}
```
我们来看一下容器的源码
```java
public final class DaggerBaseContainer implements BaseContainer {
  private final BaseModule baseModule;

  private DaggerBaseContainer(BaseModule baseModuleParam) {
    this.baseModule = baseModuleParam;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public void inject(BaseActivity baseActivity) {
    injectBaseActivity(baseActivity);
  }

  private BaseActivity injectBaseActivity(BaseActivity instance) {
    BaseActivity_MembersInjector.injectClothes(instance, new Clothes());
    BaseActivity_MembersInjector.injectView(instance, BaseModule_GetViewFactory.getView(baseModule));
    return instance;
  }

  public static final class Builder {
    private BaseModule baseModule;

    private Builder() {
    }

    public Builder baseModule(BaseModule baseModule) {
      this.baseModule = Preconditions.checkNotNull(baseModule);
      return this;
    }

    public BaseContainer build() {
      Preconditions.checkBuilderRequirement(baseModule, BaseModule.class);
      return new DaggerBaseContainer(baseModule);
    }
  }
}
```
大致流程和之前的容器代码一样 1、根据接口生成代码 2、通过Budilder生成容器对象 3、将需要绑定的类和成员变量绑定
###### @Module
唯一的差异化是 在生成容器类对象的时候 需要传入@Component 注释里面的参数类，在双向绑定的时候，`依赖提供方` 获取的方式也有所不同。并且我们发现在Buidler里面对module做了很多检查。 接下里我们看下 提供方`BaseModule_GetViewFactory.getView(baseModule)` 是从哪里来的

```Java
  //1 、
  public final class ViewModule_GetViewFactory implements Factory<View> {
    private final ViewModule module;

    public ViewModule_GetViewFactory(ViewModule module) {
      this.module = module;
    }
    //1 、
    @Override
    public View get() {
      return getView(module);
    }
    //2
    public static ViewModule_GetViewFactory create(ViewModule module) {
      return new ViewModule_GetViewFactory(module);
    }

    public static View getView(ViewModule instance) {
      return Preconditions.checkNotNullFromProvides(instance.getView());
    }
  }
```
1、根据`module名称+方法名称` 生成类 并实现Factory接口，说明当前类也支持提供对象的能力了，2、通过构造传入ViewModule的对象，并且调用我们写好的方法`getView`方法，来获取View的 对象。3、那问题来了，ViewModule的实例从哪里获取到的呢？在我们调用的时候，通过Builder传入的，这个时候，就可以明白， 为什么一定要用builder模式来构建容器类了，当有多个module的时候，builder模式就起到作用了。

### Dagger2进阶-范围(单例)
在前面的使用中，我们 `依赖提供方`每次提供的都是一个新的实例，但是在实际开发中，我们有很多时候都希望项目中只存在一个实例，那就需要我们用到一个注解@Singleton ，由于单例只针对 `依赖提供方` 所以，注释主要只在两个地方1、module 中 2、被@Injec修饰构造方法得类
```Java
    //1 还需要在容器接口上添加
    @Provides
    @Singleton
    fun getView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_task_home, null)
    }

    //2
    @Singleton
    class  Person @Inject constructor(){

    }
```
##### @Singleton
```java
  //省略部分代码
  @Inject
  lateinit var clothes: Clothes   //普通被@Inject和 @Singleton 注释的修饰构造的类
  @Inject
  lateinit var phone: Phone       //普通被@Inject注释的修饰构造的类
  @Inject
  lateinit var view: View         //通过Module单例提供的类
  @Inject
  lateinit var house: House       //通过Module提供的类


  //生成的容器类
  //省略部分代码...
   BaseActivity_MembersInjector.injectClothes(instance, clothesProvider.get());
   BaseActivity_MembersInjector.injectPhone(instance, new Phone());
   BaseActivity_MembersInjector.injectView(instance, getViewProvider.get());
   BaseActivity_MembersInjector.injectHouse(instance, TestModule_HouseFactory.house(testModule));

```
我们继续分析容器类，在上面我们通过成员变量注释，像需求方注入了很多实例，这些实例的提供方各不相同,但最终都是和`instance`进行绑定，我们逐个分析

- Phone:`被@Inject注释修饰构造的类`之前已经讲过了。通过容器直接new 一个实例和 instance绑定

- Clothes:`被@Inject和 @Singleton注释 修饰构造的类`，相对于Phone类，Clothes是多了一个@Singleton注释，
```Java
  //1、获取Clothes 实例
  clothesProvider.get()
  //2clothesProvider 其实就是Clothes_Factory的实例
  this.clothesProvider = DoubleCheck.provider(Clothes_Factory.create());
  //3 我们分析一下DoubleCheck.provider
  //由于DoubleCheck也继承了Provider重写了get方法，
  //4 DoubleCheck.get()通过双重判断单例，获取到provider.get()也就是Clothes对象，保证唯一
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
- View:
```Java
    // 和Clothes相同
    this.getViewProvider = DoubleCheck.provider(ViewModule_GetViewFactory.create(viewModuleParam));
```
- House:

总结: @Singleton 保证提供对象时唯一的办法，就是通过调用`DoubleCheck`类中的get方法，通过单例进行判断


### Dagger2进阶-子组件
在实际项目中，为了方便管理，我们通常会将各个容器进行统一管理，这样有一个好处，父容器所提供的实例，子容器也可以用，并且子容器的生命周期是可控的。

######  @Subcomponent
 我们通过登陆模块进行详解，

LoginComponent.class 子容器接口
```Java
// 1、 首先定义一个登陆容器接口
@Subcomponent
interface LoginComponent {
    // 2、 提供一个接口 用来创建这个容器接口的实例
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }
    // 3、 绑定一个BaseActivity
    fun inject(loginActivity: BaseActivity)
}

```
ApplicationComponent.class 父容器接口
```Java
@Singleton
@Component(modules = [ViewModule::class, SubcomponentsModule::class])
interface ApplicationComponent {
    //提供一个方法，通过父容器获取子容器的实例
    fun loginComponent(): LoginComponent.Factory
}
```

SubcomponentsModule.class  
```Java
@Module(subcomponents = [LoginComponent::class])
class SubcomponentsModule {
}
```








在开发过程中，每个Module所提供实例的生命周期是不一样的，例如有很多需要常驻整个应用的实例，如网络的管理类NetworkModule，如本地数据库管理DatabaseModule，还有一些是不需要常驻整个应用的，需要每次都进行更新，


### 参考资料
- [@Component和@SubComponen](https://blog.csdn.net/soslinken/article/details/70231089)
