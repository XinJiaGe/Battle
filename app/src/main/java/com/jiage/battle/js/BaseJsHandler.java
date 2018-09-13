package com.jiage.battle.js;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseJsHandler {
    private String name;
    protected Activity mActivity;

    public BaseJsHandler(String name, Activity activity) {
        super();
        this.name = name;
        this.mActivity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void finishs() {
        if (mActivity != null) {
            mActivity.finish();
        }
    }
    /**
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        mActivity.startActivity(new Intent(mActivity, clz));
    }

    /**
     * 页面跳转
     *
     * @param clz
     * @param flags 意图
     */
    public void startActivity(Class<?> clz, int flags) {
        Intent intent = new Intent(mActivity, clz);
        intent.addFlags(flags);
        mActivity.startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivityForResult(intent, requestCode);
    }
}
