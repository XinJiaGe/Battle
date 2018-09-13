package com.jiage.battle.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

public class ViewpagerAdapter extends PagerAdapter {
    List<View> viewLists;

    public ViewpagerAdapter(List<View> lists) {
        viewLists = lists;
    }

    @Override
    public int getCount() { // 获得size
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View view, int position, Object object) {
        ((ViewPager) view).removeView(viewLists.get(position));
    }

    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager) view).addView(viewLists.get(position), 0);
        return viewLists.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}