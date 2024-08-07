
#### 对象基础

##### 接口定义
接口旨在定义一组方法供实现类去实现。这些方法是行为的规范，而非状态的存储。接口应该是无状态的，仅仅提供行为契约。
变量代表的是状态，而接口代表的是行为契约。将状态和行为分离是面向对象设计的一个基本原则。状态应由具体的实现类来管理，而不是接口。


##### 基础数据类型
###### byte
位数: 8 位（1 字节）
范围: -128 到 127
###### short

位数: 16 位（2 字节）
范围: -32,768 到 32,767
###### int

位数: 32 位（4 字节）
范围: -2^31 到 2^31 - 1（-2,147,483,648 到 2,147,483,647）
###### long
位数: 64 位（8 字节）
范围: -2^63 到 2^63 - 1（-9,223,372,036,854,775,808 到 9,223,372,036,854,775,807）

###### float
位数: 32 位（4 字节）
范围: 大约 -3.40282347E+38 到 3.40282347E+38，精度约为 7 位十进制数字
###### double
位数: 64 位（8 字节）
范围: 大约 -1.7976931348623157E+308 到 1.7976931348623157E+308，精度约为 15 位十进制数字
###### char
位数: 16 位（2 字节）
范围: 0 到 65,535（Unicode 字符集中的字符）
boolean

位数: 在实际 JVM 中，boolean 类型的内存占用可能不固定。它通常是一个字节（8 位），但在集合或数组中，JVM 实现可能会进行优化。概念上，它有两个可能的值：true 和 false。

##### 创建一个对象的过程
类加载检查：检查并加载类。
- JVM 检查 Person 类是否已加载。
- 如果未加载，通过类加载器加载 Person 类。
- 类加载过程包括类的验证、准备、解析和初始化。   

分配内存：在堆中分配内存。
-	JVM 在堆内存中分配一块足够大的内存空间，用于存储 Person 对象。
-	这块内存被标记为未初始化状态。

初始化默认值：初始化为默认值。
-	name 字段被初始化为 null。
-	age 字段被初始化为 0。

调用构造函数：设置实际值。
-	JVM 调用 Person 类的构造函数，并将 "Alice" 和 30 作为参数传递。
-	在构造函数内部，name 字段被赋值为 "Alice"，age 字段被赋值为 30。

返回引用：返回对象引用。
-	构造函数执行完毕后，JVM 返回 Person 对象的引用。
- 	这个引用被赋值给变量 person。  

#####	如果两个对象的 hashCode值一样，则它们用equals()比较也是为true么
-	如果两个对象用 equals 比较相等，它们的 hashCode 值必须相同。
-	当覆盖 equals 方法时，应该相应地覆盖 hashCode 方法，以确保对象的一致性，特别是在使用哈希集合（如 HashMap 或 HashSet）时。

#####	Object中有哪些方法，有什么作用(百度笔试题)
- 1.clone()，保护方法，实现对象的浅复制，
- 2.getClass(),获取类名
- 3.toString（），类名 + 类的 hash地址
- 4.equals（），比较地址值
- 5.finalize 该方法用于释放资源


##### 为什么要使用克隆？
用一个对象得到了大量的数据，需要对此处理，但同时又想保存原来的数据，就需要对原数据进行克隆操作。

##### 如何实现对象克隆？
-	1、实现Cloneable接口，并重写其中的clone()方法，在里面定义克隆动作；
- 2、实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆

##### 深拷贝和浅拷贝区别是什么？
-	在进行深拷贝时，会拷贝所有的属性，并且如果这些属性是对象，也会对这些对象进行深拷贝，直到最底层的基本数据类型为止。这意味着，对于深拷贝后的对象，即使原对象的属性值发生了变化，深拷贝后的对象的属性值也不会受到影响。
-	相反，浅拷贝只会拷贝对象的第一层属性，如果这些属性是对象，则不会对这些对象进行拷贝，而是直接复制对象的引用。这意味着，对于浅拷贝后的对象，如果原对象的属性值发生了变化，浅拷贝后的对象的属性值也会跟着发生变化。

##### 接口和抽象类有什么区别？
-	接口：适用于定义行为规范，可以被多个不相关的类实现，提供多重继承能力。
	接口强调行为的规范，提供多重继承的能力。
-	抽象类：适用于定义一组通用行为和特性，提供基本实现，同时强制子类实现特定方法，适用于类之间有较强关系的情况。
	抽象类则强调类的层次结构，提供部分实现和子类共享的行为。

#### 关键字基础
##### final、finally、finalize区别
-	final
			用于修饰类、成员变量和成员方法。
			final修饰的类，不能被继承（String、StringBuilder、StringBuffer、Math，不可变类），
			其中所有的方法都不能被重写，所以不能同时用abstract和final修饰类（abstract修饰的类是抽象类，
			抽象类是用于被子类继承的，和final起相反的作用）。
			Final修饰的方法不能被重写，但是子类可以用父类中final修饰的方法；
			Final修饰的成员变量是不可变的，如果成员变量是基本数据类型，初始化之后成员变量的值不能被改变，
			如果成员变量是引用类型，那么它只能指向初始化时指向的那个对象，不能再指向别的对象，
			但是对象当中的内容是允许改变的。
-	finally
	 		Finally通常和try catch搭配使用，保证不管有没有发生异常，
			资源都能够被释放（释放连接、关闭IO流）。
-	finaliz
	  	Finalize是object类中的一个方法，子类可以重写finalize()方法实现对资源的回收。
			垃圾回收只负责回收内存，并不负责资源的回收，资源回收要由程序员完成，
			Java虚拟机在垃圾回收之前会先调用垃圾对象的finalize方法用于使对象释放资源
			（如关闭连接、关闭文件），之后才进行垃圾回收，这个方法一般不会显示的调用，
			在垃圾回收时垃圾回收器会主动调用。

##### try{} catch{} finally{} 是如何工作的  
[try{} catch{}](https://blog.csdn.net/qq_24309787/article/details/81304855)

##### static关键字
static是Java里的非访问修饰符，它可以用来创建类方法和类变量。
-	修饰方法
						当修饰一个方法的时候，此方法就成了独立于对象的静态方法，
						静态方法不能使用类的非静态变量，因为静态方法和静态变量先于非静态的其他成员初始化，
						静态方法先出来，然后才是非静态的，所以明白这个顺序很重要。
						静态方法从参数列表得到数据，然后计算这些数据。
						修饰方法时，此方法能够不用在初始化对象的前提下直接调用，即，可以直接通过类名.static方法（）这样来访问。

- 修饰变量
					当修饰一个变量的时候，此变量就成了独立于对象的静态变量，无论一个类实例化了多少个对象
					，这个类只有一份这个静态变量的拷贝，所以static修饰的变量，即静态变量，
					也被叫做类变量。一个局部变量不能被声明为static变量。
					修饰变量时，这个变量只在内存中有一个副本。

-	修饰代码块
					这部分代码块只会被执行一次。

##### Java 中静态方法可以被重写吗？
严格来说，不存在静态方法的重写，当一个子类继承父类时，写同样的方法时，只是将父类的静态方法隐藏。
[静态方法能否被重写]（http://xm-king.iteye.com/blog/745787）
					所谓静态，就是在运行时，虚拟机已经认定此方法属于哪个类。 专业术语有严格的含义,用语要准确.。
					"重写"只能适用于实例方法.不能用于静态方法.对于静态方法,只能隐藏。
					 静态方法的调用不需要实例化.. 不实例化也就不能用多态了，也就没有所谓的父类引用指向子类实例.因为不能实例化 也就没有机会去指向子类的实例。所以也就不存在多态了。


### 字符串基础
#####	java中的“==”和.equals的区别
对于==
-	基本类型，比如int等，==比较的是值是否相同；
-	引用类型,比如自定义对象：比较地址是否相同；  
尤其地，对常量，由于常量被放在常量池里管理，所以对String等常量，==也是比较值

对于equals 方法  
-	对于String，ArrayList等，equals方法是比较值；
-	但在Object里，equals还是比较地址；
-	如果自己创建了一个类，但没有重写equals方法，还是会比较地址

##### String、StringBuffer和StringBuilder
-	String是不可变类，每次操作都会生成新的String对象，并将结果指针指向新的对象，由此会产生内存碎片。如果要频繁对字符串修改，建议采用StringBuffer 和 StringBuilder。
-	StringBuffer 和 StringBuilder的差别在于，StringBuffer 是线程安全的，而 StringBuilder 是非线程安全的，由于无需维护线程安全的操作，所以StringBuilder 的性能要高于 StringBuffer，所以在单线程环境下推荐使用 StringBuilder，多线程环境下推荐使用 StringBuffer。由于大多数环境下是单线程，所以大多是用 StringBuilder。
-	StringBuffer 使用 `synchronized` 关键字实现线程同步

##### String str="abc"与 String str=new String(“abc”)的定义方法一样吗？
不一样  
-	String str="abc"的方式，java 虚拟机会将其分配到常量池中；所以建议这种写法。
-	String str=new String(“abc”) 则会被分到堆内存中，如果再频繁修改，会导致内存碎片。

##### String 类的常用方法都有那些？
- indexOf()：返回指定字符的索引。
- length()：返回字符串长度。
- equals()：字符串比较。
- replace()：字符串替换。
- trim()：去除字符串两端空白。
- toLowerCase()：将字符串转成小写字母。
- split()：分割字符串，返回一个分割后的字符串数组。
- toUpperCase()：将字符串转成大写字符。
- substring()：截取字符串。

#####	判断下面输出结果
```Java
			String a = new String("myString");
			String b = "myString";
			String c = "my" + "String";
			String d = c;
			System.out.print(a == b); false
			System.out.print(a == c); f
			System.out.print(b == c);	t
			System.out.print(b == d);	t
```

#####	什么是 String.intern() ？ 何时使用？ 为什么使用 ？
调用str1.intern()，编译器会将"abc"字符串添加到常量池中
[String.intern](https://blog.csdn.net/wqadxmm/article/details/115660378)
[String.intern](https://blog.csdn.net/weixin_44201525/article/details/120897052)

##### 什么是String，它是什么数据类型？
String是定义在java.lang包下的一个类，它不是基本数据类型。
String是不可变的，JVM使用字符串池来储存所有的字符串对象。

##### 创建String对象的不同方式有哪些
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



##### 两种序列化的方式
-	https://www.jianshu.com/p/a60b609ec7e7
-	编码上：
				Serializable代码量少，写起来方便
				Parcelable代码多一些
-	效率上：
				Parcelable的速度比高十倍以上
serializable的迷人之处在于你只需要对某个类以及它的属性实现Serializable 接口即可。Serializable 接口是一种标识接口（marker interface），这意味着无需实现方法，Java便会对这个对象进行高效的序列化操作。  
这种方法的缺点是使用了反射，序列化的过程较慢。这种机制会在序列化的时候创建许多的临时对象，容易触发垃圾回收。  
Parcelable方式的实现原理是将一个完整的对象进行分解，而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能了  




##### java中有哪些引用方式，请做详细解释(百度)
-	强引用  
			是指创建一个对象并把这个对象赋给一个引用变量
			比如：
			Object object =new Object();
			String str ="hello";
			当运行至Object[] objArr = new Object[1000];这句时，如果内存不足，JVM会抛出OOM错误也不会回收object指向的对象。
			如果想中断强引用和某个对象之间的关联，可以显示地将引用赋值为null，这样一来的话，JVM在合适的时间就会回收该对象。
			不过要注意的是，当fun1运行完之后，object和objArr都已经不存在了，所以它们指向的对象都会被JVM回
			收。

-	软引用
			如果一个对象具有软引用，内存空间足够，垃圾回收器就不会回收它
			如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。
			软引用可用来实现内存敏感的高速缓存,比如网页缓存、图片缓存等。使用软引用能防止内存泄露，增强程序的健壮性。
			SoftReference的特点是它的一个实例保存对一个Java对象的软引用， 该软引用的存在不妨碍垃圾收集线程对该Java对象的回收。
			也就是说，一旦SoftReference保存了对一个Java对象的软引用后，在垃圾线程对 这个Java对象回收前，SoftReference类所提供的get()方法返回Java对象的强引用。
			另外，一旦垃圾线程回收该Java对象之 后，get()方法将返回null。
			MyObject aRef = new  MyObject();    			//强引用  
			SoftReference aSoftRef=new SoftReference(aRef); //弱引用    
    
			此时，对于这个MyObject对象，有两个引用路径，一个是来自SoftReference对象的软引用，
			一个来自变量aReference的强引用，所以这个MyObject对象是强可及对象。

			MyObject anotherRef=(MyObject)aSoftRef.get();  //获取若引用的对象
			重新获得对该实例的强引用。而回收之后，调用get()方法就只能得到null了。

			作为一个Java对象，SoftReference对象除了具有保存软引用的特殊性之外，也具有Java对象的一般性。
			所以，当软可及对象被回收之后，虽然这个SoftReference对象的get()方法返回null,
			但这个SoftReference对象已经不再具有存在的价值，需要一个适当的清除机制，避免大量SoftReference对象带来的内存泄漏。在java.lang.ref包里还提供了ReferenceQueue。

			ReferenceQueue queue = new  ReferenceQueue()		//ReferenceQueue中保存的对象是Reference对象，而且是已经失去了它所软引用的对象的Reference对象
			SoftReference  ref=new  SoftReference(aMyObject, queue);  //



-	弱引用  
			弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。在java中，用java.lang.ref.WeakReference类来表示。下面是使用示例：
```java
				WeakReference<People>reference=new WeakReference<People>(new People("zhouqian",20));  
		        System.out.println(reference.get());  
		        System.gc();//通知GVM回收资源  
		        System.out.println(reference.get());
				结果：
				[name:zhouqian,age:20]
				null

				第二个输出结果是null，这说明只要JVM进行垃圾回收，被弱引用关联的对象必定会被回收掉。不过要注意的是，这里所说的被弱引用关联的对象是指只有弱引用与之关联，如果存在强引用同时与之关联，则进行垃圾回收时也不会回收该对象（软引用也是如此）。

				People people=new People("zhouqian",20);  
		        WeakReference<People>reference=new WeakReference<People>(people);
		        System.out.println(reference.get());  
		        System.gc();  
		        System.out.println(reference.get());  
				结果
				[name:zhouqian,age:20]  
				[name:zhouqian,age:20]
```
				弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被JVM回收，这个软引用就会被加入到与之关联的引用队列中。

-	虚引用

				虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期。在java中用java.lang.ref.PhantomReference类表示。如果一个对象与虚引用关联，
				则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。
				要注意的是，虚引用必须和引用队列关联使用，当垃圾回收器准备回收一个对象时，
				如果发现它还有虚引用，就会把这个虚引用加入到与之 关联的引用队列中。
				程序可以通过判断引用队列中是否已经加入了虚引用，
				来了解被引用的对象是否将要被垃圾回收。
				如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。









###	多态
- Java实现多态有三个必要条件：继承、重写、向上转型。
