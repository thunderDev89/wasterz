package com.acemen.android.wasterz.view.model;

import com.acemen.android.wasterz.domain.model.Waste;

/**
 * Created by Audrik ! on 30/04/2017.
 */

public class WasteHolder {
    private String filename;
    private String address;
    private String wasteType;
    private String longitude;
    private String latitude;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Waste getWaste() {
        final Waste waste = new Waste();
        waste.setAddress(address);
        waste.setType(wasteType);
        waste.setCaptureFilename(filename);
        if (longitude != null) {
            Double.parseDouble(longitude);
        }
        if (latitude != null) {
            Double.parseDouble(latitude);
        }

        return waste;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WasteHolder:[")
                .append("Address=").append(address).append("\n")
                .append("Long=").append(longitude).append("\n")
                .append("Lat=").append(latitude).append("\n")
                .append("Type=").append(wasteType).append("\n")
                .append("CaptureFileName=").append(filename).append("\n")
                .append("]");
        return builder.toString();
    }
}
