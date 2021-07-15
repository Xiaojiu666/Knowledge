### 什么是"依赖注入(DI)"
##### 官方介绍
  类在使用过程中，通常会引用其他的类，例如Car 需要引用 Engine 类。这些必需类称为依赖项，在此示例中，Car 类依赖于拥有 Engine 类的一个实例才能运行。
  类通常通过以下三种方式获取所需的对象：

    1.类构造其所需的依赖项。Car 将创建并初始化自己的 Engine 实例。
    2.从其他地方抓取。某些 Android API（如 Context getter 和 getSystemService()）的工作原理便是如此。
    3.以参数形式提供。应用可以在构造类时提供这些依赖项，或者将这些依赖项传入需要各个依赖项的函数。
   第三种方式，其实就算是手动依赖注入，通俗来讲就是

```Java
    class Car {

        private Engine engine = new Engine();

        public void start() {
            engine.start();
        }
    }

    class MyApp {
        public static void main(String[] args) {
            Car car = new Car();
            car.start();
        }
    }

```
##### Android 中有两种主要的依赖项注入方式(手动依赖项注入)：
    自行创建、提供并管理不同类的依赖项，而不依赖于库。这称为手动依赖项注入或人工依赖项注入。
- 构造函数
- 字段注入(或 setter 注入)

##### 自动依赖注入
    手动依赖注入，在只有一个依赖项会很简洁，但依赖项和类越多，手动依赖项注入就越繁琐。手动依赖项注入还会带来多个问题：  
- 对于大型应用，获取所有依赖项并正确连接它们可能需要大量样板代码。在多层架构中，要为顶层创建一个对象，必须提供其下层的所有依赖项。例如，要制造一辆真车，可能需要引擎、传动装置、底盘以及其他部件；而要制造引擎，则需要汽缸和火花塞。
```Java
    public void main(String[] args) {
          EngineConfig engineConfig = new EngineConfig();
          EngineConfig engineConfigB = new EngineConfig();
          Engine engine = new Engine(engineConfig, engineConfigB);
          Car car = new Car(engine);
      }

      class Car {
          // 构造注入
          public Car(Engine engine) { }
      }

      class Engine {
          // 构造注入
          public Engine(EngineConfig configA, EngineConfig configB) { }
      }

      class EngineConfig {
          // 构造注入
          public EngineConfig() { }
      }
```
- 如果您无法在传入依赖项之前构造依赖项（例如，当使用延迟初始化或将对象作用域限定为应用流时），则需要编写并维护管理内存中依赖项生命周期的自定义容器（或依赖关系图）。
    从而有一些库通过自动执行创建和提供依赖项的过程解决此问题。它们归为两类：
- 基于反射的解决方案，可在运行时连接依赖项.[Guice](https://en.wikipedia.org/wiki/Google_Guice)
- 静态解决方案，可生成在编译时连接依赖项的代码。[Dagger](https://dagger.dev/)


##### 依赖项注入的替代方法
  依赖项注入的替代方法是使用服务定位器。服务定位器设计模式还改进了类与具体依赖项的分离。您可以创建一个名为服务定位器的类，该类创建和存储依赖项，然后按需提供这些依赖项。
```Java
class ServiceLocator {

    private static ServiceLocator instance = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized(ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public Engine getEngine() {
        return new Engine();
    }
}

class Car {

    private Engine engine = ServiceLocator.getInstance().getEngine();

    public void start() {
        engine.start();
    }
}

class MyApp {
    public static void main(String[] args) {
        Car car = new Car();
        car.start();
    }
}
```
服务定位器模式与依赖项注入在元素使用方式上有所不同。使用服务定位器模式，类可以控制并请求注入对象；使用依赖项注入，应用可以控制并主动注入所需对象。
与依赖项注入相比：
- 服务定位器所需的依赖项集合使得代码更难测试，因为所有测试都必须与同一全局服务定位器进行交互。


### 手动依赖注入

### Dagger
##### 什么是Dagger
  Dagger 可以执行以下操作，使您无需再编写冗长乏味又容易出错的样板代码：
- 生成您在手动 DI 部分手动实现的 AppContainer 代码（应用图）。
- 为应用图中提供的类创建 factory。这就是在内部满足依赖关系的方式。
- 重复使用依赖项或创建类型的新实例，具体取决于您如何使用作用域配置该类型。
- 为特定流程创建容器，操作方法与上一部分中使用 Dagger 子组件为登录流程创建容器的方法相同。这样可以释放内存中不再需要的对象，从而提升应用性能。


### QA


### 参考资料
- [Android 中的依赖项注入](https://developer.android.google.cn/training/dependency-injection#java)
