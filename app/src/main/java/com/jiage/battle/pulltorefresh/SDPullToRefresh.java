package com.jiage.battle.pulltorefresh;

/**
 * 作者：李忻佳
 * 时间：2017/11/28
 * 说明：SDPullToRefresh
 */
public interface SDPullToRefresh {
    /**
     * 设置刷新中
     */
    void setRefreshing();

    /**
     * 设置只允许下拉刷新
     */
    void setModePullFromStart();

    /**
     * 设置只允许上拉加载更多
     */
    void setModePullFromEnd();

    /**
     * 设置既可以下拉刷新也可以上拉加载更多
     */
    void setModeBoth();

    /**
     * 设置屏蔽下拉刷新和上拉加载更多
     */
    void setModeDisabled();

    /**
     * 设置刷新完毕
     */
    void onRefreshComplete();


}
