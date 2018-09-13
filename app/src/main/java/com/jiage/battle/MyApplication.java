package com.jiage.battle;

import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.jiage.battle.common.ImgConfig;
import com.jiage.battle.util.SDLibrary;
import com.sunday.eventbus.SDEventManager;

import org.xutils.x;


/**
 * 作者：李忻佳
 * 时间：2017/11/26/026
 * 说明：MyApplication
 */

public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;
    public static boolean isDebug = false;//是否是debug模式
    public static boolean msgRoaming = true;//消息是否漫游
    public static String DbVersion = "4";//数据库版本
    public static String IP;

    /**
     * 初始化Application
     *
     * @return
     */
    public static MyApplication getApplication() {
        if (myApplication == null) {
            synchronized (MyApplication.class) {
                if (myApplication == null) {
                    myApplication = new MyApplication();
                }
            }
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initTextAndFormal(getResources().getString(R.string.type));
        //library初始
        SDLibrary.getInstance().init(getApplication());
        // android 7.0系统解决拍照的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        // 初始化ImageLoader
        ImgConfig.initImgConfig(this);
        //xUtils3
        x.Ext.init(this);
        x.Ext.setDebug(isDebug); //输出debug日志，开启会影响性能
    }

    /**
     * 根据budle初始化
     *
     * @param string
     */
    private void initTextAndFormal(String string) {
        switch (string) {
            case "zs":
                isDebug = false;
                IP = "api.ixiaobaitu.com";
                break;
        }
    }

    @Override
    public void onTerminate() {
        SDEventManager.unregister(this);
        super.onTerminate();
    }
}
