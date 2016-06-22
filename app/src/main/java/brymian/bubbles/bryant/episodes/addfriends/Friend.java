package brymian.bubbles.bryant.episodes.addfriends;

import java.io.Serializable;


public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;
    String username, firstLastName;
    int uid;
    boolean isSelected;


    public Friend(){

    }

    public Friend(String username, String firstLastName, int uid, boolean isSelected){
        this.username = username;
        this.firstLastName = firstLastName;
        this.uid = uid;
        this.isSelected = isSelected;
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

    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    public boolean getIsSelected(){
        return isSelected;
    }
}
