package com.acemen.android.wasterz.repository.network.interceptor;

import com.acemen.android.wasterz.repository.network.api.ApiClient;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Audrik ! on 16/04/2017.
 */

public class DefaultAuthorizationInterceptor implements Interceptor {
    private final ApiClient api;

    public DefaultAuthorizationInterceptor(
            ApiClient api) {
        this.api = api;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
//        final Request request = chain.request();
//        ApiClient api = new WasterzApi(); // TODO to delete; Use DI instead
        Headers headers = new Headers.Builder().add("Accept", "application/json")
                .add("Authorization", Credentials.basic(api.getClientId(), api.getClientSecret())).add("Referer", api.getBaseUrl())
                .add("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
                .build();

        final Request authentifiedRequest = chain.request().newBuilder()
                .headers(headers)
                .build();
        return chain.proceed(authentifiedRequest);
    }
}
