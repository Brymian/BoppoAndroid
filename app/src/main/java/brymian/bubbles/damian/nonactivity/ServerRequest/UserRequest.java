package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.CustomException.SetOrNotException;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.convertPathsToFull;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;

public class UserRequest {

    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public UserRequest(Activity activity) {
        //pd = new ProgressDialog(activity);
        //pd.setCancelable(false);
        //pd.setTitle("Processing");
        //pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }



    public void setUser(Integer uid, String first_name, String last_name, String email,
        String phone, String user_account_privacy_label,
        Boolean[] setOrNot, StringCallback stringCallback) throws SetOrNotException
    {
        //pd.show();
        new SetUser(uid, first_name, last_name, email, phone, user_account_privacy_label,
            setOrNot, stringCallback).execute();
    }

    public void getUserProfileData(Integer uid, StringCallback stringCallback)
    {
        new GetUserProfileData(uid, stringCallback).execute();
    }

    public void getUsersSearchedByName(Integer searchedByUid, String searchedName,
       StringCallback stringCallback)
    {
        //pd.show();
        new GetUsersSearchedByName(searchedByUid, searchedName, stringCallback).execute();
    }

    public void getFriends(Integer uid, StringCallback stringCallback)
    {
        //pd.show();
        new GetFriends(uid, stringCallback).execute();
    }



    private class SetUser extends AsyncTask<Void, Void, String> {

        Integer uid;
        String firstName;
        String lastName;
        String email;
        String phone;
        String userPrivacyLabel;

        Map<String,Boolean> setOrNot = new HashMap<>();
        StringCallback stringCallback;

        private SetUser(Integer uid, String firstName, String lastName, String email,
            String phone, String userPrivacyLabel,
            Boolean[] setOrNot, StringCallback stringCallback) throws SetOrNotException
        {
            if (setOrNot.length != 6)
                throw new SetOrNotException("Incorrect quantity of booleans set in the SetOrNot array.");

            this.uid = uid;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
            this.userPrivacyLabel = userPrivacyLabel;

            this.setOrNot.put("uid", null);
            this.setOrNot.put("firstName", setOrNot[1]);
            this.setOrNot.put("lastName", setOrNot[2]);
            this.setOrNot.put("email", setOrNot[3]);
            this.setOrNot.put("phone", setOrNot[4]);
            this.setOrNot.put("userPrivacyLabel", setOrNot[5]);

            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/UserRequest.php?function=setUser";

            try
            {
                JSONObject jsonSetOrNotObject = new JSONObject(setOrNot);

                JSONObject jObject = new JSONObject();
                jObject.put("uid", getNullOrValue(uid));
                jObject.put("firstName", getNullOrValue(firstName));
                jObject.put("lastName", getNullOrValue(lastName));
                jObject.put("email", getNullOrValue(email));
                jObject.put("phone", getNullOrValue(phone));
                jObject.put("userPrivacyLabel", getNullOrValue(userPrivacyLabel));

                jObject.put("setOrNot", jsonSetOrNotObject);

                String jUser = jObject.toString();
                Post request = new Post();
                String response = request.post(url, jUser);

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
        protected void onPostExecute(String string) {
            //pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }
    }

    private class GetUserProfileData extends AsyncTask<Void, Void, String>
    {
        Integer uid;
        StringCallback stringCallback;

        private GetUserProfileData(Integer uid, StringCallback stringCallback)
        {
            this.uid = uid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() +
                "AndroidIO/UserRequest.php?function=getUserProfileData";

            Post request = new Post();

            try
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", getNullOrValue(uid));
                String jsonString = jsonObject.toString();

                String response = request.post(url, jsonString);

                return convertPathsToFull(response);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String string)
        {
            //pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class GetUsersSearchedByName extends AsyncTask<Void, Void, String> {

        Integer searchedByUid;
        String  searchedName;
        StringCallback stringCallback;

        private GetUsersSearchedByName(Integer searchedByUid, String searchedName,
            StringCallback stringCallback)
        {
            this.searchedByUid = searchedByUid;
            this.searchedName = searchedName;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() +
                "AndroidIO/UserRequest.php?function=getUsersSearchedByName";

            Post request = new Post();

            try
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("searchedByUid", getNullOrValue(searchedByUid));
                jsonObject.put("searchedName", getNullOrValue(searchedName));
                String jsonString = jsonObject.toString();

                String response = request.post(url, jsonString);

                return convertPathsToFull(response);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String string)
        {
            //pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class GetFriends extends AsyncTask<Void, Void, String> {

        Integer uid;
        StringCallback stringCallback;

        private GetFriends(Integer uid, StringCallback stringCallback)
        {
            this.uid = uid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() + "AndroidIO/UserRequest.php?function=getFriends";

            Post request = new Post();

            try
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", getNullOrValue(uid));
                String jsonString = jsonObject.toString();

                String response = request.post(url, jsonString);

                return convertPathsToFull(response);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String string)
        {
            //pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }
}
