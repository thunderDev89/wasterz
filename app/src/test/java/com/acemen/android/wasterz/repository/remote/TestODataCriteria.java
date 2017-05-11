package com.acemen.android.wasterz.repository.remote;

import com.acemen.android.wasterz.repository.network.odata.ODataCriteria;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Audrik ! on 19/04/2017.
 */

public class TestODataCriteria {

    @Test
    public void testNormalizedCriteria_isCorrect() {
        // criteria = "$select=id,address,longitude,latitude&$orderby=id desc&$top=10";
        ODataCriteria criteria = new ODataCriteria();
        criteria.addCriteria(ODataCriteria.PARAM_SELECT, "id,address");
        criteria.addCriteria(ODataCriteria.PARAM_ORDER_BY, "id desc");
        criteria.addCriteria(ODataCriteria.PARAM_TOP, "5");

        final String encodedCriteria = criteria.getEncodedCriteria();

        Assert.assertNotNull(encodedCriteria);
        Assert.assertEquals("&$select=id%2Caddress&$orderby=id+desc&$top=5", encodedCriteria);
    }

    @Test
    public void testNoCriteria_isCorrect() {
        ODataCriteria criteria = new ODataCriteria();
        final String encodedCriteria = criteria.getEncodedCriteria();

        Assert.assertNotNull(encodedCriteria);
        Assert.assertEquals("", encodedCriteria);
    }
}
