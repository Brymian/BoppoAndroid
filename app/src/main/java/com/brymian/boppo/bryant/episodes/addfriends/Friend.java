package com.brymian.boppo.bryant.episodes.addfriends;

import java.io.Serializable;


public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username, firstLastName, userImagePath;
    private int uid;
    private boolean isSelected;

    public Friend(String username, String firstLastName, int uid, String userImagePath, boolean isSelected){
        this.username = username;
        this.firstLastName = firstLastName;
        this.uid = uid;
        this.isSelected = isSelected;
        this.userImagePath = userImagePath;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setFirstLastName(String firstLastName){
        this.firstLastName = firstLastName;
    }

    public String getFirstLastName(){
        return firstLastName;
    }

    public void setUid(int uid){
        this.uid = uid;
    }

    public int getUid(){
        return uid;
    }

    public String getUserImagePath(){
        return this.userImagePath;
    }

    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    public boolean getIsSelected(){
        return isSelected;
    }
}
