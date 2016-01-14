package com.yx.android.copyuc.ui.impl;

import com.yx.android.copyuc.ui.holder.ViewHolderBase;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public interface ViewHolderCreator <ItemDataType> {
    ViewHolderBase<ItemDataType> createViewHolder(int var1);
}
