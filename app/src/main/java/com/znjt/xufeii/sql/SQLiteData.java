package com.znjt.xufeii.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2019/4/28 0028.
 */

public class SQLiteData extends SQLiteOpenHelper {

    private static final String DATA_SQL2="create table study("
            +"id integer primary key autoincrement,"
            +"word text)";

    public SQLiteData(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATA_SQL2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
