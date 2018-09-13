package com.jiage.battle.js;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SDToast;

/**
 * 作者：李忻佳
 * 日期：2017/12/25/025.
 * 说明：
 */

public class AppJsHandler extends BaseJsHandler {
    private static final String DEFAULT_NAME = "App";

    public AppJsHandler(String name, Activity activity) {
        super(name, activity);
    }

    public AppJsHandler(Activity activity) {
        this(DEFAULT_NAME, activity);
    }

    @JavascriptInterface
    public void finish() {
        finishs();
    }

    @JavascriptInterface
    public void openIntent() {

    }

    @JavascriptInterface
    public void savePicture(String urls) {
        showToast(urls);
    }

    @JavascriptInterface
    public void lookPicture(String urls) {
        showToast(urls);
    }

    @JavascriptInterface
    public void Copy(String text) {
        OtherUtil.copyText(text);
        showToast("复制成功");
    }

    private void showToast(String text) {
        SDToast.showToast(text);
    }
}
