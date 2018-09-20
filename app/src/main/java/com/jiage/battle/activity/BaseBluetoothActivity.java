package com.jiage.battle.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;

import com.jiage.battle.entity.BlueDeviceEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 作者：李忻佳
 * 日期：2018/9/20
 * 说明：蓝牙控制Base
 */

public abstract class BaseBluetoothActivity extends BaseActivit {
    protected boolean isBluetooth = true;//设备是否支持蓝牙
    protected boolean isEnabled = true;//设备是启动蓝牙
    protected BluetoothAdapter mBluetoothAdapter;
    protected List<BlueDeviceEntity> mDeviceListPaired = new ArrayList<>();
    protected List<BlueDeviceEntity> mDeviceListAvailable = new ArrayList<>();
    protected BluetoothReceiver blueReceiver;

    @Override
    public void initView(View view) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            isBluetooth = false;
            showToast("设备不支持蓝牙");
        }
        isEnabled = mBluetoothAdapter.isEnabled();
        getPairedBluetooth();
    }

    /**
     * 获取已配对设备
     */
    protected void getPairedBluetooth(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                BlueDeviceEntity entity = new BlueDeviceEntity();
                entity.setName(device.getName());
                entity.setAddress(device.getAddress());
                entity.setState(device.getBondState() - 10);
                mDeviceListPaired.add(entity);
            }
        }
    }

    /**
     * 搜索
     */
    protected void search(){
        if (mBluetoothAdapter.isDiscovering() != true) {
            mBluetoothAdapter.startDiscovery();
        }
    }

    /**
     * 停止搜索
     * @return
     */
    protected void stopSearth() {
        if(mBluetoothAdapter!=null) {
            if (mBluetoothAdapter.isDiscovering() == true) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }
    }
    /**
     * 开启蓝牙
     */
    protected void openBluetooth(){
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }
    /**
     * 关闭蓝牙
     */
    protected void closeBluetooth(){
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(blueReceiver==null) {
            blueReceiver = new BluetoothReceiver();
            //需要过滤多个动作，则调用IntentFilter对象的addAction添加新动作
            IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            foundFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            foundFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            registerReceiver(blueReceiver, foundFilter);
        }
    }
    /**
     * 搜索回调
     */
    private class BluetoothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 获得已经搜索到的蓝牙设备
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.e(TAG, "搜索到设备：  " + device.getName());
                if(device.getName()!=null) {
                    BlueDeviceEntity entity = new BlueDeviceEntity();
                    entity.setName(device.getName());
                    entity.setAddress(device.getAddress());
                    entity.setState(device.getBondState() - 10);
                    mDeviceListAvailable.add(entity);
                    onBluetoothReceiverSearchListener(entity);
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {//蓝牙设备搜索完成
                onBluetoothReceiverSearchCompleteListener();
            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDING) {//正在配对
                    onBluetoothReceiverPairListener();
                } else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {//完成配对
                    onBluetoothReceiverPairCompletionListener();
                } else if (device.getBondState() == BluetoothDevice.BOND_NONE) {//取消配对
                    onBluetoothReceiverCancelPairListener();
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        if (mBluetoothAdapter != null)
            mBluetoothAdapter.cancelDiscovery();
        if(blueReceiver!=null){
            unregisterReceiver(blueReceiver);
        }
        super.onDestroy();
    }

    public void onBluetoothReceiverSearchListener(BlueDeviceEntity deviceEntity){}
    public void onBluetoothReceiverSearchCompleteListener(){}
    public void onBluetoothReceiverPairListener(){}
    public void onBluetoothReceiverCancelPairListener(){}
    public void onBluetoothReceiverPairCompletionListener(){}
}
