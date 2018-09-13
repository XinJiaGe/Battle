package com.jiage.battle.util;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.jiage.battle.activity.BaseActivit;
import com.jiage.battle.activity.MainActivity;
import com.jiage.battle.common.SDActivityManager;
import com.jiage.battle.dao.JsonDbModelDao;
import com.jiage.battle.dao.UserDbDao;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.dialog.SDDialogCustom;
import com.jiage.battle.dialog.SDMyDialog;
import com.jiage.battle.entity.GetOperatePasswordEntity;
import com.jiage.battle.entity.GetUserListEntity;
import com.jiage.battle.entity.UpdataAppEntity;
import com.jiage.battle.entity.UserEntity;
import com.jiage.battle.event.EnumEventTag;
import com.jiage.battle.http.InterfaceServer;
import com.jiage.battle.http.SDRequestCallBack;
import com.jiage.battle.model.RequestModel;
import com.jiage.battle.sharedPreference.SharedPreference;
import com.jia.dialog.LoadingDialog;
import com.sunday.eventbus.SDEventManager;

import org.xutils.common.Callback;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2017/12/1/001.
 * 说明：
 */

public class AppUtil {
    private static Callback.Cancelable cancelable;
    private static int noticeId;
    private static boolean isNotice = false;
    private static List<LoadingDialog> dialogLog = new ArrayList<>();

    /**
     * 登录成功
     *
     * @param entity
     */
    public static void loginSuccess(UserEntity entity) {
        JsonDbModelDao.getInstance().insertOrUpdate(entity, true);
        UserDbDao.getInstance().insertOrUpdate(entity);
        SDEventManager.post(EnumEventTag.LOGIN_SUCCESSFUL.ordinal());
    }

    /**
     * 退出登陆
     */
    public static void outLogin() {
        JsonDbModelDao.getInstance().delete(UserEntity.class);
        JsonDbModelDao.getInstance().delete(GetUserListEntity.class);
        SDEventManager.post(EnumEventTag.EXIT_LOGIN.ordinal());
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        UserEntity user = JsonDbModelDao.getInstance().query(UserEntity.class, true);
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }
}
