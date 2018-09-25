package com.jiage.battle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jiage.battle.R;
import com.jiage.battle.dialog.SDDialogMenu;

import org.xutils.view.annotation.ViewInject;

import java.net.URISyntaxException;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：贪吃蛇
 */

public class SnakeActivity extends BaseBluetoothActivity {
    @ViewInject(R.id.act_snake_start)
    protected Button start;
    @ViewInject(R.id.act_snake_bluetooth)
    protected Button bluetooth;

    @Override
    public int bindLayout() {
        return R.layout.act_snake;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mTitle.setCenterText("贪吃蛇");
        start.setOnClickListener(this);
        bluetooth.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        if(view == start){
            startActivity(SnakeGameActivity.class);
        }
        if(view == bluetooth){
            if(!isBluetooth)
                showToast("设备不支持蓝牙");
            if(!mBluetoothAdapter.isEnabled()){
                showToast("请先开启蓝牙");
                openBluetooth();
                return;
            }
            SDDialogMenu menu = new SDDialogMenu(mActivity);
            menu.setItems(new String[]{"创建房间","加入房间"});
            menu.setmListener(new SDDialogMenu.SDDialogMenuListener() {
                @Override
                public void onCancelClick(View v, SDDialogMenu dialog) {

                }

                @Override
                public void onDismiss(SDDialogMenu dialog) {

                }

                @Override
                public void onItemClick(View v, int index, SDDialogMenu dialog) throws URISyntaxException {
                    switch (index) {
                        case 0:
                            Bundle bundle = new Bundle();
                            startActivity(SnakeBluetoothGameActivity.class,bundle);
                            break;
                        case 1:
                            startActivity(SnakeBluetoothAddActivity.class);
                            break;
                    }
                }
            }).showBottom();
        }
    }
}
