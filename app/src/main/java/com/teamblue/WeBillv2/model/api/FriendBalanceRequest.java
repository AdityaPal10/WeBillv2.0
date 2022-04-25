package com.teamblue.WeBillv2.model.api;

public class FriendBalanceRequest {
    private String username1;


    public FriendBalanceRequest(String username) {
        this.username1 = username;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }


}
