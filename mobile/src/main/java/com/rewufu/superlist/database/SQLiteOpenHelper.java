package com.rewufu.superlist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Bell on 7/22/15.
 */
public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    public static final  String CREATE_GOODS = "create table goods (gName varchar(20) primary key, kind varchar(20))";
    public static final  String CREATE_LISTS = "create table lists (list varchar(20))";

    public SQLiteOpenHelper(Context context) {
        super(context, "SuperList.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_GOODS);
        sqLiteDatabase.execSQL(CREATE_LISTS);
        //init database
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
