package com.example.sqlitedbactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager {

    private static final String TAG = "DatabaseManager";
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // CRUD operations with logging
    public void createRecord(String name, String email) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        long newRowId = db.insert("users", null, values);
        Log.i(TAG, "Record inserted with ID: " + newRowId);
    }

    public Cursor readRecords() {
        Cursor cursor = db.query("users", null, null, null, null, null, null);
        Log.i(TAG, "Records retrieved: " + cursor.getCount());
        return cursor;
    }

    // Implement updateRecord() and deleteRecord() similarly

}
