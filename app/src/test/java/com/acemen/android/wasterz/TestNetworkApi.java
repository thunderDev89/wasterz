package com.acemen.android.wasterz;

import com.acemen.android.wasterz.repository.network.NetworkUtils;
import com.acemen.android.wasterz.repository.network.api.WasterzApi;
import com.acemen.android.wasterz.repository.network.interceptor.DefaultAuthorizationInterceptor;
import com.acemen.android.wasterz.repository.network.interceptor.GzipInterceptor;
import com.acemen.android.wasterz.repository.network.odata.ODataCriteria;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Audrik ! on 20/04/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TestNetworkApi {
    @Test
    public void testLoadWastes() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new GzipInterceptor())
                .addInterceptor(new DefaultAuthorizationInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        // Init client
        WasterzApi api = new WasterzApi();
        final String route = "v1/waste";
        final ODataCriteria criteria = getCriteria();
        Request request = new Request.Builder()
                .url(NetworkUtils.URL.encode(api, "GET", route) + criteria.getEncodedCriteria())
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertNotNull(response);
        System.err.println("Response = "+response.body().string());
        Assert.assertTrue(response.message(), response.isSuccessful());
    }

    private ODataCriteria getCriteria() {
        final ODataCriteria criteria = new ODataCriteria();
        criteria.addCriteria(ODataCriteria.PARAM_SELECT, "id,address,longitude,latitude");
        criteria.addCriteria(ODataCriteria.PARAM_ORDER_BY, "id desc");
        criteria.addCriteria(ODataCriteria.PARAM_TOP, "5");
        return criteria;
    }
}
