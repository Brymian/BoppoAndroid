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



    public void subscribeDevice(Integer uid, StringCallback stringCallback)
    {
        String frid = FirebaseInstanceId.getInstance().getToken();
        new FirebaseRequest.SubscribeDevice(uid, frid, stringCallback).execute();
    }

    public void unsubscribeDevice(StringCallback stringCallback)
    {
        String frid = FirebaseInstanceId.getInstance().getToken();
        new FirebaseRequest.UnsubscribeDevice(frid, stringCallback).execute();
    }



    /*
    public void addDeviceToFirebaseAndDb(Integer uid, StringCallback stringCallback)
    {
        String frid = FirebaseInstanceId.getInstance().getToken();

        new FirebaseRequest.AddDeviceToFirebaseAndDb(uid, frid,
            stringCallback).execute();
    }
    */

    /*
    public void removeDeviceFromFirebaseAndDb(Integer uid, StringCallback stringCallback)
    {
        String frid = FirebaseInstanceId.getInstance().getToken();

        new FirebaseRequest.RemoveDeviceFromFirebaseAndDb(uid, frid,
            stringCallback).execute();
    }
    */

    /*
    public void getDeviceSubscribedTopics(StringCallback stringCallback)
    {
        String frid = FirebaseInstanceId.getInstance().getToken();

        new FirebaseRequest.GetDeviceSubscribedTopics(frid,
            stringCallback).execute();
    }

    public void removeDeviceFromFirebaseAndDb(StringCallback stringCallback)
    {
        String frid = FirebaseInstanceId.getInstance().getToken();

        new FirebaseRequest.RemoveDeviceFromFirebaseAndDb(frid,
            stringCallback).execute();
    }
    */

    private class SubscribeDevice extends AsyncTask<Void, Void, String>
    {
        Integer uid;
        String frid;
        StringCallback stringCallback;

        private SubscribeDevice(Integer uid, String frid, StringCallback stringCallback)
        {
            this.uid = uid;
            this.frid = frid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/FirebaseRequest.php?function=subscribeDevice";

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("uid", uid);
                jObject.put("frid", frid);

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

    private class UnsubscribeDevice extends AsyncTask<Void, Void, String>
    {
        String frid;
        StringCallback stringCallback;

        private UnsubscribeDevice(String frid, StringCallback stringCallback)
        {
            this.frid = frid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/FirebaseRequest.php?function=unsubscribeDevice";

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("frid", frid);

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

    /*
    private class AddDeviceToFirebaseAndDb extends AsyncTask<Void, Void, String> {

        Integer uid;
        String frid;
        StringCallback stringCallback;

        private AddDeviceToFirebaseAndDb(
            Integer uid, String frid, StringCallback stringCallback)
        {
            this.uid = uid;
            this.frid = frid;
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
                jObject.put("frid", frid);

                System.out.println("Sending out the following JSON for PHP's addDeviceToFirebaseAndDb:");
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
    */

    /*
    private class RemoveDeviceFromFirebaseAndDb extends AsyncTask<Void, Void, String> {

        Integer uid;
        String frid;
        StringCallback stringCallback;

        private RemoveDeviceFromFirebaseAndDb(
                Integer uid, String frid, StringCallback stringCallback)
        {
            this.uid = uid;
            this.frid = frid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                    "AndroidIO/FirebaseRequest.php?function=removeDeviceFromFirebaseAndDb";

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("uid", uid);
                jObject.put("frid", frid);

                System.out.println("Sending out the following JSON for PHP's removeDeviceFromFirebaseAndDb:");
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
    */

    /*
    private class GetDeviceSubscribedTopics extends AsyncTask<Void, Void, String> {

        String frid;
        StringCallback stringCallback;

        private GetDeviceSubscribedTopics(
            String frid, StringCallback stringCallback)
        {
            this.frid = frid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                    "AndroidIO/FirebaseRequest.php?function=getDeviceSubscribedTopics";

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("frid", frid);

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
    */

    /*
    private class RemoveDeviceFromFirebaseAndDb extends AsyncTask<Void, Void, String> {

        String frid;
        StringCallback stringCallback;

        private RemoveDeviceFromFirebaseAndDb(
            String frid, StringCallback stringCallback)
        {
            this.frid = frid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/FirebaseRequest.php?function=removeDeviceFromFirebaseAndDb";

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("frid", frid);

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
    */
}
