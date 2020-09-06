package com.znjt.xufeii.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.znjt.xufeii.R;
import com.znjt.xufeii.activity.AddActivity;
import com.znjt.xufeii.activity.TtsDemo;
import com.znjt.xufeii.adapter.AddAdapter;
import com.znjt.xufeii.bean.Add_bean;
import com.znjt.xufeii.dialog.AddDialog;
import com.znjt.xufeii.sql.SQLiteData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/5/6.
 */

public class Fragment_Head extends Fragment {
    private ListView mListView;
    private List<Add_bean> mList;
    private AddAdapter mAdapter;
    private SQLiteData data;
    private TextView mAdd;
    private TextView mTx;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initLister();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_layout,container,false);
    }
    private void initLister() {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDialog dialog=new AddDialog(getContext());
                dialog.setData(new AddDialog.SetData() {
                    @Override
                    public void setdata() {
                        initData();
                    }
                });
                dialog.show(getChildFragmentManager(),"");
            }
        });
        mTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),TtsDemo.class));
            }
        });
    }

    private void initData() {
        mList=new ArrayList<>();
        data=new SQLiteData(getContext(),"danci.db",null,1);
        SQLiteDatabase db=data.getWritableDatabase();
        Cursor cursor=db.query("study",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String word=cursor.getString(cursor.getColumnIndex("word"));
                mList.add(new Add_bean(id+"",word));
            }while (cursor.moveToNext());
        }
        cursor.close();
        mAdapter=new AddAdapter(getContext(),R.layout.add_item,mList);
        mListView.setAdapter(mAdapter);
    }

    private void initView() {
        mListView=getView().findViewById(R.id.add_list);
        mAdd=getView().findViewById(R.id.title_add);
        mTx=getView().findViewById(R.id.title_tx);
    }
}
