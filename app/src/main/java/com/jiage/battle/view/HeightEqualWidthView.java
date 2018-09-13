package com.jiage.battle.view;

import android.content.Context;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoRelativeLayout;

/**
 * 作者：李忻佳
 * 时间：2017/6/21
 * 说明：控制高和宽一样
 */

public class HeightEqualWidthView extends AutoRelativeLayout {
    public HeightEqualWidthView(Context context) {
        super(context);
    }

    public HeightEqualWidthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightEqualWidthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();

        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
