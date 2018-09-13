package com.jiage.battle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 作者：李忻佳
 * 时间：2018/3/28/028.
 * 描述：
 */

public class RelativeLayoutHeightToWidthView extends RelativeLayout {
    public RelativeLayoutHeightToWidthView(Context context) {
        super(context);
    }

    public RelativeLayoutHeightToWidthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativeLayoutHeightToWidthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
