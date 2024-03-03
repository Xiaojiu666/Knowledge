#### Touch 事件分发机制(百度)
外部拦截：指的是在 ViewGroup 的 onInterceptTouchEvent() 方法中对触摸事件进行拦截处理。当一个 ViewGroup 检测到有触摸事件发生时，它可以决定是否拦截这个事件，即不将该事件传递给其子 View，而是直接在自身处理。
内部拦截：指的是在子 View 的 onTouchEvent() 方法中对触摸事件进行拦截处理。当子 View 检测到有触摸事件发生时，它可以决定是否拦截这个事件，即不将该事件传递给父 ViewGroup，而是在自身处理。
```
MainActivity dispatchTouchEvent
MyViewGroup dispatchTouchEvent
MyViewGroup onInterceptTouchEvent
MyView dispatchTouchEvent
MyView onTouchEvent
MyViewGroup onTouchEvent
MainActivity onTouchEvent
```

#### RecyclerView 的缓存机制
-   在 LayoutManager 中使用 setItemPrefetchEnabled() 方法开启预取功能，以提前加载屏幕外的数据，避免滑动卡顿现象，并且优化 RecyclerView 的缓存机制。

-   使用合适的布局管理器（LayoutManager）： 根据你的列表布局需求选择合适的布局管理器。例如，如果你的列表是线性排列的，可以选择 LinearLayoutManager；如果是网格排列的，可以选择 GridLayoutManager；如果是瀑布流式布局，可以选择 StaggeredGridLayoutManager。

-   设置适当的缓存大小： RecyclerView 有三种类型的缓存：View 缓存、Scrap 缓存和Recycler 缓存。可以通过设置 setViewCacheSize()、setRecycledViewPool()、setHasFixedSize() 等方法来调整缓存大小。

-   使用局部刷新（Partial Updates）： 当数据集发生变化时，尽量使用 notifyItemChanged()、notifyItemInserted()、notifyItemRemoved() 等方法来局部刷新列表项，而不是调用 notifyDataSetChanged() 方法刷新整个列表。

-   重用 ViewHolder： 在 onBindViewHolder() 方法中，尽量复用已经创建的 ViewHolder 对象，并更新其中的数据，而不是每次都创建新的 ViewHolder 对象。

-   优化布局文件： 减少布局文件中的嵌套层级和复杂度，可以提高列表项的渲染性能。

-   使用 DiffUtil： DiffUtil 是 Android Jetpack 中的一个工具类，用于计算并应用两个数据列表之间的差异。使用 DiffUtil 可以避免不必要的列表项重新绑定，从而提高性能。

-   避免在 onBindViewHolder() 方法中执行耗时操作： onBindViewHolder() 方法应该尽量保持轻量级，避免执行耗时操作，以确保列表滚动的流畅性。

-   关闭动画（如果不需要）： 如果不需要列表项的动画效果，可以通过 setItemAnimator(null) 方法关闭默认的动画，从而提高性能。

#### RecyclerView 优化
https://www.jianshu.com/p/bd432a3527d6


#### 为什么onCreate获取不到View的宽高
onCreate()方法是在Activity被创建时调用的，此时Activity的布局视图（View hierarchy）还没有完成创建和测量。

#### Android绘制和屏幕刷新机制原理
1. **View树的绘制**：
   - Android应用的UI界面通常由View树组成，每个View都可以包含子View。在绘制过程中，系统会从根View开始递归地调用每个View的draw()方法，将每个View绘制到屏幕上。
   
2. **绘制流程**：
   - 绘制流程一般分为三个阶段：测量（Measure）、布局（Layout）和绘制（Draw）。
     - Measure阶段：系统测量每个View的大小，并确定每个View的位置。
     - Layout阶段：系统根据测量结果来布局每个View的位置，确定每个View的位置和大小。
     - Draw阶段：系统将每个View绘制到屏幕上。
   
3. **View树的遍历**：
   - 在绘制过程中，系统会遍历整个View树，对每个View进行绘制。遍历的顺序一般是从上到下，从根View到叶子View。

4. **双缓冲机制**：
   - Android中使用双缓冲技术来实现屏幕绘制，即将绘制操作先绘制到一个离屏的Bitmap上，然后再将Bitmap绘制到屏幕上，以避免绘制过程中的闪烁和卡顿。

5. **刷新频率**：
   - Android设备的屏幕刷新率一般是60Hz，即每秒刷新60次。系统会在每次刷新时更新屏幕上的内容，以实现流畅的界面显示。

6. **View的绘制优化**：
   - 为了提高绘制效率和性能，Android提供了一些绘制优化技术，比如硬件加速、View的绘制缓存（View的缓存机制）、自定义绘制（自定义View）等。


#### View#post与Handler#post的区别
View#post() 和 Handler#post() 都可以将一个Runnable投递到消息队列中执行
1. post到不同的消息队列
- View#post()会将Runnable投递到与View关联的线程中的消息队列。对于主线程中的View,就是主线程的消息队列。
- Handler#post()会将Runnable投递到与Handler关联的线程中的消息队列,可以是主线程或其他线程。

2. 延时机制不同
- View#postDelayed()可以指定延时时间,在延时后才投递到消息队列。
- Handler#postDelayed()会立即投递消息,而在消息内部实现延时逻辑。

3. 生命周期限制
- View只在自身未被销毁时才可以成功post。
- Handler没有生命周期限制,只要所在线程存在,都可以post。

4. 执行次序
- View的post要按照添加顺序执行Runnable。 
- Handler的post顺序不确定,可能随时插入消息队列任意位置。


#### invalidate怎么局部刷新
```JAVA
// 确定需要刷新的区域
Rect dirtyRect = new Rect(left, top, right, bottom);
// 调用invalidate(Rect)方法刷新指定区域
view.invalidate(dirtyRect);
```


### Fragment

#### ViewPager2 和 ViewPager 的一些区别：
1. **支持方向性**：
   - ViewPager2 支持垂直滑动，而 ViewPager 只支持水平滑动。ViewPager2 可以通过设置 Orientation 属性来实现垂直滑动。
2. **更强大的布局管理**：
   - ViewPager2 采用 RecyclerView 来实现布局管理，这使得它拥有 RecyclerView 的强大功能，例如支持 RecyclerView.Adapter、LayoutManager 等。因此，ViewPager2 支持动态添加、移除、更新页面等操作，以及更灵活的布局管理。
3. **更好的性能**：
   - ViewPager2 相比 ViewPager 在性能上有所提升，主要是由于它使用了 RecyclerView 来管理布局和回收页面，从而减少了内存占用和页面重绘的次数。
4. **更简洁的 API**：
   - ViewPager2 提供了更简洁、更易用的 API，例如支持 FragmentStateAdapter 来管理 Fragment 页面，而不再需要像 ViewPager 那样使用 FragmentPagerAdapter 或 FragmentStatePagerAdapter。
5. **支持数据集变化监听**：
   - ViewPager2 支持注册数据集变化的监听器，当数据集发生变化时可以自动更新页面。
6. **对 RTL（Right-to-Left）布局的支持**：
   - ViewPager2 支持 RTL 布局，可以根据应用的布局方向自动调整页面的顺序。
7. **API 稳定性**：
   - ViewPager2 作为 ViewPager 的后续版本，拥有更稳定和更可靠的 API，而 ViewPager 在一些版本中存在一些问题和不一致性。


#### Fragment setArguments 
使用 Fragment.setArguments(Bundle) 方法传递参数是一种常见的做法，主要有以下几个原因：

1. **Fragment的生命周期**：Fragment 在配置变化（例如屏幕旋转）或者内存不足时可能会被销毁和重新创建，而通过 setArguments(Bundle) 方法传递参数可以保证在Fragment重新创建时能够恢复之前的参数状态。

2. **解耦性**：通过将参数封装到 Bundle 中传递给 Fragment，可以实现参数与 Fragment 的解耦，使得 Fragment 更加独立和可重用。

3. **安全性**：将参数打包成 Bundle 后传递给 Fragment，可以保证参数的安全性，避免直接将参数暴露在外部，从而防止参数被意外修改或者篡改。

4. **可扩展性**：通过 Bundle 可以传递各种类型的参数，包括基本数据类型、Parcelable 对象、Serializable 对象等，具有很强的扩展性。

5. **性能优化**：传递参数时，Bundle 内部是采用序列化的方式进行传递，而不是直接传递对象的引用，这样可以避免对象的直接传递导致的不可预料的问题，同时也可以提高性能。


#### FragmentPagerAdapter和FragmentStatePagerAdapter的区别是:

1. 内存占用
- FragmentPagerAdapter会长期保留所有Fragment对象,更占内存。
- FragmentStatePagerAdapter只保留当前页面,会销毁掉屏幕外的Fragment,更省内存。

2. 复用机制
- FragmentPagerAdapter中的Fragment会一直保留,相当于put/remove,不会重新创建。
- FragmentStatePagerAdapter中的Fragment会被销毁并重建,相当于new实例。

3. 切换性能
- FragmentPagerAdapter由于是put/remove,切换会更流畅。
- FragmentStatePagerAdapter需要重新创建,切换相对比较耗时。

4. 适用场景
- 当Fragment比较复杂时,FragmentPagerAdapter更合适,避免重复创建的开销。
- 当Fragment较简单且页面较多时,建议使用FragmentStatePagerAdapter。


#### getFragmentManager、getSupportFragmentManager 、getChildFragmentManager
getFragmentManager() 用于获取 Activity 中的 FragmentManager（适用于 API 级别 11 及以上），getSupportFragmentManager() 用于获取支持包中的 FragmentManager（适用于向后兼容到较旧的 Android 版本），而 getChildFragmentManager() 用于获取 Fragment 中的子 FragmentManager。

### 动画

#### 插值器 和估值器

- 插值器控制动画播放速度;估值器计算动画值。

- 插值器作用于ValueAnimator;估值器用于ObjectAnimator。
