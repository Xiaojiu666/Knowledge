##	一、介绍功能

##	二、分析需求

#	三、设计业务逻辑

####	1 实现层级布局:通过继承RecyclerView.LayoutManager打造类似于Linnerlayoutmanager
##### 1.1 实现generateDefaultLayoutParams()方法

		实现generateDefaultLayoutParams，作用:ViewGroup中方法，用于获取子ViewGroup的
##### 1.2 实现onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)方法
		此方法通过父类调用，像子类发送两个参数，Recycler的对象和状态对象，方便对子View的管理

###2 通过onLayoutChildren()方法进行子View布局管理
##### 2.1 detachAndScrapAttachedViews(recycler)方法
		在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
##### 2.2 getItemCount() 获取适配器中条目个数
##### 2.3 根据索引通过recycler对象中getViewForPosition方法获取适配器中的View的对象
##### 2.4 通过addView将 获取的View对象添加到RCV中
##### 2.5 通过getDecoratedMeasuredWidth(view)/getDecoratedMeasuredHeight(view)方法获取View对象的宽/高
##### 2.6 通过layoutDecoratedWithMargins（view，left，top，right，bottom）进行View的布局绘制，
##### 2.7  

###3 实现滑动消除View:通过谷歌提供的ItemTouchHelper抽象类,此类主要通过监听用户对于RecyclerView的OnItemTouchListener方法监听item的触摸事件     drag:拖拽  swipe:滑动

##### 3.1 new ItemTouchHelper.Callback()抽象类 主要用于返回用户对于RecyclerView的各种触碰事件
##### 3.2 getMovementFlags()方法 用于配置item的 滑动和拖拽的方向
##### 2.1 onMove()方法 对于选择条目对象的移动、位置进行监听
##### 2.1 onSwiped() item通过动画消失的时候调用，此处多用于对item集合内的数据进行移除
##### 2.1 onChildDraw() 针对swipe和drag状态，整个过程中一直会调用这个函数,随手指移动的view就是在super里面做到的(和ItemDecoration里面的onDraw()函数对应)
##### 2.1 clearView() 针对swipe和drag状态，当一个item view在swipe、drag状态结束的时候调用 drag状态：当手指释放的时候会调用swipe状态：当item从RecyclerView中删除的时候调用，一般我们会在onSwiped()函数里面删除掉指定的item view

##### 2.1
##### 2.1


##	参考资料
-		1.https://www.jianshu.com/p/b873b717d1ea
