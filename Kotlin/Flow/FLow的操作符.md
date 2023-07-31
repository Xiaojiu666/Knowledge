### 发布者
创建流程有以下几种基本方法：  
- flowOf(...)函数从一组固定的值创建一个流。
- 通过扩展方法，各种类型的asFlow()将它们转换为流。
- flow { ... }构建器函数，用于从顺序调用到emit函数构造任意流。
- channelFlow { ... }构建器函数，用于从对发送函数的潜在并发调用构造任意流。
- MutableStateFlow和MutableSharedFlow定义了相应的构造函数来创建一个可以直接更新的热流。

### 中间方

###### flowOn()
作用:将上游的流切换到指定协程上下文的调度器中执行，同时不会把协程上下文暴露给下游的流，即flowOn方法中协程上下文的调度器不会对下游的流生效。  

在开发过程中,我们会对发送/接收方进行协程上下文的切换。  

错误示例:
```Java
fun simple(): Flow<Int> = flow {
    // 在流构建器中更改消耗 CPU 代码的上下文的错误方式
    withContext(Dispatchers.IO) {
        println("collect thread name ${this.coroutineContext}")
        for (i in 1..3) {
            Thread.sleep(100) // 假装我们以消耗 CPU 的方式进行计算
            emit(i) // 发射下一个值
        }
    }
}

fun main() = runBlocking<Unit> {
    simple().collect { value ->
        log("Collected $value")
    }
}  
// collect thread name [DispatchedCoroutine{Active}@5597512c, Dispatchers.IO]
// Exception in thread "main" java.lang.IllegalStateException: Flow invariant is violated:
// 		Flow was collected in EmptyCoroutineContext,
// 		but emission happened in [DispatchedCoroutine{Active}@5597512c, Dispatchers.IO].
// 		Please refer to 'flow' documentation or use 'flowOn' instead
```
上方代码会在运行的过程中崩溃，原因在冷流状况下，当数据被订阅的时候，发布者才开始执行发射数据流的代码，假设此时，我们在对发送或者接收方进行上下文的切换，会导致双发的协程上下文出现问题。  
所以我们需要使用`flowOn()`操作符
```Java
fun simple(): Flow<Int> = flow {
        println("collect thread name ${currentCoroutineContext()}")
        for (i in 1..3) {
            Thread.sleep(100) // 假装我们以消耗 CPU 的方式进行计算
            emit(i) // 发射下一个值
        }
}.flowOn(Dispatchers.IO)
```
### 背压
###### 什么是背压
简单来说，在一般的流处理框架中，消息的接收处理速度跟不上消息的发送速度，从而导致数据不匹配，造成积压。如果不及时正确处理背压问题，会导致一些严重的问题

- 比如说，消息拥堵了，系统运行不畅从而导致崩溃
- 比如说，资源被消耗殆尽，甚至会发生数据丢失的情况

###### Flow的背压机制
由于Flow是基于协程中使用的，所以我们不需要单独的去处理背压，因为无论是发送还是接受方法，都是支持挂起函数的，因此，Flow<T>在同一个协程中发射和收集时，如果收集器跟不上数据流，它可以简单地暂停元素的发射，直到它准备好接收更多。

###### buffer()缓冲
当处理数据延时过大时,因为挂起函数，会将每一个值，处理完继续下一个。
```Java
fun simple(): Flow<Int> = flow<Int> {
    println("emit start")
    for (i in 1..3) {
        delay(1000)
        emit(i) // 发射下一个值
    }
    println("emit end")
}

suspend fun main() {
    val totalTime = measureTimeMillis {
        simple().collect {
            delay(2000)
            println("collect result $it")
        }
    }
    println("totalTime result $totalTime")
}

// emit start
// time 1687256248248 emit data 1
// collect result 1
// time 1687256251278 emit data 2
// collect result 2
// time 1687256254300 emit data 3
// collect result 3
// emit end
// totalTime result 9108
```
当使用了`buffer()`,可以看得出来，会将flow{}中的数据，发射到到buffer()中，而不是等collect()的时候在发送,这样就让数据的发送从 `同步`，变成了`异步`   

```Java
fun simple(): Flow<Int> = flow<Int> {
    println("emit start")
    for (i in 1..3) {
        delay(100)
        emit(i) // 发射下一个值
    }
    println("emit end")
}.buffer()
// emit start
// time 1687256091078 emit data 1
// time 1687256092099 emit data 2
// collect result 1
// time 1687256093110 emit data 3
// emit end
// collect result 2
// collect result 3
// totalTime result 7171
```
它还为我们提供了缓冲功能，我们可以指定capacity我们的缓冲区和处理策略onBufferOverflow，所以当Buffer溢出的时候，它为我们提供了三个选项
- 默认使用SUSPEND：会将当前协程挂起，直到缓冲区中的数据被消费了
- DROP_OLDEST：它会丢弃最老的数据
- DROP_LATEST: 它会丢弃最新的数据

###### conflate()
conflate操作符于Channel中的Conflate模式是一直的，新数据会直接覆盖掉旧数据，它不设缓冲区，也就是缓冲区大小为 0，丢弃旧数据，也就是采取 DROP_OLDEST 策略

###### zip()
```Java
fun main() = runBlocking<Unit> {
    val nums = (1..3).asFlow() // 数字 1..3
    val strs = flowOf("one", "two", "three") // 字符串
    nums.zip(strs) { a, b -> "$a -> $b" } // 组合单个字符串
        .collect { println(it) } // 收集并打印
}
//1 -> one
// 2 -> two
// 3 -> three
````


### 末端操作符
###### reduce()
```java
// 从第一个元素开始累加值，并对当前累加器值和每个元素应用操作
val list = (1..5).asFlow().map {
       it * 100
   }.reduce { accumulator, value ->
     // accumulator 计算后的新值，默认数据为第一个
     // value 每次的新数据，默认为数据的第二个
       println("accumulator $accumulator, value $value")
       accumulator + value
   }
   // println("list result $list")
   // accumulator 100, value 200
   // accumulator 300, value 300
   // accumulator 600, value 400
   // accumulator 1000, value 500
   // list result 1500
```
fold() 和 reduce() 一样，只是多了一个默认的初始值，fold(initial = 1)，accumulator 默认数据为第initial
 ,value 默认为数据的第一条数据



### 参考资料
- [中文文档](https://book.kotlincn.net/text/flow.html)
