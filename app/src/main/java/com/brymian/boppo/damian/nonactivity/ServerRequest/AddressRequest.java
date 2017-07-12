package com.brymian.boppo.damian.nonactivity.ServerRequest;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.brymian.boppo.damian.nonactivity.Connection.HTTPConnection;
import com.brymian.boppo.damian.nonactivity.Post;

import static com.brymian.boppo.damian.nonactivity.Miscellaneous.convertPathsToFull;
import static com.brymian.boppo.damian.nonactivity.Miscellaneous.getNullOrValue;

/**
 * Created by Ziomster on 5/16/2017.
 */

public class AddressRequest
{
    private HTTPConnection httpConnection = null;

    public AddressRequest() {
        httpConnection = new HTTPConnection();
    }

    /** This method adds the specified unparsed address to the specified event. **/
    String addUnparsedAddressSync(String addressName, String addressUnparsedText/*,
        StringCallback stringCallback*/)
        throws InterruptedException, ExecutionException, TimeoutException
    {
        String result = new AddUnparsedAddressAsync(
            addressName, addressUnparsedText/*, stringCallback*/).execute().get();
        setParsedAddressAsync();
        return result;
    }

    /** TO-DO
     *  This method will add the specified parsed address to the specified event.
     */
    String addParsedAddressSync()
    {
        setUnparsedAddressAsync();
        return "";
    }

    private void setParsedAddressAsync()
    {
        /** TO-DO
         *  This method will attempt to populate the parsed address fields for the specified
         *  Address ID (aid) using the unparsed address field.
         */
    }

    private void setUnparsedAddressAsync()
    {
        /** TO-DO
         *  This method will attempt to populate the unparsed address field for the specified
         *  Address ID (aid) using the parsed address fields.
         */
    }





    private class AddUnparsedAddressAsync extends AsyncTask<Void, Void, String>
    {
        String addressName;
        String addressUnparsedText;

        private AddUnparsedAddressAsync(String addressName, String addressUnparsedText)
        {
            this.addressName = addressName;
            this.addressUnparsedText = addressUnparsedText;
        }

        @Override
        protected String doInBackground(Void... params)
        {
            String url = httpConnection.getWebServerString() +
                "AndroidIO/AddressRequest.php?function=addUnparsedAddress";

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
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
        }

        @Override
        protected void onPostExecute(String string)
        {
            super.onPostExecute(string);
        }

    }



    private class AddParsedAddressAsync extends AsyncTask<Void, Void, String> {

        private AddParsedAddressAsync()
        {
            // PLACEHOLDER
        }

        @Override
        protected String doInBackground(Void... params)
        {
            // PLACEHOLDER

            return null;
        }

        @Override
        protected void onPostExecute(String string)
        {
            super.onPostExecute(string);
        }

    }
}
