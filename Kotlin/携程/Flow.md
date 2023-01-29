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
         transformData
             .catch { exception -> LogUtils.d(exception) }
             .collect {
                 textView.text = it
             }
     }
```



## StateFlow
StateFlow 和 LiveData 具有相似之处。两者都是可观察的数据容器类，并且在应用架构中使用时，两者都遵循相似模式。
区别
- StateFlow 需要将初始状态传递给构造函数，而 LiveData 不需要。
- 当 View 进入 STOPPED 状态时，LiveData.observe() 会自动取消注册使用方，而从 StateFlow 或任何其他数据流收集数据的操作并不会自动停止。如需实现相同的行为，您需要从 Lifecycle.repeatOnLifecycle 块收集数据流。
