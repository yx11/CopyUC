package com.yx.android.copyuc.ui.activtiy;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.config.Constants;
import com.yx.android.copyuc.ui.adapter.GuideAdapter;
import com.yx.android.copyuc.ui.widget.AnimationCircleTextView;
import com.yx.android.copyuc.ui.widget.MyViewPager;
import com.yx.android.copyuc.ui.widget.ViewPagerCompat;
import com.yx.android.copyuc.utils.SPUtil;
import com.yx.android.copyuc.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx on 2016/1/21 0021.
 */
public class GuideActivity extends BaseActivity implements ViewPagerCompat.OnPageChangeListener, View.OnClickListener {
    private View view1, view2;
    private List<View> relativeLayoutList;
    private AlphaAnimation anim;
    private ImageView mRing, mStar, mSlogan;

    private AnimationCircleTextView mLeft, mTop, mCenter, mRight, mBottom, mLeftTop, mLeftBottom, mRightTop;
    private boolean isNext = true;
    private TextView mStart;
    private MyViewPager mViewPager;
    private GuideAdapter mAdapter;
    private RelativeLayout mView2;

    @Override
    protected void initViewsAndEvents() {
        mViewPager = (MyViewPager) findViewById(R.id.vp_guide);
        initData();

        mAdapter = new GuideAdapter(relativeLayoutList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCanMove(false);
        mViewPager.setPageTransformer(true, new MyViewPager.MyPageTransformer());

        mRing = (ImageView) view1.findViewById(R.id.iv_ring);
        mStar = (ImageView) view1.findViewById(R.id.iv_star);
        mSlogan = (ImageView) view1.findViewById(R.id.iv_slogan);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.ring_rolate);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.star_rolate);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.slogan);
        mRing.startAnimation(animation);
        mStar.startAnimation(animation1);
        mSlogan.startAnimation(animation2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(1);
            }
        }, 6000);


        mTop = (AnimationCircleTextView) view2.findViewById(R.id.tv_top);
        mLeft = (AnimationCircleTextView) view2.findViewById(R.id.tv_left);
        mCenter = (AnimationCircleTextView) view2.findViewById(R.id.tv_center);
        mRight = (AnimationCircleTextView) view2.findViewById(R.id.tv_right);
        mBottom = (AnimationCircleTextView) view2.findViewById(R.id.tv_bottom);
        mLeftTop = (AnimationCircleTextView) view2.findViewById(R.id.tv_left_top);
        mLeftBottom = (AnimationCircleTextView) view2.findViewById(R.id.tv_left_bottom);
        mRightTop = (AnimationCircleTextView) view2.findViewById(R.id.tv_top_right);
        mStart = (TextView) view2.findViewById(R.id.tv_start_exprice);
        mView2 = (RelativeLayout) view2.findViewById(R.id.rl_view);


        mTop.setOnClickListener(this);
        mLeft.setOnClickListener(this);
        mCenter.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mBottom.setOnClickListener(this);
        mStart.setOnClickListener(this);
        mLeftTop.setOnClickListener(this);
        mLeftBottom.setOnClickListener(this);
        mRightTop.setOnClickListener(this);
    }

    private void initData() {
        view1 = UIUtils.inflate(R.layout.guide1);
        view2 = UIUtils.inflate(R.layout.guide2);
        relativeLayoutList = new ArrayList<>();
        relativeLayoutList.add(view1);
        relativeLayoutList.add(view2);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_guide;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        anim.start();

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_top:
                break;
            case R.id.tv_left:
                break;
            case R.id.tv_right:
                break;
            case R.id.tv_bottom:

                break;
            case R.id.tv_center:
                mCenter.startAnimation(AnimationUtils.loadAnimation(this, R.anim.circle_center_out));
                if (isNext) {
                    startAnimation(mCenter, "上一批", R.anim.circle_center_out, R.anim.circle_center_in, false);
                    startAnimation(mTop, "历史", R.anim.circle_top_out, R.anim.circle_top_in, true);
                    startAnimation(mLeftTop, "社会", R.anim.circle_top_out, R.anim.circle_top_in, true);
                    startAnimation(mLeft, "养生", R.anim.circle_left_out, R.anim.circle_left_in, true);
                    startAnimation(mLeftBottom, "汽车", R.anim.circle_left_out, R.anim.circle_left_in, true);
                    startAnimation(mBottom, "财经", R.anim.circle_bottom_out, R.anim.circle_bottom_in, true);
                    startAnimation(mRight, "男女", R.anim.circle_right_out, R.anim.circle_right_in, true);
                    startAnimation(mRightTop, "军事", R.anim.circle_right_out, R.anim.circle_right_in, true);
                    isNext = false;
                } else {
                    startAnimation(mCenter, "换一批", R.anim.circle_center_out, R.anim.circle_center_in, false);
                    startAnimation(mTop, "NBA", R.anim.circle_top_out, R.anim.circle_top_in, true);
                    startAnimation(mLeftTop, "头条", R.anim.circle_top_out, R.anim.circle_top_in, true);
                    startAnimation(mLeft, "冷知识", R.anim.circle_left_out, R.anim.circle_left_in, true);
                    startAnimation(mLeftBottom, "科技", R.anim.circle_left_out, R.anim.circle_left_in, true);
                    startAnimation(mBottom, "搞笑", R.anim.circle_bottom_out, R.anim.circle_bottom_in, true);
                    startAnimation(mRight, "娱乐", R.anim.circle_right_out, R.anim.circle_right_in, true);
                    startAnimation(mRightTop, "足球", R.anim.circle_right_out, R.anim.circle_right_in, true);
                    isNext = true;
                }
                break;
            case R.id.tv_start_exprice:
                SPUtil.putBoolean(Constants.Preference.IS_GUIDE, true);
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
        }
    }

    private void startAnimation(final AnimationCircleTextView textView, final String text, int out, final int in, final boolean isAnimation) {
        Animation animation = AnimationUtils.loadAnimation(this, out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText(text);
                Animation animation1 = AnimationUtils.loadAnimation(GuideActivity.this, in);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView.startAnimation(GuideActivity.this, isAnimation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                textView.startAnimation(animation1);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textView.startAnimation(animation);
    }
}
