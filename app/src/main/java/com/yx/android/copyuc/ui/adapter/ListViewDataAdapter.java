package com.yx.android.copyuc.ui.adapter;

import com.yx.android.copyuc.ui.impl.ViewHolderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx on 2016/1/25 0025.
 */
public class ListViewDataAdapter <ItemDataType> extends ListViewDataAdapterBase<ItemDataType> {
    protected List<ItemDataType> mItemDataList = new ArrayList();

    public ListViewDataAdapter() {
    }

    public ListViewDataAdapter(ViewHolderCreator<ItemDataType> viewHolderCreator) {
        super(viewHolderCreator);
    }

    public List<ItemDataType> getDataList() {
        return this.mItemDataList;
    }

    public int getCount() {
        return this.mItemDataList.size();
    }

    public ItemDataType getItem(int position) {
        return this.mItemDataList.size() > position && position >= 0?this.mItemDataList.get(position):null;
    }

    public long getItemId(int position) {
        return (long)position;
    }
}
