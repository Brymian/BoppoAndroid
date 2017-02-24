package brymian.bubbles.bryant.main;

import java.io.Serializable;
import java.util.List;

public class MainTabNewsFeedInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    String username, firstLastName, eid, episodeTitle, timestamp;
    int uid;
    List<String> uids, usernames;

    public MainTabNewsFeedInfo(){

    }

    /* for FriendsJoinedMutualEvent */
    public MainTabNewsFeedInfo(String eid, String episodeTitle, String timestamp, List<String> usernames, List<String> uids){
        this.eid = eid;
        this.episodeTitle = episodeTitle;
        this.timestamp = timestamp;
        this.usernames = usernames;
        this.uids = uids;
    }

    public MainTabNewsFeedInfo(String username, String firstLastName, int uid){
        this.username = username;
        this.firstLastName = firstLastName;
        this.uid = uid;
    }


    public String getEid(){
        return eid;
    }

    public String getEpisodeTitle(){
        return episodeTitle;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public List<String> getUsernames(){
        return usernames;
    }

    public List<String> getUids(){
        return uids;
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