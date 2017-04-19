package com.acemen.android.wasterz.repository.network;

import com.acemen.android.wasterz.repository.network.interceptor.DefaultAuthorizationInterceptor;
import com.acemen.android.wasterz.repository.network.interceptor.GzipInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Audrik ! on 10/04/2017.
 */

public class NetworkRequest {
    private OkHttpClient mClient;

    public void initClient() {
        if (mClient == null) {
            mClient = new OkHttpClient.Builder()
                    .addInterceptor(new GzipInterceptor())
                    .addInterceptor(new DefaultAuthorizationInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
    }


}
