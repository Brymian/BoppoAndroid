package brymian.bubbles.bryant.episodes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.addfriends.EpisodeAddFriends;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;

/**
 * Created by Almanza on 2/9/2016.
 */
public class EpisodeCreate extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    Toolbar mToolbar;
    EditText etEpisodeTitle;
    CheckBox cbPrivate, cbCurrentLocation;
    ImageButton ibAddFriends;
    LinearLayout llAddFriends;
    String privacy;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.episode_create);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.Create_Episode);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etEpisodeTitle = (EditText) findViewById(R.id.etEpisodeTitle);
        cbPrivate = (CheckBox) findViewById(R.id.cbPrivate);
        cbPrivate.setOnCheckedChangeListener(this);
        cbCurrentLocation = (CheckBox) findViewById(R.id.cbCurrentLocation);
        cbCurrentLocation.setChecked(true);
        cbCurrentLocation.setOnCheckedChangeListener(this);
        ibAddFriends = (ImageButton) findViewById(R.id.ibAddFriends);
        llAddFriends = (LinearLayout) findViewById(R.id.llAddFriends);

        ibAddFriends.setOnClickListener(this);
        llAddFriends.setOnClickListener(this);

        setPrivacy("Public");
        setLatitude(SaveSharedPreference.getLatitude(this));
        setLongitude(SaveSharedPreference.getLongitude(this));


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.cbPrivate){
            if(isChecked){
                privacy = "Private";
                System.out.println("getPrivacy(): " + getPrivacy());
            }
            else{
                privacy = "Public";
                System.out.println("getPrivacy(): " + getPrivacy());
            }
        }

        if(buttonView.getId() == R.id.cbCurrentLocation){
            if (!isChecked){
                latitude = 0;
                longitude = 0;
                System.out.println("getLatitude(): " + getLatitude() + "\t getLongitude(): " + getLongitude());
            }
            else{
                latitude = SaveSharedPreference.getLatitude(this);
                longitude = SaveSharedPreference.getLongitude(this);
                System.out.println("getLatitude(): " + getLatitude() + "\t getLongitude(): " + getLongitude());
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.ibAddFriends) {
            startActivity(new Intent(this, EpisodeAddFriends.class));
        }


        if(v.getId() == R.id.ibDone || v.getId() == R.id.llDone){
            System.out.println("latitude: " + latitude + "\t longitude: " + longitude + "\t privacy: " + privacy);
            new EventRequest(this).createEvent(
                    SaveSharedPreference.getUserUID(this),      /* uid */
                    etEpisodeTitle.getText().toString(),        /* Episode Title */
                    getPrivacy(),                               /* Episode Privacy */
                    "Host",                                     /* Invite Type */
                    true,                                       /* Episode Image Allowed Indicator */
                    null,                                       /* Episode start time */
                    null,                                       /* Episode end time */
                    getLatitude(),                              /* Episode GPS latitude */
                    getLongitude(),                             /* Episode GPS longitude */
                    new StringCallback() {
                @Override
                public void done(String string) {
                    System.out.println("String call back: " + string);
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setPrivacy(String privacy){
        this.privacy = privacy;
    }

    private String getPrivacy(){
        return privacy;
    }

    private void setLatitude(double latitude){
        this.latitude = latitude;
    }

    private double getLatitude(){
        return latitude;
    }

    private void setLongitude(double longitude){
        this.longitude = longitude;
    }

    private double getLongitude(){
        return longitude;
    }

}