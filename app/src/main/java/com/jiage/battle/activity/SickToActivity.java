package com.jiage.battle.activity;

import android.content.pm.ActivityInfo;
import android.view.View;

import com.jiage.battle.R;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.dialog.SDDialogCustom;
import com.jiage.battle.surface.sickto.SicktoSurfaceView;
import com.jiage.battle.surface.sickto.Soldier;

import org.xutils.view.annotation.ViewInject;

/**
 * 作者：李忻佳
 * 日期：2018/12/26
 * 说明：坚守战
 */

public class SickToActivity extends BaseActivit {
    @ViewInject(R.id.act_sickto_surface)
    private SicktoSurfaceView surface;

    @Override
    public void initBar() {
        setNotTitle(true);
        setNotStatusBar(true);
    }
    @Override
    public int bindLayout() {
        return R.layout.act_sickto;
    }

    @Override
    public void initView(View view) {
        final SDDialogConfirm confirm = new SDDialogConfirm(mActivity);
        confirm.setTextConfirm("升级");
        surface.setmListener(new SicktoSurfaceView.onMListener() {
            @Override
            public void showDialog(final Soldier soldier) {
                confirm.setTextContent(soldier.getGrade() == Soldier.Grade.ONE?"升级(-500)":"升级(-1000)");
                confirm.setmListener(new SDDialogCustom.SDDialogCustomListener() {
                    @Override
                    public void onClickCancel(View v, SDDialogCustom dialog) {

                    }

                    @Override
                    public void onClickConfirm(View v, SDDialogCustom dialog) {
                        surface.upgrade(soldier);
                    }

                    @Override
                    public void onDismiss(SDDialogCustom dialog) {

                    }
                });
                if(!confirm.isShowing())
                    confirm.show();
            }
        });
    }

    public void addSoldier(View view) {
        surface.addSoldier();
    }
}
