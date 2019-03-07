package com.jiage.battle.activity;

import android.os.Bundle;
import android.view.View;

import com.jiage.battle.R;
import com.jiage.battle.constant.Constant;
import com.jiage.battle.surface.defense.TowerDefenseSurfaceView;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/12/18
 * 说明：方圆塔防
 */

public class TowerDefenseActivity extends BaseActivit {
    public static String CHECKPOINT = "checkpoint";
    @ViewInject(R.id.act_tower_defense_surfaceview)
    private TowerDefenseSurfaceView surface;

    private int checkpoint;

    @Override
    public void initBar() {
        setNotTitle(true);
        setNotStatusBar(true);
    }
    @Override
    public void initParms(Bundle parms) {
        checkpoint = parms.getInt(CHECKPOINT,1);
    }

    @Override
    public int bindLayout() {
        return R.layout.act_tower_defense;
    }

    @Override
    public void initView(View view) {
        surface.setCheckpoint(checkpoint);
    }
}
