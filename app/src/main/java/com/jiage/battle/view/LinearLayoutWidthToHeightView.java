package com.jiage.battle.view;

import android.content.Context;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoLinearLayout;

/**
 * 作者：李忻佳
 * 时间：2018/3/22/022.
 * 描述：宽度随高度变化
 */

public class LinearLayoutWidthToHeightView extends AutoLinearLayout {
    public LinearLayoutWidthToHeightView(Context context) {
        super(context);
    }

    public LinearLayoutWidthToHeightView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutWidthToHeightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
