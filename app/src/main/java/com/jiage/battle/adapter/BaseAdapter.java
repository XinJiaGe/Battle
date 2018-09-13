package com.jiage.battle.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jiage.battle.dialog.SDDialogRefresh;
import com.jiage.battle.dialog.SDMyDialog;
import com.jiage.battle.util.SDTimerUtil;
import com.jia.dialog.LoadingDialog;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseAdapter<T> extends SDAdapter<T> implements SDDialogRefresh {
    private Toast mToast;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean isDialog = true;
    private List<LoadingDialog> dialogLog = new ArrayList<>();

    public BaseAdapter(List<T> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    protected View onGetView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int layoutId = getLayoutId(position, convertView, parent);
            convertView = mInflater.inflate(layoutId, parent, false);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        }
        bindData(position, convertView, parent, getItem(position));
        return convertView;
    }

    public abstract int getLayoutId(int position, View convertView, ViewGroup parent);

    public abstract void bindData(int position, View convertView, ViewGroup parent, T model);

    /**
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        mActivity.startActivity(new Intent(mActivity, clz));
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
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle,int flags) {
        Intent intent = new Intent();
        intent.setClass(mActivity, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(flags);
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
    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mActivity, cls);
        mActivity.startActivityForResult(intent, requestCode);
    }
    /**
     * 简化Toast
     */
    protected void showToastLooper(String message) {
        Looper.prepare();
        if (mToast == null) {
            mToast = Toast.makeText(mActivity, message, Toast.LENGTH_SHORT);
        }
        mToast.setText(message);
        mToast.cancel();
        mHandler.removeCallbacks(mShowToastRunnable);
        mHandler.postDelayed(mShowToastRunnable, 0);
        Looper.loop();
    }
    /**
     * 简化Toast
     */
    protected void showToast(String messageId) {
        if (mToast == null) {
            mToast = Toast.makeText(mActivity, messageId, Toast.LENGTH_SHORT);
        }
        mToast.setText(messageId);
        mToast.cancel();
        mHandler.removeCallbacks(mShowToastRunnable);
        mHandler.postDelayed(mShowToastRunnable, 0);
    }

    /**
     * show Toast
     */
    private Runnable mShowToastRunnable = new Runnable() {
        @Override
        public void run() {
            mToast.show();
        }
    };

    /**
     * 设置8毫秒内如果没执行Dismiss就显示dialog
     *
     * @param msg
     * @param delay //是否延迟
     */
    private void dialogTimeSetting(final String msg,boolean delay) {
        if(delay){
            isDialog = true;
            new SDTimerUtil().startWork(500, new SDTimerUtil.SDTimerListener() {
                @Override
                public void onWork() {

                }

                @Override
                public void onWorkMain() {
                    if (isDialog) {
                        if (msg == null) {
                            dialogLog.add(SDMyDialog.loading());
                        } else {
                            dialogLog.add(SDMyDialog.loading(msg));
                        }
                    }
                }
            });
        }else{
            if (msg == null) {
                dialogLog.add(SDMyDialog.loading());
            } else {
                dialogLog.add(SDMyDialog.loading(msg));
            }
        }
    }

    @Override
    public void onDialogSuccess() {
        SDMyDialog.success();
    }

    @Override
    public void onDialogSuccess(String msg) {
        SDMyDialog.success(msg);
    }

    @Override
    public void onDialogFail() {
        SDMyDialog.fail();
    }

    @Override
    public void onDialogFail(String msg) {
        SDMyDialog.fail(msg);
    }

    @Override
    public void onDialogLaugh() {
        SDMyDialog.laugh();
    }

    @Override
    public void onDialogLoading(String msg,boolean delay) {
        dialogTimeSetting(msg,delay);
    }

    @Override
    public void onDialogLoading(boolean delay) {
        dialogTimeSetting(null,delay);
    }
    @Override
    public void onDialogLoading() {
        dialogTimeSetting(null,true);
    }

    @Override
    public void onDialogLoading(String msg) {
        dialogTimeSetting(msg,true);
    }
    @Override
    public void onDialogWarning() {
        SDMyDialog.warning();
    }

    @Override
    public void onDialogWarning(String msg) {
        SDMyDialog.warning(msg);
    }

    @Override
    public void onDialogDismiss() {
        isDialog = false;
        if (dialogLog != null) {
            Iterator<LoadingDialog> dialog = dialogLog.iterator();
            while (dialog.hasNext()) {
                LoadingDialog log = dialog.next();
                if (log != null) {
                    log.cancel();
                    dialog.remove();
                }
            }
        }
    }
}
