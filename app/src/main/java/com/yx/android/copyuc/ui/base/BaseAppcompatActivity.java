package com.yx.android.copyuc.ui.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yx.android.copyuc.R;
import com.yx.android.copyuc.eventbus.EventCenter;
import com.yx.android.copyuc.manager.BaseAppManager;
import com.yx.android.copyuc.utils.SmartBarUtils;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by yx on 2016/1/11 0011.
 */
public abstract class BaseAppcompatActivity extends AppCompatActivity {
    protected static String TAG_LOG = null;
    protected Context mContext = null;
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //进入动画
        this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
        super.onCreate(savedInstanceState);

        //沉浸式菜单
        this.setTranslucentStatus(this.isApplyStatusBarTranslucency());


        Bundle extras = this.getIntent().getExtras();
        if(null != extras) {
            this.getBundleExtras(extras);
        }

        //注册EventBus
        if (this.isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }

        this.mContext = this;
        TAG_LOG = this.getClass().getSimpleName();
        BaseAppManager.getInstance().addActivity(this);

        SmartBarUtils.hide(this.getWindow().getDecorView());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.mScreenDensity = displayMetrics.density;
        this.mScreenHeight = displayMetrics.heightPixels;
        this.mScreenWidth = displayMetrics.widthPixels;

        if (this.getContentViewLayoutID() != 0) {
            this.setContentView(this.getContentViewLayoutID());
            this.initViewsAndEvents();
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
    }


    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);

    }


    /**
     * 设置状态栏背景状态
     */
    private void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
    }


    /**
     * 设置状态栏背景
     *
     * @param tintDrawable
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= 19) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable((Drawable) null);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        if (this.isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.right_in, R.anim.right_out);
        BaseAppManager.getInstance().removeActivity(this);
    }


    protected abstract boolean isApplyStatusBarTranslucency();

    protected abstract boolean isBindEventBusHere();

    protected abstract void initViewsAndEvents();

    protected abstract int getContentViewLayoutID();

    protected abstract void onEventComming(EventCenter var1);

    protected abstract void getBundleExtras(Bundle var1);
}
