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

/**
 * Created by Ziomster on 6/16/2016.
 */
public class MiscellaneousRequest {

    private HTTPConnection httpConnection = null;

    private ProgressDialog pd = null;

    public MiscellaneousRequest(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }

    public void incrementObjectViewCount(Integer oid, String objectTypeLabel, StringCallback stringCallback)
    {
        pd.show();
        new IncrementObjectViewCount(oid, objectTypeLabel, stringCallback).execute();
    }



    private class IncrementObjectViewCount extends AsyncTask<Void, Void, String> {

        Integer oid;
        String objectTypeLabel;
        StringCallback stringCallback;

        private IncrementObjectViewCount(Integer oid, String objectTypeLabel, StringCallback stringCallback)
        {
            this.oid = oid;
            this.objectTypeLabel = objectTypeLabel;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/MiscellaneousRequest.php?function=incrementObjectViewCount";

            Post request = new Post();

            try
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("oid", getNullOrValue(oid));
                jsonObject.put("objectTypeLabel", objectTypeLabel);

                String jsonString = jsonObject.toString();
                String response = request.post(url, jsonString);

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
