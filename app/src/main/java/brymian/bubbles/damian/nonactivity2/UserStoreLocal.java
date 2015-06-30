package brymian.bubbles.damian.nonactivity2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ziomster on 5/21/2015.
 */
public class UserStoreLocal {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserStoreLocal(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void setUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.commit();
    }

    public User getUserData() {
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        User user = new User(username, password);
        return user;
    }

    public void resetUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public void setUserLoggedStatus(boolean loggedStatus) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedStatus", loggedStatus);
        spEditor.commit();
    }

    public boolean getUserLoggedStatus() {
        return userLocalDatabase.getBoolean("loggedStatus", false);
    }
//temp
}
