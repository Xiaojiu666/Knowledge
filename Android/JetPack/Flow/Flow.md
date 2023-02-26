### Flow
在Kotlin协程中提供的响应式编程方式就是Kotlin Flow。

###### 冷流和热流
冷流  
冷流，即执行是惰性的，调用末端流操作符(collect 是其中之一)之前， flow{ ... } 中的代码不会执行，只有当数据被订阅的时候(执行collect)，发布者才开始执行发射数据流的代码(执行flow{ ... })。当有多个订阅者的时候，每个订阅者都会收到发送者完整的流程。即订阅者和发送者都是一对一的关系。
```JAVA
  flow = flow<Int> {
      for (i in 1..100) {
          emit(i)
          println("emitData $i")
      }
  }
  // 点击 按钮，才会拉流
  flow!!.collect {
          println("receiveData1 $it")
  }
  //receiveData1 1
  //emitData1
  //receiveData1 2
  //emitData2
  //..
  //receiveData1 100
  //emitData100
  // 当有；两个 订阅者的时候，会先走第一个，在走第二个
  flow!!.collect {
           println("receiveData2 $it")
       }
  //receiveData1 1
  //emitData1
  //..
  //receiveData1 100
  //emitData100

  //receiveData2 1
  //emitData1
  //..
  //receiveData2 100
  //emitData100
```
热流 (StateFlow,SharedFlow)
- 对于同一个数据流，可以允许有多个订阅者共享。
- 不调用collect收集数据，也会开始发送数据。
- 允许缓存历史数据
- 发送数据函数都是线程安全的。  
由于特性，所以适合多线程通信

### 参考资料
- [Kotlin Flow上手指南（一）基础使用](https://juejin.cn/post/7034379406730592269)
