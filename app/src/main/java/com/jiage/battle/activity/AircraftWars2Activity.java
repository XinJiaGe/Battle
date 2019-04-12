package com.jiage.battle.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.dialog.SDDialogCustom;
import com.jiage.battle.surface.aircraft2.AircraftSurface;
import com.jiage.battle.util.SDTimerUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2019/4/9
 * 说明：
 */

public class AircraftWars2Activity extends BaseActivit implements AircraftSurface.onSurfaceListener {
    @ViewInject(R.id.act_aircraftwars2_surface)
    private AircraftSurface surface;
    @ViewInject(R.id.act_aircraftwars_titlell)
    private LinearLayout titlell;
    @ViewInject(R.id.act_aircraftwars_blood1)
    private ImageView blood1;
    @ViewInject(R.id.act_aircraftwars_blood2)
    private ImageView blood2;
    @ViewInject(R.id.act_aircraftwars_blood3)
    private ImageView blood3;
    @ViewInject(R.id.act_aircraftwars_fraction)
    private TextView fractionTv;
    @ViewInject(R.id.act_aircraftwars2_booswarningll)
    private RelativeLayout booswarningll;
    @ViewInject(R.id.act_aircraftwars2_booswarningbg)
    private ImageView booswarningbg;

    private int blood = 3;//血量
    private int fraction = 0;//分数
    private SDTimerUtil timerUtil = new SDTimerUtil();
    private int boosTime = 0;
    private boolean boosBg = true;

    @Override
    public void initBar() {
        setNotTitle(true);
        setSwipeBackEnable(false);
    }
    @Override
    public int bindLayout() {
        return R.layout.act_aircraftwars2;
    }

    @Override
    public void initView(View view) {
        titleHeight(titlell);
        surface.setmListener(this);
    }

    @Override
    public void injured(int i) {
        if(blood>0){
            blood -= i ;
        }
        upBlood();
        surface.getPlay().setInvincible(true);
        SDTimerUtil timerUtil = new SDTimerUtil();
        timerUtil.startWork(2000, new SDTimerUtil.SDTimerListener() {
            @Override
            public void onWork() {

            }

            @Override
            public void onWorkMain() {
                surface.getPlay().setInvincible(false);
            }
        });
    }

    @Override
    public void addBlood(int i) {
        if(blood<3){
            blood += i ;
        }
        upBlood();
    }

    @Override
    public void addFraction(int i) {
        fraction += i;
        upFraction();
    }

    @Override
    public void BoosTime() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boosTime = 0;
                booswarningll.setVisibility(View.VISIBLE);
                timerUtil.startWork(300,300, new SDTimerUtil.SDTimerListener() {
                    @Override
                    public void onWork() {

                    }

                    @Override
                    public void onWorkMain() {
                        if(boosBg) booswarningbg.setBackgroundResource(R.drawable.icon_booswarning1);
                        else booswarningbg.setBackgroundResource(R.drawable.icon_booswarning2);
                        boosBg = !boosBg;
                        boosTime += 300;
                        if(boosTime>=1800){
                            booswarningll.setVisibility(View.GONE);
                            timerUtil.stopWork();
                        }
                    }
                });
            }
        });
    }

    /**
     * 更新分数
     */
    private void upFraction(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fractionTv.setText(fraction+"");
            }
        });
    }
    /**
     * 更新血量
     */
    private void upBlood(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(blood == 3){
                    blood1.setVisibility(View.VISIBLE);
                    blood2.setVisibility(View.VISIBLE);
                    blood3.setVisibility(View.VISIBLE);
                }else if(blood == 2){
                    blood1.setVisibility(View.VISIBLE);
                    blood2.setVisibility(View.VISIBLE);
                    blood3.setVisibility(View.INVISIBLE);
                }else if(blood == 1){
                    blood1.setVisibility(View.VISIBLE);
                    blood2.setVisibility(View.INVISIBLE);
                    blood3.setVisibility(View.INVISIBLE);
                }else {
                    blood1.setVisibility(View.INVISIBLE);
                    blood2.setVisibility(View.INVISIBLE);
                    blood3.setVisibility(View.INVISIBLE);
                    surface.setStop(true);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final SDDialogConfirm dialogConfirm = new SDDialogConfirm(mActivity);
                            dialogConfirm.setTextContent("游戏结束(得分："+fraction+"分)");
                            dialogConfirm.setTextCancel("");
                            dialogConfirm.setTextConfirm("结束游戏");
                            dialogConfirm.setmListener(new SDDialogCustom.SDDialogCustomListener() {
                                @Override
                                public void onClickCancel(View v, SDDialogCustom dialog) {

                                }

                                @Override
                                public void onClickConfirm(View v, SDDialogCustom dialog) {
                                    dialogConfirm.dismiss();
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
            }
        });
    }
}
