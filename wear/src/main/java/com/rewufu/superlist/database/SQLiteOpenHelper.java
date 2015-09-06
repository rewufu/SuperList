package com.rewufu.superlist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Bell on 8/31/15.
 */
public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    private Context context;
    public static final  String CREATE_LIST_ITEM = "create table list_item (gName varchar(20), bought varchar(10), list varchar(20))";


    public SQLiteOpenHelper(Context context) {
        super(context, "SuperList_Wear.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LIST_ITEM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

