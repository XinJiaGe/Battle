package com.jiage.battle.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiage.battle.R;
import com.jiage.battle.common.SDActivityManager;
import com.jiage.battle.common.SDFragmentManager;
import com.jiage.battle.dialog.SDDialogRefresh;
import com.jiage.battle.dialog.SDMyDialog;
import com.jiage.battle.event.EnumEventTag;
import com.jiage.battle.sharedPreference.SharedPreference;
import com.jiage.battle.sharedPreference.SharedPreferenceConfig;
import com.jiage.battle.util.SDPackageUtil;
import com.jiage.battle.util.SDTimerUtil;
import com.jiage.battle.view.TitleView;
import com.jia.dialog.LoadingDialog;
import com.jia.swipeback.SwipeBackActivity;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.sunday.eventbus.SDEventObserver;
import com.zhy.autolayout.utils.ScreenUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/11/26
 * 说明：BaseActivit
 */

public abstract class BaseActivit extends SwipeBackActivity implements View.OnClickListener, SDEventObserver, TitleView.onTitleClickListener, SDDialogRefresh {
    public static int INSTALL_PACKAGES_REQUESTCODE = 11110;
    public static int GET_UNKNOWN_APP_SOURCES = 11111;
    public static String installApkPath = "";
    public static int ForcedToupdate = 1;
    public static final int REQUESTCODE = 1211;
    protected final String TAG = this.getClass().getSimpleName();
    protected Activity mActivity;
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;
    /**
     * 是否无标题
     **/
    private boolean isNotTitle = false;
    /**
     * 是否无状态栏
     **/
    private boolean isNotStatusBar = false;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    private Toast mToast;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    /* titleView */
    protected TitleView mTitle;
    private List<LoadingDialog> dialogLog = new ArrayList<>();
    private SDFragmentManager mFragmentManager;
    private boolean isDialog = true;
    private FrameLayout titleFrame;
    private FrameLayout contentView;
    public boolean reception = true;//是否是前台运行
    //用来控制应用前后台切换的逻辑
    private boolean isCurrentRunningForeground = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDActivityManager.getInstance().onCreate(this);
        initBar();
        Intent intent = getIntent();
        if (intent != null) {
            initIntent(intent);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                initParms(bundle);
            }
        }
        mActivity = this;
        View mView = bindView();
        if (null == mView) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        } else
            mContextView = mView;
        if (isSetStatusBar) {
            steepStatusBar();
        }
        setContentView(mContextView);
        SDEventManager.register(this);
        x.view().inject(this);
        initView(mContextView);
        doView();
        doBusiness();
    }

    @Override
    public void setContentView(View view) {
        if (isNotStatusBar) {//是否显示状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        View VaseView = getLayoutInflater().inflate(R.layout.act_base, null);
        //设置填充act_base布局
        super.setContentView(VaseView);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setFitsSystemWindows(true);
        }
        //加载子类Activity的布局
        if (!isNotTitle) {//是否显示title
            //添加title
            titleFrame = mActivity.findViewById(R.id.act_base_title);
            mTitle = new TitleView(mActivity);
            mTitle.setmListener(this);
            titleFrame.addView(mTitle);
            if (!isNotStatusBar && isSetStatusBar) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mTitle.setSetStatusBar();
                }
            }
        }
        //添加主布局
        contentView = (FrameLayout) findViewById(R.id.act_base_content);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        params.setMargins(0, -ScreenUtils.getStatusBarHeight(mActivity), 0, 0);
        contentView.setLayoutParams(params);
        contentView.addView(view);
    }

    @Override
    public void onClick(View view) {
        if (fastClick()) {
            widgetClick(view);
        }
    }

    /**
     * 初始化参数
     *
     * @param intent
     */
    public void initIntent(Intent intent) {
    }

    /**
     * 初始化参数
     *
     * @param parms
     */
    public void initParms(Bundle parms) {
    }

    /**
     * 绑定视图
     *
     * @return
     */
    public View bindView() {
        return null;
    }

    /**
     * 绑定控件
     *
     * @param resId
     * @return
     */
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * 绑定布局
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * 初始化控件
     *
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * View操作
     */
    public void doView() {
    }

    ;

    /**
     * 业务操作
     */
    public void doBusiness() {
    }

    ;

    /**
     * 初始titlebar
     */
    public void initBar() {
    }

    ;

    /**
     * View点击
     *
     * @param view
     */
    public void widgetClick(View view) {
    }

    /**
     * 删除title
     */
    public void removeTitle() {
        if (titleFrame != null) {
            titleFrame.removeAllViews();
        }
        if (contentView != null) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            contentView.setLayoutParams(params);
        }
    }

    /**
     * 沉浸状态栏
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void steepStatusBar() {
//            // 透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getWindow().getDecorView().setFitsSystemWindows(true);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //透明状态栏 @顶部
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏 @底部 这一句不要加，目的是防止沉浸式状态栏和部分底部自带虚拟按键的手机（比如华为）发生冲突，注释掉就好了
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 防止快速点击
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * 简化Toast
     */
    protected void showToastLooper(String message) {
        Looper.prepare();
        if (mToast == null) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
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
    protected void showToast(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        }
        mToast.setText(message);
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
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivit.this, clz));
    }

    /**
     * 页面跳转
     *
     * @param clz
     * @param flags 意图
     */
    public void startActivity(Class<?> clz, int flags) {
        Intent intent = new Intent(BaseActivit.this, clz);
        intent.addFlags(flags);
        startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * Class打开编辑界面
     *
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 获取FragmentManager
     *
     * @return
     */
    public SDFragmentManager getSDFragmentManager() {
        if (mFragmentManager == null) {
            mFragmentManager = new SDFragmentManager(getSupportFragmentManager());
        }
        return mFragmentManager;
    }

    /**
     * 获取SharedPreference
     *
     * @return
     */
    public SharedPreferenceConfig getSharedPreference() {
        return SharedPreference.getSharedPreference(mActivity);
    }

    /**
     * 设置8毫秒内如果没执行Dismiss就显示dialog
     *
     * @param msg
     * @param delay //是否延迟
     */
    private void dialogTimeSetting(final String msg, boolean delay) {
        if (delay) {
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
        } else {
            if (msg == null) {
                dialogLog.add(SDMyDialog.loading());
            } else {
                dialogLog.add(SDMyDialog.loading(msg));
            }
        }
    }

    /**
     * 手动回收图片
     *
     * @param imageView
     */
    public void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    /**
     * 设置LinearLayout的padding以适配沉浸式
     *
     * @param ll
     */
    public void titleHeight(LinearLayout ll) {
        if (isNotTitle) {
            if (ll != null) {
                if (!isNotStatusBar && isSetStatusBar) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        ll.setPadding(0, ScreenUtils.getStatusBarHeight(mActivity), 0, 0);
                    }
                }
            }
        }
    }

    public void setNotTitle(boolean notTitle) {
        isNotTitle = notTitle;
    }

    public void setNotStatusBar(boolean notStatusBar) {
        isNotStatusBar = notStatusBar;
    }

    public void setSetStatusBar(boolean setStatusBar) {
        isSetStatusBar = setStatusBar;
    }

    public boolean isSetStatusBar() {
        return isSetStatusBar;
    }

    @Override
    public void onEvent(SDBaseEvent event) {
    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case LOGIN_SUCCESSFUL:
                break;
        }
    }

    @Override
    public void onEventBackgroundThread(SDBaseEvent event) {
    }

    @Override
    public void onEventAsync(SDBaseEvent event) {
    }

    @Override
    public void onTitleBackListener() {
        finish();
    }

    @Override
    public void onTitleCenterListener() {
    }

    @Override
    public void onTitleRightListener() {
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

    /**
     * @param msg
     * @param delay 是否延迟
     */
    @Override
    public void onDialogLoading(String msg, boolean delay) {
        dialogTimeSetting(msg, delay);
    }

    @Override
    public void onDialogLoading(boolean delay) {
        dialogTimeSetting(null, delay);
    }

    @Override
    public void onDialogLoading() {
        dialogTimeSetting(null, true);
    }

    @Override
    public void onDialogLoading(String msg) {
        dialogTimeSetting(msg, true);
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

    @Override
    protected void onStart() {
        super.onStart();
        if (!isCurrentRunningForeground) {
            reception = true;
            NotificationManager mNotificationManager = (NotificationManager) mActivity.getSystemService(NOTIFICATION_SERVICE);
            mNotificationManager.cancelAll();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isCurrentRunningForeground = isRunningForeground();
        if (!isCurrentRunningForeground) {
            reception = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SDActivityManager.getInstance().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SDActivityManager.getInstance().onDestroy(this);
        SDEventManager.unregister(this);
    }

    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                    Log.d(TAG, "EntryActivity isRunningForeGround");
                    return true;
                }
            }
        }
        Log.d(TAG, "EntryActivity isRunningBackGround");
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_UNKNOWN_APP_SOURCES) {
            if (Build.VERSION.SDK_INT >= 26) {
                boolean b = SDActivityManager.getInstance().getLastActivity().getPackageManager().canRequestPackageInstalls();
                if (b) {
                    SDPackageUtil.installApkPackage(installApkPath);//安装应用的逻辑(写自己的就可以)
                    if (ForcedToupdate == 0) {
                        SDActivityManager.getInstance().finishAllActivity();
                    }
                } else {
                    //请求安装未知应用来源的权限
                    ActivityCompat.requestPermissions(SDActivityManager.getInstance().getLastActivity(), new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, MainActivity.INSTALL_PACKAGES_REQUESTCODE);
                }
            } else {
                SDPackageUtil.installApkPackage(installApkPath);
                if (ForcedToupdate == 0) {
                    SDActivityManager.getInstance().finishAllActivity();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == INSTALL_PACKAGES_REQUESTCODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SDPackageUtil.installApkPackage(installApkPath);
                if (ForcedToupdate == 0) {
                    SDActivityManager.getInstance().finishAllActivity();
                }
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
            }
        }
    }
}
