package com.jiage.battle.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2017/11/28/028.
 * 说明：选择器
 */

public class SelectTitleView extends AutoLinearLayout {
    private SelectTitleView ins;
    private List<String> mTextList;
    private onCLickListener mClickListener;
    private int lastIndex = 0;

    public SelectTitleView(Context context, List<String> text) {
        super(context);
        this.mTextList = text;
        this.ins = this;
        init(context);
    }

    public SelectTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < mTextList.size(); i++) {
            TextView textView = new TextView(context);
            textView.setText(mTextList.get(i));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
            textView.setPadding(25, 25, 25, 25);
            if (i == 0) {
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.layer_white_corner_right_white);
            } else if (i == mTextList.size() - 1) {
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(R.drawable.layer_hong_corner_left_frame_white);
            } else {
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(R.drawable.layer_hong_corner_item_single);
            }
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onClick(view, finalI);
                    }
                }
            });
            addView(textView);
        }
    }

    public void setCurrentItem(int finalI) {
        TextView textView1 = (TextView) ins.getChildAt(finalI);
        textView1.setTextColor(Color.BLACK);
        textView1.setBackgroundResource(R.drawable.layer_white_corner_left_white);
        if (finalI == 0) {
            textView1.setBackgroundResource(R.drawable.layer_white_corner_right_white);
        } else if (finalI == mTextList.size() - 1) {
            textView1.setBackgroundResource(R.drawable.layer_white_corner_left_white);
        } else {
            textView1.setBackgroundResource(R.drawable.layer_hong_corner_item_single);
        }
        TextView textView2 = (TextView) ins.getChildAt(lastIndex);
        textView2.setTextColor(Color.WHITE);
        if (lastIndex == 0) {
            textView2.setBackgroundResource(R.drawable.layer_hong_corner_right_frame_white);
        } else if (lastIndex == mTextList.size() - 1) {
            textView2.setBackgroundResource(R.drawable.layer_hong_corner_left_frame_white);
        } else {
            textView2.setBackgroundResource(R.drawable.layer_hong_corner_item_single);
        }
        lastIndex = finalI;
    }

    public void setmCLickListener(onCLickListener cLickListener) {
        this.mClickListener = cLickListener;
    }

    public interface onCLickListener {
        void onClick(View view, int index);
    }
}
