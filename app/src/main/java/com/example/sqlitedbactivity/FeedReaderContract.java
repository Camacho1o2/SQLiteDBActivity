package com.example.sqlitedbactivity;

import android.provider.BaseColumns;

public class FeedReaderContract {

    private FeedReaderContract() {

    }

    public static class FeedEntry implements BaseColumns {
        public static final String USER_TABLE = "user_details";
        public static final String USER_NAME = "user_name";
        public static final String USER_CONTACT = "user_contact_number";
        public static final String USER_ADDRESS = "user_address";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.USER_TABLE + " (" +
                    FeedEntry.USER_NAME + " TEXT PRIMARY KEY," +
                    FeedEntry.USER_CONTACT + " TEXT," +
                    FeedEntry.USER_ADDRESS + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.USER_TABLE;
}