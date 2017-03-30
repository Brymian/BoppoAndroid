package brymian.bubbles.bryant.episodes;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.map.MapActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.MiscellaneousRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserCommentRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserLikeRequest;
import brymian.bubbles.objects.Event;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;

public class EpisodeActivity extends AppCompatActivity implements View.OnClickListener{
    public static List<Bitmap> episodeImage = new ArrayList<>();
    TextView  tvEpisodeHostName, tvEpisodeHostUsername, tvLikeCount, tvDislikeCount, tvRating, tvViewCount, tvCommentsNumber;
    FloatingActionButton fabPlay;
    ImageView ivLike, ivDislike, ivAddComment, ivParticipants, ivMap, ivAddImage;
    EditText etAddComment;
    Toolbar mToolbar;
    RecyclerView rvComments;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    //int ADD_PARTICIPANTS_CODE = 123;
    private int eid;
    private boolean isHost, isParticipant, isStarted, isEnded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        /*--------------------------------Checking for putExtras()--------------------------------*/
        int eid;
        String episodeTitle;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eid = 0;
                episodeTitle = null;
            }
            else {
                eid = extras.getInt("eid");
                episodeTitle = extras.getString("episodeTitle");
            }
        }
        else {
            eid = savedInstanceState.getInt("eid");
            episodeTitle = savedInstanceState.getString("episodeTitle");
        }
        /*----------------------------------------------------------------------------------------*/

        tvEpisodeHostName = (TextView) findViewById(R.id.tvEpisodeHostName);
        tvEpisodeHostUsername = (TextView) findViewById(R.id.tvEpisodeHostUsername);
        tvLikeCount = (TextView) findViewById(R.id.tvLikeCount);
        tvDislikeCount = (TextView) findViewById(R.id.tvDislikeCount);
        tvViewCount = (TextView) findViewById(R.id.tvViewCount);
        ivLike = (ImageView) findViewById(R.id.ivLike);
        ivLike.setOnClickListener(this);
        ivDislike = (ImageView) findViewById(R.id.ivDislike);
        ivDislike.setOnClickListener(this);
        tvRating = (TextView) findViewById(R.id.tvRating);
        fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);
        fabPlay.setOnClickListener(this);
        ivParticipants = (ImageView) findViewById(R.id.ivParticipants);
        ivParticipants.setOnClickListener(this);
        ivMap = (ImageView) findViewById(R.id.ivMap);
        ivMap.setOnClickListener(this);
        ivAddImage = (ImageView) findViewById(R.id.ivAddImage);
        ivAddImage.setOnClickListener(this);
        tvCommentsNumber = (TextView) findViewById(R.id.tvCommentsNumber);
        etAddComment = (EditText) findViewById(R.id.etAddComment);
        ivAddComment = (ImageView) findViewById(R.id.ivAddComment);
        ivAddComment.setOnClickListener(this);
        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        setEid(eid);
        incrementViewCount(eid);
        getEpisodeInfo(eid);
        getEpisodeComments();
        setIsParticipant();

        mToolbar.setTitle(episodeTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(1);
        downloadEpisodePictures();

        Log.e("episodeImage.size()", ""+episodeImage.size());
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();

        switch (view.getId()){
            case R.id.ivLike:
                new UserLikeRequest(EpisodeActivity.this).setObjectLikeOrDislike(SaveSharedPreference.getUserUID(EpisodeActivity.this), "Event", getEid(), true, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("Like", string);
                    }
                });
                break;

            case R.id.ivDislike:
                new UserLikeRequest(EpisodeActivity.this).setObjectLikeOrDislike(SaveSharedPreference.getUserUID(EpisodeActivity.this), "Event", getEid(), false, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("Dislike", string);
                    }
                });
                break;

            case R.id.ivParticipants:
                startActivity(new Intent(this, EpisodeParticipants.class).putExtra("eid", getEid()).putExtra("isHost", getIsHost()));
                //startActivityForResult(new Intent(this, EpisodeParticipants.class), ADD_PARTICIPANTS_CODE);
                break;

            case R.id.ivMap:
                startActivity(new Intent(this, MapActivity.class));
                break;

            case R.id.ivAddImage:

                break;

            case R.id.fabPlay:
                if (episodeImage.size() > 0){
                    startFragment(fm, R.id.episode_activity, new EpisodeView());
                }
                break;

            case R.id.ivAddComment:
                addComment();
                break;

            case R.id.tvDeleteEpisode:
                new EventRequest(this).deleteEvent(getEid(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("deleteEpisode", string);
                    }
                });
                break;

            case R.id.tvEndEpisode:
                new EventRequest(this).updateEvent(getEid(), null, null, null, null, null, null, getCurrentTimeStamp(), null, null, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("EndEpisode", string);
                    }
                });
                break;

            case R.id.tvLeaveEpisode:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

            /*

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
            */


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

            case R.id.popUpMenu:
                popUpMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        episodeImage.clear();
    }

    private void addComment(){
        new UserCommentRequest(this).setObjectComment(SaveSharedPreference.getUserUID(this), "Event", getEid(), null, etAddComment.getText().toString(), null, new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("addComment", string);
            }
        });
    }

    private void getEpisodeComments() {
        new UserCommentRequest(this).getObjectComments("Event", getEid(), new StringCallback() {
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
                        Log.e("comments", string);
                        tvCommentsNumber.setText(String.valueOf(jArray.length()));
                        for (int i = 0; i < jArray.length(); i++){
                            JSONObject jArray_jObject = jArray.getJSONObject(i);
                            uid.add(jArray_jObject.getInt("uid"));
                            userComment.add(jArray_jObject.getString("userComment"));
                            userCommentTimestamp.add(commentTimestampLayout(jArray_jObject.getString("userCommentSetTimestamp")));
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
                        return "1 month ago";
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
                                                    return "1 minute ago";
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
                                                        return "1 minute ago";
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
                    return "1 year ago";
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
                            return "1 month ago";
                        }
                    }
                    else if (monthDifference > 1) {
                        if (commentDay < currentDay) {
                            return monthDifference + " months ago";
                        }
                        else if (commentDay > currentDay) {
                            int newMonthDifference = monthDifference - 1;
                            if (newMonthDifference == 1) {
                                return "1 month ago";
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
                        return "1 year ago";
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
                            return "1 year ago";
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

    private void getEpisodeInfo(int eid){
        /** BRYANT FIX THIS **/
        /*
        new EventRequest(this).getEventData(eid, new EventCallback() {
            @Override
            public void done(Event event) {
                mToolbar.setTitle(event.eventName);
                tvEpisodeHostName.setText(event.eventHostFirstName + " " + event.eventHostLastName);
                tvEpisodeHostUsername.setText(event.eventHostUsername);
                tvLikeCount.setText(String.valueOf(event.eventLikeCount));
                tvDislikeCount.setText(String.valueOf(event.eventDislikeCount));
                tvViewCount.setText(String.valueOf(event.eventViewCount) + " views");

                if (event.eventLikeCount == 0 && event.eventDislikeCount == 0){
                    tvRating.setText("0.0%");
                } else {
                    double total = (double) event.eventDislikeCount + (double) event.eventLikeCount;
                    double dislikePercent = 100 * ((double) event.eventDislikeCount / total);
                    double rating = 100 - dislikePercent;
                    tvRating.setText(String.valueOf(round(rating, 2)) + "%");
                }

                if (event.eventHostUid == SaveSharedPreference.getUserUID(EpisodeActivity.this)){
                    setIsHost(true);
                } else{
                    setIsHost(false);
                }

                if (event.eventStartDatetime.equals("null")){
                    setIsStarted(false);
                } else {
                    setIsStarted(true);
                }

                if (event.eventEndDatetime.equals("null")){
                    setIsEnded(false);
                } else {
                    setIsEnded(true);
                }
            }
        });
        */
    }

    private void setIsParticipant(){
        new EventUserRequest(this).getEventUsersData("Joined", getEid(), new StringCallback() {
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

        LinearLayout llMap = (LinearLayout) alertLayout.findViewById(R.id.llMap);
        CheckBox cbMap = (CheckBox) alertLayout.findViewById(R.id.cbMap);
        cbMap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        LinearLayout llPrivate = (LinearLayout) alertLayout.findViewById(R.id.llPrivate);
        CheckBox cbPrivate = (CheckBox) alertLayout.findViewById(R.id.cbPrivate);
        cbPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        TextView tvEndEpisode = (TextView) alertLayout.findViewById(R.id.tvEndEpisode);
        tvEndEpisode.setOnClickListener(this);
        TextView tvChangeStartDate = (TextView) alertLayout.findViewById(R.id.tvChangeStartDate);
        tvChangeStartDate.setOnClickListener(this);
        TextView tvLeaveEpisode = (TextView) alertLayout.findViewById(R.id.tvLeaveEpisode);
        tvLeaveEpisode.setOnClickListener(this);
        TextView tvDeleteEpisode = (TextView) alertLayout.findViewById(R.id.tvDeleteEpisode);
        tvDeleteEpisode.setOnClickListener(this);
        TextView tvReport = (TextView) alertLayout.findViewById(R.id.tvReport);

        View vPrivate = alertLayout.findViewById(R.id.vPrivate);
        View vMap = alertLayout.findViewById(R.id.vMap);
        View vEndEpisode = alertLayout.findViewById(R.id.vEndEpisode);
        View vChangeStartDate = alertLayout.findViewById(R.id.vChangeStartDate);
        View vLeaveEpisode = alertLayout.findViewById(R.id.vLeaveEpisode);
        View vDeleteEpisode = alertLayout.findViewById(R.id.vDeleteEpisode);

        if (getIsHost() && !getIsStarted() && !getIsEnded()){
            tvLeaveEpisode.setVisibility(View.GONE);
            vLeaveEpisode.setVisibility(View.GONE);
            tvReport.setVisibility(View.GONE);
        }
        else if (getIsHost() && !getIsEnded()){
            tvLeaveEpisode.setVisibility(View.GONE);
            vLeaveEpisode.setVisibility(View.GONE);
            tvChangeStartDate.setVisibility(View.GONE);
            vChangeStartDate.setVisibility(View.GONE);
            vDeleteEpisode.setVisibility(View.GONE);
            tvReport.setVisibility(View.GONE);
        }
        else if (getIsHost() && getIsStarted() && getIsEnded()){
            llPrivate.setVisibility(View.GONE);
            vPrivate.setVisibility(View.GONE);
            tvEndEpisode.setVisibility(View.GONE);
            vEndEpisode.setVisibility(View.GONE);
            tvChangeStartDate.setVisibility(View.GONE);
            vChangeStartDate.setVisibility(View.GONE);
            tvLeaveEpisode.setVisibility(View.GONE);
            vLeaveEpisode.setVisibility(View.GONE);
            tvReport.setVisibility(View.GONE);
        }
        else if (getIsParticipant()){
            llPrivate.setVisibility(View.GONE);
            vPrivate.setVisibility(View.GONE);
            llMap.setVisibility(View.GONE);
            vMap.setVisibility(View.GONE);
            tvEndEpisode.setVisibility(View.GONE);
            vEndEpisode.setVisibility(View.GONE);
            tvChangeStartDate.setVisibility(View.GONE);
            vChangeStartDate.setVisibility(View.GONE);
            tvDeleteEpisode.setVisibility(View.GONE);
            vDeleteEpisode.setVisibility(View.GONE);
        }
        else {
            llPrivate.setVisibility(View.GONE);
            vPrivate.setVisibility(View.GONE);
            llMap.setVisibility(View.GONE);
            vMap.setVisibility(View.GONE);
            tvEndEpisode.setVisibility(View.GONE);
            vEndEpisode.setVisibility(View.GONE);
            tvChangeStartDate.setVisibility(View.GONE);
            vChangeStartDate.setVisibility(View.GONE);
            tvLeaveEpisode.setVisibility(View.GONE);
            vLeaveEpisode.setVisibility(View.GONE);
            tvDeleteEpisode.setVisibility(View.GONE);
            vDeleteEpisode.setVisibility(View.GONE);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        return sdfDate.format(now);
    }

    private void incrementViewCount(int eid){
        new MiscellaneousRequest(this).incrementObjectViewCount(eid, "Event", new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("View Count", string);
            }
        });
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private boolean getIsParticipant(){
        return isParticipant;
    }

    private void setIsStarted(boolean isStarted){
        this.isStarted = isStarted;
    }

    private boolean getIsStarted(){
        return isStarted;
    }

    private void setIsEnded(boolean isEnded){
        this.isEnded = isEnded;
    }

    private boolean getIsEnded(){
        return  isEnded;
    }

    private void setEid(int eid){
        this.eid = eid;
    }

    private int getEid(){
        return eid;
    }

    private void setIsHost(boolean isHost){
        this.isHost = isHost;
    }

    private boolean getIsHost(){
        return isHost;
    }

    private void downloadEpisodePictures(){
    /** BRYANT FIX THIS **/
    /*
        new UserImageRequest(this).getImagesByEid(getEid(), new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    List<String> episodeImagePath = new ArrayList<>();
                    HTTPConnection httpConnection = new HTTPConnection();
                    JSONArray jsonArray = new JSONArray(string);
                    Log.e("try","length: " + jsonArray.length());
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jArray_jObject = jsonArray.getJSONObject(i);
                        JSONObject jImage = jArray_jObject.getJSONObject("image");
                        episodeImagePath.add(httpConnection.getUploadServerString() + jImage.getString("userImagePath").replaceAll(" ", "%20"));
                        Log.e("image path", httpConnection.getUploadServerString() + jImage.getString("userImagePath").replaceAll(" ", "%20"));
                        new DownloadEpisodeImage(episodeImagePath.get(i)).execute();
                    }
                }catch (JSONException jsone){
                    jsone.printStackTrace();
                }
            }
        });
        */
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
                episodeImage.add(bitmap);
            }
            else{
                Log.e("bitmap", "bitmap not here");
            }
        }
    }
}
