package com.brymian.boppo.damian.nonactivity.ServerRequest;

import android.os.AsyncTask;

import com.brymian.boppo.damian.nonactivity.Connection.HTTPConnection;
import com.brymian.boppo.damian.nonactivity.Post;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.brymian.boppo.damian.nonactivity.Miscellaneous.getNullOrValue;

/**
 * Created by Ziomster on 8/15/2017.
 */

public class UserRelationshipRequest
{
    private HTTPConnection httpConnection = null;

    public UserRelationshipRequest() {
        httpConnection = new HTTPConnection();
    }





    public void setUserRelationship(Integer uid1, Integer uid2, String userRelationshipAction,
        StringCallback stringCallback)
    {
        // VALID VALUES FOR userRelationshipAction:
        // "Add", "Cancel", "Accept", "Reject", "Unfriend", "Block", "Unblock"
        new UserRelationshipRequest.SetUserRelationship(uid1, uid2, userRelationshipAction,
            stringCallback).execute();
    }





    private class SetUserRelationship extends AsyncTask<Void, Void, String> {

        Integer uid1;
        Integer uid2;
        String userRelationshipAction;
        StringCallback stringCallback;

        private SetUserRelationship(Integer uid1, Integer uid2, String userRelationshipAction,
            StringCallback stringCallback)
        {
            this.uid1 = uid1;
            this.uid2 = uid2;
            this.userRelationshipAction = userRelationshipAction;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/UserRelationshipRequest.php?function=setUserRelationship";

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("uid1", getNullOrValue(uid1));
                jObject.put("uid2", getNullOrValue(uid2));
                jObject.put("userRelationshipAction", getNullOrValue(userRelationshipAction));

                String jString = jObject.toString();
                Post request = new Post();
                String response = request.post(url, jString);

                return response;
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
            //pd.dismiss();
            stringCallback.done(string);

            super.onPostExecute(string);
        }
    }
}
