package com.acemen.android.wasterz.view.contribute;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.acemen.android.wasterz.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContributeActivity extends FragmentActivity {

    @BindView(R.id.contributeFragmentPager)
    ViewPager mViewPager;

    private ContributePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);

        ButterKnife.bind(this);

        mPagerAdapter = new ContributePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.setPager((Contribute.Pager) mViewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
    }
}
