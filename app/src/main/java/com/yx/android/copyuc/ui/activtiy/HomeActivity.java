package com.yx.android.copyuc.ui.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.ui.fragment.MainFragment;
import com.yx.android.copyuc.ui.fragment.ShowFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yx on 2015/12/9 0009.
 */
public class HomeActivity extends BaseActivity {
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabContents.add(new MainFragment());
        mTabContents.add(new ShowFragment());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());


        viewPager.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
}
