package com.yx.android.copyuc.ui.holder;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by yx on 2016/1/14 0014.
 */
public abstract class ViewHolderBase<ItemDataType> {
    protected int mLastPosition;
    protected int mPosition = -1;
    protected View mCurrentView;

    public ViewHolderBase() {
    }

    public abstract View createView(LayoutInflater var1);

    public abstract void showData(int var1, ItemDataType var2);

    public void setItemData(int position, View view) {
        this.mLastPosition = this.mPosition;
        this.mPosition = position;
        this.mCurrentView = view;
    }

    public boolean stillHoldLastItemData() {
        return this.mLastPosition == this.mPosition;
    }
}