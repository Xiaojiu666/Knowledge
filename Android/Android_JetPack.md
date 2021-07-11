##	Jetpack_DataStore



## Jetpack--DataBinding

## Jetpack--Room



##	Jetpack--LiveData

####	介绍

[`LiveData`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData) 是一种可观察的数据存储器类。与常规的可观察类不同，LiveData 具有生命周期感知能力，意指它遵循其他应用组件（如 Activity、Fragment 或 Service）的生命周期。这种感知能力可确保 LiveData 仅更新处于活跃生命周期状态的应用组件观察者。

Livedata 遵循观察者模式，并且 Livedata 会在生命周期变化的时候通知观察者。
它优雅的处理了生命周期问题，并不会所有的数据变化都会回调，所以你可以在他回调时大胆的做更新 UI操作。

观察者都是绑定Lifecycle的， Lifecycle destory 的话，会销毁自己。



## Jetpack--ViewModel

####	介绍

ViewModel类是被设计用来以可感知生命周期的方式存储和管理 UI 相关数据，ViewModel中数据会一直存活即使 activity configuration发生变化，比如横竖屏切换的时候。

#### 1、数据持久化

我们知道在屏幕旋转的 时候 会经历 activity 的销毁与重新创建，这里就涉及到数据保存的问题，显然重新请求或加载数据是不友好的。在 ViewModel 出现之前我们可以用 activity 的onSaveInstanceState()机制保存和恢复数据，但缺点很明显，onSaveInstanceState只适合保存少量的可以被序列化、反序列化的数据，假如我们需要保存是一个比较大的 bitmap list ，这种机制明显不合适。
由于 ViewModel 的特殊设计，可以解决此痛点。

#### 2、异步回调问题

通常我们 app 需要频繁异步请求数据，比如调接口请求服务器数据。当然这些请求的回调都是相当耗时的，之前我们在 Activity 或 fragment里接收这些回调。所以不得不考虑潜在的内存泄漏情况，比如 Activity 被销毁后接口请求才返回。处理这些问题，会给我们增添好多复杂的工作。
但现在我们利用 ViewModel 处理数据回调，可以完美的解决此痛点。

#### 3、分担P层负担

从最早的 MVC 到目前流行的 MVP、MVVM，目的无非是 明确职责，分离 UI controller 负担。
UI controller 比如 Activity 、Fragment 是设计用来渲染展示数据、响应用户行为、处理系统的某些交互。如果再要求他去负责加载网络或数据库数据，会让其显得臃肿和难以管理。所以为了简洁、清爽、丝滑，我们可以分离出数据操作的职责给 ViewModel。

#### 4、Fragments 间共享数据

```java
public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Item> selected = new MutableLiveData<Item>();

    public void select(Item item) {
        selected.setValue(item);
    }

    public LiveData<Item> getSelected() {
        return selected;
    }
}


public class MasterFragment extends Fragment {
    private SharedViewModel model;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        itemSelector.setOnClickListener(item -> {
            model.select(item);
        });
    }
}

public class DetailFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedViewModel model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.getSelected().observe(this, { item ->
           // Update the UI.
        });
    }
}
```









## Jetpack--WorkManager







#### QA

1. Q : Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.

   A : https://blog.csdn.net/wjzj000/article/details/95863976




####	参考资料

- [数据库可视化工具SQLScout](https://blog.csdn.net/xhnmbest/article/details/105994122)
- [深入了解架构组件之ViewModel](https://www.jianshu.com/p/35d143e84d42)

