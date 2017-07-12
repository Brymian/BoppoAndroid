package com.brymian.boppo.bryant.sendTo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.nonactivity.SaveSharedPreference;
import com.brymian.boppo.damian.nonactivity.Connection.HTTPConnection;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.EventRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequest.EventUserImageRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequest.UserImageRequest;

public class SendTo extends Fragment implements CompoundButton.OnCheckedChangeListener{
    Toolbar mToolbar;
    CheckBox cbMap;
    FloatingActionButton fabDone;
    boolean isMap = false;
    String privacy = "Public", encodedImage, thumbnailEncodedImage;
    Integer[] uiid = new Integer[1];

    public ProgressDialog pd = null;

    int year, month, day, hour, minute, second;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_to, container, false);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Send_To);
        mToolbar.setPadding(0, getStatusBarHeight(),0, 0);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cbMap = (CheckBox) view.findViewById(R.id.cbMap);
        cbMap.setOnCheckedChangeListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvMyEpisodes);
        setDateTime();
        setLiveParticipatedEpisodes();
        fabDone = (FloatingActionButton) view.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(getActivity());
                pd.setCancelable(false);
                pd.setTitle("Uploading Image...");
                pd.setMessage("...to server");
                pd.show();
                uploadImage();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            encodedImage = bundle.getString("encodedImage", null);
            thumbnailEncodedImage = bundle.getString("thumbnailEncodedImage", null);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.switch_cam);
        item.setVisible(false);
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

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void uploadImage(){
        new UserImageRequest(getActivity()).uploadImage(
                SaveSharedPreference.getUserUID(getActivity()),     // uid
                null,                                               // image profile sequence
                imageName(),                                        // image name
                privacy,                                            // image privacy label
                SaveSharedPreference.getLatitude(getActivity()),    // image latitude
                SaveSharedPreference.getLongitude(getActivity()),   // image longitude
                encodedImage,                                       // image in String
                thumbnailEncodedImage,                              // thumbnail image in string
                new StringCallback() {
                    @Override
                    public void done(String string) {
                        if (string.contains("Success:")){
                            String[] arr = string.split(" ");
                            setUiid(Integer.parseInt(arr[1]));
                            uploadImageToEpisode();
                        }
                    }
                });
    }

    private void uploadImageToEpisode(){
        List<Episode> singleEpisodeList = ((SendToEpisodesRecyclerAdapter) adapter).getEpisodeList();
        if (singleEpisodeList.size() > 0){
            for(int i = 0; i < singleEpisodeList.size(); i++){
                final Episode singleEpisode = singleEpisodeList.get(i);
                if(singleEpisode.getIsSelected()){
                    pd.setMessage("...to " + singleEpisode.getEpisodeTitle());
                    new EventUserImageRequest().addImagesToEvent(singleEpisode.getEpisodeEid(), getUiid(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            try{
                                JSONArray jArray = new JSONArray(string);
                                for(int i =0; i < jArray.length(); i++){
                                    if(jArray.get(i).equals("Success")){
                                        //Log.e("ok", "worksad");
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Error uploading image to " + singleEpisode.getEpisodeTitle(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            catch (JSONException jsone){
                                jsone.printStackTrace();
                            }
                        }
                    });
                }
            }
            pd.dismiss();
            getActivity().finish();
        }
    }


    private void setLiveParticipatedEpisodes(){
            new EventRequest(getActivity()).getEventDataByMember(SaveSharedPreference.getUserUID(getActivity()), new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("sendTo", string);
                try{
                    JSONObject jsonObject = new JSONObject(string);
                    String eventsString = jsonObject.getString("events");
                    JSONArray eventsArray = new JSONArray(eventsString);
                    ArrayList<Episode> episodeList = new ArrayList<>();
                    String[] dateTime, dateArray, timeArray;
                    HTTPConnection httpConnection = new HTTPConnection();
                    for (int i = 0; i < eventsArray.length(); i++){
                        JSONObject eventsObj = eventsArray.getJSONObject(i);
                        String eventHostString = eventsObj.getString("eventHost");
                        JSONObject eventHostObj = new JSONObject(eventHostString);
                        String eventProfileImageString = eventsObj.getString("eventProfileImages");
                        JSONArray eventProfileImageArray = new JSONArray(eventProfileImageString);
                        String imagePath;
                        if (eventProfileImageArray.length() > 0){
                            JSONObject imagePathObj = eventProfileImageArray.getJSONObject(0);
                            imagePath = httpConnection.getUploadServerString() + imagePathObj.getString("euiThumbnailPath");
                        }
                        else {
                            imagePath = "empty";
                        }
                        Log.e("imagePath", imagePath);
                        String startTime = eventsObj.getString("eventStartDatetime");
                        if (eventsObj.getString("eventEndDatetime").equals("null") && !startTime.equals("null")){
                            dateTime = startTime.split("\\s");
                            dateArray = dateTime[0].split("-");
                            timeArray = dateTime[1].split(":");

                            if (year > Integer.valueOf(dateArray[0])){ //if current year is greater than eventStartDate year, automatically add into List
                                Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), imagePath, false);
                                episodeList.add(episode);
                            }
                            else if (year == Integer.valueOf(dateArray[0])){ //if current year is equal to eventStartDate year
                                if (month > Integer.valueOf(dateArray[1])){
                                    Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), imagePath, false);
                                    episodeList.add(episode);
                                }
                                else if (month == Integer.valueOf(dateArray[1])){
                                    if (day > Integer.valueOf(dateArray[2])){
                                        Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), imagePath, false);
                                        episodeList.add(episode);
                                    }
                                    else if (day == Integer.valueOf(dateArray[2])){
                                        if (hour > Integer.valueOf(timeArray[0])){
                                            Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), imagePath, false);
                                            episodeList.add(episode);
                                        }
                                        else if (hour == Integer.valueOf(timeArray[0])){
                                            if (minute > Integer.valueOf(timeArray[1])){
                                                Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), imagePath, false);
                                                episodeList.add(episode);
                                            }
                                            else if (minute == Integer.valueOf(timeArray[1])){
                                                if (second > Integer.valueOf(timeArray[2])){
                                                    Episode episode = new Episode(eventsObj.getString("eventName"), eventHostObj.getString("firstName")+ " " + eventHostObj.getString("lastName"), eventHostObj.getString("username"), Integer.valueOf(eventsObj.getString("eid")), imagePath,false);
                                                    episodeList.add(episode);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    adapter = new SendToEpisodesRecyclerAdapter(getActivity(), episodeList);
                    layoutManager = new LinearLayoutManager(getActivity());
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
        this.uiid[0] = uiid;
    }

    private Integer[] getUiid(){
        return uiid;
    }

    private String imageName(){
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", new java.util.Date());
        return SaveSharedPreference.getUserUID(getActivity()) + "_" + charSequenceName;
    }
}
