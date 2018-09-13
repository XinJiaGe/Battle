package com.jiage.battle.activity;

import android.view.View;
import android.widget.Button;

import com.jiage.battle.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：贪吃蛇
 */

public class SnakeActivity extends BaseActivit {
    @ViewInject(R.id.act_snake_start)
    protected Button start;

    @Override
    public int bindLayout() {
        return R.layout.act_snake;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("贪吃蛇");
        start.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        if(view == start){
            startActivity(SnakeGameActivity.class);
        }
    }
}
