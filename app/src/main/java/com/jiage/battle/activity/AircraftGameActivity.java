package com.jiage.battle.activity;

import android.view.View;

import com.jiage.battle.R;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.dialog.SDDialogCustom;
import com.jiage.battle.surface.aircraft.AircraftSurface;
import com.jiage.battle.surface.arkanoid.ArkanoidZDYSurface;
import com.jiage.battle.surface.snake.SnakeSurface;
import com.jiage.battle.util.SDHandlerUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：打飞机游戏
 */

public class AircraftGameActivity extends BaseActivit {
    @ViewInject(R.id.act_aircraft_surface)
    private AircraftSurface surface;


    @Override
    public void initBar() {
        setNotTitle(true);
    }

    @Override
    public int bindLayout() {
        return R.layout.act_aircraft;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void widgetClick(View view) {

    }

    @Override
    public void doBusiness() {
        surface.setOnLenter(new AircraftSurface.onLenter() {
            @Override
            public void gameOver() {
                SDHandlerUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SDDialogConfirm dialogConfirm = new SDDialogConfirm(mActivity);
                        surface.setSuspend(false);
                        dialogConfirm.setTextContent("游戏结束");
                        dialogConfirm.setTextCancel("");
                        dialogConfirm.setTextConfirm("结束游戏");
                        dialogConfirm.setmListener(new SDDialogCustom.SDDialogCustomListener() {
                            @Override
                            public void onClickCancel(View v, SDDialogCustom dialog) {

                            }

                            @Override
                            public void onClickConfirm(View v, SDDialogCustom dialog) {
                                finish();
                            }

                            @Override
                            public void onDismiss(SDDialogCustom dialog) {

                            }
                        });
                        dialogConfirm.show();
                    }
                });
            }

            @Override
            public void victory() {

            }
        });
    }
}
