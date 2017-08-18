package com.brymian.boppo.damian.nonactivity.ServerRequest;

import android.content.Context;
import android.os.AsyncTask;

import com.brymian.boppo.damian.nonactivity.Connection.HTTPConnection;
import com.brymian.boppo.damian.nonactivity.Post;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Ziomster on 7/13/2017.
 */

public class FirebaseRequest
{
    private HTTPConnection httpConnection = null;
    //private Context context = null;     // Make sure the Application context is being used

    //public FirebaseRequest(Context context) {
    public FirebaseRequest() {
        httpConnection = new HTTPConnection();
        //this.context = context;
    }



    public void addDeviceToFirebaseAndDb(Integer uid, StringCallback stringCallback)
    {
        String firebaseRegistrationIdentifier = FirebaseInstanceId.getInstance().getToken();

        new FirebaseRequest.AddDeviceToFirebaseAndDb(uid, firebaseRegistrationIdentifier,
            stringCallback).execute();
    }



    public void getDeviceSubscribedTopics(StringCallback stringCallback)
    {
        String firebaseRegistrationIdentifier = FirebaseInstanceId.getInstance().getToken();

        new FirebaseRequest.GetDeviceSubscribedTopics(firebaseRegistrationIdentifier,
            stringCallback).execute();
    }



    public void deleteDeviceFromFirebaseAndDb(StringCallback stringCallback)
    {
        String firebaseRegistrationIdentifier = FirebaseInstanceId.getInstance().getToken();

        new FirebaseRequest.DeleteDeviceFromFirebaseAndDb(firebaseRegistrationIdentifier,
            stringCallback).execute();
    }



    private class AddDeviceToFirebaseAndDb extends AsyncTask<Void, Void, String> {

        Integer uid;
        String firebaseRegistrationIdentifier;
        StringCallback stringCallback;

        private AddDeviceToFirebaseAndDb(
            Integer uid, String firebaseRegistrationIdentifier, StringCallback stringCallback)
        {
            this.uid = uid;
            this.firebaseRegistrationIdentifier = firebaseRegistrationIdentifier;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/FirebaseRequest.php?function=addDeviceToFirebaseAndDb";

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("uid", uid);
                jObject.put("firebaseRegistrationIdentifier", firebaseRegistrationIdentifier);

                System.out.println("Sending out the following JSON for PHP's addDeviceToFcmAndDb:");
                System.out.println(jObject.toString());

                Post request = new Post();
                String response = request.post(url, jObject.toString());

                return response;
            }
            catch (IOException ioe)
            {
                return ioe.toString();
            }
            catch (JSONException jsone)
            {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string)
        {
            stringCallback.done(string);
            super.onPostExecute(string);
        }
    }



    private class GetDeviceSubscribedTopics extends AsyncTask<Void, Void, String> {

        String firebaseRegistrationIdentifier;
        StringCallback stringCallback;

        private GetDeviceSubscribedTopics(
            String firebaseRegistrationIdentifier, StringCallback stringCallback)
        {
            this.firebaseRegistrationIdentifier = firebaseRegistrationIdentifier;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                    "AndroidIO/FirebaseRequest.php?function=getDeviceSubscribedTopics";

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("firebaseRegistrationIdentifier", firebaseRegistrationIdentifier);

                System.out.println("Sending out the following JSON for PHP's getDeviceSubscribedTopics:");
                System.out.println(jObject.toString());

                Post request = new Post();
                String response = request.post(url, jObject.toString());

                return response;
            }
            catch (IOException ioe)
            {
                return ioe.toString();
            }
            catch (JSONException jsone)
            {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string)
        {
            stringCallback.done(string);
            super.onPostExecute(string);
        }
    }



    private class DeleteDeviceFromFirebaseAndDb extends AsyncTask<Void, Void, String> {

        String firebaseRegistrationIdentifier;
        StringCallback stringCallback;

        private DeleteDeviceFromFirebaseAndDb(
            String firebaseRegistrationIdentifier, StringCallback stringCallback)
        {
            this.firebaseRegistrationIdentifier = firebaseRegistrationIdentifier;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                    "AndroidIO/FirebaseRequest.php?function=deleteDeviceFromFirebaseAndDb";

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("firebaseRegistrationIdentifier", firebaseRegistrationIdentifier);

                System.out.println("Sending out the following JSON for PHP's deleteDeviceFromFirebaseAndDb:");
                System.out.println(jObject.toString());

                Post request = new Post();
                String response = request.post(url, jObject.toString());

                return response;
            }
            catch (IOException ioe)
            {
                return ioe.toString();
            }
            catch (JSONException jsone)
            {
                return jsone.toString();
            }
        }

        @Override
        protected void onPostExecute(String string)
        {
            stringCallback.done(string);
            super.onPostExecute(string);
        }
    }
}
