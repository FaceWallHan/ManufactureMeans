package com.znjt.xufeii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.znjt.xufeii.AppClient;
import com.znjt.xufeii.R;

import java.util.Date;

/**
 * Created by Administrator on 2019/4/26.
 */

public class SplashActivity extends AppCompatActivity {
    private AppClient app;
   // private TextView skip;
    private final int first=1,second=2;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case first:
                    startActivity(new Intent(SplashActivity.this,AddActivity.class));
                    finish();
                    break;
                case second:
                    startActivity(new Intent(SplashActivity.this,PagerActivity.class));
                    finish();
                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        inView();
        sendIntent();
    }
    private void sendIntent(){
        if (app.isLogin()){
                handler.sendEmptyMessageDelayed(first,1500);
        }else {
                handler.sendEmptyMessageDelayed(second,1500);
                app.setLogin(true);
        }
    }
    private void inView(){
        app= (AppClient) getApplication();
    }
}
