---
title: apk学习-今日头条
date: 2023-1-6 16:24:41
categories:
           - apk学习
tags:
           - 反编译
           - 屏幕适配
---
### 前言
##### 背景
如果我看得更远，那是因为我站在巨人的肩膀上。  
前段时间面试，被问到屏幕适配的问题，日常开发中，适配遇到最多的问题的就是，给View指定dp，因为不同分辨率和尺寸的手机，屏幕密度不同，导致 1dp = 1px = 2px =3px， 解决方案 除了根据设计姐姐 切的不同分辨率的图以外，尽量不使用指定dp， 但有地方强制要求，对父控件选择时，尽量选择带百分比的，保证整体UI不错乱变形。  
衍生问题: 想到几年前看到一篇关于[今日头条的适配方案](https://www.jianshu.com/p/5660c1f528ec)，突然觉得，巨人就在眼前，为什么不去看看别人的项目架构和优化方案呢？

##### 入手方向
要想了解别人的项目，个人认为应该从几个点切入:   
- 1、官方技术博客
- 2、一般大厂的项目，都会遵循开源协议，将使用的框架暴露出来  
- 3、通过apk，分析资源文件、清单文件和项目结构等  
- 4、通过反编译进行业务逻辑查看(但大多数都会混淆，压力会大很多)
- 5、通过抓包查看网络接口数据(大多数请求数据会加密...)

##### 分析版本
来源: 网上资源包  
版本: Android  
应用版本: Version 9.1.7
包名: com.ss.android.article.news

### 开源协议
![在这里插入图片描述](https://img-blog.csdnimg.cn/3883213f845e45e48f74f1210e04a779.png)  
###### UI适配
看得出来，开源协议里面 有引用第三方库[AndroidAutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout)库用来适配。  
处理AndroidUI适配，无非从两个角度出发。  
1、通过代码根据计算出屏幕密度，进行比例计算Px，1dp定义为屏幕密度值为160ppi时的1px，即，在mdpi时，1dp = 1px。 以mdpi为标准，这些屏幕的密度值比为：ldpi : mdpi : hdpi : xhdpi : xxhdpi = 0.75 : 1 : 1.5 : 2 : 3；即，在xhdpi的密度下，1dp=2px；在hdpi情况下，1dp=1.5px。其他类推。  
2、让UI 多切图通过保证图片每个尺寸都有不同的分辨率，根据插件自动生成适配文件-xxhdpi等适配的文件夹，将图片放入，保证UI不会模糊出现失真。
###### 其他库
其他都是开发中经常用到的，例如网络请求 okio， 图片加载picasso ，数据流程RxJava等。



### apk分析
![在这里插入图片描述](https://img-blog.csdnimg.cn/ea7d818ed30f401aade556377820f692.png)
看得出来，资源和C++的动态库占了40%左右
##### assets资源部份
主要存放一些jar包/ js文件/ 字体 还有一些配置文件和图片资源，但大多数都为了H5服务的
![在这里插入图片描述](https://img-blog.csdnimg.cn/b84019c7ff5d413d94d52d1b71215f80.png)

##### Res资源部分
从压缩包内容来看，大多数图片资源都只在默认的drawable-x/xx/xxxhdpi下

看下layout文件夹，虽然xml文件名称被混淆，但是内容还是可以看到大概的
![在这里插入图片描述](https://img-blog.csdnimg.cn/6f0dee09258c4340b4ac7f56ea0c10ef.png)
不得不说，今日头条的布局可真够多的，3840个
![在这里插入图片描述](https://img-blog.csdnimg.cn/874937c4f35f444185ca68bba3d3d91d.png)
发现大多布局文件的根布局，都有一个自定义的View` FeedItemRootxxxLayout`，里面有个tt_skin_ignore_view 属性， 应该是控制换肤的，控制一些整体变化用的。  
往下看了几十个，自定义View 是真的多，还有不少地方用到了ConstraintLayout 做适配。

看完布局，再看看,项目结构。

##### 源码部分
![在这里插入图片描述](https://img-blog.csdnimg.cn/849d8e5209f844f0b18b9b3f97fc7442.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/4e4c4f1c3c2c4cf4bb7ec6bb344ff23f.png)
通过清单文件找到包名、启动页
![在这里插入图片描述](https://img-blog.csdnimg.cn/60241d2a03bd4da58174ac71e9fd8720.png)

分析文件路径，个人感觉整体并不是MVP 和MVVM的结构，更多的像是以功能点划分，例如:文章/直播/我得/隐私协议/主题等  

简单分析启动页onCreate()方法和构造的smail语法
```Java
.class public Lcom/ss/android/article/news/activity/SplashActivity;
.super Lcom/ss/android/newmedia/activity/AbsSplashActivity;
.source "SourceFile"


# annotations
.annotation runtime Lcom/bytedance/splash/api/SplashActivity;
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;  #注解类为MemberClasses，这里定义的是该类中的内部类
    value = {
        LX/1dX;             # 内部类路径
    }
.end annotation


# static fields
.field public static changeQuickRedirect:Lcom/meituan/robust/ChangeQuickRedirect;  # 美团热修复的对象 https://github.com/Meituan-Dianping/Robust

.field public static sInited:Z   # boolean类型 静态变量，是否序列化


# instance fields
.field public enableInitSchedulerGlobal:Z    # boolean类型 启用调度 不知是否和线程有关系
.field public mJumpIntent:Landroid/content/Intent; # Intent 不用多说了，跳转意图

.field public mReadApkLock:Ljava/util/concurrent/CountDownLatch;  #线程同步的一种方式 (CountDownLatch)[https://blog.csdn.net/liu_da_da/article/details/124983187]


# direct methods   方法文件
.method public static constructor <clinit>()V  # 无参构造
    .registers 1  #会使用到寄存器个数

    .prologue
    .line 262144 #源文件行数
    new-instance v0, Lcom/ss/android/article/news/activity/SplashActivity$1; 创建一个SplashActivity对象，放入v0寄存器

    #调用SplashActivity 构造
    invoke-direct {v0}, Lcom/ss/android/article/news/activity/SplashActivity$1;-><init>()V

     #调用静态内部类 LX/1Bh 的e 方法，传入 Runnable 接口，返回 Future 接口  多线程操作
    invoke-static {v0}, LX/1Bh;->e(Ljava/lang/Runnable ;)Ljava/util/concurrent/Future;

    new-instance v0, LX/1db;

    invoke-direct {v0}, LX/1db;-><init>()V

    invoke-static {v0}, LX/1Bh;->d(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;

    new-instance v0, LX/1dZ;


    invoke-direct {v0}, LX/1dZ;-><init>()V
    invoke-static {v0}, LX/1Bh;->d(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;
    new-instance v0, LX/1dW;
    invoke-direct {v0}, LX/1dW;-><init>()V


    invoke-static {v0}, LX/1Bh;->d(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;

    return-void
.end method

.method public constructor <init>()V
    .registers 3

    .prologue
    invoke-direct {p0}, Lcom/ss/android/newmedia/activity/AbsSplashActivity;-><init>()V


    new-instance v1, Ljava/util/concurrent/CountDownLatch;


    const/4 v0, 0x1

    invoke-direct {v1, v0}, Ljava/util/concurrent/CountDownLatch;-><init>(I)V

    iput-object v1, p0, Lcom/ss/android/article/news/activity/SplashActivity;->mReadApkLock:Ljava/util/concurrent/CountDownLatch;


    const-string v0, "enableGlobal"

    invoke-static {v0}, LX/1AS;->a(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Boolean;

    invoke-virtual {v0}, Ljava/lang/Boolean;->booleanValue()Z


    move-result v0

    iput-boolean v0, p0, Lcom/ss/android/article/news/activity/SplashActivity;->enableInitSchedulerGlobal:Z
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V   # onCreate 方法
    # 分配8个寄存器
    .registers 8
    .prologue
    .line 17104896
    #通过sget-object指令把changeQuickRedirect这个ChangeQuickRedirect类型的成员变量获取并放到v3寄存器中。
    sget-object v3, Lcom/ss/android/article/news/activity/SplashActivity;->changeQuickRedirect:Lcom/meituan/robust/ChangeQuickRedirect;


    #调用静态方法isEnable 传入变量，返回布尔类型的值
    invoke-static {v3}, Lcom/meituan/robust/PatchProxy;->isEnable(Lcom/meituan/robust/ChangeQuickRedirect;)Z

    move-result v0

    const/4 v2, 0x1

    const/4 v5, 0x0

    # 条件语法 判断是否相等
    if-eqz v0, :cond_1a

    .line 17104905
    .line 17104906
    # 创建数组
    new-array v1, v2, [Ljava/lang/Object;
    aput-object p1, v1, v5

    const v0, 0x33314

    # 热修复逻辑
    invoke-static {v1, p0, v3, v5, v0}, Lcom/meituan/robust/PatchProxy;->proxy([Ljava/lang/Object;Ljava/lang/Object;Lcom/meituan/robust/ChangeQuickRedirect;ZI)Lcom/meituan/robust/PatchProxyResult;
    move-result-object v0
    iget-boolean v0, v0, Lcom/meituan/robust/PatchProxyResult;->isSupported:Z
    if-eqz v0, :cond_1a

    return-void

    ...
    # 看起来像是 埋点操作
    const-string v4, "com.ss.android.article.news.activity.SplashActivity"
    const-string v3, "onCreate"
    invoke-static {v4, v3, v2}, Lcom/bytedance/apm/agent/v2/instrumentation/ActivityAgent;->onTrace(Ljava/lang/String;Ljava/lang/String;Z)V

    ...
     # 设置背景
    invoke-direct {p0}, Lcom/ss/android/article/news/activity/SplashActivity;->setBackground()V
    invoke-static {}, LX/1ma;->a()V
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J
    move-result-wide v1
    const-string v0, "SplashActivity-onCreate"
    invoke-static {v0, v1, v2, v5}, LX/2CQ;->a(Ljava/lang/String;JZ)V
    const-string v0, "Splash onCreate"
    invoke-static {v0}, LX/1BK;->a(Ljava/lang/String;)V
    sget-object v0, Lcom/bytedance/lego/init/model/InitPeriod;->SPLASH_ONCREATE2SUPER:Lcom/bytedance/lego/init/model/InitPeriod;
    # 初始化 什么模块的周期
    invoke-static {v0}, LX/19A;->a(Lcom/bytedance/lego/init/model/InitPeriod;)V
    # 是否允许网络
    invoke-virtual {p0}, Lcom/ss/android/newmedia/activity/AbsSplashActivity;->isAllowNetwork()Z
    move-result v0
    # 条件语句
    if-eqz v0, :cond_46
    invoke-static {p0}, Lcom/ss/android/article/news/activity/SplashActivity;->startAppListThread(Landroid/content/Context;)V
    invoke-direct {p0}, Lcom/ss/android/article/news/activity/SplashActivity;->onCreateForUg()V
    :cond_46
    # 混淆了，看不太出来
    invoke-static {p0}, LX/BrH;->e(Landroid/app/Activity;)V
    invoke-static {p0}, LX/BrH;->a(Landroid/app/Activity;)V
    const-string v0, "onCreateOld"
    invoke-static {v0}, LX/1BK;->a(Ljava/lang/String;)V
    invoke-direct {p0, p1}, Lcom/ss/android/article/news/activity/SplashActivity;->onCreateOld(Landroid/os/Bundle;)V
    invoke-static {}, LX/1BK;->a()V
    invoke-static {p0}, LX/BrH;->f(Landroid/app/Activity;)V
    invoke-static {}, LX/1BK;->a()V
    invoke-static {v4, v3, v5}, Lcom/bytedance/apm/agent/v2/instrumentation/ActivityAgent;->onTrace(Ljava/lang/String;Ljava/lang/String;Z)V
    return-void
.end method

```

##### lib动态库部分
![在这里插入图片描述](https://img-blog.csdnimg.cn/7595b31b9742464ab1653cefc31441a0.png)
今日头条app只对 [v7a架构](https://blog.csdn.net/qq_22990635/article/details/122706266)的处理器做了适配  
大多数都是字节团队自己的库，有部分推送，加密，log，图像处理等，剩下的靠名字认不出来。

### 总结
##### 架构方面
1、采用美团的热更新技术
2、开发语言应该多采用java
3、未发现特殊的架构

##### 优化方面
1、使用了AndroidAutoLayout、ConstraintLayout、多文件 结合保证UI问题 。
2、将图片格式采用 webp 减少资源大小

##### 其他
时间仓促，只能发现皮毛，只有巨人的肩膀上才能看到的更远，有时间继续深耕~
