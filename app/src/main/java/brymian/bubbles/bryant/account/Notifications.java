package brymian.bubbles.bryant.account;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;


public class Notifications extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Switch sVibration, sLED, sSound;
    Toolbar mToolbar;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle(R.string.Notifications);
        mToolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sVibration = (Switch) findViewById(R.id.sVibration);
        sLED = (Switch) findViewById(R.id.sLED);
        sSound = (Switch) findViewById(R.id.sSound);

        if(SaveSharedPreference.getNotificationVibration(this).length() != 0){
            sVibration.setChecked(true);
        }
        if(SaveSharedPreference.getNotificationLEDLight(this).length() != 0){
            sLED.setChecked(true);
        }
        if(SaveSharedPreference.getNotificationSound(this).length() != 0){
            sSound.setChecked(true);
        }

        sVibration.setOnCheckedChangeListener(this);
        sLED.setOnCheckedChangeListener(this);
        sSound.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
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
                    if(SaveSharedPreference.getNotificationVibration(this).length() == 0){
                        SaveSharedPreference.setNotificationVibration(this, "true");
                        notificationVibration();
                    }
                }
                else {
                    if (SaveSharedPreference.getNotificationVibration(this).length() != 0) {
                        SaveSharedPreference.clearNotificationVibration(this);
                    }
                }
                break;

            case R.id.sLED:
                if(b){
                    if(SaveSharedPreference.getNotificationLEDLight(this).length() == 0){
                        SaveSharedPreference.setNotificationsLEDLight(this, "true");
                        notificationLED();
                    }
                }
                else{
                    if(SaveSharedPreference.getNotificationLEDLight(this).length() != 0){
                        SaveSharedPreference.clearNotificationLEDLight(this);
                    }
                }
                break;

            case R.id.sSound:
                if(b){
                    if(SaveSharedPreference.getNotificationSound(this).length() == 0) {
                        SaveSharedPreference.setNotificationsSound(this, "true");
                        notificationSound();
                    }
                }
                else {
                    if(SaveSharedPreference.getNotificationSound(this).length() != 0){
                        SaveSharedPreference.clearNotificationSound(this);
                    }
                }
                break;
        }
    }

    public void notificationVibration(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        long[] pattern = {0, 200, 0};
        Notification builder = new NotificationCompat.Builder(this).setVibrate(pattern).build();
        notificationManager.notify(0, builder);
    }

    public void notificationLED(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification builder = new NotificationCompat.Builder(this).setLights(Color.GREEN, 5000, 5000).build();
        notificationManager.notify(0, builder);
    }

    public void notificationSound(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification builder = new NotificationCompat.Builder(this).setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).build();
        notificationManager.notify(0, builder);
    }
}
