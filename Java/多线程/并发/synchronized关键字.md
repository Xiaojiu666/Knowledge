##### 概念
synchronized 是 Java 中的关键字，是利用锁的机制来实现同步的。
锁机制有如下两种特性：
互斥性：即在同一时间只允许一个线程持有某个对象锁，通过这种特性来实现多线程中的协调机制，这样在同一时间只有一个线程对需同步的代码块(复合操作)进行访问。互斥性我们也往往称为操作的原子性。

可见性：必须确保在锁被释放之前，对共享变量所做的修改，对于随后获得该锁的另一个线程是可见的（即在获得锁时应获得最新共享变量的值），否则另一个线程可能是在本地缓存的某个副本上继续操作从而引起不一致。



#####  同步方法
同步方法使用 synchronized 关键字声明在方法前面，以确保在同一时刻只有一个线程能够执行该方法。如果方法是实例方法，那么锁定的是当前实例对象（this）；如果是静态方法，那么锁定的是整个类对象（Class）。

```JAVA
class InstanceLockExample {

    private var counter = 0

    @Synchronized
    fun increment() {
        println(Thread.currentThread().name + " got instance lock")
        try {
            counter++
            Thread.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        println(Thread.currentThread().name + " released instance lock $counter ")
    }
}
```
##### 同步静态方法
``` JAVA
object SynchronizedStaticExample {
    @get:Synchronized
    var counter = 0
        private set

    // 同步静态方法
    @Synchronized
    fun increment() {
        println(Thread.currentThread().name + " got instance lock")
        counter++
        println(Thread.currentThread().name + " released instance lock $counter")
    }
}
```
##### 同步代码块
同步代码块提供了更细粒度的控制，允许你只在需要的时候锁定特定的对象，而不是锁定整个方法。这样可以减少锁的持有时间，从而提高并发性能。

示例：同步代码块
```JAVA
class SynchronizedBlockExample {
    private var counter = 0
    private val lock = Any()
    fun increment() {
        synchronized(lock) { counter++ }
    }

    fun getCounter(): Int {
        synchronized(lock) { return counter }
    }
}
```

###### 注意事项
-   锁的粒度：锁的粒度越小，并发性能越高。但是，粒度太小会增加死锁的风险。因此，需要在性能和安全性之间取得平衡。
-   避免死锁：当多个线程试图获取多个锁时，需要小心避免死锁。可以通过固定加锁顺序和使用超时锁来减少死锁风险。
-   可见性：synchronized 确保锁定和解锁之间的所有操作对其他线程可见。因此，它不仅提供了原子性，还提供了内存可见性。
###### 总结
-   同步方法：使用 synchronized 关键字修饰方法，锁定的是当前对象（实例方法）或类对象（静态方法）。
-   同步代码块：使用 synchronized 关键字在代码块内锁定特定对象，提供更细粒度的锁控制。
-   避免死锁：通过设计和编码策略，确保在多线程环境下使用锁时避免死锁。
通过正确使用 synchronized 关键字，可以有效地保护共享资源，确保多线程程序的正确性和安全性。


##### 重用锁

