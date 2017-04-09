package com.acemen.android.wasterz.view.base;

import android.content.Context;

/**
 * Created by Audrik ! on 19/03/2017.
 */

public interface MVP {
    interface View {
        Context getContext();

        Presenter getPresenter();
    }

    interface Model{}

    interface Presenter<V> {
        public void attachView(V view);

        void onResume();

        void onPause();
    }
}
