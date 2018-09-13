package com.jiage.battle.util;

import android.app.Application;

/**
 * 作者：李忻佳
 * 日期：2017/12/3/003.
 * 说明：
 */

public class   SDLibrary {

    private static SDLibrary mInstance;
    private Application mApplication;

    public void init(Application application) {
        this.mApplication = application;
    }

    public static SDLibrary getInstance() {
        if (mInstance == null) {
            mInstance = new SDLibrary();
        }
        return mInstance;
    }

    public Application getApplication() {
        return mApplication;
    }
}
