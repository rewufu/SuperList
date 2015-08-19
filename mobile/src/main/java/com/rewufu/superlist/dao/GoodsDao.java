package com.rewufu.superlist.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Bell on 7/22/15.
 */
public class GoodsDao {
    private SQLiteOpenHelper openHelper;
    private Context context;

    public GoodsDao(Context context) {
        this.context = context;
        openHelper = new com.rewufu.superlist.database.SQLiteOpenHelper(context);
    }

    //query goods
    public ArrayList<String> queryGoods() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select gName from goods;", null);
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

    public ArrayList<String> queryGoodsByKind(String kind) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select gName from goods where kind = ?;", new String[]{kind});
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


    public ArrayList<String> queryKinds() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select distinct kind from goods;", null);
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



}
