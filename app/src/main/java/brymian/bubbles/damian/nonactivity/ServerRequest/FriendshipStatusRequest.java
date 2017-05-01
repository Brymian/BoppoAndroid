package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.objects.User;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getBooleanObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getDoubleObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getIntegerObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getLongObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;

public class FriendshipStatusRequest {

    private HTTPConnection httpConnection = null;

    public FriendshipStatusRequest() {
        httpConnection = new HTTPConnection();
    }

    public void getFriendshipStatusRequestSentUsers(Integer uid1, String userRelationshipTypeLabel,
        UserListCallback userListCallback)
    {
        new GetFriendshipStatusRequestSentUsers(uid1, userRelationshipTypeLabel, userListCallback).execute();
    }

    public void getFriendshipStatusRequestReceivedUsers(Integer uid2, String userRelationshipTypeLabel,
        UserListCallback userListCallback)
    {
        new GetFriendshipStatusRequestReceivedUsers(uid2, userRelationshipTypeLabel, userListCallback).execute();
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

    private class GetFriendshipStatusRequestSentUsers extends AsyncTask<Void, Void, List<User>> {

        Integer uid1;
        String userRelationshipTypeLabel;
        UserListCallback userListCallback;

        private GetFriendshipStatusRequestSentUsers(Integer uid1, String userRelationshipTypeLabel,
            UserListCallback userListCallback)
        {
            this.uid1 = uid1;
            this.userRelationshipTypeLabel = userRelationshipTypeLabel;
            this.userListCallback = userListCallback;
        }

        @Override
        protected List<User> doInBackground(Void... params) {
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

                JSONArray jUserArray = new JSONArray(response);

                List<User> users = new ArrayList<>();
                for (int i = 0; i < jUserArray.length(); i++)
                {
                    JSONObject jUser = jUserArray.getJSONObject(i);
                    User user = new User();
                    user.setUser(
                        getIntegerObjectFromObject(jUser.get("uid")),
                        null,
                        null,
                        jUser.getString("username"),
                        null,
                        jUser.getString("firstName"),
                        jUser.getString("lastName"),
                        null,
                        null,
                        null,
                        null
                    );
                    users.add(user);
                }
                return users;
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
        protected void onPostExecute(List<User> users) {
            userListCallback.done(users);

            super.onPostExecute(users);
        }
    }



    private class GetFriendshipStatusRequestReceivedUsers extends AsyncTask<Void, Void, List<User>> {

        Integer uid2;
        String userRelationshipTypeLabel;
        UserListCallback userListCallback;

        private GetFriendshipStatusRequestReceivedUsers(Integer uid2, String userRelationshipTypeLabel,
            UserListCallback userListCallback)
        {
            this.uid2 = uid2;
            this.userRelationshipTypeLabel = userRelationshipTypeLabel;
            this.userListCallback = userListCallback;
        }

        @Override
        protected List<User> doInBackground(Void... params) {
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
                System.out.println("THE RESPONSE: " + response);

                JSONArray jUserArray = new JSONArray(response);

                List<User> users = new ArrayList<>();
                for (int i = 0; i < jUserArray.length(); i++)
                {
                    JSONObject jUser = jUserArray.getJSONObject(i);
                    User user = new User();
                    user.setUser(
                        getIntegerObjectFromObject(jUser.get("uid")),
                        null,
                        null,
                        jUser.getString("username"),
                        null,
                        jUser.getString("firstName"),
                        jUser.getString("lastName"),
                        null,
                        null,
                        null,
                        null
                    );
                    users.add(user);
                }
                return users;
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
        protected void onPostExecute(List<User> users) {
            userListCallback.done(users);

            super.onPostExecute(users);
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