package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;

/**
 * Created by Ziomster on 3/23/2017.
 */

public class EventUserImageRequest
{
    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public EventUserImageRequest(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }

    public void addImagesToEvent(Integer eid, List<Integer> uiids, StringCallback stringCallback) {
        pd.show();
        new EventUserImageRequest.AddImagesToEvent(eid, uiids, stringCallback).execute();
    }

    public void setEuiEventProfileSequence(Integer eid, Integer uiid,
        Short euiEventProfileSequence, StringCallback stringCallback)
    {
        pd.show();
        new SetEuiEventProfileSequence(eid, uiid, euiEventProfileSequence,
            stringCallback).execute();
    }





    private class AddImagesToEvent extends AsyncTask<Void, Void, String> {

        Integer eid;
        List<Integer> uiids;
        StringCallback stringCallback;

        private AddImagesToEvent(Integer eid, List<Integer> uiids, StringCallback stringCallback) {
            this.eid = eid;
            this.uiids = uiids;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() + "AndroidIO/UserImageRequest.php?function=addImagesToEvent";

            Post request = new Post();

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("eid", getNullOrValue(eid));
                JSONArray jsonArray = new JSONArray(uiids);
                jsonObject.put("uiids", jsonArray);
                String jsonString = jsonObject.toString();

                String response = request.post(url, jsonString);

                return response;
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
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }
    }

    private class SetEuiEventProfileSequence extends AsyncTask<Void, Void, String> {

        Integer eid;
        Integer uiid;
        Short euiEventProfileSequence;
        StringCallback stringCallback;

        private SetEuiEventProfileSequence(Integer eid, Integer uiid,
            Short euiEventProfileSequence, StringCallback stringCallback)
        {
            this.eid = eid;
            this.uiid = uiid;
            this.euiEventProfileSequence = euiEventProfileSequence;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = httpConnection.getWebServerString() +
                "AndroidIO/EventUserImageRequest.php?function=setEuiEventProfileSequence";

            Post request = new Post();

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("eid", eid);
                jsonObject.put("uiid", uiid);
                jsonObject.put("euiEventProfileSequence", getNullOrValue(euiEventProfileSequence));
                String jsonString = jsonObject.toString();

                String response = request.post(url, jsonString);

                return response;
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
            pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }
    }
}
