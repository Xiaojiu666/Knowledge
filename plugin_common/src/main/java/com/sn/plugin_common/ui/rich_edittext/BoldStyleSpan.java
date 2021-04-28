package com.sn.plugin_common.ui.rich_edittext;

import android.graphics.Typeface;
import android.os.Parcel;
import android.text.Editable;
import android.text.style.StyleSpan;

import androidx.annotation.NonNull;

public class BoldStyleSpan extends StyleSpan implements ISpan
{
    public BoldStyleSpan() {
        super(Typeface.BOLD);
    }

    public BoldStyleSpan(@NonNull Parcel src) {
        super(src);
    }

    @Override
    public SpanType getSpanType() {
        return SpanType.BOLD;
    }
}
