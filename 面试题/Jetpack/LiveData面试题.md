#### LiveData 是粘性的吗？若是，它是怎么做到的
-   LiveData 的值被存储在内部的字段中，直到有更新的值覆盖，所以值是持久的。
-   两种场景下 LiveData 会将存储的值分发给观察者。一是值被更新，此时会遍历所有观察者并分发之。二是新增观察者或观察者生命周期发生变化（至少为 STARTED），此时只会给单个观察者分发值。
-   LiveData 的观察者会维护一个“值的版本号”，用于判断上次分发的值是否是最新值。该值的初始值是-1，每次更新 LiveData 值都会让版本号自增。
-   LiveData 并不会无条件地将值分发给观察者，在分发之前会经历三道坎：1. 数据观察者是否活跃。2. 数据观察者绑定的生命周期组件是否活跃。3. 数据观察者的版本号是否是最新的。
“新观察者”被“老值”通知的现象叫“粘性”。因为新观察者的版本号总是小于最新版号，且添加观察者时会触发一次老值的分发。

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
在高频数据更新的场景下使用 LiveData.postValue() 时，会造成数据丢失。因为“设值”和“分发值”是分开执行的，之间存在延迟。值先被缓存在变量中，再向主线程抛一个分发值的任务。若在这延迟之间再一次调用 postValue()，则变量中缓存的值被更新，之前的值在没有被分发之前就被擦除了。

              