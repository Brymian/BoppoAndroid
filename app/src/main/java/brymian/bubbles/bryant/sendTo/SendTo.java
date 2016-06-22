package brymian.bubbles.bryant.sendTo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.main.MainActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.objects.Event;

public class SendTo extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Toolbar mToolbar;
    TextView tvPrivate, tvCurrentLocation;
    CheckBox cbMap, cbPrivate, cbCurrentLocation;
    FloatingActionButton fabDone;
    String privacy = "Public", encodedImage;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_to);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.Send_To);

        final String encodedImage;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                encodedImage= null;
            }
            else {
                encodedImage = extras.getString("encodedImage");
            }
        }
        else {
            encodedImage = savedInstanceState.getString("encodedImage");
        }

        this.encodedImage = encodedImage;
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvPrivate = (TextView) findViewById(R.id.tvPrivate);
        tvCurrentLocation = (TextView) findViewById(R.id.tvCurrentLocation);
        cbPrivate = (CheckBox) findViewById(R.id.cbPrivate);
        if(SaveSharedPreference.getUserPicturePrivacy(this).length() != 0){
            cbPrivate.setChecked(true);
            privacy = "Private";
        }
        cbPrivate.setOnCheckedChangeListener(this);
        cbCurrentLocation = (CheckBox) findViewById(R.id.cbCurrentLocation);
        cbCurrentLocation.setOnCheckedChangeListener(this);
        cbMap = (CheckBox) findViewById(R.id.cbMap);
        cbMap.setOnCheckedChangeListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rvMyEpisodes);
        setLiveParticipatedEpisodes();
        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
                uploadImageToEpisode();
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.cbMap:
                if(isChecked){
                    tvPrivate.setTextColor(Color.BLACK);
                    tvCurrentLocation.setTextColor(Color.BLACK);
                    cbPrivate.setClickable(true);
                    cbCurrentLocation.setClickable(true);
                }
                else{
                    tvPrivate.setTextColor(Color.GRAY);
                    tvCurrentLocation.setTextColor(Color.GRAY);
                    cbPrivate.setClickable(false);
                    cbPrivate.setClickable(false);
                }
                break;
            case R.id.cbPrivate:
                if(isChecked){
                    privacy = "Private";
                }
                else{
                    privacy = "Public";
                }

                break;

            case R.id.cbCurrentLocation:


                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void uploadImage(){
        new UserImageRequest(SendTo.this).uploadImage(
                SaveSharedPreference.getUserUID(SendTo.this),       /* uid */
                imageName(),                                        /* image name */
                "Regular",                                          /* image purpose label */
                privacy,                                            /* image privacy label */
                SaveSharedPreference.getLatitude(SendTo.this),      /* image latitude */
                SaveSharedPreference.getLongitude(SendTo.this),     /* image longitude */
                encodedImage,                                       /* image in String */
                new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("String response", string);
                        setUiid(Integer.parseInt(string));
                        //startActivity(new Intent(SendTo.this, MainActivity.class));
                    }
                });
    }

    private void uploadImageToEpisode(){
        List<Episode> singleEpisodeList = ((SendToEpisodesRecyclerAdapter) adapter).getEpisodeList();
        for(int i = 0; i < singleEpisodeList.size(); i++){
            Episode singleEpisode = singleEpisodeList.get(i);
            if(singleEpisode.getIsSelected()){
                uploadImage();
                //new UserImageRequest(this).addImagesToEvent(singleEpisode.getEpisodeEid(), SaveSharedPreference.getUserUID(this), );
            }
        }
    }


    private void setLiveParticipatedEpisodes(){
        new EventRequest(this).getEventDataByMember(SaveSharedPreference.getUserUID(this), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                if(eventList.size() > 0){
                    ArrayList<Episode> episodeList = new ArrayList<>();
                    for (Event event : eventList) {
                        Episode episode = new Episode(event.eventName, event.eventHostFirstName + " " + event.eventHostLastName, event.eventHostUsername, event.eid, false);
                        episodeList.add(episode);
                    }

                    adapter = new SendToEpisodesRecyclerAdapter(SendTo.this, episodeList);
                    layoutManager = new LinearLayoutManager(SendTo.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                }
            }
        });
    }

    int uiid;
    private void setUiid(int uiid){
        this.uiid = uiid;
    }

    private int getUiid(){
        return uiid;
    }

    private String imageName(){
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", new java.util.Date());
        return SaveSharedPreference.getUserUID(this) + "_" + charSequenceName;
    }
}
