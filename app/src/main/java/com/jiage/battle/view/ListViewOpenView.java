package com.jiage.battle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者：李忻佳
 * 日期：2017/11/30/030.
 * 说明：展开listview的全部数据
 */

public class ListViewOpenView extends ListView {
    public ListViewOpenView(Context context) {
        super(context);
    }

    public ListViewOpenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewOpenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
