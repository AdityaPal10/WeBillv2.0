package com.teamblue.WeBillv2.model.pojo;

import java.util.ArrayList;

public class Group {
    private ArrayList<Friend> groupName = new ArrayList<>();
    private String uniqueGroupKey;

    public Group(){
    }
    public Group(ArrayList<Friend> groupName) {
        this.groupName = groupName;
    }
    public Group(ArrayList<Friend> groupName, String uniqueGroupKey) {
        this.groupName = groupName;
        this.uniqueGroupKey = uniqueGroupKey;
    }

    private void addFriend(Group group, Friend friendName){
        group.groupName.add(friendName);
    }

    private void removeFriend(Group group, Friend friendName){
        group.groupName.remove(friendName);
    }

    public Group createGroup(ArrayList<Friend> groupName){
        Group newGroup=new Group(groupName);
        return newGroup;
    }



}
