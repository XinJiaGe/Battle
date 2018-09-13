package com.jiage.battle.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.surface.SnakeSurface;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：贪吃蛇游戏
 */

public class SnakeGameActivity extends BaseActivit {
    @ViewInject(R.id.act_snake_game_ll)
    protected LinearLayout ll;
    @ViewInject(R.id.act_snake_game_surface)
    protected SnakeSurface surface;
    @ViewInject(R.id.act_snake_game_start)
    protected TextView start;

    @Override
    public void initBar() {
        setNotTitle(true);
    }

    @Override
    public int bindLayout() {
        return R.layout.act_snake_game;
    }

    @Override
    public void initView(View view) {
        titleHeight(ll);
        start.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        if(view == start){
            if (start.getText().toString().equals("暂停")) {
                start.setText("开始");
                surface.setSuspend(true);
            } else {
                start.setText("暂停");
                surface.setSuspend(false);
            }
        }
    }
}
