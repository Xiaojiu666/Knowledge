---
title: RecyclerView_缓存机制Recycler
date: 2022-05-01 16:05:41
categories:
           - 源码学习
tags:
           - RecyclerView
           - 自定义View
---

### Recycler
###### 官方注释
Recycler负责管理报废或分离的itemview以供重用。  
一个“scrapped”的view仍然会附加到他的父RecyclerView的视图里，但会标记为删除或重用。
RecyclerView对recycle的典型使用。LayoutManager将用于获取表示给定位置或项目ID上的数据的适配器数据集的视图。如果要重用的视图被认为是"脏的(是指那些在展示之前必须重新绑定的视图，比如一个视图原来展示的是“张三”，之后需要展示“李四”了，那么这个视图就是脏视图，需要重新绑定数据后再展示的。)"，适配器将被要求重新绑定它。如果不是，则无需进一步工作，LayoutManager就可以快速重用该视图。没有请求布局的干净视图可以由LayoutManager重新定位，而不需要重新测量。


### Recycler的四层缓存
我们知道，在编写RecyclerView的Adapter时，需要为其绑定一个ViewHolder，用于将ItemView的复用，减少布局的创建，减少内存开销，但其根本原因就在于下面的几个集合，而Recycler就是负责管理的类。

###### 1、Scrap(notifyItem增/删/改)
Scrap是RecyclerView中最轻量的缓存，它不参与滑动时的回收复用，只是作为重新布局时的一种临时缓存，缓存（保存）动作只发生在重新布局时，布局完成后就要清空缓存。它的作用是配合局部刷新方法，进行布局复用，将发生改变的Item 和未发生的改变的Item区分开，根据`notify**`方法传入的索引，mChangeScrap存放标记索引的ViewHolder，mAttachedScrap未标记，从而进行缓存区分。
![image.png](https://upload-images.jianshu.io/upload_images/5442795-ef36b25a67aa868a.png)
- mChangeScrap  存放标记索引的ViewHolder
- mAttachedScrap  存放未标记索引的ViewHolder

###### 2、Cache(滑动缓存) （mCachedViews）
保存刚被移出屏幕的holder, 默认容量2 （可理解为上下各一个），通过position来保存，数据不变，直接复用。滑动时，该缓存一边add，一边remove。

###### 3、Extension （mViewCacheExtension）
可自定义的缓存实现，默认空实现，基本用不上。

###### 4、Pool （mRecyclerPool）
与前两者不同，RecycledViewPool在进行回收的时候，目标只是回收一个该viewType的ViewHolder对象，并没有保存下原来ViewHolder的内容，在保存之前会进行ViewHolder的格式化清空数据内容，因为清空后的ViewHolder都是一样的，所以它只保存前五个，后面的直接丢掉，并没有使用LRU缓存逻辑，在复用时，将会调用bindViewHolder() 按照我们在onBindViewHolder()描述的绑定步骤进行重新绑定，从而摇身一变变成了一个新的列表项展示出来。
同样，RecycledViewPool也有一个最大数量限制，默认情况下是5。在没有超过最大数量限制的情况下，Recycler会尽量把将被废弃的ViewHolder回收到RecycledViewPool中，以期能被复用。值得一提的是，RecycledViewPool只会按照ViewType进行区分，只要ViewType是相同的，甚至可以在多个RecyclerView中进行通用的复用，只要为它们设置同一个RecycledViewPool就可以了。



### 通过LinerLayoutManager了解四级缓存
为RecyclerView定义一个LinerLayoutManager布局管理,里面加载20个item，通过AS自带的布局检测工具，我们发现，RecyclerView只加载了六个ItemView，每次上下滑动时，RecyclerView的ItemView并不会新增，这就取决于RecyclerView的缓存机制。我们需要通过`onLayoutChildren()`方法，先了解首次摆放时数据的来源。
![image.png](https://upload-images.jianshu.io/upload_images/5442795-c376f2bf527aa008.png)

#### onLayoutChildren()方法
调用时机: adapter首次初始化或刷新时notifyItemChanged() 时会触发，算是外部对子布局摆放的一个入口

//官方建议的步骤:    
//布局算法：  
//1）通过检查子项和其他变量，找到锚点坐标和锚点项目位置。  
//2）向起点填充，从底部堆叠  
//3）向末端填充，从顶部堆叠  
//4）滚动以满足要求，如从底部堆叠。  
// 创建布局状态  
锚点:可以理解为整个布局起点(是否从底部开始/坐标等)   
虽然`onLayoutChildren()`方法代码很多，但是我们唯一需要关注的只有一个方法`fill()`，来自官方的注释`The magic functions :)`
```Java
// 整个fill方法，就是在为我们填充RecyclerView中的Item
int fill(RecyclerView.Recycler recycler, LayoutState layoutState,
           RecyclerView.State state, boolean stopOnFocusable) {
       // 通过LayoutState 获取可用空间 第一次为RecyclerView得大小
       final int start = layoutState.mAvailable;
       //剩余空间，每次增加一个View 会重新计算,如果剩余的空间为负数，停止查询
       int remainingSpace = layoutState.mAvailable + layoutState.mExtraFillSpace;
        //布局Item后的结果
       LayoutChunkResult layoutChunkResult = mLayoutChunkResult;
       // layoutState.hasMore(state) 是否有更多需要绘制得Item，这里只是一个状态，不涉及Item得缓存
       while ((layoutState.mInfinite || remainingSpace > 0) && layoutState.hasMore(state)) {
           //又一个核心，摆放关键方法布局块
           layoutChunk(recycler, state, layoutState, layoutChunkResult);
           // 拜访完毕后，重置剩余空间
           if (!layoutChunkResult.mIgnoreConsumed || layoutState.mScrapList != null
                   || !state.isPreLayout()) {
               layoutState.mAvailable -= layoutChunkResult.mConsumed;
               remainingSpace -= layoutChunkResult.mConsumed;
           }
       //...
       return start - layoutState.mAvailable;
   }
```
至此，我们已经找到了`LinerLayoutManager`中的入口,但还是没有了解到缓存机制，我们需要深入的了解`layoutChunk()`方法找到我们ItemView的来源。
```Java
void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state,
        LayoutState layoutState, LayoutChunkResult result) {
    //ItemView的来源      
    View view = layoutState.next(recycler);
    ...
}
```
通过层层调用，最终指向了`Recycler`中的`tryGetViewHolderForPositionByDeadline()`方法。  

### tryGetViewHolderForPositionByDeadline()
该方法的参数和返回值也很清晰，根据索引查找当前数据对应的`ViewHolder()`  
接下来我们围绕这个源码进行抽丝剥茧
```java
ViewHolder tryGetViewHolderForPositionByDeadline(int position,
               boolean dryRun, long deadlineNs) {
           ...
           ViewHolder holder = null;
           // mState.isPreLayout() 可以理解为局部刷新还是全局刷新的开关
           //1、Scrap-
           if (mState.isPreLayout()) {
               //从mChangedScrap里面查询
               holder = getChangedScrapViewForPosition(position);
               fromScrapOrHiddenOrCache = holder != null;
           }
           if (holder == null) {
               //从mAttachedScrap里面查询
               holder = getScrapOrHiddenOrCachedHolderForPosition(position, dryRun);
           }
           //2、滑动缓存
           if (holder == null) {
               //获取偏移的
               final int offsetPosition = mAdapterHelper.findPositionOffset(position);
               final int type = mAdapter.getItemViewType(offsetPosition);
               if (mAdapter.hasStableIds()) {
                  //滑动缓存
                  //1、先从mAttachedScrap查找 防止先删除在滑动，并且滑动距离较短的
                  //2、再从 mCachedViews 查找
                  holder=getScrapOrCachedViewForId(mAdapter.getItemI
               }
               //3、自定义缓存 Extension
               if (holder == null && mViewCacheExtension != null) {
                   final View view = mViewCacheExtension
                           .getViewForPositionAndType(this, position, type);
                   if (view != null) {
                       holder = getChildViewHolder(view);
                   }
               }
               //4、ViewPool
               if (holder == null) {
                   holder = getRecycledViewPool().getRecycledView(type);
                   if (holder != null) {
                       holder.resetInternal();
                       if (FORCE_INVALIDATE_DISPLAY_LIST) {
                           invalidateDisplayListInt(holder);
                       }
                   }
               }
               //5.直接创建
               if (holder == null) {
                   long start = getNanoTime();
                   if (deadlineNs != FOREVER_NS
                           && !mRecyclerPool.willCreateInTime(type, start, deadlineNs)) {
                       // abort - we have a deadline we can't meet
                       return null;
                   }
                   holder = mAdapter.createViewHolder(RecyclerView.this, type);
                   if (ALLOW_THREAD_GAP_WORK) {
                       // only bother finding nested RV if prefetching
                       RecyclerView innerView = findNestedRecyclerView(holder.itemView);
                       if (innerView != null) {
                           holder.mNestedRecyclerView = new WeakReference<>(innerView);
                       }
                   }

                   long end = getNanoTime();
                   mRecyclerPool.factorInCreateTime(type, end - start);
               }
           }
           //...
           //...
           return holder;
       }
```

###### 1 、Scrap（mChangedScrap、mAttachedScrap）
通过断点我们验证一下1.1 和1.2 的总结   
1、我们在Adapter中和List数组中，皆移除第1位索引。
![image.png](https://upload-images.jianshu.io/upload_images/5442795-cae1f1479782926e.png)
此时断点发现,被移除的第一位索引对应的ViewHolder，确实被加入了mChangeScrap中，而其他的ViewHolder，则被加入为mAttachedScrap。  

###### 2、Cache （mCachedViews）
mCacheViews的缓存动作发生在滑动时，当有Item滑出屏幕外，就会原封不动的保存到mCacheViews中，复用动作发生在滑动回来的时候，场景是当上下小距离滑动时，刚划出去的Item又划回来，不用再重新创建和重新绑定数据。
注意mCachedViews是有大小限制的，默认最大是2。

###### 3、Extension （mViewCacheExtension）
可自定义的缓存实现，默认空实现，基本用不上。

###### 4、Pool （mRecyclerPool）
RecycledViewPool是最后一层缓存:
保存已被移出屏幕的无效的holder，默认容量5，可以自己new一个pool传进去（一般在RV嵌套时，子RV与父RV可共用同一个Pool），mRecyclerPoor保存第二级缓存中保存不了的ItemView。通过itemType来保存，每种itemType可以保存5个ItemView。
SparsArray:mScarp 默认容量为10，根据key,value的形式存储scarpdata,每个viewType 对应一个scarpData,那么数据便是ScarpData:
```Java
ArrayList<ViewHodler> mScarpHeap  
int mMaxScarp
long mCreateRunningAverageNs
long mBindRunningAverageNs
```
mScarpHeap 主要存储的就是每个viewholder,默认大小为5，可以修改存储最大值。
最终结果就是一个RecyclerView最多能存储的ViewHolder数量是：5 * 10 = 50个

开发中经常有这种场景：我们有多个页面，使用ViewPager结合Fragment来切换，例如新闻列表分类页面，每个Fragment内部都是RecycledView。如果多个页面存在相同类型的ViewHolder ，我们可以让各个RecycledView共享一个池。适用于RecyclerView的优化
```Java
  RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
  recyclerView1.setRecycledViewPool(pool);
  recyclerView2.setRecycledViewPool(pool);
  recyclerView3.setRecycledViewPool(pool);
```

### 总结
至此，关于RecyclerView的缓存，已经了解的差不多了，其实源码看下来，逻辑并不复杂，与其说是四级缓存，倒不如说是，根据不同的使用情况进行不同的复用。有些地方讲的不是很细致，还望多多理解，希望通过此篇文章，不仅可以可以帮助大家了解RecyclerView 的缓存机制，还能帮助大家理解LayoutManger Adapter之间的联动关系，还是建议大家有空多读下源码，增强架构思路的学习。


### 参考资料
- [源码版本] androidx.recyclerview:recyclerview:1.2.1
