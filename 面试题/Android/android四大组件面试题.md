### Activity

#### activity 生命周期

- 1. onCreat()
- 2. onStart()
- 3. onResume()
- 4. onPaue()
- 5. onStop()
- 6. onResttart()
- 7. onDestory()返回键点击时触发

##### A-->B 生命周期

1.Activity1 启动：
Activity1: onCreate()
Activity1: onStart()
Activity1: onResume()

2.点击按钮跳转到 Activity2:
Activity1: onPause()
Activity2: onCreate()
Activity2: onStart()
Activity2: onResume()
Activity1: onStop()

3.从 Activity2 中返回：
Activity2: onPause()
Activity1: onRestart()
Activity1: onStart()
Activity1: onResume()
Activity2: onStop()
Activity2: onDestroy()

4.Activity1 退出
Activity1: onPause()
Activity1: onStop()
Activity1: onDestroy()

#### 切换横竖屏时Activity的生命周期?
竖屏：onCreat->onStart->onResume.
切换横屏时：onPause-> onSaveInstanceState ->onStop->onDestory

onCreat->onStart->onSaveInstanceState->onResume.

但是，我们在如果配置这个属性:android:configChanges="orientation|keyboardHidden|screenSize"
就不会在调用Activity的生命周期，只会调用onConfigurationChanged方法


#### 启动模式

-   standard（标准模式）：
每次启动 Activity 都会创建一个新的实例，并放置在调用它的任务栈的顶部。
-   singleTop（单顶部模式）：
如果新启动的 Activity 已经位于任务栈的顶部，那么不会创建新的实例，而是调用已存在的实例的 onNewIntent 方法；如果不在顶部，则会创建新的实例。
-   singleTask（单任务模式）：
在同一个任务栈中只能存在一个该 Activity 的实例。如果已经存在实例，则会将任务栈上该实例以上的所有 Activity 移除，使其处于栈顶，并调用其 onNewIntent 方法。如果不存在实例，则会创建一个新的实例。
-   singleInstance（单实例模式）：

该模式下的 Activity 是独立于其他任务栈的，会单独位于一个新的任务栈中，并且系统不会在该任务栈中放置任何其他 Activity。如果已经存在实例，则会调用其 onNewIntent 方法，而不会创建新的实例。
#### 数据保存和恢复

    开发者提前可以复写onSaveInstanceState方法，创建一个Bundle类型的参数，
    把数据存储在这个Bundle对象中，这样即使Activity意外退出
    ，Activity被系统摧毁，当重新启动这个Activity而调用onCreate方法时，上述Bundle对象会作为参数传递给onCreate方法，开发者可以从Bundle对象中取出保存的数据，利用这些数据将Activity恢复到被摧毁之前的状态。

#### 为什么 Activity 在屏幕旋转重建后可以恢复 ViewModel？

ViewModel 底层是基于原生 Activity 因设备配置变更重建时恢复数据的机制实现的，这个其实跟 Fragment#setRetainInstance(true) 持久 Fragment 的机制是相同的。当 Activity 因配置变更而重建时，我们可以将页面上的数据或状态可以定义为 2 类：

第 1 类 - 配置数据： 例如窗口大小、多语言字符、多主题资源等，当设备配置变更时，需要根据最新的配置重新读取新的数据，因此这部分数据在配置变更后便失去意义，自然也就没有存在的价值；
第 2 类 - 非配置数据： 例如用户信息、视频播放信息、异步任务等非配置相关数据，这些数据跟设备配置没有一点关系，如果在重建 Activity 的过程中丢失，不仅没有必要，而且会损失用户体验（无法快速恢复页面数据，或者丢失页面进度）。
基于以上考虑，Activity 是支持在设备配置变更重建时恢复 第 2 类 - 非配置数据 的，源码中存在 NonConfiguration 字眼的代码，就是与这个机制相关的代码。我将整个过程大概可以概括为 3 个阶段：

阶段 1： 系统在处理 Activity 因配置变更而重建时，会先调用 retainNonConfigurationInstances 获取旧 Activity 中的数据，其中包含 ViewModelStore 实例，而这一份数据会临时存储在当前 Activity 的 ActivityClientRecord（属于当前进程，下文说明）；
阶段 2： 在新 Activity 重建后，系统通过在 Activity#onAttach(…) 中将这一份数据传递到新的 Activity 中；
阶段 3： Activity 在构造 ViewModelStore 时，会优先从旧 Activity 传递过来的这份数据中获取，为空才会创建新的 ViewModelStore。
对于 ViewModel 来说，相当于旧 Activity 中所有的 ViewModel 映射表被透明地传递到重建后新的 Activity 中，这就实现了恢复 ViewModel 的功能。总结一下重建前后的实例变化，帮助你理解:

Activity： 构造新的实例；
ViewModelStore： 保留旧的实例；
ViewModel： 保留旧的实例（因为 ViewModel 存储在 ViewModelStore 映射表中）；
LiveData： 保留旧的实例（因为 LiveData 是 ViewModel 的成员变量）；

####  ViewModel 的数据在什么时候才会清除
ViewModel 的数据会在 Activity 非配置变更触发的销毁时清除，具体分为 3 种情况：

第 1 种： 直接调用 Activity#finish() 或返回键等间接方式；
第 2 种： 异常退出 Activity，例如内存不足；
第 3 种： 强制退出应用

#### ViewModel 和 onSaveInstanceState() 的对比
-   1、ViewModel： 使用场景针对于配置变更重建中非配置数据的恢复，由于内存是可以满足这种存储需求的，因此可以选择内存存储。又由于内存空间相对较大，因此可以存储大数据，但会受到内存空间限制；
-   2、onSaveInstanceState() ：使用场景针对于应用被系统回收后重建时对数据的恢复，由于应用进程在这个过程中会消亡，因此不能选择内存存储而只能选择使用持久化存储。又由于这部分数据需要通过 Bundle 机制在应用进程和 AMS 服务之间传递，因此会受到 Binder 事务缓冲区大小限制，只可以存储小规模数据。

![Alt text](image.png)
### Service
#### 启动方式
区别：对应着动态绑定和静态绑定； 静态对应着startService，动态对应着bindService，静态有自己独立的生命周期，动态会依附activity等组件的生命周期。

生命周期： onCreate → startCommand → onDestroy
onCreate → onBind→onUnBind→ onDestroy

使用场景：即上述区别，如果需要一直存在的服务，即静态绑定，反之依赖于组件

#### Service如何进行保活
利用系统广播拉活
利用系统service拉活
利用Native进程拉活<Android5.0以后失效> fork进行监控主进程，利用native拉活
利用JobScheduler机制拉活<Android5.0以后>

### ContentProvider
