package com.acemen.android.wasterz.domain.model;

/**
 * Created by Audrik ! on 21/03/2017.
 */

public class Waste {
    private long id;
    private String remoteId;
    private String type;
    private String address;
    private long longitude;
    private long latitude;
    private String captureFilename;

    public Waste() {}

    public Waste(String type, String address, String captureFilename) {
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String wasteType) {
        this.type = wasteType;
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

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
