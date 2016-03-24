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

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getBooleanObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getDoubleObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getIntegerObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getLongObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.isStringAnInteger;

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
/*
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
*/
    public void createEvent(
        Integer eventHostUid, String eventName, String eventPrivacyLabel, String eventInviteTypeLabel,
        Boolean eventImageUploadAllowedIndicator, String eventStartDatetime, String eventEndDatetime,
        Double eventGpsLatitude, Double eventGpsLongitude, StringCallback objectCallback)
    {
    pd.show();
    new CreateEvent(eventHostUid, eventName, eventPrivacyLabel, eventInviteTypeLabel,
        eventImageUploadAllowedIndicator, eventStartDatetime, eventEndDatetime,
        eventGpsLatitude, eventGpsLongitude, objectCallback).execute();
    }

    public void getEid(Integer eventHostUid, String eventName, IntegerCallback integerCallback)
    {
        pd.show();
        new GetEid(eventHostUid, eventName, integerCallback).execute();
    }

    public void getEventData(int eid, EventCallback eventCallback)
    {
        pd.show();
        new GetEventData(eid, eventCallback).execute();
    }

    public void deleteEvent(int eid, StringCallback stringCallback)
    {
        pd.show();
        new DeleteEvent(eid, stringCallback).execute();
    }

    private class CreateEvent extends AsyncTask<Void, Void, String> {

        Integer eventHostUid;
        String  eventName;
        String  eventPrivacyLabel;
        String  eventInviteTypeLabel;
        Boolean eventImageUploadAllowedIndicator;
        String  eventStartDatetime;
        String  eventEndDatetime;
        Double  eventGpsLatitude;
        Double  eventGpsLongitude;
        StringCallback stringCallback;

        private CreateEvent(Integer eventHostUid, String eventName, String eventPrivacyLabel,
                            String eventInviteTypeLabel, Boolean eventImageUploadAllowedIndicator,
                            String eventStartDatetime, String eventEndDatetime,
                            Double eventGpsLatitude, Double eventGpsLongitude,
                            StringCallback stringCallback) {

            this.eventHostUid = eventHostUid;
            this.eventName = eventName;
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

                jsonEventObject.put("eventHostUid", getNullOrValue(eventHostUid));
                jsonEventObject.put("eventName", getNullOrValue(eventName));
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

    private class GetEid extends AsyncTask<Void, Void, Integer> {

        Integer eventHostUid;
        String eventName;
        IntegerCallback integerCallback;

        private GetEid(Integer eventHostUid, String eventName, IntegerCallback integerCallback) {

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

                if (isStringAnInteger(response))
                    return Integer.parseInt(response.trim());
                else {
                    System.out.println(response);
                    return -1;
                }
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
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

        private GetEventData(int eid, EventCallback eventCallback)
        {
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

                Event event = new Event(
                    eid,
                    getIntegerObjectFromObject(jEvent.get("eventHostUid")),
                    jEvent.getString("eventName"),
                    jEvent.getString("eventInviteTypeLabel"),
                    jEvent.getString("eventPrivacyLabel"),
                    getBooleanObjectFromObject(jEvent.get("eventImageUploadAllowedIndicator")),
                    jEvent.getString("eventStartDatetime"),
                    jEvent.getString("eventEndDatetime"),
                    getDoubleObjectFromObject(jEvent.get("eventGpsLatitude")),
                    getDoubleObjectFromObject(jEvent.get("eventGpsLongitude")),
                    getIntegerObjectFromObject(jEvent.get("eventLikeCount")),
                    getIntegerObjectFromObject(jEvent.get("eventDislikeCount")),
                    getLongObjectFromObject(jEvent.get("eventViewCount"))
                );

                System.out.println("TESTING: ");
                System.out.println(response);

                return event;
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
        protected void onPostExecute(Event event) {
            pd.dismiss();
            eventCallback.done(event);

            super.onPostExecute(event);
        }

    }



    private class DeleteEvent extends AsyncTask<Void, Void, String> {

        int eid;
        StringCallback stringCallback;

        private DeleteEvent(int eid, StringCallback stringCallback)
        {
            this.eid = eid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=deleteEvent";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eid", getNullOrValue(eid));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

                if (response.equals("Success."))
                    return response;
                else
                    return "Failed.";
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
