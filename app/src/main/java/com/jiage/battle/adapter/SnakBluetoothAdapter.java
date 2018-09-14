package com.jiage.battle.adapter;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.entity.BlueDeviceEntity;
import com.jiage.battle.util.ViewHolder;

import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/9/14/014.
 * 说明：
 */

public class SnakBluetoothAdapter extends BaseAdapter<BlueDeviceEntity> {
    public SnakBluetoothAdapter(List<BlueDeviceEntity> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_snakbluetooth;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, BlueDeviceEntity model) {
        TextView textView = ViewHolder.get(R.id.item_snakbluetooth_text,convertView);

        textView.setText(model.getName());
    }
}
