package com.acemen.android.wasterz.repository.remote;

import android.support.annotation.NonNull;

import com.acemen.android.wasterz.domain.WasteDataSource;
import com.acemen.android.wasterz.domain.model.Waste;
import com.acemen.android.wasterz.repository.network.NetworkUtils;
import com.acemen.android.wasterz.repository.network.api.WasterzApi;
import com.acemen.android.wasterz.repository.network.interceptor.DefaultAuthorizationInterceptor;
import com.acemen.android.wasterz.repository.network.interceptor.GzipInterceptor;
import com.acemen.android.wasterz.repository.network.odata.ODataCriteria;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Audrik ! on 17/04/2017.
 */

public class RemoteWastesRepository implements WasteDataSource {
    private static RemoteWastesRepository INSTANCE;

    private OkHttpClient mClient;

    private RemoteWastesRepository() {
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

    public static RemoteWastesRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteWastesRepository();
        }
        return INSTANCE;
    }

    @Override
    public void addWaste(@NonNull Waste newWaste, @NonNull AddWasteCallback callback) {

    }

    @Override
    public void loadWastes(@NonNull final LoadWastesCallback callback) {
        WasterzApi api = new WasterzApi();
        final String route = "v1/waste";
        final ODataCriteria criteria = getCriteria();
        Request request = new Request.Builder()
                .url(NetworkUtils.URL.encode(api, "GET", route) + criteria.getEncodedCriteria())
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Timber.e("Error when getting wastes", e);
                callback.onDataNotAvailable();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    Timber.d("Request successfully sent");
                    final String body = response.body().string();
                    Timber.d("reponse json ->\n" + body);
                    callback.onWastesLoaded(parseToWastesList(body));
                } else {
                    Timber.e("Response code = "+response.code());
                    callback.onDataNotAvailable();
                }
                response.body().close();
            }
        });
    }

    private List<Waste> parseToWastesList(String remoteResponse) {
        ArrayList<Waste> list = new ArrayList<>(1);
        list.add(new Waste());
        list.get(0).setAddress(remoteResponse);
        return list;
    }

    private ODataCriteria getCriteria() {
        final ODataCriteria criteria = new ODataCriteria();
        criteria.addCriteria(ODataCriteria.PARAM_SELECT, "id,address,longitude,latitude");
        criteria.addCriteria(ODataCriteria.PARAM_ORDER_BY, "id desc");
        criteria.addCriteria(ODataCriteria.PARAM_TOP, "5");
        return criteria;
    }

    /**
     * normalizeCriteria
     *
     * @param criteria
     * @return
     */
    private static String normalizeCriteria(String criteria) {
        if (criteria == null) {
            return "";
        }

        final StringBuilder stringBuilder = new StringBuilder();
        final List<String> keys = Arrays.asList("$select", "$filter", "$orderby", "$skip", "$top");

        final String[] options = criteria.split("&");
        for (String option : options) {
            final String[] params = option.split("=");
            if (params.length != 2) {
                continue;
            }

            final String key = params[0];
            if (!keys.contains(key)) {
                continue;
            }
            final String value = params[1];

            stringBuilder.append("&").append(key).append("=").append(NetworkUtils.URI.encode(value));
        }

        return stringBuilder.toString();
    }
}
