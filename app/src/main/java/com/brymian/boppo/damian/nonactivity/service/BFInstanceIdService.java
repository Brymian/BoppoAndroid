package com.brymian.boppo.damian.nonactivity.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Ziomster on 6/20/2017.
 */

public class BFInstanceIdService extends FirebaseInstanceIdService
{
    private static final String TAG = "FInstanceIdService";

    @Override
    public void onTokenRefresh()
    {
        // Get updated InstanceID token.
        String frid = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed frid: " + frid);
    }
}
