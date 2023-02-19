---
title: LiveData
date: 2023-1-2 21:53:19
type: Jetpack
---
###	什么是LiveData
###### 官方介绍
[`LiveData`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData) 是一种可观察的数据存储器类。与常规的可观察类不同，LiveData 具有生命周期感知能力，意指它遵循其他应用组件（如 Activity、Fragment 或 Service）的生命周期。这种感知能力可确保 LiveData 仅更新处于活跃生命周期状态的应用组件观察者。

如果观察者（由 Observer 类表示）的生命周期处于` STARTED` 或 `RESUMED `状态，则 LiveData 会认为该观察者处于活跃状态。LiveData 只会将更新通知给活跃的观察者。为观察 LiveData 对象而注册的非活跃观察者不会收到更改通知。

Livedata 遵循观察者模式，并且 Livedata 会在生命周期变化的时候通知观察者。
它优雅的处理了生命周期问题，并不会所有的数据变化都会回调，所以你可以在他回调时大胆的做更新 UI操作。

观察者都是绑定Lifecycle(观察的生命周期)的， Lifecycle destory 的话，会销毁自己。


### 基础使用
使用 LiveData的步骤：
1. 创建LiveData的实例，以存储某种数据类型(泛型，包括集合)，大多数都配合ViewModel使用
2. 通过observe()方法，绑定Observe对象，该对象会在被LiveData所修饰的数据改变时触发，将修改后的数据回调回来。

###### Java创建LiveData
```Java
// 创建一个MutableLiveData对象，这个使用LiveData的子类MutableLiveData
// MutableLiveData暴露了postValue和setValue方法用于通知数据变化
MutableLiveData<Object> liveData = new MutableLiveData<>();

// 在UI线程中调用该方法通知数据变更
liveData.setValue(object);
// 在子线程中调用该方法通知数据变更，该方法中切换到UI线程后调用setValue方法
liveData.postValue(object);

// 监听数据变化，进行界面更新等操作，该方法一般放在Activity onCreate方法中调用，只注册一次
//  通常，LiveData 仅在数据发生更改时才发送更新，并且仅发送给活跃观察者。此行为的一种例外情况是，观察者从非活跃状态更改为活跃状态时也会收到更新。此外，如果观察者第二次从非活跃状态更改为活跃状态，则只有在自上次变为活跃状态以来值发生了更改时，它才会收到更新。
liveData.observe(this, new Observer<Object>() {
    @Override
    public void onChanged(Object o) {
     // TODO 此处进行o对象的数据与界面进行绑定刷新
    }
```

###### Kotlin创建LiveData
由于Kotlin语法问题，除了常规的直接创建，还提供了不少边界函数 用户创建LiveData对象
- MutableLiveData() 对象

```java
    // 通过liveData()方法创建 ，内部创建一个携程空间，并返回一个LiveData对象
    val currentTime: MutableLiveData<String> by lazy {
          MutableLiveData<String>()
      }
    //监听同Java
    currentTime.currentTimeTransformed.observe(this) {
         // TODO
    }

```

- liveData()

```java
    // 通过liveData()方法创建 ，内部创建一个携程空间，并返回一个LiveData对象
    var currentTime: LiveData<Long> = liveData {
            // 携程空间
           while (true) {
               emit(System.currentTimeMillis())
               delay(1000)
           }
    }
    //监听同Java
    currentTime.currentTimeTransformed.observe(this) {
         // TODO
    }
```

### 进阶使用

###### 自定义LiveData
假设我们有一个系统时间显示的需求，在页面启动时显示，在销毁时断开
```JAVA
class TimeLiveData : LiveData<Long>() {
    //获取系统时间管理类
    private val systemTimeManager = SystemTimeManager()

    //获取系统时间回调接口
    private val listener = SystemTimeListener {
        value = it
    }
    //当 LiveData 对象具有活跃观察者时，会调用 onActive() 方法。这意味着，从此刻，开始获取当前系统时间
    override fun onActive() {
        systemTimeManager.requestCurrentTime(listener)
    }
    //当 LiveData 对象没有任何活跃观察者时，会调用 onInactive() 方法。由于没有观察者在监听，因此没有理由与 StockManager 服务保持连接。
    override fun onInactive() {
        systemTimeManager.removeUpdates()
    }

    homeViewModel.currentTimeTransformed.observe(viewLifecycleOwner){
      textView.text = it.toString()
  }
}
```
###### 转换 LiveData
平常开发过程中，我们会需要将某些数据进行转化，例如:时间戳转化成字符串时间，我们可以通过`Transformations` 将一个LiveData对象转换成另一种LiveData对象
- Transformations.map()  
对存储在 LiveData 对象中的值应用函数，并将结果传播到下游。

```JAVA
  //原数据
  // Long
  val systemTime : MutableLiveData<Long>  = TimeLiveData()
  // String
  val viewTime = Transformations.map(systemTime) {
    it.toString()
}
  ```

- Transformations.switchMap()  
与 map() 类似，对存储在 LiveData 对象中的值应用函数，并将结果解封和分派到下游。传递给 switchMap() 的函数必须返回 LiveData 对象.

```JAVA
  // 根据系统时间获取当前月份
  private fun getYear(time: Long): LiveData<String> {
  ...
  }
  // Long
  val systemTime : MutableLiveData<Long>  = TimeLiveData()
  // 可以继续被观察，当月份变化时，可以做一些其他的UI处理
  val year = Transformations.switchMap(systemTime) { time -> getYear(time) }
```

- Transformations.switchMap() 实战  
假设有个页面需要根据当前时间往前，获取任务列表，并更新UI

```JAVA
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private fun getTaskList4Time(time: Long): LiveData<List<Task>> {
        return repository.getTaskList(time)
    }
}
```  
问题:由于网络延迟，从当前页面返回，其他页面，VM的持久引用，`LiveData`没有设置宿主，无法跟随宿主的生命周期进行中断。再次进入时，无法拿到新的`LiveData`实例，并且如果需要在页面初始化时，就去调用一次接口，上面也无法实现。所以使用将数据通过switchMap转换`LiveData`对象。

```JAVA
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
  private val time = MutableLiveData<Long>()
     val taskList: LiveData<String> = Transformations.switchMap(addressInput) {
             time -> repository.getTaskList(time) }

     private fun setInputTime(systemTime: Long) {
         time.value = systemTime
     }
}
```  

### 配合Room

### 配合携程





###	参考资料
- [map()和switchMap](https://blog.csdn.net/a1203991686/article/details/106952398)
- [Transformations的switchMap该怎么理解好](https://blog.csdn.net/newmandirl/article/details/100022021)
- [MediatorLiveData](https://medium.com/androiddevelopers/livedata-beyond-the-viewmodel-reactive-patterns-using-transformations-and-mediatorlivedata-fda520ba00b7)
- [深入了解架构组件之ViewModel](https://www.jianshu.com/p/35d143e84d42)
- [set/post丢值](https://blog.csdn.net/csj731742019/article/details/126375610)
