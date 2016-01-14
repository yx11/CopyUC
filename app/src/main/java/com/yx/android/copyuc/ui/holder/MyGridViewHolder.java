package com.yx.android.copyuc.ui.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.bean.GridViewInfo;
import com.yx.android.copyuc.ui.adapter.MyGridViewAdapter;
import com.yx.android.copyuc.utils.UIUtils;

/**
 * Created by yx on 2016/1/14 0014.
 */
public class MyGridViewHolder extends ViewHolderBase<GridViewInfo> implements View.OnClickListener {
    private ImageView mLogo, mDelete;
    private TextView mName;
    private MyGridViewAdapter adapter;

    public MyGridViewHolder(final MyGridViewAdapter gridViewAdapter) {
        adapter = gridViewAdapter;
    }

    @Override
    public View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.ui_gridview_item, null);
        mLogo = (ImageView) view.findViewById(R.id.iv_img);
        mDelete = (ImageView) view.findViewById(R.id.iv_delete);
        mName = (TextView) view.findViewById(R.id.tv_title);
        mDelete.setOnClickListener(this);
        mDelete.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void showData(int position, GridViewInfo info) {
        mLogo.setImageDrawable(UIUtils.getDrawable(info.getViewLogo()));
        mName.setText(info.getViewName());
    }

    @Override
    public void onClick(View v) {
        adapter.remove(adapter.getItem(mPosition));
    }
}
