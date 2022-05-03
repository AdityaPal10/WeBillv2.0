package com.teamblue.WeBillv2.model.pojo;

public class MapsRequestModel {
    String username;
    String year;

    public MapsRequestModel() {
    }

    public MapsRequestModel(String username, String year) {
        this.username = username;
        this.year = year;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
