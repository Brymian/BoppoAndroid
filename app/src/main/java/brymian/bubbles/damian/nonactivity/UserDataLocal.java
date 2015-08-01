package brymian.bubbles.damian.nonactivity;

import android.content.Context;
import android.content.SharedPreferences;

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
        userDataLocalEditor.putInt("uid", user.uid());
        userDataLocalEditor.putString("facebook_uid", user.facebookUid());
        userDataLocalEditor.putInt("googlep_uid", user.googlepUid());
        userDataLocalEditor.putString("username", user.username());
        userDataLocalEditor.putString("namefirst", user.namefirst());
        userDataLocalEditor.putString("namelast", user.namelast());
        userDataLocalEditor.putString("email", user.email());
        userDataLocalEditor.commit();
    }

    /* Get logged in user's data from device */
    public User getUserData() {
        User user = new User();
        user.setUser(
                userDataLocal.getInt("uid", -1),
                userDataLocal.getString("facebook_uid", null),
                userDataLocal.getInt("googlep_uid", -1),
                userDataLocal.getString("username", null),
                userDataLocal.getString("password", null),
                userDataLocal.getString("namefirst", null),
                userDataLocal.getString("namelast", null),
                userDataLocal.getString("email", null)
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
    }

    /* Check the current logged status for the user */
    public boolean getLoggedStatus() {
        return userDataLocal.getBoolean("loggedIn", false);
    }
}
