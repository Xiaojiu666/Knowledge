### LiveData 面试题

#### 一、基础概念
1. **什么是 LiveData？**
   - LiveData 是一个可观察的数据持有类
   - 具有生命周期感知能力
   - 仅在组件处于活跃状态时更新数据
   - 确保 UI 与数据状态匹配

2. **LiveData 的优势是什么？**
   - 确保 UI 与数据状态匹配
     * 不会发生内存泄漏
     * 不会因 Activity 停止而崩溃
     * 自动清理数据
   - 生命周期感知
     * 只在活跃生命周期更新 UI
     * 自动处理配置更改
   - 数据始终保持最新
   - 适当的配置更改处理
   - 资源共享能力

3. **LiveData 与 MutableLiveData 有什么区别？**
   - MutableLiveData 是 LiveData 的子类
   - LiveData 只暴露观察数据的方法（observe, getValue）
   - MutableLiveData 额外提供修改数据的方法（setValue, postValue）
   - 体现了封装思想，保证数据单向流动

#### 二、核心机制
4. **LiveData 的粘性是如何实现的？**
   - 值持久性：存储在内部字段直到被覆盖
   - 分发机制：
     * 值更新时遍历所有观察者分发
     * 新增观察者时给单个观察者分发
   - 版本号机制：
     * 观察者维护版本号（初始值-1）
     * 每次更新值版本号自增
   - 分发条件：
     * 观察者活跃
     * 生命周期组件活跃
     * 版本号非最新

5. **observe 和 observeForever 有什么区别？**
   - observe:
     * 需要生命周期所有者
     * 自动移除观察者
     * 仅活跃状态接收更新
   - observeForever:
     * 不需要生命周期所有者
     * 需手动移除观察者
     * 持续接收更新
     * 易造成内存泄漏

#### 三、数据处理机制
6. **setValue 和 postValue 的区别与问题？**
   - setValue:
     * 主线程调用
     * 同步更新
     * 大数据量时可能阻塞主线程
   - postValue:
     * 任意线程调用
     * 异步更新
     * 高频调用可能丢失数据

7. **postValue 为什么会丢失数据？**
   - 工作机制：
     * 值保存在临时变量
     * Handler 发送 Runnable 到主线程
     * Runnable 执行时才更新数据
   - 丢失原因：
     * 连续调用时后值覆盖前值
     * 最终只处理最后一个值

8. **如何解决 postValue 数据丢失？**
   ```kotlin
   // 方案一：使用 Channel 或 Flow
   private val _dataChannel = Channel<List<Data>>()
   val dataFlow = _dataChannel.receiveAsFlow()

   // 方案二：使用队列缓存
   class SafePostLiveData<T> : MutableLiveData<T>() {
       private val pendingQueue = ConcurrentLinkedQueue<T>()
       // ... 实现队列处理逻辑
   }

   // 方案三：批量处理
   private val dataBuffer = mutableListOf<Data>()
   private val BATCH_SIZE = 100
   // ... 实现批量处理逻辑
   ```

#### 四、实践应用
9. **如何实现 LiveData 线程切换？**
   ```kotlin
   // 方式一：Transformations
   liveData.map { 
       withContext(Dispatchers.IO) {
           // 执行耗时操作
       }
   }

   // 方式二：liveData 协程构建器
   liveData(Dispatchers.IO) {
       // 执行耗时操作
       emit(result)
   }
   ```

10. **如何合并多个数据源？**
    ```kotlin
    // 方式一：MediatorLiveData
    val mediatorLiveData = MediatorLiveData<String>()
    mediatorLiveData.addSource(liveData1) { value1 ->
        // 处理数据
    }

    // 方式二：Transformations
    Transformations.map(liveData1) { value1 ->
        // 转换数据
    }
    ```

#### 五、最佳实践
11. **LiveData 使用建议**
    - 大数据量场景优先使用 Flow 或 Channel
    - 必须用 LiveData 时实现批量处理
    - 考虑数据分页加载
    - 高频数据使用防抖或节流
    - 注意生命周期绑定
    - 及时移除 observeForever 观察者

#### LiveData 是粘性的吗？若是，它是怎么做到的
-   LiveData 的值被存储在内部的字段中，直到有更新的值覆盖，所以值是持久的。
-   两种场景下 LiveData 会将存储的值分发给观察者。一是值被更新，此时会遍历所有观察者并分发之。二是新增观察者或观察者生命周期发生变化（至少为 STARTED），此时只会给单个观察者分发值。
-   LiveData 的观察者会维护一个"值的版本号"，用于判断上次分发的值是否是最新值。该值的初始值是-1，每次更新 LiveData 值都会让版本号自增。
-   LiveData 并不会无条件地将值分发给观察者，在分发之前会经历三道坎：1. 数据观察者是否活跃。2. 数据观察者绑定的生命周期组件是否活跃。3. 数据观察者的版本号是否是最新的。
"新观察者"被"老值"通知的现象叫"粘性"。因为新观察者的版本号总是小于最新版号，且添加观察者时会触发一次老值的分发。

#### 粘性的 LiveData 会造成什么问题？怎么解决？
- 在每次LiveData得观察者，被初始化时，都会被调用  
- 解决方案一：带消费记录的值
```JAVA
// 一次性值
open class OneShotValue<out T>(private val value: T) {
    // 值是否被消费
    private var handled = false
    // 获取值，如果值未被处理则返回，否则返回空
    fun getValue(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            value
        }
    }
    // 获取上次被处理的值
    fun peekValue(): T = value
}
```
缺点 ：所有需要消费的页面，都不能拉取 黏性值了

- 解决方案二：带有最新版本号的观察者
```JAVA
// 观察者包装类型 所以得通过反射修改 mLastVersion 字段。
private abstract class ObserverWrapper {
    // 当前观察者最新值版本号，初始值为 -1
    int mLastVersion = START_VERSION;
    ...
}
```
缺点：入侵性强，将整个LiveData修改了
- 解决方案三：SingleLiveEvent
```java
public class SingleLiveEvent<T> extends MutableLiveData<T> {
    // 标志位，用于表达值是否被消费
    private final AtomicBoolean mPending = new AtomicBoolean(false);

    public void observe(LifecycleOwner owner, final Observer<T> observer) {
        // 中间观察者
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                // 只有当值未被消费过时，才通知下游观察者
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    public void setValue(@Nullable T t) {
        // 当值更新时，置标志位为 true
        mPending.set(true);
        super.setValue(t);
    }

    public void call() {
        setValue(null);
    }
}
```

缺点：SingleLiveEvent 添加多个观察者，则当第一个观察者消费了数据后，其他观察者就没机会消费了。因为mPending是所有观察者共享的。

- 解决方案四：Flow
#### 什么情况下 LiveData 会丢失数据?
在高频数据更新的场景下使用 LiveData.postValue() 时，会造成数据丢失。因为"设值"和"分发值"是分开执行的，之间存在延迟。值先被缓存在变量中，再向主线程抛一个分发值的任务。若在这延迟之间再一次调用 postValue()，则变量中缓存的值被更新，之前的值在没有被分发之前就被擦除了。

#### LiveData 的优势是什么？
- 确保 UI 与数据状态匹配
  - 不会发生内存泄漏（观察者绑定生命周期）
  - 不会因 Activity 停止而崩溃
  - 自动清理数据
- 生命周期感知
  - 只在活跃生命周期（STARTED 或 RESUMED）才更新 UI
  - 自动处理配置更改
- 始终保持数据最新
  - 确保在重新激活后立即收到最新数据
- 适当的配置更改处理
  - 在屏幕旋转等配置更改后自动接收最新数据
- 资源共享
  - 可以扩展 LiveData 对象以实现资源共享

#### LiveData 的 observe 和 observeForever 有什么区别？
- observe(LifecycleOwner, Observer)
  - 需要传入生命周期所有者
  - 会在生命周期结束时自动移除观察者
  - 只在活跃状态（STARTED 或 RESUMED）接收更新
- observeForever(Observer)
  - 不需要生命周期所有者
  - 需要手动调用 removeObserver 移除观察者
  - 会一直接收更新，不考虑任何生命周期状态
  - 容易造成内存泄漏，使用时需要特别注意

#### LiveData 的 setValue 和 postValue 有什么区别？
- setValue
  - 必须在主线程调用
  - 同步更新数据
  - 直接触发观察者更新
- postValue
  - 可以在任何线程调用
  - 异步更新数据（通过 Handler 发送到主线程）
  - 可能会丢失中间值（如果短时间内多次调用）
  - 在下一个主线程消息循环中触发观察者更新

#### LiveData 如何实现线程切换？
- LiveData 本身不提供线程切换功能
- 可以通过以下方式实现线程切换：
```kotlin
// 方式一：使用 Transformations
liveData.map { 
    withContext(Dispatchers.IO) {
        // 执行耗时操作
    }
}

// 方式二：使用 liveData 协程构建器
liveData(Dispatchers.IO) {
    // 执行耗时操作
    emit(result)
}
```

#### 如何实现 LiveData 合并多个数据源？
```kotlin
// 方式一：使用 MediatorLiveData
val mediatorLiveData = MediatorLiveData<String>()
mediatorLiveData.addSource(liveData1) { value1 ->
    // 处理 liveData1 的数据
}
mediatorLiveData.addSource(liveData2) { value2 ->
    // 处理 liveData2 的数据
}

// 方式二：使用 Transformations.map 或 switchMap
Transformations.map(liveData1) { value1 ->
    // 转换数据
}
```

              