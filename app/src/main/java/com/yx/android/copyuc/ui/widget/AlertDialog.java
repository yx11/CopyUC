package com.yx.android.copyuc.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.utils.StringUtils;
import com.yx.android.copyuc.utils.UIUtils;


public class AlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private TextView txt_msg;
    public EditText et_input;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showInput = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private boolean isDismiss = true;
    public String inputStr;

    public AlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.ui_alertdialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        et_input = (EditText) view.findViewById(R.id.et_input);
        et_input.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle) {
            @Override
            public void dismiss() {
                if (showInput) {
                    //关闭软键盘
                    UIUtils.closeKeybord(et_input, context);
                }
                super.dismiss();
            }
        };
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));
        return this;
    }

    public AlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(Html.fromHtml(title));
        }
        return this;
    }

    /**
     * 设置消息内容，默认居左
     *
     * @param msg
     * @return
     */
    public AlertDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(Html.fromHtml(msg));
        }
        return this;
    }

    /**
     * 设置消息内容，可指定文字位置
     *
     * @param msg
     * @param gravity
     * @return
     */
    public AlertDialog setMsg(String msg, int gravity) {
        txt_msg.setGravity(gravity);
        return setMsg(msg);
    }

    /**
     * 设置输入框
     *
     * @param hint   输入框提示内容
     * @param digits 允许输入的字符
     * @return
     */
    public AlertDialog setEditInput(String hint, String digits) {
        showInput = true;
        et_input.setHint(hint);
        if (!StringUtils.isEmpty(digits)) {
            et_input.setKeyListener(DigitsKeyListener.getInstance(digits));
        }
        return this;
    }


    /**
     * 设置返回键
     *
     * @param cancel
     * @return
     */
    public AlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertDialog setIsDismiss(boolean isDismiss) {
        this.isDismiss = isDismiss;
        return this;
    }

    public AlertDialog setPositiveButton(String text, final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (showInput) {
                        inputStr = et_input.getText().toString().trim();
                    }
                    listener.onClick(v);
                    if (!isDismiss) {
                        isDismiss = true;
                        return;
                    }
                }
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertDialog setNegativeButton(String text, final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                et_input.setError(null);
                isDismiss = true;
                dialog.dismiss();
            }
        });
        return this;
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    private void setLayout() {
        if (!showTitle && !showMsg && !showInput) {
            txt_title.setText("提示ʾ");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }
        if (showInput) {
            //弹出软键盘
            UIUtils.openKeybord(et_input, context);
            //显示输入框
            et_input.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setInputLimited(int limitedLength) {
        et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(limitedLength)});
    }
}
