package com.opendata.yu_hsienchou.taiwanair;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class AirSQLiteModel {
    private MyAirDBHelper myAirDBHelper;
    public AirSQLiteModel(Context context){
        myAirDBHelper = new MyAirDBHelper(context,"airdata.db",1,null);
    }

    /**
     * 新增資料並清除所有資訊
     * @param airData
     * @param gps_lat
     * @param gps_lon
     */
    public void insert_clean_all(ArrayList<AirDataModel> airData,double gps_lat, double gps_lon){
        this.clean_all();
        DistanceConvent distanceConvent = new DistanceConvent();
        SQLiteDatabase db = myAirDBHelper.getWritableDatabase();
        for( int i = 0; i <= airData.size(); i++){
            ContentValues values = new ContentValues();
            values.put("name",airData.get(i).getSiteName());
            values.put("gps_lat",airData.get(i).getGpsLatitude());
            values.put("gps_lon",airData.get(i).getGpsLongitude());
            values.put("pm25",airData.get(i).getPm25Value());
            distanceConvent.setDistance(gps_lon,gps_lat,airData.get(i).getGpsLongitude(),airData.get(i).getGpsLatitude());
            values.put("distance",distanceConvent.getKm());
            db.insert("airdata",null,values);
        }
        db.close();
    }

    /**
     * 依照當前距離取得鄰近檢測站
     * @return Cursor
     */
    public Cursor getAdjacent(){
        SQLiteDatabase db = myAirDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM airdata ORDER BY distance  ASC ",null);
        return cursor;
    }
    private void clean_all(){
        SQLiteDatabase db = myAirDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM airdata");
        db.close();
    }

}
