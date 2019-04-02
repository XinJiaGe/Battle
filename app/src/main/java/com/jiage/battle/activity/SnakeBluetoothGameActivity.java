package com.jiage.battle.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.dialog.SDDialogCustom;
import com.jiage.battle.entity.BlueDeviceEntity;
import com.jiage.battle.surface.snake.RectangleKeyboard;
import com.jiage.battle.surface.snake.SnakeBluetoothSurface;
import com.jiage.battle.util.SDHandlerUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：贪吃蛇游戏蓝牙对战
 */

public class SnakeBluetoothGameActivity extends BaseBluetoothDataActivity implements SnakeBluetoothSurface.onListener {
    public static String ADDRESS = "address";//
    @ViewInject(R.id.act_snake_game_ll)
    protected LinearLayout ll;
    @ViewInject(R.id.act_snake_game_surfaceview)
    protected SnakeBluetoothSurface surface;
    @ViewInject(R.id.act_snake_game_start)
    protected TextView start;
    @ViewInject(R.id.act_snake_game_man1)
    protected TextView man1;
    @ViewInject(R.id.act_snake_game_man2)
    protected TextView man2;
    @ViewInject(R.id.act_snake_game_fraction)
    protected TextView fraction;
    private SDDialogConfirm dialogConfirm;
    private int i = 0;
    private boolean man2Ready = false;
    private String address;

    @Override
    public void initBar() {
        setNotTitle(true);
    }

    @Override
    public void initParms(Bundle parms) {
        address = parms.getString(ADDRESS);
    }

    @Override
    public int bindLayout() {
        return R.layout.act_snake_blue_game;
    }

    @Override
    public void main1Message(BlueDeviceEntity entitiy) {
        switch (entitiy.getType()) {
            case 1:
                showToast(entitiy.getName()+"加入游戏");
                man2Ready = true;
                surface.addSnakeTo(entitiy.getAddress());
                BlueDeviceEntity entity = new BlueDeviceEntity();
                entity.setName(mBluetoothAdapter.getName());
                entity.setAddress(mBluetoothAdapter.getAddress());
                entity.setType(4);
                entity.setX(surface.getMyX());
                entity.setY(surface.getMyY());
                write(entity);
                break;
            case 5:
                surface.setDIRECTIONTO(entitiy.getDIRECTIONMY());
                break;
        }
    }

    @Override
    public void main2Message(BlueDeviceEntity entitiy) {
        switch (entitiy.getType()) {
            case 4:
                surface.addSnakeMy(entitiy.getAddress());
                break;
            case 6:
                surface.setMyrect(entitiy.getRect());
                break;
            case 7:
                surface.setTorect(entitiy.getRect());
                break;
        }
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        titleHeight(ll);
        start.setOnClickListener(this);
        surface.setOnListener(this);
        if (address == null) {
            man1.setText(mBluetoothAdapter.getName());
            surface.addSnake(true,mBluetoothAdapter.getAddress());
            surface.setServer(true);
        } else {
            man2.setText(mBluetoothAdapter.getName());
            start.setVisibility(View.GONE);
        }
    }

    @Override
    public void widgetClick(View view) {
        if (view == start) {
            if (createRoomSuccessfully) {
                if (man2Ready) {
                    surface.setSuspend(false);
                } else {
                    showToast("对方未加入");
                }
            } else {
                showToast("房间未创建成功，请重试");
            }
        }
    }

    @Override
    public void doBusiness() {
        if (address == null) {
            if (isBluetooth) {
                create();
            }
        } else {
            connect(address);
        }
    }

    @Override
    public void gameOver(int type) {
        SDHandlerUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialogConfirm = new SDDialogConfirm(mActivity);
                surface.setSuspend(true);
                start.setText("开始");
                dialogConfirm.setTextContent("游戏结束");
                dialogConfirm.setTextCancel("重新开始");
                dialogConfirm.setTextConfirm("结束游戏");
                dialogConfirm.setmListener(new SDDialogCustom.SDDialogCustomListener() {
                    @Override
                    public void onClickCancel(View v, SDDialogCustom dialog) {
                        i = 0;
                        fraction.setText("分数:" + i);
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
        });
    }

    @Override
    public void fraction(int type) {
        SDHandlerUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                i++;
                fraction.setText("分数:" + i);
            }
        });
    }

    @Override
    public void directionto(RectangleKeyboard.Direction direction) {
        BlueDeviceEntity entity = new BlueDeviceEntity();
        entity.setType(5);
        entity.setDIRECTIONMY(direction);
        write(entity);
    }

    @Override
    public void rect(Rect rectmy, Rect rectto) {
        BlueDeviceEntity entity = new BlueDeviceEntity();
        entity.setType(6);
        entity.setRect(rectmy);
        write(entity);
        BlueDeviceEntity entity2 = new BlueDeviceEntity();
        entity2.setType(7);
        entity2.setRect(rectto);
        write(entity2);
    }
}
