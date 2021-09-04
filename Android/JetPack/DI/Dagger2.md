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

  容器类

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
根据依赖需求方 Person 中 被@Inject修饰的变量 生成此类
```Java
  public final class Person_MembersInjector implements MembersInjector<Person> {
      private final Provider<Phone> phoneProvider;
      //构造
      public Person_MembersInjector(Provider<Phone> phoneProvider) {
        this.phoneProvider = phoneProvider;
      }

      public static MembersInjector<Person> create(Provider<Phone> phoneProvider) {
        return new Person_MembersInjector(phoneProvider);
      }

      @Override
      public void injectMembers(Person instance) {
        injectPhone(instance, phoneProvider.get());
      }

      @InjectedFieldSignature("com.gx.task.di.demo.Person.phone")
      public static void injectPhone(Person instance, Phone phone) {
        instance.phone = phone;
      }
}
  ```
  我们继续分析源码

  首先来看容器接口: 1、根据容器接口，生成容器实现类，通过`create()`方法使用BUilde模式，生成当前类的实例。2、实现接口方法`inject()`，将传入的person 和  `new phone()`进行绑定。

  再来看被我们注释的成员变量:1、Dagger检测到 Person类中有变量存在@Inject注释，自动根据Person(需求方的名字)+_MembersInjector 实现 MembersInjector生成一个类。2、构造仍然传入一个 `Provider<T>`(注入方的实例)，3、重写接口中`injectMembers()`方法，
  4、和之前一样，通过`create()`方法 对外提供当前类实例。5、核心关键`injectPhone()`方法，把phone的实例，赋值给person中的 phone变量。


最后我们总结一下@Inject注释：
- 注解在构造:1、被注解的类，自动实现`Provider<T>`接口生成工厂类，通过实现接口中`get()`方法，用于提供注解类的对象。2、如果被注解的构造有参数，则会通过参数的`Provider<T>`的`get()`方法，注入到需求方。
- 注解在变量:2、被注解变量所在的类，自动实现`MembersInjector<T>`接口生成帮助类，通过实现接口中的`injectMembers()`方法，进行赋值绑定。

######  @Component
  之前说过，整个依赖注入里面可以包括三个模块，被注入方，依赖提供方，容器。上面我们已经通过@Injected注释，讲了被注入方和提供方，接下来我们看看被@Component注释的接口。上面在注解成员变量的时候，我们有用过@Component注释的接口。那让分析下源码。
  @Component相对来说比较简单，只能用在接口上
  ```Java
  @Component
  interface PersonContainer {
      fun person(): Person
  }
  ```
  依旧是Build一下项目
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
  分析代码发现，还是挺简单的，被@Component 注释的借口 会自动以`Dagger+接口名`生成实现类,通过一个Builder模式创建自己(为什么用Builder模式后面会讲到)，并且根据接口的方法，自动去new 所需要的对象，这时候就可以通过`DaggerPersonContainer.create().person()` 获取对象了，其实到这里，就会产生很多疑问？既然Person 是直接new的 那Factory的作用呢？如果这里是直接new的，那构造是不是可以不用@Inject注释？事实证明，我们才刚刚学到了皮毛，真正的用法，还需要继续深入学习。

#### 进阶-
