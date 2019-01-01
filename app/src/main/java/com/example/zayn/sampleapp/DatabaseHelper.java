package com.example.zayn.sampleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "person.db";
    public static final String TABLE_NAME = "persons";
    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "password";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME+ "(id integer primary key, name text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String id, String name, String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,password);

        long res = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (res == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }
}
