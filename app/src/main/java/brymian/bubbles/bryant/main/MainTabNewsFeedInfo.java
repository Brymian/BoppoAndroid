package brymian.bubbles.bryant.main;

import java.io.Serializable;
import java.util.List;

public class MainTabNewsFeedInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    String username, firstLastName, eid, episodeTitle, timestamp, uid;
    String user1Username, user1Uid, user2Username, user2Uid;
    String uiid, uidImage, userImagePurposeLabel, userImageGpsLatitude, userImageGpsLongitude, userImageUploadTimestamp, userImageLikeCount, userImageDislikeCount, userImageCommentCount;
    //int uid;
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

    /* for FriendCreatedEvent, ActiveEvent as well */
    public MainTabNewsFeedInfo(String eid, String episodeTitle, String episodeTimestamp, String username, String uid){
        this.eid = eid;
        this.episodeTitle = episodeTitle;
        this.timestamp = episodeTimestamp;
        this.username = username;
        this.uid = uid;
    }

    /* for FriendsThatBecameFriends */
    public MainTabNewsFeedInfo(String user1Username, String user1Uid, String user2Username, String user2Uid){
        this.user1Username = user1Username;
        this.user1Uid = user1Uid;
        this.user2Username = user2Username;
        this.user2Uid = user2Uid;
    }

    /* for UploadImages */
    public MainTabNewsFeedInfo(String uiid, String uidImage, String userImagePurposeLabel, String userImageGpsLatitude, String userImageGpsLongitude, String userImageUploadTimestamp, String userImageLikeCount, String userImageDislikeCount, String userImageCommentCount){
        this.uiid = uiid;
        this.uidImage = uidImage;
        this.userImagePurposeLabel = userImagePurposeLabel;
        this.userImageGpsLatitude = userImageGpsLatitude;
        this.userImageGpsLongitude = userImageGpsLongitude;
        this.userImageUploadTimestamp = userImageUploadTimestamp;
        this.userImageLikeCount = userImageLikeCount;
        this.userImageDislikeCount = userImageDislikeCount;
        this.userImageCommentCount = userImageCommentCount;
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

    public String getUsername(){
        return username;
    }

    public String getUser1Username(){
        return user1Username;
    }

    public String getUser1Uid(){
        return user1Uid;
    }

    public String getUser2Uid(){
        return user2Uid;
    }

    public String getUser2Username(){
        return user2Username;
    }

    public String getUiid(){
        return uiid;
    }

    public String getUidImage(){
        return uidImage;
    }

    public String getUid(){
        return uid;
    }

}