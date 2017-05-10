package com.acemen.android.wasterz.repository.local;

import android.provider.BaseColumns;

/**
 * Created by Audrik ! on 09/05/2017.
 */

public class WasterzContract {

    private WasterzContract() {}

    public static class WasteEntry implements BaseColumns {
        public static final String TABLE_NAME = "waste";

        public static final String COLUMN_REMOTE_ID = "remote_id";
        public static final String COLUMN_FIlENAME = "filename";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LONG = "coord_long";
        public static final String COLUMN_CREATION_DATE = "creation_date";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_REMOTE_ID + " INTEGER, " +
                        COLUMN_FIlENAME + " TEXT NOT NULL, " +
                        COLUMN_ADDRESS + " TEXT NOT NULL, " +
                        COLUMN_TYPE + " TEXT NOT NULL, " +
                        COLUMN_COORD_LAT + " REAL NOT NULL, " +
                        COLUMN_COORD_LONG + " REAL NOT NULL, " +
                        COLUMN_CREATION_DATE + " NUMERIC NOT NULL" +
                        ")";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
