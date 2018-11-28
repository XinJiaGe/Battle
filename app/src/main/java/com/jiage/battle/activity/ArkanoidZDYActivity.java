package com.jiage.battle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jiage.battle.R;
import com.jiage.battle.adapter.AddArkanoidItemAdapter;
import com.jiage.battle.dao.JsonDbModelDao;
import com.jiage.battle.dialog.SDDialogConfirm;
import com.jiage.battle.dialog.SDDialogCustom;
import com.jiage.battle.dialog.SDDialogMenu;
import com.jiage.battle.entity.CheckpointEntity;
import com.jiage.battle.entity.CheckpointItemEntity;

import org.xutils.view.annotation.ViewInject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/8/21
 * 说明：自定义关卡
 */

public class ArkanoidZDYActivity extends BaseActivit implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    @ViewInject(R.id.act_customize_gridview)
    private GridView gridview;


    private AddArkanoidItemAdapter adapter;
    private List<CheckpointItemEntity> listModel = new ArrayList<>();


    @Override
    public int bindLayout() {
        return R.layout.act_customize;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("所有关卡");
        mTitle.setRightText("添加");
        gridview.setOnItemLongClickListener(this);
        gridview.setOnItemClickListener(this);
    }

    @Override
    public void doView() {
        listModel.clear();
        CheckpointEntity entity = JsonDbModelDao.getInstance().query(CheckpointEntity.class);
        if(entity!=null){
            listModel.addAll(entity.getItemEntityList());
            adapter = new AddArkanoidItemAdapter(listModel,this);
            gridview.setAdapter(adapter);
        }
    }

    @Override
    public void onTitleRightListener() {
        startActivity(new Intent(this,AddArkanoidActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            CheckpointEntity entity = JsonDbModelDao.getInstance().query(CheckpointEntity.class);
            if(entity!=null){
                listModel.clear();
                listModel.addAll(entity.getItemEntityList());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        SDDialogConfirm confirm = new SDDialogConfirm(mActivity);
        confirm.setTextContent("确定要删除该关卡吗？");
        confirm.setmListener(new SDDialogCustom.SDDialogCustomListener() {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog) {

            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog) {
                CheckpointEntity entity = JsonDbModelDao.getInstance().query(CheckpointEntity.class);
                if(entity!=null){
                    entity.getItemEntityList().remove(position);
                    JsonDbModelDao.getInstance().insertOrUpdate(entity);
                    doView();
                }
            }

            @Override
            public void onDismiss(SDDialogCustom dialog) {

            }
        });
        confirm.show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt(ArkanoidGameActivity.CHECKPOINTNUMBER,(position+1));
        bundle.putSerializable(ArkanoidGameActivity.CHECKPOINT,listModel.get(position));
        startActivity(ArkanoidGameActivity.class,bundle);
    }
}
