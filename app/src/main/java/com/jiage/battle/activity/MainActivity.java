package com.jiage.battle.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.battle.R;

import org.xutils.view.annotation.ViewInject;


/**
 * 作者：李忻佳
 * 时间：2017/11/26/026
 * 说明：主页
 */
public class MainActivity extends BaseActivit {
    @ViewInject(R.id.act_main_arkanoid)
    protected TextView arkanoid;

    @Override
    public int bindLayout() {
        return R.layout.act_main;
    }

    @Override
    public void initView(View view) {
        setSwipeBackEnable(false);
        mTitle.removeBack();
        mTitle.setCenterText("我的游戏");
    }

    @Override
    public void doView() {
        arkanoid.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        if(view == arkanoid){
            startActivity(ArkanoidActivity.class);
        }
    }
}