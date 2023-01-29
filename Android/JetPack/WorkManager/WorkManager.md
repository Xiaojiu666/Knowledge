### 什么是WorkManager
##### 定义
  WorkManger可以定义“工作”，将其置于队列，随后运行，并且在完成时通知

##### 基础功能
- 延期执行
      不仅仅是定时执行任务，不必考虑执行时间，WorkManager会自动分配最佳时机，包括电量，状态等
- 约束 Constraints
      例如:空闲时/充电时/连接网络时/
- 多工作之间 通信
      例如:获取数据工作完成后，同步数据到本地数据库
- 定期执行任务

- 清楚的观察任务状态、

- 持久性
      无论在页面被回收还是横竖屏切换，包括进程被终止，设备重启，任务都会从中断位置恢复

- 保持任务唯一性 ExistingWorkPolicy
      例如网络请求借口，重复点击导致借口被调用多次,通过ExistingWorkPolicy 枚举进行控制
- 支持多进程


### 使用
##### 几种Worker
谷歌提供了四种Worker给我们使用，分别为：自动运行在后台线程的Worker、结合协程的CoroutineWorker、结合RxJava2的RxWorker和以上三个类的基类的ListenableWorker。


### 参考资料
- [Android Studio 对现代 WorkManager 的支持](https://www.bilibili.com/video/BV1tS4y1F7Bc?p=1&share_medium=iphone&share_plat=ios&share_source=WEIXIN&share_tag=s_i&timestamp=1646407852&unique_k=Usz3RNA)
- [WorkManager从入门到实践，有这一篇就够了](https://segmentfault.com/a/1190000020077800)
- [即学即用Android Jetpack - WorkManger](https://www.jianshu.com/p/68e720b8a939)
- [WorkManager的基本使用](https://zhuanlan.zhihu.com/p/78599394)
