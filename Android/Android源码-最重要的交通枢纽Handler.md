#	什么是Handler机制

众所周知，Handler消息机制是为了解决Android线程间通讯而设计的，在开发过程中，最常用的地方就是子线程从网络获取数据，通过Handler().post(r)/sendMsg()通知主线程更新UI。

Handler消息机制，由多个类组成：

- Looper ：负责关联线程以及消息的分发，在该线程下通过loop方法()从 MessageQueue 获取 Message，分发给 Handler。
- MessageQueue ：消息队列，负责Hander发送过来的Msg存储与管理，
- Handler : 负责发送并处理消息，面向开发者，提供 API，并隐藏背后实现的细节。
- Message：用于存放消息内容和其他相应数据，被final修饰，类不可继承， 实现了Parcelable 序列化接口，可以在不同进程之间传递，

### Handler

在想熟悉一个类之前，首先要了解如何创建它的对象，Handler提供了两种方式。

构造函数/createAsync()：

<img src="/Users/edz/Library/Application Support/typora-user-images/image-20210308141149312.png" alt="image-20210308141149312" style="zoom:50%;" />

无论用哪种方式，其实就是通过三个参数构建不同的对象，

- Callback：当实例化Handler时，可以使用回调接口，以避免必须实现自己的Handler子类。主要提供了一种数据回掉的方法，不需要继承也可以通过接口中的 handleMessage()拿到Msg数据
- Looper：主要作用就是为Handler绑定线程，Handler在哪里创建，就获得哪个线程的Looper。主线程创建的Handler，即默认使用主线程的Looper。
- Async：设置消息是否是异步的，一般不设置，默认是false，如果设置成异步，可能会对消息顺序造成影响。



了解完如何创建对象后，要了解常用的两种发送消息的方式：

1、send**()

<img src="/Users/edz/Library/Application Support/typora-user-images/image-20210308114600711.png" alt="image-20210308114600711" style="zoom:50%;" />



2.post**()

<img src="/Users/edz/Library/Application Support/typora-user-images/image-20210308143537247.png" alt="image-20210308143537247" style="zoom:50%;" />



表面上send/post()方法都很多，但如果仔细看过每一个方法后，你会发现，最终核心都会走到一个方法里面，这么多重载的方法主要起两个作用，1.是为消息配置uptimeMillis ，控制每个msg入队列的顺序，2.是构造不同的Msg实例，post方法的Msg 都不包含消息obj，send方法可以自定义Msg的信息

通过target为msg绑定Handler，直接说明一个线程可以有多个Handler

```java
private boolean enqueueMessage(@NonNull MessageQueue queue, @NonNull Message msg,
        long uptimeMillis) {
    msg.target = this;
    msg.workSourceUid = ThreadLocalWorkSource.getUid();

    if (mAsynchronous) {
        msg.setAsynchronous(true);
    }
    return queue.enqueueMessage(msg, uptimeMillis);
}
```

通过Handler.enqueueMessage()方法，我们发现，Handler 主要对外提供构建不同Msg的方法,然后把他们加入到Msg队列中，接下来问题来了，那消息队列是在哪里传入的呢？

回顾一下上面Handler创建时候发的构造方法， 我们会发现队列其实是通过Looper对象获取的，接下来消息已经传递进去了，我们一起来看下MsgQueue对象是如何管理消息的吧



### MessageQueue

构造函数

```java
MessageQueue(boolean quitAllowed) {
    mQuitAllowed = quitAllowed;
    mPtr = nativeInit();
}
```

乍一看朴实无华，就一个构造，一个参数，相比Handler可太清晰了，参数也很简单，是否允许消息队列退出，由Looper创建时提供。

但是这里面竟然有很多native层的接口，暂时先不管c层的调用，因为我也还不会。。。。

通过Handler源码，我们发现最终会调用enqueueMessage方法，那我们就看下这里面的逻辑,主要就是通过when对消息队列进行排序从过next 对下一个节点进行关联

```java
boolean enqueueMessage(Message msg, long when) {
    ...
    synchronized (this) {   //同步，避免多线程操作
        ...
        msg.when = when;// 绑定消息的time
        Message p = mMessages;
        boolean needWake;
        if (p == null || when == 0 || when < p.when) { //排序，把不为null 并且时间小 的msg 放在前面
            // New head, wake up the event queue if blocked.
            msg.next = p;
            mMessages = msg;
            needWake = mBlocked;
        } else {
          	//消息插到队列中间
            needWake = mBlocked && p.target == null && msg.isAsynchronous();
            Message prev;
            //遍历整个队列，当队列中的某个消息的执行时间比当前消息晚时，将消息插到这个消息的前面。
            for (;;) {
                prev = p;
                p = p.next;
                if (p == null || when < p.when) {
                    break;
                }
                if (needWake && p.isAsynchronous()) {
                    needWake = false;
                }
            }
            msg.next = p; // invariant: p == prev.next
            prev.next = msg;
        }
    }
    return true;
}
```

到目前为止，其实Handler从新建消息，到消息进入队列的排序，基本就已经清楚了，接下来就是通过Looper进行消息的获取和分发



### Looper

构造函数

```java
private Looper(boolean quitAllowed) {
        mQueue = new MessageQueue(quitAllowed);	//绑定消息队列
        mThread = Thread.currentThread();				//获取对应线程信息
}
```

找了一圈发现，你会发现，Looper只有一个私有的构造，那这岂不是不允许外部调用？其实不是的，在Looper中搜索一下new Looper(),你会发现，有一个静态方法，已经帮你做好了这件事，并且很重要的一点是，一个线程只能有一个Looper，如果在一个线程perpare两次，会抛出异常。

```java
private static void prepare(boolean quitAllowed) {
    if (sThreadLocal.get() != null) {
        throw new RuntimeException("Only one Looper may be created per thread");
    }
    sThreadLocal.set(new Looper(quitAllowed));
}
```



通过上面的代码我们会发现，上面出现了一个ThreadLocal的静态实例，那么这个类的作用是什么呢？做个不恰当的比喻，从表面上看ThreadLocal相当于维护了一个map，key就是当前的线程，value就是需要存储的对象。其实这里的比喻不是很恰当，具体可以看下这篇博客[ThreadLocal](https://www.jianshu.com/p/3c5d7f09dfbd)，主要区分各个线程对应不同的Looper。



loop() 循环获取消息并分发

```java
public static void loop() {

    final Looper me = myLooper();
 
    if (me == null) {
        throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
    }
   
    final MessageQueue queue = me.mQueue;
    //...
    for (;;) {
        Message msg = queue.next(); // 获取消息
        if (msg == null) {
            return;
        }

        try {
            msg.target.dispatchMessage(msg); //获取消息对应的Handler 进行消息分发
            //...
        } catch (Exception exception) {
            //...
            throw exception;
        } finally {
            //...
        }
        //...
        msg.recycleUnchecked();
    }
}
```

看到这里 你会发现，获取消息的方法在queue



常见问题：

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

3.什么是HandlerTheard？

> 简单来说就是一个自带Looper的子线程，不需要自己管理Looper的生命周期

4.sendMsg 和sendMsgDelayed事件入消息队列，和收到的顺序

sendMsg 也会调用 sendDelayed()，默认时间为0，->   控制顺序sendMessageAtTime(msg，SystemClock.uptimeMillis(**开机到当前的时间总数**) + delayMillis )  



参考资料：

- https://blog.csdn.net/u013700040/article/details/105655154
- https://blog.csdn.net/ly502541243/article/details/52414637
- https://www.jianshu.com/p/3c5d7f09dfbd

