package com.yx.android.copyuc.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.utils.UIUtils;

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

    // 波纹颜色
    private static final int WAVE_PAINT_COLOR = 0x880000aa;
    // y = Asin(wx+b)+h
    private static final float STRETCH_FACTOR_A = 15;
    private static final int OFFSET_Y = 0;
    // 第一条水波移动速度
    private static final int TRANSLATE_X_SPEED_ONE = 3;

    private float mCycleFactorW;

    private int mTotalWidth, mTotalHeight;
    private float[] mYPositions;
    private float[] mResetOneYPositions;
    private int mXOffsetSpeedOne;
    private int mXOneOffset;

    private Paint mWavePaint;
    private DrawFilter mDrawFilter;

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

        // 将dp转化为px，用于控制不同分辨率上移动速度基本一致
        mXOffsetSpeedOne = UIUtils.dip2px(TRANSLATE_X_SPEED_ONE);

        // 初始绘制波纹的画笔
        mWavePaint = new Paint();
        // 去除画笔锯齿
        mWavePaint.setAntiAlias(true);
        // 设置风格为实线
        mWavePaint.setStyle(Paint.Style.FILL);
        // 设置画笔颜色
        mWavePaint.setColor(WAVE_PAINT_COLOR);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

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
        // 从canvas层面去除绘制时锯齿
        canvas.setDrawFilter(mDrawFilter);
        if (!mIsAnimation) {
            resetPositonY();
            for (int i = 0; i < mTotalWidth; i++) {

                // 减40只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
                // 绘制第一条水波纹
                canvas.drawLine(i + 10, mTotalHeight - mResetOneYPositions[i] - 40, i + 10,
                        mTotalHeight - 10,
                        mWavePaint);
            }

            // 改变两条波纹的移动点
            mXOneOffset += mXOffsetSpeedOne;

            // 如果已经移动到结尾处，则重头记录
            if (mXOneOffset >= mTotalWidth) {
                mXOneOffset = 0;
            }
            // 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片
            postInvalidate();

        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 2 - 10, mCirclePaint);
    }

    private void resetPositonY() {
        // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!mIsAnimation) {
            // 记录下view的宽高
            mTotalWidth = w - 10;
            mTotalHeight = h;
            // 用于保存原始波纹的y值
            mYPositions = new float[mTotalWidth];
            // 用于保存波纹一的y值
            mResetOneYPositions = new float[mTotalWidth];

            // 将周期定为view总宽度
            mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

            // 根据view总宽度得出所有对应的y值
            for (int i = 0; i < mTotalWidth; i++) {
                mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
            }
        }
    }
}
