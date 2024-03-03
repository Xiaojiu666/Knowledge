### 基础
######	java中的“==”和.equals的区别
对于==
-	基本类型，比如int等，==比较的是值是否相同；
-	引用类型,比如自定义对象：比较地址是否相同；  
尤其地，对常量，由于常量被放在常量池里管理，所以对String等常量，==也是比较值

对于equals 方法  
-	对于String，ArrayList等，equals方法是比较值；
-	但在Object里，equals还是比较地址；
-	如果自己创建了一个类，但没有重写equals方法，还是会比较地址

######	如果两个对象的 hashCode值一样，则它们用equals()比较也是为 true，是不是？
-	hashCode是定义在HashMap里，用以快速索引；
-	Object里，hashCode和equals是两个不同的方法，默认hashCode是返回对象地址，equals方法也是对比地址；
-	两者不是一回事，可以通过重写对象的hashCode方法，让不同值的对象有相同的hashCode，但它们的equals方法未必相同

###### final的作用(不可修改性)
-	修饰在类上，该类不能被继承。
-	修饰在方法上，该方法不能被重写。
-	修饰在变量上，叫常量，该常量必须初始化，初始化之后值就不能被修改，而常量一般全都是用大写来命名。


###### 接口和抽象类有什么区别？
-	抽象类的子类要用extends 来继承；而实现接口要用 implements 。
-	
，人类可以extends动物这个抽象类。
-	而接口是对功能的归纳，比如可以定义一个“提供数据库访问功能”的 接口，在其中封装若干操作数据库的方法。

###### 子类能否重写父类的静态方法
-	父类的静态方法可以被子类继承，但是不能重写。
-	静态方法属于静态绑定,在编译阶段已经确定函数名和地址,静态方法当然是可以被继承的,但是却不能被重写,为什么那?
因为重写的意思是重新定义父类的虚函数,但是虚函数是动态绑定的,而静态方法是静态绑定的,所以静态函数必然不能是虚函数,也就不存在所说的重写了.你在子类中重新写一个同名的函数,覆盖了父类的同名函数,在使用子类指针进行调用的时候,调用的就是子类的这个静态方法


###### final、finally、finalize区别
-	Final
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

###### try{} catch{} finally{} 是如何工作的  
[try{} catch{}](https://blog.csdn.net/qq_24309787/article/details/81304855)

###### static关键字
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


###### Java 中静态方法可以被重写吗？
					严格来说，不存在静态方法的重写，当一个子类继承父类时，写同样的方法时，只是将父类的静态方法隐藏。
					[静态方法能否被重写]（http://xm-king.iteye.com/blog/745787）
					所谓静态，就是在运行时，虚拟机已经认定此方法属于哪个类。 专业术语有严格的含义,用语要准确.。
					"重写"只能适用于实例方法.不能用于静态方法.对于静态方法,只能隐藏。
					 静态方法的调用不需要实例化.. 不实例化也就不能用多态了，也就没有所谓的父类引用指向子类实例.因为不能实例化 也就没有机会去指向子类的实例。所以也就不存在多态了。


###### 两种序列化的方式
-	https://www.jianshu.com/p/a60b609ec7e7
-	编码上：
				Serializable代码量少，写起来方便
				Parcelable代码多一些
-	效率上：
				Parcelable的速度比高十倍以上
serializable的迷人之处在于你只需要对某个类以及它的属性实现Serializable 接口即可。Serializable 接口是一种标识接口（marker interface），这意味着无需实现方法，Java便会对这个对象进行高效的序列化操作。  
这种方法的缺点是使用了反射，序列化的过程较慢。这种机制会在序列化的时候创建许多的临时对象，容易触发垃圾回收。  
Parcelable方式的实现原理是将一个完整的对象进行分解，而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能了  

####	Object中有哪些方法，有什么作用(百度笔试题)
- 1.clone()，保护方法，实现对象的浅复制，
- 2.getClass(),获取类名
- 3.toString（），类名 + 类的 hash地址
- 4.equals（），比较地址值
- 5.finalize 该方法用于释放资源



###### java中有哪些引用方式，请做详细解释(百度)
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

				弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被JVM回收，这个软引用就会被加入到与之关联的引用队列中。

-	虚引用

				虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期。在java中用java.lang.ref.PhantomReference类表示。如果一个对象与虚引用关联，
				则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。
				要注意的是，虚引用必须和引用队列关联使用，当垃圾回收器准备回收一个对象时，
				如果发现它还有虚引用，就会把这个虚引用加入到与之 关联的引用队列中。
				程序可以通过判断引用队列中是否已经加入了虚引用，
				来了解被引用的对象是否将要被垃圾回收。
				如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。


### 字符串基础
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

######	判断下面输出结果
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

######	什么是 String.intern() ？ 何时使用？ 为什么使用 ？
调用str1.intern()，编译器会将"abc"字符串添加到常量池中
[String.intern](https://blog.csdn.net/wqadxmm/article/details/115660378)
[String.intern](https://blog.csdn.net/weixin_44201525/article/details/120897052)



### 对象拷贝
###### 为什么要使用克隆？
用一个对象得到了大量的数据，需要对此处理，但同时又想保存原来的数据，就需要对原数据进行克隆操作。

###### 如何实现对象克隆？
-	1、实现Cloneable接口，并重写其中的clone()方法，在里面定义克隆动作；
- 2、实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆

###### 深拷贝和浅拷贝区别是什么？
-	在进行深拷贝时，会拷贝所有的属性，并且如果这些属性是对象，也会对这些对象进行深拷贝，直到最底层的基本数据类型为止。这意味着，对于深拷贝后的对象，即使原对象的属性值发生了变化，深拷贝后的对象的属性值也不会受到影响。
-	相反，浅拷贝只会拷贝对象的第一层属性，如果这些属性是对象，则不会对这些对象进行拷贝，而是直接复制对象的引用。这意味着，对于浅拷贝后的对象，如果原对象的属性值发生了变化，浅拷贝后的对象的属性值也会跟着发生变化。


###	多态
- Java实现多态有三个必要条件：继承、重写、向上转型。
