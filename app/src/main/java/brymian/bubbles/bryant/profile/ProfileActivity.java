package brymian.bubbles.bryant.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.EpisodeMyRecyclerAdapter;
import brymian.bubbles.bryant.friends.FriendsRecyclerAdapter;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.friends.FriendsActivity;
import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView ivProfilePicture;
    FloatingActionButton fabStatus;
    TextView tvProfileFirstLastName, tvFriendsNum, tvFriendStatus, tvEpisodesNum, tvSeeAllFriends, tvSeeAllEpisodes;
    int userUID;
    String username, privacy, friendShipStatus;
    Toolbar mToolbar;

    RecyclerView rvFriends, rvEpisodes;
    RecyclerView.Adapter adapter, adapterFriends, adapterEpisodes;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(1);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //mToolbar.setPadding(0, getStatusBarHeight(),0, 0);
        }

        /*--------------------------------Checking for putExtras()--------------------------------*/
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                uid = 0;
            }
            else {
                uid = extras.getInt("uid");
            }
        }
        else {
            uid = savedInstanceState.getInt("uid");
        }
        /*----------------------------------------------------------------------------------------*/
        ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);
        fabStatus = (FloatingActionButton) findViewById(R.id.fabStatus);
        tvFriendStatus = (TextView) findViewById(R.id.tvFriendStatus);
        tvFriendStatus.setVisibility(View.GONE);
        tvProfileFirstLastName = (TextView) findViewById(R.id.tvUserFirstLastName);
        tvFriendsNum = (TextView) findViewById(R.id.tvFriendsNum);
        tvEpisodesNum = (TextView) findViewById(R.id.tvEpisodesNum);
        tvSeeAllEpisodes = (TextView) findViewById(R.id.tvSeeAllEpisodes);
        tvSeeAllEpisodes.setOnClickListener(this);
        tvSeeAllFriends = (TextView) findViewById(R.id.tvSeeAllFriends);
        tvSeeAllFriends.setOnClickListener(this);

        rvFriends = (RecyclerView) findViewById(R.id.rvFriends);
        rvEpisodes = (RecyclerView) findViewById(R.id.rvEpisodes);


        if(uid != 0){
            setUID(uid);
            setFriendshipStatus(uid);
            getProfilePictures(uid);
            getFriends(uid);
            setUserProfileInfo(uid);
            getEpisodes(uid);
        }
    }

    // A method to find height of the status bar
    /*
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
            Log.e("statusBarHght", result + "");
        }
        return result;
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_activity_menu_inflater, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.popUpMenu:
                if (getUID() == SaveSharedPreference.getUserUID(this)){
                    startActivity(new Intent(this, ProfileEdit.class));
                }
                else{
                    popUpMenu();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvEditProfile:
                startActivity(new Intent(this, ProfileEdit.class));
                break;
            case R.id.tvRemoveFriend:
                new FriendshipStatusRequest(this).unFriend(SaveSharedPreference.getUserUID(this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.tvBlockUser:
                new FriendshipStatusRequest(this).blockUser(SaveSharedPreference.getUserUID(this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.tvSeeAllFriends:
                startActivity(new Intent(this, FriendsActivity.class).putExtra("uid", getUID()));
                break;
            case R.id.tvReportUser:
                Toast.makeText(this, "Report under construction", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void popUpMenu(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.profile_activity_menu_alertdialog, null);

        TextView tvEditProfile = (TextView) alertLayout.findViewById(R.id.tvEditProfile);
        tvEditProfile.setOnClickListener(this);
        TextView tvRemoveFriend = (TextView) alertLayout.findViewById(R.id.tvRemoveFriend);
        tvRemoveFriend.setOnClickListener(this);
        TextView tvBlockUser = (TextView) alertLayout.findViewById(R.id.tvBlockUser);
        tvBlockUser.setOnClickListener(this);
        TextView tvReportUser = (TextView) alertLayout.findViewById(R.id.tvReportUser);
        tvReportUser.setOnClickListener(this);

        View vEditProfile = alertLayout.findViewById(R.id.vEditProfile);
        View vRemoveFriend = alertLayout.findViewById(R.id.vRemoveFriend);
        View vBlockUser = alertLayout.findViewById(R.id.vBlockUser);

        switch (getFriendShipStatus()){
            case "logged in user":
                tvRemoveFriend.setVisibility(View.GONE);
                vRemoveFriend.setVisibility(View.GONE);
                tvBlockUser.setVisibility(View.GONE);
                vBlockUser.setVisibility(View.GONE);
                tvReportUser.setVisibility(View.GONE);
                break;
            case "Already friends with user.":
                tvEditProfile.setVisibility(View.GONE);
                vEditProfile.setVisibility(View.GONE);
                break;
            case "Already sent friend request to user.":
                tvEditProfile.setVisibility(View.GONE);
                vEditProfile.setVisibility(View.GONE);
                break;
            case "User is awaiting confirmation for friend request.":
                tvEditProfile.setVisibility(View.GONE);
                vEditProfile.setVisibility(View.GONE);
                break;
            case "Not friends.":
                tvEditProfile.setVisibility(View.GONE);
                vEditProfile.setVisibility(View.GONE);
                break;
            case "User is currently being blocked.":
                break;
            case "Currently being blocked by user.":
                break;
            default:
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void setFriendshipStatus(int uid){
        if (uid == SaveSharedPreference.getUserUID(this)){
            setUserProfileDisplay("logged in user");
            }
        else{
            new ServerRequestMethods(this).getFriendStatus(SaveSharedPreference.getUserUID(ProfileActivity.this), uid, new StringCallback() {
                @Override
                public void done(String string) {
                    setUserProfileDisplay(string);
                }
            });
        }
    }

    private void setUserProfileInfo(int uid){
        if (uid == SaveSharedPreference.getUserUID(this)){
            setUsername(SaveSharedPreference.getUsername(this));
            mToolbar.setTitle(SaveSharedPreference.getUsername(this));
            tvProfileFirstLastName.setText(SaveSharedPreference.getUserFirstName(this) + " " + SaveSharedPreference.getUserLastName(this));

        }
        else {
            new ServerRequestMethods(ProfileActivity.this).getUserData(uid, new UserCallback() {
                @Override
                public void done(User user) {
                    setUsername(user.getUsername());
                    mToolbar.setTitle(user.getUsername());
                    tvProfileFirstLastName.setText(user.getFirstName() + " " + user.getLastName());
                    setPrivacy(user.getUserAccountPrivacy());
                }
            });
        }
    }

    private void setUserProfileDisplay(String profile){
        this.friendShipStatus = profile;
        switch (profile){
            case "logged in user":
                fabStatus.hide();
                break;
            case "Already friends with user.":
                fabStatus.setVisibility(View.INVISIBLE);
                break;
            case "Already sent friend request to user.":
                fabStatus.setImageResource(R.mipmap.ic_dots_horizontal_white_24dp);
                tvFriendStatus.setText(profile);
                tvFriendStatus.setVisibility(View.VISIBLE);
                fabStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogFriendRequestSent();
                    }
                });
                break;
            case "User is awaiting confirmation for friend request.":
                fabStatus.setImageResource(R.mipmap.ic_dots_horizontal_white_24dp);
                tvFriendStatus.setText(profile);
                tvFriendStatus.setVisibility(View.VISIBLE);
                fabStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogFriendRequestReceived();
                    }
                });
                break;
            case "Not friends.":
                fabStatus.setImageResource(R.mipmap.ic_person_add_white_24dp);
                fabStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ServerRequestMethods(ProfileActivity.this).setFriendStatus(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(),
                                new StringCallback() {
                                    public void done(String string) {
                                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }
                });
                break;
            case "User is currently being blocked.":
                /* hide everything, show user doesnt exist like facebook? */
                fabStatus.hide();
                break;
            case "Currently being blocked by user.":
                /* hide everything, show user doesnt exist like facebook? */
                fabStatus.hide();
                break;
            default:
        }
    }

    private void getFriends(int uid){
        new UserRequest(this).getFriends(uid, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    JSONObject jsonObject = new JSONObject(string);
                    String friendsString = jsonObject.getString("friends");
                    JSONArray jsonArray = new JSONArray(friendsString);
                    tvFriendsNum.setText(String.valueOf(jsonArray.length()));
                    if (jsonArray.length() > 0){
                        List<Integer> friendsUid = new ArrayList<>();
                        List<String> friendsFullName = new ArrayList<>();
                        List<String> friendsUsername = new ArrayList<>();
                        List<String> friendsUserImagePath = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject friendsObj = jsonArray.getJSONObject(i);
                            String username = friendsObj.getString("username");
                            String fullname = friendsObj.getString("firstName") + " " + friendsObj.getString("lastName");
                            String uid = friendsObj.getString("uid");

                            String userImagesString = friendsObj.getString("userProfileImages");
                            JSONArray userImagesArray = new JSONArray(userImagesString);
                            String userImagePath;
                            if (userImagesArray.length() > 0){
                                JSONObject userImagePathObj = userImagesArray.getJSONObject(0);
                                userImagePath = userImagePathObj.getString("userImagePath");
                            }
                            else {
                                userImagePath = "empty";
                            }

                            friendsUid.add(Integer.valueOf(uid));
                            friendsFullName.add(fullname);
                            friendsUsername.add(username);
                            friendsUserImagePath.add(userImagePath);
                        }

                        adapter = new FriendsRecyclerAdapter(ProfileActivity.this, "horizontal", friendsFullName, friendsUsername, friendsUid, friendsUserImagePath);
                        layoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        rvFriends.setLayoutManager(layoutManager);
                        rvFriends.setNestedScrollingEnabled(false);
                        rvFriends.setAdapter(adapter);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void getEpisodes(int uid){
        new EventRequest(this).getEventDataByMember(uid, new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("string", string);
                try{
                    JSONObject jsonObject = new JSONObject(string);
                    String episodesString = jsonObject.getString("events");
                    JSONArray episodeArray = new JSONArray(episodesString);
                    tvEpisodesNum.setText(String.valueOf(episodeArray.length()));
                    if (episodeArray.length() > 0){
                        List<Integer> episodeEid = new ArrayList<>();
                        List<String> episodeName = new ArrayList<>();
                        List<String> episodeType = new ArrayList<>();
                        List<String> episodeImagePath = new ArrayList<>();
                        for (int i = 0; i < episodeArray.length(); i++){
                            JSONObject episodeObj = episodeArray.getJSONObject(i);
                            String eid = episodeObj.getString("eid");
                            String name = episodeObj.getString("eventName");
                            String type = episodeObj.getString("eventTypeLabel");

                            String episodeProfileImagesString = episodeObj.getString("eventProfileImages");
                            JSONArray episodeProfileImagesArray = new JSONArray(episodeProfileImagesString);
                            String imagePath;
                            if (episodeProfileImagesArray.length() > 0){
                                HTTPConnection httpConnection = new HTTPConnection();
                                String path = httpConnection.getUploadServerString();
                                JSONObject episodeProfileImagesObj = episodeProfileImagesArray.getJSONObject(0);
                                imagePath = path + episodeProfileImagesObj.getString("euiPath");
                            }
                            else {
                                imagePath = "empty";
                            }
                            episodeEid.add(Integer.valueOf(eid));
                            episodeName.add(name);
                            episodeType.add(type);
                            episodeImagePath.add(imagePath);

                            Log.e("imagePath", imagePath);
                        }
                        adapter = new EpisodeMyRecyclerAdapter(ProfileActivity.this, episodeName, episodeImagePath, episodeEid);
                        layoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        rvEpisodes.setLayoutManager(layoutManager);
                        rvEpisodes.setNestedScrollingEnabled(false);
                        rvEpisodes.setAdapter(adapter);

                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        /* BRYANT, REVISIT THIS */
        /*
        new EventRequest(this).getEventDataByMember(uid, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                if(eventList.size() > 0){
                    tvEpisodesNum.setText(String.valueOf(eventList.size()));

                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeHostUsername = new ArrayList<>();
                    List<Integer> episodeEid = new ArrayList<>();

                    if(eventList.size() <= 3){
                        for (Event event: eventList){
                            episodeTitle.add(event.eventName);
                            episodeHostUsername.add(event.eventHostUsername);
                            episodeEid.add(event.eid);
                        }

                        adapter = new ProfileActivityUserEpisodesRecyclerAdapter(ProfileActivity.this, episodeTitle, episodeHostUsername, episodeEid);
                        layoutManager = new LinearLayoutManager(ProfileActivity.this);
                        rvUserEpisodes.setLayoutManager(layoutManager);
                        rvUserEpisodes.setNestedScrollingEnabled(false);
                        rvUserEpisodes.setAdapter(adapter);
                    }
                    else{
                        ArrayList<Integer> list = new ArrayList<>();
                        for (int i = 0; i < eventList.size() - 1; i++) {
                            list.add(i, i);
                        }
                        Collections.shuffle(list);
                        for (int i = 0; i < 3; i++) {
                            episodeTitle.add(eventList.get(list.get(i)).eventName);
                            episodeHostUsername.add(eventList.get(list.get(i)).eventHostUsername);
                            episodeEid.add(eventList.get(list.get(i)).eid);
                        }
                        adapter = new ProfileActivityUserEpisodesRecyclerAdapter(ProfileActivity.this, episodeTitle, episodeHostUsername, episodeEid);
                        layoutManager = new LinearLayoutManager(ProfileActivity.this);
                        rvUserEpisodes.setLayoutManager(layoutManager);
                        rvUserEpisodes.setNestedScrollingEnabled(false);
                        rvUserEpisodes.setAdapter(adapter);
                    }
                }
                else {
                    tvEpisodesNum.setText("0");
                }
            }
        });
        */
    }

    private void alertDialogFriendRequestReceived(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_dialog_friend_request_received, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new FriendshipStatusRequest(ProfileActivity.this).rejectFriend(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ServerRequestMethods(ProfileActivity.this).setFriendStatus(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void alertDialogFriendRequestSent(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_dialog_friend_request_sent, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new FriendshipStatusRequest(ProfileActivity.this).cancelFriend(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void getProfilePictures(int uid){
            new UserImageRequest(this).getImagesByUid(uid, true, new StringCallback() {
                @Override
                public void done(String string) {
                    try{
                        JSONObject jsonObject = new JSONObject(string);
                        String images = jsonObject.getString("images");
                        JSONArray jsonArray = new JSONArray(images);
                        if (jsonArray.length() > 0){
                            JSONObject imageObj = jsonArray.getJSONObject(0);
                            String userImagePath = imageObj.getString("userImagePath");
                            Picasso.with(ProfileActivity.this).load(userImagePath).fit().centerCrop().into(ivProfilePicture);
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            });
    }

    private void setUID(int uid){
        this.userUID = uid;
    }

    private int getUID(){
        return userUID;
    }


    private void setUsername(String username){
        this.username = username;
    }

    private String getUsername(){
        return username;
    }

    private void setPrivacy(String privacy){
        this.privacy = privacy;
    }

    private String getFriendShipStatus(){
        return friendShipStatus;
    }

    private String getPrivacy(){
        return privacy;
    }
}
