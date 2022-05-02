package com.teamblue.WeBillv2.model.pojo;

public class ModifyPasswordModel {
    private String username;
    private String newPassword;

    public ModifyPasswordModel() {
    }

    public ModifyPasswordModel(String username, String newPass) {
        this.username = username;
        this.newPassword = newPass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getNewPass() {
        return newPassword;
    }

    public void setNewPass(String newPass) {
        this.newPassword = newPass;
    }
}
