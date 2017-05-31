package brymian.bubbles.bryant.logIn;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserRequest;

public class LoginSignUpPhoneNumber extends Fragment {
    Toolbar toolbar;
    TextInputLayout tilPhoneNumber;
    EditText etPhoneNumber;
    Button bVerify;
    ProgressBar pbVerify;
    FloatingActionButton fabDone;
    private boolean delivered;
    private static final String SMS_STRING = "Hello from Bryant.";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_sign_up_phone_number, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.Phone_Number);

        tilPhoneNumber = (TextInputLayout) view.findViewById(R.id.tilPhoneNumber);
        etPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumber);

        bVerify = (Button) view.findViewById(R.id.bVerify);
        bVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });

        pbVerify = (ProgressBar) view.findViewById(R.id.pbVerify);

        fabDone = (FloatingActionButton) view.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSignUpProfilePicture loginSignUpProfilePicture = new LoginSignUpProfilePicture();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_activity, loginSignUpProfilePicture);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        setReceiver();
        return view;
    }

    private void sendSMS(){
        int size = etPhoneNumber.getText().length();
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
                            pbVerify.setVisibility(View.GONE);
                            bVerify.setText("Verify");
                            Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            pbVerify.setVisibility(View.GONE);
                            bVerify.setText("Verify");
                            Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            pbVerify.setVisibility(View.GONE);
                            bVerify.setText("Verify");
                            Toast.makeText(context, "No PDU provided", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            pbVerify.setVisibility(View.GONE);
                            bVerify.setText("Verify");
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
                                pbVerify.setVisibility(View.GONE);
                                bVerify.setText("Verify");
                            }
                            else if (getDelivered()){
                                tilPhoneNumber.setErrorEnabled(false);
                            }
                           break;
                        case Activity.RESULT_CANCELED:
                            pbVerify.setVisibility(View.GONE);
                            bVerify.setText("Verify");
                            Toast.makeText(getActivity(), "Verification failed", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SMS_DELIVERED));

            tilPhoneNumber.setErrorEnabled(false);
            SmsManager sms = SmsManager.getDefault();
            String phoneNum = etPhoneNumber.getText().toString();
            byte[] smsBody = SMS_STRING.getBytes();
            short port = 1112;
            sms.sendDataMessage(phoneNum, null, port, smsBody, sentPendingIntent, deliveredPendingIntent);
            bVerify.setText("");
            pbVerify.setVisibility(View.VISIBLE);
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
                        pbVerify.setVisibility(View.GONE);
                        bVerify.setText("Verified");
                        bVerify.setClickable(false);
                        etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_phone_grey600_24dp, 0, R.mipmap.ic_done_black_24dp, 0);
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
        new UserRequest(getActivity()).setUser(SaveSharedPreference.getUserUID(getActivity()), null, null, null, etPhoneNumber.getText().toString(), null,
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
