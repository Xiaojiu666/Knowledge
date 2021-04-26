package com.sn.plugin_common.ui

import android.content.Context
import android.graphics.Typeface
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.widget.EditText

class RichEditText : EditText {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    init {
        val editableText = editableText
        val start: Int = selectionStart
        val end: Int = selectionEnd
        val flags = if (start == end) Spanned.SPAN_INCLUSIVE_INCLUSIVE else Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        editableText.setSpan(StyleSpan(Typeface.BOLD),start,end,flags)
    }



}