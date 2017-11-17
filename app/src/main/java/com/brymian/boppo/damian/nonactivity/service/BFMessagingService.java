package com.brymian.boppo.damian.nonactivity.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Ziomster on 6/20/2017.
 */

public class BFMessagingService extends FirebaseMessagingService
{
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());
        Log.d(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Icon: " + remoteMessage.getNotification().getIcon());
        Log.d(TAG, "Notification Sound: " + remoteMessage.getNotification().getSound());
    }
}