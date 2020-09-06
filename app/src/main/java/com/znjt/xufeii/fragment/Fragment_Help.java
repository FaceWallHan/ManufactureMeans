package com.znjt.xufeii.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.znjt.xufeii.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/5/5.
 */

public class Fragment_Help extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_layout,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView helpful=getView().findViewById(R.id.helpful);
        List<String>list=new ArrayList<>();
        list.add("1.面向的用户");
        list.add("        "+"该软件适用于全国小学生的学校、家长、学生，尤其适用于工作繁忙，没有时间给宝宝听写的家长。");
        list.add("2.词库数据");
        list.add("        "+"您可以使用课本配套词库（内置人教版1至6年级）");
        list.add("        "+"您可以手动添加单词（自己不熟不会的单词重点记忆）");
        list.add("        "+"您可以拍照添加单词（方便用户单词的添加）");
        list.add("3.运行环境");
        list.add("        "+"适用于安卓版本4.0以上");
        list.add("4.功能介绍");
        list.add("        "+"语音唤醒:省去繁杂的找软件，打开软件的时间");
        list.add("        "+"人机交互：可纯语音交互，解放双手，省去繁杂的手工操作，富有趣味性的交流，让听写不再是一种负担，让孩子爱上学习");
        list.add("        "+"自动检测结果：人工智能检测听写结果，解放家长、孩子的时间，智能判断对错");
        list.add("5.运行说明");
        list.add("        "+"使用本软件前，请先开启网络和对应权限，如录音权限，拍照权限等。否则可能导致部分功能无法使用");
        list.add("6.语音命令一览");
        list.add("        "+"1）可以/ ok /开始 ：开始语音单词听写");
        list.add("        "+"2）下一个/继续：开始听写下一个单词");
        list.add("        "+"3）暂停/暂停吧/继续/继续吧/取消/取消吧：暂时停止听写");
        list.add("        "+"4）好的，检查吧/检查吧/检查：开始检查听写结果");
        list.add("        "+"5）不用了，一会儿再检查：跳过检差环节");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
        helpful.setAdapter(adapter);
    }
}
