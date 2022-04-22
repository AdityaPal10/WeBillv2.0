package com.teamblue.WeBillv2.model.pojo;

public class PaymentSheetModel {

    private String setupIntent;
    private String customerID;
    private String publishableKey;

    public String getSetupIntent() {
        return setupIntent;
    }

    public void setSetupIntent(String setupIntent) {
        this.setupIntent = setupIntent;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getPublishableKey() {
        return publishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        this.publishableKey = publishableKey;
    }

}
