package brymian.bubbles.bryant.episodes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.addfriends.EpisodeAddFriends;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.activity.AuthenticateActivity;
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
    String episodeTitle ,privacy;
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
        ibAddFriends = (ImageButton) findViewById(R.id.ibCreateEpisode);
        llAddFriends = (LinearLayout) findViewById(R.id.llCreateEpisode);

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

        if(v.getId() == R.id.ibCreateEpisode) {
            new EventRequest(this).createEvent(
                    SaveSharedPreference.getUserUID(this),      /* uid */
                    getEpisodeTitle(),                          /* Episode Title */
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
                            for(String something: string.split(" ")){
                                if(something.equals("Success.")){
                                    addFriends();
                                }
                            }
                        }
                    });

            /* code below is if we decide to create the episode AND add the friends at the same time */
            /**
            startActivity(new Intent(this, EpisodeAddFriends.class)
                .putExtra("title", etEpisodeTitle.getText().toString())
                .putExtra("privacy", getPrivacy())
                .putExtra("inviteType", "Host")
                .putExtra("imageAllowed", true)
                .putExtra("latitude", getLatitude())
                .putExtra("longitude", getLongitude()));

             **/
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


    private void addFriends() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_alertdialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton(R.string.Later, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(EpisodeCreate.this, EpisodeAddFriends.class)
                    .putExtra("episodeTitle", getEpisodeTitle()));
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private String getEpisodeTitle(){
        this.episodeTitle = etEpisodeTitle.getText().toString();
        return episodeTitle;
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