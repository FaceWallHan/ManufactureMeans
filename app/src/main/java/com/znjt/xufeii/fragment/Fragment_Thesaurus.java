package com.znjt.xufeii.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.znjt.xufeii.R;
import com.znjt.xufeii.adapter.ListViewAdapter;
import com.znjt.xufeii.bean.DataSimple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/5/6.
 */

public class Fragment_Thesaurus extends Fragment {
    private ProgressDialog dialog;
    private List<DataSimple> list;
    private ListViewAdapter adapter;
    private ListView word;
    private int image[]=new int[]{R.drawable.stu11,
            R.drawable.stu22,
            R.drawable.stu21,
            R.drawable.stu222,
            R.drawable.stu411,
            R.drawable.stu41,
            R.drawable.stu51,
            R.drawable.stu511,
            R.drawable.stu61,
            R.drawable.stu60};
    private String name[]=new String[]{"一年级语文上册",
            "二年级语文上册",
            "二年级英语上册",
            "二年级英语下册",
            "四年级语文上册",
            "四年级英语上册",
            "五年级英语上册",
            "五年级语文上册",
            "六年级英语上册",
            "六年级语文上册"};
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            dialog.dismiss();
            Toast.makeText(getContext(), "获取失败，请联系客服", Toast.LENGTH_SHORT).show();
            return false;
        }
    });
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thesaurus_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        setListener();
    }
    private void setListener(){
        word.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.show();
                handler.sendEmptyMessageDelayed(0,1000);
            }
        });
        for (int i = 0; i < image.length; i++) {
            list.add(new DataSimple(name[i],image[i]));
        }
        adapter.notifyDataSetChanged();
    }
    private void inView(){
        word=getView().findViewById(R.id.word);
        dialog=new ProgressDialog(getContext());
        dialog.setTitle("");
        dialog.setMessage("loading...");
        list=new ArrayList<>();
        adapter=new ListViewAdapter(getContext(),list);
        word.setAdapter(adapter);
    }
}
