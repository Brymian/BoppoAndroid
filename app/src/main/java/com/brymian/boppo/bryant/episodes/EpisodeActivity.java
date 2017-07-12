package com.brymian.boppo.bryant.episodes;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.camera.CameraActivity;
import com.brymian.boppo.bryant.nonactivity.Misc;
import com.brymian.boppo.bryant.nonactivity.SaveSharedPreference;
import com.brymian.boppo.bryant.profile.ProfileActivity;
import com.brymian.boppo.damian.nonactivity.ServerRequest.EventUserRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequest.MiscellaneousRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.EventRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequest.UserCommentRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequest.UserImageRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequest.UserLikeRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequestMethods;

import static com.brymian.boppo.damian.nonactivity.Miscellaneous.startFragment;

public class EpisodeActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvEnd, tvStartDatetime, tvEndDatetime, tvLocationName, tvLocationAddress, tvDescription, tvParticipants, tvAddPhoto, tvEpisodeHostName, tvEpisodeHostUsername, tvLikeCount, tvDislikeCount, tvViewCount, tvCommentsNumber;
    RelativeLayout rlLocation;
    FloatingActionButton fabPlay;
    ImageView ivEpisodeProfileImage, ivAddComment, ivEpisodeHostImage, ivMap;
    EditText tvAddComment;
    Toolbar mToolbar;
    View vComments;
    LinearLayout rlEpisodeHostInfo;
    Button bAddFriendHost;
    RecyclerView rvComments;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    //int ADD_PARTICIPANTS_CODE = 123;
    private int eid, hostUid;
    int year, month, day, hour, minute, second;
    private boolean isHost, isParticipant, isStarted, isEnded;
    private String latitude, longitude;
    private String episodeImagePath, episodeType, episodeTitle, episodeStartDateTime, episodeEndDateTime, locationName, locationAddress;
    List<Bitmap> loadedEpisodeImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.episode_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(1);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            CollapsingToolbarLayout.LayoutParams cl = (CollapsingToolbarLayout.LayoutParams)mToolbar.getLayoutParams();
            cl.setMargins(0, getStatusBarHeight(), 0, 0);
        }
        //hide keyboard when activity starts
        /*--------------------------------Checking for putExtras()--------------------------------*/
        int eid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eid = 0;
            }
            else {
                eid = extras.getInt("eid");
            }
        }
        else {
            eid = savedInstanceState.getInt("eid");
        }
        /*----------------------------------------------------------------------------------------*/

        fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);
        fabPlay.setOnClickListener(this);

        tvViewCount = (TextView) findViewById(R.id.tvViewCount);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvLikeCount = (TextView) findViewById(R.id.tvLikeCount);
        tvLikeCount.setOnClickListener(this);
        tvDislikeCount = (TextView) findViewById(R.id.tvDislikeCount);
        tvDislikeCount.setOnClickListener(this);
        tvParticipants = (TextView) findViewById(R.id.tvParticipants);
        tvParticipants.setOnClickListener(this);
        tvAddPhoto = (TextView) findViewById(R.id.tvAddPhoto);
        tvAddPhoto.setOnClickListener(this);

        tvStartDatetime = (TextView) findViewById(R.id.tvStartDatetime);
        tvEndDatetime = (TextView) findViewById(R.id.tvEndDatetime);
        tvEnd = (TextView) findViewById(R.id.tvEnd);

        ivMap = (ImageView) findViewById(R.id.ivMap);
        rlLocation = (RelativeLayout) findViewById(R.id.rlLocation);
        tvLocationName = (TextView) findViewById(R.id.tvLocationName);
        tvLocationAddress = (TextView) findViewById(R.id.tvLocationAddress);

        rlEpisodeHostInfo = (LinearLayout) findViewById(R.id.rlEpisodeHostInfo);
        rlEpisodeHostInfo.setOnClickListener(this);
        ivEpisodeProfileImage = (ImageView) findViewById(R.id.ivEpisodeProfileImage);
        tvEpisodeHostName = (TextView) findViewById(R.id.tvEpisodeHostName);
        tvEpisodeHostUsername = (TextView) findViewById(R.id.tvEpisodeHostUsername);
        ivEpisodeHostImage = (ImageView) findViewById(R.id.ivEpisodeHostImage);
        bAddFriendHost = (Button) findViewById(R.id.bAddFriendHost);

        tvCommentsNumber = (TextView) findViewById(R.id.tvCommentsNumber);
        tvAddComment = (EditText) findViewById(R.id.tvAddComment);
        ivAddComment = (ImageView) findViewById(R.id.ivAddComment);
        ivAddComment.setOnClickListener(this);
        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        vComments = findViewById(R.id.vComments);

        this.eid = eid;
        setDateTime();
        getEpisodeProfilePictures(eid);
        getEpisodeInfo(eid);
        incrementViewCount(eid);
        getEpisodeComments();
        setIsParticipant();
        downloadEpisodePictures();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();

        switch (view.getId()){
            case R.id.tvLikeCount:
                new UserLikeRequest(EpisodeActivity.this).setObjectLikeOrDislike(SaveSharedPreference.getUserUID(EpisodeActivity.this), "Event", eid, true, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("Like", string);
                        //if (string.equals("User has successfully liked or disliked the object.")){}
                    }
                });
                break;

            case R.id.tvDislikeCount:
                new UserLikeRequest(EpisodeActivity.this).setObjectLikeOrDislike(SaveSharedPreference.getUserUID(EpisodeActivity.this), "Event", eid, false, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("Dislike", string);
                        //if (string.equals("User has successfully liked or disliked the object.")){}
                    }
                });
                break;

            case R.id.tvParticipants:
                EpisodeParticipants episodeParticipants = new EpisodeParticipants();
                Bundle bundle = new Bundle();
                bundle.putInt("eid", eid);
                bundle.putBoolean("isHost", isHost);
                bundle.putBoolean("isEnded", isEnded);
                episodeParticipants.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.episode_activity, episodeParticipants);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case R.id.tvAddPhoto:
                if (isStarted){
                    startActivity(new Intent(this, CameraActivity.class));
                }
                break;

            case R.id.fabPlay:
                if (loadedEpisodeImages.size() > 0){
                    startFragment(fm, R.id.episode_activity, new EpisodeView());
                }
                break;

            case R.id.ivAddComment:
                addComment();
                break;

            case R.id.tvLeaveEpisode:

                break;

            case R.id.rlEpisodeHostInfo:
                startActivity(new Intent(this, ProfileActivity.class).putExtra("uid", hostUid));
                break;

            case R.id.bAddFriendHost:
                new ServerRequestMethods(this).setFriendStatus(SaveSharedPreference.getUserUID(EpisodeActivity.this), hostUid, new StringCallback() {
                    @Override
                    public void done(String string) {
                        if (string.equals("Friendship Pending request sent successfully.")) {
                            bAddFriendHost.setText("Sent");
                            bAddFriendHost.setOnClickListener(null);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.episode_activity_menu_inflater, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.episodeOptions:
                if (isHost){
                    EpisodeEdit episodeEdit = new EpisodeEdit();
                    Bundle bundle = new Bundle();
                    bundle.putInt("eid", eid);
                    bundle.putString("episodeTitle", episodeTitle);
                    bundle.putString("episodeType", episodeType);
                    bundle.putString("episodeImagePath", episodeImagePath);
                    bundle.putString("episodeStartDateTime", episodeStartDateTime);
                    bundle.putString("episodeEndDateTime", episodeEndDateTime);
                    bundle.putString("locationName", locationName);
                    bundle.putString("locationAddress", locationAddress);
                    episodeEdit.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.episode_activity, episodeEdit);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else {
                    popUpMenu();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
        else {
            super.onBackPressed();
        }
    }

    private void getEpisodeInfo(int eid){
        new EventRequest(this).getEventData(eid, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    Log.e("string", string);
                    JSONObject episodeInfoObject = new JSONObject(string);
                    episodeTitle = episodeInfoObject.getString("eventName");
                    mToolbar.setTitle(episodeTitle);
                    episodeType = episodeInfoObject.getString("eventTypeLabel");
                    tvLikeCount.setText(episodeInfoObject.getString("eventLikeCount"));
                    tvDislikeCount.setText(episodeInfoObject.getString("eventDislikeCount"));
                    tvViewCount.setText(episodeInfoObject.getString("eventViewCount") + " views");
                    String description = episodeInfoObject.getString("eventDescriptionText");
                    latitude = episodeInfoObject.getString("eventGpsLatitude");
                    longitude = episodeInfoObject.getString("eventGpsLongitude");
                    if (description.equals("null")){
                        tvDescription.setVisibility(View.GONE);
                    }
                    else {
                        tvDescription.setText(description);
                    }

                    Misc misc = new Misc();
                    episodeStartDateTime = episodeInfoObject.getString("eventStartDatetime");
                    episodeEndDateTime = episodeInfoObject.getString("eventEndDatetime");
                    tvStartDatetime.setText(misc.dateTimeConverterTogether(episodeStartDateTime));
                    if (episodeEndDateTime.equals("null")){
                        tvEnd.setVisibility(View.GONE);
                        tvEndDatetime.setVisibility(View.GONE);
                    }
                    else {
                        tvEndDatetime.setText(misc.dateTimeConverterTogether(episodeEndDateTime));
                    }

                    String episodeAddressString = episodeInfoObject.getString("eventAddress");
                    JSONObject episodeAddressObj = new JSONObject(episodeAddressString);
                    locationName = episodeAddressObj.getString("addressName");
                    locationAddress = episodeAddressObj.getString("addressUnparsedText");

                    if (latitude.equals("0") && longitude.equals("0")){
                        ivMap.setVisibility(View.GONE);
                        rlLocation.setVisibility(View.GONE);
                    }
                    else {
                        setStaticMap();
                        tvLocationName.setText(locationName);
                        tvLocationAddress.setText(locationAddress);
                    }


                    String episodeHostInfoString = episodeInfoObject.getString("eventHost");
                    JSONObject episodeHostInfoObject = new JSONObject(episodeHostInfoString);
                    tvEpisodeHostName.setText(episodeHostInfoObject.getString("firstName") + " " + episodeHostInfoObject.getString("lastName"));
                    tvEpisodeHostUsername.setText(episodeHostInfoObject.getString("username"));
                    hostUid = Integer.valueOf(episodeHostInfoObject.getString("uid"));
                    setIsFriendWithHost();

                    String episodeHostImageString = episodeHostInfoObject.getString("userProfileImages");
                    JSONArray episodeHostImageArray = new JSONArray(episodeHostImageString);
                    JSONObject episodeHostImageObject = episodeHostImageArray.getJSONObject(0);
                    Picasso.with(EpisodeActivity.this).load(episodeHostImageObject.getString("userImageThumbnailPath")).fit().centerCrop().into(ivEpisodeHostImage);

                    /*
                    //for ratings
                    int likeCount = Integer.valueOf(episodeInfoObject.getString("eventLikeCount"));
                    int dislikeCount = Integer.valueOf(episodeInfoObject.getString("eventDislikeCount"));
                    if ( likeCount == 0 && dislikeCount == 0){
                        tvRating.setText("0.0%");
                    }
                    else {
                        double total = (double) dislikeCount + (double) likeCount;
                        double dislikePercent = 100 * ((double) dislikeCount / total);
                        double rating = 100 - dislikePercent;
                        tvRating.setText(String.valueOf(round(rating, 2)) + "%");
                    }
                    */

                    isHost = Integer.valueOf(episodeHostInfoObject.getString("uid")) == SaveSharedPreference.getUserUID(EpisodeActivity.this);

                    if (!episodeInfoObject.getString("eventStartDatetime").equals("null")){
                        String[] dateTime, dateArray, timeArray;
                        String startTime = episodeInfoObject.getString("eventStartDatetime");
                        dateTime = startTime.split("\\s");
                        dateArray = dateTime[0].split("-");
                        timeArray = dateTime[1].split(":");

                        if (year > Integer.valueOf(dateArray[0])){ //if current startYear is greater than eventStartDate startYear, automatically add into List
                            isStarted = true;
                        }
                        else if (year == Integer.valueOf(dateArray[0])){ //if current startYear is equal to eventStartDate startYear
                            if (month > Integer.valueOf(dateArray[1])){
                                isStarted = true;
                            }
                            else if (month == Integer.valueOf(dateArray[1])){
                                if (day > Integer.valueOf(dateArray[2])){
                                    isStarted = true;
                                }
                                else if (day == Integer.valueOf(dateArray[2])){
                                    if (hour > Integer.valueOf(timeArray[0])){
                                        isStarted = true;
                                    }
                                    else if (hour == Integer.valueOf(timeArray[0])){
                                        if (minute > Integer.valueOf(timeArray[1])){
                                            isStarted = true;
                                        }
                                        else if (minute == Integer.valueOf(timeArray[1])){
                                            if (second > Integer.valueOf(timeArray[2])){
                                                isStarted = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (episodeInfoObject.getString("eventEndDatetime").equals("null")){
                        isEnded = true;
                    }
                    else {
                        String[] dateTime, dateArray, timeArray;
                        String endTime = episodeInfoObject.getString("eventEndDatetime");
                        dateTime = endTime.split("\\s");
                        dateArray = dateTime[0].split("-");
                        timeArray = dateTime[1].split(":");

                        if (year > Integer.valueOf(dateArray[0])){ //if current startYear is greater than eventStartDate startYear, automatically add into List
                            isEnded = true;
                        }
                        else if (year == Integer.valueOf(dateArray[0])){ //if current startYear is equal to eventStartDate startYear
                            if (month > Integer.valueOf(dateArray[1])){
                                isEnded = true;
                            }
                            else if (month == Integer.valueOf(dateArray[1])){
                                if (day > Integer.valueOf(dateArray[2])){
                                    isEnded = true;
                                }
                                else if (day == Integer.valueOf(dateArray[2])){
                                    if (hour > Integer.valueOf(timeArray[0])){
                                        isEnded = true;
                                    }
                                    else if (hour == Integer.valueOf(timeArray[0])){
                                        if (minute > Integer.valueOf(timeArray[1])){
                                            isEnded = true;
                                        }
                                        else if (minute == Integer.valueOf(timeArray[1])){
                                            if (second > Integer.valueOf(timeArray[2])){
                                                isEnded = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void addComment(){
        new UserCommentRequest(this).setObjectComment(SaveSharedPreference.getUserUID(this), "Event", eid, null, tvAddComment.getText().toString(), null, new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("addComment", string);
            }
        });
    }

    private void getEpisodeComments() {
        new UserCommentRequest(this).getObjectComments("Event", eid, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    if (string.equals("\"The specified object with the specified ID does not exist, or it has no comments.\"")){
                        tvCommentsNumber.setText("0");
                    }
                    else{
                        List<String> userProfileImagePath = new ArrayList<>();
                        List<Integer> uid = new ArrayList<>();
                        List<String> userComment = new ArrayList<>();
                        List<String> userCommentTimestamp = new ArrayList<>();
                        List<String> userUsername = new ArrayList<>();

                        JSONObject object = new JSONObject(string);
                        JSONArray jArray  = object.getJSONArray("comments");
                        tvCommentsNumber.setText(String.valueOf(jArray.length()));
                        vComments.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jArray.length(); i++){
                            JSONObject jArray_jObject = jArray.getJSONObject(i);
                            uid.add(jArray_jObject.getInt("uid"));
                            userComment.add(jArray_jObject.getString("userComment"));
                            userCommentTimestamp.add(commentTimestampLayout(jArray_jObject.getString("userCommentUpsertTimestamp")));
                            userUsername.add(jArray_jObject.getJSONObject("user").getString("username"));
                            userProfileImagePath.add(jArray_jObject.getJSONObject("image").getString("userImagePath"));
                        }

                        adapter = new EpisodeActivityCommentsRecyclerAdapter(EpisodeActivity.this, uid, userUsername, userProfileImagePath, userComment, userCommentTimestamp);
                        layoutManager = new LinearLayoutManager(EpisodeActivity.this);
                        rvComments.setLayoutManager(layoutManager);
                        rvComments.setNestedScrollingEnabled(false);
                        rvComments.setAdapter(adapter);
                    }
                }
                catch (JSONException | NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

    }

    private String commentTimestampLayout(String timestamp){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentDateTimeString = dateFormat.format(date);
        String[] currentDateTimeArray = currentDateTimeString.split("\\s");
        String currentDateString = currentDateTimeArray[0];
        String currentTimeString = currentDateTimeArray[1];
        String[] currentDateArray = currentDateString.split("-");
        String[] currentTimeArray = currentTimeString.split(":");
        int currentYear = Integer.valueOf(currentDateArray[0]);
        int currentMonth = Integer.valueOf(currentDateArray[1]);
        int currentDay = Integer.valueOf(currentDateArray[2]);
        int currentHour = Integer.valueOf(currentTimeArray[0]);
        int currentMinute = Integer.valueOf(currentTimeArray[1]);
        int currentSecond = Integer.valueOf(currentTimeArray[2]);

        String[] commentDateTimeArray = timestamp.split("\\s");
        String commentDateString = commentDateTimeArray[0];
        String commentTimeString = commentDateTimeArray[1];
        String[] commentDateArray = commentDateString.split("-");
        String[] commentTimeArray = commentTimeString.split(":");
        int commentYear = Integer.valueOf(commentDateArray[0]);
        int commentMonth = Integer.valueOf(commentDateArray[1]);
        int commentDay = Integer.valueOf(commentDateArray[2]);
        int commentHour = Integer.valueOf(commentTimeArray[0]);
        int commentMinute = Integer.valueOf(commentTimeArray[1]);
        int commentSecond = Integer.valueOf(commentTimeArray[2]);
        if (commentYear == currentYear){
            if (commentMonth < currentMonth){
                int monthDifference = currentMonth - commentMonth;
                if (monthDifference == 1){
                    if (commentDay <= currentDay){
                        return "1 startMonth ago";
                    }
                    else if (commentDay > currentDay){
                        int commentDaysInMonth = 0;
                        if (commentMonth == 1 || commentMonth == 3 || commentMonth == 5 || commentMonth == 7 || commentMonth == 8 || commentMonth == 10 || commentMonth == 12){
                            commentDaysInMonth = 31;
                        }
                        else if (commentMonth == 5 || commentMonth == 6 || commentMonth == 9 || commentMonth == 11){
                            commentDaysInMonth = 30;
                        }
                        else if (commentMonth == 2){
                            if (commentYear == 2020 || commentYear == 2024){
                                commentDaysInMonth = 29;
                            }
                            else {
                                commentDaysInMonth = 28;
                            }
                        }
                        int dayDifference = (commentDaysInMonth - commentDay) + currentDay;
                        if (dayDifference < 7){
                            if (dayDifference == 1){
                                if (commentHour <= currentHour){
                                    return "Yesterday";
                                }
                                else if (commentHour > currentHour){
                                    int hourDifference = (24 - commentHour) + currentHour;
                                    if (hourDifference == 1){
                                        if (commentMinute < currentMinute){
                                            return "1 hour ago";
                                        }
                                        else if (commentMinute > currentMinute){
                                            int minuteDifference = (60 - commentMinute) + currentMinute;
                                            if (minuteDifference == 1){
                                                if (commentSecond <= currentSecond){
                                                    return "1 startMinute ago";
                                                }
                                                else if (commentSecond > currentSecond){
                                                    int secondDifference = (60 - commentSecond) + currentSecond;
                                                    if (secondDifference == 1){
                                                        return "1 second ago";
                                                    }
                                                    else if (secondDifference > 1){
                                                        return secondDifference + " seconds ago";
                                                    }
                                                }
                                            }
                                            else if (minuteDifference > 1){
                                                if (commentSecond <= currentSecond){
                                                    return minuteDifference + " minutes ago";
                                                }
                                                else if (commentSecond > currentSecond){
                                                    int newMinuteDifference = minuteDifference - 1;
                                                    if (newMinuteDifference == 1){
                                                        return "1 startMinute ago";
                                                    }
                                                    else if (newMinuteDifference > 1){
                                                        return newMinuteDifference + " minutes ago";
                                                    }
                                                }
                                            }
                                        }
                                        else if (commentMinute == currentMinute){

                                        }
                                    }
                                    else if (hourDifference > 1){

                                    }
                                }
                            }
                        }
                        else if (dayDifference >= 7){
                            int weeks = dayDifference / 7;
                            if (weeks == 1){
                                return "1 week ago";
                            }
                            else if (weeks > 1){
                                return weeks + " weeks ago";
                            }
                        }
                    }
                }
                else if (monthDifference > 1){

                }
            }
            if (commentMonth == currentMonth){
                if (commentDay == currentDay){
                    if (commentHour == currentHour){
                        if (commentMinute == currentMinute){
                            return "Just now";
                        }
                    }
                }
            }
        }
        else if (commentYear < currentYear) {
            int yearDifference = currentYear - commentYear;
            if (yearDifference == 1) {
                if (commentMonth < currentMonth) {
                    return "1 startYear ago";
                }
                else if (commentMonth > currentMonth) {
                    int monthDifference = 12 - (commentMonth - currentMonth);
                    if (monthDifference == 1) {//handles december basically
                        if (commentDay > currentDay) {//do weeks here
                            int daysInDecemberLeft = (31 - commentDay) + currentDay;
                            int weeks = daysInDecemberLeft / 4;
                            if (weeks == 1) {
                                return "1 week ago";
                            }
                            else if (weeks > 1) {
                                return weeks + " weeks ago";
                            }
                        }
                        if (commentDay <= currentDay) {
                            return "1 startMonth ago";
                        }
                    }
                    else if (monthDifference > 1) {
                        if (commentDay < currentDay) {
                            return monthDifference + " months ago";
                        }
                        else if (commentDay > currentDay) {
                            int newMonthDifference = monthDifference - 1;
                            if (newMonthDifference == 1) {
                                return "1 startMonth ago";
                            }
                            else if (newMonthDifference > 1) {
                                return newMonthDifference + " months ago";
                            }
                        }
                    }
                }
            }
            else if (yearDifference > 1) {
                if (commentMonth < currentMonth) {
                    return yearDifference + " years ago";
                }
                else if (commentMonth > currentMonth) {
                    int newYearDifference = yearDifference - 1;
                    if (newYearDifference == 1) {
                        return "1 startYear ago";
                    }
                    else if (newYearDifference > 1) {
                        return newYearDifference + " years ago";
                    }
                }
                else if (commentMonth == currentMonth){
                    if (commentDay <= currentDay){
                        return yearDifference + " years ago";
                    }
                    else if (commentDay > currentDay){
                        int newYearDifference = yearDifference - 1;
                        if (newYearDifference == 1) {
                            return "1 startYear ago";
                        }
                        else if (newYearDifference > 1) {
                            return newYearDifference + " years ago";
                        }
                    }
                }
            }
        }
        return timestamp;
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

    private void setIsParticipant(){
        new EventUserRequest().getEventUsersData("Joined", eid, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    if (string.length() > 0){
                        JSONArray jArray = new JSONArray(string);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jArray_jObject = jArray.getJSONObject(i);
                            JSONObject jEvent = jArray_jObject.getJSONObject("user");
                            if (jEvent.getInt("uid") == SaveSharedPreference.getUserUID(EpisodeActivity.this)){
                                isParticipant = true;
                                tvAddPhoto.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                catch (JSONException | NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void popUpMenu(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_activity_menu_alertdialog, null);

        TextView tvLeaveEpisode = (TextView) alertLayout.findViewById(R.id.tvLeaveEpisode);
        tvLeaveEpisode.setOnClickListener(this);
        TextView tvReport = (TextView) alertLayout.findViewById(R.id.tvReport);
        tvReport.setOnClickListener(this)
        ;
        if (!isParticipant){
            tvLeaveEpisode.setVisibility(View.GONE);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void incrementViewCount(int eid){
        new MiscellaneousRequest(this).incrementObjectViewCount(eid, "Event", new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("View Count", string);
            }
        });
    }

    private void setStaticMap(){
        String url = "http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=15&size=1000x150&scale=2&sensor=false";
        Picasso.with(this).load(url).fit().into(ivMap);
    }

    private void setIsFriendWithHost(){
        if (hostUid != SaveSharedPreference.getUserUID(this)){
            new ServerRequestMethods(this).getFriendStatus(SaveSharedPreference.getUserUID(EpisodeActivity.this), hostUid, new StringCallback() {
                @Override
                public void done(String string) {
                    if (string.equals("Not friends.")){
                        bAddFriendHost.setVisibility(View.VISIBLE);
                        bAddFriendHost.setOnClickListener(EpisodeActivity.this);
                    }
                }
            });
        }
    }

    /*
    //for ratings
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }*/

    private void getEpisodeProfilePictures(int eid){
        new UserImageRequest(this).getImagesByEid(eid, true, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    JSONObject jsonObject = new JSONObject(string);
                    String imagesString = jsonObject.getString("images");
                    JSONArray imagesArray = new JSONArray(imagesString);
                    if (imagesArray.length() > 0){
                        JSONObject imageObj = imagesArray.getJSONObject(0);
                        episodeImagePath = imageObj.getString("userImagePath");
                        Picasso.with(EpisodeActivity.this).load(episodeImagePath).fit().centerCrop().into(ivEpisodeProfileImage);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void downloadEpisodePictures(){
        new UserImageRequest(this).getImagesByEid(eid, false, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    JSONObject jsonObject = new JSONObject(string);
                    String imagesString = jsonObject.getString("images");
                    JSONArray jsonArray = new JSONArray(imagesString);
                    if (jsonArray.length() > 0){
                        fabPlay.show();
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject imagesObj = jsonArray.getJSONObject(i);
                            String imagePath = imagesObj.getString("userImagePath");
                            new DownloadEpisodeImage(imagePath).execute();
                        }
                    }
                }
                catch (JSONException jsone){
                    jsone.printStackTrace();
                }
            }
        });
    }

    private class DownloadEpisodeImage extends AsyncTask<Void, Void, Bitmap> {
        String path;
        public DownloadEpisodeImage(String path){
            this.path = path;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URLConnection connection = new URL(path).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);
                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                Log.e("bitmap", "bitmap here" );
                loadedEpisodeImages.add(bitmap);
            }
            else{
                Log.e("bitmap", "bitmap not here");
            }
        }
    }
}
