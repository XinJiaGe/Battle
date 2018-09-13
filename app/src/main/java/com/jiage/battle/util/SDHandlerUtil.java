package com.jiage.battle.util;

import android.os.Handler;
import android.os.Looper;

/**
 * HandlerUtil
 */
public class SDHandlerUtil {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void removeCallbacks(Runnable r) {
        synchronized (mHandler) {
            mHandler.removeCallbacks(r);
        }
    }
    //在UI线程上运行
    public static void runOnUiThread(Runnable r) {
        synchronized (mHandler) {
            mHandler.post(r);
        }
    }
    //在队列的UI线程前面运行
    public static void runOnUiThreadFrontOfQueue(Runnable r) {
        synchronized (mHandler) {
            mHandler.postAtFrontOfQueue(r);
        }
    }

    public static void runOnUiThreadAtTime(Runnable r, long uptimeMillis) {
        synchronized (mHandler) {
            mHandler.postAtTime(r, uptimeMillis);
        }
    }

    public static void runOnUiThreadAtTime(Runnable r, Object msgObj, long uptimeMillis) {
        synchronized (mHandler) {
            mHandler.postAtTime(r, msgObj, uptimeMillis);
        }
    }

    public static void runOnUiThreadDelayed(Runnable r, long delayMillis) {
        synchronized (mHandler) {
            mHandler.postDelayed(r, delayMillis);
        }
    }

}
