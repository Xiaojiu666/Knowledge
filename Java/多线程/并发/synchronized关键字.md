##### 概念
synchronized 是 Java 中的关键字，是利用锁的机制来实现同步的。
锁机制有如下两种特性：
互斥性：即在同一时间只允许一个线程持有某个对象锁，通过这种特性来实现多线程中的协调机制，这样在同一时间只有一个线程对需同步的代码块(复合操作)进行访问。互斥性我们也往往称为操作的原子性。

可见性：必须确保在锁被释放之前，对共享变量所做的修改，对于随后获得该锁的另一个线程是可见的（即在获得锁时应获得最新共享变量的值），否则另一个线程可能是在本地缓存的某个副本上继续操作从而引起不一致。



#####  同步方法
同步方法使用 synchronized 关键字声明在方法前面，以确保在同一时刻只有一个线程能够执行该方法。如果方法是实例方法，那么锁定的是当前实例对象（this）；如果是静态方法，那么锁定的是整个类对象（Class）。
###### 示例：同步实例方法


在 Java 中，synchronized 是一种用于确保线程安全的关键字。它可以用来锁定方法或代码块，以确保同一时刻只有一个线程能够访问被保护的资源。synchronized 的使用方式主要分为两种：同步方法和同步代码块。

1. 同步方法
同步方法使用 synchronized 关键字声明在方法前面，以确保在同一时刻只有一个线程能够执行该方法。如果方法是实例方法，那么锁定的是当前实例对象（this）；如果是静态方法，那么锁定的是整个类对象（Class）。

示例：同步实例方法
java
复制代码
public class SynchronizedExample {
    private int counter = 0;

    // 同步实例方法
    public synchronized void increment() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }
}

public class Main {
    public static void main(String[] args) {
        SynchronizedExample example = new SynchronizedExample();

        // 创建多个线程来调用 increment 方法
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + example.getCounter());  // 应该输出 2000
    }
}
示例：同步静态方法
java
复制代码
public class SynchronizedStaticExample {
    private static int counter = 0;

    // 同步静态方法
    public static synchronized void increment() {
        counter++;
    }

    public static synchronized int getCounter() {
        return counter;
    }
}

public class Main {
    public static void main(String[] args) {
        // 创建多个线程来调用 increment 方法
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                SynchronizedStaticExample.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                SynchronizedStaticExample.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + SynchronizedStaticExample.getCounter());  // 应该输出 2000
    }
}
2. 同步代码块
同步代码块提供了更细粒度的控制，允许你只在需要的时候锁定特定的对象，而不是锁定整个方法。这样可以减少锁的持有时间，从而提高并发性能。

示例：同步代码块
java
复制代码
public class SynchronizedBlockExample {
    private int counter = 0;
    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            counter++;
        }
    }

    public int getCounter() {
        synchronized (lock) {
            return counter;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SynchronizedBlockExample example = new SynchronizedBlockExample();

        // 创建多个线程来调用 increment 方法
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + example.getCounter());  // 应该输出 2000
    }
}
示例：使用 this 作为锁
java
复制代码
public class SynchronizedThisExample {
    private int counter = 0;

    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    public int getCounter() {
        synchronized (this) {
            return counter;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SynchronizedThisExample example = new SynchronizedThisExample();

        // 创建多个线程来调用 increment 方法
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + example.getCounter());  // 应该输出 2000
    }
}
注意事项
锁的粒度：锁的粒度越小，并发性能越高。但是，粒度太小会增加死锁的风险。因此，需要在性能和安全性之间取得平衡。
避免死锁：当多个线程试图获取多个锁时，需要小心避免死锁。可以通过固定加锁顺序和使用超时锁来减少死锁风险。
可见性：synchronized 确保锁定和解锁之间的所有操作对其他线程可见。因此，它不仅提供了原子性，还提供了内存可见性。
总结
同步方法：使用 synchronized 关键字修饰方法，锁定的是当前对象（实例方法）或类对象（静态方法）。
同步代码块：使用 synchronized 关键字在代码块内锁定特定对象，提供更细粒度的锁控制。
避免死锁：通过设计和编码策略，确保在多线程环境下使用锁时避免死锁。
通过正确使用 synchronized 关键字，可以有效地保护共享资源，确保多线程程序的正确性和安全性。

如果方法是实例方法，那么锁定的是当前实例对象（this）；如果是静态方法，那么锁定的是整个类对象（Class）。 这句话，锁定的概念是什么呢？锁定 实例对象 和 整个类对象又有什么区别呢？
ChatGPT
锁定的概念在多线程编程中非常重要，它决定了哪些线程可以访问被保护的资源。简单来说，锁定是指一个线程获取了某个锁，其他线程在锁被释放之前不能获得该锁。对于 synchronized 关键字来说，锁定的目标（实例对象或类对象）决定了哪个锁被获取。

锁定的概念
锁定实例对象 (this)：当一个线程进入一个同步实例方法时，它会获取当前实例对象的锁，其他任何试图进入同一个实例对象的任何同步方法的线程都会被阻塞，直到这个锁被释放。
锁定类对象 (Class)：当一个线程进入一个同步静态方法时，它会获取该类对象的锁（Class 对象），其他任何试图进入同一个类的任何同步静态方法的线程都会被阻塞，直到这个锁被释放。
锁定实例对象和类对象的区别
锁定实例对象
锁定实例对象意味着锁定的是当前实例（this），因此只影响该实例的同步方法调用。如果有多个实例，每个实例都有自己的锁，多个线程可以同时访问不同实例的同步方法。

示例

java
复制代码
public class InstanceLockExample {
    public synchronized void instanceMethod() {
        System.out.println(Thread.currentThread().getName() + " got instance lock");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " released instance lock");
    }

    public static void main(String[] args) {
        InstanceLockExample instance1 = new InstanceLockExample();
        InstanceLockExample instance2 = new InstanceLockExample();

        Thread t1 = new Thread(instance1::instanceMethod, "Thread 1");
        Thread t2 = new Thread(instance2::instanceMethod, "Thread 2");

        t1.start();
        t2.start();
    }
}
在这个例子中，Thread 1 和 Thread 2 分别锁定 instance1 和 instance2，因此它们可以并发执行 instanceMethod。

锁定类对象
锁定类对象意味着锁定的是整个类（Class 对象），因此影响所有该类的静态同步方法调用。无论有多少个实例，所有实例共享同一个类锁。

示例

java
复制代码
public class ClassLockExample {
    public static synchronized void staticMethod() {
        System.out.println(Thread.currentThread().getName() + " got class lock");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " released class lock");
    }

    public static void main(String[] args) {
        ClassLockExample instance1 = new ClassLockExample();
        ClassLockExample instance2 = new ClassLockExample();

        Thread t1 = new Thread(ClassLockExample::staticMethod, "Thread 1");
        Thread t2 = new Thread(ClassLockExample::staticMethod, "Thread 2");

        t1.start();
        t2.start();
    }
}
在这个例子中，Thread 1 和 Thread 2 都试图获取 ClassLockExample 的类锁，因此它们不能并发执行 staticMethod，只有一个线程能在同一时刻进入该方法。

总结
锁定实例对象：锁定的是具体的对象实例，只有调用该实例的同步方法时，锁才生效。不同实例的同步方法可以并发执行。
锁定类对象：锁定的是整个类，所有实例共享同一个类锁。类的静态同步方法在同一时刻只能由一个线程执行。
通过理解这两种锁定方式的区别，可以更好地设计线程安全的类，避免不必要的阻塞和提高并发性能。