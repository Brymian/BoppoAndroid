package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.convertPathsToFull;
import static brymian.bubbles.damian.nonactivity.Miscellaneous.getNullOrValue;

/**
 * Created by Ziomster on 5/16/2017.
 */

public class AddressRequest
{
    private HTTPConnection httpConnection = null;

    public AddressRequest() {
        httpConnection = new HTTPConnection();
    }

    public void addUnparsedAddress(String addressName, String addressUnparsedText, StringCallback stringCallback)
    {
        /** TO-DO
         *  This method will add the specified unparsed address to the specified event.
         */
        new AddressRequest.AddUnparsedAddress(addressName, addressUnparsedText,
            stringCallback).execute();
        setParsedAddress();
    }

    public void addParsedAddress()
    {
        /** TO-DO
         *  This method will add the specified parsed address to the specified event.
         */
        setUnparsedAddress();
    }

    private void setParsedAddress()
    {
        /** TO-DO
         *  This method will attempt to populate the parsed address fields for the specified
         *  Address ID (aid) using the unparsed address field.
         */
    }

    private void setUnparsedAddress()
    {
        /** TO-DO
         *  This method will attempt to populate the unparsed address field for the specified
         *  Address ID (aid) using the parsed address fields.
         */
    }





    private class AddUnparsedAddress extends AsyncTask<Void, Void, String> {

        String addressName;
        String addressUnparsedText;
        StringCallback stringCallback;

        private AddUnparsedAddress(String addressName, String addressUnparsedText,
            StringCallback stringCallback)
        {
            this.addressName = addressName;
            this.addressUnparsedText = addressUnparsedText;
            this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = httpConnection.getWebServerString() + "AndroidIO/AddressRequest.php?function=addUnparsedAddress";

            Post request = new Post();

            try
            {
                JSONObject jObject = new JSONObject();
                jObject.put("addressName", getNullOrValue(addressName));
                jObject.put("addressUnparsedText", getNullOrValue(addressUnparsedText));

                String jsonEventString = jObject.toString();
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
            stringCallback.done(string);

            super.onPostExecute(string);
        }

    }
}
