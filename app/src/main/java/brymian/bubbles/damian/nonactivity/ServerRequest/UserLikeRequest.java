package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.IntegerCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;

public class UserLikeRequest
{
    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public UserLikeRequest(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }

    public void setObjectLikeOrDislike(Integer uid, String objectTypeLabel, Integer oid,
       Boolean userLikeIndicator, StringCallback stringCallback)
    {
        pd.show();
        new SetObjectLikeOrDislike(uid, objectTypeLabel, oid, userLikeIndicator, stringCallback).execute();
    }




    private class SetObjectLikeOrDislike extends AsyncTask<Void, Void, String> {

        Integer uid;
        String  objectTypeLabel;
        Integer oid;
        Boolean userLikeIndicator;
        StringCallback stringCallback;

        private SetObjectLikeOrDislike(Integer uid, String objectTypeLabel, Integer oid, Boolean userLikeIndicator, StringCallback stringCallback)
        {
            this.uid = uid;
            this.objectTypeLabel = objectTypeLabel;
            this.oid = oid;
            this.userLikeIndicator = userLikeIndicator;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/UserLikeRequest.php?function=setObjectLikeOrDislike";

            Post request = new Post();

            try
            {
                JSONObject jsonUserLikeObject = new JSONObject();
                jsonUserLikeObject.put("uid", getNullOrValue(uid));
                jsonUserLikeObject.put("objectTypeLabel", getNullOrValue(objectTypeLabel));
                jsonUserLikeObject.put("oid", getNullOrValue(oid));
                jsonUserLikeObject.put("userLikeIndicator", getNullOrValue(userLikeIndicator.toString()));

                String jsonUserLikeString = jsonUserLikeObject.toString();
                System.out.println("THE STRING: " + jsonUserLikeString);
                String response = request.post(url, jsonUserLikeString);

                return response;
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
