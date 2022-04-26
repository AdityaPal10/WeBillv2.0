package com.teamblue.WeBillv2.model.pojo;

public class BillsByLoc {

    String username;
    double latitude;
    double longitude;
    String dateString;

    public BillsByLoc() {
    }

    public BillsByLoc(String username, double latitude, double longitude, String dateString) {
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateString = dateString;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
