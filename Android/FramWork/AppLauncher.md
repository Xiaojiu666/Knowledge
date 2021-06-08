## what is Zygote
    zygote名字翻译叫受精卵，首先要知道zygote进程的创建是由Linux系统中init进程创建的，
    Android中所有的进程都是直接或者间接的由init进程fork出来的，Zygote进程负责其他进程的创建和启动，
    比如创建SystemServer进程。当需要启动一个新的android应用程序的时候，ActivityManagerService就会通过Socket通知Zygote进程为这个应用创建一个新的进程


## what is Launcher
	我们要知道手机的桌面也是一个App我们叫它launcher,每一个手机应用都是在Launcher上显示，
	而Launcher的加载是在手机启动的时候加载Zygote，然后Zygote启动SystenServer
	,SystenServer会启动各种ManageService, 包括ActivityManagerService,并将这些ManageService注册到ServiceManage容器中，然后ActivityManagerService就会启动Home应用程序Launcher.


## what is ActivityManagerService
	ActivityManagerService我们简称AMS,首先当我们看到名字的时候，我们以为他是管理Activity的,
	其实四大组件都归它管，四大组件的跨进程通信都要和它合作，后面讲Binder时候会提到它。
	AMS管理Activity主要是管理什么呢？ 这就要说到AMS的相关类：

-	ProcessRecord 表示应用进程记录，每个应用进程都有对应的ProcessRecord对象
-	ActivityStack 该类主要管理回退栈
-	ActviityRecord 每次启动一个Activity会有一个对应的ActivityRecord对象，表示一个Activity的一个记录
-	ActivityInfo Activity信息，比如启动模式，taskAffinity,flag信息
-	TaskRecord Task记录信息，一个Task可能有多个ActivityRecord，但是一个ActivityRecord只能属于一个TaskRecord


## what is Binder
	
	Binder是Android跨进程通信（IPC）的一种方式，也是Android系统中最重要的特性之一，android 四大组件以及不同的App都运行在不同的进程，
	它则是各个进程的桥梁将不同的进程粘合在一起。学习Binder首先要知道相关客户端、服务端的概念，然后去把AIDL弄透彻， 知道系统生成的Aidl Java文件中每一个类所代表的是什么角色，然后看源码，能将ActivityManageService 用来跨进程同通信中各个角色弄明白就算是理解了，不过这只是一部分部分，得多总结归纳。


## what is ActivityThread
	首先ActivityThread并不是一个Thread,其作用就是在main方法内做消息循环。那我们常说的主线程是什么？
	主线程就是承载ActivityThread的Zygote fork而创建的进程，这里可能有人会不能理解进程和线程的区别，这里不详细讲述自行学习。
	ActivityThread的调用是在ActivityManageService.startProcessLocked()方法里调用并创建，这个类主要做了这几个事
-		创建Looper,开启Looper循环
-		创建内部类 H，H继承于Handler 用于跨进程通信切换线程
-		创建ApplicationThread跨进程Binder对象mAppThread。 
-		这里要说一点，ActivityThread通过ApplicationThread与AMS进行通信，ApplicationThread通过H与ActivityThread进行通信（handler机制），处理Activity的事务



 main(ZygoteInit)
        |
 forkSystemServer
        |
 handleSystemServerProcess
        |
zygoteInit
        |
applicationInit()

findStaticMain

MethodAndArgsCaller

main(ActivityThread)

handleMessage
    LAUNCH_ACTIVITY
handleLaunchActivity

performLaunchActivity

callActivityOnCreate

performCreate







## 参考资料
-   https://blog.csdn.net/oheg2010/article/details/82826415
-   



