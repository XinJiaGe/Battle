package com.jiage.battle.view;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.util.SDViewUtil;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.ScreenUtils;

/**
 * 作者：李忻佳
 * 时间：2017/11/26
 * 说明：TitleView
 */

public class TitleView extends AutoLinearLayout implements View.OnClickListener {
    private LinearLayout back;
    private ImageView backIv;
    private LinearLayout center;
    private LinearLayout right;
    private Context mContext;
    private LinearLayout ll;
    private onTitleClickListener mOnTitleClickListener;
    private View titleView;

    public TitleView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * 初始
     *
     * @param context
     */
    private void initView(Context context) {
        this.mContext = context;
        titleView = LayoutInflater.from(context).inflate(R.layout.view_title, null);
        ll = titleView.findViewById(R.id.view_title_ll);
        back = titleView.findViewById(R.id.view_title_back);
        backIv = titleView.findViewById(R.id.view_title_backIv);
        center = titleView.findViewById(R.id.view_title_center);
        right = titleView.findViewById(R.id.view_title_right);
        addView(titleView);

        back.setOnClickListener(this);
    }

    public void setSetStatusBar() {
        ll.setPadding(0, ScreenUtils.getStatusBarHeight(mContext), 0, 0);
    }

    /**
     * 设置title 中间的标题 text 靠左边
     *
     * @param text
     */
    public void setCenterTextLift(String text) {
        setCenterText(text,Color.WHITE,Gravity.LEFT);
    }
    /**
     * 设置title 中间的标题 text
     *
     * @param text
     */
    public void setCenterText(String text) {
        setCenterText(text,Color.WHITE,Gravity.CENTER);
    }
    /**
     * 设置title 中间的标题 text
     *
     * @param text
     */
    public void setCenterText(String text, int textColor) {
        setCenterText(text, textColor,Gravity.CENTER);
    }
    /**
     * 设置title 中间的标题 text
     *
     * @param text
     */
    public void setCenterText(String text, int textColor, int gravity) {
        View title = SDViewUtil.getRId(mContext, R.layout.view_title_center);
        TextView textView = title.findViewById(R.id.view_title_center_tv);
        textView.setText(text);
        textView.setTextColor(textColor);
        setCenterView(title,gravity);
    }

    /**
     * 在右边放一个view
     *
     * @param view
     */
    public void setRightView(View view) {
        right.setOnClickListener(this);
        right.removeAllViews();
        right.addView(view);
    }
    /**
     * 在左边边放一个view
     *
     * @param view
     */
    public void setLeftView(View view) {
        back.setOnClickListener(this);
        back.removeAllViews();
        back.addView(view);
    }

    /**
     * 设置右边text
     *
     * @param text
     */
    public View setRightText(String text) {
        return setRightText(text,Color.WHITE);
    }
    /**
     * 设置左边边text
     *
     * @param text
     */
    public void setBackText(String text) {
        setBackText(text,Color.WHITE);
    }
    /**
     * 设置右边text
     *
     * @param text
     */
    public View setRightText(String text, int textColor) {
        View title = SDViewUtil.getRId(mContext, R.layout.view_title_center);
        TextView lefttextView = title.findViewById(R.id.view_title_center_tv);
        TextView righttextView = title.findViewById(R.id.view_title_right_tv);
        lefttextView.setVisibility(View.GONE);
        righttextView.setVisibility(View.VISIBLE);
        righttextView.setText(text);
        righttextView.setTextColor(textColor);
        setRightView(title);
        return title;
    }
    /**
     * 设置左边text
     *
     * @param text
     */
    public void setBackText(String text,int textColor) {
        View title = SDViewUtil.getRId(mContext, R.layout.view_title_center);
        TextView lefttextView = title.findViewById(R.id.view_title_center_tv);
        TextView righttextView = title.findViewById(R.id.view_title_right_tv);
        lefttextView.setVisibility(View.GONE);
        righttextView.setVisibility(View.VISIBLE);
        righttextView.setText(text);
        righttextView.setTextColor(textColor);
        setLeftView(title);
    }
    /**
     * 设置右边图片
     *
     * @param id
     */
    public void setRightImage(int id) {
        View title = SDViewUtil.getRId(mContext, R.layout.view_title_right_imag);
        ImageView iamge = title.findViewById(R.id.view_title_right_imag);
        iamge.setBackgroundResource(id);
        setRightView(title);
    }

    /**
     * 设置title 中间的Veiw
     *
     * @param view
     * @param gravity
     */
    public void setCenterView(View view, int gravity) {
        center.setOnClickListener(this);
        center.removeAllViews();
        center.setGravity(gravity);
        int marginSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 120, getResources().getDisplayMetrics());
        int paddingSize = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics());
        if(gravity == Gravity.LEFT){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) center.getLayoutParams();
            params.leftMargin = marginSize;
            center.setLayoutParams(params);
            center.setPadding(paddingSize,paddingSize,paddingSize,paddingSize);
        }else{
            center.setPadding(paddingSize,paddingSize,paddingSize,paddingSize);
        }
        center.addView(view);
    }

    /**
     * 设置title背景颜色
     * @param id
     */
    public void setTitleBgColor(int id){
        ll.setBackgroundColor(id);
        back.setBackgroundColor(id);
        right.setBackgroundColor(id);
    }

    /**
     * 设置返回键图标
     * @param id
     */
    public void setBackIv(int id){
        backIv.setBackgroundResource(id);
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

    public void setmListener(onTitleClickListener clickListener) {
        this.mOnTitleClickListener = clickListener;
    }

    @Override
    public void onClick(View view) {
        if (mOnTitleClickListener != null) {
            if (view == back) {
                mOnTitleClickListener.onTitleBackListener();
            }
            if (view == center) {
                mOnTitleClickListener.onTitleCenterListener();
            }
            if (view == right) {
                mOnTitleClickListener.onTitleRightListener();
            }
        }
    }

    public interface onTitleClickListener {
        public void onTitleBackListener();

        public void onTitleCenterListener();

        public void onTitleRightListener();
    }

    public LinearLayout getBack() {
        return back;
    }

    public void removeBack(){
        back.removeAllViews();
        back.setEnabled(false);
    }
}
