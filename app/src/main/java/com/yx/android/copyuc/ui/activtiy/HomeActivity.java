package com.yx.android.copyuc.ui.activtiy;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.ui.fragment.MainFragment;
import com.yx.android.copyuc.ui.fragment.ShowFragment;
import com.yx.android.copyuc.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yx on 2015/12/9 0009.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private List<Fragment> mTabContents = new ArrayList<>();
    private ViewPager viewPager;
    private PopupWindow popupWindow;
    private ImageView mMenu;

    @Override
    protected void initViewsAndEvents() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabContents.add(new MainFragment());
        mTabContents.add(new ShowFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        mMenu = (ImageView) findViewById(R.id.iv_menu);
        mMenu.setOnClickListener(this);
        popupWindow = new PopupWindow();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                View view = UIUtils.inflate(R.layout.popup_window);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    }
                });
                popupWindow.setContentView(view);
                // 设置popWindow的显示和消失动画
                popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(viewPager, Gravity.BOTTOM, 0, 0);
                break;
        }

    }


    private class FragmentAdapter extends FragmentPagerAdapter {


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mTabContents.get(position);
        }

        @Override
        public int getCount() {
            return mTabContents.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

}
