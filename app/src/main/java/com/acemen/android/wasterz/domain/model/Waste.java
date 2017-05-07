package com.acemen.android.wasterz.domain.model;

/**
 * Created by Audrik ! on 21/03/2017.
 */

public class Waste {
    private long id;
    private String remoteId;
    private String type;
    private String address;
    private double longitude;
    private double latitude;
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
        return captureFilename.substring(captureFilename.lastIndexOf("/")+1);
    }

    public void setCaptureFilename(String captureFilename) {
        this.captureFilename = captureFilename;
    }

    public String getFilePath() {
        return captureFilename;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Waste:[")
                .append("id=").append(id).append("\n")
                .append("Address=").append(address).append("\n")
                .append("Long=").append(longitude).append("\n")
                .append("Lat=").append(latitude).append("\n")
                .append("Type=").append(type).append("\n")
                .append("CaptureFileName=").append(captureFilename).append("\n")
                .append("]");
        return builder.toString();
    }
}
