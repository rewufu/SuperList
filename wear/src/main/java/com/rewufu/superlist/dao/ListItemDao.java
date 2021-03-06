package com.rewufu.superlist.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.rewufu.superlist.entity.Goods;

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

    public ArrayList<Goods> queryItemByList(String list) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from list_item where list = ?;",
                    new String[]{list});
            if (cursor != null && cursor.getCount() > 0) {
                ArrayList<Goods> itemList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    String bought = cursor.getString(1);
                    itemList.add(new Goods(name, bought, list));
                }
                db.close();
                return itemList;
            }
            db.close();
            return null;
        }
        return null;
    }

    public ArrayList<String> queryListByName(String name) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select list from list_item where gName = ?;",
                    new String[]{name});
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String listName = cursor.getString(0);
                    list.add(listName);
                }
                db.close();
                return list;
            }
            db.close();
            return list;
        }
        return list;
    }

    public ArrayList<String> queryGoods() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ArrayList<String> nameList = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select gName from list_item;", null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String gName = cursor.getString(0);
                    nameList.add(gName);
                }
                db.close();
                return nameList;
            }
            db.close();
            return nameList;
        }
        return nameList;
    }

    public ArrayList<String> queryList() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        ArrayList<String> nameList = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select distinct list from list_item;", null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String listName = cursor.getString(0);
                    nameList.add(listName);
                }
                db.close();
                return nameList;
            }
            db.close();
            return nameList;
        }
        return nameList;
    }

    public ArrayList<Goods> queryAll() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from list_item;", null);
            if (cursor != null && cursor.getCount() > 0) {
                ArrayList<Goods> goodsList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    String bought = cursor.getString(1);
                    String list = cursor.getString(2);
                    if (TextUtils.equals("t", bought)) {
                        goodsList.add(new Goods(name, "true", list));
                    } else {
                        goodsList.add(new Goods(name, "false", list));
                    }
                }
                db.close();
                return goodsList;
            }
            db.close();
            return null;
        }
        return null;
    }


    public void updateItemBought(String bought, String name, String list) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("update list_item set bought = ? where (gName = ?) and (list = ?);",
                    new String[]{bought, name, list});
            db.close();
        }
    }

    public void insertListItem(Goods item) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("insert into list_item(gName, bought, list) values (?, ?, ?);",
                    new String[]{item.getName(), item.getBought(), item.getListName()});
            db.close();
        }
    }


}
