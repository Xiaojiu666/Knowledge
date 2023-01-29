### 什么是"ViewModel"
###### 官方介绍
ViewModel 类是一种业务逻辑或屏幕级状态容器。它用于将状态公开给界面，以及封装相关的业务逻辑。 它的主要优点是，它可以缓存状态，并可在配置更改后持久保留相应状态。这意味着在 activity 之间导航时或进行配置更改后（例如旋转屏幕时），界面将无需重新提取数据。

###### 优点
- 它允许您持久保留界面状态(页面数据保存)。
- 它可以提供对业务逻辑的访问权限(MVVM)。

###### 持久性
ViewModel 允许数据在 ViewModel 持有的状态和 ViewModel 触发的操作结束后继续存在。这种缓存意味着在常见的配置更改（例如屏幕旋转）完成后，无需重新提取数据。

###### 作用域
实例化 ViewModel 时，您会向其传递实现 ViewModelStoreOwner 接口的对象。它可能是 Navigation 目的地、Navigation 图表、activity、fragment 或实现接口的任何其他类型。然后，ViewModel 的作用域将限定为 ViewModelStoreOwner 的 Lifecycle。它会一直保留在内存中，直到其 ViewModelStoreOwner 永久消失。  
当 ViewModel 的作用域 fragment 或 activity 被销毁时，异步工作会在作用域限定到该 fragment 或 activity 的 ViewModel 中继续进行。这是持久性的关键。

###### SavedStateHandle
使用SavedStateHandle不仅可以在更改配置后持久保留数据，还可以在进程重新创建过程中持久保留数据。也就是说，即使关闭应用，稍后又将其打开，界面状态也可以保持不变。

###### 对业务逻辑的访问权限
尽管绝大多数业务逻辑都存在于数据层中，但界面层也可以包含业务逻辑。当您合并多个代码库中的数据以创建屏幕界面状态时，或特定类型的数据不需要数据层时，情况就是如此。
ViewModel 是在界面层处理业务逻辑的正确位置。当需要应用业务逻辑来修改应用数据时，ViewModel 还负责处理事件并将其委托给层次结构中的其他层。

###### 自我理解
 MVVM 框架核心，UI层的控制器，约等于MVP中的P层，但是多了对数据的持久化操作，VM 主要作用，M层的数据调用，存储UI所需要的数据。


### 基础使用
###### 





### 普通使用
- 数据持久化
```Java
class MyViewModel : ViewModel() {
    //LiveData 初始化
    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers()
        }  
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}
```
访问
```Java
class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val model: MyViewModel by viewModels()
        model.getUsers().observe(this, Observer<List<User>>{ users ->
            // update UI
        })
    }
}
```
- Fragment共享数据
```Java
    class SharedViewModel : ViewModel() {
        val selected = MutableLiveData<Item>()

        fun select(item: Item) {
            selected.value = item
        }
    }
    class MasterFragment : Fragment() {

        private lateinit var itemSelector: Selector
        private val model: SharedViewModel by activityViewModels()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            itemSelector.setOnClickListener { item ->
                // Update the UI
            }
        }
    }
    class DetailFragment : Fragment() {
        private val model: SharedViewModel by activityViewModels()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            model.selected.observe(viewLifecycleOwner, Observer<Item> { item ->
            })
        }
    }
```

### QA

### 参考资料
- [ViewModel 四种集成方式](https://zhuanlan.zhihu.com/p/143346337)
