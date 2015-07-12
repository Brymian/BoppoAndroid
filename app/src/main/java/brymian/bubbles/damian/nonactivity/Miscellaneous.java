package brymian.bubbles.damian.nonactivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import brymian.bubbles.R;

/**
 * Created by Ziomster on 7/12/2015.
 */
public class Miscellaneous {

    public static void startFragment(FragmentManager fm, Fragment fragment) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.AuthenticateFragment, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
