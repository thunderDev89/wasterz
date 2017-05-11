package com.acemen.android.wasterz.repository.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import timber.log.Timber;

/**
 * Created by Audrik ! on 09/05/2017.
 */

public class WasterzDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "wasterzz.db";

    public WasterzDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(WasterzContract.WasteEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Timber.w("Upgrade from version %d to %d", oldVersion, newVersion);
        sqLiteDatabase.execSQL(WasterzContract.WasteEntry.SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
