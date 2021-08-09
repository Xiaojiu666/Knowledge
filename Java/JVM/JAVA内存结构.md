###	什么JVM内存结构

![img](https://img-blog.csdnimg.cn/2019040912492050.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Jvbmd0YW91cA==,size_16,color_FFFFFF,t_70)

JVM 的运行时数据区主要包括：**堆、栈、方法区、程序计数器**等。而 JVM 的优化问题主要在**线程共享的数据区**中：**堆、方法区**。



### 程序计数器(线程私有)
  内存里面有上百个寄存器，每一种寄存器的用途都不一样，其中有一个寄存器就是程序计数器。这个寄存器的主要作用就是存放下一条需要执行的指令。

  这是因为我们的处理器在一个时刻，只能执行一个线程中的指令。但是我们的程序往往都是多线程的，这时候处理器就需要来回切换我们的线程，为了在线程切换之后回到之前正确的位置上，此时就需要一个程序计数器，这也就很容易理解了我们的每个线程都有一个自己的程序计数器来保存自己之前的状态。

  接下来如何理解这个程序计数器的功能呢？假如我们的程序代码假如是一行一行执行的，程序计数器永远指向下一行需要执行的字节码指令。在循环结构中，我们就可以改变程序计数器中的值，来指向下一条需要执行的指令。因此，在分支、循环、跳转、异常处理和线程恢复等等一些场景都需要这个程序计数器来完成。


-  程序计数器指定下一条需要执行的指令
-  每一个线程独有一个程序计数器
-  执行java代码时，寄存器保存当前指令地址
-  执行native方法时候，寄存器为空。
-  不会造成OutOfMemoryError情况

###	栈(线程私有)

![img](https://img-blog.csdnimg.cn/20190409163129199.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Jvbmd0YW91cA==,size_16,color_FFFFFF,t_70)

每一个线程都有自己的java虚拟机栈，这个栈与线程同时创建，一个线程中的每个方法从调用直至执行完成的过程，就对应着一个栈帧在虚拟机栈中入栈到出栈的过程。每个线程有一个私有的栈，随着线程的创建而创建。栈里面存着的是一种叫“栈帧”的东西，每个方法会创建一个栈帧，栈帧中存放了局部变量表（基本数据类型和对象引用）、操作数栈、动态连接和返回地址等信息。当前运行方法对应的栈帧叫做当前栈帧。下面主要对这个栈帧进行一个介绍。
栈是**线程私有**的，他的生命周期与线程相同。每个线程都会分配一个栈的空间，即**每个线程拥有独立的栈空间**。

**栈帧**是栈的元素。**每个方法在执行时都会创建一个栈帧**。栈帧中存储了**局部变量表、操作数栈、动态连接和方法出口**等信息。每个方法从调用到运行结束的过程，就对应着一个栈帧在栈中压栈到出栈的过程。

作用：存放java方法执行时的所有的数据
组成：由栈帧组成，一个栈帧代表一个方法的执行

######  局部变量表
栈帧中，由一个局部变量表存储数据。局部变量表中存储了基本数据类型（boolean、byte、char、short、int、float、long、double）的局部变量（包括参数）、和对象的引用（String、数组、对象等），但是不存储对象的内容。局部变量表所需的内存空间在编译期间完成分配，在方法运行期间不会改变局部变量表的大小。

栈帧中，由一个局部变量表存储数据。局部变量表中存储了基本数据类型（boolean、byte、char、short、int、float、long、double）的局部变量（包括参数）、和对象的引用（String、数组、对象等），但是不存储对象的内容。局部变量表所需的内存空间在编译期间完成分配，在方法运行期间不会改变局部变量表的大小。

局部变量的容量以变量槽（Variable Slot）为最小单位，每个变量槽最大存储32位的数据类型。对于64位的数据类型（long、double），JVM 会为其分配两个连续的变量槽来存储。以下简称 Slot 。

JVM 通过索引定位的方式使用局部变量表，索引的范围从0开始至局部变量表中最大的 Slot 数量。普通方法与 static 方法在第 0 个槽位的存储有所不同。非 static 方法的第 0 个槽位存储方法所属对象实例的引用。
https://img-blog.csdnimg.cn/20190410183454222.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Jvbmd0YW91cA==,size_16,color_FFFFFF,t_70

    Slot 复用？
为了尽可能的节省栈帧空间，局部变量表中的 Slot 是可以复用的。方法中定义的局部变量，其作用域不一定会覆盖整个方法。当方法运行时，如果已经超出了某个变量的作用域，即变量失效了，那这个变量对应的 Slot 就可以交给其他变量使用，也就是所谓的 Slot 复用。通过一个例子来理解变量“失效”
```java
public void test(boolean flag)
{
    if(flag)
    {
        int a = 66;
    }

    int b = 55;
}
```
当虚拟机运行 test 方法，就会创建一个栈帧，并压入到当前线程的栈中。当运行到 int a = 66时，在当前栈帧的局部变量中创建一个 Slot 存储变量 a，当运行到 int b = 55时，此时已经超出变量 a 的作用域了（变量 a 的作用域在{}所包含的代码块中），此时 a 就失效了，变量a 占用的 Slot 就可以交给b来使用，这就是 Slot 复用。

凡事有利弊。Slot 复用虽然节省了栈帧空间，但是会伴随一些额外的副作用。比如，Slot 的复用会直接影响到系统的垃圾收集行为。
```java
public class TestDemo {

    public static void main(String[] args){

        byte[] placeholder = new byte[64 * 1024 * 1024];

        System.gc();
    }
}
```
https://img-blog.csdnimg.cn/20190410185953669.png
可以看到虚拟机没有回收这 64M 内存。为什么没有被回收？其实很好理解，当执行 System.gc() 方法时，变量 placeholder 还在作用域范围之内，虚拟机是不会回收的，它还是“有效”的。

我们对上面的代码稍作修改，使其作用域“失效”。
```java
public class TestDemo {
    public static void main(String[] args){
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        System.gc();
    }
}
```
当运行到 System.gc() 方法时，变量 placeholder 的作用域已经失效了。它已经“无用”了，虚拟机会回收它所占用的内存了吧？
运行结果：
https://img-blog.csdnimg.cn/20190410190827875.png

发现虚拟机还是没有回收 placeholder 变量占用的 64M 内存。为什么所想非所见呢？在解释之前，我们再对代码稍作修改。在System.gc()方法执行之前，加入一个局部变量。

```java
public class TestDemo {

    public static void main(String[] args){
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }
}
```
在 System.gc() 方法之前，加入 int a = 0，再执行方法，查看垃圾回收情况。
https://img-blog.csdnimg.cn/20190410191854837.png

发现 placeholder 变量占用的64M内存空间被回收了，如果不理解局部变量表的Slot复用，很难理解这种现象的。

而 placeholder 变量能否被回收的关键就在于：局部变量表中的 Slot 是否还存有关于 placeholder 对象的引用。

第一次修改中，限定了 placeholder 的作用域，但之后并没有任何对局部变量表的读写操作，placeholder 变量在局部变量表中占用的Slot没有被其它变量所复用，所以作为 GC Roots 一部分的局部变量表仍然保持着对它的关联。所以 placeholder 变量没有被回收。

第二次修改后，运行到 int a = 0 时，已经超过了 placeholder 变量的作用域，此时 placeholder 在局部变量表中占用的Slot可以交给其他变量使用。而变量a正好复用了 placeholder 占用的 Slot，至此局部变量表中的 Slot 已经没有 placeholder 的引用了，虚拟机就回收了placeholder 占用的 64M 内存空间。

######	操作数表
操作数栈是一个后进先出栈。操作数栈的元素可以是任意的Java数据类型。方法刚开始执行时，操作数栈是空的，在方法执行过程中，通过字节码指令对操作数栈进行压栈和出栈的操作。通常进行算数运算的时候是通过操作数栈来进行的，又或者是在调用其他方法的时候通过操作数栈进行参数传递。操作数栈可以理解为栈帧中用于计算的临时数据存储区。

通过一段代码来了解操作数栈。

```Java
public class OperandStack{

    public static int add(int a, int b){
        int c = a + b;
        return c;
    }

    public static void main(String[] args){
        add(100, 98);
    }
}
```
使用 javap 反编译 OperandStack 后，根据虚拟机指令集，得出操作数栈的运行流程如下：
https://img-blog.csdnimg.cn/20190409231224263.png
add 方法刚开始执行时，操作数栈是空的。当执行 iload_0 时，把局部变量 0 压栈，即 100 入操作数栈。然后执行 iload_1，把局部变量1压栈，即 98 入操作数栈。接着执行 iadd，弹出两个变量（100 和 98 出操作数栈），对 100 和 98 进行求和，然后将结果 198 压栈。然后执行 istore_2，弹出结果（出栈）。

下面通过一张图，对比执行100+98操作，局部变量表和操作数栈的变化情况。
https://img-blog.csdnimg.cn/20190409205401344.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Jvbmd0YW91cA==,size_16,color_FFFFFF,t_70

对于java虚拟机栈的描述，最后看一下可能发生的异常情况：

- 如果线程请求分配的栈容量超过java虚拟机栈所允许的最大容量，java虚拟机就会抛出StackOverfolwError
- 如果java虚拟机栈动态扩展，在扩展时没有申请到足够的内存或者是创建新线程时没有足够的内存再创建java虚拟机栈了，那么java虚拟机就会抛出outOfMemoryError

###### 本地方法栈
与虚拟机栈类似，区别是虚拟机栈执行java方法，本地方法站执行native方法。在虚拟机规范中对本地方法栈中方法使用的语言、使用方法与数据结构没有强制规定，因此虚拟机可以自由实现它。本地方法栈可以抛出StackOverflowError和OutOfMemoryError异常。不过这块区域我们不怎么去关心。

###### 内存堆
Java堆是被所有线程共享的一块内存区域，在虚拟机启动时创建，用来存放对象实例。是内存中最大的一块区域。垃圾收集器（GC）在该区域回收不使用的对象的内存空间。但是并不是所有的对象都在这保存，深入理解java虚拟机中说道，随着JIT编译器的发展和逃逸分析技术逐渐成熟，栈上分配、标量调换优化技术将会导致一些微妙的变化，所有的对象都分配在堆上也逐渐变得不那么绝对了。
Java堆分为年轻代（Young Generation）和老年代（Old Generation）；年轻代又分为伊甸园（Eden）和幸存区（Survivor区）；幸存区又分为From Survivor空间和 To Survivor空间。
年轻代存储“新生对象”，我们新创建的对象存储在年轻代中。当年轻内存占满后，会触发Minor GC，清理年轻代内存空间。

老年代存储长期存活的对象和大对象。年轻代中存储的对象，经过多次GC后仍然存活的对象会移动到老年代中进行存储。老年代空间占满后，会触发Full GC。

注：Full GC是清理整个堆空间，包括年轻代和老年代。如果Full GC之后，堆中仍然无法存储对象，就会抛出OutOfMemoryError异常。


###### 方法区
法区同 Java 堆一样是被所有线程共享的区间，用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码。更具体的说，静态变量+常量+类信息（版本、方法、字段等）+运行时常量池存在方法区中。常量池是方法区的一部分。

常量池
优点：常量池避免了频繁的创建和销毁对象而影响系统性能，其实现了对象的共享。

举个栗子： Integer 常量池（缓存池），和字符串常量池
- Integer常量池：
```Java
public void TestIntegerCache()
{
    public static void main(String[] args)
    {

        Integer i1 = new Integer(66);
        Integer i2 = new integer(66);
        Integer i3 = 66;
        Integer i4 = 66;
        Integer i5 = 150;
        Integer i6 = 150;
        //基本数据类型比较的是数值，而引用数据类型比较的是内存地址。
        System.out.println(i1 == i2);//false
        // Integer i3 = 66 实际上有一步装箱的操作，即将 int 型的 66 装箱成 Integer，通过 Integer 的 valueOf 方法。
        System.out.println(i3 == i4);//true
        System.out.println(i5 == i6);//false
    }    
      //装箱  IntegerCache 的最小值（-128）和最大值（127）之间
      public static Integer valueOf(int i) {
          if (i >= IntegerCache.low && i <= IntegerCache.high)
              return IntegerCache.cache[i + (-IntegerCache.low)];
          return new Integer(i);
      }
}
```
再看一段拆箱的代码。
```Java
// 由于 i1 和 i2 是 Integer 对象，是不能使用+运算符的。首先 i1 和 i2 进行自动拆箱操作，拆箱成int后再进行数值加法运算。i3 也是拆箱后再与之比较数值是否相等的。所以 i3 == i1+i2 其实是比较的 int 型数值是否相等，所以为true。
public static void main(String[] args){
       Integer i1 = new Integer(4);
       Integer i2 = new Integer(6);
       Integer i3 = new Integer(10);
       System.out.print(i3 == i1+i2);//true
    }
```
- String常量池
```Java
  //1、 new 创建的对象，存放在堆中。每次调用都会创建一个新的对象。
  String str = new String("abcd");

  //2、先在栈上创建一个 String 类的对象引用变量 str，然后通过符号引用去字符串常量池中找有没有 “abcd”，如果没有，则将“abcd”存放到字符串常量池中，并将栈上的 str 变量引用指向常量池中的“abcd”。如果常量池中已经有“abcd”了，则不会再常量池中创建“abcd”，而是直接将 str 引用指向常量池中的“abcd”。
  String str = "abcd";

  public static void main(String[] args){
       String str1 = "abcd";
       String str2 = "abcd";
       String strN1 = new String("abcd");
       String strN2 = new String("abcd");
       //常量池中同一个 对象
       System.out.print(str1 == str2);//true
       //分别在堆上创建了不同的对象。两个引用指向堆中两个不同的对象，所以为 false。
       System.out.print(strN1 == strN2);// false
    }
```
关于字符串 + 号连接问题：
```Java
  {
  //在程序编译期，JVM就会将其优化为 + 号连接后的值。所以在编译期其字符串常量的值就确定了。
  String a = "a1";   
  String b = "a" + 1;   
  System.out.println((a == b)); //result = true  

  String a = "atrue";   
  String b = "a" + "true";   
  System.out.println((a == b)); //result = true

  String a = "a3.4";   
  String b = "a" + 3.4;   
  System.out.println((a == b)); //result = true
}
```
关于字符串引用 + 号连接问题：
```Java
// str3 等于 str1 引用 + 字符串常量“b”，在编译期无法确定，在运行期动态的分配并将连接后的新地址赋给 str3，所以 str2 和 str3 引用的内存地址不同，所以 str2 == str3 结果为 false
public static void main(String[] args){
     String str1 = "a";
	   String str2 = "ab";
	   String str3 = str1 + "b";
	   System.out.print(str2 == str3);//false
    }
    经过 jad 反编译工具反编译代码后，代码如下
    //发现 new 了一个 StringBuilder 对象，然后使用 append 方法优化了 + 操作符。
public static void main(String args[])
      {
            String s = "a";
            String s1 = "ab";
            String s2 = (new StringBuilder()).append(s).append("b").toString();
            System.out.print(s1 = s2);
        }
```
使用final修饰的字符串
```Java
//final 修饰的变量是一个常量，编译期就能确定其值。所以 str1 + "b"就等同于 "a" + "b"，所以结果是 true。
public static void main(String[] args){
        final String str1 = "a";
        String str2 = "ab";
        String str3 = str1 + "b";
        System.out.print(str2 == str3);//true
    }
```
String对象的intern方法。
```Java
public static void main(String[] args){
        String s = "ab";
        String s1 = "a";
        String s2 = "b";
        String s3 = s1 + s2;
        // s1+s2 实际上在堆上 new 了一个 StringBuilder 对象，而 s 在常量池中创建对象 “ab”，所以 s3 == s 为 false。但是 s3 调用 intern 方法，返回的是s3的内容（ab）在常量池中的地址值。所以 s3.intern() == s 结果为 true。
        System.out.println(s3 == s);//false
        System.out.println(s3.intern() == s);//true
    }
```


### 参考资料
- [一文搞懂JVM内存结构](https://blog.csdn.net/rongtaoup/article/details/89142396)
- [JVM系列（1）java8的内存结构](https://zhuanlan.zhihu.com/p/68145978)
- [学习java应该如何理解反射？](https://www.zhihu.com/question/24304289)
