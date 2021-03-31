package com.sn.module_account_book.ui.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.sn.plugin_base.mvp.AnimatorUtils

/**
 * Created by GuoXu on 2020/10/19 17:22.
 * ? 可空 ，不崩
 * !! 不能空 ， 空指针
 */
class SlidingExpansionView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context!!, attrs, defStyleAttr) {
    private var viewDragHelper: ViewDragHelper? = null
    private var mBgView: View? = null
    private var mChildSlidingView: View? = null

    private var slidingOpenState: Boolean = false

    private fun initView() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, SlidingViewDragHelper(this))
        viewDragHelper?.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
        viewDragHelper?.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper!!.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper!!.processTouchEvent(event)
        return true
    }

    inner class SlidingViewDragHelper(private val slidingExpansionView: SlidingExpansionView) :
        ViewDragHelper.Callback() {

        /**
         * 告诉ViewDragHelper，哪个View 可以滑动
         *
         * @param child
         * @param pointerId
         * @return
         */
        override fun tryCaptureView(
            child: View,
            pointerId: Int
        ): Boolean {
            return child === mChildSlidingView
        }

        /**
         * 告诉ViewDragHelper，哪个View 横向最大/最小可以滑动距离
         *
         * @return
         */
        override fun clampViewPositionHorizontal(
            child: View,
            left: Int,
            dx: Int
        ): Int {
            return 0
        }

        /**
         * 告诉ViewDragHelper，哪个View 纵向最大/最小可以滑动距离
         *
         * @return
         */
        override fun clampViewPositionVertical(
            child: View,
            top: Int,
            dy: Int
        ): Int {
            val i = height - child.height
            val q1 = mBgView!!.measuredHeight

            return top.coerceAtLeast(q1 / 2).coerceAtMost(q1)
        }

        /**
         * 某个View手指释放的时候回调 xvel yvel 是速度
         */
        override fun onViewReleased(
            releasedChild: View,
            xvel: Float,
            yvel: Float
        ) {
            if (releasedChild === mChildSlidingView) {
                val top = mChildSlidingView!!.top
                val centerX = mBgView!!.measuredHeight * 3 / 4
                val moveTop: Int
                moveTop = if (top >= centerX) {
                    mBgView!!.measuredHeight
                } else {
                    mBgView!!.measuredHeight / 2
                }
                viewDragHelper!!.settleCapturedViewAt(0, moveTop)
                invalidate()
            }
        }

        /**
         * 在边界移动式
         *
         * @param edgeFlags
         * @param pointerId
         */
        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            //通过captureChildView对其进行捕获，该方法可以绕过tryCaptureView
            viewDragHelper!!.captureChildView(mChildSlidingView!!, pointerId)
        }

        /**
         * 解决ziView 设置点击事件和当前滑动冲突
         *
         * @param child
         * @return
         */
        override fun getViewHorizontalDragRange(child: View): Int {
            return measuredWidth - child.measuredWidth
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return measuredHeight - child.measuredHeight
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
//            super.onViewPositionChanged(changedView, left, top, dx, dy)
            slidingOpenState = top == mBgView?.measuredHeight
            Log.e(
                TAG,
                "left : $left , top :$top , dx : $dx , dy : $dy , slidingStauts : $slidingOpenState"
            )
        }
    }

    /**
     * 完成绘制
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onFinishInflate() {
        super.onFinishInflate()
        mBgView = getChildAt(0)
        mChildSlidingView = getChildAt(1)
//        mChildSlidingView?.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
//            Log.d(
//                TAG,
//                "onFinishInflate new: left : $left , top : $top ,right : $right, bottom : $bottom"
//            )
//            Log.d(
//                TAG,
//                "onFinishInflate old: oldLeft : $oldLeft , oldTop : $oldTop ,oldRight : $oldRight, oldBottom : $oldBottom"
//            )
//        }
        mChildSlidingView?.setOnScrollChangeListener { view, i, i2, i3, i4 ->
            Log.d(
                TAG,
                "onFinishInflate new: left : $i , top : $i2 ,right : $i3, bottom : $i4"
            )
        }
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        layoutVertical(l, t, r, b)
    }

    /**
     * 根据需求重新摆放子View的位置
     *
     * @param l
     * @param t
     * @param r
     * @param b
     */
    private fun layoutVertical(l: Int, t: Int, r: Int, b: Int) {
        //TODO : 暂时不考虑margin 和Padding
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child == null) {
            } else if (child.visibility != View.GONE) {
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                Log.e(
                    TAG,
                    "childWidth : " + childWidth + "childHeight ： " + childHeight
                )
                val layoutDirection = layoutDirection
                if (i == 0) {
                    setChildFrame(
                        child, 0, 0,
                        childWidth, childHeight
                    )
                } else if (i == 1) {
                    val startTop = mBgView!!.measuredHeight / 2
                    setChildFrame(
                        child, 0, startTop,
                        childWidth, childHeight
                    )
                }
            }
        }
    }

    private fun setChildFrame(
        child: View,
        left: Int,
        top: Int,
        width: Int,
        height: Int
    ) {
        child.layout(left, top, left + width, top + height)
    }


    /**
     *  控制SlidingView展开 或者关闭
     *  ImageViewV为SlidingView 上方箭头
     */
    fun expansionView(imageView: ImageView) {
        //       if (mChildSlidingView?.translationY!!.toInt() == (mBgView?.measuredHeight?.div(2))) {
        if (slidingOpenState) {
            mChildSlidingView?.scrollTo(0,300)
//            AnimatorUtils.translationY(
//                mChildSlidingView!!,
//                mChildSlidingView?.translationY!!.toFloat(),
//                -(mBgView?.measuredHeight?.div(2))?.toFloat()!!
//            )
            AnimatorUtils.startRotate(
                imageView,
                imageView.rotation,
                0f
            )
            slidingOpenState = false;
        } else {
            Log.e(TAG, "OPEN")
            mChildSlidingView?.scrollTo(0,-300)
//            AnimatorUtils.translationY(
//                mChildSlidingView!!,
//                mChildSlidingView?.translationY!!.toFloat(),
//                mBgView!!.measuredHeight.toFloat() / 2
//            )
            AnimatorUtils.startRotate(
                imageView,
                0f,
                180f
            )
            slidingOpenState = true;
        }
    }


    // 回弹效果需要重写
    override fun computeScroll() {
        if (viewDragHelper!!.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    companion object {
        const val TAG = "SlidingExpansionView"
    }

    init {
        initView()
    }
}