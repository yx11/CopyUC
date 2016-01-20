package com.yx.android.copyuc.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.yx.android.copyuc.R;

/**
 * Created by yx on 2016/1/19 0019.
 */
public class AnimationCircleTextView extends TextView {
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;//默认字体颜色
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;//默认背景颜色
    private static final boolean IS_ANIMATION = true;//默认有动画
    private int mCircleColor, mBackGroundColor;
    private Paint mCirclePaint;
    private boolean mIsAnimation;


    public AnimationCircleTextView(Context context) {
        super(context);
    }

    public AnimationCircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView);
        if (a != null) {
            mCircleColor = a.getColor(R.styleable.CircleTextView_CircleColor, DEFAULT_TEXT_COLOR);
            mBackGroundColor = a.getResourceId(R.styleable.CircleTextView_Background, DEFAULT_BACKGROUND_COLOR);
            mIsAnimation = a.getBoolean(R.styleable.CircleTextView_isAnimation, IS_ANIMATION);
        }
        a.recycle();
        init(context);
    }


    public AnimationCircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView, defStyleAttr, 0);
        if (a != null) {
            mCircleColor = a.getColor(R.styleable.CircleTextView_CircleColor, DEFAULT_TEXT_COLOR);
            mBackGroundColor = a.getResourceId(R.styleable.CircleTextView_Background, DEFAULT_BACKGROUND_COLOR);
            mIsAnimation = a.getBoolean(R.styleable.CircleTextView_isAnimation, IS_ANIMATION);
        }
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(1);
        mCirclePaint.setColor(mCircleColor);
        startAnimation(context, mIsAnimation);
    }

    public void startAnimation(Context context, boolean mIsAnimation) {
        if (mIsAnimation) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.cirle_textview);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    startAnimation(animation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            setAnimation(animation);
            animation.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2 - 10, mCirclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int max = Math.max(measuredWidth, measuredHeight);
        setMeasuredDimension(max, max);
    }



    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }
}
