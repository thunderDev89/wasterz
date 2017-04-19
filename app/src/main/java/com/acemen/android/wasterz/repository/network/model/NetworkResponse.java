package com.acemen.android.wasterz.repository.network.model;

/**
 * Created by Audrik ! on 20/04/2017.
 */

public class NetworkResponse {
    private Data data;

    private String success;

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return "NetworkResponse [data = "+data+", success = "+success+"]";
    }

    //TODO example
    //{"success":true,"data":{"wastes":[{"id":42,"address":"49","type":"any-waste","latitude":"48.87327720","longitude":"2.22951550","creationDate":{"date":"2016-08-23 22:49:10.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":43,"address":"Pantin","type":"butt","latitude":"48.85342750","longitude":"2.35827880","creationDate":{"date":"2016-08-24 09:05:48.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":44,"address":"49","type":"butt","latitude":"48.87592313","longitude":"2.23055224","creationDate":{"date":"2016-08-24 21:53:24.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":45,"address":"oo","type":"butt","latitude":"48.85342750","longitude":"2.35827880","creationDate":{"date":"2016-08-25 14:03:44.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":46,"address":"oo","type":"butt","latitude":"48.85342750","longitude":"2.35827880","creationDate":{"date":"2016-08-25 14:17:41.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":47,"address":"Ddf","type":"excrement","latitude":"48.89738856","longitude":"2.39648035","creationDate":{"date":"2016-08-25 14:22:30.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":48,"address":"oo","type":"butt","latitude":"48.87901830","longitude":"2.33790630","creationDate":{"date":"2016-08-26 11:38:31.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":49,"address":"oo","type":"butt","latitude":"48.87901830","longitude":"2.33790630","creationDate":{"date":"2016-08-26 11:39:56.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":50,"address":"oo","type":"butt","latitude":"48.87901830","longitude":"2.33790630","creationDate":{"date":"2016-08-26 11:40:18.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":51,"address":"oo","type":"excrement","latitude":"48.85342750","longitude":"2.35827880","creationDate":{"date":"2016-08-26 15:54:44.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":52,"address":"99 Bergstra\u00dfe, 64319 Pfungstadt","type":"tag","latitude":"49.79459800","longitude":"8.60043100","creationDate":{"date":"2016-08-26 17:02:40.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":53,"address":"55 Am Sp\u00f6rkel, 44227 Dortmund","type":"tag","latitude":"51.47299000","longitude":"7.42648000","creationDate":{"date":"2016-08-26 23:36:09.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":54,"address":"Ee","type":"butt","latitude":"49.57096924","longitude":"3.66567246","creationDate":{"date":"2016-08-27 21:50:24.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":55,"address":"8 rue villot la courneuve","type":"excrement","latitude":"48.92732310","longitude":"2.39539460","creationDate":{"date":"2016-08-28 15:57:50.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":56,"address":"Uu","type":"butt","latitude":"49.57024714","longitude":"3.68391453","creationDate":{"date":"2016-08-28 17:21:04.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":57,"address":"Cachan","type":"tag","latitude":"48.80333090","longitude":"2.32393580","creationDate":{"date":"2016-08-28 18:57:16.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":58,"address":"Pantin","type":"butt","latitude":"48.89657526","longitude":"2.39576315","creationDate":{"date":"2016-08-30 16:14:29.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":59,"address":"Oo","type":"butt","latitude":"48.87450152","longitude":"2.23515842","creationDate":{"date":"2016-08-31 23:27:44.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":60,"address":"7 Rue du D\u00e9barcad\u00e8re, 93500 Pantin","type":"tag","latitude":"48.89758810","longitude":"2.39679640","creationDate":{"date":"2016-09-01 10:21:52.000000","timezone_type":3,"timezone":"Europe\/Berlin"}},{"id":61,"address":"oo","type":"butt","latitude":"48.87901830","longitude":"2.33790630","creationDate":{"date":"2016-09-07 14:31:54.000000","timezone_type":3,"timezone":"Europe\/Berlin"}}]}}
}
