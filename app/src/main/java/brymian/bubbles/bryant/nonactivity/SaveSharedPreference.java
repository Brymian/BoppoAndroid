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
    static final String PREF_NOTIFICATIONS_VIBRATION = "vibration";
    static final String PREF_NOTIFICATIONS_LED_LIGHT = "led light";
    static final String PREF_NOTIFICATIONS_SOUND = "sound";

    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";


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

    public static void setNotificationVibration(Context ctx, String condition){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NOTIFICATIONS_VIBRATION, condition); /* condition can be equal to true or false */
        editor.apply();
    }

    public static String getNotificationVibration(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_NOTIFICATIONS_VIBRATION, "");
    }

    public static void setNotificationsLEDLight(Context ctx, String condition){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NOTIFICATIONS_LED_LIGHT, condition); /* condition can be equal to true or false */
        editor.apply();
    }

    public static String getNotificationLEDLight(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_NOTIFICATIONS_LED_LIGHT, "");
    }

    public static void setNotificationsSound(Context ctx, String condition){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NOTIFICATIONS_SOUND, condition); /* condition can be equal to true or false */
        editor.apply();
    }

    public static String getNotificationSound(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_NOTIFICATIONS_SOUND, "");
    }

    public static void setLatitude(Context ctx, double latitude){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putFloat(LATITUDE, (float)latitude);
        editor.apply();
    }

    public static double getLatitude(Context ctx){
        return getSharedPreferences(ctx).getFloat(LATITUDE, 0);
    }

    public static void setLongitude(Context ctx, double longitude){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putFloat(LONGITUDE, (float) longitude);
        editor.apply();
    }

    public static double getLongitude(Context ctx){
        return getSharedPreferences(ctx).getFloat(LONGITUDE, 0);
    }

    public static void clearUsername(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        //editor.clear(); //clear all stored data
        editor.remove(PREF_USER_NAME);
        editor.apply();
    }

    public static void clearNotificationVibration(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_NOTIFICATIONS_VIBRATION);
        editor.apply();
    }

    public static void clearNotificationLEDLight(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_NOTIFICATIONS_LED_LIGHT);
        editor.apply();
    }

    public static void clearNotificationSound(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_NOTIFICATIONS_SOUND);
        editor.apply();
    }

    public static void clearLatitude(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(LATITUDE);
        editor.apply();
    }

    public static void clearLongitude(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(LONGITUDE);
        editor.apply();
    }

}