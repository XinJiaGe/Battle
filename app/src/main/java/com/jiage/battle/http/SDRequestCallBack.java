package com.jiage.battle.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jiage.battle.common.SDActivityManager;
import com.jiage.battle.entity.BaseEntity;
import com.jiage.battle.util.AppUtil;
import com.jiage.battle.util.OtherUtil;
import com.jiage.battle.util.SDToast;

import java.lang.reflect.Type;

public abstract class SDRequestCallBack<E> extends RequestCallBack<String> {
    private boolean mToast = true;
    public String responseInfo;
    private E actModel;
    private BaseEntity baseActModel;

    public SDRequestCallBack(boolean toast) {
        this.mToast = toast;
    }

    public SDRequestCallBack() {
    }

    @Override
    public void onSuccessBack(String responseInfo) {
        this.responseInfo = responseInfo;
        parseActModel();
        isSingleModel();
        super.onSuccessBack(responseInfo);
    }

    @Override
    public void onSuccess(String responseInfo) {
        if (baseActModel != null && baseActModel.getCode() != null) {
            String code = baseActModel.getCode();
            if (code.equals("0000")) {
                onSuccess(actModel);
            } else if (code.equals("0002")) {
                AppUtil.outLogin();
                SDToast.showToast(baseActModel.getMsg());
                onFailure(code, baseActModel.getMsg());
            } else {
                if (mToast) {
                    SDToast.showToast(baseActModel.getMsg());
                }
                onFailure(code, baseActModel.getMsg());
            }
        } else {
            onSuccess(actModel);
        }
    }

    public abstract void onSuccess(E entity);

    @SuppressWarnings("unchecked")
    protected void parseActModel() {
        Class<E> clazz = null;
        Type type = OtherUtil.getType(getClass(), 0);
        if (type instanceof Class) {
            clazz = (Class<E>) type;
            try {
                actModel = JSON.parseObject(this.responseInfo, clazz);
                Log.e("MYHTTP", "RESOUT:   " + responseInfo);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MYHTTP", "解析出错:" + e.getMessage());
                actModel = JSON.parseObject("{\"icon_scan_code\": 0,\"msg\": \"APP解析出错" + e.getMessage() + "\"}", clazz);
            }
        }
    }

    private void isSingleModel() {
        if (actModel instanceof BaseEntity) {
            baseActModel = (BaseEntity) actModel;
        }
    }
}
