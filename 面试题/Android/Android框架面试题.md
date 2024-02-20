### 网络请求

#### OkHttp在实现中应用了以下几种常见的设计模式:
-   工厂方法模式(Factory Method) - OkHttp通过工厂方法创建Request、Response等不同子类的实例。
-   单例模式(Singleton) - OkHttp的Dispatcher、ConnectionPool等为单例,防止重复创建。
-   外观模式(Facade) - OkHttp类提供了简单易用的外部接口,隐藏了内部的复杂逻辑。
-   代理模式(Proxy)- OkHttp使用代理模式实现拦截器,以扩展功能。
-   过滤器模式(Filter)- 拦截器链像过滤器链一样,对请求/响应进行处理。
-   策略模式(Strategy) - Strategy如RetryAndFollowUpInterceptor用于不同的重试策略。
-   建造者模式(Builder) - 如Request.Builder构建请求。
-   观察者模式(Observer) - Call回调接口允许观察调用过程。
-   中介者模式(Mediator) - Dispatcher充当协调线程之间消息的中介者。

#### 拦截器链
-  RetryAndFollowUpInterceptor：重试和失败定向拦截器，会创建StreamAllocation对象，用来传递给后面的拦截器
-  BridgeInterceptor：桥接和适配拦截器
-  CacheInterceptor：缓存拦截器
-  ConnectInterceptor：连接拦截器，建立可用的连接，是下面拦截器的基础
-  CallServerInterceptor：负责将我们的请求写进网络流中，别切会从IO流中读取服务器返回给我们的客户端的数据