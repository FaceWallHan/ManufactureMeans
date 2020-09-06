package com.znjt.xufeii.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.znjt.xufeii.R;
import com.znjt.xufeii.fragment.Fragment_Developer;
import com.znjt.xufeii.fragment.Fragment_Help;
import com.znjt.xufeii.fragment.Fragment_Touch;

/**
 * Created by Administrator on 2019/5/5.
 */

public class AllOtherActivity extends BaseActivity {
    private TextView touch,help,developer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_layout);
        inView();
        setListener();
    }
    private void inView(){
        left();
        addActivity(this);
        setText("其他");
        touch=findViewById(R.id.touch);
        help=findViewById(R.id.help);
        developer=findViewById(R.id.developer);

        touch.setTextColor(Color.WHITE);
        help.setTextColor(Color.parseColor("#838282"));
        developer.setTextColor(Color.parseColor("#838282"));
        replace(new Fragment_Touch());
    }
    private void replace(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.commitAllowingStateLoss();
    }
    private void setListener(){
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touch.setTextColor(Color.WHITE);
                help.setTextColor(Color.parseColor("#838282"));
                developer.setTextColor(Color.parseColor("#838282"));
                replace(new Fragment_Touch());
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help.setTextColor(Color.WHITE);
                touch.setTextColor(Color.parseColor("#838282"));
                developer.setTextColor(Color.parseColor("#838282"));
                replace(new Fragment_Help());
            }
        });
        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                developer.setTextColor(Color.WHITE);
                help.setTextColor(Color.parseColor("#838282"));
                touch.setTextColor(Color.parseColor("#838282"));
                replace(new Fragment_Developer());
            }
        });
    }
}
