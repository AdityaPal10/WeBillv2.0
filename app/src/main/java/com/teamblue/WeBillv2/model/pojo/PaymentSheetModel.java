package com.teamblue.WeBillv2.model.pojo;

public class PaymentSheetModel {

    private String setupIntent;
    private String customerID;
    private String ephemeralKey;
    private String publishableKey;

    public PaymentSheetModel(String setupIntent, String customerID, String ephKey, String pubKey) {
        this.setupIntent = setupIntent;
        this.customerID = customerID;
        this.ephemeralKey = ephKey;
        this.publishableKey = pubKey;
    }

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

    public String getEphemeralKey() {
        return ephemeralKey;
    }

    public void setEphemeralKey(String ephemeralKey) {
        this.ephemeralKey = ephemeralKey;
    }

    public String getPublishableKey() {
        return publishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        this.publishableKey = publishableKey;
    }

}
