package brymian.bubbles.objects;

/**
 * Created by Ziomster on 5/21/2015.
 */
public class User {

    private int uid;
    private String facebookUid;
    private String googlepUid;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String userAccountCreationTimestamp;
    private String userAccountPrivacy;
    private boolean init = false;

    private final int NORMAL = 1;
    private final int FACEBOOK = 2;
    private final int GOOGLEP = 3;

    /* Create an empty user object */
    public User() {
    }

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
        String userAccountCreationTimestamp, String userAccountPrivacy)
    {
        this.uid = uid;
        this.facebookUid = facebookUid;
        this.googlepUid = googlepUid;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userAccountCreationTimestamp = userAccountCreationTimestamp;
        this.userAccountPrivacy = userAccountPrivacy;
    }

    public void setUserAccountPrivacyLabel(String userAccountPrivacyLabel) {
        this.userAccountPrivacy = userAccountPrivacy;
    }

    public int getUid() {
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

    public String getUserAccountCreationTimestamp() { return userAccountCreationTimestamp; }

    public String getUserAccountPrivacy() { return userAccountPrivacy; }
}
