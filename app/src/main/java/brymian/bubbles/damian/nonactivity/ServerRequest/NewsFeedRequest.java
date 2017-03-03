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

import static brymian.bubbles.damian.nonactivity.Miscellaneous.convertPathsToFull;

public class NewsFeedRequest
{
    private HTTPConnection httpConnection = null;

    private ProgressDialog pd = null;

    public NewsFeedRequest(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }



    public void getNewsEvents(Integer uid, Integer max, StringCallback stringCallback)
    {
        pd.show();
        new GetNewsEvents(uid, max, stringCallback).execute();
    }



    private class GetNewsEvents extends AsyncTask<Void, Void, String> {

        Integer uid;
        Integer max;
        StringCallback stringCallback;

        private GetNewsEvents(Integer uid, Integer max, StringCallback stringCallback)
        {
            this.uid = uid;
            this.max = max;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/NewsFeedRequest.php?function=getNewsEvents";

            Post request = new Post();

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("uid", uid);
                jObject.put("max", max);

                String jString = jObject.toString();
                String response = convertPathsToFull(request.post(url, jString));

                return response;
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return ioe.toString();
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return jsone.toString();
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
