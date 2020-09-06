package com.znjt.xufeii.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.znjt.xufeii.R;
import com.znjt.xufeii.adapter.ViewPagerAdapter;
import com.znjt.xufeii.fragment.Fragment_Description_One;
import com.znjt.xufeii.fragment.Fragment_Description_Three;
import com.znjt.xufeii.fragment.Fragment_Description_Two;
import com.znjt.xufeii.fragment.Fragment_Pager3;
import com.znjt.xufeii.fragment.Fragment_Pager5;
import com.znjt.xufeii.fragment.Fragment_Pager6;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/26.
 */

public class PagerActivity extends AppCompatActivity {
    private List<Fragment>list;
    private ViewPager pager;
    private Fragment_Description_Three three;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_layout);
        inView();
        addData();
    }

    private void addData(){
        list.add(new Fragment_Pager6());
        list.add(new Fragment_Description_One());
        list.add(new Fragment_Description_Two());
        list.add(new Fragment_Pager3());
        list.add(new Fragment_Pager5());
        list.add(three);
        pager.setOffscreenPageLimit(list.size());
        pager.setCurrentItem(0);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),list));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void inView(){
        list=new ArrayList<>();
        pager=findViewById(R.id.pager);
        three=new Fragment_Description_Three();
        three.setFragmentChange(new Fragment_Description_Three.FragmentChange() {
            @Override
            public void change() {
                startActivity(new Intent(PagerActivity.this,AddActivity.class));
                finish();
            }
        });
    }
}
