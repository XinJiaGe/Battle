package com.jiage.battle.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.jiage.battle.R;
import com.jiage.battle.adapter.SnakBluetoothAdapter;
import com.jiage.battle.entity.BlueDeviceEntity;
import com.jiage.battle.util.SDViewBinder;
import com.jiage.battle.util.SDViewUtil;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/9/14/014.
 * 说明：贪吃蛇蓝牙对战
 */

public class SnakeBluetoothActivity extends BaseActivit implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    @ViewInject(R.id.act_snakebluetooth_cb)
    protected CheckBox cb;
    @ViewInject(R.id.act_snakebluetooth_search)
    protected Button search;
    @ViewInject(R.id.act_snakebluetooth_nosearch)
    protected Button nosearch;
    @ViewInject(R.id.act_snakebluetooth_listview)
    protected ListView listview;


    private BluetoothAdapter mBluetooth;
    private List<BlueDeviceEntity> mDeviceList = new ArrayList<>();
    private SnakBluetoothAdapter adapter;
    private BluetoothReceiver blueReceiver;

    @Override
    public int bindLayout() {
        return R.layout.act_snakebluetooth;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("蓝牙对战");
    }

    @Override
    public void doView() {
        cb.setOnCheckedChangeListener(this);
        search.setOnClickListener(this);
        nosearch.setOnClickListener(this);

        adapter = new SnakBluetoothAdapter(mDeviceList,mActivity);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        if(view == search){
            beginDiscovery();
        }
        if(view == nosearch){
            cancelDiscovery();
        }
    }

    @Override
    public void doBusiness() {
        bluetoothPermissions();
        mBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (mBluetooth == null) {
            showToast("本机未找到蓝牙功能");
            finish();
        }
    }

    // 定义获取基于地理位置的动态权限
    private void bluetoothPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    /**
     * 重写onRequestPermissionsResult方法
     * 获取动态权限请求的结果,再开启蓝牙
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mBluetooth = BluetoothAdapter.getDefaultAdapter();
            if (mBluetooth == null) {
                showToast("本机未找到蓝牙功能");
                finish();
            }
        } else {
            showToast("用户拒绝了权限");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onStart() {
        super.onStart();
        blueReceiver = new BluetoothReceiver();
        //需要过滤多个动作，则调用IntentFilter对象的addAction添加新动作
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        foundFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        foundFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(blueReceiver, foundFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        cancelDiscovery();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**
     * 搜索
     */
    private void beginDiscovery() {
        if (mBluetooth.isDiscovering() != true) {
            mBluetooth.startDiscovery();
        }
    }

    /**
     * 取消搜索
     */
    private void cancelDiscovery() {
        if (mBluetooth.isDiscovering() == true) {
            mBluetooth.cancelDiscovery();
            unregisterReceiver(blueReceiver);
        }
    }


    private class BluetoothReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive action=" + action);
            // 获得已经搜索到的蓝牙设备
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BlueDeviceEntity item = new BlueDeviceEntity(device.getName(), device.getAddress(), device.getBondState() - 10);
                mDeviceList.add(item);
                adapter.notifyDataSetChanged();
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {//蓝牙设备搜索完成
            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDING) {//正在配对
                } else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {//完成配对
                } else if (device.getBondState() == BluetoothDevice.BOND_NONE) {//取消配对
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked == true) {
            beginDiscovery();
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(intent, 1);
        } else {
            cancelDiscovery();
            mDeviceList.clear();
            adapter.notifyDataSetChanged();
        }
    }
}
