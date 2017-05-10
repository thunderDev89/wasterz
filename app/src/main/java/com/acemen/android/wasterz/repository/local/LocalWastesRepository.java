package com.acemen.android.wasterz.repository.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.acemen.android.wasterz.domain.WasteDataSource;
import com.acemen.android.wasterz.domain.model.Waste;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Audrik ! on 09/05/2017.
 */

public class LocalWastesRepository implements WasteDataSource {
    private static LocalWastesRepository INSTANCE;

    private WasterzDbHelper mDbHelper;


    private LocalWastesRepository(Context context) {
        mDbHelper = new WasterzDbHelper(context);
    }

    public static LocalWastesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalWastesRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void addWaste(@NonNull Waste newWaste,
            @NonNull AddWasteCallback callback) {
        try {
            insertWaste(newWaste);
            callback.onWasteAdded("Waste added to local db");
        } catch (Exception e) {
            Timber.e(e);
            callback.onDataNotAvailable();
        }
    }

    public void insertWaste(Waste waste) throws Exception {
        final ContentValues cv = new ContentValues();
        cv.put(WasterzContract.WasteEntry.COLUMN_REMOTE_ID, waste.getRemoteId());
        cv.put(WasterzContract.WasteEntry.COLUMN_ADDRESS, waste.getAddress());
        cv.put(WasterzContract.WasteEntry.COLUMN_FIlENAME, waste.getCaptureFilename());
        cv.put(WasterzContract.WasteEntry.COLUMN_TYPE, waste.getType());
        cv.put(WasterzContract.WasteEntry.COLUMN_COORD_LAT, waste.getLatitude());
        cv.put(WasterzContract.WasteEntry.COLUMN_COORD_LONG, waste.getLongitude());
        cv.put(WasterzContract.WasteEntry.COLUMN_CREATION_DATE, System.currentTimeMillis());

        mDbHelper.getWritableDatabase().insert(WasterzContract.WasteEntry.TABLE_NAME, null, cv);
    }

    @Override
    public void loadWastes(@NonNull LoadWastesCallback callback) {
        try {
            final List<Waste> wastes = getAllWastes();
            int cnt = wastes.size();
            if (cnt == 0) {
                callback.onDataNotAvailable();
            } else {
                callback.onWastesLoaded(wastes);
            }

        } catch (Exception e) {
            Timber.e(e);
            callback.onDataNotAvailable();
        }
    }

    public List<Waste> getAllWastes() {
        final String[] projection = {
                WasterzContract.WasteEntry._ID,
                WasterzContract.WasteEntry.COLUMN_REMOTE_ID,
                WasterzContract.WasteEntry.COLUMN_ADDRESS,
                WasterzContract.WasteEntry.COLUMN_FIlENAME,
                WasterzContract.WasteEntry.COLUMN_TYPE,
                WasterzContract.WasteEntry.COLUMN_COORD_LONG,
                WasterzContract.WasteEntry.COLUMN_COORD_LAT,
                WasterzContract.WasteEntry.COLUMN_CREATION_DATE
        };
        final String orderBy = WasterzContract.WasteEntry.COLUMN_CREATION_DATE + " desc";

        final Cursor cursor = mDbHelper.getReadableDatabase().query(
                WasterzContract.WasteEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy
        );

        final List<Waste> wastes = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            wastes.add(getWasteFromCursor(cursor));
        }

        return wastes;
    }

    private Waste getWasteFromCursor(Cursor cursor) {
        final Waste waste = new Waste();

        waste.setId(cursor.getLong(cursor.getColumnIndex(WasterzContract.WasteEntry._ID)));
        waste.setRemoteId(cursor.getString(
                cursor.getColumnIndex(WasterzContract.WasteEntry.COLUMN_REMOTE_ID)));
        waste.setAddress(
                cursor.getString(cursor.getColumnIndex(WasterzContract.WasteEntry.COLUMN_ADDRESS)));
        waste.setCaptureFilename(cursor.getString(
                cursor.getColumnIndex(WasterzContract.WasteEntry.COLUMN_FIlENAME)));
        waste.setType(
                cursor.getString(cursor.getColumnIndex(WasterzContract.WasteEntry.COLUMN_TYPE)));
        waste.setLatitude(cursor.getDouble(
                cursor.getColumnIndex(WasterzContract.WasteEntry.COLUMN_COORD_LAT)));
        waste.setLongitude(cursor.getDouble(
                cursor.getColumnIndex(WasterzContract.WasteEntry.COLUMN_COORD_LONG)));
        waste.setCreationDate(cursor.getLong(
                cursor.getColumnIndex(WasterzContract.WasteEntry.COLUMN_CREATION_DATE)));

        return waste;
    }
}
