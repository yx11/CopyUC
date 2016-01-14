package com.yx.android.copyuc.ui.impl;


public interface GridAdapterInterface {
    void reorderItems(int originalPosition, int newPosition);
    int getColumnCount();
    boolean canReorder(int position);

}
