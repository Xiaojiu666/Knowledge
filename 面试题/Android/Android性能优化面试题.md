
### android 性能优化

优化 Android 应用的性能是一个广泛的话题，涉及多个方面，包括内存管理、UI渲染、网络请求、数据存取等。以下是一些常见的优化技巧：

1. **布局优化**：使用 `ConstraintLayout` 替代 `RelativeLayout` 或 `LinearLayout`，避免使用过深的视图层次结构，合理使用 `ViewStub` 和 `Merge` 标签来减少布局层次。

2. **图片优化**：使用合适大小和格式的图片，避免过大的图片资源，尽可能使用矢量图（Vector Drawable），并对图片进行压缩处理。

3. **内存管理**：及时释放不再使用的对象，避免内存泄漏，使用内存分析工具（如 Android Studio 中的 Memory Profiler）来检测和解决内存问题。

4. **网络请求优化**：减少网络请求次数，合并请求，使用缓存减少重复请求，使用合适的网络库（如 Retrofit）并进行网络请求的异步处理。

5. **数据存取优化**：使用合适的数据存储方式（如 SharedPreferences、SQLite、Room 等），对数据库操作进行批量处理、异步处理等优化。

6. **UI渲染优化**：使用 `ViewHolder` 模式优化 `ListView`、`RecyclerView` 等列表视图的性能，避免在 UI 线程进行耗时操作，使用异步任务进行处理。

7. **代码优化**：避免频繁的对象创建和销毁，使用静态变量或单例模式合理管理全局对象，避免使用过多的匿名内部类等。

8. **使用性能分析工具**：利用 Android Studio 自带的性能分析工具（如 Profiler）来监控应用的 CPU、内存、网络等性能指标，找出性能瓶颈并进行优化。

9. **适配优化**：针对不同的设备和屏幕尺寸进行适配优化，避免过度绘制（Overdraw）等问题。

10. **混淆优化**：在发布应用时使用代码混淆工具（如 ProGuard）对代码进行混淆，减小 APK 大小并增加安全性。

这些只是优化 Android 应用性能的一些常见技巧，实际上，性能优化是一个持续改进的过程，需要根据具体应用的情况进行分析和优化。

##### App内存优化
- 1、非静态内部类会隐式地持有外部类的引用，容易导致内存泄漏。使用静态内部类避免这种情况。
	对于某些长生命周期的对象，使用WeakReference可以避免持有对象的强引用，防止内存泄漏。
	```JAVA
	static class MyHandler extends Handler {
		private final WeakReference<MyActivity> mActivity;

		MyHandler(MyActivity activity) {
			mActivity = new WeakReference<>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			MyActivity activity = mActivity.get();
			if (activity != null) {
				// Handle message
			}
		}
	}
	```
- 2、 及时清理资源
释放Bitmap
Bitmap是Android中占用内存较大的对象，使用完毕后应及时调用recycle()方法释放内存。
关闭流
及时关闭文件流
- 3、使用内存缓存 LruCache  
	```JAVA
	int cacheSize = 4 * 1024 * 1024; // 4MiB
	LruCache<String, Bitmap> bitmapCache = new LruCache<>(cacheSize);
	bitmapCache.put(key, bitmap);
	Bitmap bitmap = bitmapCache.get(key);
	```
- 4、避免内存抖动
	减少内存创建，合理的在需要的时候再去创建内存
	使用依赖注入维护对象

- 5、使用工具监控内存
	Android Profiler
	LeakCanary

- 6、使用合适的数据结构
	选择合适的数据结构可以节省内存。例如，使用SparseArray代替HashMap。

- 7、避免使用内存密集型组件
	使用合适的布局
	避免使用嵌套过深的布局，使用ConstraintLayout替代多层级布局，减少内存开销。
- 8、管理大文件和资源
	分析和压缩资源
	buildTypes {
    release {
        minifyEnabled true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
#### 图片压缩
```Java
    /**
	- 根据指定的文件路径以及所需的宽度和高度对图片进行采样率压缩
	- @param filename 图片文件的路径
	- @param reqWidth 所需的宽度
	- @param reqHeight 所需的高度
	- @return 压缩后的 Bitmap 对象
	*/
	public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight) {
	// 首先获取 Bitmap 的原始尺寸信息
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 设置该选项为 true，仅获取尺寸信息，不加载 Bitmap 到内存中
		BitmapFactory.decodeFile(filename, options);
		// 根据原始尺寸信息和所需的尺寸计算采样率
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// 使用计算出的采样率加载Bitmap
		options.inJustDecodeBounds = false; // 设置该选项为false，加载Bitmap到内存中
		return BitmapFactory.decodeFile(filename, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
				// 计算图片原始尺寸的一半
				final int halfHeight = height / 2;
				final int halfWidth = width / 2;

				// 如果图片的一半尺寸仍然比所需的尺寸大，则继续缩小采样率
				while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
					inSampleSize *= 2; // 采样率每次翻倍
				}
		}

		return inSampleSize;
	}

```

#### 内存泄露问题

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

- 6.WebView 导致的内存泄漏

      WebView比较特殊，即使是调用了它的destroy方法，依然会导致内存泄漏。其实避免WebView导致内存泄漏的最好方法就是
      让WebView所在的Activity处于另一个进程中，当这个Activity结束时杀死当前WebView所处的进程即可，我记得阿里钉钉的WebView就是另外开启的一个进程，应该也是采用这种方法避免内存泄漏。
- 7.使用内存分析工具：
	使用Android Studio提供的内存分析工具（如Memory Profiler）来检测内存泄漏问题，并定位泄漏的原因。
	优化方法：使用内存分析工具来识别应用程序中的内存泄漏，并采取适当的措施解决这些问题。
	通过以上优化方法，可以有效地减少Android应用程序中的内存泄漏问题，提高应用程序的稳定性和性能。 	

- 8 ViewModel 的内存泄漏问题
	ViewModel 的内存泄漏是指 Activity 已经销毁，但是 ViewModel 却被其他组件引用。这往往是因为数据层是通过回调监听器的方式返回数据，并且数据层是单例对象或者属于全局生命周期，所以导致 Activity 销毁了，但是数据层依然间接持有 ViewModel 的引用。
	如果 ViewModel 是轻量级的或者可以保证数据层操作快速完成，这个泄漏影响不大可以忽略。但如果数据层操作并不能快速完成，或者 ViewModel 存储了重量级数据，就有必要采取措施。例如：

	方法 1： 在 ViewModel#onCleared() 中通知数据层丢弃对 ViewModel 回调监听器的引用；
	方法 2： 在数据层使用对 ViewModel 回调监听器的弱引用（这要求 ViewModel 必须持有回调监听器的强引用，而不能使用匿名内部类，这会带来编码复杂性）；
	方法 3： 使用 EventBus 代替回调监听器（这会带来编码复杂性）；
	方法 4： 使用 LiveData 的 Transformations.switchMap() API 包装数据层的请求方法，这相当于在 ViewModel 和数据层中间使用 LiveData 进行通信。例如：