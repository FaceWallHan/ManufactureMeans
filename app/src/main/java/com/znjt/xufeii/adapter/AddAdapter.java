package com.znjt.xufeii.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.znjt.xufeii.R;
import com.znjt.xufeii.bean.Add_bean;

import java.util.List;

/**
 * Created by Administrator on 2019/4/28 0028.
 */

public class AddAdapter extends ArrayAdapter<Add_bean> {

    private int mLayout;

    public AddAdapter( Context context, int resource, List<Add_bean> objects) {
        super(context, resource, objects);
        mLayout=resource;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        Add_bean add_bean=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(mLayout,parent,false);
        TextView id=view.findViewById(R.id.add_id);
        TextView tv=view.findViewById(R.id.add_tv);
        id.setText(add_bean.getId());
        tv.setText(add_bean.getMessage());
        return view;
    }
}
