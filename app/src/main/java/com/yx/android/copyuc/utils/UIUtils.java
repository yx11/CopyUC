package com.yx.android.copyuc.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.yx.android.copyuc.manager.BaseApplication;
import com.yx.android.copyuc.ui.activtiy.BaseActivity;
import com.yx.android.copyuc.ui.widget.AlertDialog;


public class UIUtils {

    public static Context getContext() {
        return BaseApplication.getApplication();
    }


    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    private static Toast toast;

    public static void showToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(UIUtils.getContext(), "", str.length() < 10 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        }
        toast.setText(str);//刷新文字内容
        toast.show();//显示toast
    }

    /**
     * 呼出软键盘
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private static AlertDialog noNetWorkDialog;

    public static void showNoNetWorkDialog() {
        if (BaseActivity.getForegroundActivity() == null) {
            return;
        }
        if (noNetWorkDialog == null || !noNetWorkDialog.isShowing()) {
            noNetWorkDialog = new AlertDialog(BaseActivity.getForegroundActivity()).builder();
            noNetWorkDialog.setTitle("当前无网络")
                    .setMsg("当前正处于无网络状态,请您先设置网络连接")
                    .setPositiveButton("设置", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            // 跳转到系统的网络设置界面
                            UIUtils.getContext().startActivity(intent);
                        }
                    })
                    .setNegativeButton("知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
        }
    }

    /**
     * @Title: closeDialog
     * @Description: 关闭对话框
     */
    public static void closeNoNetWorkDialog() {
        if (noNetWorkDialog != null && noNetWorkDialog.isShowing()) {
            noNetWorkDialog.dismiss();
        }
    }
}


