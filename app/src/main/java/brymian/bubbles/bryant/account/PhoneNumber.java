package brymian.bubbles.bryant.account;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.RegularExpressions;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserRequest;

public class PhoneNumber extends Fragment {
    private Pattern pattern = Pattern.compile(RegularExpressions.PHONE_NUMBER_PATTERN);

    TextInputLayout tilPhoneNumber;
    TextInputEditText tietPhoneNumber;
    TextView tvSuccess;
    FloatingActionButton fabDone;

    private boolean delivered;
    private static final String SMS_STRING = "Hello from Bryant.";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_phone_number, container, false);

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Phone_Number);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tilPhoneNumber = (TextInputLayout) view.findViewById(R.id.tilPhoneNumber);
        tietPhoneNumber = (TextInputEditText) view.findViewById(R.id.tietPhoneNumber);
        tietPhoneNumber.setText(SaveSharedPreference.getUserPhoneNumber(getActivity()));

        tvSuccess = (TextView) view.findViewById(R.id.tvSuccess);

        fabDone = (FloatingActionButton) view.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePhoneNumber()){
                    sendSMS();
                }
            }
        });
        setReceiver();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.search);
        item.setVisible(false);
    }

    private boolean validatePhoneNumber() {
        boolean sitch;
        Matcher matcher = pattern.matcher(tietPhoneNumber.getText().toString());
        if (matcher.matches()){
            tilPhoneNumber.setErrorEnabled(false);
            sitch = true;
        }
        else{
            tilPhoneNumber.setError("Invalid phone number");
            sitch = false;
        }
        return sitch;
    }

    private void sendSMS(){
        int size = tietPhoneNumber.getText().length();
        if (size > 0){

            String SMS_SENT = "SMS_SENT";
            String SMS_DELIVERED = "SMS_DELIVERED";

            PendingIntent sentPendingIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(SMS_SENT), 0);
            PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(SMS_DELIVERED), 0);

            // For when the SMS has been sent
            getActivity().registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(context, "No PDU provided", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SMS_SENT));

            // For when the SMS has been delivered
            getActivity().registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            if (!getDelivered()){
                                tilPhoneNumber.setError("Incorrect number");
                            }
                            else if (getDelivered()){
                                tilPhoneNumber.setErrorEnabled(false);
                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(getActivity(), "Verification failed", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SMS_DELIVERED));

            tilPhoneNumber.setErrorEnabled(false);
            SmsManager sms = SmsManager.getDefault();
            String phoneNum = tietPhoneNumber.getText().toString();
            byte[] smsBody = SMS_STRING.getBytes();
            short port = 1112;
            sms.sendDataMessage(phoneNum, null, port, smsBody, sentPendingIntent, deliveredPendingIntent);
        }
        else if (size == 0){
            tilPhoneNumber.setError("Empty");
        }
    }

    private void setReceiver(){
        BroadcastReceiver smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // code to read the incoming SMS
                Bundle bundle = intent.getExtras();

                SmsMessage[] msgs;

                String str = "";

                if (bundle != null){
                    // Retrieve the Binary SMS data
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];

                    // For every SMS message received (although multipart is not supported with binary)
                    for (int i=0; i<msgs.length; i++) {
                        byte[] data;

                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                        //str += "Binary SMS from " + msgs[i].getOriginatingAddress() + " :";

                        //str += "\nBINARY MESSAGE: ";

                        // Return the User Data section minus the
                        // User Data Header (UDH) (if there is any UDH at all)
                        data = msgs[i].getUserData();

                        // Generally you can do away with this for loop
                        // You'll just need the next for loop
                        for (byte aData : data) {
                            str += Character.toString((char) aData);

                        }
                        /*
                        //original
                        for (byte aData1 : data) {
                            str += Byte.toString(aData1);
                        }
                        str += "\nTEXT MESSAGE (FROM BINARY): ";

                        for (byte aData : data) {
                            str += Character.toString((char) aData);

                        }

                        str += "\n";
                        */
                    }
                    if (str.equals(SMS_STRING)){
                        setDelivered(true);
                        fabDone.setClickable(false);
                        tietPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_phone_grey600_24dp, 0, R.mipmap.ic_done_black_24dp, 0);
                        addPhoneNumberToDB();
                    }
                }
            }
        };
        // Register a broadcast receiver
        IntentFilter intentFilter = new IntentFilter("android.intent.action.DATA_SMS_RECEIVED");
        intentFilter.setPriority(10);
        intentFilter.addDataScheme("sms");
        intentFilter.addDataAuthority("*", "1112");
        getActivity().registerReceiver(smsReceiver, intentFilter);
    }

    private void addPhoneNumberToDB(){
        /** BRYANT, UPDATE THIS **/
        /*
        new UserRequest(getActivity()).setUser(SaveSharedPreference.getUserUID(getActivity()), null, null, null, tietPhoneNumber.getText().toString(), null,
                new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("sfas", string);
                    }
                });
        */
    }

    private void setDelivered(boolean delivered){
        this.delivered = delivered;
    }

    private boolean getDelivered(){
        return this.delivered;
    }
}
