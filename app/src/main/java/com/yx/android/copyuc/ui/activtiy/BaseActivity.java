package com.yx.android.copyuc.ui.activtiy;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.eventbus.EventCenter;
import com.yx.android.copyuc.manager.BaseAppManager;
import com.yx.android.copyuc.ui.base.BaseAppcompatActivity;
import com.yx.android.copyuc.ui.base.BaseView;

import butterknife.ButterKnife;

/**
 * Created by yx on 2015/12/9 0009.
 */
public abstract class BaseActivity extends BaseAppcompatActivity implements BaseView {

    public Toolbar mToolbar;
    public TextView mTitle;
    public RelativeLayout mTopBar;

    /**
     * 记录处于前台的Activity
     */
    private static BaseActivity mForegroundActivity = null;
    private static BaseActivity mCurrentActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isApplyStatusBarTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));
        }
        mForegroundActivity = this;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        mTopBar = ButterKnife.findById(this, R.id.rl_title_bar);
        mTitle = ButterKnife.findById(this, R.id.toolbar_title);
        if (null != mToolbar) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            if (setToolBar()) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }
        }
    }

    protected boolean setToolBar() {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCurrentActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mForegroundActivity = this;
    }

    @Override
    protected void onEventComming(EventCenter var1) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mForegroundActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCurrentActivity = null;
    }


    /**
     * 获取当前处于前台的activity
     */
    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }


    public static BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }


    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showError(String msg) {
//        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
//        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
//        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg) {
//        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
//        toggleShowLoading(false, null);
    }

    public void finishActivities() {
        BaseAppManager.getInstance().clear();
    }

    @Override
    protected void getBundleExtras(Bundle var1) {

    }
}
