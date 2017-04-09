package com.acemen.android.wasterz.domain.model;

/**
 * Created by Audrik ! on 21/03/2017.
 */

public class Waste {
    private long id;
    private String remoteId;
    private String wasteType;
    private String address;
    private String captureFilename;

    public Waste() {}

    public Waste(String wasteType, String address, String captureFilename) {
        this.wasteType = wasteType;
        this.address = address;
        this.captureFilename = captureFilename;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCaptureFilename() {
        return captureFilename;
    }

    public void setCaptureFilename(String captureFilename) {
        this.captureFilename = captureFilename;
    }
}
