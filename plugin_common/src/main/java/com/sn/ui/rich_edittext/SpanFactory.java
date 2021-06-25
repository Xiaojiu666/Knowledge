package com.sn.ui.rich_edittext;

public class SpanFactory {

    public static ISpan createSpan(SpanType spanType) {
        ISpan iSpan = null;
        if (spanType == SpanType.BOLD) {
            iSpan = new BoldStyleSpan();
        }

        return iSpan;
    }
}
