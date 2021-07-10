#一、View相关介绍
##1.简介
	为什么要自定义View，

##2.类
###2.1 

###2.2 AttributeSet 
###2.3 Canvs(画布)
###2.3 Rect(矩阵)
###2.3 matrix(矩阵)
###2.3 ColorFilter()	

##3.方法
###3.1 构造
	1.直接创建对象的时候使用
	2.在布局文件中使用
	3.当有自定义属性的时候使用

###3.3 onDraw()
	



----------


###3.3 onLayout()
####3.3.1 onLayout() 介绍
	主要功能为摆放View的位置，view对外暴露的方法只能得到部件的位置，并不能去设置位置，主要是由viewGroup进行摆放，自定义的控件并不能随意摆放自己。

----------

###3.4 onMeasure()
####3.4.1 onMeasure()介绍
	主要测量目的是为了通知父控件自己的大小和模式 ，一般自定义View不处理由View的OnMeasure 进行摆放
	onMeasure方法的作用时测量空间的大小，什么时候需要测量控件的大小呢？
	我们举个栗子，做饭的时候我们炒一碗菜，炒菜的过程我们并不要求知道这道菜有多少分量，只有在菜做熟了我们要拿个碗盛放的时候，我们才需要掂量拿多大的碗盛放，这时候我们就要对菜的分量进行估测。

	而我们的控件也正是如此，创建一个View(执行构造方法)的时候不需要测量控件的大小，
	只有将这个view放入一个容器（父控件）中的时候才需要测量，而这个测量方法就是父控件唤起调用的。
	当控件的父控件要放置该控件的时候，父控件会调用子控件的onMeasure方法询问子控件：“你有多大的尺寸，我要给你多大的地方才能容纳你？”，
	然后传入两个参数（widthMeasureSpec和heightMeasureSpec），这两个参数就是父控件告诉子控件可获得的空间以及关于这个空间的约束条件（好比我在思考需要多大的碗盛菜的时候我要看一下碗柜里最大的碗有多大，菜的分量不能超过这个容积，这就是碗对菜的约束），子控件拿着这些条件就能正确的测量自身的宽高了。

####3.4.2 测量规格相关介绍
	1.widthMeasureSpec & heightMeasureSpec 不是宽高的值，是由模式+宽高的值组合起来的
	2.在控件没有父布局之前，MeasureSpec 有三种模式:
	------二进制表示：00
   

    EXACTLY：表示父控件给子view一个具体的值，子view要设置成这些值的大小。
    ------直接数值 && match_parent
    ------二进制表示：01
	------ 1073741824
	父控件给子控件决定了确切大小，子控件将被限定在给定的边界里而忽略它本身大小。
	特别说明如果是填充父窗体，说明父控件已经明确知道子控件想要多大的尺寸了（就是剩余的空间都要了）
	栗子：碗柜最大的碗就这么大，菜有多少都只能盛到这个最大的碗里，盛不下的我就管不了了（吃掉或者倒掉）


    AT_MOST：表示父控件个子view一个最大的特定值，而子view不能超过这个值的大小。
    ------wrap_content
    ------二进制表示：10
	------ -2147483648
	子控件至多达到指定大小的值。包裹内容就是父窗体并不知道子控件到底需要多大尺寸（具体值），
	需要子控件自己测量之后再让父控件给他一个尽可能大的尺寸以便让内容全部显示但不能超过包裹内容的大小
	栗子：碗柜有各种大小的碗，菜少就拿小碗放，菜多就拿大碗放，但是不能浪费碗的容积，要保证碗刚好盛满菜
	在控件没有父布局之前

	3.在控件有父布局的时候
		父控件会调用子控件的onMeasure方法询问子控件：“你有多大的尺寸，我要给你多大的地方才能容纳你？
		然后传入两个参数（widthMeasureSpec和heightMeasureSpec）两个规格参数是由父控件计算好的，根据父控件
		和子控件的宽高布局来进行及计算
		父为EXACTLY(匹配&&精确值)，子为匹配&&精确值属性时，子约束为EXACTLY,反之子为包裹时，子约束为At_Most
        父为At_Most(包裹)，子为精确值属性时，子约束为EXACTLY 反之子为包裹时&&匹配时，子约束为At_Most
		父为UNSPECIFIED时，子为精确值属性时，子约束为EXACTLY 反之子为包裹时&&匹配时，子约束为UNSPECIFIE


####3.4.3 源码流程
	1.测量控件大小是父控件发起的
	父控件要测量子控件大小，需要重写onMeasure方法，然后调用measureChildren或者measureChildWithMargin方法
	on Measure方法的参数是通过getChildMeasureSpec生成的
	如果我们自定义控件需要使用wrap_content，我们需要重写onMeasure方法

	2.测量控件的步骤：
	父控件onMeasure->measureChildrenmeasureChildWithMargin->getChildMeasureSpec-> 子控件的measure->onMeasure->setMeasureDimension-> 父控件onMeasure结束调用setMeasureDimension保存自己的大小

![](https://xiaojiu-daily.oss-cn-beijing.aliyuncs.com/onMeasure%E5%A4%84%E7%90%86.jpg)
	

####3.4.4 使用场景
	自定义View时，如果不去重写onMeasure时，会发生一个问题，就是当view父控件matchparnt时，如果自定义控件包裹，自定义View的布局还是填充父控件，这个时候需要在onMeasure里对规格和尺寸进行重新定义，

	`// 1.自定义包裹类型*/
        int width;
        if (widthSpecMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            width = widthSpecSize;
        } else {
            width = 100;
            //根据需求内容自定义包裹的宽高
        }`


----------



#二、自定义属性
##步骤:
- 1.配置：在res/values中的attrs.xml中自定义属性。

    
   		 <declare-styleable name="TestView"> 
    		<attr name="attrone" format="dimension"/>
    			<attr name="attrtwo" format="string" > 
    			<enum name="one" value="0"/> 
    		<enum name="two" value="1"/> 
    		</attr> 
 	   	</declare-styleable>
    

- 2.使用：首先加入命名空间：xmlns:app="http://schemas.android.com/apk/res-auto"

	    <com.mg.axe.androiddevelop.view.TestView 
			android:layout_width="match_parent" 
			android:layout_height="match_parent" 
			app:attrone="10dp" 
			app:attrtwo="two" />


- 3.自定义View里获取自定义属性

		TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TestView); 
		float attrone = ta.getDimension(R.styleable.TestView_attrone,0); 
		String attrTwo = ta.getString(R.styleable.TestView_attrtwo); 


- 4.赋值





#三、参考资料
- 1.https://blog.csdn.net/xmxkf/article/details/51468648      //Android自定义View（二、深入解析自定义属性）
- 2.https://blog.csdn.net/wwww_dong/article/details/53910776  //自定义控件之onLayout()
- 3.https://www.jianshu.com/p/049df709ddbf					  //window、Activity、DecorView、ViewRoot关系
- 4.https://www.jianshu.com/p/a5b1e778744f					  //自定义View-onLayout篇
- 5.https://github.com/openXu/OXChart						  //自定义Viewchart
- 6.https://www.cnblogs.com/wjtaigwh/p/6647114.html			  //贝塞尔曲线



