package brymian.bubbles.damian.nonactivity;

/**
 * Created by Ziomster on 5/21/2015.
 */
public class User {

    private int uid;
    private String facebook_uid;
    private int googlep_uid;
    private String username;
    private String password;
    private String namefirst;
    private String namelast;
    private String email;
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
    public void initUserFacebook(String facebook_uid) {
        if (!init) {
            this.facebook_uid = facebook_uid;
            init = true;
        }
    }

    /* Initialize the Google+ user object */
    public void initUserGooglep(int googlep_uid) {
        if (!init) {
            this.googlep_uid = googlep_uid;
            init = true;
        }
    }

    /* Set the data for the normal user login object - to */
    public void setUserNormalLogin(String username, String password, String namefirst, String namelast, String email) {
        this.username = username;
        this.password = password;
        this.namefirst = namefirst;
        this.namelast = namelast;
        this.email = email;
    }

    /* Set the data for the Facebook user login object */
    public void setUserFacebookLogin(String facebook_uid, String namefirst, String namelast, String email) {
        this.facebook_uid = facebook_uid;
        this.namefirst = namefirst;
        this.namelast = namelast;
        this.email = email;
    }

    /* Set the data for the Google+ user login object */
    public void setUserGooglepLogin(int googlep_uid, String namefirst, String namelast, String email) {
        this.googlep_uid = googlep_uid;
        this.namefirst = namefirst;
        this.namelast = namelast;
        this.email = email;
    }

    /* Set the data for the general user object */
    public void setUser(int uid, String facebook_uid, int googlep_uid, String username, String password, String namefirst, String namelast, String email) {
        this.uid = uid;
        this.facebook_uid = facebook_uid;
        this.googlep_uid = googlep_uid;
        this.username = username;
        this.namefirst = namefirst;
        this.namelast = namelast;
        this.email = email;
    }

    public int uid() {
        return uid;
    }

    public String facebookUid() {
        return facebook_uid;
    }

    public int googlepUid() {
        return googlep_uid;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String namefirst() {
        return namefirst;
    }

    public String namelast() {
        return namelast;
    }

    public String email() {
        return email;
    }
}
