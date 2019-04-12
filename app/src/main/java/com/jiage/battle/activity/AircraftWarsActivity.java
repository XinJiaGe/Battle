package com.jiage.battle.activity;

import android.view.View;
import android.widget.Button;

import com.jiage.battle.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2019/4/9
 * 说明：飞机大战
 */

public class AircraftWarsActivity extends BaseActivit {
    @ViewInject(R.id.act_main_aircraft1)
    protected Button aircraft1;
    @ViewInject(R.id.act_main_aircraft2)
    protected Button aircraft2;

    @Override
    public int bindLayout() {
        return R.layout.act_aircraftwars;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("飞机大战");

        aircraft1.setOnClickListener(this);
        aircraft2.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        if(view == aircraft1){
            startActivity(AircraftGameActivity.class);
        }
        if(view == aircraft2){
            startActivity(AircraftWars2Activity.class);
        }
    }
}
