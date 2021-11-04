### 什么是Hilt

官方介绍:

  Hilt 是 Android 的依赖项注入库，
  Hilt 在热门 DI 库 Dagger 的基础上构建而成，因而能够受益于 Dagger 的编译时正确性、运行时性能、可伸缩性和 Android Studio 支持。

Hilt的本质就是在[Dagger2](https://blog.csdn.net/xiaoJiu___/article/details/120783647)基础上构建而成，针对Android平台，进行了额外一些扩展。

### Hilt-基础

##### @HiltAndroidApp

```java
//使用
@HiltAndroidApp
class MainApplication : BaseApplication() {

}
//build generate
public abstract class Hilt_MainApplication extends Application implements GeneratedComponentManagerHolder {
  private final ApplicationComponentManager componentManager = new ApplicationComponentManager(new ComponentSupplier() {
    @Override
    public Object get() {
      return DaggerMainApplication_HiltComponents_SingletonC.builder()
          .applicationContextModule(new ApplicationContextModule(Hilt_MainApplication.this))
          .build();
    }
  });

  @Override
  public final ApplicationComponentManager componentManager() {
    return componentManager;
  }

  @Override
  public final Object generatedComponent() {
    return this.componentManager().generatedComponent();
  }

  @CallSuper
  @Override
  public void onCreate() {
    // This is a known unsafe cast, but is safe in the only correct use case:
    // MainApplication extends Hilt_MainApplication
    ((MainApplication_GeneratedInjector) generatedComponent()).injectMainApplication(UnsafeCasts.<MainApplication>unsafeCast(this));
    super.onCreate();
  }
}
```
该注释将生成一个基类，类名以`Hilt_+"被注释的类名"`，被注释的类会继承该类。


### 参考资料
- [Hiltdependencies](https://dagger.dev/hilt/gradle-setup.html)
