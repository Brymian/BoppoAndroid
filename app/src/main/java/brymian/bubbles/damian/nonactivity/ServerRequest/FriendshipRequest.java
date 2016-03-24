package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;

public class FriendshipRequest {

    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public FriendshipRequest(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }

    public void blockUser(Integer uid1, Integer uid2, StringCallback stringCallback)
    {
        pd.show();
        new BlockUser(uid1, uid2, stringCallback).execute();
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
            String url = httpConnection.getWebServerString() + "AndroidIO/FriendshipRequest.php?function=blockUser";

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
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }
}