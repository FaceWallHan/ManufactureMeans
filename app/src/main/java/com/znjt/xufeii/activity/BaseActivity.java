package com.znjt.xufeii.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.znjt.xufeii.R;
import com.znjt.xufeii.bean.DataSimple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/26.
 */

public class BaseActivity extends AppCompatActivity {
    private static List<Activity> list=new ArrayList<>();
    private static long time=0;
    private SlidingMenu menu;
    private SimpleAdapter adapter;
    private List<Map<String,Object>>mapList;
    public  void left(){
        menu=new SlidingMenu(this);
        menu.setFadeDegree(0.35f);
        menu.setMode(SlidingMenu.LEFT);
        menu.setMenu(R.layout.menu);
        menu.setBehindWidth(400);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.attachToActivity(this,SlidingMenu.SLIDING_WINDOW);
        ListView lv=menu.findViewById(R.id.lv);
        mapList=new ArrayList<>();
        List<DataSimple> simpleList=new ArrayList<>();
        simpleList.add(new DataSimple("添加",R.drawable.add11));
        simpleList.add(new DataSimple("听写",R.drawable.test11));
        simpleList.add(new DataSimple("检查",R.drawable.test21));
        simpleList.add(new DataSimple("其他",R.drawable.test31));
        simpleList.add(new DataSimple("评价",R.drawable.test41));
        /**simpleList.add(new DataSimple("以往错题",R.drawable.btn_l_skype));
        simpleList.add(new DataSimple("错题分析",R.drawable.btn_l_target));
        simpleList.add(new DataSimple("开发人员",R.drawable.btn_l_twitter));**/
        for (int i = 0; i < simpleList.size(); i++) {
            Map<String,Object>map=new HashMap<>();
            map.put("name",simpleList.get(i).getName());
            map.put("image",simpleList.get(i).getImage());
            mapList.add(map);
        }
        adapter=new SimpleAdapter(this,mapList,R.layout.base_item_layout,new String[]{"name","image"},new int[]{R.id.name,R.id.image});
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(BaseActivity.this,AddActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(BaseActivity.this,TtsDemo.class));
                        break;
                    case 2:
                        startActivity(new Intent(BaseActivity.this,AllCheckActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(BaseActivity.this,AllOtherActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(BaseActivity.this,AllUserActivity.class));
                        break;
                }
            }
        });
    }
    public static void addActivity(Activity activity){
        list.add(activity);
    }
    public void setText(String text){
        TextView content=findViewById(R.id.content);
        content.setText(text);
        ImageView change=findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.showMenu();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-time>3000){
            time=System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Activity activity=list.get(i);
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
