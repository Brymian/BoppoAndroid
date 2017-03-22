package brymian.bubbles.bryant.sendTo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.main.MainActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;

public class SendTo extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Toolbar mToolbar;
    CheckBox cbMap;
    FloatingActionButton fabDone;
    boolean isMap = false;
    String privacy = "Public", encodedImage;
    List<Integer> uiid = new ArrayList<>();

    int year, month, day, hour, minute, second;

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

        cbMap = (CheckBox) findViewById(R.id.cbMap);
        cbMap.setOnCheckedChangeListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.rvMyEpisodes);
        setDateTime();
        setLiveParticipatedEpisodes();
        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.cbMap:
                if (isChecked){
                    isMap = true;
                }
                else if (!isChecked){
                    isMap = false;
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadImage(){
        new UserImageRequest(SendTo.this).uploadImage(
                SaveSharedPreference.getUserUID(SendTo.this),       /* uid */
                null,                                               /* image profile sequence */
                imageName(),                                        /* image name */
                "Regular",                                          /* image purpose label */
                privacy,                                            /* image privacy label */
                SaveSharedPreference.getLatitude(SendTo.this),      /* image latitude */
                SaveSharedPreference.getLongitude(SendTo.this),     /* image longitude */
                encodedImage,                                       /* image in String */
                new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("uploadImage", string);
                        setUiid(Integer.parseInt(string));
                        uploadImageToEpisode();
                    }
                });
    }

    private void uploadImageToEpisode(){
        List<Episode> singleEpisodeList = ((SendToEpisodesRecyclerAdapter) adapter).getEpisodeList();
        if (singleEpisodeList.size() >= 1){
            for(int i = 0; i < singleEpisodeList.size(); i++){
                Episode singleEpisode = singleEpisodeList.get(i);
                if(singleEpisode.getIsSelected()){
                    new UserImageRequest(this).addImagesToEvent(singleEpisode.getEpisodeEid(), getUiid(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            Log.e("UITE", string);
                            try{
                                JSONArray jArray = new JSONArray(string);
                                for(int i =0; i < jArray.length(); i++){
                                    Log.e("JSON String","Status of Adding Image #" + i + " to UIID: " + jArray.get(i));
                                    if(i == jArray.length() - 1){
                                        startActivity(new Intent(SendTo.this, MainActivity.class));
                                    }
                                }
                            }catch (JSONException jsone){
                                jsone.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
        else {
            startActivity(new Intent(SendTo.this, MainActivity.class));
        }
    }


    private void setLiveParticipatedEpisodes(){
        new EventRequest(this).getEventDataByMember(SaveSharedPreference.getUserUID(this), new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("sendTo", string);
                try{
                    JSONObject jsonObject = new JSONObject(string);
                    String eventsString = jsonObject.getString("events");
                    JSONArray eventsArray = new JSONArray(eventsString);
                    ArrayList<Episode> episodeList = new ArrayList<>();
                    String[] dateTime, dateArray, timeArray;
                    for (int i = 0; i < eventsArray.length(); i++){
                        JSONObject eventsObj = eventsArray.getJSONObject(i);
                        String eventHostString = eventsObj.getString("eventHost");
                        JSONObject eventHostObj = new JSONObject(eventHostString);
                        if (eventsObj.getString("eventEndDatetime").equals("null")){
                            String startTime = eventsObj.getString("eventStartDatetime");
                            dateTime = startTime.split("\\s");
                            dateArray = dateTime[0].split("-");
                            timeArray = dateTime[1].split(":");

                            if (year > Integer.valueOf(dateArray[0])){ //if current year is greater than eventStartDate year, automatically add into List
                                Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), false);
                                episodeList.add(episode);
                            }
                            else if (year == Integer.valueOf(dateArray[0])){ //if current year is equal to eventStartDate year
                                if (month > Integer.valueOf(dateArray[1])){
                                    Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), false);
                                    episodeList.add(episode);
                                }
                                else if (month == Integer.valueOf(dateArray[1])){
                                    if (day > Integer.valueOf(dateArray[2])){
                                        Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), false);
                                        episodeList.add(episode);
                                    }
                                    else if (day == Integer.valueOf(dateArray[2])){
                                        if (hour > Integer.valueOf(timeArray[0])){
                                            Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), false);
                                            episodeList.add(episode);
                                        }
                                        else if (hour == Integer.valueOf(timeArray[0])){
                                            if (minute > Integer.valueOf(timeArray[1])){
                                                Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), false);
                                                episodeList.add(episode);
                                            }
                                            else if (minute == Integer.valueOf(timeArray[1])){
                                                if (second > Integer.valueOf(timeArray[2])){
                                                    Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), false);
                                                    episodeList.add(episode);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    adapter = new SendToEpisodesRecyclerAdapter(episodeList);
                    layoutManager = new LinearLayoutManager(SendTo.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(adapter);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String[] dateTime = dateFormat.format(date).split("\\s");
        String[] dateArray = dateTime[0].split("-");
        String[] timeArray = dateTime[1].split(":");
        this.year = Integer.valueOf(dateArray[0]);
        this.month = Integer.valueOf(dateArray[1]);
        this.day = Integer.valueOf(dateArray[2]);
        this.hour = Integer.valueOf(timeArray[0]);
        this.minute = Integer.valueOf(timeArray[1]);
        this.second = Integer.valueOf(timeArray[2]);
    }

    private void setUiid(int uiid){
        this.uiid.add(uiid);
    }

    private List<Integer> getUiid(){
        return uiid;
    }

    private String imageName(){
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", new java.util.Date());
        return SaveSharedPreference.getUserUID(this) + "_" + charSequenceName;
    }
}
