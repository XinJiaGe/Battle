package com.jiage.battle.activity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.jiage.battle.constant.ApkConstant;
import com.jiage.battle.entity.BlueDeviceEntity;
import com.jiage.battle.util.JsonUtil;
import com.jiage.battle.util.SDToast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 作者：李忻佳
 * 日期：2018/9/20
 * 说明：蓝牙控制Base
 */

public abstract class BaseBluetoothDataActivity extends BaseBluetoothActivity {
    private BluetoothServerSocket serverSocket;
    protected boolean createRoomSuccessfully = false;
    private BluetoothSocket socket;
    private InputStream is;
    private OutputStream os;
    private BluetoothDevice device;

    public abstract void main1Message(BlueDeviceEntity entitiy);
    public abstract void main2Message(BlueDeviceEntity entitiy);
    /**
     * 客户端发送的消息
     */
    private Handler handlerMan1 = new Handler() {
        public void handleMessage(Message msg) {
            String json = String.valueOf(msg.obj);
            try {
                BlueDeviceEntity entitiy = JsonUtil.json2Object(json, BlueDeviceEntity.class);
                if (entitiy!=null) {
                    main1Message(entitiy);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };
    /**
     * 服务端发送的消息
     */
    private Handler handlerMan2 = new Handler() {
        public void handleMessage(Message msg) {
            String json = String.valueOf(msg.obj);
            try {
                BlueDeviceEntity entitiy = JsonUtil.json2Object(json, BlueDeviceEntity.class);
                if (entitiy!=null) {
                    main2Message(entitiy);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void initView(View view) {
        super.initView(view);

    }

    protected void create(){
        AcceptThread acceptThread = new AcceptThread();
        acceptThread.start();
    }
    /**
     * 创建服务器
     */
    private class AcceptThread extends Thread {
        public AcceptThread() {
            try {
                UUID uuid = UUID.fromString(ApkConstant.MY_UUID);
                serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("jiage", uuid);
                showToast("创建房间成功");
                createRoomSuccessfully = true;
            } catch (Exception e) {
                showToast("创建房间失败");
                Log.e("SnakeBluetoothEstablish", "创建房间失败  " + e.getMessage());
            }
        }

        //实时监听客户端返回的数据
        public void run() {
            try {
                socket = serverSocket.accept();
                is = socket.getInputStream();
                os = socket.getOutputStream();
                while (true) {
                    byte[] buffer = new byte[1024];
                    int count = is.read(buffer);
                    Message msg = new Message();
                    msg.obj = new String(buffer, 0, count, "utf-8");
                    handlerMan1.sendMessage(msg);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 连接对方
     */
    protected void connect(final String address) {
        onDialogLoading();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //判断当前是否正在搜索
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }
                try {
                    if (device == null) {
                        //获得远程设备
                        device = mBluetoothAdapter.getRemoteDevice(address);
                    }
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        socket = null;
                    }
                    //创建客户端蓝牙Socket
                    socket = device.createRfcommSocketToServiceRecord(UUID.fromString(ApkConstant.MY_UUID));
                    //开始连接蓝牙，如果没有配对则弹出对话框提示我们进行配对
                    socket.connect();
                    //获得输出流（客户端指向服务端输出文本）
                    os = socket.getOutputStream();
                    is = socket.getInputStream();
                    onDialogDismiss();
                    SDToast.showToast("加入成功");
                } catch (Exception e) {
                    Log.e("SnakBluetoothAdapter", "加入失败  " + e.getMessage());
                    try {
                        Log.e("SnakBluetoothAdapter", "尝试后退…");
                        socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);
                        socket.connect();
                        //获得输出流（客户端指向服务端输出文本）
                        os = socket.getOutputStream();
                        is = socket.getInputStream();
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
                            if (socket != null) {
                                socket.close();
                                socket = null;
                            }
                        } catch (Exception e3) {
                        }
                    }
                }
                BlueDeviceEntity entity = new BlueDeviceEntity();
                entity.setName(mBluetoothAdapter.getName());
                entity.setAddress(mBluetoothAdapter.getAddress());
                entity.setType(1);
                write(entity);
                try {
                    while (true) {
                        byte[] buffer = new byte[1024];
                        int count = is.read(buffer);
                        Message msg = new Message();
                        msg.obj = new String(buffer, 0, count, "utf-8");
                        handlerMan2.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    protected void write(BlueDeviceEntity entity){
        if (os != null&&entity!=null) {
            try {
                //往服务端写信息
                os.write(JsonUtil.obj2JsonString(entity).getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            if(os!=null) {
                os.close();
                os = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
