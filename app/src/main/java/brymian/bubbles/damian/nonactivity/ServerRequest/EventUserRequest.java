package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventUserCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.objects.EventUser;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.getBooleanObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getDoubleObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getIntegerObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getLongObjectFromObject;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;

public class EventUserRequest {

    private HTTPConnection httpConnection = null;
    private ProgressDialog pd = null;

    public EventUserRequest(Activity activity) {
        pd = new ProgressDialog(activity);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
        httpConnection = new HTTPConnection();
    }

    public void addUserToEvent(Integer eid, Integer inviterUid, Integer inviteeUid, StringCallback stringCallback)
    {
        pd.show();
        new AddUserToEvent(eid, inviterUid, inviteeUid, stringCallback).execute();
    }

    public void getEventUserData(Integer eid, Integer uid, EventUserCallback eventUserCallback)
    {
        pd.show();
        new GetEventUserData(eid, uid, eventUserCallback).execute();
    }



    private class AddUserToEvent extends AsyncTask<Void, Void, String> {

        Integer eid;
        Integer inviterUid;
        Integer inviteeUid;
        StringCallback stringCallback;

        private AddUserToEvent(Integer eid, Integer inviterUid, Integer inviteeUid, StringCallback stringCallback)
        {
            this.eid = eid;
            this.inviterUid = inviterUid;
            this.inviteeUid = inviteeUid;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/EventUserRequest.php?function=addUserToEvent";

            Post request = new Post();

            try
            {
                JSONObject jsonEventUserObject = new JSONObject();
                jsonEventUserObject.put("eid", getNullOrValue(eid));
                jsonEventUserObject.put("inviterUid", getNullOrValue(inviterUid));
                jsonEventUserObject.put("inviteeUid", getNullOrValue(inviteeUid));

                String jsonEventUserString = jsonEventUserObject.toString();
                String response = request.post(url, jsonEventUserString);

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

    private class GetEventUserData extends AsyncTask<Void, Void, EventUser> {

        Integer eid;
        Integer uid;
        EventUserCallback eventUserCallback;

        private GetEventUserData(Integer eid, Integer uid, EventUserCallback eventUserCallback)
        {
            this.eid = eid;
            this.uid = uid;
            this.eventUserCallback = eventUserCallback;
        }

        @Override
        protected EventUser doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                    "AndroidIO/EventUserRequest.php?function=getEventUserData";

            Post request = new Post();
            JSONObject jsonEventUserObject;

            try
            {
                jsonEventUserObject = new JSONObject();
                jsonEventUserObject.put("eid", getNullOrValue(eid));
                jsonEventUserObject.put("uid", getNullOrValue(uid));

                String jsonEventUserString = jsonEventUserObject.toString();
                String response = request.post(url, jsonEventUserString);

                if (response.equals("This user is not a member of this event."))
                    return  null;
                else
                {
                    jsonEventUserObject = new JSONObject(response);
                    EventUser jsonEventUser = new EventUser(
                        getIntegerObjectFromObject(jsonEventUserObject.get("eid")),
                        getIntegerObjectFromObject(jsonEventUserObject.get("uid")),
                        jsonEventUserObject.getString("eventUserTypeLabel"),
                        jsonEventUserObject.getString("eventUserInviteStatusLabel"),
                        jsonEventUserObject.getString("eventUserInviteStatusActionTimestamp")
                    );
                    return jsonEventUser;
                }
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
        protected void onPostExecute(EventUser eventUser) {
            pd.dismiss();
            eventUserCallback.done(eventUser);

            super.onPostExecute(eventUser);
        }

    }
}
