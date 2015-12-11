package com.yx.android.copyuc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.ui.adapter.GradViewAdapter;
import com.yx.android.copyuc.ui.widget.MyGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yx on 2015/12/11 0011.
 */
public class ShowFragment extends Fragment {
    private MyGridView mGradView;
    private String[] iconName = {"通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
            "设置", "语音", "天气", "浏览器", "视频"};
    // 图片封装为一个数组
    private int[] icon = {android.R.drawable.ic_menu_my_calendar, android.R.drawable.ic_menu_my_calendar,
            android.R.drawable.ic_menu_camera, android.R.drawable.ic_lock_lock, android.R.drawable.ic_dialog_alert,
            android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_camera};
    private List<Map<String, Object>> lists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mGradView = (MyGridView) view.findViewById(R.id.gv_list);
        //新建List
        lists = new ArrayList<>();
        //获取数据
        getData();

        final GradViewAdapter adapter = new GradViewAdapter(getActivity(), mGradView, lists);

        mGradView.setAdapter(adapter);

        mGradView.setOnChanageListener(new MyGridView.OnChanageListener() {
            @Override
            public void onChange(int from, int to) {
                Map<String, Object> temp = lists.get(from);
                //这里的处理需要注意下
                if (from < to) {
                    for (int i = from; i < to; i++) {
                        Collections.swap(lists, i, i + 1);
                    }
                } else if (from > to) {
                    for (int i = from; i > to; i--) {
                        Collections.swap(lists, i, i - 1);
                    }
                }

                lists.set(to, temp);

                adapter.notifyDataSetChanged();
            }
        });
    }

    private List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            lists.add(map);
        }

        return lists;
    }

}
