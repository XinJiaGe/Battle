package com.jiage.battle.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 作者：李忻佳
 * 时间：2018/3/28/028.
 * 描述：
 */

public class ButtonViewWidthToHeightView extends android.support.v7.widget.AppCompatButton {
    public ButtonViewWidthToHeightView(Context context) {
        super(context);
    }

    public ButtonViewWidthToHeightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonViewWidthToHeightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
