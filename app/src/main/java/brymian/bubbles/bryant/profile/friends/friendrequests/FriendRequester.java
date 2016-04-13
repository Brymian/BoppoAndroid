package brymian.bubbles.bryant.profile.friends.friendrequests;

import java.io.Serializable;

/**
 * Created by Almanza on 4/13/2016.
 */
public class FriendRequester implements Serializable {

    private static final long serialVersionUID = 1L;
    String username, firstLastName;
    int uid;

    public FriendRequester(){

    }

    public FriendRequester(String username, String firstLastName, int uid){
        this.username = username;
        this.firstLastName = firstLastName;
        this.uid = uid;
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


}