package com.acemen.android.wasterz.view.contribute;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.acemen.android.wasterz.view.base.MVP;
import com.acemen.android.wasterz.view.contribute.Contribute.PagerVisitor;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by Audrik ! on 22/03/2017.
 */

public class ContributePagerAdapter extends FragmentStatePagerAdapter implements Contribute.Pager, PagerVisitor, ViewPager.OnPageChangeListener {
    public static final int NB_PAGES = 3;

    private LocalizationFragment mLocalizationFragment;
    private SelectWasteTypeFragment mSelectWasteTypeFragment;
    private TakeAPicFragment mTakeAPicFragment;
    private Contribute.Pager mPager;
    private static final Map<Integer, MVP.View> FRAGMENTS = new HashMap<>(NB_PAGES);

    public ContributePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mLocalizationFragment == null) {
                    mLocalizationFragment = new LocalizationFragment();
                    mLocalizationFragment.setPager(mPager);
                    Timber.d("mLocalizationFragment initialized");
                    FRAGMENTS.put(Contribute.LOCALIZATION_FRAGMENT_POSITION, mLocalizationFragment);
                }
                return mLocalizationFragment;
            case 1:
                if (mSelectWasteTypeFragment == null) {
                    mSelectWasteTypeFragment = new SelectWasteTypeFragment();
                    mSelectWasteTypeFragment.setPager(mPager);
                    Timber.d("mSelectWasteTypeFragment initialized");
                    FRAGMENTS.put(Contribute.SELECT_WASTE_TYPE_FRAGMENT_POSITION, mSelectWasteTypeFragment);
                }
                return mSelectWasteTypeFragment;
            case 2:
                if (mTakeAPicFragment == null) {
                    mTakeAPicFragment = new TakeAPicFragment();
                    Timber.d("mTakeAPicFragment initialized");
                    FRAGMENTS.put(Contribute.TAKE_A_PIC_FRAGMENT_POSITION, mTakeAPicFragment);
                }
                return mTakeAPicFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NB_PAGES;
    }

    @Override
    public void next(int position) {
        if (NB_PAGES > position)
        mPager.next(position);
    }

    @Override
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mPager.addOnPageChangeListener(listener);
    }

    @Override
    public void setPager(Contribute.Pager pager) {
        mPager = pager;

        mPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageSelected(int position) {
        position++;

        Timber.d("OnPageSelected");
        for (Map.Entry<Integer, MVP.View> entry : FRAGMENTS.entrySet()) {
            if (entry.getKey() == position) {
                entry.getValue().getPresenter().onResume();
            } else {
                entry.getValue().getPresenter().onPause();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
}
