package com.yx.android.copyuc.ui.activtiy;


import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.ui.widget.AnimationCircleTextView;


/**
 * Created by yx on 2016/1/18 0018.
 */
public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private AnimationCircleTextView mLeft, mTop, mCenter, mRight, mBottom, mLeftTop, mLeftBottom, mRightTop;
    private boolean isNext = true;
    private TextView mStart;

    @Override
    protected void initViewsAndEvents() {
        mTop = (AnimationCircleTextView) findViewById(R.id.tv_top);
        mLeft = (AnimationCircleTextView) findViewById(R.id.tv_left);
        mCenter = (AnimationCircleTextView) findViewById(R.id.tv_center);
        mRight = (AnimationCircleTextView) findViewById(R.id.tv_right);
        mBottom = (AnimationCircleTextView) findViewById(R.id.tv_bottom);
        mLeftTop = (AnimationCircleTextView) findViewById(R.id.tv_left_top);
        mLeftBottom = (AnimationCircleTextView) findViewById(R.id.tv_left_bottom);
        mRightTop = (AnimationCircleTextView) findViewById(R.id.tv_top_right);
        mStart = (TextView) findViewById(R.id.tv_start_exprice);

        mStart.startAnimation(AnimationUtils.loadAnimation(this, R.anim.start));

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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
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
                Animation animation1 = AnimationUtils.loadAnimation(SplashActivity.this, in);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView.startAnimation(SplashActivity.this, isAnimation);
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
