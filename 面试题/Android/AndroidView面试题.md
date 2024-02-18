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