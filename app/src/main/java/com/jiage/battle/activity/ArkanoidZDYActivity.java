package com.jiage.battle.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jiage.battle.R;
import com.jiage.battle.adapter.AddArkanoidItemAdapter;
import com.jiage.battle.dao.JsonDbModelDao;
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

public class ArkanoidZDYActivity extends BaseActivit{
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
    }

    @Override
    public void doView() {
        adapter = new AddArkanoidItemAdapter(listModel,this);
        gridview.setAdapter(adapter);
        CheckpointEntity entity = JsonDbModelDao.getInstance().query(CheckpointEntity.class);
        if(entity!=null){
            listModel.addAll(entity.getItemEntityList());
            adapter.notifyDataSetChanged();
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

//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//        showToast("ddddddd");
//        SDDialogMenu menu = new SDDialogMenu(mActivity);
//        menu.setItems(new String[]{"删除"});
//        menu.setmListener(new SDDialogMenu.SDDialogMenuListener() {
//            @Override
//            public void onCancelClick(View v, SDDialogMenu dialog) {
//
//            }
//
//            @Override
//            public void onDismiss(SDDialogMenu dialog) {
//
//            }
//
//            @Override
//            public void onItemClick(View v, int index, SDDialogMenu dialog) throws URISyntaxException {
//                listModel.remove(position);
//                adapter.notifyDataSetChanged();
//                CheckpointEntity entity = JsonDbModelDao.getInstance().query(CheckpointEntity.class);
//                entity.getItemEntityList().remove(position);
//            }
//        });
//        menu.show();
//        return true;
//    }
}
