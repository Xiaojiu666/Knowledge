#### ReentrantLock 
ReentrantLock 是一种可重入锁（Reentrant Lock），它比 synchronized 关键字提供了更灵活的锁机制。重入锁允许线程多次获得同一把锁，而不会造成死锁。以下是对 ReentrantLock 的详细介绍。

###### 特点
-   可重入性：同一个线程可以多次获得同一个锁，而不会陷入死锁。
-   锁获取与释放的灵活性：可以尝试获取锁、超时获取锁、以及中断获取锁。
-   条件变量支持：可以使用 Condition 对象实现更加复杂的线程间协调机制。
-   公平锁与非公平锁：可以选择公平锁（按顺序获得锁）和非公平锁（抢占式获得锁）。

###### 基本用法

```JAVA
class ReentrantLockExample {
    private val lock = ReentrantLock()
    fun criticalSection() {
        lock.lock()
        try {
            // 临界区代码
            println(Thread.currentThread().name + " is in the critical section")
        } finally {
            lock.unlock()
        }
    }
}

```



###### 尝试获取锁
避免死锁：如果多个线程可能会互相等待对方持有的锁，从而导致死锁，那么尝试获取锁可以避免这种情况发生。如果一个线程无法立即获取锁，它可以选择放弃或者执行其他操作，而不是一直等待。

提高系统响应性：在某些实时系统或者高响应性要求的场景中，线程不能无限期地等待锁。使用 tryLock 可以使线程在无法获取锁时执行其他任务，提高系统的整体响应性。

处理超时：在分布式系统中，资源可能会因为网络问题等原因而长时间被占用。使用带有超时的 tryLock 可以让线程在一定时间内尝试获取锁，如果超过这个时间仍无法获取锁，线程可以执行其他操作或者重新尝试。

并行任务协调：在某些并行任务中，某些任务可能需要先获得资源才能执行。通过 tryLock 可以让任务在无法获取资源时，进行其他任务的调度，等待一段时间后再尝试获取资源。

```JAVA
import java.util.concurrent.locks.ReentrantLock;

public class AvoidDeadlockExample {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    public void task1() {
        while (true) {
            try {
                if (lock1.tryLock()) {
                    try {
                        Thread.sleep(50); // 模拟某些工作
                        if (lock2.tryLock()) {
                            try {
                                System.out.println("Task1 acquired both locks");
                                break; // 成功获得锁，退出循环
                            } finally {
                                lock2.unlock();
                            }
                        }
                    } finally {
                        lock1.unlock();
                    }
                }
                // 等待一段时间再重试
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void task2() {
        while (true) {
            try {
                if (lock2.tryLock()) {
                    try {
                        Thread.sleep(50); // 模拟某些工作
                        if (lock1.tryLock()) {
                            try {
                                System.out.println("Task2 acquired both locks");
                                break; // 成功获得锁，退出循环
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } finally {
                        lock2.unlock();
                    }
                }
                // 等待一段时间再重试
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        AvoidDeadlockExample example = new AvoidDeadlockExample();

        Thread t1 = new Thread(example::task1, "Thread 1");
        Thread t2 = new Thread(example::task2, "Thread 2");

        t1.start();
        t2.start();
    }
}

```

