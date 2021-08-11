## 一、介绍

## 二、常见方法
####  1、

## 三、常见属性
####  1、
######  1、SCROLL_STATE_TOUCH_SCROLL   //正在滚动
######  1、SCROLL_STATE_FLING          //开始滚动
######  1、SCROLL_STATE_IDLE           //已经停止



## 四、拓展使用
#### 1、https://www.jianshu.com/p/4a647f6cbe42 //

####  abstract class SnapHelper
    SnapHelper是一个抽象类，用于控制RecyclerView手势滚动停止时某个子Item停留的位置。

###### class PagerSnapHelper extends SnapHelper
    RecyclerView滚动停止时相应的Item停留中间位置，一次可能滑多个页面，只是控制最终显示时的条目居中对齐

-   findTargetSnapPosition

-   findSnapView

-   calculateDistanceToFinalSnap


###### class LinearSnapHelper extends SnapHelper
    RecyclerView像ViewPager一样的效果，一次只能滑一页，而且居中显示。


######  什么是Fling？
    首先来了解一个概念，手指在屏幕上滑动RecyclerView然后松手，
    RecyclerView中的内容会顺着惯性继续往手指滑动的方向继续滚动直到停止，
    这个过程叫做Fling。Fling操作从手指离开屏幕瞬间被触发，在滚动停止时结束。


######  
