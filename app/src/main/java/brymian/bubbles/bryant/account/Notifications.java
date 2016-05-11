package brymian.bubbles.bryant.account;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;


public class Notifications extends Fragment implements CompoundButton.OnCheckedChangeListener{
    Switch sVibration, sLED, sSound;
    Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notifications, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.tool_bar);
        mToolbar.setTitle(R.string.Notifications);
        mToolbar.setTitleTextColor(Color.BLACK);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sVibration = (Switch) rootView.findViewById(R.id.sVibration);
        sLED = (Switch) rootView.findViewById(R.id.sLED);
        sSound = (Switch) rootView.findViewById(R.id.sSound);

        if(SaveSharedPreference.getNotificationVibration(getActivity()).length() != 0){
            sVibration.setChecked(true);
        }
        if(SaveSharedPreference.getNotificationLEDLight(getActivity()).length() != 0){
            sLED.setChecked(true);
        }
        if(SaveSharedPreference.getNotificationSound(getActivity()).length() != 0){
            sSound.setChecked(true);
        }

        sVibration.setOnCheckedChangeListener(this);
        sLED.setOnCheckedChangeListener(this);
        sSound.setOnCheckedChangeListener(this);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //getActivity().getMenuInflater().inflate(R.menu.profile_pictures_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                //finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton cb, boolean b){
        switch (cb.getId()){
            case R.id.sVibration:
                if(b){
                    if(SaveSharedPreference.getNotificationVibration(getActivity()).length() == 0){
                        SaveSharedPreference.setNotificationVibration(getActivity(), "true");
                        notificationVibration();
                    }
                }
                else {
                    if (SaveSharedPreference.getNotificationVibration(getActivity()).length() != 0) {
                        SaveSharedPreference.clearNotificationVibration(getActivity());
                    }
                }
                break;

            case R.id.sLED:
                if(b){
                    if(SaveSharedPreference.getNotificationLEDLight(getActivity()).length() == 0){
                        SaveSharedPreference.setNotificationsLEDLight(getActivity(), "true");
                        notificationLED();
                    }
                }
                else{
                    if(SaveSharedPreference.getNotificationLEDLight(getActivity()).length() != 0){
                        SaveSharedPreference.clearNotificationLEDLight(getActivity());
                    }
                }
                break;

            case R.id.sSound:
                if(b){
                    if(SaveSharedPreference.getNotificationSound(getActivity()).length() == 0) {
                        SaveSharedPreference.setNotificationsSound(getActivity(), "true");
                        notificationSound();
                    }
                }
                else {
                    if(SaveSharedPreference.getNotificationSound(getActivity()).length() != 0){
                        SaveSharedPreference.clearNotificationSound(getActivity());
                    }
                }
                break;
        }
    }

    public void notificationVibration(){
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        long[] pattern = {0, 200, 0};
        Notification builder = new NotificationCompat.Builder(getActivity()).setVibrate(pattern).build();
        notificationManager.notify(0, builder);
    }

    public void notificationLED(){
        getActivity();
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification builder = new NotificationCompat.Builder(getActivity()).setLights(Color.GREEN, 5000, 5000).build();
        notificationManager.notify(0, builder);
    }

    public void notificationSound(){
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification builder = new NotificationCompat.Builder(getActivity()).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).build();
        notificationManager.notify(0, builder);
    }
}
