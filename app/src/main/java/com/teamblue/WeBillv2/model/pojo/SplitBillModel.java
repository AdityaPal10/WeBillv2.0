package com.teamblue.WeBillv2.model.pojo;

import java.util.Map;

public class SplitBillModel {
    String username;
    String billname;
    double totalamount;
    String date;
    String paid_by;
    String latitude;
    String longitude;
    Map<String,Double> splitMap;

    public SplitBillModel() {
    }

    public SplitBillModel(String username, String billname, double totalamount, String date, String paid_by, String latitude, String longitude, Map<String, Double> splitMap) {
        this.username = username;
        this.billname = billname;
        this.totalamount = totalamount;
        this.date = date;
        this.paid_by = paid_by;
        this.latitude = latitude;
        this.longitude = longitude;
        this.splitMap = splitMap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBillname() {
        return billname;
    }

    public void setBillname(String billname) {
        this.billname = billname;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaid_by() {
        return paid_by;
    }

    public void setPaid_by(String paid_by) {
        this.paid_by = paid_by;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Map<String, Double> getSplitMap() {
        return splitMap;
    }

    public void setSplitMap(Map<String, Double> splitMap) {
        this.splitMap = splitMap;
    }
}
