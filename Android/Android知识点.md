#	四大组件

## Activity:

启动模式:

- 默认

- singleTop

- singleTask

- singleInstance

  A(默认)--B(singleInstance)--C(默认)  按下Back键 C 返回A 返回B

生命周期:

![img](https://img-blog.csdn.net/20160717151833576?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## Server:

启动方式：

> StartServer 和 BinderServer

服务类型:

> 分为本地服务（LocalService）和远程服务（RemoteService）
>
> 1、本地服务依附在主进程上而不是独立的进程，这样在一定程度上节约了资源，另外Local服务因为是在同一进程因此不需要IPC，
>
> 也不需要AIDL。相应bindService会方便很多。主进程被Kill后，服务便会终止。
>
> 2、远程服务为独立的进程，对应进程名格式为所在包名加上你指定的android:process字符串。由于是独立的进程，因此在Activity所在进程被Kill的时候，该服务依然在

###### IntentService

> IntentService是Android中提供的后台服务类，我们在外部组件中通过Intent向IntentService发送请求命令，之后IntentService逐个执行命令队列里的命令，接收到首个命令时，IntentService就开始启动并开始一条后台线程执行首个命令，接着队列里的命令将会被顺序执行，最后执行完队列的所有命令后，服务也随即停止并被销毁。
>
> 与Service的不同
>
> 1. Service中的程序仍然运行于主线程中，而在IntentService中的程序运行在我们的异步后台线程中。在接触到IntentService之前，我在项目中大多是自己写一个Service，在Service中自己写一个后台线程去处理相关事务，而这些工作Android其实早已经帮我们封装的很好。
> 2. 在Service中当我的后台服务执行完毕之后需要在外部组件中调用stopService方法销毁服务，而IntentService并不需要，它会在工作执行完毕后自动销毁。
>

## BroadcastReceiver:

广播类型:



## ContentProvider:

作用





#	Handler机制

组成部分：

- Looper ：负责关联线程以及消息的分发，在该线程下从 MessageQueue 获取 Message，分发给 Handler。
- MessageQueue ：消息队列，负责Hander发送过来的Msg存储与管理，
- Handler : 负责发送并处理消息，面向开发者，提供 API，并隐藏背后实现的细节。
- Message：final类不可继承， 实现了Parcelable 序列化接口，可以在不同进程之间传递。

面试题：

1.为什么Looper死循环没能造成UI线程卡死

> 之所以死循环，可以保证UI线程不会被退出，因为Android中界面绘制都是通过Handler消息来实现，这样可以让界面保持可绘制的状态。
>
> 1，epoll模型
> 当没有消息的时候会epoll.wait，等待句柄写的时候再唤醒，这个时候其实是阻塞的。
>
> 2，所有的ui操作都通过handler来发消息操作。
> 比如屏幕刷新16ms一个消息，你的各种点击事件，所以就会有句柄写操作，唤醒上文的wait操作，所以不会被卡死了。



2.一个线程有几个looper？如何保障线程和Looper的管理？几个Handler？同一个Looper是怎么区分不同的Handler的?（保证消息Handler和Msg对应的）

> 一个线程只能有一个Looper，通过ThreadLocal 进行管理looper中多个线程 ，无数的Handler 但是都公用一个Loop，Handler#enqueueMessage方法中，会将当前Msg.targt = this;从而达到绑定区分
>

3.什么是HandlerTheard？

> 简单来说就是一个自带Looper的子线程，不需要自己管理Looper的生命周期

4.sendMsg 和sendMsgDelayed事件入消息队列，和收到的顺序

sendMsg 也会调用 sendDelayed()，默认时间为0，->   控制顺序sendMessageAtTime(msg，SystemClock.uptimeMillis(**开机到当前的时间总数**) + delayMillis )  



参考资料：

- https://blog.csdn.net/u013700040/article/details/105655154
- https://blog.csdn.net/ly502541243/article/details/52414637



##	自定义View



## 性能优化

1. 网络：API 优化、流量优化、弱网优化  
2. 内存：OOM 处理、内存泄漏、内存检测、分析、Bitmap 优化 ,LeakCanary 原理，什么检测内存泄漏需要两次？
3. 绘制 
4. 电量：WeakLock 机制、JobScheduler 机制  
5. APK 瘦身 （APK 瘦身是怎么做的，只用 armabi-v7a 没有什么问题么？
   APK 瘦身这个基本是 100% 被面试问到，可能是我简历上提到的原因。）  
6. 内存抖动  
7. 内存泄漏  
8. 卡顿 {如何检测卡顿，卡顿原理是什么，怎么判断页面响应卡顿还是逻辑处理造成的卡顿} ，BlockCanary 的原理 
9. 布局优化、过度渲染处理、ANR 处理、监控、埋点、Crash 上传。 
10. 启动优化



## 面试题

#### 常见面试题

- https://mp.weixin.qq.com/s/17XeoP8DEj2KTnKCBv4H_A



#### 面试题加答案

- Android--判断App处于前台还是后台的方案  		//https://blog.csdn.net/u011386173/article/details/79095757




