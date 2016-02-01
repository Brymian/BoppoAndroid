package brymian.bubbles.bryant.MenuButtons.SettingsButtons;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;

/**
 * Created by Almanza on 7/6/2015.
 */
public class Notifications extends FragmentActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    Switch sVibration, sLED, sWakeScreen, sSound;
    ImageButton ibMenu;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        sVibration = (Switch) findViewById(R.id.sVibration);
        sLED = (Switch) findViewById(R.id.sLED);
        sWakeScreen = (Switch) findViewById(R.id.sWakeScreen);
        sSound = (Switch) findViewById(R.id.sSound);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);

        ibMenu.setOnClickListener(this);
        sVibration.setOnCheckedChangeListener(this);
        sLED.setOnCheckedChangeListener(this);
        sWakeScreen.setOnCheckedChangeListener(this);
        sSound.setOnCheckedChangeListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibMenu:
                startActivity(new Intent(this, MenuActivity.class));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton cb, boolean b){
        switch (cb.getId()){
            case R.id.sVibration:
                if(b){
                    notificationVibration();
                }
                else{
                    Toast.makeText(Notifications.this, "Vibration is off", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.sLED:
                if(b){
                    notificationLED();
                }
                else{
                    Toast.makeText(Notifications.this, "LED is off", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.sSound:
                if(b){
                    notificationSound();
                }
                else{
                    Toast.makeText(Notifications.this, "Sound is off", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.sWakeScreen:
                if(b){
                }
                else{

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
