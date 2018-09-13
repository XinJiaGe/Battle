package com.jiage.battle.constant;

import com.jiage.battle.MyApplication;

import org.xutils.x;

/**
 * 作者：李忻佳
 * 日期：2017/11/28/028.
 * 说明：
 */

public class ApkConstant {
    public static String API_URL_AGR = "http://";
    public static String API_URL_DOM = MyApplication.getApplication().IP;
    public static String API_URL_CAT = "/api/";

    public static String getApiUrl(){
        return API_URL_AGR+API_URL_DOM+API_URL_CAT;
    }

}
