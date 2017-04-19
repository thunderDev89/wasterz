package com.acemen.android.wasterz.repository.network.model;

/**
 * Created by Audrik ! on 20/04/2017.
 */

public class WasteNetworkResponse {
    private String id;

    private String address;

    private String longitude;

    private String latitude;

    private String type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "WasteNetworkResponse [id = "+id+", address = "+address+", longitude = "+longitude+", latitude = "+latitude+", type = "+type+"]";
    }

}
