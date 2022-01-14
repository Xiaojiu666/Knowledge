###	什么是LiveData
###### 官方介绍
[`LiveData`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData) 是一种可观察的数据存储器类。与常规的可观察类不同，LiveData 具有生命周期感知能力，意指它遵循其他应用组件（如 Activity、Fragment 或 Service）的生命周期。这种感知能力可确保 LiveData 仅更新处于活跃生命周期状态的应用组件观察者。

如果观察者（由 Observer 类表示）的生命周期处于 STARTED 或 RESUMED 状态，则 LiveData 会认为该观察者处于活跃状态。LiveData 只会将更新通知给活跃的观察者。为观察 LiveData 对象而注册的非活跃观察者不会收到更改通知。

Livedata 遵循观察者模式，并且 Livedata 会在生命周期变化的时候通知观察者。
它优雅的处理了生命周期问题，并不会所有的数据变化都会回调，所以你可以在他回调时大胆的做更新 UI操作。

观察者都是绑定Lifecycle(观察的生命周期)的， Lifecycle destory 的话，会销毁自己。


### 基础使用
使用 LiveData的步骤：
1. 创建LiveData的实例，以存储某种数据类型(泛型，包括集合)，大多数都配合ViewModel使用
2. 通过observe()方法，绑定Observe对象，该对象会在被LiveData所修饰的数据改变时触发，将修改后的数据回调回来。

######  创建LiveData 对象
大多数LiveData 都创建在ViewModel里，因为减少UI的代码量，并且使UI和 数据分离
```java
class NameViewModel : ViewModel() {
    // Create a LiveData with a String
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    // 通过liveData 方法创建 ， 可以发送一些延迟数据 或者 数据源 可见LiveDataScope接口
    var currentTime: LiveData<Long> = liveData {
       while (true) {
           emit(System.currentTimeMillis())
           delay(1000)
       }
   }

}
```
######  观察 LiveData 对象
通常，LiveData 仅在数据发生更改时才发送更新，并且仅发送给活跃观察者。此行为的一种例外情况是，观察者从非活跃状态更改为活跃状态时也会收到更新。此外，如果观察者第二次从非活跃状态更改为活跃状态，则只有在自上次变为活跃状态以来值发生了更改时，它才会收到更新。
```JAVA
class NameActivity : AppCompatActivity() {

    private val model: NameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nameObserver = Observer<String> { newName ->
            nameTextView.text = newName
        }
        model.currentName.observe(this, nameObserver)
    }
}
```



######  更新 LiveData 对象
LiveData 没有公开可用的方法来更新存储的数据。所以我们会使用子类 MutableLiveData 重的 setValue(T) 和 postValue(T) 方法，进行更新LiveData中的数据
```JAVA
button.setOnClickListener {
    val anotherName = "Test"
    model.currentName.setValue(anotherName)
}
```

### 进阶使用

###### 配合Room

###### 配合携程

###### 自定义LiveData
假设我们有一个需要实时同步的数据(例如心电图，股票)，在页面启动时开启，在销毁时断开
```JAVA
class StockLiveData : LiveData<Int>() {

    private val stockManager = StockManager()

    private val listener = SimplePriceListener {
        value = it
    }

    override fun onActive() {
        stockManager.requestPriceUpdates(listener)
    }

    override fun onInactive() {
        stockManager.removeUpdates()
    }
}
```
- 当 LiveData 对象具有活跃观察者时，会调用 onActive() 方法。这意味着，您需要从此方法开始观察股价更新。
- 当 LiveData 对象没有任何活跃观察者时，会调用 onInactive() 方法。由于没有观察者在监听，因此没有理由与 StockManager 服务保持连接。

###### 转换 LiveData
平常开发过程中，我们会需要将某些数据进行转化，例如:时间戳转化成字符串时间，我们可以通过`Transformations` 将一个LiveData对象转换成另一种LiveData对象
- Transformations.map()
  ```JAVA
  //愿数据
  val time : MutableLiveData<Long> by lazy {
      MutableLiveData()
  }
  val time = viewModel.time
        "map test $it "
  }
  ```

- Transformations.switchMap()
  ```JAVA
  val time : MutableLiveData<Long> by lazy {
      MutableLiveData()
  }
  val time = viewModel.time
  val switch = Transformations.switchMap(time) {
      val value = "switch test $it "
      MutableLiveData<String>(value)
  }
  ```
  switchMap 和 map的区别是，switchMap的回调方法返回的是一个LiveData对象，而 map 只需要返回数据类型即可，这也证明

- 区别
所以对于这两个函数的区别来说，map，更关注于数值的转换，他只会通过你之前的值去生成一个新的值，强调的是这个转换，也就是说新的LiveData对象的值，仍然是基于当前这个LiveData的值而生成的。

而switchMap，更关注于数值的触发，也就是说，他会监听你这个值的变化，而不关注你这个值本身，你可以理解成触发器或者扳机，当触发之后，你就需要自己去主动的返回任意一个你指定的liveData对象。







###	参考资料
- [map()和switchMap](https://blog.csdn.net/a1203991686/article/details/106952398)
- [Transformations的switchMap该怎么理解好](https://blog.csdn.net/newmandirl/article/details/100022021)
- [数据库可视化工具SQLScout](https://blog.csdn.net/xhnmbest/article/details/105994122)
- [深入了解架构组件之ViewModel](https://www.jianshu.com/p/35d143e84d42)
