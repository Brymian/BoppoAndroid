package com.brymian.boppo.damian.objects;

/**
 * Created by Ziomster on 5/21/2015.
 */
public class User {

    private Integer uid;
    private String facebookUid;
    private String googlepUid;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String userInsertTimestamp;
    private String userPrivacy;
    private boolean init = false;

    private String friendshipStatus = null;

    private final int NORMAL = 1;
    private final int FACEBOOK = 2;
    private final int GOOGLEP = 3;

    /* Create an empty user object */
    public User() {}

    /* Initialize the normal user object */
    public void initUserNormal(String username, String password) {
        if (!init) {
            this.username = username;
            this.password = password;
            init = true;
        }
    }

    /* Initialize the Facebook user object */
    public void initUserFacebook(String facebookUid) {
        if (!init) {
            this.facebookUid = facebookUid;
            init = true;
        }
    }

    /* Initialize the Google+ user object */
    public void initUserGooglep(String googlepUid) {
        if (!init) {
            this.googlepUid = googlepUid;
            init = true;
        }
    }

    /* Set the data for the normal user login object - to */
    public void setUserNormalLogin(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /* Set the data for the Facebook user login object */
    public void setUserFacebookLogin(String facebookUid, String firstName, String lastName, String email) {
        this.facebookUid = facebookUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /* Set the data for the Google+ user login object */
    public void setUserGooglepLogin(String googlepUid, String firstName, String lastName, String email) {
        this.googlepUid = googlepUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /* Set the data for the general user object */
    public void setUser(int uid, String facebookUid, String googlepUid,
        String username, String password, String firstName, String lastName, String email,
        String phone, String userInsertTimestamp, String userPrivacy)
    {
        this.uid = uid;
        this.facebookUid = facebookUid;
        this.googlepUid = googlepUid;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.userInsertTimestamp = userInsertTimestamp;
        this.userPrivacy = userPrivacy;
    }

    public void setUserPrivacyLabel(String userPrivacyLabel) {
        this.userPrivacy = userPrivacy;
    }

    public void setFriendshipStatus(String friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUid() {
        return uid;
    }

    public String getFacebookUid() {
        return facebookUid;
    }

    public String getGooglepUid() {
        return googlepUid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() { return phone; }

    public String getUserInsertTimestamp() { return userInsertTimestamp; }

    public String getUserPrivacy() { return userPrivacy; }

    public String getFriendshipStatus() { return friendshipStatus; }
}
