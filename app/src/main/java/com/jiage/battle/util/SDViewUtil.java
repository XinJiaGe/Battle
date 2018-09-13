package com.jiage.battle.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import com.jiage.battle.adapter.SDAdapter;
import com.jiage.battle.adapter.SDBaseAdapter;
import com.jiage.battle.common.SDActivityManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/11/27/027
 * 说明：
 */

public class SDViewUtil {
    /**
     * 获取资源id
     *
     * @param context
     * @param id
     * @return
     */
    public static View getRId(Context context, int id) {
        return LayoutInflater.from(context).inflate(id, null);
    }

    /**
     * 判断当前线程是否是UI线程.
     *
     * @return
     */
    public static boolean isUIThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 隐藏输入法
     *
     * @param view
     */
    public static void hideInputMethod(View view) {
        hideInputMethod(view, SDActivityManager.getInstance().getLastActivity().getApplication());
    }

    public static void hideInputMethod(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示输入法
     *
     * @param view
     * @param view
     */
    public static void showInputMethod(View view) {
        showInputMethod(view, SDActivityManager.getInstance().getLastActivity().getApplication(), 0);
    }

    public static void showInputMethod(View view, long delay) {
        showInputMethod(view, SDActivityManager.getInstance().getLastActivity().getApplication(), delay);
    }

    /**
     * 显示输入法
     *
     * @param context
     * @param view
     * @param delay
     */
    public static void showInputMethod(final View view, final Context context, long delay) {
        if (delay < 0) {
            delay = 0;
        }

        SDHandlerUtil.runOnUiThreadDelayed(new Runnable() {

            @Override
            public void run() {
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, delay);
    }

    /**
     * 重置listview高度，解决和scrollview嵌套问题
     *
     * @param listView
     */
    public static void resetListViewHeightBasedOnChildren(ListView listView) {
        int totalHeight = getListViewTotalHeight(listView);
        if (totalHeight > 0) {
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight;
            params.height += 5;
            listView.setLayoutParams(params);
        }
    }

    /**
     * 设置背景图片drawable
     *
     * @param view
     * @param drawable
     */
    public static void setBackgroundDrawable(View view, Drawable drawable) {
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        view.setBackgroundDrawable(drawable);
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    /**
     * 设置背景图片resource
     *
     * @param view
     * @param resId
     */
    public static void setBackgroundResource(View view, int resId) {
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        view.setBackgroundResource(resId);
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    /**
     * 设置背景颜色resId
     *
     * @param view
     * @param resId
     */
    public static void setBackgroundColorResId(View view, int resId) {
        view.setBackgroundColor(SDResourcesUtil.getColor(resId));
    }

    /**
     * 设置textview颜色ResId
     *
     * @param textView
     * @param resId
     */
    public static void setTextViewColorResId(TextView textView, int resId) {
        textView.setTextColor(SDResourcesUtil.getColor(resId));
    }

    /**
     * 设置scrllToView的Y
     *
     * @param sv
     * @param y
     * @param delay
     */
    @SuppressLint("NewApi")
    public static void scrollToViewY(final ScrollView sv, final int y, int delay) {
        if (sv != null && delay >= 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                SDHandlerUtil.runOnUiThreadDelayed(new Runnable() {

                    @Override
                    public void run() {
                        sv.scrollTo(0, y);
                    }
                }, delay);
            }
        }
    }

    // -------------------------layoutParams
    // LinearLayout
    public static LinearLayout.LayoutParams getLayoutParamsLinearLayoutWW() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams getLayoutParamsLinearLayoutMM() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams getLayoutParamsLinearLayoutMW() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams getLayoutParamsLinearLayoutWM() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    // RelativeLayout
    public static RelativeLayout.LayoutParams getLayoutParamsRelativeLayoutWW() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams getLayoutParamsRelativeLayoutMM() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    public static RelativeLayout.LayoutParams getLayoutParamsRelativeLayoutMW() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public static RelativeLayout.LayoutParams getLayoutParamsRelativeLayoutWM() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    // FrameLayout
    public static FrameLayout.LayoutParams getLayoutParamsFrameLayoutWW() {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams getLayoutParamsFrameLayoutMM() {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public static FrameLayout.LayoutParams getLayoutParamsFrameLayoutMW() {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams getLayoutParamsFrameLayoutWM() {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
    }

    // ViewGroup
    public static ViewGroup.LayoutParams getLayoutParamsViewGroupWW() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams getLayoutParamsViewGroupMM() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static ViewGroup.LayoutParams getLayoutParamsViewGroupMW() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams getLayoutParamsViewGroupWM() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    // ------------------------layoutInflater
    public static LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(SDActivityManager.getInstance().getLastActivity().getApplication());
    }

    public static View inflate(int resource, ViewGroup root) {
        return getLayoutInflater().inflate(resource, root);
    }

    public static View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return getLayoutInflater().inflate(resource, root, attachToRoot);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return SDActivityManager.getInstance().getLastActivity().getApplication().getResources().getDisplayMetrics();
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics metrics = getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics metrics = getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * 屏幕宽度的百分比
     *
     * @param percent 0-1
     * @return
     */
    public static int getScreenWidthPercent(float percent) {
        return (int) ((float) getScreenWidth() * percent);
    }

    /**
     * 屏幕高度的百分比
     *
     * @param percent 0-1
     * @return
     */
    public static int getScreenHeightPercent(float percent) {
        return (int) ((float) getScreenHeight() * percent);
    }

    /**
     * 获取密度
     *
     * @return
     */
    public static float getDensity() {
        return SDActivityManager.getInstance().getLastActivity().getApplication().getResources().getDisplayMetrics().density;
    }

    public static float getScaledDensity() {
        return SDActivityManager.getInstance().getLastActivity().getApplication().getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * sp转px
     *
     * @param sp
     * @return
     */
    public static int sp2px(float sp) {
        final float fontScale = getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int dp2px(float dp) {
        final float scale = getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param px
     * @return
     */
    public static int px2dp(float px) {
        final float scale = getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取标高
     *
     * @param originalWidth
     * @param originalHeight
     * @param scaleWidth
     * @return
     */
    public static int getScaleHeight(int originalWidth, int originalHeight, int scaleWidth) {
        int result = 0;
        if (originalWidth != 0) {
            result = originalHeight * scaleWidth / originalWidth;
        }
        return result;
    }

    /**
     * 获取标宽
     *
     * @param originalWidth
     * @param originalHeight
     * @param scaleHeight
     * @return
     */
    public static int getScaleWidth(int originalWidth, int originalHeight, int scaleHeight) {
        int result = 0;
        if (originalHeight != 0) {
            result = originalWidth * scaleHeight / originalHeight;
        }
        return result;
    }

    /**
     * 获取listview的总高度
     *
     * @param listView
     * @return
     */
    public static int getListViewTotalHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = getListViewHeightRange(listView, 0, listAdapter.getCount() - 1);
        return totalHeight;
    }

    /**
     * 获取listview的范围高度
     *
     * @param listView
     * @param start
     * @param end
     * @return
     */
    public static int getListViewHeightRange(ListView listView, int start, int end) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        if (start >= 0 && end >= start && end < listAdapter.getCount()) {
            for (int i = start; i <= end; i++) {
                View listItem = listAdapter.getView(i, null, listView);
                if (listItem != null) {
                    listItem.measure(0, 0);
                    int height = listItem.getMeasuredHeight();
                    int dividerHeight = listView.getDividerHeight() * (listAdapter.getCount() - 1);
                    totalHeight += (height + dividerHeight);
                }
            }
        }
        return totalHeight;
    }

    /**
     * 测量角度
     *
     * @param v
     */
    public static void measureView(View v) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
    }

    /**
     * 获取控件的高度
     *
     * @param view
     * @return
     */
    public static int getViewHeight(View view) {
        int height = 0;
        height = view.getHeight();
        if (height <= 0) {
            measureView(view);
            height = view.getMeasuredHeight();
        }
        return height;
    }

    /**
     * 获取控件包含控件内的高度
     *
     * @param view
     * @return
     */
    public static int getViewHeightAll(View view) {
        int height = getViewHeight(view);
        ViewGroup.MarginLayoutParams params = getViewMarginLayoutParams(view);
        if (params != null) {
            height = height + params.topMargin + params.bottomMargin;
        }
        return height;
    }

    /**
     * 获取控件的宽度
     *
     * @param view
     * @return
     */
    public static int getViewWidth(View view) {
        int width = 0;
        width = view.getWidth();
        if (width <= 0) {
            measureView(view);
            width = view.getMeasuredWidth();
        }
        return width;
    }

    /**
     * 获取控件包含控件内的宽度
     *
     * @param view
     * @return
     */
    public static int getViewWidthAll(View view) {
        int width = getViewWidth(view);
        ViewGroup.MarginLayoutParams params = getViewMarginLayoutParams(view);
        if (params != null) {
            width = width + params.leftMargin + params.rightMargin;
        }
        return width;
    }

    public static void toggleEmptyMsgByList(List<? extends Object> list, View emptyView) {
        if (emptyView != null) {
            if (list != null && list.size() > 0) {
                hide(emptyView);
            } else {
                show(emptyView);
            }
        }
    }

    public static void toggleViewByList(List<? extends Object> list, View view) {
        if (view != null) {
            if (list != null && list.size() > 0) {
                show(view);
            } else {
                hide(view);
            }
        }
    }

    public static <T> void updateAdapterByList(List<T> listOriginalData, List<T> listNewData, SDBaseAdapter<T> mAdapter, boolean isLoadMore) {
        updateAdapterByList(listOriginalData, listNewData, mAdapter, isLoadMore, null, "未找到更多数据");
    }

    public static <T> void updateAdapterByList(List<T> listOriginalData, List<T> listNewData, SDBaseAdapter<T> mAdapter, boolean isLoadMore,
                                               String noData, String noMoreData) {
        updateList(listOriginalData, listNewData, isLoadMore, noData, noMoreData);
        if (mAdapter != null && listOriginalData != null) {
            mAdapter.updateData(listOriginalData);
        }
    }

    public static <T> void updateAdapterByList(List<T> listOriginalData, List<T> listNewData, SDAdapter<T> mAdapter, boolean isLoadMore) {
        updateAdapterByList(listOriginalData, listNewData, mAdapter, isLoadMore, null, "未找到更多数据");
    }

    public static <T> void updateAdapterByList(List<T> listOriginalData, List<T> listNewData, SDAdapter<T> mAdapter, boolean isLoadMore,
                                               String noData, String noMoreData) {
        updateList(listOriginalData, listNewData, isLoadMore, noData, noMoreData);
        if (mAdapter != null && listOriginalData != null) {
            mAdapter.updateData(listOriginalData);
        }
    }

    public static <T> void updateList(List<T> listOriginalData, List<T> listNewData, boolean isLoadMore) {
        updateList(listOriginalData, listNewData, isLoadMore, null, "未找到更多数据");
    }

    public static <T> void updateList(List<T> listOriginalData, List<T> listNewData, boolean isLoadMore, String noData, String noMoreData) {
        if (listOriginalData != null) {
            if (listNewData != null && listNewData.size() > 0) // 有新数据
            {
                if (!isLoadMore) {
                    listOriginalData.clear();
                }
                listOriginalData.addAll(listNewData);
            } else {
                if (!isLoadMore) {
                    if (!TextUtils.isEmpty(noData)) {
                        Toast.makeText(SDActivityManager.getInstance().getLastActivity(), noData, Toast.LENGTH_SHORT);
                    }
                    listOriginalData.clear();
                } else {
                    if (!TextUtils.isEmpty(noMoreData)) {
                        Toast.makeText(SDActivityManager.getInstance().getLastActivity(), noMoreData, Toast.LENGTH_SHORT);
                    }
                }
            }
        }
    }

    public static View wrapperTitle(int contentLayoutId, int titleLayoutId) {
        LayoutInflater inflater = LayoutInflater.from(SDActivityManager.getInstance().getLastActivity().getApplication());
        View contentView = inflater.inflate(contentLayoutId, null);
        View titleView = inflater.inflate(titleLayoutId, null);
        return wrapperTitle(contentView, titleView);
    }

    public static View wrapperTitle(View contentView, View titleView) {
        LinearLayout linAll = new LinearLayout(SDActivityManager.getInstance().getLastActivity().getApplication());
        linAll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramsTitle = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams paramsContent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linAll.addView(titleView, paramsTitle);
        linAll.addView(contentView, paramsContent);
        return linAll;
    }

    public static View wrapperTitle(View contentView, int titleLayoutId) {
        LayoutInflater inflater = LayoutInflater.from(SDActivityManager.getInstance().getLastActivity().getApplication());
        View titleView = inflater.inflate(titleLayoutId, null);
        return wrapperTitle(contentView, titleView);
    }

    /**
     * 设置控件高度
     *
     * @param view
     * @param height
     * @return
     */
    public static boolean setViewHeight(View view, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            if (params.height != height) {
                params.height = height;
                view.setLayoutParams(params);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置控件宽度
     *
     * @param view
     * @param width
     * @return
     */
    public static boolean setViewWidth(View view, int width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            if (params.width != width) {
                params.width = width;
                view.setLayoutParams(params);
            }
            return true;
        }
        return false;
    }

    /**
     * 重置控件的高度
     *
     * @param view
     */
    public static void resetViewHeight(View view) {
        setViewHeight(view, getViewHeight(view));
    }

    /**
     * 重置控件的宽度
     *
     * @param view
     */
    public static void resetViewWidth(View view) {
        setViewWidth(view, getViewWidth(view));
    }

    /**
     * 影藏控件
     *
     * @param view
     */
    public static void hide(View view) {
        if (view != null && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 影藏控件invisible
     *
     * @param view
     */
    public static void invisible(View view) {
        if (view != null && view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示控件
     *
     * @param view
     */
    public static void show(View view) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 切换控件的影藏状态
     *
     * @param view
     * @return
     */
    public static boolean toggleGone(View view) {
        boolean result = false;
        if (view != null) {
            int visibility = view.getVisibility();
            if (visibility == View.VISIBLE) {
                view.setVisibility(View.GONE);
                result = false;
            } else if (visibility == View.GONE) {
                view.setVisibility(View.VISIBLE);
                result = true;
            }
        }
        return result;
    }

    /**
     * 切换控件的影藏状态Invisible
     *
     * @param view
     * @return
     */
    public static boolean toggleInvisible(View view) {
        boolean result = false;
        if (view != null) {
            int visibility = view.getVisibility();
            if (visibility == View.VISIBLE) {
                view.setVisibility(View.INVISIBLE);
                result = false;
            } else if (visibility == View.INVISIBLE) {
                view.setVisibility(View.VISIBLE);
                result = true;
            }
        }
        return result;
    }

    /**
     * 获取在其窗口中计算该视图的坐标
     *
     * @param view
     * @return
     */
    public static int[] getLocationInWindow(View view) {
        int[] location = null;
        if (view != null) {
            location = new int[2];
            view.getLocationInWindow(location);
        }
        return location;
    }

    /**
     * 获取在屏幕上计算该视图的坐标
     *
     * @param view
     * @return
     */
    public static int[] getLocationOnScreen(View view) {
        int[] location = null;
        if (view != null) {
            location = new int[2];
            view.getLocationOnScreen(location);
        }
        return location;
    }

    /**
     * 获取去掉状态栏后计算该视图的坐标
     *
     * @param view
     * @return
     */
    public static int[] getLocationOnScreenWithoutStatusBar(View view) {
        int[] location = getLocationOnScreen(view);
        if (location != null) {
            int statusBarHeight = getStatusBarHeight();
            location[1] -= statusBarHeight;
        }
        return location;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = SDActivityManager.getInstance().getLastActivity().getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = SDActivityManager.getInstance().getLastActivity().getApplication().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 动态设置字体SP格式的大小
     *
     * @param view
     * @param sizeSp 字体大小
     */
    public static void setTextSizeSp(TextView view, float sizeSp) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
    }

    public static void setImageViewGray(ImageView view) {
        if (view != null) {
            Drawable drawable = view.getDrawable();
            if (drawable != null) {
                Bitmap bmp = SDImageUtil.drawable2Bitmap(drawable);
                Bitmap bmpGray = SDImageUtil.getGrayBitmap(bmp);
                if (bmpGray != null) {
                    view.setImageBitmap(bmpGray);
                }
            }
        }
    }

    /**
     * 设置控件的背景灰度
     *
     * @param view
     */
    public static void setViewBackgroundGray(View view) {
        if (view != null) {
            Drawable drawable = view.getBackground();
            if (drawable != null) {
                Bitmap bmp = SDImageUtil.drawable2Bitmap(drawable);
                Bitmap bmpGray = SDImageUtil.getGrayBitmap(bmp);
                if (bmpGray != null) {
                    Drawable drawableGray = SDImageUtil.Bitmap2Drawable(bmpGray);
                    if (drawableGray != null) {
                        setBackgroundDrawable(view, drawableGray);
                    }
                }
            }
        }
    }

    /**
     * 设置Webview缩放控制可见性
     *
     * @param webview
     * @param visibility
     */
    public static void setWebviewZoomControlVisibility(View webview, int visibility) {
        try {
            Field field = WebView.class.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(webview);
            mZoomButtonsController.getZoomControls().setVisibility(visibility);
            field.set(webview, mZoomButtonsController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是完全可见的第一个Item
     *
     * @param absListView
     * @return
     */
    public static boolean isFirstItemTotallyVisible(AbsListView absListView) {
        final Adapter adapter = absListView.getAdapter();
        if (null == adapter || adapter.isEmpty()) {
            return true;
        } else {
            if (absListView.getFirstVisiblePosition() <= 1) {
                final View firstVisibleChild = absListView.getChildAt(0);
                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= 0;
                }
            }
        }
        return false;
    }

    /**
     * 是完全可见的最后一个Item
     *
     * @param absListView
     * @return
     */
    public static boolean isLastItemTotallyVisible(AbsListView absListView) {
        final Adapter adapter = absListView.getAdapter();
        if (null == adapter || adapter.isEmpty()) {
            return true;
        } else {
            final int lastItemPosition = absListView.getCount() - 1;
            final int lastVisiblePosition = absListView.getLastVisiblePosition();
            if (lastVisiblePosition >= lastItemPosition - 1) {
                final int childIndex = lastVisiblePosition - absListView.getFirstVisiblePosition();
                final View lastVisibleChild = absListView.getChildAt(childIndex);
                if (lastVisibleChild != null) {
                    return lastVisibleChild.getBottom() <= absListView.getBottom();
                }
            }
        }
        return false;
    }

    /**
     * 从父视图中删除视图
     *
     * @param child
     */
    public static void removeViewFromParent(View child) {
        if (child != null) {
            ViewParent parent = child.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) parent;
                vg.removeView(child);
            }
        }
    }

    /**
     * 创建视图的位图
     *
     * @param view
     * @return
     */
    public static Bitmap createViewBitmap(View view) {
        Bitmap bmp = null;
        if (view != null) {
            view.setDrawingCacheEnabled(true);
            Bitmap.createBitmap(view.getDrawingCache());
            view.destroyDrawingCache();
        }
        return bmp;
    }

    /**
     * 获取控件在窗口可见区域
     *
     * @param view
     * @return
     */
    public static Rect getWindowVisibleDisplayFrame(View view) {
        Rect rect = new Rect();
        if (view != null) {
            view.getWindowVisibleDisplayFrame(rect);
        }
        return rect;
    }

    /**
     * 获取控件在在屏幕上的X
     *
     * @param view
     * @return
     */
    public static int getViewXOnScreen(View view) {
        int[] location = new int[2];
        if (view != null) {
            view.getLocationOnScreen(location);
        }
        return location[0];
    }

    /**
     * 获取控件在在屏幕上的Y
     *
     * @param view
     * @return
     */
    public static int getViewYOnScreen(View view) {
        int[] location = new int[2];
        if (view != null) {
            view.getLocationOnScreen(location);
        }
        return location[1];
    }

    /**
     * 获取屏幕上的控件位置
     *
     * @param view
     * @return
     */
    public static int[] getViewLocationOnScreen(View view) {
        int[] location = new int[2];
        if (view != null) {
            view.getLocationOnScreen(location);
        }
        return location;
    }

    /**
     * 获取视图中心在屏幕上的X位置
     *
     * @param view
     * @return
     */
    public static int getViewCenterXOnScreen(View view) {
        int x = 0;
        if (view != null) {
            x = getViewXOnScreen(view);
            int width = getViewWidth(view);
            x = x + width / 2;
        }
        return x;
    }

    /**
     * 获取视图中心在屏幕上的Y位置
     *
     * @param view
     * @return
     */
    public static int getViewCenterYOnScreen(View view) {
        int y = 0;
        if (view != null) {
            y = getViewYOnScreen(view);
            int height = getViewHeight(view);
            y = y + height / 2;
        }
        return y;
    }

    /**
     * 获取控件在屏幕上的区域
     *
     * @param view
     * @return
     */
    public static Rect getViewRect(View view) {
        Rect r = new Rect();
        if (view != null && view.getVisibility() == View.VISIBLE) {
            int[] location = getViewLocationOnScreen(view);
            r.left = location[0];
            r.right = r.left + getViewWidth(view);
            r.top = location[1];
            r.bottom = r.top + getViewHeight(view);
        }
        return r;
    }

    /**
     * 判断点击的点是否在控件的区域内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isTouchView(View view, int x, int y) {
        boolean result = false;
        Rect r = getViewRect(view);
        if (r != null) {
            result = r.contains(x, y);
        }
        return result;
    }

    /**
     * 判断点击的点的Y轴是否在控件的区域内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isTouchViewY(View view, int x, int y) {
        boolean result = false;
        Rect r = getViewRect(view);
        if (r != null) {
            result = r.left < r.right && r.top < r.bottom && y >= r.top && y < r.bottom;
        }
        return result;
    }

    /**
     * 判断点击的点的X轴是否在控件的区域内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isTouchViewX(View view, int x, int y) {
        boolean result = false;
        Rect r = getViewRect(view);
        if (r != null) {
            result = r.left < r.right && r.top < r.bottom && x >= r.left && x < r.right;
        }
        return result;
    }

    /**
     * 判断点击时间的点是否在控件的区域内
     *
     * @param view
     * @param e
     * @return
     */
    public static boolean isTouchView(View view, MotionEvent e) {
        boolean result = false;
        if (e != null) {
            result = isTouchView(view, (int) e.getRawX(), (int) e.getRawY());
        }
        return result;
    }

    /**
     * 设置控件的MarginTop
     *
     * @param view
     * @param top
     */
    public static void setViewMarginTop(View view, int top) {
        ViewGroup.MarginLayoutParams p = getViewMarginLayoutParams(view);
        if (p != null) {
            p.topMargin = top;
            view.setLayoutParams(p);
        }
    }

    /**
     * 设置控件的MarginLeft
     *
     * @param view
     * @param left
     */
    public static void setViewMarginLeft(View view, int left) {
        ViewGroup.MarginLayoutParams p = getViewMarginLayoutParams(view);
        if (p != null) {
            p.leftMargin = left;
            view.setLayoutParams(p);
        }
    }

    /**
     * 设置控件的MarginBottom
     *
     * @param view
     * @param bottom
     */
    public static void setViewMarginBottom(View view, int bottom) {
        ViewGroup.MarginLayoutParams p = getViewMarginLayoutParams(view);
        if (p != null) {
            p.bottomMargin = bottom;
            view.setLayoutParams(p);
        }
    }

    /**
     * 设置控件的MarginRight
     *
     * @param view
     * @param right
     */
    public static void setViewMarginRight(View view, int right) {
        ViewGroup.MarginLayoutParams p = getViewMarginLayoutParams(view);
        if (p != null) {
            p.rightMargin = right;
            view.setLayoutParams(p);
        }
    }

    /**
     * 设置控件的Margin
     *
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams p = getViewMarginLayoutParams(view);
        if (p != null) {
            p.topMargin = top;
            p.leftMargin = left;
            p.bottomMargin = bottom;
            p.rightMargin = right;
            view.setLayoutParams(p);
        }
    }

    /**
     * 设置控件的Margins
     *
     * @param view
     * @param margins
     */
    public static void setViewMargins(View view, int margins) {
        setViewMargin(view, margins, margins, margins, margins);
    }

    /**
     * 获取控件的MarginLayoutParams
     *
     * @param view
     * @return
     */
    public static ViewGroup.MarginLayoutParams getViewMarginLayoutParams(View view) {
        ViewGroup.MarginLayoutParams result = null;
        if (view != null) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params != null && params instanceof ViewGroup.MarginLayoutParams) {
                result = (ViewGroup.MarginLayoutParams) params;
            }
        }
        return result;
    }

    /**
     * 设置控件的PaddingLeft
     *
     * @param view
     * @param left
     */
    public static void setViewPaddingLeft(View view, int left) {
        setViewPadding(view, left, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    /**
     * 设置控件的PaddingTop
     *
     * @param view
     * @param top
     */
    public static void setViewPaddingTop(View view, int top) {
        setViewPadding(view, view.getPaddingLeft(), top, view.getPaddingRight(), view.getPaddingBottom());
    }

    /**
     * 设置控件的PaddingRight
     *
     * @param view
     * @param right
     */
    public static void setViewPaddingRight(View view, int right) {
        setViewPadding(view, view.getPaddingLeft(), view.getPaddingTop(), right, view.getPaddingBottom());
    }

    /**
     * 设置控件的PaddingBottom
     *
     * @param view
     * @param bottom
     */
    public static void setViewPaddingBottom(View view, int bottom) {
        setViewPadding(view, view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), bottom);
    }

    /**
     * 设置控件的Padding
     *
     * @param view
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public static void setViewPadding(View view, int left, int top, int right, int bottom) {
        view.setPadding(left, top, right, bottom);
    }

    /**
     * 设置控件的Paddings
     *
     * @param view
     * @param paddings
     */
    public static void setViewPaddings(View view, int paddings) {
        setViewPadding(view, paddings, paddings, paddings, paddings);
    }

    /**
     * 显示PopupWindow并且设置marginBottom
     *
     * @param pop
     * @param view
     * @param marginBottom
     */
    public static void showPopTop(PopupWindow pop, View view, int marginBottom) {
        int[] location = getLocationOnScreen(view);

        int x = location[0] - SDViewUtil.getScreenWidth() / 2 + view.getWidth() / 2;
        int y = view.getHeight() + marginBottom;
        pop.showAtLocation(view, Gravity.BOTTOM, x, y);
    }

    /**
     * 显示PopupWindow并且设置marginRight
     *
     * @param pop
     * @param view
     * @param marginRight
     */
    public static void showPopLeft(PopupWindow pop, View view, int marginRight) {
        int[] location = getLocationOnScreen(view);
        pop.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - pop.getWidth() - marginRight, location[1]);
    }

    /**
     * 显示PopupWindow并且设置marginLeft
     *
     * @param pop
     * @param view
     * @param marginLeft
     */
    public static void showPopRight(PopupWindow pop, View view, int marginLeft) {
        int[] location = getLocationOnScreen(view);
        pop.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth() + marginLeft, location[1]);
    }

    /**
     * 更新图片控件的大小
     *
     * @param imageView
     * @param drawable
     */
    public static void updateImageViewSize(ImageView imageView, Drawable drawable) {
        if (drawable != null && imageView != null) {
            int width = getViewWidth(imageView);
            if (width > 0) {
                int newHeight = getScaleHeight(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), width);
                setViewHeight(imageView, newHeight);
            }
        }
    }

    /**
     * 设置控件的宽度WRAP_CONTENT
     *
     * @param view
     */
    public static void setViewWidthWrapContent(View view) {
        setViewWidth(view, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置控件的宽度MATCH_PARENT
     *
     * @param view
     */
    public static void setViewWidthMatchParent(View view) {
        setViewWidth(view, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置控件的高度WRAP_CONTENT
     *
     * @param view
     */
    public static void setViewHeightWrapContent(View view) {
        setViewHeight(view, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置控件的高度MATCH_PARENT
     *
     * @param view
     */
    public static void setViewHeightMatchParent(View view) {
        setViewHeight(view, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 设置控件宽的权重weight
     *
     * @param view
     * @param weight
     */
    public static void setViewWidthWeightContent(View view, float weight) {
        if (view != null) {
            ViewGroup.LayoutParams vgParams = view.getLayoutParams();
            if (vgParams instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vgParams;
                if (params != null) {
                    params.width = 0;
                    params.weight = weight;
                    view.setLayoutParams(params);
                }
            }
        }
    }

    /**
     * 设置控件高的权重weight
     *
     * @param view
     * @param weight
     */
    public static void setViewHeightWeightContent(View view, float weight) {
        if (view != null) {
            ViewGroup.LayoutParams vgParams = view.getLayoutParams();
            if (vgParams instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vgParams;
                if (params != null) {
                    params.height = 0;
                    params.weight = weight;
                    view.setLayoutParams(params);
                }
            }
        }
    }

    /**
     * 开始动画绘制
     *
     * @param drawable
     */
    public static void startAnimationDrawable(Drawable drawable) {
        if (drawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            if (!animationDrawable.isRunning()) {
                animationDrawable.start();
            }
        }
    }

    /**
     * 停止动画绘制
     *
     * @param drawable
     */
    public static void stopAnimationDrawable(Drawable drawable) {
        stopAnimationDrawable(drawable, 0);
    }

    /**
     * 停止动画在index的地方的绘制
     *
     * @param drawable
     */
    public static void stopAnimationDrawable(Drawable drawable, int stopIndex) {
        if (drawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            if (animationDrawable.isRunning()) {
                animationDrawable.stop();
                animationDrawable.selectDrawable(stopIndex);
            }
        }
    }

    /**
     * 滚动listview，当deltaY大于listview的高度的时候则该次调用只能滚动listview的高度
     *
     * @param deltaY   滚动的距离。正值：item向下移动，负值：item向上移动
     * @param listView
     */
    @TargetApi(19)
    public static void scrollListBy(int deltaY, AbsListView listView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            listView.scrollListBy(-deltaY);
        } else {
            try {
                Method method = AbsListView.class.getDeclaredMethod("trackMotionScroll", int.class, int.class);
                if (method != null) {
                    method.setAccessible(true);
                    method.invoke(listView, deltaY, deltaY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取listview下面的间距
     *
     * @param listView
     * @return
     */
    public static int getListViewBelowSpacing(ListView listView) {
        int belowSpacing = 0;

        ListAdapter adapter = listView.getAdapter();
        if (adapter != null) {
            int totalCount = adapter.getCount();
            if (totalCount > 0) {
                int lastVisiblePosition = listView.getLastVisiblePosition();
                int lastItemBottom = listView.getChildAt(listView.getChildCount() - 1).getBottom();
                int spaceBelowLastItem = lastItemBottom - listView.getHeight();
                int spaceBelowLeft = getListViewHeightRange(listView, lastVisiblePosition + 1, totalCount - 1);
                belowSpacing = spaceBelowLastItem + spaceBelowLeft;
            }
        }
        return belowSpacing;
    }

    /**
     * 设置半透明的状态
     *
     * @param activity
     * @param on
     */
    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity, boolean on) {
        if (activity != null) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * 重置视图
     *
     * @param view
     */
    public static void resetView(View view) {
        if (view != null) {
            view.setAlpha(1.0f);
            view.setRotation(0.0f);
            view.setRotationX(0.0f);
            view.setRotationY(0.0f);
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
        }
    }

    /**
     * 获取可得到的标识符
     *
     * @param name
     * @return
     */
    public static int getIdentifierDrawable(String name) {
        int id = 0;
        try {
            id = SDResourcesUtil.getResources().getIdentifier(name, "drawable", SDPackageUtil.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 测量文本
     *
     * @param textView
     * @param content
     * @return
     */
    public static float measureText(TextView textView, String content) {
        float width = 0;
        if (textView != null) {
            TextPaint textPaint = textView.getPaint();
            width = textPaint.measureText(content);
        }
        return width;
    }

    /**
     * 更换视图
     *
     * @param viewGroup
     * @param child
     * @return
     */
    public static boolean replaceView(View viewGroup, View child) {
        return replaceView(viewGroup, child, null);
    }

    /**
     * 更换视图
     *
     * @param viewGroup
     * @param child
     * @return
     */
    public static boolean replaceView(View viewGroup, View child, ViewGroup.LayoutParams params) {
        return addView(viewGroup, child, params, true);
    }

    /**
     * 添加视图
     *
     * @param viewGroup
     * @param child
     * @return
     */
    public static boolean addView(View viewGroup, View child) {
        return addView(viewGroup, child, null);
    }

    /**
     * 添加视图
     *
     * @param viewGroup
     * @param child
     * @param params
     * @return
     */
    public static boolean addView(View viewGroup, View child, ViewGroup.LayoutParams params) {
        return addView(viewGroup, child, params, false);
    }

    /**
     * 添加视图
     *
     * @param viewGroup
     * @param child
     * @param params
     * @param removeAllViews
     * @return
     */
    private static boolean addView(View viewGroup, View child, ViewGroup.LayoutParams params, boolean removeAllViews) {
        if (viewGroup != null && child != null) {
            if (viewGroup instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) viewGroup;
                if (removeAllViews) {
                    vg.removeAllViews();
                }
                removeViewFromParent(child);
                if (params != null) {
                    vg.addView(child, params);
                } else {
                    vg.addView(child);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 延迟获得view的大小
     *
     * @param view      目标view
     * @param invisible true:需要先隐藏后显示
     * @param delay     延迟多少毫秒触发
     * @param listener  回调监听
     */
    public static void getViewSize(final View view, final boolean invisible, long delay, final SDViewSizeListener listener) {
        if (view != null) {
            int width = SDViewUtil.getViewWidth(view);
            int height = SDViewUtil.getViewHeight(view);
            if (width <= 0 && height <= 0) {
                // true:需要先隐藏后显示
                if (invisible) {
                    SDViewUtil.invisible(view);
                }
                // 需要延迟
                if (delay < 0) {
                    delay = 100;
                }
                SDHandlerUtil.runOnUiThreadDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (invisible) {
                            SDViewUtil.show(view);
                        }
                        if (listener != null) {
                            listener.onResult(view);
                        }
                    }
                }, delay);
            } else {
                if (listener != null) {
                    listener.onResult(view);
                }
            }
        }
    }

    static long lastClick = 0;
    /**
     * 防止快速点击
     *
     * @return
     */
    public static boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 500) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
    public interface SDViewSizeListener {
        void onResult(View view);
    }
}
