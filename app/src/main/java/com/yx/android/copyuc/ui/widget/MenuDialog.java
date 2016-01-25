package com.yx.android.copyuc.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.bean.MenuInfo;
import com.yx.android.copyuc.ui.adapter.ListViewDataAdapter;
import com.yx.android.copyuc.ui.holder.MenuInfoHolder;
import com.yx.android.copyuc.ui.holder.ViewHolderBase;
import com.yx.android.copyuc.ui.impl.ViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx on 2016/1/25 0025.
 */
public class MenuDialog {
    private Context context;
    private Display display;
    private List<MenuInfo> datas;
    private DynamicGridView gridView;
    private Dialog dialog;
    private ListViewDataAdapter adapter;
    private View view;

    public MenuDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        datas = new ArrayList<>();
    }

    public MenuDialog builder() {
//        View view = UIUtils.inflate(R.layout.popup_window);
        // 获取Dialog布局
        view = LayoutInflater.from(context).inflate(R.layout.ui_menu, null);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionBottomDialogStyle);
//        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        dialog.setContentView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }

    public MenuDialog addItem(List<MenuInfo> strItems, final OnSheetItemClickListener listener) {
        datas.clear();
        datas.addAll(strItems);
        if (adapter == null) {
            gridView = (DynamicGridView) view.findViewById(R.id.gv_list);
            adapter = new ListViewDataAdapter(new ViewHolderCreator<MenuInfo>() {

                @Override
                public ViewHolderBase<MenuInfo> createViewHolder(int position) {
                    return new MenuInfoHolder();
                }


            });
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listener.onClick(position);
                }
            });
        }
        adapter.getDataList().addAll(datas);
        adapter.notifyDataSetChanged();
        return this;
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    /**
     * 设置外部点击
     *
     * @param cancel
     * @return
     */
    public MenuDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }


    public void show() {

        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        if (dialog.isShowing()) {
            return true;
        } else {
            return false;
        }

    }


}
