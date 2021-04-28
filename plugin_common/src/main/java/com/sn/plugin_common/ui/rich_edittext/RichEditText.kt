package com.sn.plugin_common.ui.rich_edittext

import android.content.Context
import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.Log
import android.widget.EditText

class RichEditText : EditText {
    private val TAG = "RichEditText"

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }


    init {
        val editableText = editableText
        val start: Int = selectionStart
        val end: Int = selectionEnd
        Log.e(TAG, "selectionStart $selectionStart , selectionEnd$selectionEnd ")
        val flags =
            if (start == end) Spanned.SPAN_INCLUSIVE_INCLUSIVE else Spanned.SPAN_EXCLUSIVE_INCLUSIVE
//        editableText.setSpan(StyleSpan(Typeface.BOLD), start, end, flags)
        val createSpan = SpanFactory.createSpan(SpanType.BOLD) as StyleSpan
        editableText.setSpan(createSpan, 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }


    /*·
        start  从哪开始
        end 到哪里的
        setSpan(样式/从哪到哪/)
        都是0-0 新输入文字 SPAN_EXCLUSIVE_INCLUSIVE 有加粗  SPAN_EXCLUSIVE_INCLUSIVE 无加粗


        Spanned.SPAN_INCLUSIVE_INCLUSIVE  ： start - end 中间字体 会变色
        Spanned.SPAN_EXCLUSIVE_INCLUSIVE  ： tart - end 中间字体 不会变色
     */
}