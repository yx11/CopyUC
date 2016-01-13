package com.yx.android.copyuc.ui.fragment;

import android.view.View;

import com.yx.android.copyuc.eventbus.EventCenter;
import com.yx.android.copyuc.ui.base.BaseLazyFragment;

/**
 * Created by yx on 2016/1/13 0013.
 */
public class BaseFragment extends BaseLazyFragment {
    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected void onEventComming(EventCenter var1) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showException(String msg) {

    }

    @Override
    public void showNetError() {

    }
}
