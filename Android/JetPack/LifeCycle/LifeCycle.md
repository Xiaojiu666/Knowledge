---
title: LifeCycle
date: 2022-05-01 16:05:41
categories:
           - JetPack
tags:
           - JetPack学习
---

### 什么是LifeCycle
###### 官方介绍
生命周期感知型组件可执行操作来响应另一个组件（如 Activity 和 Fragment）的生命周期状态的变化。这些组件有助于您编写出更有条理且往往更精简的代码，此类代码更易于维护。

一种常见的模式是在 activity 和 fragment 的生命周期方法中实现依赖组件的操作。但是，这种模式会导致代码条理性很差而且会扩散错误。通过使用生命周期感知型组件，您可以将依赖组件的代码从生命周期方法移入组件本身中。

androidx.lifecycle 软件包提供了可用于构建生命周期感知型组件的类和接口 - 这些组件可以根据 activity 或 fragment 的当前生命周期状态自动调整其行为。

###### 作用
 Lifecycle 是一个类，用于存储有关组件（如 activity 或 fragment）的生命周期状态的信息，并允许其他对象观察此状态。

 其最终目的就是将四大组件得状态，转发给不同得`LifecycleObserver`实现类，通过注解   `@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)`


 ### 基础使用
 在日常使用自定义View的时候，都会引用外部Activity得上下文对象，如果在View中，通过context 进行一些耗时操作，那么页面销毁的时候，也应该中断操作销毁对象，否则继续持用activity的引用造成内存泄露。
 ###### LifecycleObserver(自定义)
 ```Java
 class MyChronometer : Chronometer, LifecycleObserver {
     constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
     var elapsedTime: Long = 0
     //计时开始 ,方法添加了注解
     @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
     open fun startMeter() {
         base = SystemClock.elapsedRealtime() - elapsedTime
         start()
     }
     //计时暂停 ,方法添加了注解
     @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
     open fun stopMeter() {
         elapsedTime = SystemClock.elapsedRealtime() - base
         stop()
     }
 }
 ```

 ###### 调用
 ```Java
 class XXXX : AppCompatActivity() {
     private var chronometer: MyChronometer? = null
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         //...
         //组件
         chronometer = findViewById(R.id.chronometer)
         //添加观察者
         lifecycle.addObserver(chronometer!!)
     }
 }
 ```
这时候，当Activity `ON_RESUME` 方法被触发的时候，就会调用addObserver所观察得接口中， 被`@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)`注解修饰的方法，从而同步生命周期。

###### 注意
- 1.一个Activity 可以注册多个`LifecycleObserver`接口

### 进阶使用

###### DefaultLifecycleObserver
```Java
  //在Java8以后，interface接口类型，可以有自己的默认实现。
  implementation "androidx.lifecycle:lifecycle-common-java8:2.2.0"
```
默认实现了`LifecycleObserver`接口，里面已经帮我们处理了 七种生命周期。

######  LifecycleOwner(自定义宿主-基本为四大组件)
支持库 26.1.0 及更高版本中的 Fragment 和 Activity 已实现 LifecycleOwner 接口。
如果您有一个自定义类并希望使其成为 LifecycleOwner，您可以使用 LifecycleRegistry 类，但需要将事件转发到该类，如以下代码示例中所示：
```java
class MyActivity : Activity(), LifecycleOwner {

    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    public override fun onStart() {
        super.onStart()
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}
```

### 问题
###### 即使在OnResume 中进行监听，仍然会处罚OnCreate方法,为什么
我们要先搞懂什么是状态State和事件Event。
```Java
// Event事件对应的就是Activty的onCreate,onStart()事件
// State 而状态却只有5种。以STARTED状态为例，这个状态发生在Activity#onStart()之后，Activity#onPause()之前。
public enum Event {
        ON_CREATE,
        ON_START,
        ON_RESUME,
        ON_PAUSE,
        ON_STOP,
        ON_DESTROY,
        ON_ANY
    }
public enum State {
        DESTROYED,
        INITIALIZED,
        CREATED,
        /**
         *     <li>after {@link android.app.Activity#onStart() onStart} call;
         *     <li><b>right before</b> {@link android.app..Activity#onPause( onPause} call.
         * </ul>
         */
        STARTED,
        RESUMED;
        public boolean isAtLeast(@NonNull State state) {
            return compareTo(state) >= 0;
        }
    }

```
在`LifecycleRegistry`源码中，并不是依靠Event得值进行直接回调，而是会将Event结合State 进行二次处理，保证后面注册，也能收到之前得状态。


### 参考资料
- [源码详解](https://blog.csdn.net/c10WTiybQ1Ye3/article/details/125307587)
