### 基础

###### 并行和并发的区别

- 并行是指两个或多个事件在同一时刻发生；而并发是指两个或多个事件在同一时间间隔发生。
- 并行是在不同实体上的多个事件，并发是在同一实体上的多个事件。
- 在一台处理器上“同时”处理多个任务，在多台处理器上同时处理多个任务。如hadoop分布式集群。
- 实际应用场景里，一般是考虑多并发问题，而不是多并行问题。

###### 线程和进程的区别？
进程是程序运行和资源分配的基本单位，一个程序至少有一个进程，一个进程至少有一个线程，但一个进程一般有多个线程。  
进程在运行过程中，需要拥有独立的内存单元，否则如果申请不到，就会挂起。而多个线程能共享内存资源，这样就能降低运行的门槛，从而效率更高。  
线程是是cpu调度和分派的基本单位，在实际开发过程中，一般是考虑多线程并发。

###### 创建线程有哪几种方式？
- 1、继承Thread类创建线程类
通过extends Thread定义Thread类的子类，并重写该类的run方法。  
创建Thread子类的实例，并调用线程对象的start()方法来启动该线程。  
- 2、通过Runnable接口创建线程类  
implements Runnable接口的实现类，并重写该接口的run()方法。  
创建 Runnable实现类的实例，并依此实例作为Thread的target来创建Thread对象，该Thread对象才是真正的线程对象。  
调用线程对象的start()方法来启动该线程。  


###### 线程有哪些状态？
- 创建  
创建状态。创建好线程对象，并没有调用该对象的start方法，此时线程处于创建状态。  
- 就绪  
就绪状态。当调用线程对象的start方法之后，该线程就进入了就绪状态，但是此时线程调度程序还没有把该线程设置为当前线程，也就是说还没进入运行状态。或者在线程运行之后，从等待或者睡眠状态中回来之后，也会处于就绪状态，等待被调度进入运行状态。  
- 运行  
运行状态。线程调度程序将处于就绪状态的线程设置为当前线程，此时线程就进入了运行状态，开始运行run函数当中的代码。
- 阻塞  
阻塞状态。线程正在运行的时候，被暂停，通常是为了等待某个实践的发生(比如说某项资源就绪)之后再继续运行。wait方法都可以导致线程阻塞。
- 死亡  
死亡状态。如果一个线程的run方法执行结束或者调用stop方法后，该线程就会死亡。对于已经死亡的线程，无法再使用start方法令其进入就绪  

###### sleep() 和 wait() 有什么区别？
- sleep()：这是线程类（Thread）的静态方法，让线程进入睡眠状态，等休眠时间结束后，线程进入就绪状态，和其他线程一起竞争cpu的执行时间。  
因为sleep() 是static静态的方法，他不能改变对象的机锁，当一个synchronized块中调用了sleep() 方法，线程虽然进入休眠，但是对象的机锁没有被释放，其他线程依然无法访问这个对象，这时就会引发问题，此类现象请注意。  

- wait()：wait()是Object类的方法，当一个线程执行到wait方法时，它就进入到一个和该对象相关的等待池，同时释放对象的机锁，使得其他线程能够访问，可以通过notify，notifyAll方法来唤醒等待的线程。  

###### 线程的 run()和 start()有什么区别？
- run()方法里是线程需要执行的任务
- start()方法的作用是启动线程

###### ThreadLocal 是什么？有哪些使用场景？
这是线程局部变量，属于线程自身私有，不在多个线程间共享。
Java提供ThreadLocal类来支持线程局部变量，是一种实现线程安全的方式。

源码分析:
```Java
/**
* 根据当前线程做Key,变量做value 保证 变量是线程私有的
*/
public void set(T value) {
    Thread t = Thread.currentThread();
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}
```



### 线程同步
##### Synchronized 关键字原理
- 普通同步方法，锁是当前实例对象
- 静态同步方法，锁是当前类的class对象
- 同步方法块，锁是括号里面的对象

[Synchronized原理](https://blog.csdn.net/li1325169021/article/details/121779198)

自旋锁、适应性自旋锁、锁消除、锁粗化、偏向锁、轻量级锁等技术来减少锁操作的开销。

synchronized的语义底层是通过监视器锁（monitor）的对象来完成，每个对象有一个monitor。每个synchronized修饰过的代码当它的monitor 被占用时就会处于锁定状态并且尝试获取monitor的所有权，过程如下所示：  
（A）、 如果monitor的进入数为0，则该线程进入monitor，然后将进入数设置为 1，该线程即为 monitor的所有者。  
（B）、如果线程己经占有该monitor，只是重新进入，则进入monitor 的进入数加1。  
（C）、如果其他线程己经占用了monitor，则该线程进入阻塞状态，直到 monitor的进入数为 0，再重新尝试获取 monitor 的所有权。  

##### 线程间通信之Lock
[线程间通信之Lock](https://www.mianshigee.com/note/detail/235886eoy/)

#####	java线程同步的方法有哪几种，请做解释(百度面试题)
[java线程同步的几种方法](https://www.cnblogs.com/mikechenshare/p/16736772.html)
-  synchronized关键字
-  wait和notify
-  使用特殊域变量volatile实现线程同步
-  使用重入锁实现线程同步
-  使用局部变量来实现线程同步
-  使用阻塞队列实现线程同步

###### 在 java 程序中怎么保证多线程的运行安全？
- 原子性：提供互斥访问，同一时刻只能有一个线程对数据进行操作，（atomic,synchronized）；
- 可见性：一个线程对主内存的修改可以及时地被其他线程看到，（synchronized,volatile）；
- 有序性：一个线程观察其他线程中的指令执行顺序，由于指令重排序，该观察结果一般杂乱无序，（happens-before原则）

###### synchronized 和 volatile 的区别是什么？
- volatile是在告诉jvm当前变量在自身线程的内存区里，值是不确定的，需要从主存中读取； - synchronized则是锁定当前变量，只有当前线程可以访问该变量，其他线程被阻塞住。
- volatile仅能使用在变量级别；synchronized则可以使用在变量、方法、和类级别的。
- volatile仅能实现变量的修改可见性，不能保证原子性；而synchronized则可以保证变量的修改可见性和原子性。
- volatile不会造成线程的阻塞；synchronized可能会造成线程的阻塞。
- volatile标记的变量不会被编译器优化；synchronized标记的变量可以被编译器优化。

###### synchronized 和 Lock 有什么区别？
- synchronized是java关键字，Lock是个java类；
- synchronized无法判断是否获取锁的状态，Lock可以判断是否获取到锁；
- synchronized会自动释放锁，Lock需在finally中手工释放锁（unlock()方法释放锁）；
- 用synchronized关键字的两个线程1和线程2，如果当前线程1获得锁，线程2线程等待。如果线程1阻塞，线程2则会一直等待下去，而Lock锁就不一定会等待下去，如果尝试获取不到锁，线程可以不用一直等待就结束了；
- synchronized的锁可重入、不可中断、非公平，而Lock锁可重入、可判断、可公平(皆可)
- Lock锁适合大量同步的代码的同步问题，synchronized锁适合代码少量的同步问题。
结论：synchronized很重，而且大多只能加在单个方法上，而Lock可以作用在调用多个业务的方法上，使用起来比较简便。





### 线程池
##### 线程池得几种创建方式
[线程池得七种创建方式](https://blog.csdn.net/qq_41821963/article/details/125341789)
1. Executors.newFixedThreadPool：创建⼀个固定⼤⼩的线程池，可控制并发的线程数，超出的线程会在队列中等待；  
2. Executors.newCachedThreadPool：创建⼀个可缓存的线程池，若线程数超过处理所需，缓存⼀段时间后会回收，若线程数不够，则新建线程；  
3. Executors.newSingleThreadExecutor：创建单个线程数的线程池，它可以保证先进先出的执⾏顺序；  
4. Executors.newScheduledThreadPool：创建⼀个可以执⾏延迟任务的线程池；  
5. Executors.newSingleThreadScheduledExecutor：创建⼀个单线程的可以执⾏延迟任务的线程池；  
6. Executors.newWorkStealingPool：创建⼀个抢占式执⾏的线程池（任务执⾏顺序不确定）【JDK1.8 添加】。  
7. ThreadPoolExecutor：最原始的创建线程池的⽅式，它包含了 7 个参数可供设置，后⾯会详细讲。    
两种执行方式
```Java
public static class FixedThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        // 方式1
        Future<String> submit = es.submit(() -> Thread.currentThread().getName());
        System.out.println(submit.get());
        // 方式2
        es.execute(() -> System.out.println(Thread.currentThread().getName()));
    }
}
// execute和submit都属于线程池的方法，execute只能提交Runnable类型的任务
// submit既能提交Runnable类型任务也能提交Callable类型任务。
// execute()没有返回值
// submit有返回值，所以需要返回值的时候必须使用submit
```

###### 线程池的状态
- Running 运行中
- ShutDown  关闭
- Stop 停止
- Tidying 整理
- Terminated 结束

###### 线程池中 submit()和 execute()方法有什么区别？
接收的参数不一样
- submit有返回值，而execute没有
- submit方法能进行Exception处理
