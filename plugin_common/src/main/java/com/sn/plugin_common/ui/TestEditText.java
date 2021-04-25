package com.sn.plugin_common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

public class TestEditText extends EditText {
    public TestEditText(Context context) {
        super(context);
        Log.e("TestEditText", "context : " + context.toString());
    }

    public TestEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("TestEditText", "AttributeCount : " + attrs.getAttributeCount());
    }

    public TestEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("TestEditText", "defStyleAttr : " + defStyleAttr);
    }
}
