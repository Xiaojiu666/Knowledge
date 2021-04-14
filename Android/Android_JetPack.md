##	Jetpack--DataStore 



## Jetpack--DataBinding

## Jetpack--Room



##	Jetpack--LiveData

[`LiveData`](https://developer.android.google.cn/reference/androidx/lifecycle/LiveData) 是一种可观察的数据存储器类。与常规的可观察类不同，LiveData 具有生命周期感知能力，意指它遵循其他应用组件（如 Activity、Fragment 或 Service）的生命周期。这种感知能力可确保 LiveData 仅更新处于活跃生命周期状态的应用组件观察者。

## Jetpack--ViewModel

[`ViewModel`](https://developer.android.google.cn/reference/androidx/lifecycle/ViewModel) 类旨在以注重生命周期的方式存储和管理界面相关的数据。[`ViewModel`](https://developer.android.google.cn/reference/androidx/lifecycle/ViewModel) 类让数据可在发生屏幕旋转等配置更改后继续留存。

简单来说就是数据不在依赖所关联的类，例如在某个用户列表Activity中包含用户列表，在页面销毁时，不销毁Activity中的相关数据，从而达到数据与布局分类，当然也可以通过onSaveInstanceState() 在onCreate 时恢复，但此方法仅适合可以序列化再反序列化的少量数据，而不适合数量可能较大的数据，如用户列表或位图。





## Jetpack--WorkManager







#### QA

1. Q : Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.

   A : https://blog.csdn.net/wjzj000/article/details/95863976




####	参考资料

1.[数据库可视化工具SQLScout](https://blog.csdn.net/xhnmbest/article/details/105994122)

