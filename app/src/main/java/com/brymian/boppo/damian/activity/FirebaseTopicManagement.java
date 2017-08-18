package com.brymian.boppo.damian.activity;

import android.app.Activity;
import android.os.Bundle;

import com.brymian.boppo.R;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.FirebaseRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ziomster on 8/3/2017.
 */

public class FirebaseTopicManagement extends Activity
{
    private final String API_ACCESS_KEY = "AAAAXYaD2lQ:APA91bFBAmMkugKsJ4Y7WjKwfcvkAG8kNvdHTCpZR4wYUvhUzw9PViugpfluCpsJB-pwVWHMdR7X94xICP4nmvg2eWYce2qsUfPAzueYs8kmrcRoAmpFRLiyvdakyJrYQsc80Oerz-B3";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        main();
    }

    public static void main()
    {
        System.out.println("---- START OF MAIN METHOD FOR THE FIREBASE TOPIC MANAGEMENT CLASS. ----");
        System.out.println("-- This device's Firebase Registration Identifier is: ");
        System.out.println(FirebaseInstanceId.getInstance().getToken());
        new FirebaseRequest().getDeviceSubscribedTopics(new StringCallback()
        {
            @Override
            public void done(String string)
            {
                System.out.println("-- String returned by getDeviceSubscribedTopics: --");
                System.out.println(string);

                if(string.contains("responseType"))
                {
                    System.out.println("-- The server returned the following message: --");
                    System.out.println(string);
                }
                else
                {
                    try
                    {
                        JSONArray jObject = new JSONObject(string).getJSONArray("topicNames");
                        String[] topicNames = new String[jObject.length()];
                        for (int i = 0; i < jObject.length(); i++)
                        {
                            topicNames[i] = jObject.getString(i);
                        }
                        unsubscribeFromAllTopics(topicNames);
                        System.out.println("-- Topics unsubscribed successfully. --");
                    }
                    catch (JSONException jsone)
                    {
                        jsone.printStackTrace();
                    }
                }
            }
        });
    }

    public static void unsubscribeFromAllTopics(String[] topicNames)
    {
        for (String topic : topicNames)
        {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
        }
    }
}
