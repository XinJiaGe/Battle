package com.jiage.battle.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.dialog.SDDialogEdit;
import com.jiage.battle.sharedPreference.SharedPreference;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/9/12
 * 说明：打砖块设置
 */

public class ArkanoidSettingActivity extends BaseActivit {
    @ViewInject(R.id.act_arkanoid_setting_bulletSound)
    private CheckBox bulletSound;
    @ViewInject(R.id.act_arkanoid_setting_maxbullet)
    private LinearLayout maxbullet;
    @ViewInject(R.id.act_arkanoid_setting_fnctions2ll)
    private LinearLayout fnctions2ll;
    @ViewInject(R.id.act_arkanoid_setting_fnctions3ll)
    private LinearLayout fnctions3ll;
    @ViewInject(R.id.act_arkanoid_setting_fnctions4ll)
    private LinearLayout fnctions4ll;
    @ViewInject(R.id.act_arkanoid_setting_fnctions2Tv)
    private TextView fnctions2Tv;
    @ViewInject(R.id.act_arkanoid_setting_fnctions3Tv)
    private TextView fnctions3Tv;
    @ViewInject(R.id.act_arkanoid_setting_fnctions4Tv)
    private TextView fnctions4Tv;
    @ViewInject(R.id.act_arkanoid_setting_maxbulletTv)
    private TextView maxbulletTv;

    @Override
    public int bindLayout() {
        return R.layout.act_arkanoid_setting;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("设置");
        maxbullet.setOnClickListener(this);
        fnctions2ll.setOnClickListener(this);
        fnctions3ll.setOnClickListener(this);
        fnctions4ll.setOnClickListener(this);
        bulletSound.setChecked(SharedPreference.getSharedPreference().isArkanoidBulletSound());
        maxbulletTv.setText(SharedPreference.getSharedPreference().getArkanoidMaxBullet() + "");
        fnctions2Tv.setText(SharedPreference.getSharedPreference().getArkanoidFunctions2()+"");
        fnctions3Tv.setText(SharedPreference.getSharedPreference().getArkanoidFunctions3()+"");
        fnctions4Tv.setText(SharedPreference.getSharedPreference().getArkanoidFunctions4()+"");
    }

    @Override
    public void doView() {
        bulletSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreference.getSharedPreference().setArkanoidBulletSound(isChecked);
            }
        });
    }

    @Override
    public void widgetClick(View view) {
        if(view == fnctions2ll){
            SDDialogEdit edit = new SDDialogEdit(mActivity);
            edit.setTextTitle("设置");
            edit.setContentHint("输入正整数1-100");
            edit.setmListener(new SDDialogEdit.YYKDialogInputListener() {
                @Override
                public void onClickCancel(View v, SDDialogEdit dialog) {

                }

                @Override
                public void onClickConfirm(View v, EditText content, SDDialogEdit dialog) {
                    try {
                        double dou = Double.parseDouble(content.getText().toString());
                        SharedPreference.getSharedPreference().setArkanoidFunctions2((int) dou);
                        fnctions2Tv.setText(SharedPreference.getSharedPreference().getArkanoidFunctions2() + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        showToast("请输入正确的整数");
                    }
                }

                @Override
                public void onDismiss(SDDialogEdit dialog) {

                }
            });
            edit.show();
        }
        if(view == fnctions3ll){
            SDDialogEdit edit = new SDDialogEdit(mActivity);
            edit.setTextTitle("设置");
            edit.setContentHint("输入正整数1-100");
            edit.setmListener(new SDDialogEdit.YYKDialogInputListener() {
                @Override
                public void onClickCancel(View v, SDDialogEdit dialog) {

                }

                @Override
                public void onClickConfirm(View v, EditText content, SDDialogEdit dialog) {
                    try {
                        double dou = Double.parseDouble(content.getText().toString());
                        SharedPreference.getSharedPreference().setArkanoidFunctions3((int) dou);
                        fnctions3Tv.setText(SharedPreference.getSharedPreference().getArkanoidFunctions3() + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        showToast("请输入正确的整数");
                    }
                }

                @Override
                public void onDismiss(SDDialogEdit dialog) {

                }
            });
            edit.show();
        }
        if(view == fnctions4ll){
            SDDialogEdit edit = new SDDialogEdit(mActivity);
            edit.setTextTitle("设置");
            edit.setContentHint("输入正整数1-100");
            edit.setmListener(new SDDialogEdit.YYKDialogInputListener() {
                @Override
                public void onClickCancel(View v, SDDialogEdit dialog) {

                }

                @Override
                public void onClickConfirm(View v, EditText content, SDDialogEdit dialog) {
                    try {
                        double dou = Double.parseDouble(content.getText().toString());
                        SharedPreference.getSharedPreference().setArkanoidFunctions4((int) dou);
                        fnctions4Tv.setText(SharedPreference.getSharedPreference().getArkanoidFunctions4() + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        showToast("请输入正确的整数");
                    }
                }

                @Override
                public void onDismiss(SDDialogEdit dialog) {

                }
            });
            edit.show();
        }
        if (view == maxbullet) {
            SDDialogEdit edit = new SDDialogEdit(mActivity);
            edit.setTextTitle("设置");
            edit.setContentHint("输入正整数");
            edit.setmListener(new SDDialogEdit.YYKDialogInputListener() {
                @Override
                public void onClickCancel(View v, SDDialogEdit dialog) {

                }

                @Override
                public void onClickConfirm(View v, EditText content, SDDialogEdit dialog) {
                    try {
                        double dou = Double.parseDouble(content.getText().toString());
                        SharedPreference.getSharedPreference().setArkanoidMaxBullet((int) dou);
                        maxbulletTv.setText(SharedPreference.getSharedPreference().getArkanoidMaxBullet() + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        showToast("请输入正确的整数");
                    }
                }

                @Override
                public void onDismiss(SDDialogEdit dialog) {

                }
            });
            edit.show();
        }
    }
}
