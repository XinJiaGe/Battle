package com.jiage.battle.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.dao.JsonDbModelDao;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.dialog.SDDialogCustom;
import com.jiage.battle.entity.CheckpointEntity;
import com.jiage.battle.entity.CheckpointItemEntity;
import com.jiage.battle.surface.ArkanoidGameSurface;

import org.xutils.view.annotation.ViewInject;

public class ArkanoidGameActivity extends BaseActivit implements ArkanoidGameSurface.onLenter {
    public static String CHECKPOINT = "checkpoint";
    public static String CHECKPOINTNUMBER = "checkpointNumber";
    @ViewInject(R.id.act_main_surface)
    private ArkanoidGameSurface surface;

    private CheckpointItemEntity checkpoint;
    private int checkpointNumber = 1;
    private SDDialogConfirm dialogConfirm;
    private View rightView;
    private TextView righttextView;

    @Override
    public void initBar() {
    }

    @Override
    public void initParms(Bundle parms) {
        checkpointNumber = parms.getInt(CHECKPOINTNUMBER);
        checkpoint = (CheckpointItemEntity) parms.getSerializable(CHECKPOINT);
    }

    @Override
    public int bindLayout() {
        return R.layout.act_arkanoid_game;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("第" + checkpointNumber + "关");
        rightView = mTitle.setRightText("开始");
        righttextView = rightView.findViewById(R.id.view_title_right_tv);
        surface.setOnLenter(this);
    }

    @Override
    public void doView() {
        surface.setData(checkpoint);
    }

    @Override
    public void onTitleRightListener() {
        if (righttextView.getText().toString().equals("暂停")) {
            surface.setSuspend(true);
            righttextView.setText("开始");
        } else {
            surface.setSuspend(false);
            surface.setInitSuspend(false);
            righttextView.setText("暂停");
        }
    }

    @Override
    public void gameOver() {
        Message message = new Message();
        message.what = 2;
        handler.sendMessage(message);
    }

    @Override
    public void victory() {
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }

    @Override
    public void clearance() {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://胜利
                    if(dialogConfirm==null) {
                        dialogConfirm = new SDDialogConfirm(mActivity);
                        final CheckpointEntity entity = JsonDbModelDao.getInstance().query(CheckpointEntity.class);
                        surface.setSuspend(true);
                        surface.setInitSuspend(true);
                        righttextView.setText("开始");
                        dialogConfirm.setTextContent("胜利了");
                        dialogConfirm.setTextCancel("再来一次");
                        if(checkpointNumber<entity.getItemEntityList().size()){
                            dialogConfirm.setTextConfirm("下一关");
                        }else{
                            dialogConfirm.setTextConfirm("结束游戏（通关）");
                        }
                        dialogConfirm.setmListener(new SDDialogCustom.SDDialogCustomListener() {
                            @Override
                            public void onClickCancel(View v, SDDialogCustom dialog) {
                                surface.renew();
                            }

                            @Override
                            public void onClickConfirm(View v, SDDialogCustom dialog) {
                                if(checkpointNumber<entity.getItemEntityList().size()){
                                    surface.setData(entity.getItemEntityList().get(checkpointNumber));
                                    surface.nextLevel();
                                    checkpointNumber++;
                                    mTitle.setCenterText("第" + checkpointNumber + "关");
                                }else{
                                    finish();
                                }
                            }

                            @Override
                            public void onDismiss(SDDialogCustom dialog) {
                                dialogConfirm = null;
                            }
                        });
                        dialogConfirm.show();
                    }
                    break;
                case 2://死亡
                    if(dialogConfirm == null) {
                        dialogConfirm = new SDDialogConfirm(mActivity);
                        surface.setSuspend(true);
                        surface.setInitSuspend(true);
                        righttextView.setText("开始");
                        dialogConfirm.setTextContent("你死了");
                        dialogConfirm.setTextCancel("重新开始");
                        dialogConfirm.setTextConfirm("结束游戏");
                        dialogConfirm.setmListener(new SDDialogCustom.SDDialogCustomListener() {
                            @Override
                            public void onClickCancel(View v, SDDialogCustom dialog) {
                                surface.renew();
                            }

                            @Override
                            public void onClickConfirm(View v, SDDialogCustom dialog) {
                                finish();
                            }

                            @Override
                            public void onDismiss(SDDialogCustom dialog) {
                                dialogConfirm = null;
                            }
                        });
                        dialogConfirm.show();
                    }
                    break;
            }
        }
    };
}
