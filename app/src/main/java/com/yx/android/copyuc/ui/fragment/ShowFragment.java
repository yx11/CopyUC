package com.yx.android.copyuc.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.bean.GridViewInfo;
import com.yx.android.copyuc.ui.activtiy.VoiceActivity;
import com.yx.android.copyuc.ui.adapter.MyGridViewAdapter;
import com.yx.android.copyuc.ui.holder.MyGridViewHolder;
import com.yx.android.copyuc.ui.holder.ViewHolderBase;
import com.yx.android.copyuc.ui.impl.ViewHolderCreator;
import com.yx.android.copyuc.ui.widget.DynamicGridView;
import com.yx.android.copyuc.utils.TLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yx on 2015/12/11 0011.
 */
public class ShowFragment extends BaseFragment {
    private ImageView mSpeek;
    private List<GridViewInfo> lists;
    private DynamicGridView gridView;
    private MyGridViewAdapter adapter;

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_show;
    }

    private void initView() {
        mSpeek = (ImageView) getActivity().findViewById(R.id.iv_speek);

        mSpeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VoiceActivity.class));
            }
        });

        //新建List
        lists = new ArrayList<>();
        //获取数据
        getData();


        gridView = (DynamicGridView) getActivity().findViewById(R.id.dynamic_grid);

        adapter = new MyGridViewAdapter<>(new ViewHolderCreator<GridViewInfo>() {

            @Override
            public ViewHolderBase<GridViewInfo> createViewHolder(int position) {
                return new MyGridViewHolder(adapter);
            }
        }, lists, 4);
        gridView.setAdapter(adapter);
        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                TLog.d("onDragStarted", "drag started at position " + position);
            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                TLog.d("onDragPositionsChanged", String.format("drag item position changed from %d to %d", oldPosition, newPosition));
//                gridView.stopEditMode();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), parent.getAdapter().getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData() {
        for (int i = 0; i < 24; i++) {
            GridViewInfo info = new GridViewInfo();
            info.setViewName("通讯录" + i);
            if (i % 2 == 0) {
                info.setViewLogo(android.R.drawable.ic_menu_my_calendar);
            } else {
                info.setViewLogo(android.R.drawable.ic_menu_add);
            }
            lists.add(info);
        }

    }

    @Override
    protected void onUserInvisible() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
        }
    }
}
