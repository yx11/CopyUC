package com.yx.android.copyuc.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx on 2016/1/21 0021.
 */
public class GuideAdapter extends PagerAdapter {
    List<View> relativeLayoutList = new ArrayList<View>();

    public GuideAdapter(List<View> imageViewList){
        this.relativeLayoutList = imageViewList;
    }
    @Override
    public int getCount() {
        return relativeLayoutList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View iv = relativeLayoutList.get(position);
        container.addView(iv);
        return iv;
    }



}
