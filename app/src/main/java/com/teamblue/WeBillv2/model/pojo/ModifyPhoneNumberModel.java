package com.teamblue.WeBillv2.model.pojo;

public class ModifyPhoneNumberModel {
    private String username;
   // private String oldPhoneNumber;
    private String newPhone;

    public ModifyPhoneNumberModel(String username, String newPhoneNumber) {
        this.username = username;
        this.newPhone = newPhoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*public String getOldPhoneNumber() {
        return oldPhoneNumber;
    }

    public void setOldPhoneNumber(String oldPhoneNumber) {
        this.oldPhoneNumber = oldPhoneNumber;
    }*/

    public String getNewPhoneNumber() {
        return newPhone;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhone = newPhoneNumber;
    }
}
