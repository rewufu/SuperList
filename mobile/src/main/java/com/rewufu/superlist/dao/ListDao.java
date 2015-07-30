package com.rewufu.superlist.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Bell on 7/22/15.
 */
public class ListDao {
    private SQLiteOpenHelper openHelper;
    private Context context;

    public ListDao(Context context) {
        this.context = context;
        openHelper = new com.rewufu.superlist.database.SQLiteOpenHelper(context);
    }

    //query lists
    public ArrayList<String> queryLists() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select list from lists;", null);
            if (cursor != null && cursor.getCount() > 0) {
                ArrayList<String> lists = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String list = cursor.getString(0);
                    lists.add(list);
                }
                db.close();
                return lists;
            }
            db.close();
            return null;
        }
        return null;
    }

    public void insertLists(String list) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("insert into lists(list) values (?);",
                    new Object[]{list});
            db.close();
        }
    }

}
