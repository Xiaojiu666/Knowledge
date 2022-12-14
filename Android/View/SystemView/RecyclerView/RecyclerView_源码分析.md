### RecyclerView类
###### 继承ViewGroup
不了解ViewGroup的同学建议百度搜一下,网上教程会更细  
主要是容器布局，为子View 分配宽高，绘制子View的位置

###### ScrollingView 接口
1.计算水平/垂直滚动范围  
2.计算水平/垂直滚动偏移  
3.计算水平/垂直滚动面积  

###### NestedScrollingChild2接口


### 构造函数
###### 三个参数构造
和我们自定义View无任何区别，第一个构造New的时候调用，第二个解析清单文件时调用，第三个如果Activity指定theme的时候会调用。  
RecyclerView 在第三个构造函数里初始化属性与方法。


### 方法
通常使用时，我们只需要调用两个方法`setLayoutManager` 和`setAdapter`，就可以正常显示我们需要的列表了
###### setLayoutManager 发现两个问题
通过setLayoutManager 发现两个问题  
1、如果layoutManger 不等于上一次绑定的对象时，就会清除并停止之前的操作(滑动状态\滑动效果\动画等)  
2、如果layoutManger 已经绑定其他的RecyclerView ，那么将会抛出异常。


###### setAdapter
1、不冻结RecyclerView状态

### 抽象静态内部类RecyclerView.LayoutManager
