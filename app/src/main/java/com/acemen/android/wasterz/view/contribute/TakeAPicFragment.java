package com.acemen.android.wasterz.view.contribute;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.acemen.android.wasterz.R;
import com.acemen.android.wasterz.view.base.MVP;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class TakeAPicFragment extends Fragment implements Contribute.View.Step3View {
    @BindView(R.id.iconPhoto)
    ImageView mIconPhoto;

    @BindView(R.id.capturedPhoto)
    ImageView mCapturedPhoto;

    private Contribute.Presenter.Step3 mPresenter;

    public TakeAPicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TakeAPicPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_take_apic, container, false);

        ButterKnife.bind(this, rootView);

        mIconPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.takePhoto();
            }
        });
        return rootView;
    }

    @Override
    public void displayPhoto(String photo) {
        Timber.d("Display photo by string");
        Picasso.with(getContext()).load(Uri.parse(photo)).into(mCapturedPhoto); // TODO try to use Glide instead of Picasso to have rounded images
    }

    @Override
    public void displayPhoto(Bitmap photo) {
        Timber.d("Display photo by Bitmap");
        final RoundedBitmapDrawable roundedPhoto = RoundedBitmapDrawableFactory.create(getResources(), photo);
        roundedPhoto.setCornerRadius(Math.max(photo.getWidth(), photo.getHeight()) / 2.0f);
        mCapturedPhoto.setImageDrawable(roundedPhoto);
//        mCapturedPhoto.setImageBitmap(photo);
    }

    @Override
    public void noPhotoTaken() {
        Toast.makeText(getContext(), "Aucune photo n'a été prise", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorSavingPhoto() {
        Toast.makeText(getContext(), "Erreur lors de la sauvegarde de l'image", Toast.LENGTH_LONG).show();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public MVP.Presenter getPresenter() {
        return mPresenter;
    }
}
