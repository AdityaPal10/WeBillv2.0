package com.teamblue.WeBillv2.model.pojo;

public class Friend {

    private String username;
    private String friendUsername;
    private String uniqueFriendshipKey;

    public Friend() {
    }

    public Friend(String username, String friendUsername) {
        this.username = username;
        this.friendUsername = friendUsername;
    }

    public Friend(String username, String friendUsername, String uniqueFriendshipKey) {
        this.username = username;
        this.friendUsername = friendUsername;
        this.uniqueFriendshipKey = uniqueFriendshipKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public String getUniqueFriendshipKey() {
        return uniqueFriendshipKey;
    }

    public void setUniqueFriendshipKey(String uniqueFriendshipKey) {
        this.uniqueFriendshipKey = uniqueFriendshipKey;
    }
}
