package com.jiage.battle.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.util.SDViewUtil;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * 作者：李忻佳
 * 日期：2018/1/3/003.
 * 说明：
 */

public class MySelectView extends AutoLinearLayout {
    private Context mContext;
    private int selectIndex = 1;
    private onSelectViewListener mListener;
    private TextView[] textViews;
    private View[] views;

    public MySelectView(Context context) {
        super(context);
        init(context);
    }

    public MySelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MySelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        setBackgroundColor(Color.WHITE);
    }

    public void addText(String[] text){
        textViews = new TextView[text.length];
        views = new View[text.length];
        for (int i = 0; i < text.length; i++) {
            View view = SDViewUtil.getRId(mContext, R.layout.view_my_select);
            view.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1));
            TextView tv = view.findViewById(R.id.view_my_select_text);
            View v = view.findViewById(R.id.view_my_select_view);
            textViews[i] = tv;
            views[i] = v;
            tv.setText(text[i]);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    updataTab(finalI+1);
                    if(mListener!=null){
                        mListener.onSelectIndex(finalI+1);
                    }
                }
            });

            addView(view);
        }
        updataTab(selectIndex);
    }

    /**
     * 更新tab
     * @param index
     */
    private void updataTab(int index){
        for (int i = 0; i < textViews.length; i++) {
            if(i+1==index){
                textViews[i].setTextColor(mContext.getResources().getColor(R.color.mainTv));
                views[i].setVisibility(View.VISIBLE);
            }else{
                textViews[i].setTextColor(Color.BLACK);
                views[i].setVisibility(View.GONE);
            }
        }
    }

    public void setat(){
        if(mListener!=null){
            mListener.onSelectIndex(selectIndex);
        }
    }
    public void setmSelectListener(onSelectViewListener listener){
        this.mListener = listener;
    }

    public interface onSelectViewListener{
        void onSelectIndex(int index);
    }
}
