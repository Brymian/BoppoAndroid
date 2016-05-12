package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.objects.Event;
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

    public void getEventDataByMember(Integer uid, EventListCallback eventListCallback)
    {
        pd.show();
        new GetEventDataByMember(uid, eventListCallback).execute();
    }

    public void getEventDataByName(String eventName, EventListCallback eventListCallback)
    {
        pd.show();
        new GetEventDataByName(eventName, eventListCallback).execute();
    }

    public void getEventDataByTopNViews(Integer topN, EventListCallback eventListCallback)
    {
        pd.show();
        new GetEventDataByTopNViews(topN, eventListCallback).execute();
    }

    public void getEventDataByTopNLikes(Integer topN, EventListCallback eventListCallback)
    {
        pd.show();
        new GetEventDataByTopNLikes(topN, eventListCallback).execute();
    }

    public void getEventDataByTopNDislikes(Integer topN, EventListCallback eventListCallback)
    {
        pd.show();
        new GetEventDataByTopNDislikes(topN, eventListCallback).execute();
    }

    public void updateEvent(
        Integer eid, Integer eventHostUid, String eventName, String eventPrivacyLabel,
        String eventInviteTypeLabel, Boolean eventImageUploadAllowedIndicator,
        String eventStartDatetime, String eventEndDatetime,
        Double eventGpsLatitude, Double eventGpsLongitude, StringCallback objectCallback)
    {
        pd.show();
        new UpdateEvent(eid, eventHostUid, eventName, eventPrivacyLabel, eventInviteTypeLabel,
            eventImageUploadAllowedIndicator, eventStartDatetime, eventEndDatetime,
            eventGpsLatitude, eventGpsLongitude, objectCallback).execute();
    }

    public void deleteEvent(int eid, StringCallback stringCallback)
    {
        pd.show();
        new DeleteEvent(eid, stringCallback).execute();
    }

    public void incrementEventViewCount(Integer eid, StringCallback stringCallback)
    {
        pd.show();
        new IncrementEventViewCount(eid, stringCallback).execute();
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
            StringCallback stringCallback)
        {

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
                jsonEventObject.put("eventImageUploadAllowedIndicator",
                    getNullOrValue(eventImageUploadAllowedIndicator));
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
                    null,
                    null,
                    null,
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



    private class GetEventDataByMember extends AsyncTask<Void, Void, List<Event>> {

        Integer uid;
        EventListCallback eventListCallback;

        private GetEventDataByMember(Integer uid, EventListCallback eventListCallback)
        {
            this.uid = uid;
            this.eventListCallback = eventListCallback;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByMember";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("uid", getNullOrValue(uid));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

                JSONArray jEventArray = new JSONArray(response);

                List<Event> eventList = new ArrayList<>();
                for (int i = 0; i < jEventArray.length(); i++)
                {
                    JSONObject jEvent = jEventArray.getJSONObject(i);
                    Event event = new Event(
                        getIntegerObjectFromObject(jEvent.get("eid")),
                        getIntegerObjectFromObject(jEvent.get("eventHostUid")),
                        jEvent.getString("eventName"),
                        jEvent.getString("eventHostUsername"),
                        jEvent.getString("eventHostFirstName"),
                        jEvent.getString("eventHostLastName"),
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
                    eventList.add(event);
                }

                return eventList;
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
        protected void onPostExecute(List<Event> eventList) {
            pd.dismiss();
            eventListCallback.done(eventList);

            super.onPostExecute(eventList);
        }

    }



    private class GetEventDataByName extends AsyncTask<Void, Void, List<Event>> {

        String eventName;
        EventListCallback eventListCallback;

        private GetEventDataByName(String eventName, EventListCallback eventListCallback)
        {
            this.eventName = eventName;
            this.eventListCallback = eventListCallback;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByName";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eventName", getNullOrValue(eventName));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

                JSONArray jEventArray = new JSONArray(response);

                List<Event> eventList = new ArrayList<>();
                for (int i = 0; i < jEventArray.length(); i++)
                {
                    JSONObject jEvent = jEventArray.getJSONObject(i);
                    Event event = new Event(
                        getIntegerObjectFromObject(jEvent.get("eid")),
                            getIntegerObjectFromObject(jEvent.get("eventHostUid")),
                        jEvent.getString("eventName"),
                        jEvent.getString("eventHostUsername"),
                        jEvent.getString("eventHostFirstName"),
                        jEvent.getString("eventHostLastName"),
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
                    eventList.add(event);
                }

                return eventList;
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
        protected void onPostExecute(List<Event> eventList) {
            pd.dismiss();
            eventListCallback.done(eventList);

            super.onPostExecute(eventList);
        }

    }



    private class GetEventDataByTopNViews extends AsyncTask<Void, Void, List<Event>> {

        Integer topNViews;
        EventListCallback eventListCallback;

        private GetEventDataByTopNViews(Integer topNViews, EventListCallback eventListCallback)
        {
            this.topNViews = topNViews;
            this.eventListCallback = eventListCallback;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByTopNViews";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("topNViews", getNullOrValue(topNViews));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

                JSONArray jEventArray = new JSONArray(response);

                List<Event> eventList = new ArrayList<>();
                for (int i = 0; i < jEventArray.length(); i++)
                {
                    JSONObject jEvent = jEventArray.getJSONObject(i);
                    Event event = new Event(
                            getIntegerObjectFromObject(jEvent.get("eid")),
                            getIntegerObjectFromObject(jEvent.get("eventHostUid")),
                            jEvent.getString("eventName"),
                            jEvent.getString("eventHostUsername"),
                            jEvent.getString("eventHostFirstName"),
                            jEvent.getString("eventHostLastName"),
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
                    eventList.add(event);
                }

                return eventList;
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
        protected void onPostExecute(List<Event> eventList) {
            pd.dismiss();
            eventListCallback.done(eventList);

            super.onPostExecute(eventList);
        }

    }



    private class GetEventDataByTopNLikes extends AsyncTask<Void, Void, List<Event>> {

        Integer topN;
        EventListCallback eventListCallback;

        private GetEventDataByTopNLikes(Integer topN, EventListCallback eventListCallback)
        {
            this.topN = topN;
            this.eventListCallback = eventListCallback;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByTopNLikes";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("topN", getNullOrValue(topN));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

                JSONArray jEventArray = new JSONArray(response);

                List<Event> eventList = new ArrayList<>();
                for (int i = 0; i < jEventArray.length(); i++)
                {
                    JSONObject jEvent = jEventArray.getJSONObject(i);
                    Event event = new Event(
                            getIntegerObjectFromObject(jEvent.get("eid")),
                            getIntegerObjectFromObject(jEvent.get("eventHostUid")),
                            jEvent.getString("eventName"),
                            jEvent.getString("eventHostUsername"),
                            jEvent.getString("eventHostFirstName"),
                            jEvent.getString("eventHostLastName"),
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
                    eventList.add(event);
                }

                return eventList;
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
        protected void onPostExecute(List<Event> eventList) {
            pd.dismiss();
            eventListCallback.done(eventList);

            super.onPostExecute(eventList);
        }

    }



    private class GetEventDataByTopNDislikes extends AsyncTask<Void, Void, List<Event>> {

        Integer topN;
        EventListCallback eventListCallback;

        private GetEventDataByTopNDislikes(Integer topN, EventListCallback eventListCallback)
        {
            this.topN = topN;
            this.eventListCallback = eventListCallback;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByTopNDislikes";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("topN", getNullOrValue(topN));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

                JSONArray jEventArray = new JSONArray(response);

                List<Event> eventList = new ArrayList<>();
                for (int i = 0; i < jEventArray.length(); i++)
                {
                    JSONObject jEvent = jEventArray.getJSONObject(i);
                    Event event = new Event(
                        getIntegerObjectFromObject(jEvent.get("eid")),
                            getIntegerObjectFromObject(jEvent.get("eventHostUid")),
                        jEvent.getString("eventName"),
                        jEvent.getString("eventHostUsername"),
                        jEvent.getString("eventHostFirstName"),
                        jEvent.getString("eventHostLastName"),
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
                    eventList.add(event);
                }

                return eventList;
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
        protected void onPostExecute(List<Event> eventList) {
            pd.dismiss();
            eventListCallback.done(eventList);

            super.onPostExecute(eventList);
        }

    }



    private class UpdateEvent extends AsyncTask<Void, Void, String> {

        Integer eid;
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

        private UpdateEvent(Integer eid, Integer eventHostUid, String eventName, String eventPrivacyLabel,
            String eventInviteTypeLabel, Boolean eventImageUploadAllowedIndicator,
            String eventStartDatetime, String eventEndDatetime,
            Double eventGpsLatitude, Double eventGpsLongitude,
            StringCallback stringCallback)
        {
            this.eid = eid;
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
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=updateEvent";

            Post request = new Post();
            try
            {
                JSONObject jsonEventObject = new JSONObject();

                jsonEventObject.put("eid", getNullOrValue(eid));
                jsonEventObject.put("eventHostUid", getNullOrValue(eventHostUid));
                jsonEventObject.put("eventName", getNullOrValue(eventName));
                jsonEventObject.put("eventPrivacyLabel", getNullOrValue(eventPrivacyLabel));
                jsonEventObject.put("eventInviteTypeLabel", getNullOrValue(eventInviteTypeLabel));
                jsonEventObject.put("eventImageUploadAllowedIndicator",
                    getNullOrValue(eventImageUploadAllowedIndicator));
                /** THE ABOVE RETURNS AN ERROR IF NULL, FIX THIS! **/
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



    private class IncrementEventViewCount extends AsyncTask<Void, Void, String> {

        Integer eid;
        StringCallback stringCallback;

        private IncrementEventViewCount(Integer eid, StringCallback stringCallback)
        {
            this.eid = eid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=incrementEventViewCount";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eid", getNullOrValue(eid));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

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
