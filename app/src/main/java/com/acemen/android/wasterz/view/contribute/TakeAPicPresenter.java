package com.acemen.android.wasterz.view.contribute;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.acemen.android.wasterz.R;
import com.acemen.android.wasterz.view.model.WasteHolder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by Audrik ! on 21/03/2017.
 */

public class TakeAPicPresenter extends AbstractPresenter implements Contribute.Presenter.Step3, Contribute.PagerVisitor {
    private static final int REQUEST_IMAGE_CAPTURE = 0326;
    private static final String EXTRA_CAPTURE_FILE = "com.acemen.android.captureExtra";

    private Contribute.Pager mPager;

    private Contribute.View.Step3View mView;

    private String mCurrentPhotoPath;

    private boolean onPause = false;

    public TakeAPicPresenter(Contribute.View.Step3View view, WasteHolder wasteHolder) {
        super(wasteHolder);
        attachView(view);
    }

    @Override
    public void attachView(Contribute.View.Step3View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void setPager(Contribute.Pager pager) {
        mPager = pager;
    }

    @Override
    public void takePhoto() {
        // Call camera app
        if (mCurrentPhotoPath != null) {
            deletePhoto();
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mView.getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Timber.e("Error when saving photo", e);
                mView.errorSavingPhoto();
            }
            if (photoFile != null) {
                final Uri photoURI = FileProvider.getUriForFile(
                        mView.getContext(),
                        mView.getContext().getString(R.string.app_file_provider),
                        photoFile
                );
                takePictureIntent.putExtra(EXTRA_CAPTURE_FILE, photoURI);
                mView.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                mView.noPhotoTaken();
            }
        }
    }

    private File createImageFile() throws IOException {
        final String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        final String photoFileName = "WASTE_" + timeStamp + "_";
        final File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ mView.getContext().getString(R.string.pics_dir_path));
        if (!storageDir.exists())
            storageDir.mkdirs();
        final File image = File.createTempFile(
                photoFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        Timber.d("File created at '" + mCurrentPhotoPath + "'");
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE
                && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                final Bundle extras = data.getExtras();
                mView.displayPhoto((Bitmap) extras.get("data"));
            } else {
                mView.displayPhoto("file:" + mCurrentPhotoPath);
            }
        }
    }

    @Override
    public void deletePhoto() {
        final File picFile = new File(mCurrentPhotoPath);
        picFile.delete();

        Timber.d("File '" + mCurrentPhotoPath + "' successfully deleted");
        mCurrentPhotoPath = null;
    }

    @Override
    public void goToNextStep() {
        Timber.d("Go to next step of pic step");
        if (mCurrentPhotoPath != null) {
            mPager.next(Contribute.TAKE_A_PIC_FRAGMENT_POSITION);
        } else {
            mView.noPhotoTaken();
        }
    }

    @Override
    public void onResume() {
        Timber.d("OnResume called");
//        mCurrentPhotoPath = getWasteHolder().setFilename();
//        Utils.getStringPreferences(mView.getContext(), Contribute.FILENAME_PARAM, Contribute.PREFS_NAME);
        onPause = false;
    }

    @Override
    public void onPause() {
        Timber.d("OnPause called");
        if (!onPause) {
            getWasteHolder().setFilename(mCurrentPhotoPath);
//            Utils.setStringPreferences(mView.getContext(), Contribute.FILENAME_PARAM, mCurrentPhotoPath, Contribute.PREFS_NAME);
            onPause = true;
        }
    }
}
