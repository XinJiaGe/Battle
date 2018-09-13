package com.jiage.battle.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.jiage.battle.common.SDActivityManager;

import java.io.IOException;
import java.util.List;

/**
 * package包工具类
 *
 * @author zhengjun
 */
public class SDPackageUtil {

    /**
     * 获取设备ID
     */
    public static String getDeviceId() {
        try {
            TelephonyManager tm = (TelephonyManager) SDActivityManager.getInstance().getLastActivity().getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();
            return deviceId;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void chmod(String permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Boolean isPackageExist(String packageName) {
        PackageManager manager = SDActivityManager.getInstance().getLastActivity().getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (PackageInfo pi : pkgList) {
            if (pi.packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }


    public static PackageInfo getApkPackageInfo(String apkFilePath) {
        PackageManager pm = SDActivityManager.getInstance().getLastActivity().getPackageManager();
        PackageInfo apkInfo = pm.getPackageArchiveInfo(apkFilePath, PackageManager.GET_META_DATA);
        return apkInfo;
    }

    public static PackageInfo getPackageInfo(String packageName) {
        PackageManager pm = SDActivityManager.getInstance().getLastActivity().getPackageManager();
        PackageInfo apkInfo = null;
        try {
            apkInfo = pm.getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
        }
        return apkInfo;
    }

    public static PackageInfo getCurrentPackageInfo() {
        return getPackageInfo(getPackageName());
    }

    public static Boolean installApkPackage(String apkFilePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFilePath), "application/vnd.android.package-archive");
        SDActivityManager.getInstance().getLastActivity().startActivity(intent);
        return true;
    }

    public static Bundle getMetaData(Context context) {
        try {
            ApplicationInfo info = SDActivityManager.getInstance().getLastActivity().getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPackageName() {
        return SDActivityManager.getInstance().getLastActivity().getPackageName();
    }

    public static void startAPP(String appPackageName) {
        try {
            Intent intent = SDActivityManager.getInstance().getLastActivity().getPackageManager().getLaunchIntentForPackage(appPackageName);
            SDActivityManager.getInstance().getLastActivity().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startCurrentApp() {
        startAPP(SDActivityManager.getInstance().getLastActivity().getPackageName());
    }

    public static boolean isBackground() {
        ActivityManager activityManager = (ActivityManager) SDActivityManager.getInstance().getLastActivity().getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(getPackageName())) {
                if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static int getVersionCode() {
        PackageInfo pi = getCurrentPackageInfo();
        return pi.versionCode;
    }

    public static String getVersionName() {
        PackageInfo pi = getCurrentPackageInfo();
        return pi.versionName;
    }

}
