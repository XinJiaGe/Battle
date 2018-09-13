package com.jiage.battle.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.util.SDViewUtil;

/**
 * 作者：李忻佳
 * 日期：2017/12/14/014.
 * 说明：自定义EditText
 */

public class EditTextImageView extends RelativeLayout {
    private TextView hind;
    private EditText edText;
    private TextWatcher mTextWatcher;

    public EditTextImageView(Context context) {
        super(context);
        init(context);
    }

    public EditTextImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditTextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = SDViewUtil.getRId(context, R.layout.view_edittext_imageview);
        final LinearLayout ll = view.findViewById(R.id.view_edittext_imageview_ll);
        hind = view.findViewById(R.id.view_edittext_imageview_hind);
        edText = view.findViewById(R.id.view_edittext_imageview_edText);

        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mTextWatcher!=null){
                    mTextWatcher.beforeTextChanged(charSequence,i,i1,i2);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mTextWatcher!=null){
                    mTextWatcher.onTextChanged(charSequence,i,i1,i2);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ed = editable.toString();
                if(ed.length()>0){
                    ll.setVisibility(View.GONE);
                }else{
                    ll.setVisibility(View.VISIBLE);
                }
                if(mTextWatcher!=null){
                    mTextWatcher.afterTextChanged(editable);
                }
            }
        });

        addView(view);
    }
    public void setMyTextWatcher(TextWatcher textWatcher){
        this.mTextWatcher = textWatcher;
    }
    public EditTextImageView setHind(String hind) {
        if(this.hind!=null){
            this.hind.setText(hind);
        }
        return this;
    }

    public EditText getEdText() {
        return edText;
    }
}
