### RecyclerView.LayoutManager


### 自定义LayoutManger

###### 继承 抽象静态内部类RecyclerView.LayoutManager
不了解ViewGroup的同学建议百度搜一下,网上教程会更细  
主要是容器布局，为子View 分配宽高，绘制子View的位置

###### 实现ViewDropHandler 接口
1.计算水平/垂直滚动范围  
2.计算水平/垂直滚动偏移  
3.计算水平/垂直滚动面积  

###### ScrollVectorProvider 接口
1、computeScrollVectorForPosition  
根据索引计算滑动位置



### 构造
-  第一个和第二个构造基本一致通过New 传递属性
-  第三个构造，用到很少，根据布局文件配置获取属性

###### setOrientation

###### setReverseLayout
设置是否反转布局   
如果是垂直布局,将布局上下反转0..10 ->10..0，并将焦点移动到最下面的0上面


### final 类Recycler
