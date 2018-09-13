package com.jiage.battle.util;

import android.content.Context;
import android.content.Intent;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：指纹utile
 */

public class FingerprintUtil {
    private static final String ACTION_SETTING = "android.settings.SETTINGS";

    /**
     * 进入手机设置指纹
     * @param context
     */
    public static void openFingerPrintSettingPage(Context context) {
        Intent intent = new Intent(ACTION_SETTING);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }
}
