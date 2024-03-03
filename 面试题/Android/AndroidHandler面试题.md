#### Handler的实现原理
Handler是Android中用于在不同线程之间进行通信的重要工具。它的实现原理涉及到Looper、MessageQueue和Message等组件。
1. **Looper**：Looper是一个线程本地的消息循环对象，负责不断地从MessageQueue中取出消息并进行分发。**每个线程只能有一个Looper对象**，它通常在线程的入口处创建，并且在消息处理结束时退出消息循环。
2. **MessageQueue**：MessageQueue是一个存储消息的队列，每个Looper都会持有一个MessageQueue对象。当一个线程通过Handler发送消息时，消息会被加入到该线程对应的MessageQueue中。
3. **Message**：Message是消息的载体，它包含了消息的标识符、目标Handler、消息内容等信息。当一个线程通过Handler发送消息时，实际上是创建了一个Message对象并将其加入到目标线程的MessageQueue中。


#### 子线程中能不能直接new一个Handler,为什么主线程可以主线程的Looper第一次调用loop方法,什么时候,哪个类
1. 子线程中不能直接创建Handler
必须通过主线程的Looper创建Handler或者在子线程中先创建Looper。

2. 主线程可以直接创建Handler
是因为App启动时,ActivityThread首先会创建主线程的Looper,然后Activity的onCreate中创建的Handler就可以依赖这个Looper来工作。

3. 主线程的Looper第一次被调用是在ActivityThread类的main方法中:
```java
public static void main(String[] args) {
  Looper.prepareMainLooper();
  
  // ...

  Looper.loop();
}
```

在这里第一次调用了Looper.prepareMainLooper()来初始化主线程的Looper,然后通过Looper.loop()轮询启动消息循环。

所以主线程可以直接创建Handler,是因为Android在应用启动时就已经初始化好了主线程的Looper。子线程想创建Handler就必须自己初始化Looper。
#### 一个线程可以有几个Handler,几个Looper,几个MessageQueue对象
一个线程中可以有多个Handler对象,但只能有一个Looper和一个MessageQueue

#### Message对象创建的方式有哪些 & 区别？
Message对象的常见创建方式有以下几种:

1. 通过Message.obtain()获得
```java
Message msg = Message.obtain();
这是最基本的获取Message实例的方式,会从全局的消息池中重复利用Message对象。
```

2. 自己通过构造函数创建
```java
Message msg = new Message();
这种方式会创建新的Message实例,如果频繁创建会造成内存占用过多。
```
3. 通过Handler.obtainMessage()获得
```java
Message msg = handler.obtainMessage(); 
这种方式也是从消息池重复利用Message实例,但带有Handler的信息,会直接关联到这个Handler。
```java


Message msg = handler.obtainMessage();
Bundle bundle = new Bundle();
bundle.putString("name", "abc"); 
msg.setData(bundle);
```
通过Bundle可以在Message中携带更多数据。

区别:

- obtain()方式可以重用消息实例,而直接new会创建新的实例。
- Handler.obtainMessage()会关联Handler信息。
- 通过Bundle可以传递更多的数据。

所以,获得Message最好通过obtain()或Handler.obtainMessage(),如果需要携带更多数据可以使用Bundle。

#### Handler的post与sendMessage的区别和应用场景
- **post方法**通常用于在UI线程中执行一些操作，比如更新UI元素或执行一些简单的任务。
- **sendMessage方法**通常用于在后台线程中发送消息给UI线程，或者在UI线程中发送延迟消息。

#### handler postDealy后消息队列有什么变化，假设先 postDelay 10s, 再postDelay 1s, 怎么处理这2条消息
```JAVA
//通过当前时间计算
sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
```

#### ThreadLocal
1. HandlerThread 是一种含有 Looper 的线程,可以使用 Handler 向其发送 Message。

2. 与普通 Thread 相比,其主要优点是自带 Looper,使用更方便,可以直接通过 Handler 与其进行交互。

3. 原理上,HandlerThread 在 run() 中自动初始化了 Looper,并调用 Looper.loop() 进入消息循环。这样就提供了当前线程的 Looper。

4. 主要使用场景:
   - 需要一个含 MessageQueue 的后台线程来处理时间消耗任务。
   - 需求 Handler 机制,但普通 Thread 无法满足。
   - 处理异步任务或网络操作的后台线程。

5. 典型用法:
```java
HandlerThread handlerThread = new HandlerThread("my_thread"); 
handlerThread.start();
Handler handler = new Handler(handlerThread.getLooper()) {
  @Override
  public void handleMessage(Message msg) {
    // 处理消息
  } 
}
// 通过 handler 发送消息
handler.sendEmptyMessage(1);
```

综上,HandlerThread 通过自带 Looper 的线程,可以方便地使用 Handler 来维护一个后台线程,在 Android 中使用广泛。
IdleHandler及其使用场景
消息屏障,同步屏障机制
#### 子线程能不能更新UI
不能这是因为在Android中,界面视图元素并非线程安全的,只能在创建它们的线程中更新和操作


为什么Android系统不建议子线程访问UI
```
Android中为什么主线程不会因为Looper.loop()里的死循环卡死
在Android中，主线程（也称为UI线程）不会因为Looper.loop()中的死循环而卡死，是因为Looper.loop()方法本身并不是一个死循环。实际上，Looper.loop()方法是一个消息循环，它会不断地从消息队列中取出消息，并将这些消息分发到对应的Handler进行处理。

具体来说，Looper.loop()方法会在每次循环开始时阻塞线程，然后等待消息队列中的消息。当有消息到达时，Looper会将消息分发给对应的Handler，并在Handler的handleMessage()方法中处理消息。处理完消息后，Looper会继续下一轮循环，从消息队列中取出下一个消息。

因此，虽然Looper.loop()方法看起来像是一个死循环，但实际上它是一个阻塞式的循环，会在每次循环开始时等待消息的到来。这样就避免了主线程因为消息循环而被卡死的情况，因为主线程中的其他任务（比如响应用户输入、更新UI等）都是通过消息机制来处理的，而不会因为消息循环的阻塞而被阻塞。
```
#### Handler消息机制中，一个looper是如何区分多个Handler的
每个Message对象中都包含了一个Handler对象的引用（target），这个引用指示了消息的目标Handler。
#### 当Activity有多个Handler的时候，怎么样区分当前消息由哪个Handler处理
1. **使用标识符区分消息**：可以在发送消息时给每个消息设置一个唯一的标识符，然后在Handler的handleMessage()方法中根据标识符来区分不同的消息，并进行相应的处理。

```java
// 发送消息时设置标识符
handler1.sendMessage(Message.obtain(handler1, MSG_ID_1, ...));
handler2.sendMessage(Message.obtain(handler2, MSG_ID_2, ...));

// 在Handler中处理消息时根据标识符区分消息
@Override
public void handleMessage(Message msg) {
    switch (msg.what) {
        case MSG_ID_1:
            // 处理handler1的消息
            break;
        case MSG_ID_2:
            // 处理handler2的消息
            break;
        // 其他case...
    }
}
```

2. **使用不同的Handler类**：可以创建不同的Handler子类来处理不同类型的消息，然后在Activity中创建对应的Handler实例，并分别使用不同的Handler对象发送消息。

```java
class Handler1 extends Handler {
    @Override
    public void handleMessage(Message msg) {
        // 处理handler1的消息
    }
}

class Handler2 extends Handler {
    @Override
    public void handleMessage(Message msg) {
        // 处理handler2的消息
    }
}

// 在Activity中创建不同的Handler实例并发送消息
Handler1 handler1 = new Handler1();
Handler2 handler2 = new Handler2();
handler1.sendMessage(Message.obtain(handler1, ...));
handler2.sendMessage(Message.obtain(handler2, ...));
```

3. **使用Handler的getLooper()方法**：可以通过Handler的getLooper()方法获取Looper对象，然后根据Looper对象来判断当前消息应该由哪个Handler处理。这种方法通常在自定义Looper和Handler时使用较多。

```java
Looper looper1 = handler1.getLooper();
Looper looper2 = handler2.getLooper();
```

这些方法都可以根据不同的条件来区分当前消息应该由哪个Handler处理，具体选择哪种方法取决于实际情况和需求。


处理message的时候怎么知道是去哪个callback处理的
Looper.quit/quitSafely的区别
通过Handler如何实现线程的切换
Handler 如何与 Looper 关联的
Looper 如何与 Thread 关联的
Looper.loop()源码
MessageQueue的enqueueMessage()方法如何进行线程同步的
#### MessageQueue的next()方法内部原理
#### 子线程中是否可以用MainLooper去创建Handler，Looper和Handler是否一定处于一个线程
可以，