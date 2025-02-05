### Kotlin Flow 面试题

#### 一、基础概念
1. **什么是 Flow？**
   - Flow 是 Kotlin 协程的冷流 API
   - 用于异步处理数据流
   - 支持背压处理
   - 基于协程构建，具有结构化并发特性

2. **Flow 与其他响应式框架的区别？**
   - 与 RxJava 比较：
     * Flow 基于协程，RxJava 基于观察者模式
     * Flow 更轻量级，API 更简单
     * Flow 天然支持协程作用域和结构化并发
   - 与 LiveData 比较：
     * Flow 是冷流，LiveData 是热流
     * Flow 支持背压，LiveData 不支持
     * Flow 提供丰富的操作符
     * Flow 需要在协程作用域中收集

3. **冷流与热流的区别？**
   - 冷流：
     * 每个收集器都会触发流的重新执行
     * 不会主动发射数据
     * 多个收集器会收到不同的数据
   - 热流：
     * 无论是否有收集器都会发射数据
     * 所有收集器共享数据
     * 适合用于状态管理

#### 二、Flow 构建与收集
4. **创建 Flow 的几种方式？**
```kotlin
// 方式一：flow 构建器
val flow = flow {
    for (i in 1..3) {
        emit(i)
        delay(100)
    }
}

// 方式二：flowOf
val flow = flowOf(1, 2, 3)

// 方式三：asFlow
val flow = (1..3).asFlow()

// 方式四：channelFlow
val flow = channelFlow {
    for (i in 1..3) {
        send(i)
        delay(100)
    }
}
```

5. **Flow 的收集方式有哪些？**
```kotlin
// 方式一：collect
flow.collect { value ->
    println(value)
}

// 方式二：collectLatest（只处理最新值）
flow.collectLatest { value ->
    delay(100) // 长时间处理
    println(value)
}

// 方式三：launchIn（在特定作用域中收集）
flow.onEach { value ->
    println(value)
}.launchIn(viewModelScope)
```

#### 三、操作符与异常处理
6. **Flow 常用操作符有哪些？**
```kotlin
flow.map { it * 2 }              // 转换
    .filter { it > 0 }           // 过滤
    .take(2)                     // 限制数量
    .onEach { println(it) }      // 副作用
    .catch { e -> handle(e) }    // 异常处理
    .flowOn(Dispatchers.IO)      // 切换上游上下文
    .buffer()                    // 缓冲
    .debounce(300)              // 防抖
    .distinctUntilChanged()      // 去重
```

7. **Flow 的异常处理机制？**
```kotlin
flow {
    emit(1)
    throw Exception("Error")
}
.catch { e -> 
    // 处理上游异常
    emit(-1) // 可以发射默认值
}
.onCompletion { cause -> 
    // 流完成或取消时调用
    if (cause != null) {
        // 处理异常
    }
}
.collect {
    // try-catch 处理收集器异常
    try {
        process(it)
    } catch (e: Exception) {
        handle(e)
    }
}
```

#### 四、上下文与并发
8. **Flow 的上下文保存特性是什么？**
   - Flow 构建器中的代码遵循上下文保存
   - 不能在不同上下文中发射值
   - 必须使用 flowOn 来改变上游上下文
```kotlin
// 错误示例
flow {
    withContext(Dispatchers.IO) { // 违反上下文保存
        emit(data)
    }
}

// 正确示例
flow {
    emit(data)
}.flowOn(Dispatchers.IO)
```

9. **如何实现 Flow 并发处理？**
```kotlin
// 方式一：buffer
flow.buffer(Channel.BUFFERED)

// 方式二：conflate（保留最新值）
flow.conflate()

// 方式三：collectLatest（取消旧值处理）
flow.collectLatest { ... }

// 方式四：flatMapMerge（并发处理多个流）
flow.flatMapMerge { value ->
    flow {
        emit(process(value))
    }
}
```

#### 五、状态流
10. **StateFlow 和 SharedFlow 的区别？**
    - StateFlow：
      * 必须有初始值
      * 只保留最新值
      * 相同值不会重复发射
      * 适合状态管理
    - SharedFlow：
      * 可配置重放策略
      * 支持多个订阅者
      * 可以发射重复值
      * 适合事件传递

11. **如何使用 StateFlow 实现状态管理？**
```kotlin
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateState(newData: Data) {
        _uiState.update { currentState ->
            currentState.copy(data = newData)
        }
    }
}



// 在 UI 层收集
viewModel.uiState.collect { state ->
    updateUi(state)
}
```

12. **SharedFlow 的基础用法有哪些？**
```kotlin
class EventViewModel : ViewModel() {
    // 1. 基本创建方式
    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    // 2. 配置重放和缓存
    private val _configuredEvents = MutableSharedFlow<Event>(
        replay = 1,                // 新订阅者可以收到最后一个值
        extraBufferCapacity = 64,  // 额外缓冲区大小
        onBufferOverflow = BufferOverflow.DROP_OLDEST  // 缓冲区满时策略
    )

    // 3. 发送事件的方式
    fun sendEvent(event: Event) {
        viewModelScope.launch {
            _events.emit(event)        // 挂起函数，确保发送成功
            _events.tryEmit(event)     // 非挂起函数，返回是否发送成功
        }
    }
}

// 4. 不同的收集方式
class MainActivity : AppCompatActivity() {
    private val viewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 基本收集
        lifecycleScope.launch {
            viewModel.events.collect { event ->
                handleEvent(event)
            }
        }

        // 带超时的收集
        lifecycleScope.launch {
            withTimeoutOrNull(5000L) {
                viewModel.events.collect { event ->
                    handleEvent(event)
                }
            }
        }

        // 条件收集
        lifecycleScope.launch {
            viewModel.events
                .filter { it is Event.Important }
                .collect { event ->
                    handleImportantEvent(event)
                }
        }
    }
}

// 5. 常见配置场景
class ConfigExample {
    // 粘性事件（保留最后一个值）
    private val stickyEvents = MutableSharedFlow<Event>(replay = 1)

    // 无缓冲区，可能挂起
    private val immediateEvents = MutableSharedFlow<Event>(
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    // 有缓冲区，满时丢弃旧数据
    private val bufferedEvents = MutableSharedFlow<Event>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    // 有缓冲区，满时丢弃新数据
    private val dropNewEvents = MutableSharedFlow<Event>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
}

// 6. 实际应用示例
sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object ShowLoading : UiEvent()
    object HideLoading : UiEvent()
}

class RealWorldViewModel : ViewModel() {
    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun showError(message: String) {
        viewModelScope.launch {
            _uiEvents.emit(UiEvent.ShowToast(message))
        }
    }

    fun navigateToDetail(id: String) {
        viewModelScope.launch {
            _uiEvents.emit(UiEvent.ShowLoading)
            try {
                // 执行操作
                _uiEvents.emit(UiEvent.Navigate("detail/$id"))
            } finally {
                _uiEvents.emit(UiEvent.HideLoading)
            }
        }
    }
}
```


12. **SharedFlow 的缓冲区是什么？为什么需要它？**
   - **缓冲区的作用**：
     * 防止背压：发送速度快于接收速度时，缓存数据
     * 提升性能：减少发送方和接收方的耦合
     * 防止数据丢失：临时存储尚未处理的数据

   - **缓冲区配置**：
```kotlin
// 配置示例
val flow = MutableSharedFlow<Int>(
    replay = 1,                // 重放最后一个值
    extraBufferCapacity = 64,  // 额外缓冲区大小
    onBufferOverflow = BufferOverflow.DROP_OLDEST  // 溢出策略
)
```

   - **溢出策略**：
     * SUSPEND：缓冲区满时挂起（默认）
     * DROP_OLDEST：丢弃最旧的值
     * DROP_LATEST：丢弃最新的值

   - **缓冲区大小选择建议**：
     * 小缓冲区（1-10）：用户交互事件
     * 中等缓冲区（10-100）：网络请求响应
     * 大缓冲区（100+）：高频数据采集

   - **实际应用示例**：
```kotlin
class SensorDataViewModel : ViewModel() {
    // 高频传感器数据，使用大缓冲区
    private val _sensorData = MutableSharedFlow<SensorData>(
        extraBufferCapacity = 100,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    // 用户事件，使用小缓冲区
    private val _userEvents = MutableSharedFlow<UserEvent>(
        extraBufferCapacity = 10,
        onBufferOverflow = BufferOverflow.SUSPEND
    )

    // 监控缓冲区状态
    fun monitorBuffer() {
        viewModelScope.launch {
            while (true) {
                val replaySize = _sensorData.replayCache.size
                val subscriptionCount = _sensorData.subscriptionCount.value
                Log.d("Buffer", "Size: $replaySize, Subscribers: $subscriptionCount")
                delay(1000)
            }
        }
    }
}
```

   - **最佳实践**：
     * 根据数据产生速率估算缓冲区大小
     * 考虑内存限制
     * 选择合适的溢出策略
     * 监控缓冲区状态


#### 六、最佳实践
13. **Flow 使用建议**
    - 使用适当的作用域收集流
    - 正确处理异常情况
    - 注意内存泄漏
    - 选择合适的操作符
    - 适当使用背压策略
    - 注意生命周期管理
