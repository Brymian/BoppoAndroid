package brymian.bubbles.damian.nonactivity;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ziomster on 7/12/2015.
 */
public class Miscellaneous {

    public static void startFragment(FragmentManager fm, int fragmentId, Fragment fragment) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(fragmentId, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public static int getJsonNullableInt(JSONObject jObject, String key) {
        try {
            return jObject.getInt(key);
        } catch (JSONException jsone) {
            return 0;
        }
    }

    /** This method returns the value JSONObject.NULL if a certain value was specified for a certain datatype.
      * This method returns the value that was input otherwise. **/

    public static Object getNullOrValue(Object object)
    {
        if (object instanceof String)
            if (((String)object).equals("") || ((String)object).toLowerCase().equals("null"))
                return JSONObject.NULL;
            else
                return (String)object;
        else if (object instanceof Integer)
            if ((Integer)object == -1)
                return JSONObject.NULL;
            else
                return (Integer)object;
        else if (object instanceof Long)
            if ((Long)object == -1L)
                return JSONObject.NULL;
            else
                return (Long)object;
        else if (object instanceof Double)
            if ((Double)object == -1.0)
                return JSONObject.NULL;
            else
                return (Double)object;
        else if (object instanceof Boolean)
            return (Boolean)object;   // NULL is handled in the "object == null" statement
        else if (object == null)
            return JSONObject.NULL;

        return "ERROR: Unsupported data type: " + object.getClass();
    }

    public static Boolean getBooleanObjectFromObject(Object object)
    {
        try { return (Boolean)object; }
        catch (ClassCastException cce) { return null; }
    }

    public static Integer getIntegerObjectFromObject(Object object)
    {
        try { return (Integer)object; }
        catch (ClassCastException cce) { return null; }
    }

    public static Long getLongObjectFromObject(Object object)
    {
        try { return Long.valueOf((String)object); }
        catch (ClassCastException cce) { System.out.println("ERROR CASTING: " + object.toString()); return null; }
    }

    public static Double getDoubleObjectFromObject(Object object)
    {
        try { return (Double)object; }
        catch (ClassCastException cce) { return null; }
    }

    public static boolean isStringAnInteger(String string)
    {
        if (string.length() < 1) return false;

        for (char c : string.trim().toCharArray())
            if (!Character.isDigit(c))
                return false;

        return true;
    }

    public static void printLongString(String string)
    {
        final int MAX_LENGTH = 2000;

        if (string.length() > MAX_LENGTH)
        {
            System.out.println(string.substring(0, MAX_LENGTH));
            printLongString(string.substring(MAX_LENGTH));
        }
        else
        {
            System.out.println(string);
        }
    }

    public static String convertPathsToFull(String string)
    {
        HTTPConnection httpConnection = new HTTPConnection();
        string = string.replaceAll(
            "(magePath\\\":\\\")(.*)\\\"", "$1" + httpConnection.getUploadServerString() + "$2\"");
        string = string.replaceAll(
            "(magePath\\\":\\\")(.*)\\\"", "$1" + "$2".replaceAll(" ", "%20") + "\"");

        return string;
    }
}
