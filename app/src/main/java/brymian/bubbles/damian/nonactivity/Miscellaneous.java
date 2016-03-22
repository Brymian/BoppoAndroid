package brymian.bubbles.damian.nonactivity;

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
        try { return new Long(object.toString()); }
        catch (ClassCastException cce) { return null; }
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
}
