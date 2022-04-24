package com.teamblue.WeBillv2.model.pojo;

public class UserStripeAccount {

    private String username;
    private String accountId;
    private String customerId;

    public UserStripeAccount(String username, String accountId, String customerId) {
        this.username = username;
        this.accountId = accountId;
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCustomerId(){
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
