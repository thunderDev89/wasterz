package com.acemen.android.wasterz.repository.network.odata;

import android.support.annotation.StringDef;

import com.acemen.android.wasterz.repository.network.NetworkUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Audrik ! on 19/04/2017.
 */

public class ODataCriteria {
    private Map<String, String> criterias;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({PARAM_SELECT, PARAM_FILTER, PARAM_ORDER_BY, PARAM_SKIP, PARAM_TOP})
    public @interface ParamName {}
    public static final String PARAM_SELECT = "$select";
    public static final String PARAM_FILTER = "$filter";
    public static final String PARAM_ORDER_BY = "$orderby";
    public static final String PARAM_SKIP = "$skip";
    public static final String PARAM_TOP = "$top";

    private final static @ParamName String[] AUTHORIZED_CRITERIAS = {PARAM_SELECT, PARAM_FILTER, PARAM_ORDER_BY, PARAM_SKIP, PARAM_TOP};
    public ODataCriteria() {
        criterias = new HashMap<>();
    }

    public void addCriteria(@ParamName String name, String value) {
        criterias.put(name, value);
    }

    public String getEncodedCriteria() {
        final StringBuilder criteriaBuilder = new StringBuilder("");

        if (criterias != null && !criterias.isEmpty()) {
            for (String authorizedKey : AUTHORIZED_CRITERIAS) {
                if (criterias.containsKey(authorizedKey)) {
                    criteriaBuilder.append("&")
                            .append(authorizedKey).append("=")
                            .append(NetworkUtils.URI.encode(criterias.get(authorizedKey)));
                }
            }
        }
        return criteriaBuilder.toString();
    }

//    public String getUrlCriteria() {
//        final StringBuilder criteriaBuilder = new StringBuilder("");
//
//        if (criterias != null && !criterias.isEmpty()) {
//            for (String authorizedKey : AUTHORIZED_CRITERIAS) {
//                if (criterias.containsKey(authorizedKey)) {
//                    criteriaBuilder.append("&")
//                            .append(authorizedKey).append("=")
//                            .append(criterias.get(authorizedKey));
//                }
//            }
//        }
//        return criteriaBuilder.toString();
//    }
}
