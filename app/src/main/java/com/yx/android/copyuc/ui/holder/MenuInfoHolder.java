package com.yx.android.copyuc.ui.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.bean.MenuInfo;
import com.yx.android.copyuc.utils.UIUtils;

/**
 * Created by yx on 2016/1/25 0025.
 */
public class MenuInfoHolder extends ViewHolderBase<MenuInfo> {
    private ImageView mImg;
    private TextView mText;

    @Override
    public View createView(LayoutInflater position) {
        View view = UIUtils.inflate(R.layout.gridview_item);
        mImg = (ImageView) view.findViewById(R.id.iv_img);
        mText = (TextView) view.findViewById(R.id.tv_text);
        return view;
    }

    @Override
    public void showData(int position, MenuInfo info) {
        mImg.setImageResource(info.getImg());
        mText.setText(info.getText());
    }
}
