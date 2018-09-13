package com.jiage.battle.activity;

import android.view.View;
import android.widget.TextView;

import com.jiage.battle.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/9/12
 * 说明：打砖块游戏
 */

public class ArkanoidActivity extends BaseActivit {
    @ViewInject(R.id.act_arkanoid_customize)
    protected TextView customize;


    @Override
    public int bindLayout() {
        return R.layout.act_arkanoid_zdy;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("打砖块");
        mTitle.setRightText("设置");
        customize.setOnClickListener(this);
    }

    @Override
    public void onTitleRightListener() {
        startActivity(ArkanoidSettingActivity.class);
    }

    @Override
    public void widgetClick(View view) {
        if(view == customize){
            startActivity(ArkanoidZDYActivity.class);
        }
    }
}
