package com.jiage.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 作者：李忻佳.
 * 时间：2016/12/16.
 * 说明：饼状BaseView
 */

public class BaseDrawCakeView extends BaseDrawView {
    /**
     * 原点X
     */
    private int originX;
    /**
     * 原点Y
     */
    private int originY;

    public BaseDrawCakeView(Context context) {
        super(context);
    }

    public BaseDrawCakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseDrawCakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        originX = mWidth/2;
        originY = mHeight/2;
    }

    public int getOriginX() {
        return originX;
    }

    public void setOriginX(int originX) {
        this.originX = originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setOriginY(int originY) {
        this.originY = originY;
    }
}
