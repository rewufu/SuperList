package com.rewufu.superlist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by Bell on 7/22/15.
 */
public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    private Context context;
    public static final  String CREATE_GOODS = "create table goods (gName varchar(20) primary key, kind varchar(20))";
    public static final  String CREATE_LISTS = "create table lists (list varchar(20))";

    public SQLiteOpenHelper(Context context) {
        super(context, "SuperList.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_GOODS);
        sqLiteDatabase.execSQL(CREATE_LISTS);
        //init database
        String json = "";
        try {
            InputStream inputStream = context.getAssets().open("database/goods.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null){
                stringBuffer.append(str);
            }
            json = stringBuffer.toString();
        } catch (IOException e) {
            Log.e("ReadJson", e.getMessage());
        }
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
        for(Map.Entry<String, String> entry : map.entrySet()){
            sqLiteDatabase.execSQL("insert into goods(gName, kind) values (?, ?);",
                    new Object[]{entry.getKey(), entry.getValue()});
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
