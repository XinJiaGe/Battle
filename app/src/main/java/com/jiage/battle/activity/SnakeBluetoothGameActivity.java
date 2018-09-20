package com.jiage.battle.activity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.constant.ApkConstant;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.dialog.SDDialogCustom;
import com.jiage.battle.entity.BlueDeviceEntity;
import com.jiage.battle.surface.snake.SnakeBluetoothSurface;
import com.jiage.battle.surface.snake.SnakeSurface;
import com.jiage.battle.util.JsonUtil;
import com.jiage.battle.util.SDHandlerUtil;
import com.jiage.battle.util.SDToast;

import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 作者：李忻佳
 * 日期：2018/9/13
 * 说明：贪吃蛇游戏蓝牙对战
 */

public class SnakeBluetoothGameActivity extends BaseBluetoothActivity implements SnakeBluetoothSurface.onListener {
    public static String TYPE = "type";//true: 服务器 false：客户端
    public static String ADDRESS = "address";//
    @ViewInject(R.id.act_snake_game_ll)
    protected LinearLayout ll;
    @ViewInject(R.id.act_snake_game_surface)
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
    private boolean type = true;
    private boolean createRoomSuccessfully = false;
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private String address;
    private InputStream is;
    private BluetoothDevice device;
    private OutputStream os;

    @Override
    public void initBar() {
        setNotTitle(true);
    }

    @Override
    public void initParms(Bundle parms) {
        type = parms.getBoolean(TYPE);
        address = parms.getString(ADDRESS);
    }

    @Override
    public int bindLayout() {
        return R.layout.act_snake_game;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        titleHeight(ll);
        start.setOnClickListener(this);
        surface.setOnListener(this);
        if(type)
            man1.setText(mBluetoothAdapter.getName());
        else {
            man2.setText(mBluetoothAdapter.getName());
            start.setVisibility(View.GONE);
        }
    }

    @Override
    public void widgetClick(View view) {
        if(view == start){
            if(createRoomSuccessfully) {
                if (man2Ready) {
                    surface.setSuspend(false);
                }else{
                    showToast("对方未加入");
                }
            }else{
                showToast("房间未创建成功，请重试");
            }
        }
    }

    @Override
    public void doBusiness() {
        if(type) {
            if (isBluetooth) {
                AcceptThread acceptThread = new AcceptThread();
                acceptThread.start();
            }
        }else{
            connect();
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

    /**
     * 客户端发送的消息
     */
    private Handler handlerMan1 = new Handler() {
        public void handleMessage(Message msg) {
            String json = String.valueOf(msg.obj);
            try {
                BlueDeviceEntity entitiy = JsonUtil.json2Object(json, BlueDeviceEntity.class);
                if(entitiy.isAdd()){
                    man2.setText(entitiy.getName());
                    man2Ready = true;
                    surface.addSnake();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };

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
                Log.e("SnakeBluetoothEstablish","创建房间失败  "+e.getMessage());
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
            }catch (Exception e) {
            }
        }
    }

    /**
     * 连接对方
     */
    private void connect(){
        onDialogLoading();
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
                        onDialogDismiss();
                        SDToast.showToast("加入成功");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                man1.setText(device.getName());
                            }
                        });
                    } catch (Exception e) {
                        Log.e("SnakBluetoothAdapter", "加入失败  " + e.getMessage());
                        try {
                            Log.e("SnakBluetoothAdapter", "尝试后退…");
                            socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);
                            socket.connect();
                            Log.e("SnakBluetoothAdapter", "Connected");
                            onDialogDismiss();
                            SDToast.showToast("加入成功");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    man1.setText(device.getName());
                                }
                            });
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
                    if (os != null) {
                        //往服务端写信息
                        BlueDeviceEntity entity = new BlueDeviceEntity();
                        entity.setName(mBluetoothAdapter.getName());
                        entity.setAddress(mBluetoothAdapter.getAddress());
                        entity.setAdd(true);
                        os.write(JsonUtil.obj2JsonString(entity).getBytes("utf-8"));
                    }
                } catch (Exception e) {
                }
            }
        });
        thread.start();
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
