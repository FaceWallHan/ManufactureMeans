package com.znjt.xufeii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.znjt.xufeii.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2019/5/5.
 */

public class AllCheckActivity extends BaseActivity {
    private List<String>list;
    private ListView check_history;
    private ArrayAdapter<String>adapter;
    private Button check;
    private String[] type=new String[]{"语文","英语"};
    private Random random;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_layout);
        inView();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });
        for (int i = 0; i < 10; i++) {
            int month=random.nextInt(12);
            int day=random.nextInt(28);
            if (month==0){
                month++;
            }
            if (day==0){
                day++;
            }
            int time=random.nextInt(60);
            list.add((i+1)+"."+type[random.nextInt(type.length)]+"听写记录\t"+"2019."+month+"."+day+"\t"+random.nextInt(24)+":"+time+":"+time);
        }
        adapter.notifyDataSetChanged();
    }
    private void inView(){
        left();
        addActivity(this);
        setText("检查");
        list=new ArrayList<>();
        check_history=findViewById(R.id.check_history);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        check_history.setAdapter(adapter);
        check=findViewById(R.id.check);
        random=new Random();
    }
}
