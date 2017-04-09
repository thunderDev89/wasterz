package com.acemen.android.wasterz.domain;

import android.support.annotation.NonNull;

import com.acemen.android.wasterz.domain.model.Waste;

import java.util.List;

/**
 * Created by Audrik ! on 05/04/2017.
 */

public interface WasteDataSource {
    interface LoadWastesCallback {
        void onWastesLoaded(List<Waste> wastes);

        void onDataNotAvailable();
    }

    interface AddWasteCallback {
        void onWasteAdded(String msg);

        void onDataNotAvailable();
    }


    void addWaste(@NonNull Waste newWaste, @NonNull AddWasteCallback callback);

    void loadWastes(@NonNull LoadWastesCallback callback);
}
