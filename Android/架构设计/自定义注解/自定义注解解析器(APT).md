
### APT
APT，就是Annotation Processing Tool 的简称，就是可以在代码编译期间对注解进行处理，并且生成Java文件，减少手动的代码输入。注解我们平时用到的比较多的可能会是运行时注解，比如大名鼎鼎的retrofit就是用运行时注解，通过动态代理来生成网络请求。编译时注解平时开发中可能会涉及的比较少，但并不是说不常用，比如我们经常用的轮子Dagger2, ButterKnife, EventBus3 都在用，所以要紧跟潮流来看看APT技术的来龙去脉。




### 注意事项
- 1、新建Processor库时， 一定要用javaLib，否则会找不到AbstractProcessor
- 2、如果Processor库中使kotlin做开发语言，使用AutoService做注册的时候，一定要使用kapt，否则Auto找不到实现类

```Java
kapt 'com.google.auto.service:auto-service:1.0-rc7'
implementation  'com.google.auto.service:auto-service-annotations:1.0-rc7'
```


### 参考资料
- [APT技术](https://www.jianshu.com/p/9616f4a462bd)
