package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;


public class EventRequest2 {

    private HTTPConnection httpConnection = null;

    public EventRequest2()
    {
        httpConnection = new HTTPConnection();
    }



    public String updateEventUnparsedAddress(Integer eid, String eventAdressName,
       String eventUnparsedAddress/*,
       StringCallback stringCallback*/)
        throws InterruptedException, ExecutionException, TimeoutException
    {
        //pd.show();
        String result = new UpdateEventUnparsedAddress(eid, eventAdressName, eventUnparsedAddress/*,
            stringCallback*/).execute().get();
        return result;
    }



    private class UpdateEventUnparsedAddress extends AsyncTask<Void, Void, String> {

        Integer eid;
        String  eventAddressName;
        String  eventUnparsedAddress;
        //StringCallback stringCallback;

        private UpdateEventUnparsedAddress(Integer eid, String eventAddressName,
                                           String eventUnparsedAddress/*,
                                           StringCallback stringCallback*/)
        {
            this.eid = eid;
            this.eventAddressName = eventAddressName;
            this.eventUnparsedAddress = eventUnparsedAddress;
            //this.stringCallback = stringCallback;
        }

        @Override
        protected String doInBackground(Void... params) {

            try
            {
                String auaResponseString =
                    new AddressRequest().addUnparsedAddressSync(eventAddressName, eventUnparsedAddress/*,
                        new StringCallback() {
                            @Override
                            public void done(String string) {}
                        }*/);
                System.out.println("auaResponseString: " + auaResponseString);

                return auaResponseString;
                /*
                JSONObject auaResponse = new JSONObject(auaResponseString);
                String auaResponseType = auaResponse.getString("responseType");

                if (auaResponseType.toLowerCase().equals("success"))
                {
                    JSONObject responseData = auaResponse.getJSONObject("response");
                    Long aid = responseData.getLong("aid");
                    String url = httpConnection.getWebServerString() +
                        "AndroidIO/EventRequest.php?function=updateEventUnparsedAddress";

                    Post request = new Post();

                    JSONObject jObject = new JSONObject();
                    jObject.put("eid", getNullOrValue(eid));
                    jObject.put("eventAid", getNullOrValue(aid));

                    String jString = jObject.toString();

                    return jString;
                }
                else
                {
                    return auaResponseString;
                }
                */
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            catch (ExecutionException ee)
            {
                ee.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            catch (TimeoutException te)
            {
                te.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            /*
            catch (JSONException jsone)
            {
                jsone.printStackTrace();
                return "ERROR ENCOUNTERED. SEE ANDROID LOG.";
            }
            */
        }

        @Override
        protected void onPostExecute(String string) {
            //pd.dismiss();
            //stringCallback.done(string);

            super.onPostExecute(string);
        }

    }

}
