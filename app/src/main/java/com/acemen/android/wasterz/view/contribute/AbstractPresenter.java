package com.acemen.android.wasterz.view.contribute;

import com.acemen.android.wasterz.view.model.WasteHolder;

/**
 * Created by Audrik ! on 01/05/2017.
 */

public class AbstractPresenter {
    private WasteHolder mWasteHolder;

    public AbstractPresenter(WasteHolder wasteHolder) {
        mWasteHolder = wasteHolder;
    }

    public WasteHolder getWasteHolder() {
        return mWasteHolder;
    }
}
