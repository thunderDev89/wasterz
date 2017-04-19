package com.acemen.android.wasterz.repository.network.model;

/**
 * Created by Audrik ! on 20/04/2017.
 */

public class Data {
    private WasteNetworkResponse[] wastes;

    public WasteNetworkResponse[] getWastes ()
    {
        return wastes;
    }

    public void setWastes (WasteNetworkResponse[] wastes)
    {
        this.wastes = wastes;
    }

    @Override
    public String toString()
    {
        return "Data [wastes = "+wastes+"]";
    }
}
