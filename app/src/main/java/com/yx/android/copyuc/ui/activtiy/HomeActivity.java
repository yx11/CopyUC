package com.yx.android.copyuc.ui.activtiy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.yx.android.copyuc.R;
import com.yx.android.copyuc.bean.MenuInfo;
import com.yx.android.copyuc.ui.fragment.MainFragment;
import com.yx.android.copyuc.ui.fragment.ShowFragment;
import com.yx.android.copyuc.ui.widget.MenuDialog;
import com.yx.android.copyuc.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yx on 2015/12/9 0009.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private List<Fragment> mTabContents = new ArrayList<>();
    private ViewPager viewPager;
    //    private PopupWindow popupWindow;
    private ImageView mMenu;
    private MenuDialog dialog;
    private List<MenuInfo> list;
    private static float MIN_SCALE = 0.75f;
    private LinearLayout mTop;

    @Override
    protected void initViewsAndEvents() {
        mTop = (LinearLayout) findViewById(R.id.ll_top);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabContents.add(new MainFragment());
        mTabContents.add(new ShowFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                if (position < -1) { // [-Infinity,-1)
                } else if (position <= 0) { // [-1,0]
                    ViewHelper.setTranslationY(view, -30);
                } else if (position <= 1) { // (0,1]
                } else { // (1,+Infinity]
                }

            }
        });

        list = new ArrayList<>();
        initData();
        mMenu = (ImageView) findViewById(R.id.iv_menu);
        mMenu.setOnClickListener(this);
//        popupWindow = new PopupWindow();
    }

    private void initData() {
        MenuInfo info1 = new MenuInfo();
        info1.setImg(R.mipmap.menu_bookmarkhistory);
        info1.setText("书签/收藏");

        MenuInfo info2 = new MenuInfo();
        info2.setImg(R.mipmap.menu_download);
        info2.setText("下载/文件");

        MenuInfo info3 = new MenuInfo();
        info3.setImg(R.mipmap.menu_novel_bookshelf);
        info3.setText("小说书架");

        MenuInfo info4 = new MenuInfo();
        info4.setImg(R.mipmap.menu_my_video);
        info4.setText("我的视频");

        MenuInfo info5 = new MenuInfo();
        info5.setImg(R.mipmap.menu_checkupdate);
        info5.setText("检查更新");

        MenuInfo info6 = new MenuInfo();
        info6.setImg(R.mipmap.menu_addtobookmark);
        info6.setText("收藏网址");

        MenuInfo info7 = new MenuInfo();
        info7.setImg(R.mipmap.menu_my_video);
        info7.setText("夜间模式");

        MenuInfo info8 = new MenuInfo();
        info8.setImg(R.mipmap.menu_setting_inland);
        info8.setText("设置");

        MenuInfo info9 = new MenuInfo();
        info9.setImg(R.mipmap.menu_refresh);
        info9.setText("刷新");

        MenuInfo info10 = new MenuInfo();
        info10.setImg(R.mipmap.menu_exit);
        info10.setText("退出");

        list.add(info1);
        list.add(info2);
        list.add(info3);
        list.add(info4);
        list.add(info5);
        list.add(info6);
        list.add(info7);
        list.add(info8);
        list.add(info9);
        list.add(info10);
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
                dialog = new MenuDialog(this).builder();
                dialog.addItem(list, new MenuDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        UIUtils.showToast(list.get(which).getText().toString());
                    }
                }).setCanceledOnTouchOutside(true).show();
//                View view = UIUtils.inflate(R.layout.popup_window);
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (popupWindow.isShowing()) {
//                            popupWindow.dismiss();
//                        }
//                    }
//                });
//                popupWindow.setContentView(view);
//                // 设置popWindow的显示和消失动画
//                popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.setFocusable(true);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//                popupWindow.showAtLocation(viewPager, Gravity.BOTTOM, 0, 0);
//                break;
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
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
