package com.brymian.boppo.damian.nonactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.res.Resources;

import com.brymian.boppo.R;

/**
 * Created by Ziomster on 7/15/2015.
 */
public class DialogMessage {

    public static void showErrorLoggedIn(Activity activity) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        //dialogBuilder.setMessage(Resources.getSystem().getString(R.string.error_logged_in));
        dialogBuilder.setMessage(activity.getString(R.string.error_logged_in));
        dialogBuilder.setPositiveButton("Continue", null);
        dialogBuilder.show();
    }

    public static void showErrorConnection(Activity activity) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setMessage(Resources.getSystem().getString(R.string.error_connection));
        dialogBuilder.setPositiveButton("Continue", null);
        dialogBuilder.show();
    }

    public static void showErrorCustom(Activity activity, String error) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setMessage(error);
        dialogBuilder.setPositiveButton("Continue", null);
        dialogBuilder.show();
    }

    public static void showMessageRegistration(final Activity activity) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setMessage(activity.getString(R.string.message_registration));
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                FragmentManager fm = activity.getFragmentManager();
                fm.popBackStack();
            }
        });
        dialogBuilder.show();
    }
}
