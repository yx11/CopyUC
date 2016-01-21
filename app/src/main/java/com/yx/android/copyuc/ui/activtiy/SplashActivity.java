package com.yx.android.copyuc.ui.activtiy;

import android.content.Intent;
import android.os.Handler;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.config.Constants;
import com.yx.android.copyuc.utils.SPUtil;

/**
 * Created by yx on 2016/1/21 0021.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void initViewsAndEvents() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SPUtil.getBoolean(Constants.Preference.IS_GUIDE, false)) {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                }
            }
        }, 2000);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }
}
