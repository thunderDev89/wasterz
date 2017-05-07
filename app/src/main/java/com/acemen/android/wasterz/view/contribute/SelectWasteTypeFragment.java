package com.acemen.android.wasterz.view.contribute;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.acemen.android.wasterz.R;
import com.acemen.android.wasterz.view.base.MVP;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectWasteTypeFragment extends Fragment implements Contribute.View.Step2View, Contribute.PagerVisitor {
    @BindView(R.id.btValidateType)
    Button mBtValidateType;

    @BindView(R.id.wasteType1)
    ImageView mWasteType1;
    @BindView(R.id.wasteType2)
    ImageView mWasteType2;
    @BindView(R.id.wasteType3)
    ImageView mWasteType3;
    @BindView(R.id.wasteType4)
    ImageView mWasteType4;
    @BindView(R.id.wasteType5)
    ImageView mWasteType5;
    @BindView(R.id.wasteType6)
    ImageView mWasteType6;

    private Contribute.Presenter.Step2 mPresenter;
    private Contribute.Pager mPager;

    public SelectWasteTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPresenter = new SelectWasteTypePresenter(this);
        ((Contribute.PagerVisitor) mPresenter).setPager(mPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_waste_type, container, false);

        ButterKnife.bind(this, rootView);
        mBtValidateType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goToNextStep();
            }
        });

        final View.OnClickListener listener = new WasteTypeClickListener();

        mWasteType1.setOnClickListener(listener);
        mWasteType2.setOnClickListener(listener);
        mWasteType3.setOnClickListener(listener);
        mWasteType4.setOnClickListener(listener);
        mWasteType5.setOnClickListener(listener);
        mWasteType6.setOnClickListener(listener);

        return rootView;
    }

    public void onWasteTypeSelected(View v) {
        mPresenter.selectWasteType(v.getTag().toString());
        Toast.makeText(getContext(), v.getTag().toString() + " selected", Toast.LENGTH_SHORT).show();
    }

    public void newMethod(View v) {
        Toast.makeText(getContext(), " selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPager(Contribute.Pager pager) {
        mPager = pager;
    }

    @Override
    public void noTypeSelected() {
        Toast.makeText(getContext(), "Please select one type before continue", Toast.LENGTH_SHORT).show();
    }

    @Override
    public MVP.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(Contribute.Presenter.Step2 presenter) {
        mPresenter = presenter;
    }

    private class WasteTypeClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            onWasteTypeSelected(view);
        }
    }

    @Override
    public void onResume() {
        mPresenter.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mPresenter.onPause();
        super.onPause();
    }
}
