### 什么是Dagger2
  当我们有一个Person类 需要去实例化，通常手段都是`Person person = new Person()`, 如果 Person 中需要依赖一个Phone对象，一般情况下都是通过构造传入`new Person(new Phone)`，或者 set 方法 传入，这其实就是依赖注入最常见的两种方式，Dagger2 就是此两种方式上，通过`注解+JavaPoet`等手段，在编译期间，动态的生成 依赖方 所需要的对象。

### 使用Dagger2

#### 基础介绍
围绕着上面的问题，我们尝试使用Dagger来试一下效果，并了解下其中原理
######  @Inject
  最核心的注释，自动生成创建对象的工厂 可以说其他的注释均为此类提供服务，主要注释在两个地方(构造函数和全局变量)

  - 空构造函数:

  被注解的类，Dagger会自动帮我们实例化对象，也就是 帮助我们做了`new Phone()`的动作，那让我们试一下，被`@Inject`注释的类会发生什么变化。
```Java
//手机类，@Inject 后，Rebuid一下项目
class Phone @Inject constructor() {
        var phoneName = "Huawei"
}
```
上面讲过 ，Dagger2会在编译期生成代码，Rebuid一下项目，我们就会看到Dagger帮我们生成的代码，让我们查看一下
```Java
//省略..。
public final class Phone_Factory implements Factory<Phone> {
        @Override
        public Phone get() {
          return newInstance();
        }

        public static Phone_Factory create() {
          return InstanceHolder.INSTANCE;
        }

        public static Phone newInstance() {
          return new Phone();
        }

        private static final class InstanceHolder {
          private static final Phone_Factory INSTANCE = new Phone_Factory();
        }
}
```
分析下生成的`Phone_Factory`类 首先不可修改 实现Factory工厂接口，主要就是做了两件事：1、重写get方法，返回我们@Inject 构造的对象，其实就是帮我们`new 了一个 Person()`。2、生成一个`Phone_Factory`的单例(注意，是Phone_Factory，而不是Person单例)。

  那我们是不是可以通过`Phone_Factory.newInstance()` 或者 `Phone_Factory.create().get` 就能拿到Phone了呢，确实可以，但这样做完全没意义，直接new 不香么，没人会傻到这么做，虽然代码不多，但这个Factory 就已经是是Dagger的核心了，后面的进阶，基本都是通过其他注释，进行二次包装。
  - 带参构造:

  将Phone注入Person
  ```Java
  class Person @Inject constructor(var phone: Phone) {}
  ```
  Rebuild一下项目

  ```Java
  public final class Person_Factory implements Factory<Person> {
      private final Provider<Phone> phoneProvider;

      //1、
      public Person_Factory(Provider<Phone> phoneProvider) {
        this.phoneProvider = phoneProvider;
      }

      @Override
      public Person get() {
        return newInstance(phoneProvider.get());
      }

      public static Person_Factory create(Provider<Phone> phoneProvider) {
        return new Person_Factory(phoneProvider);
      }

      public static Person newInstance(Phone phone) {
        return new Person(phone);
      }
    }
  ```
  对比无参构造发现，你会发现两大区别

  1、Factory本身的创建方式需要提供一个Provider对象,如果你在上面细看过源码后，你会发现Factory 实现了Provider接口 ，那也就是说，这里可以理解为传入了一个Phone_Factory。

  2、Person newInstance的时候也需要一个参数，而这个参数，则是通过Phone_Factory.get()获取到的，其实这时候就关联起来了，这样我们就通过Person_Factory.get()方法拿到了一个带着Phone对象的Person引用

  但这还是不够，因为没人会这样用，简直就是多此一举，接下来我们使用其他注解，继续完善代码。


  - 成员变量:

  在开发的过程中，并不是所有类都有构造，例如Android中，常见的Activity，全部是通过系统源码创建，那我们如何像Activity中注入所需要的对象呢，Dagger 为我们提供了一种方法，将@Inject 注解在成员变量上。

  我们依旧将Phone注入给Person

  1、声明容器
  ```java
  @Component
  //@Component下面会说到用法
    interface PersonContainer {
        fun inject(person: Person)
    }
  ```
  2、调用并注入
  ```Java
  class Person {
        @Inject
        @JvmField
        public var phone: Phone? = null

        init {
            DaggerPersonContainer.create().inject(this)
        }
}

  ```
  rebuild项目生成代码

  容器接口暂时省略..
  下面讲到在细说

  根据依赖需求方 Person 中 被@Inject修饰的变量 生成此类
```Java
  //1
  public final class Person_MembersInjector implements MembersInjector<Person> {
      private final Provider<Phone> phoneProvider;
      //2 构造
      public Person_MembersInjector(Provider<Phone> phoneProvider) {
        this.phoneProvider = phoneProvider;
      }

      public static MembersInjector<Person> create(Provider<Phone> phoneProvider) {
        return new Person_MembersInjector(phoneProvider);
      }

      //3
      @Override
      public void injectMembers(Person instance) {
        injectPhone(instance, phoneProvider.get());
      }
      //4
      @InjectedFieldSignature("com.gx.task.di.demo.Person.phone")
      public static void injectPhone(Person instance, Phone phone) {
        instance.phone = phone;
      }
}
  ```
  我们继续分析源码

  来看被我们注释的成员变量:1、Dagger检测到 Person类中有变量存在@Inject注释，自动根据Person(需求方的名字)+_MembersInjector 实现 MembersInjector生成一个类。2、构造仍然传入一个 `Provider<T>`(注入方的实例)，3、重写接口中`injectMembers()`方法，
  4、核心关键`injectPhone()`方法，把phone的实例，赋值给person中的 phone变量,用于绑定。


最后我们总结一下@Inject注释：
- 注解在构造:1、被注解的类，自动实现`Provider<T>`接口生成工厂类，通过实现接口中`get()`方法，主要用于提供注解类的对象。2、如果被注解的构造有参数，则会通过参数的`Provider<T>`的`get()`方法，注入到需求方。
- 注解在变量:2、被注解变量所在的类，自动实现`MembersInjector<T>`接口生成帮助类，通过实现接口中的`injectMembers()`方法，进行赋值绑定。

######  @Component
  之前说过，整个依赖注入里面可以包括三个模块，被注入方，依赖提供方，容器。上面我们已经通过@Injected注释，讲了被注入方和提供方，接下来我们看看被@Component注释的接口。上面在注解成员变量的时候，我们有用过@Component注释的接口。让我们继续看下源码。

  @Component相对来说比较简单，只能用在接口上
  - 简单对外提供实例

  ```Java
  @Component
  interface PersonContainer {
      fun person(): Person
  }
  // 调用DaggerPersonContainer.create().person();
  ```
  依旧是Build一下项目

  ```Java
    // 1
    public final class DaggerPersonContainer implements PersonContainer {
        private final DaggerPersonContainer personContainer = this;
        private DaggerPersonContainer() {

        }
        public static Builder builder() {
          return new Builder();
        }
        public static PersonContainer create() {
          return new Builder().build();
        }
        // 2
        @Override
        public Person person() {
          return new Person(new Phone());
        }
        public static final class Builder {
          private Builder() {
          }
          public PersonContainer build() {
            return new DaggerPersonContainer();
          }
        }
  }
  ```
  继续枯燥的分析源码: 1、被@Component 注释的接口 会自动以`Dagger+接口名`生成实现类,通过一个Builder模式提供当容器类的实例(为什么用Builder模式后面会讲到) 2、重写接口方法，根据方法返回值，帮助生成对象。


  - 赋值绑定

  在参构造时，我们通过 把当前类当作参数传递给容器。
  ```java
    @Component
      //@Component下面会说到用法
      interface PersonContainer {
          fun inject(person: Person)
      }

      //调用DaggerPersonContainer.create().inject(this);
  ```
  ```Java
  public final class DaggerPersonContainer implements PersonContainer {
      private final DaggerPersonContainer personContainer = this;
      private DaggerPersonContainer() {

      }

      public static Builder builder() {
        return new Builder();
      }

      public static PersonContainer create() {
        return new Builder().build();
      }

      @Override
      public void inject(Person person) {
        injectPerson(person);
      }

      private Person injectPerson(Person instance) {
        Person_MembersInjector.injectPhone(instance, new Phone());
        return instance;
      }

      public static final class Builder {
        private Builder() {
        }

        public PersonContainer build() {
          return new DaggerPersonContainer();
        }
      }
  }
```
  首先来看容器接口: 1、根据容器接口，生成容器实现类，通过`create()`方法使用Builder模式，生成当前类的实例。2、实现接口方法`inject()`，将传入的person 和  `new phone()`传入到 上面提过的`Person_MembersInjector`类中，进行赋值绑定。

简单总结一下@Component 注释:
- 协助@Injcet 接口对外提供对象
- 只能用于接口上，会根据接口自动生成类
- 会根据接口的里面的方法自动生成实例(普通)，或者协助两个类做绑定(适用于无参构造)。


问题:
往往在使用一个框架的时候，一定要经常问自己，为何这样设计？有什么好处？

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
在前面的使用中，我们需求提供方每次都是一个新的实例，但是在实际开发中，我们有很多时候都希望项目中只存在一个实例，那就需要我们用到一个注解@Singleton


### Dagger2进阶-子组建
