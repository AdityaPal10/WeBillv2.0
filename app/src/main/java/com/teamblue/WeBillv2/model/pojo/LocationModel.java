package com.teamblue.WeBillv2.model.pojo;

public class LocationModel {
    private int expense_id;
    private String location_name;
    private double latitude;
    private double longitude;
    private double total_expense;
    private int visits;

    public LocationModel() {
    }

    public LocationModel(int expense_id, String location_name, double latitude, double longitude, double total_expense, int visits) {
        this.expense_id = expense_id;
        this.location_name = location_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.total_expense = total_expense;
        this.visits = visits;
    }

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
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

    public double getTotal_amount() {
        return total_expense;
    }

    public void setTotal_amount(double total_expense) {
        this.total_expense = total_expense;
    }

    public void setTotal_amount(float total_expense) {
        this.total_expense = total_expense;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "LocationModel{" +
                "expense_id=" + expense_id +
                ", location_name='" + location_name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", total_expense=" + total_expense +
                ", visits=" + visits +
                '}';
    }
}
