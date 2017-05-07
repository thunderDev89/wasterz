package com.acemen.android.wasterz.view.contribute;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acemen.android.wasterz.R;
import com.acemen.android.wasterz.view.base.MVP;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LocalizationFragment extends Fragment implements Contribute.PagerVisitor, Contribute.View.Step1View {
    @BindView(R.id.btLocalizeMe)
    Button mBtLocalizeMe;

    @BindView(R.id.txtManualAddress)
    EditText mTxtManualAddress;

    @BindView(R.id.btValidateLocalization)
    Button mBtValidateLocalization;

    private Contribute.Presenter.Step1 mPresenter;

    //TODO reference to remove from view. Add it to presenter
    private Contribute.Pager mPager;

    public LocalizationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mPresenter = new LocalizationPresenter(this);
        ((Contribute.PagerVisitor) mPresenter).setPager(mPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_localization, container, false);
        ButterKnife.bind(this, rootView);

        mBtValidateLocalization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goToNextStep();
            }
        });

        mBtLocalizeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.findLocation();
            }
        });

        mPresenter.onCreateView();

        return rootView;
    }

    @Override
    public void showLocation(String location) {
        mTxtManualAddress.setText(location);
    }

    @Override
    public void locationNotFound() {
        Toast.makeText(getContext(), "Location not found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getAddress() {
        return mTxtManualAddress.getText().toString();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        Timber.d("OnResume called");
        mPresenter.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        Timber.d("Onpause called");
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void setPager(Contribute.Pager pager) {
        mPager = pager;
    }

    @Override
    public MVP.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(Contribute.Presenter.Step1 presenter) {
        mPresenter = presenter;
    }
}