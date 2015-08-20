package com.rewufu.superlist.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Bell on 7/22/15.
 */
public class ListItemDao {
    private SQLiteOpenHelper openHelper;
    private Context context;

    public ListItemDao(Context context) {
        this.context = context;
        openHelper = new com.rewufu.superlist.database.SQLiteOpenHelper(context);
    }

    public ArrayList<String> queryItemByList(String list) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select gName from list_item where list = ?;",
                    new String[]{list});
            if (cursor != null && cursor.getCount() > 0) {
                ArrayList<String> itemList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String item = cursor.getString(0);
                    itemList.add(item);
                }
                db.close();
                return itemList;
            }
            db.close();
            return null;
        }
        return null;
    }

    public void insertListItem(String item, String list) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("insert into list_item(gName, list) values (?, ?);",
                    new String[]{item, list});
            db.close();
        }
    }

    public void deleteItem(String item) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("delete from list_item where gName = ?;",
                    new String[]{item});
            db.close();
        }
    }

}
