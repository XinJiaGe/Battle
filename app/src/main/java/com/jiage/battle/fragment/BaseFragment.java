package com.jiage.battle.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiage.battle.R;
import com.jiage.battle.activity.BaseActivit;
import com.jiage.battle.view.TitleView;
import com.jia.dialog.LoadingDialog;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.sunday.eventbus.SDEventObserver;
import com.jiage.battle.common.SDFragmentManager;
import com.jiage.battle.dialog.SDDialogRefresh;
import com.jiage.battle.dialog.SDMyDialog;
import com.jiage.battle.sharedPreference.SharedPreference;
import com.jiage.battle.sharedPreference.SharedPreferenceConfig;
import com.jiage.battle.util.SDTimerUtil;
import com.zhy.autolayout.utils.ScreenUtils;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * 作者：李忻佳
 * 时间：2017/11/26
 * 说明：BaseFragment
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener,TitleView.onTitleClickListener, SDDialogRefresh, SDEventObserver {
    protected final String TAG = this.getClass().getSimpleName();
    private View mContextView = null;
    private Toast mToast;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    protected Activity mActivity;
    protected List<LoadingDialog> dialogLog = new ArrayList<>();
    private SDFragmentManager mFragmentManager;
    private boolean isDialog = true;
    /**
     * 是否无标题
     **/
    private boolean isNotTitle = true;
    protected TitleView mTitle;
    private FrameLayout titleFrame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = this.getActivity();
        initBar();
        View mView = bindView();
        if(mView == null){
            mContextView = inflater.inflate(bindLayout(), container, false);
        }else{
            mContextView = mView;
        }
        mContextView = setContentView(mContextView);
        x.view().inject(this, mContextView);
        SDEventManager.register(this);
        initView(mContextView);
        doView();
        doBusiness();
        return mContextView;
    }

    /**
     * 绑定布局
     *
     * @return
     */
    public abstract int bindLayout();
    /**
     * 绑定视图
     *
     * @return
     */
    public View bindView() {
        return null;
    }
    /**
     * 初始化控件
     *
     * @param view
     */
    public abstract void initView(View view);

    /**
     * @param view
     */
    public View setContentView(View view) {
        View VaseView = getLayoutInflater().inflate(R.layout.act_base, null);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setFitsSystemWindows(true);
        }
        //加载子类Activity的布局
        if (!isNotTitle) {//是否显示title
            //添加title
            titleFrame = VaseView.findViewById(R.id.act_base_title);
            mTitle = new TitleView(mActivity);
            mTitle.setmListener(this);
            titleFrame.addView(mTitle);
            if (((BaseActivit) mActivity).isSetStatusBar()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mTitle.setSetStatusBar();
                }
            }
        }
        //添加主布局
        FrameLayout contentView = (FrameLayout) VaseView.findViewById(R.id.act_base_content);
        contentView.addView(view);
        return VaseView;
    }

    /**
     * View操作
     */
    public void doView() {
    }
    /**
     * 初始titlebar
     */
    public void initBar() {
    }
    /**
     * View点击
     **/
    public void widgetClick(View view) {
    }

    /**
     * 删除title
     */
    public void removeTitle(){
        if(titleFrame!=null){
            titleFrame.removeAllViews();
        }
    }

    /**
     * 业务操作
     */
    public void doBusiness() {
    }

    ;

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }

    /**
     * 绑定控件
     *
     * @param resId
     * @return
     */
    public <T extends View> T $(int resId) {
        return (T) mContextView.findViewById(resId);
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
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(mActivity, clz));
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
        intent.setClass(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivityForResult(intent, requestCode);
    }
    /**
     * 获取FragmentManager
     *
     * @return
     */
    public SDFragmentManager getSDFragmentManager() {
        if (mFragmentManager == null) {
            mFragmentManager = new SDFragmentManager(getChildFragmentManager());
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
    public void setNotTitle(boolean notTitle) {
        isNotTitle = notTitle;
    }

    @Override
    public void onTitleBackListener() {
        mActivity.finish();
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
    public void onEvent(SDBaseEvent event) {
    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
    }

    @Override
    public void onEventBackgroundThread(SDBaseEvent event) {
    }

    @Override
    public void onEventAsync(SDBaseEvent event) {
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

	/**
     * 设置在沉浸式时title高度
     * @param ll
     */
    public void titleHeight(LinearLayout ll){
        if(isNotTitle){
            if(ll!=null){
                if (((BaseActivit) mActivity).isSetStatusBar()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        ll.setPadding(0, ScreenUtils.getStatusBarHeight(mActivity), 0, 0);
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SDEventManager.unregister(this);
    }
}
