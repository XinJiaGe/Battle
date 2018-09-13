package com.jiage.battle.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳.
 * 时间：2017/3/7.
 * 说明：
 */

public class PermissionUtil {
    private static PermissionUtil permissionUtil = null;
    private static List<String> mListPermissions;

    /**
     * 添加权限
     * author LH
     * data 2016/7/27 11:27
     */
    private void addAllPermissions(List<String> mListPermissions) {
        mListPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        mListPermissions.add(Manifest.permission.WAKE_LOCK);
        mListPermissions.add(Manifest.permission.INTERNET);
        mListPermissions.add(Manifest.permission.CHANGE_NETWORK_STATE);
        mListPermissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        mListPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        mListPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        mListPermissions.add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        mListPermissions.add(Manifest.permission.READ_PHONE_STATE);
        mListPermissions.add(Manifest.permission.GET_ACCOUNTS);
        mListPermissions.add(Manifest.permission.CAMERA);
        mListPermissions.add(Manifest.permission.CHANGE_WIFI_STATE);
        mListPermissions.add(Manifest.permission.ACCESS_WIFI_STATE);
        mListPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        mListPermissions.add(Manifest.permission.BROADCAST_STICKY);
        mListPermissions.add(Manifest.permission.WRITE_SETTINGS);
        mListPermissions.add(Manifest.permission.VIBRATE);
        mListPermissions.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);
        mListPermissions.add(Manifest.permission.GET_TASKS);
        mListPermissions.add(Manifest.permission.BATTERY_STATS);
        mListPermissions.add(Manifest.permission.RECORD_AUDIO);

    }

    /**
     * 单例模式初始化
     * author LH
     * data 2016/7/27 11:27
     */
    public static PermissionUtil getInstance() {
        if (permissionUtil == null) {
            permissionUtil = new PermissionUtil();
        }
        return permissionUtil;
    }

    /**
     * 判断当前为M以上版本
     * author LH
     * data 2016/7/27 11:29
     */
    private boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 获得没有授权的权限
     * author LH
     * data 2016/7/27 11:46
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    private List<String> findDeniedPermissions(Activity activity, List<String> permissions) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permissions) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 获取所有权限
     * author LH
     * data 2016/7/27 13:37
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    public void requestPermissions(Activity activity, int requestCode, onIsOverMarshmallow isOverMarshmallow) {
        if (!isOverMarshmallow()) {
            isOverMarshmallow.noIsOverMarshmallow();
            return;
        }
        if (mListPermissions == null) {
            mListPermissions = new ArrayList<String>();
            addAllPermissions(mListPermissions);
        }
        mListPermissions = findDeniedPermissions(activity, mListPermissions);
        if (mListPermissions != null && mListPermissions.size() > 0) {
            activity.requestPermissions(mListPermissions.toArray(new String[mListPermissions.size()]), requestCode);
        }
    }

    public interface onIsOverMarshmallow {
        public void noIsOverMarshmallow();
    }

}
