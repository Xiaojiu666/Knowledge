
## 常见问题
####	1.JVM和DVM的区别
	1.	JVM，
		1.	JVM基于栈，栈是内存上面的一段连续的存储空间
		2.	JVM是Oracle公司
		3.	java文件 -> .class文件 -> .jar文件
		4.	![avatar](https://img-blog.csdn.net/20161104122858683?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
	2.
		1.	DVM，Dalvik Virtual Machine  Dalvik基于寄存器 Dalvik经过优化，允许在有限的内存中同时运行多个虚拟机的实例，并且每一个 Dalvik应用作为一个独立的Linux进程执行。独立的进程可以防止在虚拟机崩溃的时候所有程序都被关闭。寄存器是CPU上面的一块存储空间
		2.	Google
		3.	java文件 –> .class文件 -> .dex文件


####	2.说说你对AIDL的理解，原理及其步骤(百度)
		AIDL介绍:AIDL,安卓端跨进程通信，
		AIDL，全称是Android Interface Define Language，即安卓接口定义语言，可以实现安卓设备中进程之间的通信（Inter Process Communication, IPC）。假设有如下场景，需要计算a+b的值，在客户端中获取a和b的值，然后传递给服务端，服务端进行a+b的计算，并将计算结果返回给客户端。
		这里的客户端和服务端均是指安卓设备中的应用，由于每一个应用对应一个进程，由此可以模拟安卓系统中通过AIDL进行进程之间的通信。
		AIDL的具体使用步骤如下：


####	3.如果对图片进行采样压缩，假设图片是TEST.JPG,请写出简要原理(百度)
	https://blog.csdn.net/bluezhangfun/article/details/50402639
	1.设置bitmapFactory.options中的 inJustDecodeBunlde 对第一次采样进行配置， ture 只读文件的宽高，不读大小进内存
	2.通过bitmapFactory.decodeFile进行对图片的获取，
	3.进行采样率计算
	3.1 根据屏幕的宽高，根据通过options获取的图片宽高，进行计算
	3.2 如果图片宽高的1/2 / 采样率 还是大于屏幕的宽高，通过while循环继续循环增加采样率，直到图片宽高小于屏幕的宽和高，inSampleSize*=2 每次翻两倍
	4.通过计算出的采样率，设置给options.insmple()
	5.设置每一个像素点的存储方式 inPreferredConfig =Bitmap.Config.RGB565
####	4.Touch事件分发机制(百度)

####	5.ListView和RecyclerView的使用，区别？
-   效果 :  LayoutManager, 自带条目动画，可移动
-   性能 :  自带ViewHodler  对item进行复用，
-   可扩展性高 : 

####	6.Git问题
######	6.1新建一个分支
	git branch text
######	6.2提交修改内容
	git add ”“
	git commit
######	6.3把test分支合并到develop
	git merge develop
######	6.4把develop推送到远程服务器
	git remote add origin ”地址“
	git push
######	6.5更新develop分支
	$ git fetch [name] master 从仓库检出分支只是从远程获取最新版本到本地,不会merge(合并)
	$ git merge origin/master 合并本地仓库和远程仓库

######	7.内存泄露问题
	内存指程序在申请内存后,无法释放已经申请的内存空间,一次内存泄露危害可以忽略,但内存泄露堆积后的结果很严重,无论多少内存,迟早会被占光
- 1.单例模式导致的内存泄漏

		最常见的例子就是创建这个单例对象需要传入一个Context，这时候传入了一个Activity类型的Context，由于单例对象的静态属性，
		导致它的生命周期是从单例类加载到应用程序结束为止，所以即使已经finish掉了传入的Activity，由于我们的单例对象依然持
		有Activity的引用，所以导致了内存泄漏。解决办法也很简单，不要使用Activity类型的Context，使用Application类型的Context可以避免内存泄漏。


- 2.静态变量导致的内存泄漏


		静态变量是放在方法区中的，它的生命周期是从类加载到程序结束，可以看到静态变量生命周期是非常久的。
		最常见的因静态变量导致内存泄漏的例子是我们在Activity中创建了一个静态变量，而这个静态变量的创建需要传入Activity
		的引用this。在这种情况下即使Activity调用了finish也会导致内存泄漏。原因就是因为这个静态变量的生命周期几乎和整个应
		用程序的生命周期一致，它一直持有Activity的引用，从而导致了内存泄漏。

- 3.非静态内部类导致的内存泄漏

		非静态内部类导致内存泄漏的原因是非静态内部类持有外部类的引用，最常见的例子就是在Activity中使用Handler和Thread了。使用非静态内部类创建的Handler和Thread在执行延时操作的时候会一直持有当前Activity的引用，如果在执行延时操作的时候就结束Activity，这样就会导致内存泄漏。解决办法有两种：第一种是使用静态内部类，在静态内部类中使用弱引用调用Activity。第二种方法是在Activity的onDestroy中调用handler.removeCallbacksAndMessages来取消延时事件。

- 4.使用资源未及时关闭导致的内存泄漏

		使用资源未及时关闭导致的内存泄漏。常见的例子有：操作各种数据流未及时关闭，操作Bitmap未及时recycle等等。
		有的三方库提供了注册和解绑的功能，最常见的就是EventBus了，我们都知道使用EventBus要在onCreate中注册，在onDestroy中解绑。
		如果没有解绑的话，EventBus其实是一个单例模式，他会一直持有Activity的引用，导致内存泄漏。同样常见的还有RxJava，
		在使用Timer操作符做了一些延时操作后也要注意在onDestroy方法中调用disposable.dispose()来取消操作。

- 5.属性动画导致的内存泄漏

		常见的例子就是在属性动画执行的过程中退出了Activity，这时View对象依然持有Activity的引用从而导致了内存泄漏。
		解决办法就是在onDestroy中调用动画的cancel方法取消属性动画。

- 6.WebView导致的内存泄漏

		WebView比较特殊，即使是调用了它的destroy方法，依然会导致内存泄漏。其实避免WebView导致内存泄漏的最好方法就是
		让WebView所在的Activity处于另一个进程中，当这个Activity结束时杀死当前WebView所处的进程即可，我记得阿里钉钉的WebView就是另外开启的一个进程，应该也是采用这种方法避免内存泄漏。


##	UI方面
####	1.View
######	1.1 触摸事件事件分发机制

![avatar](image\dispath.png)

	一、为什么要有事件分发机制
		为了解决安卓触摸事件需要在哪层响应，让事件可控
		响应层分为三层Activity-->ViewGroup-->View
	二、默认原理
		dispatchTouch(控制事件和分发事件)
		onTouch(响应事件和处理事件)
		在上面两个方法没有进行任何方法重写和控制情况下，也就是默认的所有dispatchTouch 和 OnTouch返回值为super的情况下，事件会默认的走完三个层级，并且三个层级全部进行事件的响应
	三、自定义需求
		1.1 三层全部不响应，那么无论dispatchTouch返回true&false 他自己和下面两层的onToutch都不会响应。
		1.2 AC层响应，下面两层不响应，将VG层的dispatchTouch返回false，因为在AC层dispatchTouch的源码中，如果返回值为false
	四、总结
		只要控制好dispatchTouch方法，就可以控制事件响应的对应的层级关系，onInterceptTouch方法主要作用是担心ViewGroup里面没有子View，但是AC就不需要担心里面没有子ViewGroup ，因为AC里面必然有xml否则会报错

######	1.2 自定义View
######	1.3 View的绘制流程
#### 2.RecyclerView
######	优化
https://www.jianshu.com/p/bd432a3527d6

## 五大组件
#### 1.activity
	Activity是Android程序与用户交互的窗口，是Android构造中最基本的一种，它需要为保持各界面的状态，
	做很多持久化的事情，妥善管理生命周期以及一些跳转逻辑。
###### 1.1 activity生命周期

-	1. onCreat()
-	2. onStart()
-	3. onResume()
-	4. onPaue()
-	5. onStop()
-	6. onResttart()
-	7. onDestory()返回键点击时触发

######	A-->B生命周期
1.Activity1启动：
      Activity1: onCreate()
      Activity1: onStart()
      Activity1: onResume()

 2.点击按钮跳转到Activity2:
      Activity1: onPause()
      Activity2: onCreate()
      Activity2: onStart()
      Activity2: onResume()
      Activity1: onStop()

 3.从Activity2中返回：
      Activity2: onPause()
      Activity1: onRestart()
      Activity1: onStart()
      Activity1: onResume()
      Activity2: onStop()
      Activity2: onDestroy()

 4.Activity1退出
      Activity1: onPause()
      Activity1: onStop()
      Activity1: onDestroy()


###### 1.2 activity启动模式
-	Standard 标准模式
-	SingleTop 栈顶复用模式
-	SingleTask 栈内复用模式
-	SingleInstance		
###### 1.3 activity进行数据保存和恢复
	开发者提前可以复写onSaveInstanceState方法，创建一个Bundle类型的参数，
	把数据存储在这个Bundle对象中，这样即使Activity意外退出
	，Activity被系统摧毁，当重新启动这个Activity而调用onCreate方法时，上述Bundle对象会作为参数传递给onCreate方法，开发者可以从Bundle对象中取出保存的数据，利用这些数据将Activity恢复到被摧毁之前的状态。


#### 2.fragment
###### 2.1 fragment生命周期
###### 2.1 fragment生命周期
######

#### 3.service
![avatar](image\service_diff.png)
######	3.1Service的两种使用方式分别是什么？它们的生命周期分别怎样迁移？

#### 4.BroadcastReceiver
		即 广播，是一个全局的监听器，属于Android四大组件之一
		广播分为两个角色：广播发送者、广播接收者
		监听 / 接收 应用 App 发出的广播消息，并 做出响应
		Android不同组件间的通信（含 ：应用内 / 不同应用之间）
		多线程通信
		与 Android 系统在特定情况下的通信 如：电话呼入时、网络可用时
		Android中的广播使用了设计模式中的观察者模式：基于消息的发布 / 订阅事件模型
		因此，Android将广播的发送者 和 接收者 解耦，使得系统方便集成，更易扩展
		https://www.jianshu.com/p/ca3d87a4cdf3

![avatar](image\broadcast.png)
-	广播接收者	消息订阅者
			继承BroadcastReceivre基类
			必须复写抽象方法onReceive()方法
			广播接收器接收到相应广播后，会自动回调 onReceive() 方法
			一般情况下，onReceive方法会涉及 与 其他组件之间的交互，如发送Notification、启动Service等
			默认情况下，广播接收器运行在 UI 线程，因此，onReceive()方法不能执行耗时操作，否则将导致ANR
		-	 静态注册
					注册方式：在AndroidManifest.xml里通过<receive>标签声明

			实例
					<receiver>
					    android:enabled=["true" | "false"]
					//此broadcastReceiver能否接收其他App的发出的广播
					//默认值是由receiver中有无intent-filter决定的：如果有intent-filter，默认值为true，否则为false
					    android:exported=["true" | "false"]
					    android:icon="drawable resource"
					    android:label="string resource"
					//继承BroadcastReceiver子类的类名
					    android:name=".mBroadcastReceiver"
					//具有相应权限的广播发送者发送的广播才能被此BroadcastReceiver所接收；
					    android:permission="string"
					//BroadcastReceiver运行所处的进程
					//默认为app的进程，可以指定独立的进程
					//注：Android四大基本组件都可以通过此属性指定自己的独立进程
					    android:process="string" >

					//用于指定此广播接收器将接收的广播类型
					//本示例中给出的是用于接收网络状态改变时发出的广播
					//意图过滤器
					 		<intent-filter>
								<action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
					    </intent-filter>
					</receiver>

		-	 动态注册
					注册方式：在代码中调用Context.registerReceiver（）方法
					动态广播最好在Activity 的 onResume()注册、onPause()注销。
					对于动态广播，有注册就必然得有注销，否则会导致内存泄露
		实例
					// 选择在Activity生命周期方法中的onResume()中注册
						@Override
					  protected void onResume(){
					      super.onResume();

					    // 1. 实例化BroadcastReceiver子类 &  IntentFilter
					     mBroadcastReceiver mBroadcastReceiver = new mBroadcastReceiver();
					     IntentFilter intentFilter = new IntentFilter();

					    // 2. 设置接收广播的类型
					    intentFilter.addAction(android.net.conn.CONNECTIVITY_CHANGE);

					    // 3. 动态注册：调用Context的registerReceiver（）方法
					     registerReceiver(mBroadcastReceiver, intentFilter);
					 }

					// 注册广播后，要在相应位置记得销毁广播
					// 即在onPause() 中unregisterReceiver(mBroadcastReceiver)
					// 当此Activity实例化时，会动态将MyBroadcastReceiver注册到系统中
					// 当此Activity销毁时，动态注册的MyBroadcastReceiver将不再接收到相应的广播。
						 @Override
						 protected void onPause() {
						     super.onPause();
						      //销毁在onResume()方法中的广播
						     unregisterReceiver(mBroadcastReceiver);
						    }
					}

-	广播发布者	消息发布者
			广播 是 用”意图（Intent）“标识
			定义广播的本质 = 定义广播所具备的“意图（Intent）”
			广播发送 = 广播发送者 将此广播的“意图（Intent）”通过sendBroadcast（）方法发送出去
	广播类型
	-	 普通广播（Normal Broadcast
				即 开发者自身定义 intent的广播（最常用）。发送广播使用如下：
				Intent intent = new Intent();
				//对应BroadcastReceiver中intentFilter的action
				intent.setAction(BROADCAST_ACTION);
				//发送广播
				sendBroadcast(intent);
  -	 系统广播（System Broadcast）
				Android中内置了多个系统广播：只要涉及到手机的基本操作（如开机、网络状态变化、拍照等等），都会发出相应的广播

  -	 有序广播（Ordered Broadcast）
				发送出去的广播被广播接收者按照先后顺序接收
				sendOrderedBroadcast(intent);

  -	 粘性广播（Sticky Broadcast）
  -	 App应用内广播（Local Broadcast）
				Android中的广播可以跨App直接通信（exported对于有intent-filter情况下默认值为true）
问题
				其他App针对性发出与当前App intent-filter相匹配的广播，由此导致当前App不断接收广播并处理；
				其他App注册与当前App一致的intent-filter用于接收广播，获取广播具体信息；
				即会出现安全性 & 效率性的问题。
使用
		-	将全局广播设置成局部广播
					注册广播时将exported属性设置为false，使得非本App内部发出的此广播不被接收；
					在广播发送和接收时，增设相应权限permission，用于权限验证；
					发送广播时指定该广播接收器所在的包名，此广播将只会发送到此包中的App内与之相匹配的有效广播接收器中。

		-	使用封装好的LocalBroadcastManager类
					使用方式上与全局广播几乎相同，只是注册/取消注册广播接收器和发送广播时将参数
					的context变成了LocalBroadcastManager的单一实例
			实现
					//注册应用内广播接收器
					//步骤1：实例化BroadcastReceiver子类 & IntentFilter mBroadcastReceiver
					mBroadcastReceiver = new mBroadcastReceiver();
					IntentFilter intentFilter = new IntentFilter();

					//步骤2：实例化LocalBroadcastManager的实例
					localBroadcastManager = LocalBroadcastManager.getInstance(this);

					//步骤3：设置接收广播的类型
					intentFilter.addAction(android.net.conn.CONNECTIVITY_CHANGE);

					//步骤4：调用LocalBroadcastManager单一实例的registerReceiver（）方法进行动态注册
					localBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);

					//取消注册应用内广播接收器
					localBroadcastManager.unregisterReceiver(mBroadcastReceiver);

					//发送应用内广播
					Intent intent = new Intent();
					intent.setAction(BROADCAST_ACTION);
					localBroadcastManager.sendBroadcast(intent);


-	Activity Manager Service	消息中心

######	4.1什么是BroadcastReceiver？它有什么作用？
######	4.2什么是有序广播？有序广播有什么特点？什么是系统广播？
######	4.3什么是粘性广播？它跟普通广播有什么区别？
######	4.4两种注册方式的区别？
######	4.5本地广播和普通广播的区别
        App应用内广播可理解为一种局部广播，广播的发送者和接收者都同属于一个App。
        相比于全局广播（普通广播），App应用内广播优势体现在：安全性高 & 效率高


![avatar](image\broadcast_diff.png)

#### 5.ContentProvider
		ContentProvider属于 Android的四大组件之一
		进程间(应用间) 进行数据交互 & 共享，即跨进程通信
		ContentProvider的底层原理 = Android中的Binder机制
		https://www.jianshu.com/p/ea8bc4aaf057
![avatar](image\URI.png)

		URI
		通过统一资源标识符（URI）进行数据定位
		外界进程通过 URI 找到对应的ContentProvider & 其中的数据，再进行数据操作
		URI分为 系统预置 & 自定义，分别对应系统内置的数据（如通讯录、日程表等等）和自定义数据库
-	MIME数据类型
			用于存储数据
-	ContentProvider类
			ContentProvider主要以 表格的形式 组织数据
			每个表格中包含多张表，每张表包含行 & 列，分别对应记录 & 字段
			进程间共享数据的本质是：添加、删除、获取 & 修改（更新）数据
    	Android为常见的数据（如通讯录、日程表等）提供了内置了默认的ContentProvider
    	但也可根据需求自定义ContentProvider，但上述6个方法必须重写
			ContentProvider类并不会直接与外部进程交互，而是通过ContentResolver 类

-	ContentResolver类
			统一管理不同 ContentProvider间的操作
			一般来说，一款应用要使用多个ContentProvider，若需要了解每个ContentProvider的不同实现从而再完成数据交互，操作成本高  & 难度大
			所以再ContentProvider类上加多了一个 ContentResolver类对所有的ContentProvider进行统一管理。
实例过程
			// 使用ContentResolver前，需要先获取ContentResolver
			// 可通过在所有继承Context的类中 通过调用getContentResolver()来获得ContentResolver
			ContentResolver resolver =  getContentResolver();

			// 设置ContentProvider的URI
			Uri uri = Uri.parse("content://cn.scu.myprovider/user");

			// 根据URI 操作 ContentProvider中的数据
			// 此处是获取ContentProvider中 user表的所有记录
			Cursor cursor = resolver.query(uri, null, null, null, "userid desc");
Android 提供了3个用于辅助ContentProvide的工具类：
	-	    ContentUris	//操作 URI  
	核心方法有两个：withAppendedId（） &parseId（）
					// withAppendedId（）作用：向URI追加一个id
					Uri uri = Uri.parse("content://cn.scu.myprovider/user")
					Uri resultUri = ContentUris.withAppendedId(uri, 7);  
					// 最终生成后的Uri为：content://cn.scu.myprovider/user/7

					// parseId（）作用：从URL中获取ID
					Uri uri = Uri.parse("content://cn.scu.myprovider/user/7")
					long personid = ContentUris.parseId(uri);
					//获取的结果为:7

	-    	UriMatcher
	在ContentProvider 中注册URI
	根据 URI 匹配 ContentProvider 中对应的数据表
					// 步骤1：初始化UriMatcher对象
					UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
					//常量UriMatcher.NO_MATCH  = 不匹配任何路径的返回码
					// 即初始化时不匹配任何东西

					// 步骤2：在ContentProvider 中注册URI（addURI（））
					int URI_CODE_a = 1；
					int URI_CODE_b = 2；
					matcher.addURI("cn.scu.myprovider", "user1", URI_CODE_a);
					matcher.addURI("cn.scu.myprovider", "user2", URI_CODE_b);
					// 若URI资源路径 = content://cn.scu.myprovider/user1 ，则返回注册码URI_CODE_a
					// 若URI资源路径 = content://cn.scu.myprovider/user2 ，则返回注册码URI_CODE_b

					// 步骤3：根据URI 匹配 URI_CODE，从而匹配ContentProvider中相应的资源（match（））

					@Override   
					public String getType(Uri uri) {   
						Uri uri = Uri.parse(" content://cn.scu.myprovider/user1");   

						switch(matcher.match(uri)){   
					 // 根据URI匹配的返回码是URI_CODE_a
					 // 即matcher.match(uri) == URI_CODE_a
						case URI_CODE_a:   
							return tableNameUser1;   
							// 如果根据URI匹配的返回码是URI_CODE_a，则返回ContentProvider中的名为tableNameUser1的表
						case URI_CODE_b:   
							return tableNameUser2;
							// 如果根据URI匹配的返回码是URI_CODE_b，则返回ContentProvider中的名为tableNameUser2的表
					}   
					}
  -  		ContentObserver		//内容观察者

				观察 Uri引起 ContentProvider 中的数据变化 & 通知外界（即访问该数据访问者）

					// 步骤1：注册内容观察者ContentObserver
					    getContentResolver().registerContentObserver（uri）；
					// 通过ContentResolver类进行注册，并指定需要观察的URI

					// 步骤2：当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
					    public class UserContentProvider extends ContentProvider {
					      public Uri insert(Uri uri, ContentValues values) {
					      db.insert("user", "userid", values);
					      getContext().getContentResolver().notifyChange(uri, null);
					      // 通知访问者
					   }
					}

						// 步骤3：解除观察者
					 getContentResolver().unregisterContentObserver（uri）；
					  // 同样需要通过ContentResolver类进行解除




######	5.1什么是ContentProvider？如何自定义一个ContentProvider？
######	5.2你使用过哪些系统的ContentProvider？


#### 6.Intent
######	6.1 Intent是什么？有什么用处？
######	6.2	Intent如何传值？Bundle是什么，有什么用？
######	6.3 序列化的两种方式？区别
######	6.4 隐士意图和显示意图的区别


## 数据&&网络
####	1.数据
######	1.1数据常用的数据持久化(长久保存数据)方式有哪些？

######	1.2LRUCache内存缓存
######	1.3DiskLRUCache硬盘缓存

######	1.4RxCache
				https://www.jianshu.com/p/d7a25b846a44

####	2.网络
######	2.1TCP和UDP的区别是什么？
######	2.2Http的get和post方法的区别什么？


## 线程&&进程方面
####	1.线程
###### 1.Handler消息机制
https://blog.csdn.net/feather_wch/article/details/81136078
		1、作用
			线程之间通讯
		主线程、子线程、Message、MessageQueen、Handler、Looper
###### 2.常见的实现异步的方式有哪些？

###### looper如何区分多个handler
			通过sendMessage方法中msg.target.dispatchMessage(msg);

######	子线程中可以创建Handler么？怎么创建？
			可以直接在子线程中new  Handler ，但是会报错，因为子线程中没有Looper对象，需要去Looper.Looper.prepare()方法
######	MessageQueue中底层是采用的队列？
			采用单链表的数据结构来维护消息队列，而不是采用队列

####	2.进程
###### 1.Binder机制

###### 2.AIDL语法
		AIDL意思即Android  Interface Definition Language,翻过来就是Android接口定义语言，是用于定义服务端和客户端通信接口的一
		种描述语言，可以拿来生产IPC代码，从某种意义上说AIDL其实就是一个模板，因为在使用过程中，实际起作用的并不是AIDL文件，而是
		据此生产的一个Interface的实例代码，AIDL其实是为了避免我们重复写代码而出现的一个模板。





## 性能优化
-   https://www.jianshu.com/p/754f7607c869
####	1.图片
###### 1.1	图片压缩
###### 1.2	图片压缩

####	2.网络
###### 2.1	ListView条目复用


##	框架问题


https://www.cnblogs.com/ldq2016/p/9035666.html
