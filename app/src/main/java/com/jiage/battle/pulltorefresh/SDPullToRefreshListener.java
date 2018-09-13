package com.jiage.battle.pulltorefresh;

import android.view.View;

/**
 * 作者：李忻佳
 * 时间：2017/11/28
 * 说明：SDPullToRefreshListener
 */
public interface SDPullToRefreshListener<T extends View> {

    void onPullDownToRefresh(T view);

    void onPullUpToRefresh(T view);

}
