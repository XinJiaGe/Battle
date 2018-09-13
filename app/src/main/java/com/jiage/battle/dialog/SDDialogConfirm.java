package com.jiage.battle.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.util.SDViewUtil;


/**
 * 带标题，内容，确定按钮和取消按钮的窗口
 *
 * @author js02
 */
public class SDDialogConfirm extends SDDialogCustom {

    public TextView mTvContent;
    private ScrollView sv_content;

    public SDDialogConfirm() {
        super();
    }

    public SDDialogConfirm(Activity activity) {
        super(activity);
    }

    @Override
    protected void init() {
        super.init();
        addTextView();
    }

    public SDDialogConfirm setTextContent(String text) {
        if (TextUtils.isEmpty(text)) {
            sv_content.setVisibility(View.GONE);
        } else {
            mTvContent.setVisibility(View.VISIBLE);
            mTvContent.setText(text);
        }
        return this;
    }

    private void addTextView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null);
        mTvContent = (TextView) view.findViewById(R.id.dialog_confirm_tv_content);
        sv_content = (ScrollView) view.findViewById(R.id.dialog_confirm_sv_content);
        LinearLayout.LayoutParams params = SDViewUtil.getLayoutParamsLinearLayoutWW();
        setCustomView(view, params);
    }

    /**
     * 设置内容显示位置
     * @param gravity
     */
    public void setContentGravity(int gravity){
        if(mTvContent!=null){
            mTvContent.setGravity(gravity);
        }
    }
}
