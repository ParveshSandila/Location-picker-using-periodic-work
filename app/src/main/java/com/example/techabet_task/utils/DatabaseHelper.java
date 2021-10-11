package com.example.techabet_task.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.techabet_task.models.LocationDataModel;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "techabet.db";
    private static final String TABLE_NAME = "LOCATION_TABLE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (TIME TEXT, LON TEXT, LAT TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public boolean insertData(String time, String lon, String lat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TIME", time);
        cv.put("LON", lon);
        cv.put("LAT", lat);

        long result = db.insert(TABLE_NAME, null, cv);

        return result != -1;
    }

    public List<LocationDataModel> getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME ;
        Cursor cursor=db.rawQuery(query, null);

        List<LocationDataModel> list=new ArrayList<>();
        list.clear();
        while (cursor.moveToNext()){
            list.add(new LocationDataModel(cursor.getString(cursor.getColumnIndex("TIME")),cursor.getString(cursor.getColumnIndex("LON")),cursor.getString(cursor.getColumnIndex("LAT"))));
        }

        return list;
    }
}







