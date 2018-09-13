package com.jiage.battle.dialog;

/**
 * 作者：李忻佳
 * 日期：2017/11/29/029.
 * 说明：SDDialogRefresh
 */

public interface SDDialogRefresh {
    /**
     * 警告
     */
    void onDialogWarning();

    /**
     * 警告
     */
    void onDialogWarning(String msg);

    /**
     * 加载中
     */
    void onDialogLoading();
    
    /**
     * 加载中/不需要延迟
     */
    void onDialogLoading(boolean delay);

    /**
     * 加载中
     */
    void onDialogLoading(String msg);

    /**
     * 加载中/不需要延迟
     */
    void onDialogLoading(String msg,boolean delay);

    /**
     * 失败
     */
    void onDialogFail();

    /**
     * 失败
     */
    void onDialogFail(String msg);

    /**
     * 成功
     */
    void onDialogSuccess();

    /**
     * 成功
     */
    void onDialogSuccess(String msg);

    /**
     * 笑脸
     */
    void onDialogLaugh();

    /**
     * 取消
     */
    void onDialogDismiss();
}
