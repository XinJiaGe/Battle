package com.jiage.battle.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.jiage.battle.common.SDActivityManager;

/**
 * 作者：李忻佳
 * 时间：2017/11/27/027
 * 说明：SharedPreferences储存
 */

public class SharedPreference {
    private static SharedPreferenceConfig mySharedPer;
    private static SharedPreferences sharedPreferences;


    public static SharedPreferenceConfig getSharedPreference(Context context) {
        if(context!=null){
            if (mySharedPer == null) {
                mySharedPer = new SharedPreferenceConfig();
            }
            initSharedPreferences(context);
            return mySharedPer;
        }
        return null;
    }
    public static SharedPreferenceConfig getSharedPreference() {
        return getSharedPreference(SDActivityManager.getInstance().getLastActivity());
    }

    private static void initSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("ysy", Context.MODE_PRIVATE);
    }

    public void setBoolean(String s, boolean bool) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(s, bool);
        edit.commit();
    }

    public void setString(String s, String str) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(s, str);
        edit.commit();
    }

    public void setInt(String s, int it) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(s, it);
        edit.commit();
    }

    public void setLong(String s, long lo) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(s, lo);
        edit.commit();
    }

    public boolean getBoolean(String s, boolean bool) {
        return sharedPreferences.getBoolean(s, bool);
    }

    public String getString(String s, String str) {
        return sharedPreferences.getString(s, str);
    }

    public int getInt(String s, int it) {
        return sharedPreferences.getInt(s, it);
    }
    public long getLong(String s, long lo) {
        return sharedPreferences.getLong(s, lo);
    }
}
