package com.jiage.battle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.jiage.battle.R;
import com.jiage.battle.adapter.SnakBluetoothAdapter;
import com.jiage.battle.entity.BlueDeviceEntity;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/9/14/014.
 * 说明：贪吃蛇蓝牙对战-加入房间
 */

public class SnakeBluetoothAddActivity extends BaseBluetoothActivity implements CompoundButton.OnCheckedChangeListener {
    @ViewInject(R.id.act_snakebluetooth_cb)
    private CheckBox cb;
    @ViewInject(R.id.act_snakebluetooth_search)
    private Button search;
    @ViewInject(R.id.act_snakebluetooth_nosearch)
    private Button nosearch;
    @ViewInject(R.id.act_snakebluetooth_listview1)
    private ListView listview1;
    @ViewInject(R.id.act_snakebluetooth_listview2)
    private ListView listview2;
    private SnakBluetoothAdapter bluetoothAvailableAdapter;

    @Override
    public int bindLayout() {
        return R.layout.act_snakebluetooth;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mTitle.setCenterText("蓝牙连接");
        cb.setChecked(isEnabled);
        cb.setOnCheckedChangeListener(this);
        isSearch(isEnabled);
        search.setOnClickListener(this);
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                add(mDeviceListPaired.get(position).getAddress());
            }
        });
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                add(mDeviceListAvailable.get(position).getAddress());
            }
        });
    }

    @Override
    public void doView() {
        SnakBluetoothAdapter bluetoothPairedAdapter = new SnakBluetoothAdapter(mDeviceListPaired, mActivity);
        listview1.setAdapter(bluetoothPairedAdapter);
        bluetoothAvailableAdapter = new SnakBluetoothAdapter(mDeviceListAvailable, mActivity);
        listview2.setAdapter(bluetoothAvailableAdapter);
    }

    @Override
    public void widgetClick(View view) {
        if (view == search) {
            mDeviceListAvailable.clear();
            search();
        }
        if (view == nosearch) {
            stopSearth();
        }
    }

    @Override
    public void onBluetoothReceiverSearchListener(BlueDeviceEntity deviceEntity) {
        bluetoothAvailableAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            openBluetooth();
            isSearch(true);
        } else {
            closeBluetooth();
            isSearch(false);
        }
    }

    private void isSearch(boolean isSearch) {
        search.setEnabled(isSearch);
        nosearch.setEnabled(isSearch);
    }

    private void add(final String address) {
        Bundle bundle = new Bundle();
        bundle.putString(SnakeBluetoothGameActivity.ADDRESS, address);
        startActivity(SnakeBluetoothGameActivity.class, bundle);
    }
}
