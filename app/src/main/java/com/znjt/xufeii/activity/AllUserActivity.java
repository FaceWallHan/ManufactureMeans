package com.znjt.xufeii.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.znjt.xufeii.R;

/**
 * Created by Administrator on 2019/4/29.
 */

public class AllUserActivity extends BaseActivity {
    private ProgressDialog dialog;
    private Button submit;
    private RatingBar able,speed;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            dialog.dismiss();
            Toast.makeText(AllUserActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }
    });
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);
        left();
        addActivity(this);
        setText("用户评价");
        inView();
        setListener();
    }
    private void setListener(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                handler.sendEmptyMessageDelayed(0,3000);
            }
        });
        able.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(AllUserActivity.this, v+"分", Toast.LENGTH_SHORT).show();
            }
        });
        speed.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(AllUserActivity.this, v+"分", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void inView(){
        submit=findViewById(R.id.submit);
        dialog=new ProgressDialog(AllUserActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setMessage("提示");
        dialog.setTitle("保存中");
        able=findViewById(R.id.able);
        speed=findViewById(R.id.speed);
    }
}
