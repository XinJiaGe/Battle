package com.jiage.battle.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.util.SDViewUtil;

/**
 * 作者：李忻佳
 * 日期：2018/1/4/004.
 * 说明：数据空
 */

public class NullDataView extends LinearLayout {
    private View view;

    public NullDataView(Context context) {
        super(context);
        init(context);
    }

    public NullDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NullDataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        view = SDViewUtil.getRId(context, R.layout.view_nulldata);
        addView(view);
    }

    public void setText(String textStr){
        TextView text = view.findViewById(R.id.view_nulldata_text);
        text.setText(textStr);
    }
    public void show(){
        if(getVisibility() == View.GONE){
            setVisibility(View.VISIBLE);
        }
    }
    public void hind(){
        if(getVisibility() == View.VISIBLE||getVisibility() == View.INVISIBLE){
            setVisibility(View.GONE);
        }
    }
}
