package com.yx.android.copyuc.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yx.android.copyuc.ui.holder.ViewHolderBase;
import com.yx.android.copyuc.ui.impl.ViewHolderCreator;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public abstract class ListViewDataAdapterBase<ItemDataType> extends BaseAdapter {
    private static final String LOG_TAG = "cube-list";
    protected ViewHolderCreator<ItemDataType> mViewHolderCreator;
    protected ViewHolderCreator<ItemDataType> mLazyCreator;
    protected boolean mForceCreateView = false;
    public static final int INVALID_ID = -1;
    private int nextStableId = 0;
    private HashMap<Object, Integer> mIdMap = new HashMap<Object, Integer>();
    private boolean isGridView = false;

    public ListViewDataAdapterBase() {
    }

    public ListViewDataAdapterBase(Object enclosingInstance, Class<?> cls) {
        this.setViewHolderClass(enclosingInstance, cls, new Object[0]);
    }

    public ListViewDataAdapterBase(ViewHolderCreator<ItemDataType> viewHolderCreator) {
        this.mViewHolderCreator = viewHolderCreator;
    }

    public void setViewHolderCreator(ViewHolderCreator<ItemDataType> viewHolderCreator) {
        this.mViewHolderCreator = viewHolderCreator;
    }

    public void setViewHolderClass(Object enclosingInstance, Class<?> cls, Object... args) {
        this.mLazyCreator = LazyViewHolderCreator.create(enclosingInstance, cls, args);
    }

    private ViewHolderBase<ItemDataType> createViewHolder(int position) {
        if (this.mViewHolderCreator == null && this.mLazyCreator == null) {
            throw new RuntimeException("view holder creator is null");
        } else {
            return this.mViewHolderCreator != null ? this.mViewHolderCreator.createViewHolder(position) : (this.mLazyCreator != null ? this.mLazyCreator.createViewHolder(position) : null);
        }
    }

    public void forceCreateView(boolean yes) {
        this.mForceCreateView = yes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Object itemData = this.getItem(position);
        ViewHolderBase holderBase = null;
        if (!this.mForceCreateView && convertView != null && convertView.getTag() instanceof ViewHolderBase) {
            holderBase = (ViewHolderBase) convertView.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            holderBase = this.createViewHolder(position);
            if (holderBase != null) {
                convertView = holderBase.createView(inflater);
                if (convertView != null && !this.mForceCreateView) {
                    convertView.setTag(holderBase);
                }
            }
        }

        if (holderBase != null) {
            holderBase.setItemData(position, convertView);
            holderBase.showData(position, itemData);
        }

        return convertView;
    }

    /**
     * Adapter must have stable id
     *
     * @return
     */
    @Override
    public final boolean hasStableIds() {
        return true;
    }

    /**
     * creates stable id for object
     *
     * @param item
     */
    protected void addStableId(Object item) {
        mIdMap.put(item, nextStableId++);
    }

    /**
     * create stable ids for list
     *
     * @param items
     */
    protected void addAllStableId(List<?> items) {
        for (Object item : items) {
            addStableId(item);
        }
    }

    /**
     * get id for position
     *
     * @param position
     * @return
     */
    @Override
    public final long getItemId(int position) {
        if (isGridView()) {
            if (position < 0 || position >= mIdMap.size()) {
                return INVALID_ID;
            }
            Object item = getItem(position);
            return mIdMap.get(item);
        } else {
            return position;
        }

    }

    /**
     * should called when clear adapter data;
     */
    protected void clearStableIdMap() {
        mIdMap.clear();
    }

    /**
     * @param item
     */
    protected void removeStableID(Object item) {
        mIdMap.remove(item);
    }

    public boolean isGridView() {
        return isGridView;
    }

    public void setIsGridView(boolean isGridView) {
        this.isGridView = isGridView;
    }


    public abstract ItemDataType getItem(int var1);


}
