package com.acemen.android.wasterz.repository.network.api;

/**
 * Created by Audrik ! on 18/04/2017.
 */

public class WasterzApi implements ApiClient {
    private String CLIENT_ID = "15147524726452519394";

    private String CLIENT_SECRET = "7AwTLP1A2J67DX95vlj5";

    private String BASE_URL = "http://codiad.api.wasterz.bonaxium.com/"; //http://od3m.com:8282/

    @Override
    public String getClientId() {
        return CLIENT_ID;
    }

    @Override
    public String getClientSecret() {
        return CLIENT_SECRET;
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }
}
