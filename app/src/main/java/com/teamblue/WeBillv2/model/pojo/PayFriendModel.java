package com.teamblue.WeBillv2.model.pojo;

public class PayFriendModel {

    private String sourceCustomerId;
    private String destAccountId;
    private int amount;

    public PayFriendModel(String sourceCustomerId, String destAccountId, int amount) {
        this.sourceCustomerId = sourceCustomerId;
        this.destAccountId = destAccountId;
        this.amount = amount;
    }

    public String getSourceCustomerId() {
        return sourceCustomerId;
    }

    public void setSourceCustomerId(String sourceCustomerId) {
        this.sourceCustomerId = sourceCustomerId;
    }

    public String getDestAccountId() {
        return destAccountId;
    }

    public void setDestAccountId(String destAccountId) {
        this.destAccountId = destAccountId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
