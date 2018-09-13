package com.jiage.battle.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.jiage.battle.R;
import com.zhy.autolayout.AutoFrameLayout;

/**
 * 设置空间允许最高高度
 */

public class SDMaxHeightView extends AutoFrameLayout {
    private static final float DEFAULT_MAX_RATIO = 0.6f;
    private static final float DEFAULT_MAX_HEIGHT = 0f;

    private float mMaxRatio = DEFAULT_MAX_RATIO;// 优先级高
    private float mMaxHeight = DEFAULT_MAX_HEIGHT;// 优先级低

    public SDMaxHeightView(Context context) {
        super(context);
        init();
    }

    public SDMaxHeightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public SDMaxHeightView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MaxHeightView);

        final int count = a.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.MaxHeightView_mhv_SDHeightRatio) {
                mMaxRatio = a.getFloat(attr, DEFAULT_MAX_RATIO);
            } else if (attr == R.styleable.MaxHeightView_mhv_SDHeightDimen) {
                mMaxHeight = a.getDimension(attr, DEFAULT_MAX_HEIGHT);
            }
        }
        a.recycle();
    }

    private void init() {
        if (mMaxHeight <= 0) {
            mMaxHeight = mMaxRatio * (float) getScreenHeight(getContext());
        } else {
            mMaxHeight = Math.min(mMaxHeight, mMaxRatio * (float) getScreenHeight(getContext()));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == View.MeasureSpec.EXACTLY) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }

        if (heightMode == View.MeasureSpec.UNSPECIFIED) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        if (heightMode == View.MeasureSpec.AT_MOST) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        int maxHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     */
    private int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
