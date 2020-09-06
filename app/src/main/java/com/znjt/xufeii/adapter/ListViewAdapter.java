package com.znjt.xufeii.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.znjt.xufeii.R;
import com.znjt.xufeii.bean.DataSimple;

import java.util.List;

/**
 * Created by Administrator on 2019/6/27.
 */

public class ListViewAdapter extends ArrayAdapter<DataSimple> {
    private List<DataSimple>list;

    public ListViewAdapter(@NonNull Context context, List<DataSimple> list) {
        super(context, 0);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_layout,parent,false);
        ImageView image=convertView.findViewById(R.id.image);
        TextView name=convertView.findViewById(R.id.name);
        DataSimple simple=list.get(position);
        image.setBackgroundResource(simple.getImage());
        name.setText(simple.getName());
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public DataSimple getItem(int position) {
        return list.get(position);
    }
}
