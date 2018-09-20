package com.jiage.battle.activity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.jiage.battle.R;
import com.jiage.battle.adapter.SnakBluetoothAdapter;
import com.jiage.battle.constant.ApkConstant;
import com.jiage.battle.entity.BlueDeviceEntity;
import com.jiage.battle.util.JsonUtil;
import com.jiage.battle.util.SDToast;

import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

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
        bundle.putBoolean(SnakeBluetoothGameActivity.TYPE,false);
        bundle.putString(SnakeBluetoothGameActivity.ADDRESS,address);
        startActivity(SnakeBluetoothGameActivity.class,bundle);

        /*onDialogLoading();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //主动连接蓝牙服务端
                try {
                    //判断当前是否正在搜索
                    if (mBluetoothAdapter.isDiscovering()) {
                        mBluetoothAdapter.cancelDiscovery();
                    }
                    try {
                        if (device == null) {
                            //获得远程设备
                            device = mBluetoothAdapter.getRemoteDevice(address);
                        }
                        if (clientSocket != null) {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            clientSocket = null;
                        }
                        //创建客户端蓝牙Socket
                        clientSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(ApkConstant.MY_UUID));
                        //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                        clientSocket.connect();
                        //获得输出流（客户端指向服务端输出文本）
                        os = clientSocket.getOutputStream();
                        onDialogDismiss();
                        SDToast.showToast("加入成功");
                    } catch (Exception e) {
                        Log.e("SnakBluetoothAdapter", "加入失败  " + e.getMessage());
                        try {
                            Log.e("SnakBluetoothAdapter", "尝试后退…");
                            clientSocket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);
                            clientSocket.connect();
                            Log.e("SnakBluetoothAdapter", "Connected");
                            onDialogDismiss();
                            SDToast.showToast("加入成功");
                        } catch (Exception e2) {
                            Log.e("SnakBluetoothAdapter", "无法建立蓝牙连接！");
                            onDialogDismiss();
                            SDToast.showToast("加入失败");
                            try {
                                if (os != null)
                                    os.close();
                                if (clientSocket != null) {
                                    clientSocket.close();
                                    clientSocket = null;
                                }
                            } catch (Exception e3) {
                            }
                        }
                    }
                    if (os != null) {
                        //往服务端写信息
                        os.write(JsonUtil.obj2JsonString(new BlueDeviceEntity(mBluetoothAdapter.getName(),mBluetoothAdapter.getAddress(),true)).getBytes("utf-8"));
                    }
                } catch (Exception e) {
                }
            }
        });
        thread.start();*/
    }
}
