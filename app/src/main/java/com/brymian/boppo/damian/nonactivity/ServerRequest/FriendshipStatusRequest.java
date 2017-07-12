package com.brymian.boppo.damian.nonactivity.ServerRequest;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.brymian.boppo.damian.nonactivity.Connection.HTTPConnection;
import com.brymian.boppo.damian.nonactivity.Post;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;

import static com.brymian.boppo.damian.nonactivity.Miscellaneous.convertPathsToFull;
import static com.brymian.boppo.damian.nonactivity.Miscellaneous.getNullOrValue;

public class FriendshipStatusRequest {

    private HTTPConnection httpConnection = null;

    public FriendshipStatusRequest() {
        httpConnection = new HTTPConnection();
    }

    public void getFriendshipStatusRequestSentUsers(Integer uid1, String userRelationshipTypeLabel,
                                                    StringCallback stringCallback)
    {
        new GetFriendshipStatusRequestSentUsers(uid1, userRelationshipTypeLabel, stringCallback).execute();
    }

    public void getFriendshipStatusRequestReceivedUsers(Integer uid2, String userRelationshipTypeLabel,
        StringCallback stringCallback)
    {
        new GetFriendshipStatusRequestReceivedUsers(uid2, userRelationshipTypeLabel, stringCallback).execute();
    }

    public void blockUser(Integer uid1, Integer uid2, StringCallback stringCallback)
    {
        new BlockUser(uid1, uid2, stringCallback).execute();
    }

    public void unblockUser(Integer uid1, Integer uid2, StringCallback stringCallback)
    {
        new UnblockUser(uid1, uid2, stringCallback).execute();
    }

    public void rejectFriend(Integer uid1, Integer uid2, StringCallback stringCallback)
    {
        new RejectFriend(uid1, uid2, stringCallback).execute();
    }

    public void cancelFriend(Integer uid1, Integer uid2, StringCallback stringCallback)
    {
        new CancelFriend(uid1, uid2, stringCallback).execute();
    }

    public void unFriend(Integer uid1, Integer uid2, StringCallback stringCallback)
    {
        new UnFriend(uid1, uid2, stringCallback).execute();
    }

    private class GetFriendshipStatusRequestSentUsers extends AsyncTask<Void, Void, String> {

        Integer uid1;
        String userRelationshipTypeLabel;
        StringCallback stringCallback;

        private GetFriendshipStatusRequestSentUsers(Integer uid1, String userRelationshipTypeLabel,
            StringCallback stringCallback)
        {
            this.uid1 = uid1;
            this.userRelationshipTypeLabel = userRelationshipTypeLabel;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/FriendshipStatusRequest.php?function=getFriendshipStatusRequestSentUsers";

            Post request = new Post();

            try
            {
                JSONObject jsonUserRelationshipObject = new JSONObject();
                jsonUserRelationshipObject.put("uid1", getNullOrValue(uid1));
                jsonUserRelationshipObject.put("userRelationshipTypeLabel", getNullOrValue(userRelationshipTypeLabel));

                String jsonEventString = jsonUserRelationshipObject.toString();
                String response = request.post(url, jsonEventString);

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
        protected void onPostExecute(String string) {
            stringCallback.done(string);

            super.onPostExecute(string);
        }
    }



    private class GetFriendshipStatusRequestReceivedUsers extends AsyncTask<Void, Void, String> {

        Integer uid2;
        String userRelationshipTypeLabel;
        StringCallback stringCallback;

        private GetFriendshipStatusRequestReceivedUsers(Integer uid2, String userRelationshipTypeLabel,
            StringCallback stringCallback)
        {
            this.uid2 = uid2;
            this.userRelationshipTypeLabel = userRelationshipTypeLabel;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/FriendshipStatusRequest.php?function=getFriendshipStatusRequestReceivedUsers";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("uid2", getNullOrValue(uid2));
                jsonEventObject.put("userRelationshipTypeLabel", getNullOrValue(userRelationshipTypeLabel));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

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
        protected void onPostExecute(String string) {
            stringCallback.done(string);

            super.onPostExecute(string);
        }
    }



    private class BlockUser extends AsyncTask<Void, Void, String> {

        Integer uid1;
        Integer uid2;
        StringCallback stringCallback;

        private BlockUser(Integer uid1, Integer uid2, StringCallback stringCallback) {
            this.uid1 = uid1;
            this.uid2 = uid2;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/FriendshipStatusRequest.php?function=blockUser";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("uid1", getNullOrValue(uid1));
                jsonEventObject.put("uid2", getNullOrValue(uid2));

                String jsonEventString = jsonEventObject.toString();

                return request.post(url, jsonEventString);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
        }

        @Override
        protected void onPostExecute(String string) {
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }



    private class UnblockUser extends AsyncTask<Void, Void, String> {

        Integer uid1;
        Integer uid2;
        StringCallback stringCallback;

        private UnblockUser(Integer uid1, Integer uid2, StringCallback stringCallback) {
            this.uid1 = uid1;
            this.uid2 = uid2;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/FriendshipStatusRequest.php?function=unblockUser";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("uid1", getNullOrValue(uid1));
                jsonEventObject.put("uid2", getNullOrValue(uid2));

                String jsonEventString = jsonEventObject.toString();

                return request.post(url, jsonEventString);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
        }

        @Override
        protected void onPostExecute(String string) {
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }



    private class RejectFriend extends AsyncTask<Void, Void, String> {

        Integer uid1;
        Integer uid2;
        StringCallback stringCallback;

        private RejectFriend(Integer uid1, Integer uid2, StringCallback stringCallback) {
            this.uid1 = uid1;
            this.uid2 = uid2;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/FriendshipStatusRequest.php?function=rejectFriend";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("uid1", getNullOrValue(uid1));
                jsonEventObject.put("uid2", getNullOrValue(uid2));

                String jsonEventString = jsonEventObject.toString();

                return request.post(url, jsonEventString);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
        }

        @Override
        protected void onPostExecute(String string) {
            stringCallback.done(string);
            super.onPostExecute(string);
        }

    }



    private class CancelFriend extends AsyncTask<Void, Void, String> {

        Integer uid1;
        Integer uid2;
        StringCallback stringCallback;

        private CancelFriend(Integer uid1, Integer uid2, StringCallback stringCallback) {
            this.uid1 = uid1;
            this.uid2 = uid2;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/FriendshipStatusRequest.php?function=cancelFriend";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("uid1", getNullOrValue(uid1));
                jsonEventObject.put("uid2", getNullOrValue(uid2));

                String jsonEventString = jsonEventObject.toString();

                return request.post(url, jsonEventString);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
        }

        @Override
        protected void onPostExecute(String string) {
            stringCallback.done(string);
            super.onPostExecute(string);
        }

    }



    private class UnFriend extends AsyncTask<Void, Void, String> {

        Integer uid1;
        Integer uid2;
        StringCallback stringCallback;

        private UnFriend(Integer uid1, Integer uid2, StringCallback stringCallback) {
            this.uid1 = uid1;
            this.uid2 = uid2;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/FriendshipStatusRequest.php?function=unFriend";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("uid1", getNullOrValue(uid1));
                jsonEventObject.put("uid2", getNullOrValue(uid2));

                String jsonEventString = jsonEventObject.toString();

                return request.post(url, jsonEventString);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
        }

        @Override
        protected void onPostExecute(String string) {
            stringCallback.done(string);
            super.onPostExecute(string);
        }

    }
}