### 什么是"ViewModel"
##### 官方介绍
ViewModel负责为界面准备数据。在配置更改期间会自动保留 ViewModel 对象，以便它们存储的数据立即可供下一个 Activity 或 Fragment 实例使用。例如，如果您需要在应用中显示用户列表，请确保将获取和保留该用户列表的责任分配给 ViewModel

如果重新创建了该 Activity，它接收的 MyViewModel 实例与第一个 Activity 创建的实例相同。当所有者 Activity 完成时，框架会调用 ViewModel 对象的 onCleared() 方法，以便它可以清理资源。

<img src="C:\Users\86188\AppData\Roaming\Typora\typora-user-images\image-20210713223405458.png" alt="image-20210713223405458" style="zoom:80%;" />

##### 生命周期
ViewModel 对象存在的时间范围是获取 ViewModel 时传递给 ViewModelProvider 的 Lifecycle。ViewModel 将一直留在内存中，直到限定其存在时间范围的 Lifecycle 永久消失：对于 Activity，是在 Activity 完成时；而对于 Fragment，是在 Fragment 分离时。

图 1 说明了 Activity 经历屏幕旋转而后结束时所处的各种生命周期状态。该图还在关联的 Activity 生命周期的旁边显示了 ViewModel 的生命周期。此图表说明了 Activity 的各种状态。这些基本状态同样适用于 Fragment 的生命周期。

##### 自我理解
将数据和View层分开，让View不持有数据的引用，即使View销毁，也不影响Data，可以理解为一个单例的Manager数据管理类，配合LiveData 可以对数据进行进行观察，MVVM 框架核心

##### 优点
- 数据持久化
- 异步回调问题
- MVVM
- Fragments 间共享数据


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
