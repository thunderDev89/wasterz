package com.acemen.android.wasterz.view.contribute;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.view.ViewPager;

import com.acemen.android.wasterz.view.base.MVP;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Audrik ! on 19/03/2017.
 */

public interface Contribute {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            LOCALIZATION_FRAGMENT_POSITION,
            SELECT_WASTE_TYPE_FRAGMENT_POSITION,
            TAKE_A_PIC_FRAGMENT_POSITION
    })
    @interface FragmentPosition{}
    int LOCALIZATION_FRAGMENT_POSITION = 1;
    int SELECT_WASTE_TYPE_FRAGMENT_POSITION = 2;
    int TAKE_A_PIC_FRAGMENT_POSITION = 3;

    String PREFS_NAME = "com.acemen.wasterz.MyPrefs";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            ADDRESS_PARAM,
            TYPE_PARAM,
            FILENAME_PARAM
    })
    @interface WasteParam{}
    String ADDRESS_PARAM = "address";
    String TYPE_PARAM = "wasteType";
    String FILENAME_PARAM = "filename";

    interface PagerVisitor {
        void setPager(Pager pager);
    }

    interface Pager {
        void next(int position);

        void addOnPageChangeListener(ViewPager.OnPageChangeListener listener);
    }

    interface View {
        interface Step1View extends MVP.View { // Geolocation of waste
            Activity getActivity();

            void showLocation(String location);

            void locationNotFound();

            String getAddress();

            void showErrorMessage(String message);
        }

        interface Step2View extends MVP.View { // Select waste type
            void noTypeSelected();
        }

        interface Step3View extends MVP.View { // Take photo
            void displayPhoto(String photo);

            void displayPhoto(Bitmap photo);

            void noPhotoTaken();

            void errorSavingPhoto();

            /** Used by camera feature purpose */
            void startActivityForResult(Intent intent, int requestCode);
        }
    }

    interface Presenter {
        interface Step1 extends MVP.Presenter<View.Step1View> { // Geolocation of waste
            void findLocation();

            void goToNextStep();

            void onStart();

            void onStop();

            void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
        }

        interface Step2 extends MVP.Presenter<View.Step2View> { // Select waste type
            void selectWasteType(String type);

            void goToNextTep();
        }

        interface Step3 extends MVP.Presenter<View.Step3View> { // Select photo
            void takePhoto();

            void onActivityResult(int requestCode, int resultCode, Intent data);

            void deletePhoto();

            void goToNextStep();
        }
    }
}
