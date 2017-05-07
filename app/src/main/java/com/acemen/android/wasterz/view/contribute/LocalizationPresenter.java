package com.acemen.android.wasterz.view.contribute;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.acemen.android.wasterz.view.model.WasteHolder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Audrik ! on 19/03/2017.
 */

public class LocalizationPresenter extends AbstractPresenter implements Contribute.Presenter.Step1, Contribute.PagerVisitor, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private Contribute.View.Step1View mView;
    private GoogleApiClient mGoogleApiClient;

    private Contribute.Pager mPager;
    private String mAddress;
    private String mLongitude;
    private String mLatitude;
    private boolean onPause = true;

    private static final int MY_LOCATION_PERMISSION = 4512;

    LocalizationPresenter(Contribute.View.Step1View view, WasteHolder wasteHolder) {
        super(wasteHolder);
        attachView(view);
    }

    @Override
    public void attachView(Contribute.View.Step1View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void findLocation() {
        if (ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mView.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    || ActivityCompat.shouldShowRequestPermissionRationale(mView.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                mView.showErrorMessage("You should activate your location setting. It's important to know your location !!!");
            } else {
                ActivityCompat.requestPermissions(mView.getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_LOCATION_PERMISSION);
            }
            return;
        }
        locate();
    }

    private void locate() {
        if (ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            Address address = geocode(location.getLatitude(), location.getLongitude());
            mAddress = address.getAddressLine(0);
            mLongitude = ""+location.getLongitude();
            mLatitude = ""+location.getLatitude();
            mView.showLocation(mAddress);
        } else {
            mView.locationNotFound();
        }
    }

    /**
     * @deprecated Define an intentService instead to get the location
     * Get the proper address by lon and lat
     * @param lat
     * @param lon
     * @return adress Object
     * @see <a href="https://developer.android.com/training/location/display-address.html">example1</a>
     * @see <a href="https://guides.codepath.com/android/Retrieving-Location-with-LocationServices-API">example2</a>
     */
    private Address geocode(double lat, double lon) {
        Geocoder gcd = new Geocoder(mView.getContext(), Locale.getDefault());
        Address address = null;
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(lat, lon, 1);
            if (addresses.size() > 0) {
                address = addresses.get(0);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public void goToNextStep() {
        mAddress = mView.getAddress();
        if (mAddress != null) {
            //TODO set it when initializing presenter
            mPager.next(Contribute.LOCALIZATION_FRAGMENT_POSITION);
        } else
            mView.locationNotFound();
    }

    @Override
    public void onCreateView() {
        // init api Client
        mGoogleApiClient = new GoogleApiClient.Builder(mView.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        findLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mView.locationNotFound();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_LOCATION_PERMISSION :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locate();
                }
        }
    }

    @Override
    public void setPager(Contribute.Pager pager) {
        mPager = pager;
    }

    @Override
    public void onResume() {
        loadWastesParamFromPreferences();
        onPause = false;
        if (mAddress != null) {
            mView.showLocation(mAddress);
        }
    }

    @Override
    public void onPause() {
        if (!onPause) {
            saveWasteParamToPreferences();
            onPause = true;
        }
    }



    private void loadWastesParamFromPreferences() {
        mAddress = getWasteHolder().getAddress();
        mLongitude = getWasteHolder().getLongitude();
        mLatitude = getWasteHolder().getLatitude();
//        mAddress = Utils.getStringPreferences(mView.getContext(), Contribute.ADDRESS_PARAM, Contribute.PREFS_NAME);
//        mLongitude = Utils.getStringPreferences(mView.getContext(), Contribute.LONGITUDE_PARAM, Contribute.PREFS_NAME);
//        mLatitude = Utils.getStringPreferences(mView.getContext(), Contribute.LATITUDE_PARAM, Contribute.PREFS_NAME);
    }

    private void saveWasteParamToPreferences() {
        getWasteHolder().setAddress(mAddress);
        getWasteHolder().setLongitude(mLongitude);
        getWasteHolder().setLatitude(mLatitude);
//        Utils.setStringPreferences(mView.getContext(), Contribute.ADDRESS_PARAM, mAddress, Contribute.PREFS_NAME);
//        Utils.setStringPreferences(mView.getContext(), Contribute.LONGITUDE_PARAM, mLongitude, Contribute.PREFS_NAME);
//        Utils.setStringPreferences(mView.getContext(), Contribute.LATITUDE_PARAM, mLatitude, Contribute.PREFS_NAME);
    }
}
