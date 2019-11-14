package com.jiage.battle.activity;

import android.view.View;
import android.widget.Button;

import com.jiage.battle.R;

import org.xutils.view.annotation.ViewInject;


/**
 * 作者：李忻佳
 * 时间：2017/11/26/026
 * 说明：主页
 */
public class MainActivity extends BaseActivit {
    @ViewInject(R.id.act_main_arkanoid)
    protected Button arkanoid;
    @ViewInject(R.id.act_main_snake)
    protected Button snake;
    @ViewInject(R.id.act_main_aircraft)
    protected Button aircraft;
    @ViewInject(R.id.act_main_towerdefense)
    protected Button towerdefense;
    @ViewInject(R.id.act_main_stickTo)
    protected Button stickTo;
    @ViewInject(R.id.act_main_stickTo2)
    protected Button stickTo2;
    @ViewInject(R.id.act_main_dota)
    protected Button dota;
    @ViewInject(R.id.act_main_arkanoidcod)
    protected Button arkanoidcod;
    @ViewInject(R.id.act_main_stickTo3)
    protected Button stickTo3;

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
        snake.setOnClickListener(this);
        aircraft.setOnClickListener(this);
        stickTo.setOnClickListener(this);
        stickTo2.setOnClickListener(this);
        towerdefense.setOnClickListener(this);
        dota.setOnClickListener(this);
        stickTo3.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        if(view == arkanoid){
            startActivity(ArkanoidActivity.class);
        }
        if(view == snake){
            startActivity(SnakeActivity.class);
        }
        if(view == aircraft){//飞机大战
            startActivity(AircraftWarsActivity.class);
        }
        if(view == towerdefense){
            startActivity(TowerDefenseCheckpointActivity.class);
        }
        if(view == stickTo){
            startActivity(SickToActivity.class);
        }
        if(view == stickTo2){
            startActivity(SickTo2Activity.class);
        }
        if(view == dota){
            startActivity(DotaActivity.class);
        }
        if(view == arkanoidcod){
            startActivity(DotaActivity.class);
        }
        if(view == stickTo3){
            startActivity(SickTo3Activity.class);
        }
    }
}
