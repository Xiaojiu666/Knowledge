#### 1.JVM 和 DVM 的区别

    1.	JVM，
    	1.	JVM基于栈，栈是内存上面的一段连续的存储空间
    	2.	JVM是Oracle公司
    	3.	java文件 -> .class文件 -> .jar文件
    	4.	![avatar](https://img-blog.csdn.net/20161104122858683?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
    2. DVM
    	1.	DVM，Dalvik Virtual Machine  Dalvik基于寄存器 Dalvik经过优化，允许在有限的内存中同时运行多个虚拟机的实例，并且每一个 Dalvik应用作为一个独立的Linux进程执行。独立的进程可以防止在虚拟机崩溃的时候所有程序都被关闭。寄存器是CPU上面的一块存储空间
    	2.	Google
    	3.	java文件 –> .class文件 -> .dex文件

#### 2.说说你对 AIDL 的理解，原理及其步骤(百度)

    	AIDL介绍:AIDL,安卓端跨进程通信，
    	AIDL，全称是Android Interface Define Language，即安卓接口定义语言，可以实现安卓设备中进程之间的通信（Inter Process Communication, IPC）。假设有如下场景，需要计算a+b的值，在客户端中获取a和b的值，然后传递给服务端，服务端进行a+b的计算，并将计算结果返回给客户端。
    	这里的客户端和服务端均是指安卓设备中的应用，由于每一个应用对应一个进程，由此可以模拟安卓系统中通过AIDL进行进程之间的通信。
    	AIDL的具体使用步骤如下：






###### 3.1Service 的两种使用方式分别是什么？它们的生命周期分别怎样迁移？

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

- 广播接收者 消息订阅者
  继承 BroadcastReceivre 基类
  必须复写抽象方法 onReceive()方法
  广播接收器接收到相应广播后，会自动回调 onReceive() 方法
  一般情况下，onReceive 方法会涉及 与 其他组件之间的交互，如发送 Notification、启动 Service 等
  默认情况下，广播接收器运行在 UI 线程，因此，onReceive()方法不能执行耗时操作，否则将导致 ANR - 静态注册
  注册方式：在 AndroidManifest.xml 里通过<receive>标签声明

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

- 广播发布者 消息发布者
  广播 是 用”意图（Intent）“标识
  定义广播的本质 = 定义广播所具备的“意图（Intent）”
  广播发送 = 广播发送者 将此广播的“意图（Intent）”通过 sendBroadcast（）方法发送出去
  广播类型
  - 普通广播（Normal Broadcast
    即 开发者自身定义 intent 的广播（最常用）。发送广播使用如下：
    Intent intent = new Intent();
    //对应 BroadcastReceiver 中 intentFilter 的 action
    intent.setAction(BROADCAST_ACTION);
    //发送广播
    sendBroadcast(intent);
- 系统广播（System Broadcast）
  Android 中内置了多个系统广播：只要涉及到手机的基本操作（如开机、网络状态变化、拍照等等），都会发出相应的广播

- 有序广播（Ordered Broadcast）
  发送出去的广播被广播接收者按照先后顺序接收
  sendOrderedBroadcast(intent);

- 粘性广播（Sticky Broadcast）
- App 应用内广播（Local Broadcast）
  Android 中的广播可以跨 App 直接通信（exported 对于有 intent-filter 情况下默认值为 true）
  问题
  其他 App 针对性发出与当前 App intent-filter 相匹配的广播，由此导致当前 App 不断接收广播并处理；
  其他 App 注册与当前 App 一致的 intent-filter 用于接收广播，获取广播具体信息；
  即会出现安全性 & 效率性的问题。
  使用 - 将全局广播设置成局部广播
  注册广播时将 exported 属性设置为 false，使得非本 App 内部发出的此广播不被接收；
  在广播发送和接收时，增设相应权限 permission，用于权限验证；
  发送广播时指定该广播接收器所在的包名，此广播将只会发送到此包中的 App 内与之相匹配的有效广播接收器中。

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

- Activity Manager Service 消息中心

###### 4.1 什么是 BroadcastReceiver？它有什么作用？

###### 4.2 什么是有序广播？有序广播有什么特点？什么是系统广播？

###### 4.3 什么是粘性广播？它跟普通广播有什么区别？

###### 4.4 两种注册方式的区别？

###### 4.5 本地广播和普通广播的区别

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

- MIME 数据类型
  用于存储数据
- ContentProvider 类
  ContentProvider 主要以 表格的形式 组织数据
  每个表格中包含多张表，每张表包含行 & 列，分别对应记录 & 字段
  进程间共享数据的本质是：添加、删除、获取 & 修改（更新）数据
  Android 为常见的数据（如通讯录、日程表等）提供了内置了默认的 ContentProvider
  但也可根据需求自定义 ContentProvider，但上述 6 个方法必须重写
  ContentProvider 类并不会直接与外部进程交互，而是通过 ContentResolver 类

- ContentResolver 类
  统一管理不同 ContentProvider 间的操作
  一般来说，一款应用要使用多个 ContentProvider，若需要了解每个 ContentProvider 的不同实现从而再完成数据交互，操作成本高 & 难度大
  所以再 ContentProvider 类上加多了一个 ContentResolver 类对所有的 ContentProvider 进行统一管理。
  实例过程
  // 使用 ContentResolver 前，需要先获取 ContentResolver
  // 可通过在所有继承 Context 的类中 通过调用 getContentResolver()来获得 ContentResolver
  ContentResolver resolver = getContentResolver();

      		// 设置ContentProvider的URI
      		Uri uri = Uri.parse("content://cn.scu.myprovider/user");

      		// 根据URI 操作 ContentProvider中的数据
      		// 此处是获取ContentProvider中 user表的所有记录
      		Cursor cursor = resolver.query(uri, null, null, null, "userid desc");

  Android 提供了 3 个用于辅助 ContentProvide 的工具类： - ContentUris //操作 URI  
   核心方法有两个：withAppendedId（） &parseId（）
  // withAppendedId（）作用：向 URI 追加一个 id
  Uri uri = Uri.parse("content://cn.scu.myprovider/user")
  Uri resultUri = ContentUris.withAppendedId(uri, 7);  
   // 最终生成后的 Uri 为：content://cn.scu.myprovider/user/7

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

-     	ContentObserver		//内容观察者

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

###### 5.1 什么是 ContentProvider？如何自定义一个 ContentProvider？

###### 5.2 你使用过哪些系统的 ContentProvider？

#### 6.Intent

###### 6.1 Intent 是什么？有什么用处？

###### 6.2 Intent 如何传值？Bundle 是什么，有什么用？

###### 6.3 序列化的两种方式？区别

###### 6.4 隐士意图和显示意图的区别

## 数据&&网络

#### 1.数据

###### 1.1 数据常用的数据持久化(长久保存数据)方式有哪些？

###### 1.2LRUCache 内存缓存

###### 1.3DiskLRUCache 硬盘缓存

###### 1.4RxCache

    			https://www.jianshu.com/p/d7a25b846a44

#### 2.网络

###### 2.1TCP 和 UDP 的区别是什么？

###### 2.2Http 的 get 和 post 方法的区别什么？

## 线程&&进程方面

#### 1.线程

###### 1.Handler 消息机制

https://blog.csdn.net/feather_wch/article/details/81136078
1、作用
线程之间通讯
主线程、子线程、Message、MessageQueen、Handler、Looper

###### 2.常见的实现异步的方式有哪些？

###### looper 如何区分多个 handler

    		通过sendMessage方法中msg.target.dispatchMessage(msg);

###### 子线程中可以创建 Handler 么？怎么创建？

    		可以直接在子线程中new  Handler ，但是会报错，因为子线程中没有Looper对象，需要去Looper.Looper.prepare()方法

###### MessageQueue 中底层是采用的队列？

    		采用单链表的数据结构来维护消息队列，而不是采用队列

#### 2.进程

###### 1.Binder 机制

###### 2.AIDL 语法

    	AIDL意思即Android  Interface Definition Language,翻过来就是Android接口定义语言，是用于定义服务端和客户端通信接口的一
    	种描述语言，可以拿来生产IPC代码，从某种意义上说AIDL其实就是一个模板，因为在使用过程中，实际起作用的并不是AIDL文件，而是
    	据此生产的一个Interface的实例代码，AIDL其实是为了避免我们重复写代码而出现的一个模板。

### 四大组件-Activity

##### 启动流程

[Activity 启动流程分析](https://blog.csdn.net/java521666/article/details/126607351)  
1、点击桌面 App 图标，Launcher 进程采用 Binder IPC 向 system_server 进程发起 startActivity 请求  
2、system_server 进程接收到请求后，向 zygote 进程发送创建进程的请求；
Zygote 进程 fork 出新的子进程，即 App 进程  
3、App 进程，通过 Binder IPC 向 sytem_server 进程发起 attachApplication 请求；  
4、system_server 进程在收到请求后，进行一系列准备工作后，再通过 binder IPC 向 App 进 程发送 scheduleLaunchActivity 请求  
5、App 进程的 binder 线程（ApplicationThread）在收到请求后，通过 handler 向主线程发送 LAUNCH_ACTIVITY 消息  
6、主线程在收到 Message 后，通过发射机制创建目标 Activity，并回调 Activity.onCreate()等方法

##### 生命周期

新页面在 onResume() 和 onStop() onCreate /onStart /onResume

##### 启动模式

### 四大组件-Fragment

##### 新建 Fragment 为何不要在构造方法中传递参数

[Fragment 传参用法](https://blog.csdn.net/stzy00/article/details/44764537/)

### APK 打包

###### APK 打包流程和其内容

## 性能优化

- https://www.jianshu.com/p/754f7607c869

#### 1.图片

###### 1.1 图片压缩

###### 1.2 图片压缩

#### 2.网络

###### 2.1 ListView 条目复用

## 框架问题

https://www.cnblogs.com/ldq2016/p/9035666.html
