package com.jiage.battle.dialog;

import android.app.Activity;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jiage.battle.R;
import com.jiage.battle.util.SDViewUtil;

/**
 * 带标题，输入框，确定按钮和取消按钮的窗口
 *
 * @author js02
 */
public class SDDialogEdit extends SDDialogCustom {

    private EditText mEtContent;

    private YYKDialogInputListener mListener;

    public YYKDialogInputListener getmListener() {
        return mListener;
    }

    public SDDialogEdit setmListener(YYKDialogInputListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public SDDialogEdit() {
        super();
    }

    public SDDialogEdit(Activity activity) {
        super(activity);
    }

    @Override
    protected void init() {
        super.init();
        setmListener(new SDDialogCustomListener() {

            @Override
            public void onDismiss(SDDialogCustom dialog) {
                if (mListener != null) {
                    mListener.onDismiss(SDDialogEdit.this);
                }
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog) {
                if (mListener != null) {
                    mListener.onClickConfirm(v, mEtContent, SDDialogEdit.this);
                }
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog) {
                if (mListener != null) {
                    mListener.onClickCancel(v, SDDialogEdit.this);
                }
            }
        });
        addEdittext();
    }

    public SDDialogEdit setTextContent(String text) {
        if (TextUtils.isEmpty(text)) {
            mEtContent.setText("");
        } else {
            mEtContent.setText(text);
        }
        return this;
    }
    public SDDialogEdit setTextContent(String text, boolean isLast) {
        if (TextUtils.isEmpty(text)) {
            mEtContent.setText("");
        } else {
            mEtContent.setText(text);
        }
        if(isLast){
            Selection.setSelection(mEtContent.getText(), mEtContent.getText().length());
        }
        return this;
    }

    /**
     * 设置输入框上的提示信息
     *
     * @param prompt
     */
    public void setPrompt(String prompt) {
        setPromptC(prompt);
    }

    /**
     * 设置输入框中的提示
     *
     * @param hint
     */
    public void setContentHint(String hint) {
        mEtContent.setHint(hint);
    }

    private void addEdittext() {
        View view = SDViewUtil.inflate(R.layout.dialog_input, null);

        mEtContent = (EditText) view.findViewById(R.id.dialog_input_et_content);
        view.setBackgroundDrawable(mDrawableManager.getLayerWhiteStrokeItemSingle(true));

        LinearLayout.LayoutParams params = SDViewUtil.getLayoutParamsLinearLayoutMW();
        int margin = SDViewUtil.dp2px(10);
        params.bottomMargin = margin;
        params.leftMargin = margin;
        params.rightMargin = margin;
        params.topMargin = margin;

        setCustomView(view, params);
    }

    @Override
    public void show() {
        super.show();
        SDViewUtil.showInputMethod(mEtContent, 200);
    }

    @Override
    public void dismiss() {
        SDViewUtil.hideInputMethod(mEtContent);
        super.dismiss();
    }

    public interface YYKDialogInputListener {
        public void onClickCancel(View v, SDDialogEdit dialog);

        public void onClickConfirm(View v, EditText content, SDDialogEdit dialog);

        public void onDismiss(SDDialogEdit dialog);
    }

}
