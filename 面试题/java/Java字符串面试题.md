#### 什么是String，它是什么数据类型？
String是定义在java.lang包下的一个类，它不是基本数据类型。
String是不可变的，JVM使用字符串池来储存所有的字符串对象。

#### 创建String对象的不同方式有哪些
两种方式。分别是常量池创建与new对象创建
方式一 String s1 = “abc”;
方式二 String s2 = new String(“abc”);

方式一：在创建的时候JVM会先在字符串常量池中检索字符串内容是否存在，若存在则直接返回对应的引用，若不存在则在堆中创建一个String对象，假如其值为abc，同时将该对象的引用缓存在字符串常量池中以便以后重用，接着返回该对象的引用给s1。
方式二：在创建的时候会先在堆中创建一个String对象，假如其值为abc，接着返回该对象的引用给s2。

##### String三大特性
不变性： 是一个 immutable 模式的对象，不变模式的主要作用是当一个对象需要被多线程共享并频繁访问时，可以保证数据的一致性。
常量池优化： String 对象创建之后，会在字符串常量池中进⾏行缓存，下次创建同样的对象时，会直接返回缓存的引用。
final： String 类不可被继承，提⾼了系统的安全性。

##### String 是线程安全的吗
String 是不可变类，一旦创建了String对象，我们就无法改变它的值。因此，它是线程安全的，同一个字符串实例可以被多个线程共享，保证了多线程的安全性

##### 面试题：String、StringBuffer、StringBuilder的区别与联系
（1）相同点：首先说一下相同点：String、StringBuffer 与 StringBuilder 都可以对字符串进行操作。

（2）效率：在字符串操作效率上看 StringBuilder >  StringBuffer  >  String

（3）效率的区别分析：
      String 在设计的时候处于安全和性能的考虑，设置为 final 修饰，长度不可变，每次在常量池新增一个字符串都是重新 new 一个对象，原来的对象没有引用后等待 GC 回收，所以效率比较慢。StringBuilder 和 StringBuffer 都是可变长度的字符串，都继承了 AbstractStringBuilder 。那么造成它们使用区别的原因分析源码可以知道。StringBuffer 的方法都加了 synchronized 同步锁，代表线程安全。而StringBuilder 则没有加锁，所以 StirngBuilder 的效率要优于 StirngBuffer。

（4）三者的使用场景总结：
             如果要操作少量的数据用 = String
            单线程操作字符串缓冲区 下操作大量数据 = StringBuilder（线程非安全）
            多线程操作字符串缓冲区 下操作大量数据 = StringBuffer（有buff就是安全，这个是线程安全的）

（5）使用 StirngBuilder/StringBuffer 的建议 ：
 StirngBuilder/StringBuffer 的构造器会创建一个默认大小（通常是16）的字符数组。在使用中，如果超出这个大小，就会重新分配内存，创建一个更大的数组，并将原先的数组复制过来，再 丢弃旧的数组。在大多数情况下，你可以在创建 StirngBuilder/StringBuffer的时候指定大小，这样就避免了在容量不够的时候自动增长，以提高性能。
如：StringBuffer buffer = new StringBuffer(1000);

##### String str=”i”与 String str=new String(“i”)一样吗？
不一样，因为内存的分配方式不一样。String str="i"的方式，Java 虚拟机会将其分配到常量池中；而 String str=new String("i") 则会被分到堆内存中。
