## Flow
数据流以协程为基础构建，可提供多个值。从概念上来讲，数据流是可通过异步方式进行计算处理的一组数据序列。所发出值的类型必须相同。例如，Flow<Int> 是发出整数值的数据流。
数据流包含三个实体：
- 提供方会生成添加到数据流中的数据。得益于协程，数据流还可以异步生成数据
- （可选）中介可以修改发送到数据流的值，或修正数据流本身。
- 使用方则使用数据流中的值。

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
          transformData.collect {
              textView.text = it
          }
      }
```
