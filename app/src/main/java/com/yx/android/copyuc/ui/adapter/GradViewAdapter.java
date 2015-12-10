package com.yx.android.copyuc.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yx.android.copyuc.R;
import com.yx.android.copyuc.ui.holder.GradViewHolder;
import com.yx.android.copyuc.ui.widget.MyGridView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yx on 2015/12/2 0002.
 */
public class GradViewAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private MyGridView mGradView;
    private Context context;
    List<Map<String, Object>> datas;
    private GradViewHolder holder;
    private boolean isShowDelete;//根据这个变量来判断是否显示删除图标，true是显示，false是不显示
    private boolean isAdd = false;

    public GradViewAdapter(Context context, MyGridView mGradView, List<Map<String, Object>> list) {
        this.context = context;
        this.mGradView = mGradView;
        this.datas = list;
        mGradView.setOnItemClickListener(this);
        mGradView.setOnItemLongClickListener(this);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gradview_item, null);
            holder = new GradViewHolder();
            holder.mText = (TextView) convertView.findViewById(R.id.tv_text);
            holder.mImage = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.mDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(holder);
        } else {
            holder = (GradViewHolder) convertView.getTag();
        }
        if (position != 0) {
            holder.mDelete.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);//设置删除按钮是否显示
            if (isAdd && position == datas.size() - 1) {
                holder.mDelete.setVisibility(View.GONE);
            }
        }
        holder.mText.setText((CharSequence) datas.get(position).get("text"));
        holder.mImage.setBackgroundResource((Integer) datas.get(position).get("image"));

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
            }
        });
        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, (CharSequence) datas.get(position).get("text"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        if (position == datas.size() - 1 && isAdd) {
            return true;
        } else {
            setIsShowDelete(isShowDelete == true ? false : true);
            return true;
        }
    }


    private void setIsShowDelete(boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
        notifyDataSetChanged();
    }

    private void deleteData(int position) {
        datas.remove(position);
        if (!isAdd) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", R.mipmap.ic_add);
            map.put("text", "");
            datas.add(map);
            isAdd = true;
        }
        setIsShowDelete(isAdd);
    }


}
