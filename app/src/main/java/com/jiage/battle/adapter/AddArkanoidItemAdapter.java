package com.jiage.battle.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.activity.ArkanoidGameActivity;
import com.jiage.battle.dao.JsonDbModelDao;
import com.jiage.battle.dialog.SDDialogMenu;
import com.jiage.battle.entity.CheckpointEntity;
import com.jiage.battle.entity.CheckpointItemEntity;
import com.jiage.battle.util.ViewHolder;
import com.jiage.battle.view.CheckpointView;

import java.net.URISyntaxException;
import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/8/24
 * 说明：
 */

public class AddArkanoidItemAdapter extends BaseAdapter<CheckpointItemEntity> {
    public AddArkanoidItemAdapter(List<CheckpointItemEntity> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.act_customize_item;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final CheckpointItemEntity model) {
        TextView text = ViewHolder.get(R.id.act_customize_item_text, convertView);
        CheckpointView view = ViewHolder.get(R.id.act_customize_view, convertView);

        text.setText("第"+(position+1)+"关");
        view.setData(model);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ArkanoidGameActivity.CHECKPOINTNUMBER,(position+1));
                bundle.putSerializable(ArkanoidGameActivity.CHECKPOINT,model);
                startActivity(ArkanoidGameActivity.class,bundle);
            }
        });
//        convertView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                showToast("position:"+position);
//                removeItem(position);
//                CheckpointEntity entity = JsonDbModelDao.getInstance().query(CheckpointEntity.class);
//                entity.getItemEntityList().remove(position);
//                return true;
//            }
//        });
    }
}
