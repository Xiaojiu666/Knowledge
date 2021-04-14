##	RxJava 1.0

##	1、什么是Rxjava


##	2、操作符(方法)
####	from(发射)
	from()创建符可以从一个列表/数组来创建Observable,并一个接一个的从列表数组中发射出来每一个对象

####	just()
	接收1个以上,10个以下的参数，然后逐个发射。   

####	fromArray()
	接收一个数组，从数组中一个一个取出来发射。

####	fromIterable();


####	转换类操作符
-	map(转换)
			将原有的对象转换为任意另一种对象，只会转换一次 属于一对一，适用项目中对某个对象的属性进行转换，
			eg：例如时间戳转换时间字符串
-	flatMap
			将原有的对象转换为Observable，只会转换一次，将原有Observable对象转换成新的Observable对象，
			并且可以操作新的Observable对象进行数据再一次转换，适合复杂的转换。
			eg：例如有两个类型相同集合，需要判断是从哪个中获取数据，然后把数据依次发射出来，
			也可以使用map，但是如果使用map就需要先进性类型判断。
代码

			flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
									@Override
									public Observable<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
										if(ApiConstants.NETEASE_ID_HOUSE.equals(id)){
															return Observable.from(stringListMap.get("北京"));
												}else{
															return Observable.from(stringListMap.get(id));
										  	}
									}
				})

-
####	过滤类操作符
-	distinct
			distinct()的过滤规则是只允许还没有发射过的数据通过，所有重复的数据项都只会发射一次。

####	2.2contact(先后顺序,控制被观察者的顺序)
![](https://upload-images.jianshu.io/upload_images/3994917-717b7a5bae136a0e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/610)

	想必在实际应用中，很多时候（对数据操作不敏感时）都需要我们先读取缓存的数据，
	如果缓存没有数据，再通过网络请求获取，随后在主线程更新我们的UI。





####	2.4zip(压合,将多个被观察者的结果压合成一条)


####	2.5compose	//避免打破链式结构
		配合3.1 可以进行重复代码复用，
		http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0819/3327.html


####	2.6buffer(缓冲)
		buffer((count)3,(skip)3);
		将要发射的数据封装成多个缓冲区，每次发射一个缓冲区
		相当于把一个观察一个List并且响应为多个List
		如果只传入第一个参数，相当于每个缓冲区的个数，[1,2,3,4...10]  [1,2,3,]
		第二个参数作用控制每组缓冲区相对于总数要跳过几个开始









##	3、接口
####	3.1 Transformer(Observalbe转换成Observalbe)
		new rx.Observable.Transformer<String, Boolean>() {
				@Override
				public Observable<Boolean> call(Observable<String> stringObservable) {
						return null;
				}
		};
		


##  线程调度器Schedulers
####    简介 
    在没有给定调度器（Scheduler）的情况下，Subscription将默认(产生事件与订阅)运行于调用线程上。
    线程调度器（Scheduler）是将RxJava从同步观察者模式转到异步观察者模式的一个重要工具。
    Rxjava通过它来指定每一段代码应该运行在什么样的线程。
    Rxjava通过subscribeOn()和observeOn()两个方法来对线程进行控制，subscribeOn()指定subscribe()时间发生的线程，
    也就是事件产生的线程，observeOn()指定Subscriber做运行的线程，也就是消费事件的线程。
    Rxjava提供了5种调度器：
    
-   Scheduler Schedulers.io()
-   Scheduler Schedulers.computation()
-   Scheduler Schedulers.immediate()
-   Scheduler Schedulers.newThread()
-   Scheduler Schedulers.trampoline()
    
    还有可用于测试的调度器Schedulers.test() 及 可自定义Scheduler—-Schedulers.form()
    
######  Schedulers.io()
    内部创建一个rx.internal.schedulers.CachedThreadScheduler。
    底层实现是一个java中的ScheduledThreadPoolExecutor (extends ThreadPoolExecutorimplements ScheduledExecutorService)
    这个调度器用于I/O操作，比如：读写文件，数据库，网络交互等等。行为模式和newThread()差不多，重点需要注意的是线程池是无限制的，大量的I/O调度操作将创建许多个线程并占用内存。
    
######  Schedulers.computation()
    计算工作默认的调度器，这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，
    例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。
    
######  Schedulers.immediate()
    这个调度器允许你立即在当前线程执行你指定的工作。这是默认的Scheduler。

######  Schedulers.newThread()
    这个调度器正如它所看起来的那样：它为指定任务启动一个新的线程。
    
######  Schedulers.trampoline()
    当我们想在当前线程执行一个任务时，并不是立即，我们可以用trampoline() 将它入队。
    这个调度器将会处理它的队列并且按序运行队列中每一个任务。

##	4、问题
-	1.A compose.Transformer转换和flatMap 的区别，
-	1.Q compose.Transformer 操作的是整个的Observalbe   而faltmap针对的是 数据流

-   什么是背压   
    Rx 中的数据流是从一个地方发射到另外一个地方。每个地方处理数据的速度是不一样的。如果生产者发射数据的速度比消费者处理的快会出现什么情况？
    事件产生的速度远远快于事件消费的速度，最终导致数据积累越来越多，从而导致OOM等异常。
    
    -   1.0解决办法
        观察者可以根据自身实际情况按需拉取数据，而不是被动接收（也就相当于告诉上游观察者把速度慢下来），
        最终实现了上游被观察者发送事件的速度的控制，实现了背压的策略。   
        继承 Subscriber  通过 在onStart方法 中  request(1);在onStart中通知被观察者先发送一个事件
       
    -   2.0解决办法 


##	参考资料
-	https://www.jianshu.com/p/3fdd9ddb534b
-	https://cloud.tencent.com/developer/article/1334650
-	https://www.jianshu.com/p/3a188b995daa
-	https://www.jianshu.com/p/3fdd9ddb534b
