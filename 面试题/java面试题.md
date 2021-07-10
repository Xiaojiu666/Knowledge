####	1.java中的“==”和.equals的区别
-	==运算符(对比对象的地址值是否相同)，.equals是方法(只有String类型对比的是内容,因为String重写了equals方法)

####	2.int、char、long各占多少字节数
-	整型 int 4字节  			
-	长整型 long 4字节
-	字符型 char 1字节
-	单精度 float 4字节
-	双精度 double 8字节
-	长双精度 long double 8字节

####	3.java八大数据类型
-	名字			字节
- char			1
- int			4
- boolean		
- double		8
- float			4
- long			8
- short			2
- byte			1

####	4.int和Integer的区别
-	Integer是int的包装类，int则是java的一种基本数据类型
-	Integer变量必须实例化后才能使用，而int变量不需要
-	Integer的默认值是null，int的默认值是0
-	Integer实际是对象的引用，当new一个Integer时，实际上是生成一个指针指向此对象；而int则是直接存储数据值
-	由于Integer变量实际上是对一个Integer对象的引用，所以两个通过new生成的Integer变量永远是不相等的（因为new生成的是两个对象，其内存地址不同）。当使用==时，发现其内存地址不同，所以进行==返回false
-	Integer变量和int变量比较时，只要两个变量的值是向等的，则结果为true（因为包装类Integer和基本数据类型int比较时，java会自动拆包装为int，然后进行比较，实际上就变为两个int变量的比较）
-	非new生成的Integer变量和new Integer()生成的变量比较时，结果为false。（因为非new生成的Integer变量指向的是java常量池中的对象，而new Integer()生成的变量指向堆中新建的对象，两者在内存中的地址不同）
-	两个不是new出来的Integer，而是诸如Integer i = x，如果x的范围在-128～127，因为如下语句： Integer i5 = 127;//java在编译的时候,被翻译成-> Integer i5 = Integer.valueOf(127); 调用了Integer.valueOf (), 这会将127缓存，下次我们写：Integer i6 = 127;时，i6指向缓存中的同一个对象。所以此时i5==i6是true； 当x范围在-128～127之外，没有缓存存在，即使他们包裹的数值相等，他们也不能使用==的到true。
-	int和Integer的比较，无论Integer是否使用new，其值和将和平常的预料的一样:Integer自动拆箱，然后和int比较数值.这里就不会被内存地址的不同所影响，该相等时就相等。
-	问题：https://blog.csdn.net/xingxinghann/article/details/80336644

####	5.多态
- Java实现多态有三个必要条件：继承、重写、向上转型。


####	6.String，StringBuffer和StringBuilder的区别
- String类对象内容不能修改，但并不代表其引用不能改变，下面通过内存的分配图说明字符串不可改变的真正含义：
	- ![](http://img.blog.csdn.net/20160320090807976?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
- 在这方面运行速度快慢为：StringBuilder > StringBuffer > String
- 在线程安全上，StringBuilder是线程不安全的，而StringBuffer是线程安全的
	- String：适用于少量的字符串操作的情况
	-　　StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况
	-　　StringBuffer：适用多线程下在字符缓冲区进行大量操作的情况


####	什么是 String.intern() ？ 何时使用？ 为什么使用 ？
-	https://blog.csdn.net/u013366617/article/details/83618361

####	指出下列程序运行的结果
		public class Example {
			String str = new String("good");
			char[] ch = {'a','b','c'};

			public static void main(String[]args) {
			Example ex = new test2();
			ex.change(ex.str,ex.ch);
			System.out.print(ex.str+" and ");
			System.out.println(ex.ch);
			}

			public void change(String str,char[] ch) {
			str = "test ok";
			ch[0] = 'g';
			}
		}

		good and gbc


####	判断下面输出结果
			String a = new String("myString");
			String b = "myString";
			String c = "my" + "String";
			String d = c;
			System.out.print(a == b); false
			System.out.print(a == c); f
			System.out.print(b == c);	t
			System.out.print(b == d);	t

####	输出值
		递归时 ，一定要看清楚条件
		split（12）
		public static int split(int number) {
			 if (number > 1){
					 if (number % 2 != 0) System.out.print(split((number + 1) / 2));
					 System.out.print(split(number / 2));
			 }
			 return number;
		}
		12136




####	7.泛型通配符extends与super的区别
-	1.<? extends T> 只能用于方法返回，告诉编译器此返参的类型的最小继承边界为T，T和T的父类都能接收，但是入参类型无法确定，只能接受null的传入
	-
-	2.<? super T>只能用于限定方法入参，告诉编译器入参只能是T或其子类型，而返参只能用Object类接收? 既不能用于入参也不能用于返参

####	8.子类能否重写父类的静态方法
-	父类的静态方法可以被子类继承，但是不能重写。
-	静态方法属于静态绑定,在编译阶段已经确定函数名和地址,静态方法当然是可以被继承的,但是却不能被重写,为什么那?
因为重写的意思是重新定义父类的虚函数,但是虚函数是动态绑定的,而静态方法是静态绑定的,所以静态函数必然不能是虚函数,也就不存在所说的重写了.你在子类中重新写一个同名的函数,覆盖了父类的同名函数,在使用子类指针进行调用的时候,调用的就是子类的这个静态方法

####	9什么是进程和线程
-	CPU+RAM+各种资源（比如显卡，光驱，键盘，GPS, 等等外设）构成我们的电脑，但是电脑的运行，实际就是CPU和相关寄存器以及RAM之间的事情。一个最最基础的事实：CPU太快，太快，太快了，寄存器仅仅能够追的上他的脚步，RAM和别的挂在各总线上的设备完全是望其项背。那当多个任务要执行的时候怎么办呢？轮流着来?或者谁优先级高谁来？不管怎么样的策略，一句话就是在CPU看来就是轮流着来。一个必须知道的事实：执行一段程序代码，实现一个功能的过程介绍 ，当得到CPU的时候，相关的资源必须也已经就位，就是显卡啊，GPS啊什么的必须就位，然后CPU开始执行。这里除了CPU以外所有的就构成了这个程序的执行环境，也就是我们所定义的程序上下文。当这个程序执行完了，或者分配给他的CPU执行时间用完了，那它就要被切换出去，等待下一次CPU的临幸。在被切换出去的最后一步工作就是保存程序上下文，因为这个是下次他被CPU临幸的运行环境，必须保存。串联起来的事实：前面讲过在CPU看来所有的任务都是一个一个的轮流执行的，具体的轮流方法就是：先加载程序A的上下文，然后开始执行A，保存程序A的上下文，调入下一个要执行的程序B的程序上下文，然后开始执行B,保存程序B的上下文。。。。========= 重要的东西出现了========进程和线程就是这样的背景出来的，两个名词不过是对应的CPU时间段的描述，名词就是这样的功能。进程就是包换上下文切换的程序执行时间总和 = CPU加载上下文+CPU执行+CPU保存上下文线程是什么呢？进程的颗粒度太大，每次都要有上下的调入，保存，调出。如果我们把进程比喻为一个运行在电脑上的软件，那么一个软件的执行不可能是一条逻辑执行的，必定有多个分支和多个程序段，就好比要实现程序A，实际分成 a，b，c等多个块组合而成。那么这里具体的执行就可能变成：程序A得到CPU =》CPU加载上下文，开始执行程序A的a小段，然后执行A的b小段，然后再执行A的c小段，最后CPU保存A的上下文。这里a，b，c的执行是共享了A的上下文，CPU在执行的时候没有进行上下文切换的。这里的a，b，c就是线程，也就是说线程是共享了进程的上下文环境，的更为细小的CPU时间段。到此全文结束，再一个总结：进程和线程都是一个时间段的描述，是CPU工作时间段的描述，不过是颗粒大小不同。

####	10.final、finally、finalize区别
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

####	 try{} catch{} finally{} 是如何工作的
-	https://blog.csdn.net/qq_24309787/article/details/81304855

####	11.static关键字
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


####	11.Java 中静态方法可以被重写吗？
		严格来说，不存在静态方法的重写，当一个子类继承父类时，写同样的方法时，只是将父类的静态方法隐藏。
		[静态方法能否被重写]（http://xm-king.iteye.com/blog/745787）
		所谓静态，就是在运行时，虚拟机已经认定此方法属于哪个类。 专业术语有严格的含义,用语要准确.。
		"重写"只能适用于实例方法.不能用于静态方法.对于静态方法,只能隐藏。
		 静态方法的调用不需要实例化.. 不实例化也就不能用多态了，也就没有所谓的父类引用指向子类实例.因为不能实例化 也就没有机会去指向子类的实例。所以也就不存在多态了。


####	11.两种序列化的方式
-	https://www.jianshu.com/p/a60b609ec7e7
-	编码上：
	Serializable代码量少，写起来方便
	Parcelable代码多一些
-	效率上：
	Parcelable的速度比高十倍以上

	serializable的迷人之处在于你只需要对某个类以及它的属性实现Serializable 接口即可。Serializable 接口是一种标识接口（marker interface），这意味着无需实现方法，Java便会对这个对象进行高效的序列化操作。

	这种方法的缺点是使用了反射，序列化的过程较慢。这种机制会在序列化的时候创建许多的临时对象，容易触发垃圾回收。

	Parcelable方式的实现原理是将一个完整的对象进行分解，而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能了

####	12.内部类
-	https://www.cnblogs.com/latter/p/5665015.html

####	13.Object中有哪些方法，有什么作用(百度)
- 1.clone()，保护方法，实现对象的浅复制，
- 2.getClass(),获取类名
- 3.toString（），类名 + 类的 hash地址
- 4.equals（），比较地址值
- 5.finalize 该方法用于释放资源

####	14.请分别简述ArrayList、HashSet、TreeSet、HasMap、TreeMap、ConcurrentHashMap、LinkedHashMap的区别(百度)
		AndroidStudyDocument\Java\集合数组&数据类型\集合数组.xlsx


####	关于java集合下列说法不正确的有哪些 A
			A、HashSet 它是线程安全的，不允许存储相同的对象 				//不安全
			B、ConcurrentHashMap 它是线程安全的，其中存储的键对象可以重复，值对象不能重复
			C、Collection接口是List接口和Set接口的父接口，通常情况下不被直接使用
			D、ArrayList线程安全的，允许存放重复对象							  //不安全





####	15.java中有哪些引用方式，请做详细解释(百度)
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



-		弱引用
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

-			虚引用

					虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期。在java中用java.lang.ref.PhantomReference类表示。如果一个对象与虚引用关联，
					则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。
					要注意的是，虚引用必须和引用队列关联使用，当垃圾回收器准备回收一个对象时，
					如果发现它还有虚引用，就会把这个虚引用加入到与之 关联的引用队列中。
					程序可以通过判断引用队列中是否已经加入了虚引用，
					来了解被引用的对象是否将要被垃圾回收。
					如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。

####	16.java线程同步的方法有哪几种，请做解释(百度)
-  synchronized关键字

-  wait和notify

-  使用特殊域变量volatile实现线程同步

-  使用重入锁实现线程同步

-  使用局部变量来实现线程同步

- 使用阻塞队列实现线程同步

####	16 参考资料
-	1.https://blog.csdn.net/scgyus/article/details/79499650			//java中线程同步的几种方法

####	可回收对象的判定
-	引用计数算法
-	可达性分析算法（根搜索算法）


####	17.简述JVM的gc几种方式 (百度)
https://blog.csdn.net/seriousplus/article/details/80780243
-  标记清除算法

-  复制收集方式

-  标记整理算法

-	 分代回收算法(虚拟机默认的算法)
####	18.JAVA如何读取文件"input.txt"的内容，并写入到"output.txt" 中，写出核心代码(百度)

-	字节流

		 File inputText = new File("I:\\input.txt");
		 File outputText = new File("I:\\output.txt");
		 try {
	            FileInputStream inputFileInputStream = new FileInputStream(inputText);
	            FileOutputStream outputFileOutPutStream = new FileOutputStream(outputText);
	            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputFileInputStream);
	            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputFileOutPutStream);
	            byte[] bys = new byte[1024];
	            int len = 0;
				//将输入缓冲区的数据读取出来，-1为节点，只要不为-1 就一直读
	            while ((len = bufferedInputStream.read(bys)) != -1) {
	                bufferedOutputStream.write(bys, 0, len);
	            }
	            bufferedOutputStream.flush();
	            inputFileInputStream.close();
	            outputFileOutPutStream.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
-	 字符流

			try {
            FileWriter fileWriter = new FileWriter(outputText);
            FileReader fileReader = new FileReader(inputText);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String readLine = "";
            readLine = bufferedReader.readLine();
            while (readLine != null) {
                bufferedWriter.write(readLine);
                readLine = bufferedReader.readLine(); // 一次读入一行数据
            }

            bufferedWriter.flush();
            fileWriter.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }




#19.运行时数据区域划分
	Java虚拟机在执行Java程序的过程中会把它所管理的内存划分为若干个不同的数据区域。这些区域都有各自的用途，以及创建和销毁的时
	间，有的区域随着虚拟机进程的启动而存在，有些区域则是依赖用户线程的启动和结束而建立和销毁。

![](https://pic002.cnblogs.com/images/2012/416402/2012101820014328.jpg)

###19.1 程序计数器
	程序计数器（Program Counter Register）是一块较小的内存空间，它的作用可以看做是当前线程所执行的字节码的行号指示器。在虚拟
	机的概念模型里（仅是概念模型，各种虚拟机可能会通过一些更高效的方式去实现），字节码解释器工作时就是通过改变这个计数器的值来选取
	下一条需要执行的字节码指令，分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖这个计数器来完成。 由于Java虚拟机的多线
	程是通过线程轮流切换并分配处理器执行时间的方式来实现的，在任何一个确定的时刻，一个处理器（对于多核处理器来说是一个内
	核）只会执行一条线程中的指令。因此，为了线程切换后能恢复到正确的执行位置，每条线程都需要有一个独立的程序计数器，各条
	线程之间的计数器互不影响，独立存储，我们称这类内存区域为“线程私有”的内存。 如果线程正在执行的是一个Java方法，这个计数器
	记录的是正在执行的虚拟机字节码指令的地址；如果正在执行的是Natvie方法，这个计数器值则为空（Undefined）。此内存区域是唯一一个
	在Java虚拟机规范中没有规定任何OutOfMemoryError情况的区域。

###19.2 栈
	与程序计数器一样，Java虚拟机栈（Java Virtual Machine Stacks）也是线程私有的，它的生命周期与线程相同。虚拟机栈描述的是Java
	方法执行的内存模型：每个方法被执行的时候都会同时创建一个栈帧（Stack Frame）用于存储局部变量表、操作栈、动态链接、方法出口
	等信息。每一个方法被调用直至执行完成的过程，就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。
	　　经常有人把Java内存区分为堆内存（Heap）和栈内存（Stack），这种分法比较粗糙，Java内存区域的划分实际上远比这复杂。这种划分
	方式的流行只能说明大多数程序员最关注的、与对象内存分配关系最密切的内存区域是这两块。其中所指的“堆”在后面会专门讲述，而所指
	的“栈”就是现在讲的虚拟机栈，或者说是虚拟机栈中的局部变量表部分。
	　　局部变量表存放了编译期可知的各种基本数据类型（boolean、byte、char、short、int、float、long、double）、对象引用
	（reference类型），它不等同于对象本身，根据不同的虚拟机实现，它可能是一个指向对象起始地址的引用指针，也可能指向一个代表对象的
	句柄或者其他与此对象相关的位置）和returnAddress类型（指向了一条字节码指令的地址）。
	　　其中64位长度的long和double类型的数据会占用2个局部变量空间（Slot），其余的数据类型只占用1个。局部变量表所需的内存
	空间在编译期间完成分配，当进入一个方法时，这个方法需要在帧中分配多大的局部变量空间是完全确定的，在方法运行期间不会改
	变局部变量表的大小。 在Java虚拟机规范中，对这个区域规定了两种异常状况：如果线程请求的栈深度大于虚拟机所允许的深度，将抛出
	StackOverflowError异常；如果虚拟机栈可以动态扩展（当前大部分的Java虚拟机都可动态扩展，只不过Java虚拟机规范中也允许固定长度的虚拟机栈），
	当扩展时无法申请到足够的内存时会抛出OutOfMemoryError异常。

###19.3 堆
	对于大多数应用来说，Java堆（Java Heap）是Java虚拟机所管理的内存中最大的一块。Java堆是被所有线程共享的一块内存区域，在虚拟机启动时创建。此内
	存区域的唯一目的就是存放对象实例，几乎所有的对象实例都在这里分配内存。这一点在Java虚拟机规范中的描述是：所有的对象实例以及数组都要在堆上分配，
	但是随着JIT编译器的发展与逃逸分析技术的逐渐成熟，栈上分配、标量替换优化技术将会导致一些微妙的变化发生，所有的对象都分配在堆上也渐渐变得不是那么“绝对”了。
	　　Java堆是垃圾收集器管理的主要区域，因此很多时候也被称做“GC堆”（Garbage Collected Heap，幸好国内没翻译成“垃圾堆”）。如果从内存回收的角度看，
	由于现在收集器基本都是采用的分代收集算法，所以Java堆中还可以细分为：新生代和老年代；再细致一点的有Eden空间、From Survivor空间、To Survivor空间
	等。如果从内存分配的角度看，线程共享的Java堆中可能划分出多个线程私有的分配缓冲区（Thread Local Allocation Buffer，TLAB）。不过，无论如何划分，都与存放内容无关，无论哪个区域，存储的都仍然是对象实例，进一步划分的目的是为了更好地回收内存，或者更快地分配内存。在本章中，我们仅仅针对内存区
	域的作用进行讨论，Java堆中的上述各个区域的分配和回收等细节将会是下一章的主题。
	　　根据Java虚拟机规范的规定，Java堆可以处于物理上不连续的内存空间中，只要逻辑上是连续的即可，就像我们的磁盘空间一样。在实现时，既可以实现成固定大小的，也可以是可扩展的，不过当前主流的虚拟机都是按照可扩展来实现的（通过-Xmx和-Xms控制）。如果在堆中没有内存完成实例分配，并且堆也无法再扩展时，将会抛出OutOfMemoryError异常。


###19.3 方法区
	方法区（Method Area）与Java堆一样，是各个线程共享的内存区域，它用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。

###19.4 运行时常量池
	　运行时常量池（Runtime Constant Pool）是方法区的一部分。Class文件中除了有类的版本、字段、方法、接口等描述等信息外，还有一项信息是常量池（Constant Pool Table），用于存放编译期生成的各种字面量和符号引用，这部分内容将在类加载后存放到方法区的运行时常量池中。


#####	20. 数组、链表 和哈希表
###20.1 数组
	操作数据（增加）： 是将原数组的数据复制一份，再加上增加的对应位置的数据，形成一个新的数组。所以较慢。
	查找数据：数组是有下标的，根据下标进行查找。

###20.2 链表
	一个链表的数据单元，结构是存储着一个数据，以及下一个链表单元数据的地址。
	操作数据（增加）：如 a-b。在ab之间增加一个c，增加c这个单元，并修改c的“下一个链表单元的地址”为b的地址，并将a的“下一个链表单元的地址”修改为c的地址即可。
	取出数据，根据上一个数据，才能找到下一个数据。慢

###20.3 哈希表
	是数组和链表的结合体。

#21.类加载过程
###1.加载
	加载是将字节码数据从不同的数据源读取到JVM内存，并映射为JVM认可的数据结构，也就是Class对象的过程。
	数据源可以是Jar文件、Class文件等等。如果数据的格式并不是ClassFile的结构，则会报ClassFormatError。
###2.链接
-	1. 验证:验证是保证JVM安全的重要步骤。JVM需要校验字节信息是否符合规范，避免恶意信息和不规范数据危害JVM运行安全。如果验证出错，则会报VerifyError。
-	2. 准备:这一步会创建静态变量，并为静态变量开辟内存空间。
-	3. 解析:这一步会将符号引用替换为直接引用。
###3.初始化
	初始化会为静态变量赋值，并执行静态代码块中的逻辑。
https://www.cnblogs.com/goody9807/p/6425399.html


####	21.Java 中的类型转换
![avatar](image\dataChange.jpg)
-	基本数据类型数据转换
			在Java中，整数类型（byte/short/int/long）中，对于未声明数据类型的整形，其默认类型为int型。
			在浮点类型（float/double）中，对于未声明数据类型的浮点型，默认为double型。
			而当我们将一个数值范围较小的类型赋给一个数值范围较大的数值型变量，jvm在编译过程中会将此数值的类型进行自动提升。
			在数值类型的自动类型提升过程中，数值精度至少不应该降低（整型保持不变，float->double精度将变高）。
			而当我们需要将数值范围较大的数值类型赋给数值范围较小的数值类型变量时，我们需要手动地转换，称为强制类型转换。

-	引用数据类型转换
			由于继承和向上转型，子类向父类的转换是很自然的。
			但是当把父类转换为子类时，强制类型转换会在运行时检查父类的真实类型：
			如果引用的父类对象的真实身份是子类类型，只不过这个子类类型的对象经历了向上转型
			变成父类对象，这个时候我们再向下转回来子类，是可以的；
			而如果真的是父类的类型，就会抛出ClassCastException的异常。

####	本地变量、实例变量以及类变量之间的区别？
	https://github.com/stormzhang/android-interview-questions-cn
	https://www.jianshu.com/p/08562e95ed49
	https://www.cnblogs.com/liyihome/p/3664594.html
	https://www.cnblogs.com/oraser/p/6428286.html
	https://www.cnblogs.com/smilefortoday/p/4031298.html
