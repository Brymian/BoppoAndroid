package brymian.bubbles.bryant.settings.blocking;

import java.io.Serializable;


public class BlockedUser implements Serializable {

    private static final long serialVersionUID = 1L;
    String username, firstLastName;
    int uid;
    /**
    public BlockedUser(){

    }
    **/

    public BlockedUser(String username, String firstLastName, int uid){
        this.username = username;
        this.firstLastName = firstLastName;
        this.uid = uid;
    }

    /**
    public void setUsername(String username){
        this.username = username;
    }
     **/

    public String getUsername(){
        return username;
    }

    /**
    public void setFirstLastName(String firstLastName){
        this.firstLastName = firstLastName;
    }
     **/


    public String getFirstLastName(){
        return firstLastName;
    }

    /**
     public void setUid(int uid){
        this.uid = uid;
    }
     **/

    public int getUid(){
        return uid;
    }


}