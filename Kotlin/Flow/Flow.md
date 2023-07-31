### Flow
来自 kotlinx.coroutines.flow 包下

###### 官方介绍
一种异步数据流，它顺序地发出值并正常或异常地完成。   
流上的中间运算符（例如map、filter、take、zip等）是应用于上游流或流并返回下游流的函数，可以在其中应用更多运算符。中间操作不执行流中的任何代码，也不暂停函数本身。他们只是为未来的执行和快速返回建立了一个操作链。这被称为冷流特性。

###### 个人理解
功能类似于Rxjava 可以异步操作数据流，更灵活，保证数据正确的流向，提供多操作符在数据发送的过程中，对数据进行转换，扩展方法多，易用性高。
- 发送方会生成添加到数据流中的数据。得益于协程，数据流还可以异步生成数据`flow`
- 中介可以修改发送到数据流的值，或修正数据流本身`zip,map,filter`
- 接收方则使用数据流中的值`collect`。



###### 冷流 :
- 当数据被订阅的时候，发布者才开始执行发射数据流的代码。
- 一对一，每一个订阅者何发布者都是一对一的关系，每个订阅者都会收到发布者完整的数据。
```Java
private var cloudFlow: Flow<String> = flow {
    println("cloudFlow emit data cloudFlow start")
    emit("1")
    emit("2")
    emit("3")
    println("cloudFlow emit data cloudFlow end")
}

  viewModelScope.launch {
      cloudFlow.collect {
          println("cloudFlow One   collect data $it")
      }
  }

  viewModelScope.launch {
      cloudFlow.collect {
          println("cloudFlow Two   collect data $it")
      }
  }

    // cloudFlow emit data cloudFlow start
    // cloudFlow One   collect data 1
    // cloudFlow One   collect data 2
    // cloudFlow One   collect data 3
    // cloudFlow emit data cloudFlow end
    // cloudFlow emit data cloudFlow start
    // cloudFlow Two   collect data 1
    // cloudFlow Two   collect data 2
    // cloudFlow Two   collect data 3
    // cloudFlow emit data cloudFlow end
```
根据日志可以看出，冷流每次collect 收集时，都会调用发布者`flow{}`代码块中的代码，并且每个订阅者都会收到发布者完整的数据。


###### 热流
- 只要该数据流被收集，或对它的任何其他引用在垃圾回收根中存在，该数据流就会一直存于内存中。
- 当热流有多个订阅者时，发布者跟订阅者是一对多的关系，收集者可以与多个订阅者共享信息。

  ```Java
  private var cloudFlow: Flow<String> = flow {
        println("cloudFlow emit data cloudFlow start")
        emit("1")
        emit("2")
        emit("3")
        println("cloudFlow emit data cloudFlow end")
    }
  //冷流 转换成ShareFlow热流
  var shareFlow = cloudFlow.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 3)

  viewModelScope.launch {
              shareFlow.collect {
                  println("shareFlow One   collect data $it")
              }
          }

          viewModelScope.launch {
              shareFlow.collect {
                  println("shareFlow Two   collect data $it")
              }
          }
       // cloudFlow emit data cloudFlow start
       // cloudFlow emit data cloudFlow end
       // shareFlow One   collect data 1
       // shareFlow One   collect data 2
       // shareFlow One   collect data 3
       // shareFlow Two   collect data 1
       // shareFlow Two   collect data 2
       // shareFlow Two   collect data 3
```
根据日志可以看出，热流在收集时，第一次去调用发布者`flow{}`拿到数据，后面并不会再去flow中取，而是从内存中提取数据  

###### 流的取消
流采用与协程同样的协作取消。像往常一样，流的收集可以在当流在一个可取消的挂起函数（例如 delay）中挂起的时候取消。

```Java
//sampleStart           
fun simple(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)          
        println("Emitting $i")
        emit(i)
    }
}

fun main() = runBlocking<Unit> {
    withTimeoutOrNull(250) { // 在 250 毫秒后超时
        simple().collect { value -> println(value) }
    }
    println("Done")
}
// Emitting 1
// 1
// Emitting 2
// 2
// Done
```


### 基础使用

###### 发送方
最基础的方法可以通过 flow { ... } 去创建一个冷流。
- `flowOf` 就是flow{}的 一个包装，里面调用了flow{}。
- 使用 `.asFlow()` 扩展函数，里面调用了flow{}进行转换。

###### 中间方
过度流操作符，例如 map 与 filter
```Java
(1..3).asFlow().map {
      it * 100
  }.collect {
      println("map result $it")
  }
  // map result 100
  // map result 200
  // map result 300
```

###### 接收方
末端操作符是在流上用于启动流收集的挂起函数。 collect 是最基础的末端操作符，但是还有另外一些更方便使用的末端操作符：
- 转化为各种集合，例如 toList 与 toSet。
```Java
val list = (1..3).asFlow().map {
      it * 100
  }.toList()
  println("list result $list")
  // list result [100, 200, 300]
```
- 获取第一个（first）值与确保流发射单个（single）值的操作符。

- 使用 reduce() 与 fold() 将流规约到单个值。


###### SharedFlow
shareIn 函数会返回一个热数据流 SharedFlow，此数据流会向从其中收集值的所有使用方发出数据。
- shareIn(scope,started,replay):   
     将冷流转换为在给定协程范围内启动的热SharedFlow，与多个下游订阅者共享来自上游流的数据，并向新订阅者重播指定数量的重播值。
  -  scope:    
    只要 scope 处于活跃状态并且存在活跃收集器，它就会一直处于活跃状态  
  -  started :    
  在shareIn和stateIn操作符中启动和停止共享协程的策略，例如:WhileSubscribed(),共享在第一个订阅者出现时启动，在最后一个订阅者消失时立即停止，并永久保留replay缓存。
  -  replay: Int = 0  
  重播值，用来指定缓存的个数。
-

#### StateFlow
StateFlow 是一个状态容器可观察数据流，可向其收集器发出当前状态更新和新状态更新。也可以通过其 value 属性读取当前状态值。如需更新状态并将其发送到数据流，请为 MutableStateFlow 类的 value 属性分配一个新值。
StateFlow 和 SharedFlow ，前者 只会存储最新的一条数据值。  
StateFlow 和 LiveData 具有相似之处。两者都是可观察的数据容器类，并且在应用架构中使用时，两者都遵循相似模式。
区别
- StateFlow 需要将初始状态传递给构造函数，而 LiveData 不需要。
- 当 View 进入 STOPPED 状态时，LiveData.observe() 会自动取消注册使用方，而从 StateFlow 或任何其他数据流收集数据的操作并不会自动停止。如需实现相同的行为，您需要从 Lifecycle.repeatOnLifecycle 块收集数据流。


### 创建流
创建流程有以下几种基本方法：  
- flowOf(...)函数从一组固定的值创建一个流。
- 通过扩展方法，各种类型的asFlow()将它们转换为流。
- flow { ... }构建器函数，用于从顺序调用到emit函数构造任意流。
- channelFlow { ... }构建器函数，用于从对发送函数的潜在并发调用构造任意流。
- MutableStateFlow和MutableSharedFlow定义了相应的构造函数来创建一个可以直接更新的热流。


##### 创建方
```Java
private fun timeFlow(): Flow<LocalTime> = flow {
     while (true) {
         emit(LocalTime.now()) // Emits the result of the request to the flow
         delay(1) // Suspends the coroutine for some time
     }
 }
```


##### 修改数据流
```Java
  val transformData =
            timeFlow()
                .map { "${it.hour} : ${it.minute} : ${it.second}"  }
                .onEach {
                    saveInt2Local()
                }
```


##### 从数据流中进行收集
```Java
GlobalScope.launch(Dispatchers.Main) {
         transformData
             .catch { exception -> LogUtils.d(exception) }
             .collect {
                 textView.text = it
             }
     }
```






### 参考资料
- [Flow官方文档](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)
- [Android官方指南](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow?hl=zh-cn#livedata)
