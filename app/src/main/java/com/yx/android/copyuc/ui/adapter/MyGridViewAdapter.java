package com.yx.android.copyuc.ui.adapter;

import com.yx.android.copyuc.ui.impl.GridAdapterInterface;
import com.yx.android.copyuc.ui.impl.ViewHolderCreator;
import com.yx.android.copyuc.utils.GridViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx on 2016/1/14 0014.
 */
public class MyGridViewAdapter<GridViewInfo> extends ListViewDataAdapterBase<GridViewInfo> implements GridAdapterInterface{
    private ArrayList<GridViewInfo> mItems = new ArrayList<>();
    private int mColumnCount;

    public MyGridViewAdapter(ViewHolderCreator<GridViewInfo> viewHolderCreator, List<GridViewInfo> items, int columnCount) {
        super(viewHolderCreator);
        setIsGridView(true);
        mColumnCount = columnCount;
        init(items);
    }

    public MyGridViewAdapter(ViewHolderCreator<GridViewInfo> viewHolderCreator) {
        super(viewHolderCreator);
    }

    public ArrayList<GridViewInfo> getDataList() {
        return this.mItems;
    }

    private void init(List<GridViewInfo> items) {
        addAllStableId(items);
        this.mItems.addAll(items);
    }


    public void set(List<GridViewInfo> items) {
        clear();
        init(items);
        notifyDataSetChanged();
    }

    public void clear() {
        clearStableIdMap();
        mItems.clear();
        notifyDataSetChanged();
    }

    public void add(GridViewInfo item) {
        addStableId(item);
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void add(int position, GridViewInfo item) {
        addStableId(item);
        mItems.add(position, item);
        notifyDataSetChanged();
    }

    public void add(List<GridViewInfo> items) {
        addAllStableId(items);
        this.mItems.addAll(items);
        notifyDataSetChanged();
    }


    public void remove(Object item) {
        mItems.remove(item);
        removeStableID(item);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public GridViewInfo getItem(int position) {
        return mItems.get(position);
    }


    @Override
    public int getColumnCount() {
        return mColumnCount;
    }

    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount;
        notifyDataSetChanged();
    }

    @Override
    public void reorderItems(int originalPosition, int newPosition) {
        if (newPosition < getCount()) {
            GridViewUtils.reorder(mItems, originalPosition, newPosition);
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean canReorder(int position) {
        return true;
    }

    public List<GridViewInfo> getItems() {
        return mItems;
    }
}
