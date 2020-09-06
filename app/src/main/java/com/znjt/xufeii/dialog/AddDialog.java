package com.znjt.xufeii.dialog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.znjt.xufeii.R;
import com.znjt.xufeii.sql.SQLiteData;

/**
 * Created by Administrator on 2019/4/28 0028.
 */

@SuppressLint("ValidFragment")
public class AddDialog extends android.support.v4.app.DialogFragment {

    private EditText mEd;
    private Button mAdd;
    private SQLiteData data;
    private Context context;

    public interface SetData{
        void setdata();
    }

    private SetData Data;

    public void setData(SetData data) {
        Data = data;
    }

    public AddDialog(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.add_dialog,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data=new SQLiteData(context,"danci.db",null,1);
        initView();
        initLister();
    }

    private void initLister() {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word=mEd.getText().toString().trim();
                if (word.equals("")){
                    Toast.makeText(context,"添加单词不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                SQLiteDatabase db=data.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("word",word);
                db.insert("study",null,values);
                AddDialog.this.dismiss();
                Data.setdata();
                Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mEd=getView().findViewById(R.id.add_ed);
        mAdd=getView().findViewById(R.id.add_ok);
    }
}
