package com.jiage.battle.activity;

import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.dialog.SDDialogCustom;
import com.jiage.battle.surface.snake.RectangleKeyboard;
import com.jiage.battle.surface.snake.SnakeBluetoothSurface;
import com.jiage.battle.surface.snake.SnakeSurface;
import com.jiage.battle.util.SDHandlerUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：贪吃蛇游戏
 */

public class SnakeGameActivity extends BaseActivit implements SnakeSurface.onListener, SnakeBluetoothSurface.onListener {
    @ViewInject(R.id.act_snake_game_ll)
    protected LinearLayout ll;
    @ViewInject(R.id.act_snake_game_surfaceview)
    protected SnakeSurface surface;
    @ViewInject(R.id.act_snake_game_start)
    protected TextView start;
    @ViewInject(R.id.act_snake_game_fraction)
    protected TextView fraction;
    private SDDialogConfirm dialogConfirm;
    private int i = 0;

    @Override
    public void initBar() {
        setNotTitle(true);
    }

    @Override
    public int bindLayout() {
        return R.layout.act_snake_game;
    }

    @Override
    public void initView(View view) {
        titleHeight(ll);
        start.setOnClickListener(this);
        surface.setOnListener(this);
    }

    @Override
    public void widgetClick(View view) {
        if(view == start){
            if (start.getText().toString().equals("暂停")) {
                start.setText("开始");
                surface.setSuspend(true);
            } else {
                start.setText("暂停");
                surface.setSuspend(false);
            }
        }
    }

    @Override
    public void gameOver() {
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
                        fraction.setText("分数:"+i);
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
    public void fraction() {
        SDHandlerUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                i++;
                fraction.setText("分数:"+i);
            }
        });
    }

    @Override
    public void gameOver(int type) {

    }

    @Override
    public void fraction(int type) {

    }

    @Override
    public void directionto(RectangleKeyboard.Direction direction) {

    }

    @Override
    public void rect(Rect rectmy, Rect rectto) {

    }
}
