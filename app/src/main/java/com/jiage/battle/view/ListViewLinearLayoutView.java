package com.jiage.battle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

import java.util.List;

/**
 * 作者：李忻佳.
 * 时间：2016/12/8.
 * 说明：用LinearLayout绑定listview
 */

public class ListViewLinearLayoutView extends LinearLayout {
    public ListViewLinearLayoutView(Context context) {
        super(context);
        init();
    }

    public ListViewLinearLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewLinearLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
    }

    /**
     * 绑定布局
     */
    public void setAdapter(Adapter adapter) {
        int count = adapter.getCount();
        removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = adapter.getView(i, null, null);
            //绑定Adapter的数据到LinearLayout布局
            addView(v);
        }
    }

    /**
     * 添加View
     *
     * @param list
     */
    public void addView(List<View> list) {
        removeAllViews();
        for (View view : list) {
            addView(view);
        }
    }
}
