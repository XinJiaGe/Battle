package com.jiage.battle.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiage.battle.R;
import com.jiage.battle.drawable.SDDrawableManager;
import com.jiage.battle.util.SDViewBinder;
import com.jiage.battle.util.SDViewUtil;

import java.util.List;

public class SDSimpleTextAdapter<T> extends BaseAdapter<T> {
    private SDDrawableManager mdDrawableManager = new SDDrawableManager();
    private SheetItemColor[] color;

    public SDSimpleTextAdapter(List<T> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_simple_text;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, T model) {
        SDViewUtil.setBackgroundDrawable(convertView, mdDrawableManager.getSelectorWhiteGray(false));
        TextView tvName = get(R.id.item_simple_text_tv_name, convertView);
        if (color != null) {
            tvName.setTextColor(Color.parseColor(color[position].getName()));
        }
        SDViewBinder.setTextView(tvName, model.toString());
    }

    public void setTextColor(SheetItemColor[] color) {
        this.color = color;
    }

    public enum SheetItemColor {
        Blue("#ff000000"), Red("#FD4A2E");

        private String name;

        SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
