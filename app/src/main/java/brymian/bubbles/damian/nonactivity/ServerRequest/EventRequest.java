package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.Event;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.IntegerCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.isStringAnInteger;

/**
 * Created by Ziomster on 3/9/2016.
 */
public class EventRequest {

    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public EventRequest(Activity activity) {
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

    public void getEid(int eventHostUid, String eventName, IntegerCallback integerCallback)
    {
        pd.show();
        new GetEid(eventHostUid, eventName, integerCallback).execute();
    }

    public void getEventData(int eid, EventCallback eventCallback)
    {
        pd.show();
        new GetEventData(eid, eventCallback).execute();
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
        StringCallback stringCallback;

        private CreateEvent(String eventName, int eventHostUid, String eventPrivacyLabel,
                            String eventInviteTypeLabel, boolean eventImageUploadAllowedIndicator,
                            String eventStartDatetime, String eventEndDatetime,
                            double eventGpsLatitude, double eventGpsLongitude,
                            StringCallback stringCallback) {

            this.eventName = eventName;
            this.eventHostUid = eventHostUid;
            this.eventPrivacyLabel = eventPrivacyLabel;
            this.eventInviteTypeLabel = eventInviteTypeLabel;
            this.eventImageUploadAllowedIndicator = eventImageUploadAllowedIndicator;
            this.eventStartDatetime = eventStartDatetime;
            this.eventEndDatetime = eventEndDatetime;
            this.eventGpsLatitude = eventGpsLatitude;
            this.eventGpsLongitude = eventGpsLongitude;

            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=createEvent";

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
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

    private class GetEid extends AsyncTask<Void, Void, Integer> {

        int eventHostUid;
        String eventName;
        IntegerCallback integerCallback;

        private GetEid(int eventHostUid, String eventName, IntegerCallback integerCallback) {

            this.eventHostUid = eventHostUid;
            this.eventName = eventName;
            this.integerCallback = integerCallback;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEid";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eventHostUid", getNullOrValue(eventHostUid));
                jsonEventObject.put("eventName", getNullOrValue(eventName));

                String jsonEventString = jsonEventObject.toString();

                String response = request.post(url, jsonEventString);

                System.out.println("TESTING: " + response);

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
            integerCallback.done(integer);

            super.onPostExecute(integer);
        }

    }



    private class GetEventData extends AsyncTask<Void, Void, Event> {

        int eid;
        EventCallback eventCallback;

        private GetEventData(int eid, EventCallback eventCallback) {

            this.eid = eid;
            this.eventCallback = eventCallback;
        }

        @Override
        protected Event doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventData";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eid", getNullOrValue(eid));

                String jsonEventString = jsonEventObject.toString();


                String response = request.post(url, jsonEventString);
                JSONObject jEvent = new JSONObject(response);

                System.out.println("GPS LATITUDE: " + jEvent.getString("eventGpsLatitude"));
                Event event = new Event(
                    eid,
                    Integer.valueOf(jEvent.getString("eventHostUid")),
                    jEvent.getString("eventName"),
                    jEvent.getString("eventInviteTypeLabel"),
                    jEvent.getString("eventPrivacyLabel"),
                    Boolean.parseBoolean(jEvent.getString("eventImageUploadAllowedIndicator")),
                    jEvent.getString("eventStartDatetime"),
                    jEvent.getString("eventEndDatetime"),
                    Double.valueOf(jEvent.getString("eventGpsLatitude")),
                    Double.valueOf(jEvent.getString("eventGpsLongitude")),
                    Integer.valueOf(jEvent.getString("eventLikeCount")),
                    Integer.valueOf(jEvent.getString("eventDislikeCount")),
                    Long.valueOf(jEvent.getString("eventViewCount"))
                );

                System.out.println("TESTING: ");
                System.out.println(response);

                return null;
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();;
                return null;
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Event event) {
            pd.dismiss();
            eventCallback.done(event);

            super.onPostExecute(event);
        }

    }

}
