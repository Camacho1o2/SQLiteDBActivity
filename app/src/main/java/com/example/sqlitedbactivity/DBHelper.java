package com.example.sqlitedbactivity;

import static com.example.sqlitedbactivity.FeedReaderContract.FeedEntry.*;
import static com.example.sqlitedbactivity.FeedReaderContract.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, USER_TABLE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public Boolean insertUserData(String name, String contact, String address){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, name);
        contentValues.put(USER_CONTACT, contact);
        contentValues.put(USER_ADDRESS, address);

        long result = db.insert(USER_TABLE, null, contentValues);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Boolean updateUserData(String name, String contact, String address){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_CONTACT, contact);
        contentValues.put(USER_ADDRESS, address);

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME + " = ?", new String[]{name});

        if (cursor.moveToFirst()) {
            long result = db.update(USER_TABLE, contentValues, USER_NAME + " = ?", new String[]{name});
            cursor.close(); // Close the cursor after use

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteUserData(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME + " = ?", new String[]{name});

        if (cursor.moveToFirst()) {
            long result = db.delete(USER_TABLE, USER_NAME + " = ?", new String[]{name});
            cursor.close(); // Close the cursor after use

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE, null);
        return cursor;
    }


}
