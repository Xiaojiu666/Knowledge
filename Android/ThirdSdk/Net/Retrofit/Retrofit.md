##  什么是Retrofit
    Retrofit是squareup公司的开源力作，和同属squareup公司开源的OkHttp，
    一个负责网络调度，一个负责网络执行，为Android开发者提供了即方便又高效的网络访问框架。
    对于Retrofit，我们还应该看到的，是她在优化App架构方面的努力，以及她在提升开发效率方面的借鉴和启示。

##  如果使用什么是Retrofit
####  1.引入
    基本使用
    compile 'com.squareup.retrofit2:retrofit:2.3.0'
    compile 'com.squareup.retrofit2:retrofit-converters:2.3.0'
    compile 'com.squareup.retrofit2:retrofit-adapters:2.3.0'
    扩展功能
    compile 'com.squareup.retrofit2:converter-gson:2.3.0'
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
####  2.



## 源码分析
    Retrofit.create(service) -进入create方法->Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },  new InvocationHandler() 通过 动态代理 去代理我们传入的接口，方法，和参数，获取所有接口中的方法--->通过loadServiceMethod(method)方法，将接口中定义的方法转换成注释上面请求的网络路径包装类ServiceMethod类—-->将需要的请求路径 和参数传入到new OkHttpCall<>(serviceMethod, args)--》通过 OkHttpCall 类 enqueue（）执行网络请求，将 请求路径和地址进行拼接


## 参考资料

####### https://www.jianshu.com/p/f57b7cdb1c99
