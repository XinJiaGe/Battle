package com.jiage.battle.util;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.jiage.battle.R;

/**
 * 作者：李忻佳.
 * 时间：2017/3/29.
 * 说明：判断edittext是否都不为空
 */

public class EditTextAllNullUtil {
    private int DECIMAL_DIGITS = 10;//小数的位数
    private EditText[] mEditTexts;
    private int[] limitDecimalPointDigit;
    private boolean[] booleans;
    private Button mSubmitBt;
    private boolean mIsBg = false;
    private int mFalseBtBg = 0;
    private int mTrueBtBg = 0;

    /**
     * 限制小数点后几位
     * 和addEditText一一对应
     *
     * @param limitDecimalPointDigit -1  不限制
     *                               0   不保留小数点
     *                               1   保留一位小数点
     * @return
     */
    public EditTextAllNullUtil limitDecimalPointDigit(int[] limitDecimalPointDigit) {
        this.limitDecimalPointDigit = limitDecimalPointDigit;
        return this;
    }

    /**
     * 添加EditTextView
     *
     * @param edit
     * @return
     */
    public EditTextAllNullUtil addEditText(EditText[] edit) {
        this.mEditTexts = edit;
        booleans = new boolean[mEditTexts.length];
        for (int i = 0; i < mEditTexts.length; i++) {
            if (mEditTexts[i].getText().toString().trim().length() > 0) {
                booleans[i] = true;
            } else {
                booleans[i] = false;
            }
            if (limitDecimalPointDigit != null) {
                DECIMAL_DIGITS = limitDecimalPointDigit[i];
                if (DECIMAL_DIGITS >= 0) {
                    //手动设置其他位数，例如3
                    mEditTexts[i].setFilters(new InputFilter[]{new MoneyValueFilterUtil().setDigits(DECIMAL_DIGITS)});
                }
            }
            final int finalI = i;
            mEditTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().trim().length() > 0) {
                        booleans[finalI] = true;
                    } else {
                        booleans[finalI] = false;
                    }
                    inspectBoolean();
                }
            });
        }
        return this;
    }

    /**
     * 是否全部为空
     */
    private void inspectBoolean() {
        boolean istrue = true;
        for (boolean aBoolean : booleans) {
            if (!aBoolean) {
                istrue = false;
                break;
            }
        }
        if (mSubmitBt != null && mFalseBtBg != 0 && mTrueBtBg != 0) {
            if (istrue) {
                mSubmitBt.setBackgroundResource(mTrueBtBg);
                mSubmitBt.setEnabled(true);
            } else {
                mSubmitBt.setBackgroundResource(mFalseBtBg);
                mSubmitBt.setEnabled(false);
            }
        }
        if (mSubmitBt != null && mIsBg) {
            if (istrue) {
                mSubmitBt.setBackgroundResource(R.drawable.layer_green_corner_item_single);
                mSubmitBt.setEnabled(true);
            } else {
                mSubmitBt.setBackgroundResource(R.drawable.layer_darkgray_corner_item_single);
                mSubmitBt.setEnabled(false);
            }
        }
    }

    /**
     * 需要改变的按钮
     *
     * @param button
     * @return
     */
    public EditTextAllNullUtil setSubmitBt(Button button) {
        this.mSubmitBt = button;
        return this;
    }
    /**
     * 需要改变的按钮
     *
     * @param button
     * @return
     */
    public EditTextAllNullUtil setSubmitBt(Button button,boolean isBg) {
        this.mSubmitBt = button;
        this.mIsBg = isBg;
        return this;
    }

    /**
     * 按钮false是的背景
     *
     * @param bg
     * @return
     */
    public EditTextAllNullUtil setFalseBtBg(int bg) {
        this.mFalseBtBg = bg;
        return this;
    }

    /**
     * 按钮true是的背景
     *
     * @param bg
     * @return
     */
    public EditTextAllNullUtil setTrueBtBg(int bg) {
        this.mTrueBtBg = bg;
        return this;
    }

    public void start() {
        inspectBoolean();
    }
}
