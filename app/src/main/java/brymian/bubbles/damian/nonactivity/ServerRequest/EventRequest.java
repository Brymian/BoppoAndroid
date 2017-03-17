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

    public void getLiveEventDataByRadius(Double longitude, Double latitude, Double radius, StringCallback stringCallback)
    {
        pd.show();
        new GetLiveEventDataByRadius(longitude, latitude, radius, stringCallback).execute();
    }

    public void getEventDataByMember(Integer uid, StringCallback stringCallback)
    {
        //pd.show();
        new GetEventDataByMember(uid, stringCallback).execute();
    }

    public void getLiveEventDataByMember(Integer uid, StringCallback stringCallback)
    {
        //pd.show();
        new GetLiveEventDataByMember(uid, stringCallback).execute();
    }

    public void getEventDataByName(String eventName, StringCallback stringCallback)
    {
        //pd.show();
        new GetEventDataByName(eventName, stringCallback).execute();
    }

    public void getLiveEventDataByName(String eventName, StringCallback stringCallback)
    {
        //pd.show();
        new GetLiveEventDataByName(eventName, stringCallback).execute();
    }

    public void getEventDataByTopNViews(Integer topN, StringCallback stringCallback)
    {
        pd.show();
        new GetEventDataByTopNViews(topN, stringCallback).execute();
    }

    public void getLiveEventDataByTopNViews(Integer topN, StringCallback stringCallback)
    {
        pd.show();
        new GetLiveEventDataByTopNViews(topN, stringCallback).execute();
    }

    public void getEventDataByTopNLikes(Integer topN, EventListCallback eventListCallback)
    {
        pd.show();
        new GetEventDataByTopNLikes(topN, eventListCallback).execute();
    }

    public void getLiveEventDataByTopNLikes(Integer topN, EventListCallback eventListCallback)
    {
        pd.show();
        new GetLiveEventDataByTopNLikes(topN, eventListCallback).execute();
    }

    public void getEventDataByTopNDislikes(Integer topN, EventListCallback eventListCallback)
    {
        pd.show();
        new GetEventDataByTopNDislikes(topN, eventListCallback).execute();
    }

    public void getLiveEventDataByTopNDislikes(Integer topN, EventListCallback eventListCallback)
    {
        pd.show();
        new GetLiveEventDataByTopNDislikes(topN, eventListCallback).execute();
    }

    public void getEventDataByTopNRatings(Integer topN, StringCallback stringCallback)
    {
        pd.show();
        new GetEventDataByTopNRatings(topN, stringCallback).execute();
    }

    public void getLiveEventDataByTopNRatings(Integer topN, StringCallback stringCallback)
    {
        pd.show();
        new GetLiveEventDataByTopNRatings(topN, stringCallback).execute();
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
                    jEvent.getLong("eventViewCount")
                );

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



    private class GetLiveEventDataByRadius extends AsyncTask<Void, Void, String> {

        Double longitude;
        Double latitude;
        Double radius;
        StringCallback stringCallback;

        private GetLiveEventDataByRadius(Double longitude, Double latitude, Double radius, StringCallback stringCallback)
        {
            this.longitude = longitude;
            this.latitude = latitude;
            this.radius = radius;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getLiveEventDataByRadius";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("longitude", getNullOrValue(longitude));
                jsonEventObject.put("latitude", getNullOrValue(latitude));
                jsonEventObject.put("radius", getNullOrValue(radius));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

                System.out.println(jsonEventString);

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



    private class GetEventDataByMember extends AsyncTask<Void, Void, String> {

        Integer uid;
        StringCallback stringCallback;

        private GetEventDataByMember(Integer uid, StringCallback stringCallback)
        {
            this.uid = uid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByMember";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("uid", uid);

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

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
            //-pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }



    private class GetLiveEventDataByMember extends AsyncTask<Void, Void, String> {

        Integer uid;
        StringCallback stringCallback;

        private GetLiveEventDataByMember(Integer uid, StringCallback stringCallback)
        {
            this.uid = uid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getLiveEventDataByMember";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("uid", getNullOrValue(uid));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

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
            //-pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }



    private class GetEventDataByName extends AsyncTask<Void, Void, String> {

        String eventName;
        StringCallback stringCallback;

        private GetEventDataByName(String eventName, StringCallback stringCallback)
        {
            this.eventName = eventName;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByName";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eventName", getNullOrValue(eventName));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

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
            //pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }



    private class GetLiveEventDataByName extends AsyncTask<Void, Void, String> {

        String eventName;
        StringCallback stringCallback;

        private GetLiveEventDataByName(String eventName, StringCallback stringCallback)
        {
            this.eventName = eventName;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getLiveEventDataByName";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eventName", getNullOrValue(eventName));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

                System.out.println("RESPONSE: " + response);

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
            //pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }



    private class GetEventDataByTopNViews extends AsyncTask<Void, Void, String> {

        Integer topNViews;
        StringCallback stringCallback;

        private GetEventDataByTopNViews(Integer topNViews, StringCallback stringCallback)
        {
            this.topNViews = topNViews;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByTopNViews";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("topNViews", getNullOrValue(topNViews));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

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



    private class GetLiveEventDataByTopNViews extends AsyncTask<Void, Void, String> {

        Integer topNViews;
        StringCallback stringCallback;

        private GetLiveEventDataByTopNViews(Integer topNViews, StringCallback stringCallback)
        {
            this.topNViews = topNViews;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getLiveEventDataByTopNViews";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("topNViews", getNullOrValue(topNViews));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

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
                        jEvent.getLong("eventViewCount")
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



    private class GetLiveEventDataByTopNLikes extends AsyncTask<Void, Void, List<Event>> {

        Integer topN;
        EventListCallback eventListCallback;

        private GetLiveEventDataByTopNLikes(Integer topN, EventListCallback eventListCallback)
        {
            this.topN = topN;
            this.eventListCallback = eventListCallback;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getLiveEventDataByTopNLikes";

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
                            jEvent.getLong("eventViewCount")
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
                            jEvent.getLong("eventViewCount")
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



    private class GetLiveEventDataByTopNDislikes extends AsyncTask<Void, Void, List<Event>> {

        Integer topN;
        EventListCallback eventListCallback;

        private GetLiveEventDataByTopNDislikes(Integer topN, EventListCallback eventListCallback)
        {
            this.topN = topN;
            this.eventListCallback = eventListCallback;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getLiveEventDataByTopNDislikes";

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
                        jEvent.getLong("eventViewCount")
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



    private class GetEventDataByTopNRatings extends AsyncTask<Void, Void, String> {

        Integer topN;
        StringCallback stringCallback;

        private GetEventDataByTopNRatings(Integer topN, StringCallback stringCallback)
        {
            this.topN = topN;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByTopNRatings";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("topN", getNullOrValue(topN));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

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



    private class GetLiveEventDataByTopNRatings extends AsyncTask<Void, Void, String> {

        Integer topN;
        StringCallback stringCallback;

        private GetLiveEventDataByTopNRatings(Integer topN, StringCallback stringCallback)
        {
            this.topN = topN;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getLiveEventDataByTopNRatings";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("topN", getNullOrValue(topN));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

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

}
