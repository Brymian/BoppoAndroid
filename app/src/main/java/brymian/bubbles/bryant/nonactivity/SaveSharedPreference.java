package brymian.bubbles.bryant.nonactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    private static final String PREF_USER_UID = "uid";
    private static final String PREF_USER_USERNAME = "username";
    private static final String PREF_USER_PASSWORD = "password";
    private static final String PREF_USER_FIRSTNAME = "first name";
    private static final String PREF_USER_LASTNAME = "last name";
    private static final String PREF_USER_EMAIL = "email";
    private static final String PREF_USER_ACCOUNT_PRIVACY = "privacy";
    private static final String PREF_USER_PICTURE_PRIVACY = "picture privacy";
    private static final String PREF_USER_SEND_TO_MAP = "map";
    private static final String PREF_NOTIFICATIONS_VIBRATION = "vibration";
    private static final String PREF_NOTIFICATIONS_LED_LIGHT = "led light";
    private static final String PREF_NOTIFICATIONS_SOUND = "sound";

    private static final String PREF_CAMERA_FLASH_ON = "flash on";
    private static final String PREF_CAMERA_FRONT_CAMERA = "front camera";

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private static final String ENCODED_IMAGE = "encoded image";

    static final String PREF_DONT_DISPLAY_AGAIN = "don't display again";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    /**----------------------------------------Clear All-----------------------------------------**/
    public static void clearAll(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/

    /**-------------------------------------------UID--------------------------------------------**/
    public static void setUserUID(Context ctx, int uid)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_USER_UID, uid);
        editor.apply();
    }

    public static int getUserUID(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF_USER_UID, 1);
    }

    public static void clearUserUID(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_UID);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/

    /**------------------------------------First/Last name---------------------------------------**/
    /* First name */
    public static void setUserFirstName(Context ctx, String firstName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_FIRSTNAME, firstName);
        editor.apply();
    }

    public static String getUserFirstName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_FIRSTNAME, "");
    }

    public static void clearUserFirstName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_FIRSTNAME);
        editor.apply();
    }

    /* Last name */
    public static void setUserLastName(Context ctx, String lastName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_LASTNAME, lastName);
        editor.apply();
    }

    public static String getUserLastName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_LASTNAME, "");
    }

    public static void clearUserLastName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_LASTNAME);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/


    /**---------------------------------------Username-------------------------------------------**/
    public static void setUsername(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_USERNAME, userName);
        editor.apply();
    }

    public static String getUsername(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_USERNAME, "");
    }

    public static void clearUsername(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        //editor.clear(); //clear all stored data
        editor.remove(PREF_USER_USERNAME);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/

    /**----------------------------------------Email---------------------------------------------**/

    public static void setEmail(Context ctx, String email){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, email);
        editor.apply();
    }

    public static String getEmail(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static void clearEmail(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_EMAIL);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/


    /**-----------------------------------Account Privacy----------------------------------------**/
    public static void setUserAccountPrivacy(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ACCOUNT_PRIVACY, "true");
        editor.apply();
    }

    public static String getUserAccountPrivacy(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_ACCOUNT_PRIVACY, "");
    }

    public static void clearUserAccountPrivacy(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_ACCOUNT_PRIVACY);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/

    /**--------------------------------------Privacy---------------------------------------------**/
    public static void setUserPicturePrivacy(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PICTURE_PRIVACY, "Private");
        editor.apply();
    }

    public static String getUserPicturePrivacy(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_PICTURE_PRIVACY, "");
    }

    public static void clearUserPicturePrivacy(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_PICTURE_PRIVACY);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/


    /**---------------------------------------Password-------------------------------------------**/
    public static void setUserPassword(Context ctx, String password){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PASSWORD, password);
        editor.apply();
    }

    public static String getUserPassword(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_PASSWORD, "");
    }

    public static void clearUserPassword(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_USER_PASSWORD);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/


    /**-------------------------------------Notifications----------------------------------------**/
    /* Vibration */
    public static void setNotificationVibration(Context ctx, String condition){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NOTIFICATIONS_VIBRATION, condition); /* condition can be equal to true or false */
        editor.apply();
    }

    public static String getNotificationVibration(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_NOTIFICATIONS_VIBRATION, "");
    }

    public static void clearNotificationVibration(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_NOTIFICATIONS_VIBRATION);
        editor.apply();
    }

    /* LED Light */
    public static void setNotificationsLEDLight(Context ctx, String condition){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NOTIFICATIONS_LED_LIGHT, condition); /* condition can be equal to true or false */
        editor.apply();
    }

    public static String getNotificationLEDLight(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_NOTIFICATIONS_LED_LIGHT, "");
    }

    public static void clearNotificationLEDLight(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_NOTIFICATIONS_LED_LIGHT);
        editor.apply();
    }

    /* Sound */
    public static void setNotificationsSound(Context ctx, String condition){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NOTIFICATIONS_SOUND, condition); /* condition can be equal to true or false */
        editor.apply();
    }

    public static String getNotificationSound(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_NOTIFICATIONS_SOUND, "");
    }

    public static void clearNotificationSound(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_NOTIFICATIONS_SOUND);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/


    /**--------------------------------------GPS Coordinates-------------------------------------**/
    /* Latitude */
    public static void setLatitude(Context ctx, double latitude){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putFloat(LATITUDE, (float) latitude);
        editor.apply();
    }

    public static double getLatitude(Context ctx){
        return getSharedPreferences(ctx).getFloat(LATITUDE, 0);
    }

    public static void clearLatitude(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(LATITUDE);
        editor.apply();
    }

    /* Longitude */
    public static void setLongitude(Context ctx, double longitude){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putFloat(LONGITUDE, (float) longitude);
        editor.apply();
    }

    public static double getLongitude(Context ctx){
        return getSharedPreferences(ctx).getFloat(LONGITUDE, 0);
    }

    public static void clearLongitude(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(LONGITUDE);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/

    /**---------------------------------------Map------------------------------------------------**/
    public static void setSendToMap(Context ctx, String map){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_SEND_TO_MAP, map);
        editor.apply();
    }

    public static String getSendToMap(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_USER_SEND_TO_MAP, "");
    }

    public static void clearSendToMap(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(ENCODED_IMAGE);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/

    /**------------------------------------Encoded Image-----------------------------------------**/
    public static void setEncodedImage(Context ctx, String encodedImage){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(ENCODED_IMAGE, encodedImage);
        editor.apply();
    }

    public static String getEncodedImage(Context ctx){
        return getSharedPreferences(ctx).getString(ENCODED_IMAGE, "");
    }

    public static void clearEncodedImage(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(ENCODED_IMAGE);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/

    /**--------------------------------Camera - Flash--------------------------------------------**/
    public static void setFlashOn(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_CAMERA_FLASH_ON, "flash on");
        editor.apply();
    }

    public static String getFlashOn(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_CAMERA_FLASH_ON, "");
    }

    public static void clearFlashOn(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_CAMERA_FLASH_ON);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/

    /**------------------------------Camera - Front Camera--------------------------------------**/
    public static void setFrontCamera(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_CAMERA_FRONT_CAMERA, "front camera");
        editor.apply();
    }

    public static String getFrontCamera(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_CAMERA_FRONT_CAMERA, "");
    }

    public static void clearFrontCamera(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_CAMERA_FRONT_CAMERA);
        editor.apply();
    }
    /**------------------------------------------------------------------------------------------**/
}