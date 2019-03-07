package com.jiage.battle.activity;

import android.content.pm.ActivityInfo;
import android.view.View;

import com.jiage.battle.R;
import com.jiage.battle.surface.sickto.SicktoSurfaceView;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/12/26
 * 说明：坚守战
 */

public class SickToActivity extends BaseActivit {
    @ViewInject(R.id.act_sickto_surface)
    private SicktoSurfaceView surface;

    @Override
    public void initBar() {
        setNotTitle(true);
        setNotStatusBar(true);
    }
    @Override
    public int bindLayout() {
        return R.layout.act_sickto;
    }

    @Override
    public void initView(View view) {

    }
}
