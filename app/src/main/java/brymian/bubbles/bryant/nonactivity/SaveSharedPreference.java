package brymian.bubbles.bryant.nonactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Almanza on 1/28/2016.
 */
public class SaveSharedPreference {
    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_PASSWORD = "password";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.apply();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public void setUserPassword(Context ctx, String password){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PASSWORD, password);
        editor.apply();
    }

    public static String getUserPassword(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_PASSWORD, "");
    }

    public static void clearUserNameAndPassword(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.apply();
    }
}