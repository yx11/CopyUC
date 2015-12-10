package com.yx.android.copyuc.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by yx on 2015/12/2 0002.
 */
public class MyGridView extends GridView {


    /**
     * MyGradView的item长按响应时间，默认1000毫秒
     */
    private long dragResponseMs = 1000;

    /**
     * 是否可以拖拽，默认不可以
     */
    private boolean isDrag = false;
    private int mDownX;
    private int mDownY;
    private int moveX;
    private int moveY;

    /**
     * 正在拖拽的position
     */
    private int mDragPosition;

    /**
     * 刚开始拖拽的item对应的View
     */
    private View mStartDragView = null;


    /**
     * 用于拖拽的镜像，这里直接用一个ImageView
     */
    private ImageView mDragImageView;


    private WindowManager mWindowManager;

    /**
     * item镜像布局参数
     */
    private WindowManager.LayoutParams mWindowMangerParams;

    /**
     * 拖拽item对应bitmap
     */
    private Bitmap mDragBitmap;

    /**
     * 按下的点距离item上边缘距离
     */
    private int mPostion2ItemTop;

    /**
     * 按下的点距离iten左边缘距离
     */
    private int mPosition2ItemLeft;

    /**
     * GradView距离屏幕顶部的偏移量
     */
    private int mOffset2Top;

    /**
     * GradView距离屏幕左边的偏移量
     */
    private int mOffset2Left;

    /**
     * GradView自动向下滚动的边界值
     */
    private int mDownScrollBorder;

    /**
     * GradView自动向上滚动的边界值
     */
    private int mUpScrollBorder;


    /**
     * 状态栏高度
     */
    private int mStatusHeight;


    /**
     * GradView自动滚动速度
     */
    private static final int speed = 20;


    /**
     * item发生变化的回调接口
     */
    private OnChanageListener onChanageListener;

    private Handler mHandler = new Handler();

    public MyGridView(Context context) {
        super(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(context); //获取状态栏的高度
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(context); //获取状态栏的高度
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(context); //获取状态栏的高度
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    private int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int height = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isDrag && mDragImageView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    moveX = (int) ev.getX();
                    moveY = (int) ev.getY();
                    //拖动item
                    onDragItem(moveX, moveY);
                    break;
                case MotionEvent.ACTION_UP:
                    onStopDrag();
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 拖动item，在里面实现了item镜像的位置更新，item的相互交换以及GridView的自行滚动
     *
     * @param moveX
     * @param moveY
     */
    private void onDragItem(int moveX, int moveY) {
        mWindowMangerParams.x = moveX - mPosition2ItemLeft + mOffset2Left;
        mWindowMangerParams.y = moveY - mPostion2ItemTop + mOffset2Top - mStatusHeight;
        mWindowManager.updateViewLayout(mDragImageView, mWindowMangerParams); //更新镜像的位置
        onSwapItem(moveX, moveY);

        //GridView自动滚动
//        mHandler.post(mScrollRunnable);
    }

    /**
     * 停止拖拽我们将之前隐藏的item显示出来，并将镜像移除
     */
    private void onStopDrag() {
        getChildAt(mDragPosition - getFirstVisiblePosition()).setVisibility(View.VISIBLE);
        removeDragImage();

    }

    public void setDrag(boolean drag) {
        this.isDrag = drag;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();
                //根据按下的X,Y坐标获取所点击item的position
                mDragPosition = pointToPosition(mDownX, mDownY);
                if (mDragPosition == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(ev);
                }
                //使用Handler延迟dragResponseMs执行mLongClickRunnable
                mHandler.postDelayed(mLongClickRunnable, dragResponseMs);

                //根据position获取该item所对应的View
                mStartDragView = getChildAt(mDragPosition - getFirstVisiblePosition());

                mPostion2ItemTop = mDownY - mStartDragView.getTop();
                mPosition2ItemLeft = mDownX - mStartDragView.getLeft();

                mOffset2Top = (int) (ev.getRawY() - mDownY);
                mOffset2Left = (int) (ev.getRawX() - mDownX);

                //获取DragGridView自动向上滚动的偏移量，小于这个值，DragGridView向下滚动
//                mDownScrollBorder = getHeight() / 4;
                //获取DragGridView自动向下滚动的偏移量，大于这个值，DragGridView向上滚动
//                mUpScrollBorder = getHeight() * 3 / 4;


                //开启mDragItemView绘图缓存
                mStartDragView.setDrawingCacheEnabled(true);
                //获取mDragItemView在缓存中的Bitmap对象
                mDragBitmap = Bitmap.createBitmap(mStartDragView.getDrawingCache());
                //这一步很关键，释放绘图缓存，避免出现重复的镜像
                mStartDragView.destroyDrawingCache();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                //如果我们在按下的item上面移动，只要不超过item的边界我们就不移除mRunnable
                if (!isTouchInItem(mStartDragView, moveX, moveY)) {
                    mHandler.removeCallbacks(mLongClickRunnable);
                }
                break;
            case MotionEvent.ACTION_UP:
                mHandler.removeCallbacks(mLongClickRunnable);
//                mHandler.removeCallbacks(mScrollRunnable);
                break;
        }


        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否点击在GridView的item上面
     *
     * @param dragView
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchInItem(View dragView, int x, int y) {
        if (dragView == null) {
            return false;
        }
        int leftOffset = dragView.getLeft();
        int topOffset = dragView.getTop();
        if (x < leftOffset || x > leftOffset + dragView.getWidth()) {
            return false;
        }

        if (y < topOffset || y > topOffset + dragView.getHeight()) {
            return false;
        }

        return true;
    }

    //用来处理是否为长按的Runnable
    private Runnable mLongClickRunnable = new Runnable() {

        @Override
        public void run() {
            isDrag = true; //设置可以拖拽
            mStartDragView.setVisibility(View.INVISIBLE);//隐藏该item

            //根据我们按下的点显示item镜像
            createDragImage(mDragBitmap, mDownX, mDownY);
        }
    };


    /**
     * 当moveY的值大于向上滚动的边界值，触发GridView自动向上滚动
     * 当moveY的值小于向下滚动的边界值，触犯GridView自动向下滚动
     * 否则不进行滚动
     */
    private Runnable mScrollRunnable = new Runnable() {

        @Override
        public void run() {
            int scrollY;
            if (moveY > mUpScrollBorder) {
                scrollY = speed;
                mHandler.postDelayed(mScrollRunnable, 25);
            } else if (moveY < mDownScrollBorder) {
                scrollY = -speed;
                mHandler.postDelayed(mScrollRunnable, 25);
            } else {
                scrollY = 0;
                mHandler.removeCallbacks(mScrollRunnable);
            }

            //当我们的手指到达GridView向上或者向下滚动的偏移量的时候，可能我们手指没有移动，但是DragGridView在自动的滚动
            //所以我们在这里调用下onSwapItem()方法来交换item
            onSwapItem(moveX, moveY);


            View view = getChildAt(mDragPosition - getFirstVisiblePosition());
            //实现GridView的自动滚动
            smoothScrollToPositionFromTop(mDragPosition, view.getTop() + scrollY);
        }
    };

    /**
     * 交换item,并且控制item之间的显示与隐藏效果
     *
     * @param moveX
     * @param moveY
     */
    private void onSwapItem(int moveX, int moveY) {
        //获取我们手指移动到的那个item的position
        int tempPosition = pointToPosition(moveX, moveY);

        //假如tempPosition 改变了并且tempPosition不等于-1,则进行交换
        if (tempPosition != mDragPosition && tempPosition != AdapterView.INVALID_POSITION) {
            if (onChanageListener != null) {
                onChanageListener.onChange(mDragPosition, tempPosition);
            }

            getChildAt(tempPosition - getFirstVisiblePosition()).setVisibility(View.INVISIBLE);//拖动到了新的item,新的item隐藏掉
            getChildAt(mDragPosition - getFirstVisiblePosition()).setVisibility(View.VISIBLE);//之前的item显示出来

            mDragPosition = tempPosition;
        }
    }


    /**
     * 创建拖动的镜像
     *
     * @param bitmap
     * @param downX  按下的点相对父控件的X坐标
     * @param downY  按下的点相对父控件的Y坐标
     */
    private void createDragImage(Bitmap bitmap, int downX, int downY) {
        mWindowMangerParams = new WindowManager.LayoutParams();
        mWindowMangerParams.format = PixelFormat.TRANSLUCENT;//图片之外的背景透明
        mWindowMangerParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowMangerParams.x = downX - mPosition2ItemLeft + mOffset2Left;
        mWindowMangerParams.y = downY - mPostion2ItemTop + mOffset2Top - mStatusHeight;
        mWindowMangerParams.alpha = 0.55f;//透明度
        mWindowMangerParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowMangerParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowMangerParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mDragImageView = new ImageView(getContext());
        mDragImageView.setImageBitmap(bitmap);
        mWindowManager.addView(mDragImageView, mWindowMangerParams);
    }

    /**
     * 从界面上面移动拖动镜像
     */
    private void removeDragImage() {
        if (mDragImageView != null) {
            mWindowManager.removeView(mDragImageView);
            mDragImageView = null;
        }
    }

    public void setOnChanageListener(OnChanageListener onChanageListener) {
        this.onChanageListener = onChanageListener;
    }

    /**
     * 设置响应拖拽的毫秒数，默认是1000毫秒
     *
     * @param dragResponseMs
     */
    public void setDragResponseMs(long dragResponseMs) {
        this.dragResponseMs = dragResponseMs;
    }

    public interface OnChanageListener {
        /**
         * 当item交换位置的时候回调的方法，我们只需要在该方法中实现数据的交换即可
         *
         * @param form 开始的position
         * @param to   拖拽到的position
         */
        public void onChange(int form, int to);
    }
}
