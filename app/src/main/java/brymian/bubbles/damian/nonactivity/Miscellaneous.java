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
}
