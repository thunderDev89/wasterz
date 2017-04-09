package com.acemen.android.wasterz.view.contribute;

import com.acemen.android.wasterz.view.contribute.utils.Utils;

import timber.log.Timber;

/**
 * Created by Audrik ! on 23/03/2017.
 */

public class SelectWasteTypePresenter implements Contribute.Presenter.Step2, Contribute.PagerVisitor {
    private Contribute.View.Step2View mView;
    private Contribute.Pager mPager;
    private String mWasteType;
    private boolean onPause = false;

    public SelectWasteTypePresenter(Contribute.View.Step2View mView) {
        attachView(mView);
    }

    @Override
    public void attachView(Contribute.View.Step2View view) {
        mView = view;
    }

    @Override
    public void setPager(Contribute.Pager pager) {
        mPager = pager;
    }

    @Override
    public void selectWasteType(String type) {
        if (type != null)
            mWasteType = type;
        else
            mView.noTypeSelected();
    }

    @Override
    public void goToNextTep() {
        if (mWasteType != null)
            mPager.next(Contribute.SELECT_WASTE_TYPE_FRAGMENT_POSITION);
        else
            mView.noTypeSelected();
    }

    @Override
    public void onResume() {
        Timber.d("OnResume Called");
        mWasteType = Utils.getStringPreferences(mView.getContext(), Contribute.TYPE_PARAM, Contribute.PREFS_NAME);
        onPause = true;
    }

    @Override
    public void onPause() {
        Timber.d("OnPause Called");
        if (!onPause) {
            Utils.setStringPreferences(mView.getContext(), Contribute.TYPE_PARAM, mWasteType, Contribute.PREFS_NAME);
            onPause = true;
        }
    }
}
