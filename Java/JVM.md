##	JVM内存管理

![img](https://img-blog.csdnimg.cn/2019040912492050.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Jvbmd0YW91cA==,size_16,color_FFFFFF,t_70)

JVM 的运行时数据区主要包括：**堆、栈、方法区、程序计数器**等。而 JVM 的优化问题主要在**线程共享的数据区**中：**堆、方法区**。



#### 栈：

![img](https://img-blog.csdnimg.cn/20190409163129199.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Jvbmd0YW91cA==,size_16,color_FFFFFF,t_70)

栈是**线程私有**的，他的生命周期与线程相同。每个线程都会分配一个栈的空间，即**每个线程拥有独立的栈空间**。

**栈中存储的是什么？**

**栈帧**是栈的元素。**每个方法在执行时都会创建一个栈帧**。栈帧中存储了**局部变量表、操作数栈、动态连接和方法出口**等信息。每个方法从调用到运行结束的过程，就对应着一个栈帧在栈中压栈到出栈的过程。

作用：存放java方法执行时的所有的数据
组成：由栈帧组成，一个栈帧代表一个方法的执行





()https://blog.csdn.net/rongtaoup/article/details/89142396



