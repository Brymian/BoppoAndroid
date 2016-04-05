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
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.IntegerCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.objects.Event;
import brymian.bubbles.objects.Image;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getBooleanObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getDoubleObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getIntegerObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getLongObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.isStringAnInteger;

public class ImageRequest {

    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public ImageRequest(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }

    public void getImagesByPrivacyAndPurpose(String imagePrivacyLabel, String imagePurposeLabel,
        ImageListCallback imageListCallback)
    {
        pd.show();
        new GetImagesByPrivacyAndPurpose(imagePrivacyLabel, imagePurposeLabel, imageListCallback).execute();
    }



    private class GetImagesByPrivacyAndPurpose extends AsyncTask<Void, Void, List<Image>> {

        String imagePrivacyLabel;
        String imagePurposeLabel;
        ImageListCallback imageListCallback;

        private GetImagesByPrivacyAndPurpose(String imagePrivacyLabel, String imagePurposeLabel,
             ImageListCallback imageListCallback)
        {
            this.imagePrivacyLabel = imagePrivacyLabel;
            this.imagePurposeLabel = imagePurposeLabel;

            this.imageListCallback = imageListCallback;
        }

        @Override
        protected List<Image> doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/UserImageRequest.php?function=getImagesByPrivacyAndPurpose";

            Post request = new Post();
            try
            {
                JSONObject jsonImageObject = new JSONObject();

                jsonImageObject.put("imagePrivacyLabel", getNullOrValue("imagePrivacyLabel"));
                jsonImageObject.put("imagePurposeLabel", getNullOrValue("imagePurposeLabel"));

                String jsonImageString = jsonImageObject.toString();

                String response = request.post(url, jsonImageString);
                return null;
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
        protected void onPostExecute(List<Image> imageList) {
            pd.dismiss();
            imageListCallback.done(imageList);

            super.onPostExecute(imageList);
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
