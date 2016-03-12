package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ObjectCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.isStringAnInteger;

/**
 * Created by Ziomster on 3/9/2016.
 */
public class Event {

    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public Event(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }

    public void createEvent(
        String eventName, int eventHostUid, String eventPrivacyLabel, String eventInviteTypeLabel,
        boolean eventImageUploadAllowedIndicator, String eventStartDatetime, String eventEndDatetime,
        double eventGpsLatitude, double eventGpsLongitude, StringCallback objectCallback)
    {
        pd.show();
        new CreateEvent(eventName, eventHostUid, eventPrivacyLabel, eventInviteTypeLabel,
                eventImageUploadAllowedIndicator, eventStartDatetime, eventEndDatetime,
                eventGpsLatitude, eventGpsLongitude, objectCallback).execute();
    }

    public void getEventIdentifier(int eventHostUid, String eventName, ObjectCallback objectCallback)
    {
        pd.show();
        new GetEventIdentifier(eventHostUid, eventName, objectCallback).execute();
    }

    private class CreateEvent extends AsyncTask<Void, Void, String> {

        String eventName;
        int eventHostUid;
        String eventPrivacyLabel;
        String eventInviteTypeLabel;
        boolean eventImageUploadAllowedIndicator;
        String eventStartDatetime;
        String eventEndDatetime;
        double eventGpsLatitude;
        double eventGpsLongitude;
        ObjectCallback objectCallback;

        private CreateEvent(String eventName, int eventHostUid, String eventPrivacyLabel,
                            String eventInviteTypeLabel, boolean eventImageUploadAllowedIndicator,
                            String eventStartDatetime, String eventEndDatetime,
                            double eventGpsLatitude, double eventGpsLongitude,
                            StringCallback objectCallback) {

            this.eventName = eventName;
            this.eventHostUid = eventHostUid;
            this.eventPrivacyLabel = eventPrivacyLabel;
            this.eventInviteTypeLabel = eventInviteTypeLabel;
            this.eventImageUploadAllowedIndicator = eventImageUploadAllowedIndicator;
            this.eventStartDatetime = eventStartDatetime;
            this.eventEndDatetime = eventEndDatetime;
            this.eventGpsLatitude = eventGpsLatitude;
            this.eventGpsLongitude = eventGpsLongitude;

            this.objectCallback = (ObjectCallback) objectCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/Event.php?function=createEvent";

            Post request = new Post();
            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eventName", getNullOrValue(eventName));
                jsonEventObject.put("eventHostUid", getNullOrValue(eventHostUid));
                jsonEventObject.put("eventPrivacyLabel", getNullOrValue(eventPrivacyLabel));
                jsonEventObject.put("eventInviteTypeLabel", getNullOrValue(eventInviteTypeLabel));
                jsonEventObject.put("eventImageUploadAllowedIndicator", getNullOrValue(eventImageUploadAllowedIndicator));
                jsonEventObject.put("eventStartDatetime", getNullOrValue(eventStartDatetime));
                jsonEventObject.put("eventEndDatetime", getNullOrValue(eventEndDatetime));
                jsonEventObject.put("eventGpsLatitude", getNullOrValue(eventGpsLatitude));
                jsonEventObject.put("eventGpsLongitude", getNullOrValue(eventGpsLongitude));

                String jsonEventString = jsonEventObject.toString();

                return request.post(url, jsonEventString);
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
            pd.dismiss();
            objectCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class GetEventIdentifier extends AsyncTask<Void, Void, Integer> {

        String eventName;
        int eventHostUid;
        ObjectCallback objectCallback;

        private GetEventIdentifier(int eventHostUid, String eventName, ObjectCallback objectCallback) {

            this.eventName = eventName;
            this.eventHostUid = eventHostUid;
            this.objectCallback = objectCallback;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/Event.php?function=getEventIdentifier";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eventHostUid", getNullOrValue(eventHostUid));
                jsonEventObject.put("eventName", getNullOrValue(eventName));

                String jsonEventString = jsonEventObject.toString();

                String response = request.post(url, jsonEventString);

                if (isStringAnInteger(response))
                    return Integer.parseInt(response.trim());
                else
                    return -1;
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();;
                return -11;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return -111;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            pd.dismiss();
            objectCallback.done(integer);

            super.onPostExecute(integer);
        }

    }
}
