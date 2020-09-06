package com.znjt.xufeii.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.znjt.xufeii.R;
import com.znjt.xufeii.fragment.Fragment_Head;
import com.znjt.xufeii.fragment.Fragment_Photo;
import com.znjt.xufeii.fragment.Fragment_Thesaurus;

/**
 * Created by Administrator on 2019/4/28 0028.
 */

public class AddActivity extends BaseActivity{
    private LinearLayout photo,head,thesaurus;
    private String click="#bdaafd",noClick="#0078bd";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        initView();
        initLister();
    }
    private void replace(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.commitAllowingStateLoss();
    }
    private void initLister() {
        thesaurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thesaurus.setBackgroundColor(Color.parseColor(click));
                head.setBackgroundColor(Color.parseColor(noClick));
                photo.setBackgroundColor(Color.parseColor(noClick));
                replace(new Fragment_Thesaurus());
            }
        });
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                head.setBackgroundColor(Color.parseColor(click));
                thesaurus.setBackgroundColor(Color.parseColor(noClick));
                photo.setBackgroundColor(Color.parseColor(noClick));
                replace(new Fragment_Head());
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo.setBackgroundColor(Color.parseColor(click));
                head.setBackgroundColor(Color.parseColor(noClick));
                thesaurus.setBackgroundColor(Color.parseColor(noClick));
                replace(new Fragment_Photo());
            }
        });
    }


    private void initView() {
        left();
        addActivity(this);
        setText("添加词汇");
        photo=findViewById(R.id.photo);
        head=findViewById(R.id.head);
        thesaurus=findViewById(R.id.thesaurus);
        thesaurus.setBackgroundColor(Color.parseColor(click));
        head.setBackgroundColor(Color.parseColor(noClick));
        photo.setBackgroundColor(Color.parseColor(noClick));
        replace(new Fragment_Thesaurus());
    }

}
