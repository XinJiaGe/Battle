package com.jiage.battle.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 作者：李忻佳
 * 时间：2017/11/26/026
 * 说明：
 */

public class TabInfo {
    public Fragment fragment;// 根据clazz和args实例化出来的Fragment对象
    public Class<?> clazz;// Fragment类的class对象
    public Bundle args;// 往Fragment传递参数的Bundle

    public TabInfo(Class<? extends Fragment> clazz, Bundle args) {
        this.clazz = clazz;
        this.args = args;
    }
}
