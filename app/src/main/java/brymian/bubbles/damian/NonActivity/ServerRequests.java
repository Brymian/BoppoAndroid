package brymian.bubbles.damian.NonActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Ziomster on 5/22/2015.
 */
public class ServerRequests {

    ProgressDialog pd;
    public static final int CONNECTION_TIMEOUT = 1000 * 10;
    public static final String SERVER_ADDRESS = "http://73.194.170.63:8080/ProjectWolf";
//
    public ServerRequests(Context context) {
        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
    }
//
    public void storeUserDataInBackground(User user, GetUserCallback userCallback) {
        pd.show();
        new StoreUserDataAsyncTask(user, userCallback).execute();
    }
//
    public void getUserDataAsyncTask(User user, GetUserCallback userCallBack) {
        pd.show();
        new GetUserDataAsyncTask(user, userCallBack).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {

            String url = SERVER_ADDRESS + "/Database/register.php";

            Post post = new Post();
            String json = post.jsonRegister(user.username, user.password);

            String response = "";
            try {
                response = post.post(url, json);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            if (response.length() > 0) {
                System.out.println(response);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
            userCallback.done(null);

            super.onPostExecute(aVoid);
        }
    }

    public class GetUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        public GetUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... params) {

            String url = SERVER_ADDRESS + "/Database/getUserExists.php";

            Post post = new Post();
            String json = post.jsonGetUserExists(user.username, user.password);

            User returnedUser = null;
            String response = "";

            try {
                response = post.post(url, json);
                JSONObject jObject = new JSONObject(response);

                if (jObject.length() == 0) {
                    returnedUser = null;
                }
                else {
                    int count = jObject.getInt("count");
                    if (count == 1) {
                        returnedUser = new User(user.username, user.password);
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (JSONException jsone) {
                jsone.printStackTrace();
            }

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            pd.dismiss();
            userCallback.done(returnedUser);

            super.onPostExecute(returnedUser);
        }
    }
}