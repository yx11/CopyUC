package com.yx.android.copyuc.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yx on 2016/1/21 0021.
 */
public class MyViewPager extends ViewPager {
    private boolean canMove = false;
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(canMove)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if(canMove)
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
}
