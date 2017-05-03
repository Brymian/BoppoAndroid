package brymian.bubbles.damian.nonactivity;

import android.content.Context;
import android.content.SharedPreferences;

import brymian.bubbles.objects.User;

/**
 * Created by Ziomster on 7/13/2015.
 */
public class UserDataLocal {

    public static final String SP = "USER";
    SharedPreferences userDataLocal;

    public UserDataLocal(Context context) {
        userDataLocal = context.getSharedPreferences(SP, 0);
    }

    /* Save logged in user's data on device */
    public void setUserData(User user) {
        SharedPreferences.Editor userDataLocalEditor = userDataLocal.edit();
        userDataLocalEditor.putInt("uid", user.getUid());
        userDataLocalEditor.putString("facebook_uid", user.getFacebookUid());
        userDataLocalEditor.putString("googlep_uid", user.getGooglepUid());
        userDataLocalEditor.putString("username", user.getUsername());
        userDataLocalEditor.putString("password", user.getPassword());
        userDataLocalEditor.putString("namefirst", user.getFirstName());
        userDataLocalEditor.putString("namelast", user.getLastName());
        userDataLocalEditor.putString("email", user.getEmail());
        userDataLocalEditor.putString("userInsertTimestamp", user.getUserInsertTimestamp());
        userDataLocalEditor.putString("userPrivacy", user.getUserPrivacy());
        userDataLocalEditor.commit();
    }

    /* Get logged in user's data from device */
    public User getUserData() {
        User user = new User();
        user.setUser(
            userDataLocal.getInt("uid", -1),
            userDataLocal.getString("facebook_uid", null),
            userDataLocal.getString("googlep_uid", null),
            userDataLocal.getString("username", null),
            userDataLocal.getString("password", null),
            userDataLocal.getString("namefirst", null),
            userDataLocal.getString("namelast", null),
            userDataLocal.getString("email", null),
            userDataLocal.getString("phone", null),
            userDataLocal.getString("userInsertTimestamp", null),
            userDataLocal.getString("userPrivacy", null)
        );
        return user;
    }

    /* Remove logged out user's data from device */
    public void resetUserData() {
        SharedPreferences.Editor userDataLocalEditor = userDataLocal.edit();
        userDataLocalEditor.clear();
        userDataLocalEditor.commit();
    }

    /* Change the status to logged in or logged out for the user */
    public void setLoggedStatus(boolean loggedIn) {
        SharedPreferences.Editor userDataLocalEditor = userDataLocal.edit();
        userDataLocalEditor.putBoolean("loggedIn", loggedIn);
        userDataLocalEditor.commit();
    }

    /* Check the current logged status for the user */
    public boolean getLoggedStatus() {
        return userDataLocal.getBoolean("loggedIn", false);
    }
}
