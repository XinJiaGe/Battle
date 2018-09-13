package com.jiage.battle.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.entity.CustomizeEntity;
import com.jiage.battle.util.ViewHolder;

import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/8/21
 * 说明：
 */

public class AddArkanoidAdapter extends BaseAdapter<CustomizeEntity>{
    public AddArkanoidAdapter(List<CustomizeEntity> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_customize;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, CustomizeEntity model) {
        TextView text = ViewHolder.get(R.id.item_customize_text,convertView);
        text.setText(model.getText());
        if(model.getCode()!=0) {
            text.setTextColor(model.getColor());
        }else{
            text.setTextColor(Color.BLACK);
        }
    }
}
