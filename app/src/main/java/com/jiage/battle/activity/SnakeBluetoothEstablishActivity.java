package com.jiage.battle.activity;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.jiage.battle.R;
import com.jiage.battle.adapter.SnakBluetoothAdapter;
import com.jiage.battle.constant.ApkConstant;
import com.jiage.battle.entity.BlueDeviceEntity;
import com.jiage.battle.util.JsonUtil;

import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 作者：李忻佳
 * 日期：2018/9/20
 * 说明：贪吃蛇蓝牙对战-创建房间
 */

public class SnakeBluetoothEstablishActivity extends BaseBluetoothActivity {
    @ViewInject(R.id.act_snakebluetooth_addren)
    private ListView listview;


    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private InputStream is;
    private AcceptThread acceptThread;
    private List<BlueDeviceEntity> listModel = new ArrayList<>();
    private SnakBluetoothAdapter adapter;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String json = String.valueOf(msg.obj);
            BlueDeviceEntity entity = JsonUtil.json2Object(json, BlueDeviceEntity.class);
            if(entity.isAdd()) {
                showToast(entity.getName() + "加入房间");
                listModel.add(entity);
            }else {
                showToast(entity.getName() + "退出房间");
                listModel.remove(entity);
            }
            adapter.notifyDataSetChanged();
            super.handleMessage(msg);
        }
    };

    //服务端监听客户端的线程类
    private class AcceptThread extends Thread {
        public AcceptThread() {
            try {
                UUID uuid = UUID.fromString(ApkConstant.MY_UUID);
                serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("com.jiage.battle", uuid);
                showToast("创建房间成功");
            } catch (Exception e) {
                showToast("创建房间失败");
                Log.e("SnakeBluetoothEstablish","创建房间失败  "+e.getMessage());
            }
        }
        public void run() {
            try {
                socket = serverSocket.accept();
                is = socket.getInputStream();
                while(true) {
                    byte[] buffer =new byte[1024];
                    int count = is.read(buffer);
                    Message msg = new Message();
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    handler.sendMessage(msg);
                }
            }
            catch (Exception e) {
            }
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.act_snakebluetooth_establish;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mTitle.setCenterText("创建房间");
    }

    @Override
    public void doView() {
        adapter = new SnakBluetoothAdapter(listModel,mActivity);
        listview.setAdapter(adapter);
    }

    @Override
    public void doBusiness() {
        acceptThread = new AcceptThread();
        acceptThread.start();
    }

    @Override
    protected void onDestroy() {
        try {
            if(socket!=null) {
                socket.close();
                socket = null;
            }
            if(is!=null) {
                is.close();
                is = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
