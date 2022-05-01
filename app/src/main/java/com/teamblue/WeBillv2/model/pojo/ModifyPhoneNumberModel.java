package com.teamblue.WeBillv2.model.pojo;

public class ModifyPhoneNumberModel {
    private String username;
    private String oldPhoneNumber;
    private String newPhoneNumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPhoneNumber() {
        return oldPhoneNumber;
    }

    public void setOldPhoneNumber(String oldPhoneNumber) {
        this.oldPhoneNumber = oldPhoneNumber;
    }

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }
}
