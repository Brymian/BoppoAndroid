package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.CustomException.SetOrNotException;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.objects.Event;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.IntegerCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.convertPathsToFull;
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
        Integer eventHostUid, String eventName, String eventCategoryLabel, String eventTypeLabel,
        String eventPrivacyLabel, String eventInviteTypeLabel,
        Boolean eventImageUploadAllowedIndicator, String eventDescriptionText,
        String eventStartDatetime, String eventEndDatetime,
        Double eventGpsLatitude, Double eventGpsLongitude, StringCallback objectCallback)
    {
    pd.show();
    new CreateEvent(eventHostUid, eventName, eventCategoryLabel, eventTypeLabel,
        eventPrivacyLabel, eventInviteTypeLabel, eventImageUploadAllowedIndicator,
        eventDescriptionText, eventStartDatetime, eventEndDatetime,
        eventGpsLatitude, eventGpsLongitude, objectCallback)
        .execute();
    }

    public void getEid(Integer eventHostUid, String eventName, IntegerCallback integerCallback)
    {
        pd.show();
        new GetEid(eventHostUid, eventName, integerCallback).execute();
    }

    public void getEventData(int eid, StringCallback stringCallback)
    {
        pd.show();
        new GetEventData(eid, stringCallback).execute();
    }

    public void getEventDataByRadius(Double longitude, Double latitude, Double radius, StringCallback stringCallback)
    {
        pd.show();
        new GetEventDataByRadius(longitude, latitude, radius, stringCallback).execute();
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

    public void getEventDataByTopNLikes(Integer topN, StringCallback stringCallback)
    {
        pd.show();
        new GetEventDataByTopNLikes(topN, stringCallback).execute();
    }

    public void getLiveEventDataByTopNLikes(Integer topN, StringCallback stringCallback)
    {
        pd.show();
        new GetLiveEventDataByTopNLikes(topN, stringCallback).execute();
    }

    public void getEventDataByTopNDislikes(Integer topN, StringCallback stringCallback)
    {
        pd.show();
        new GetEventDataByTopNDislikes(topN, stringCallback).execute();
    }

    public void getLiveEventDataByTopNDislikes(Integer topN, StringCallback stringCallback)
    {
        pd.show();
        new GetLiveEventDataByTopNDislikes(topN, stringCallback).execute();
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
        String eventInviteTypeLabel, String eventCategoryLabel, String eventTypeLabel,
        Boolean eventImageUploadAllowedIndicator, String eventDescriptionText,
        String eventStartDatetime, String eventEndDatetime,
        Double eventGpsLatitude, Double eventGpsLongitude,
        Boolean[] setOrNot, StringCallback objectCallback) throws SetOrNotException
    {
        pd.show();
        new UpdateEvent(eid, eventHostUid, eventName, eventPrivacyLabel, eventInviteTypeLabel,
            eventImageUploadAllowedIndicator, eventCategoryLabel, eventTypeLabel,
            eventDescriptionText, eventStartDatetime, eventEndDatetime,
            eventGpsLatitude, eventGpsLongitude,
            setOrNot, objectCallback).execute();
    }

    public void deleteEvent(int eid, StringCallback stringCallback)
    {
        pd.show();
        new DeleteEvent(eid, stringCallback).execute();
    }

    private class CreateEvent extends AsyncTask<Void, Void, String> {

        Integer eventHostUid;
        String  eventName;
        String  eventCategoryLabel;
        String  eventTypeLabel;
        String  eventPrivacyLabel;
        String  eventInviteTypeLabel;
        Boolean eventImageUploadAllowedIndicator;
        String  eventDescriptionText;
        String  eventStartDatetime;
        String  eventEndDatetime;
        Double  eventGpsLatitude;
        Double  eventGpsLongitude;
        StringCallback stringCallback;

        private CreateEvent(Integer eventHostUid, String eventName,
            String eventCategoryLabel, String eventTypeLabel,
            String eventPrivacyLabel, String eventInviteTypeLabel,
            Boolean eventImageUploadAllowedIndicator, String eventDescriptionText,
            String eventStartDatetime, String eventEndDatetime,
            Double eventGpsLatitude, Double eventGpsLongitude,
            StringCallback stringCallback)
        {

            this.eventHostUid = eventHostUid;
            this.eventName = eventName;
            this.eventCategoryLabel = eventCategoryLabel;
            this.eventTypeLabel = eventTypeLabel;
            this.eventPrivacyLabel = eventPrivacyLabel;
            this.eventInviteTypeLabel = eventInviteTypeLabel;
            this.eventImageUploadAllowedIndicator = eventImageUploadAllowedIndicator;
            this.eventDescriptionText = eventDescriptionText;
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
                jsonEventObject.put("eventCategoryLabel", getNullOrValue(eventCategoryLabel));
                jsonEventObject.put("eventTypeLabel", getNullOrValue(eventTypeLabel));
                jsonEventObject.put("eventPrivacyLabel", getNullOrValue(eventPrivacyLabel));
                jsonEventObject.put("eventInviteTypeLabel", getNullOrValue(eventInviteTypeLabel));
                jsonEventObject.put("eventImageUploadAllowedIndicator",
                    getNullOrValue(eventImageUploadAllowedIndicator));
                jsonEventObject.put("eventDescriptionText", getNullOrValue(eventDescriptionText));
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



    private class GetEventData extends AsyncTask<Void, Void, String> {

        int eid;
        StringCallback stringCallback;

        private GetEventData(int eid, StringCallback stringCallback)
        {
            this.eid = eid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventData";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eid", getNullOrValue(eid));

                String jsonEventString = jsonEventObject.toString();
                String response = request.post(url, jsonEventString);

                return convertPathsToFull(response);
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



    private class GetEventDataByRadius extends AsyncTask<Void, Void, String> {

        Double longitude;
        Double latitude;
        Double radius;
        StringCallback stringCallback;

        private GetEventDataByRadius(Double longitude, Double latitude, Double radius, StringCallback stringCallback)
        {
            this.longitude = longitude;
            this.latitude = latitude;
            this.radius = radius;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByRadius";

            Post request = new Post();

            try
            {
                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("longitude", getNullOrValue(longitude));
                jsonEventObject.put("latitude", getNullOrValue(latitude));
                jsonEventObject.put("radius", getNullOrValue(radius));

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



    private class GetEventDataByTopNLikes extends AsyncTask<Void, Void, String> {

        Integer topN;
        StringCallback stringCallback;

        private GetEventDataByTopNLikes(Integer topN, StringCallback stringCallback)
        {
            this.topN = topN;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByTopNLikes";

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



    private class GetLiveEventDataByTopNLikes extends AsyncTask<Void, Void, String> {

        Integer topN;
        StringCallback stringCallback;

        private GetLiveEventDataByTopNLikes(Integer topN, StringCallback stringCallback)
        {
            this.topN = topN;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getLiveEventDataByTopNLikes";

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



    private class GetEventDataByTopNDislikes extends AsyncTask<Void, Void, String> {

        Integer topN;
        StringCallback stringCallback;

        private GetEventDataByTopNDislikes(Integer topN, StringCallback stringCallback)
        {
            this.topN = topN;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getEventDataByTopNDislikes";

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



    private class GetLiveEventDataByTopNDislikes extends AsyncTask<Void, Void, String> {

        Integer topN;
        StringCallback stringCallback;

        private GetLiveEventDataByTopNDislikes(Integer topN, StringCallback stringCallback)
        {
            this.topN = topN;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=getLiveEventDataByTopNDislikes";

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
        String  eventCategoryLabel;
        String  eventTypeLabel;
        String  eventPrivacyLabel;
        String  eventInviteTypeLabel;
        Boolean eventImageUploadAllowedIndicator;
        String  eventDescriptionText;
        String  eventStartDatetime;
        String  eventEndDatetime;
        Double  eventGpsLatitude;
        Double  eventGpsLongitude;

        Map<String,Boolean> setOrNot = new HashMap<>();
        StringCallback stringCallback;

        private UpdateEvent(Integer eid, Integer eventHostUid, String eventName, String eventPrivacyLabel,
            String eventInviteTypeLabel, Boolean eventImageUploadAllowedIndicator,
            String eventCategoryLabel, String eventTypeLabel,
            String eventDescriptionText, String eventStartDatetime, String eventEndDatetime,
            Double eventGpsLatitude, Double eventGpsLongitude,
            Boolean[] setOrNot, StringCallback stringCallback) throws SetOrNotException
        {
            if (setOrNot.length != 13)
                throw new SetOrNotException("Incorrect quantity of booleans set in the SetOrNot array.");

            this.eid = eid;
            this.eventHostUid = eventHostUid;
            this.eventName = eventName;
            this.eventCategoryLabel = eventCategoryLabel;
            this.eventTypeLabel = eventTypeLabel;
            this.eventPrivacyLabel = eventPrivacyLabel;
            this.eventInviteTypeLabel = eventInviteTypeLabel;
            this.eventImageUploadAllowedIndicator = eventImageUploadAllowedIndicator;
            this.eventDescriptionText = eventDescriptionText;
            this.eventStartDatetime = eventStartDatetime;
            this.eventEndDatetime = eventEndDatetime;
            this.eventGpsLatitude = eventGpsLatitude;
            this.eventGpsLongitude = eventGpsLongitude;

            this.setOrNot.put("eid", null);
            this.setOrNot.put("eventHostUid", setOrNot[1]);
            this.setOrNot.put("eventName", setOrNot[2]);
            this.setOrNot.put("eventCategoryLabel", setOrNot[3]);
            this.setOrNot.put("eventTypeLabel", setOrNot[4]);
            this.setOrNot.put("eventPrivacyLabel", setOrNot[5]);
            this.setOrNot.put("eventInviteTypeLabel", setOrNot[6]);
            this.setOrNot.put("eventImageUploadAllowedIndicator", setOrNot[7]);
            this.setOrNot.put("eventDescriptionText", setOrNot[8]);
            this.setOrNot.put("eventStartDatetime", setOrNot[9]);
            this.setOrNot.put("eventEndDatetime", setOrNot[10]);
            this.setOrNot.put("eventGpsLatitude", setOrNot[11]);
            this.setOrNot.put("eventGpsLongitude", setOrNot[12]);

            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/EventRequest.php?function=updateEvent";

            Post request = new Post();
            try
            {
                JSONObject jsonSetOrNotObject = new JSONObject(setOrNot);

                JSONObject jsonEventObject = new JSONObject();
                jsonEventObject.put("eid", getNullOrValue(eid));
                jsonEventObject.put("eventHostUid", getNullOrValue(eventHostUid));
                jsonEventObject.put("eventName", getNullOrValue(eventName));
                jsonEventObject.put("eventCategoryLabel", getNullOrValue(eventCategoryLabel));
                jsonEventObject.put("eventTypeLabel", getNullOrValue(eventTypeLabel));
                jsonEventObject.put("eventPrivacyLabel", getNullOrValue(eventPrivacyLabel));
                jsonEventObject.put("eventInviteTypeLabel", getNullOrValue(eventInviteTypeLabel));
                jsonEventObject.put("eventImageUploadAllowedIndicator",
                    getNullOrValue(eventImageUploadAllowedIndicator));
                /** THE ABOVE RETURNS AN ERROR IF NULL, FIX THIS! **/
                jsonEventObject.put("eventDescriptionText", getNullOrValue(eventDescriptionText));
                jsonEventObject.put("eventStartDatetime", getNullOrValue(eventStartDatetime));
                jsonEventObject.put("eventEndDatetime", getNullOrValue(eventEndDatetime));
                jsonEventObject.put("eventGpsLatitude", getNullOrValue(eventGpsLatitude));
                jsonEventObject.put("eventGpsLongitude", getNullOrValue(eventGpsLongitude));
                jsonEventObject.put("setOrNot", jsonSetOrNotObject);

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
