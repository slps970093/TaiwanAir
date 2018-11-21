package com.opendata.yu_hsienchou.taiwanair;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyAirDBHelper extends SQLiteOpenHelper {

    private String db_create = "create table airdata ("+
            "( id INTEGER PRIMARY KEY  NOT NULL,"+
            " name varchar  not null,"+
            " gps_lat double default 0 ,"+
            " gps_lon double default 0,"+
            " pm25 double default 0,"+
            " distance double default 0)";


    public MyAirDBHelper(Context context, String name, int version, SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.db_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
