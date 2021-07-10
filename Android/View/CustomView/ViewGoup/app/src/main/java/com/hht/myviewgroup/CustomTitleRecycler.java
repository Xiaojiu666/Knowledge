package com.hht.myviewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomTitleRecycler extends LinearLayout {
    private TextView moreInfoTvName;
    private RecyclerView moreInfoRecycler;

    public CustomTitleRecycler(Context context) {
        this(context, null);
    }

    public CustomTitleRecycler(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustomTitleRecycler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);

    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.hht_layout_title_recycler, this, true);
        moreInfoTvName = (TextView) findViewById(R.id.moreinfo_item_tv_name);
        moreInfoRecycler = (RecyclerView) findViewById(R.id.moreinfo_item_recycler);

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleRecyclerView);
        String titleName = attributes.getString(R.styleable.CustomTitleRecyclerView_title_name);
        moreInfoTvName.setText(titleName);
        attributes.recycle();
    }

    public RecyclerView getRecyclerView() {
        return moreInfoRecycler;
    }

}
