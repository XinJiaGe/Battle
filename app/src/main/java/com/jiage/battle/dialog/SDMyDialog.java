package com.jiage.battle.dialog;

import com.jia.dialog.LibraryDialog;
import com.jia.dialog.LoadingDialog;
import com.jia.dialog.TipsToast;
import com.jiage.battle.common.SDActivityManager;

/**
 * 作者：李忻佳.
 * 时间：2017/4/6.
 * 说明：调用dialog
 */

public class SDMyDialog {
    /**
     * 加载中
     *
     * @return
     */
    public static LoadingDialog loading() {
        return LibraryDialog.loading(SDActivityManager.getInstance().getLastActivity());
    }

    /**
     * 加载中
     *
     * @param msg 加载描述
     * @return
     */
    public static LoadingDialog loading(String msg) {
        return LibraryDialog.loading(SDActivityManager.getInstance().getLastActivity(), msg);
    }

    /**
     * 警告
     *
     * @return
     */
    public static TipsToast warning() {
        return LibraryDialog.warning(SDActivityManager.getInstance().getLastActivity());
    }

    /**
     * 警告
     *
     * @param msg 警告描述
     * @return
     */
    public static TipsToast warning(String msg) {
        return LibraryDialog.warning(SDActivityManager.getInstance().getLastActivity(), msg);
    }

    /**
     * 成功
     *
     * @return
     */
    public static TipsToast success() {
        return LibraryDialog.success(SDActivityManager.getInstance().getLastActivity());
    }

    /**
     * 成功
     *
     * @param msg 成功描述
     * @return
     */
    public static TipsToast success(String msg) {
        return LibraryDialog.success(SDActivityManager.getInstance().getLastActivity(), msg);
    }

    /**
     * 失败
     *
     * @return
     */
    public static TipsToast fail() {
        return LibraryDialog.fail(SDActivityManager.getInstance().getLastActivity());
    }

    /**
     * 失败
     *
     * @param msg 失败描述
     * @return
     */
    public static TipsToast fail(String msg) {
        return LibraryDialog.fail(SDActivityManager.getInstance().getLastActivity(), msg);
    }

    /**
     * 笑脸
     *
     * @return
     */
    public static TipsToast laugh() {
        return LibraryDialog.laugh(SDActivityManager.getInstance().getLastActivity());
    }
}
