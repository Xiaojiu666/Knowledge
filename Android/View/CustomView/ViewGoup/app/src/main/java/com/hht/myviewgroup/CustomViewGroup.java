package com.hht.myviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CustomViewGroup extends ViewGroup {

    private Context mContext;
    private String TAG = "CustomViewGroup";

    public CustomViewGroup(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 测量每个子元素的宽高，内部调用了child的measure方法，
        // 如果不调用这个方法，child就不会去测量自己宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        // 遍历子View
//        for (int i = 0; i < getChildCount(); i++) {
//            View childView = getChildAt(i);
//            int childWidth = childView.getMeasuredWidth();
//            int childHeight = childView.getMeasuredHeight();
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setView(Context mContext, int id) {
        View inflate = View.inflate(mContext, id, null);
        addView(inflate);
        //addView(inflate);
        //addView(inflate);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() != 0) {
            int itemWidth = getMeasuredWidth() / getChildCount();
            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);
                childView.layout(itemWidth * i, 0, itemWidth * (i + 1), 200);
            }
        }
    }
}
