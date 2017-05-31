package brymian.bubbles.damian.nonactivity.ServerRequest;

import android.app.Activity;
import android.app.ProgressDialog;

import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;

/**
 * Created by Ziomster on 5/16/2017.
 */

public class EventAddressRequest
{
    private HTTPConnection httpConnection = null;

    public EventAddressRequest() {
        httpConnection = new HTTPConnection();
    }

    public void addUnparsedAddressToEvent()
    {
        /** TO-DO
         *  This method will add the specified unparsed address to the specified event.
         */
        setParsedAddressFromUnparsed();
    }

    public void addParsedAddressToEvent()
    {
        /** TO-DO
         *  This method will add the specified parsed address to the specified event.
         */
        setUnparsedAddressFromParsed();
    }

    private void setParsedAddressFromUnparsed()
    {
        /** TO-DO
         *  This method will attempt to populate the parsed address fields for the specified
         *  Address ID (aid) using the unparsed address field.
         */
    }

    private void setUnparsedAddressFromParsed()
    {
        /** TO-DO
         *  This method will attempt to populate the unparsed address field for the specified
         *  Address ID (aid) using the parsed address fields.
         */
    }
}
