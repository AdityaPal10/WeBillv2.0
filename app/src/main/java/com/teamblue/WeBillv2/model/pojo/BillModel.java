package com.teamblue.WeBillv2.model.pojo;

public class BillModel {

    private int billId;
    private String billName;
    private String date;
    private double totalAmount;
    private String paidBy;
    private String latitude;
    private String longitude;

    public BillModel() {
    }

    public BillModel(int billId, String billName, String date, double totalAmount, String paidBy, String latitude, String longitude) {
        this.billId = billId;
        this.billName = billName;
        this.date = date;
        this.totalAmount = totalAmount;
        this.paidBy = paidBy;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
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
}
