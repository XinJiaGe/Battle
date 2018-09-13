package com.jiage.battle.http;

import android.text.TextUtils;
import android.util.Log;

import com.jiage.battle.MyApplication;
import com.jiage.battle.util.SDToast;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.xutils.common.Callback;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class RequestCallBackProxy implements Callback.CacheCallback<String> {
    private static final String STRING_NULL = "\":null";
    private static final String STRING_FALSE = "\":false";
    private static final String STRING_EMPTY_ARRAY = "\":[]";
    private RequestCallBack<String> mOriginalCallBack;

    public RequestCallBackProxy(RequestCallBack<String> originalCallBack) {
        this.mOriginalCallBack = originalCallBack;
        if (mOriginalCallBack != null) {
            mOriginalCallBack.onStart();
        }
    }

    @Override
    public boolean onCache(String result) {
        return false;
    }

    @Override
    public void onSuccess(String result) {
        Log.d("MYHTTP", "原始结果:" + result);
        if (MyApplication.getApplication().isDebug) {
            result = beforeOnSuccessBack(result);
            if (mOriginalCallBack != null) {
                mOriginalCallBack.onSuccessBack(result);
            }
        } else {
            try {
                result = beforeOnSuccessBack(result);
                if (mOriginalCallBack != null) {
                    mOriginalCallBack.onSuccessBack(result);
                }
            } catch (Exception e) {
                showErrorTip(e);
            }
        }
        if (beforeOnSuccess(result)) {
        } else {
            if (MyApplication.getApplication().isDebug) {
                if (mOriginalCallBack != null) {
                    mOriginalCallBack.onSuccess(result);
                }
            } else {
                try {
                    if (mOriginalCallBack != null) {
                        mOriginalCallBack.onSuccess(result);
                    }
                } catch (Exception e) {
                    showErrorTip(e);
                }
            }
        }
    }

    /**
     * @param responseInfo
     * @return true:回调不继续执行，false:回调继续执行
     */
    private boolean beforeOnSuccess(String responseInfo) {
        if (checkLoginState()) {
            return true;
        }
        return false;
    }

    private boolean checkLoginState() {
        return false;
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        showErrorTip(ex);
        if (mOriginalCallBack != null) {
            mOriginalCallBack.onFailure("", ex.getMessage());
            mOriginalCallBack.onFinish();
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        if (mOriginalCallBack != null) {
            mOriginalCallBack.onCancelled();
        }
    }

    @Override
    public void onFinished() {
        if (mOriginalCallBack != null) {
            mOriginalCallBack.onFinish();
        }
    }

    private void showErrorTip(Throwable error) {
        if (error != null) {
            if (error instanceof JSONException) {
                showErrorToast("数据解析异常!");
            } else if (error instanceof UnknownHostException) {
                showErrorToast("无法访问的服务器地址!");
            } else if (error instanceof ConnectException) {
                showErrorToast("连接服务器失败!");
            } else if (error instanceof SocketTimeoutException || error instanceof ConnectTimeoutException) {
                showErrorToast("连接超时!");
            } else if (error instanceof SocketException) {
                showErrorToast("连接服务器失败!");
            } else {
//				showErrorToast("未知错误,请重试!");
            }
        }
    }

    private void showErrorToast(String text) {
        SDToast.showToast(text);
    }

    private String beforeOnSuccessBack(String content) {
        if (!TextUtils.isEmpty(content)) {
//            // 替换false为null
//            if (content.contains(STRING_FALSE)) {
//                content = content.replace(STRING_FALSE, STRING_NULL);
//            }
            // 替换[]为null
            if (content.contains(STRING_EMPTY_ARRAY)) {
                content = content.replace(STRING_EMPTY_ARRAY, STRING_NULL);
            }
        }
        return content;
    }
}
