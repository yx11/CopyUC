package com.yx.android.copyuc.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by yx on 2016/1/21 0021.
 */
public class MyViewPager extends ViewPagerCompat {
    private boolean canMove = false;
    private static float MIN_SCALE = 0.75f;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (canMove)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (canMove)
            return super.onTouchEvent(arg0);
        else
            return false;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public static final class MyPageTransformer implements ViewPager.PageTransformer {
        private static float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                ViewHelper.setAlpha(view, 0);
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when
                // moving to the left page
                ViewHelper.setAlpha(view, 1);
                // view.setTranslationX(0);
                ViewHelper.setTranslationX(view, 0);
                // view.setScaleX(1);
                ViewHelper.setScaleX(view, 1);
                // view.setScaleY(1);
                ViewHelper.setScaleY(view, 1);
            } else if (position <= 1) { // (0,1]
                ViewHelper.setAlpha(view, 1 - position);

                // Counteract the default slide transition
                // view.setTranslationX(pageWidth * -position);
                ViewHelper.setTranslationX(view, pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                // view.setScaleX(scaleFactor);
                ViewHelper.setScaleX(view, scaleFactor);
                // view.setScaleY(1);
                ViewHelper.setScaleY(view, scaleFactor);

            } else { // (1,+Infinity]
                // view.setAlpha(0);
                ViewHelper.setAlpha(view, 1);
            }
        }
    }
}
