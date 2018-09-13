package com.jiage.battle.entity;

import android.widget.EditText;

/**
 * 作者：李忻佳
 * 时间：2018/5/11/011.
 * 说明：
 */

public class DialogPwdEntity {
    private EditText editText;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}
