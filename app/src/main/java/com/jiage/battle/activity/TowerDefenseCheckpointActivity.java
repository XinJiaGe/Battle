package com.jiage.battle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jiage.battle.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/12/18
 * 说明：选择关卡
 */

public class TowerDefenseCheckpointActivity extends BaseActivit {
    @ViewInject(R.id.act_towerdefensechechk_check1)
    protected Button check1;
    @ViewInject(R.id.act_towerdefensechechk_check2)
    protected Button check2;
    @ViewInject(R.id.act_towerdefensechechk_check3)
    protected Button check3;


    @Override
    public int bindLayout() {
        return R.layout.act_towerdefensecheckpoint;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("选择关卡");
        check1.setOnClickListener(this);
        check2.setOnClickListener(this);
        check3.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        Bundle bundle = new Bundle();
        if(view == check1){
            bundle.putInt(TowerDefenseActivity.CHECKPOINT,1);
        }
        if(view == check2){
            bundle.putInt(TowerDefenseActivity.CHECKPOINT,2);
        }
        if(view == check3){
            bundle.putInt(TowerDefenseActivity.CHECKPOINT,3);
        }
        startActivity(TowerDefenseActivity.class,bundle);
    }
}
