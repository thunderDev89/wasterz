package com.acemen.android.wasterz.view.contribute;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.acemen.android.wasterz.MyApplication;
import com.acemen.android.wasterz.domain.AbstractUsecase;
import com.acemen.android.wasterz.domain.contribute.usecase.AddWaste;
import com.acemen.android.wasterz.domain.model.Waste;
import com.acemen.android.wasterz.executor.UsecaseHandler;
import com.acemen.android.wasterz.repository.remote.RemoteWastesRepository;
import com.acemen.android.wasterz.view.base.MVP;
import com.acemen.android.wasterz.view.contribute.Contribute.PagerVisitor;
import com.acemen.android.wasterz.view.model.WasteHolder;
import com.acemen.android.wasterz.view.overview.OverviewActivity;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

/**
 * Created by Audrik ! on 22/03/2017.
 */

public class ContributePagerAdapter extends FragmentStatePagerAdapter implements Contribute.Pager, PagerVisitor, ViewPager.OnPageChangeListener {
    private static final int NB_PAGES = 3; //TODO Delete from this class because not used

    private LocalizationFragment mLocalizationFragment;
    private SelectWasteTypeFragment mSelectWasteTypeFragment;
    private TakeAPicFragment mTakeAPicFragment;
    private Contribute.Pager mPager;
    private WasteHolder mWasteHolder;
    private static final Map<Integer, MVP.View> FRAGMENTS = new HashMap<>(NB_PAGES);

    public ContributePagerAdapter(FragmentManager fm) {
        super(fm);
        mWasteHolder = new WasteHolder();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mLocalizationFragment == null) {
                    mLocalizationFragment = new LocalizationFragment();

                    LocalizationPresenter mLocalizationPresenter = new LocalizationPresenter(
                            mLocalizationFragment, mWasteHolder);
                    mLocalizationFragment.setPager(this);
                    FRAGMENTS.put(Contribute.LOCALIZATION_FRAGMENT_POSITION, mLocalizationFragment);
                }
                return mLocalizationFragment;
            case 1:
                if (mSelectWasteTypeFragment == null) {
                    mSelectWasteTypeFragment = new SelectWasteTypeFragment();

                    SelectWasteTypePresenter mWasteTypePresenter = new SelectWasteTypePresenter(
                            mSelectWasteTypeFragment, mWasteHolder);
//                    mSelectWasteTypeFragment.setPager(mPager);
                    mSelectWasteTypeFragment.setPager(this);
                    FRAGMENTS.put(Contribute.SELECT_WASTE_TYPE_FRAGMENT_POSITION, mSelectWasteTypeFragment);
                }
                return mSelectWasteTypeFragment;
            case 2:
                if (mTakeAPicFragment == null) {
                    mTakeAPicFragment = new TakeAPicFragment();

                    TakeAPicPresenter mTakeAPicPresnter = new TakeAPicPresenter(mTakeAPicFragment,
                            mWasteHolder);
                    mTakeAPicFragment.setPager(this);
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
//        if (NB_PAGES > position) {
//            mPager.next(position);
//        }
        if (NB_PAGES == position) {
            sendWaste();
        } else {
            mPager.next(position);
        }
//        throw new IllegalStateException("Method should not be called here");
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

    private void sendWaste() {
        final Waste waste = loadWasteFromPreferences();

        Toast.makeText(MyApplication.getAppContext(), "Envoi du déchêt en cours...", Toast.LENGTH_LONG).show();

        UsecaseHandler.getInstance().executeUsecase(
                new AddWaste(RemoteWastesRepository.getInstance()),
                new AddWaste.Request(waste),
                new AbstractUsecase.UsecaseCallback<AddWaste.Response>() {
                    @Override
                    public void onSuccess(AddWaste.Response response) {
                        Toast.makeText(MyApplication.getAppContext(), "Déchêt envoyé !!!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MyApplication.getAppContext(),
                                OverviewActivity.class);

                        MyApplication.getAppContext().startActivity(intent);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(MyApplication.getAppContext(), "Erreur lors de l'envoi de déchêt", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private Waste loadWasteFromPreferences() {
        Timber.d(mWasteHolder.toString());
        return mWasteHolder.getWaste();
    }
}
