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
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.friends.FriendsActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Image;
import brymian.bubbles.objects.User;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView ivProfilePicture;
    FloatingActionButton fabStatus;
    TextView tvProfileFirstLastName, tvFriendsNum, tvFriendStatus, tvEpisodesNum;
    CardView cvUserFriends;
    int userUID;
    String username, privacy, friendShipStatus;
    Toolbar mToolbar;

    RecyclerView rvUserEpisodes;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

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
        cvUserFriends = (CardView) findViewById(R.id.cvUserFriends);
        cvUserFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, FriendsActivity.class).putExtra("uid", SaveSharedPreference.getUserUID(ProfileActivity.this)).putExtra("profile", getUsername()));
            }
        });
        tvFriendsNum = (TextView) findViewById(R.id.tvFriendsNum);
        tvEpisodesNum = (TextView) findViewById(R.id.tvEpisodesNum);
        rvUserEpisodes = (RecyclerView) findViewById(R.id.rvUserEpisodes);

        if(uid != 0){
            setFriendshipStatus(uid);
            getProfilePictures(uid);
            getFriendsNum(uid);
            //getEpisodesNum(uid);
        }
    }

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
                popUpMenu();
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
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void setFriendshipStatus(int uid){
        if (uid == SaveSharedPreference.getUserUID(this)){
            setUserProfileInfo("logged in user");
            setUID(SaveSharedPreference.getUserUID(this));
            setUsername(SaveSharedPreference.getUsername(this));
            mToolbar.setTitle(SaveSharedPreference.getUsername(this));
            tvProfileFirstLastName.setText(SaveSharedPreference.getUserFirstName(this) + " " + SaveSharedPreference.getUserLastName(this));
        }
        else{
            final int innerUid = uid;
            new ServerRequestMethods(this).getFriendStatus(SaveSharedPreference.getUserUID(ProfileActivity.this), uid, new StringCallback() {
                @Override
                public void done(String string) {
                    setUserProfileInfo(string);
                    new ServerRequestMethods(ProfileActivity.this).getUserData(innerUid, new UserCallback() {
                        @Override
                        public void done(User user) {
                            setUsername(user.getUsername());
                            mToolbar.setTitle(user.getUsername());
                            tvProfileFirstLastName.setText(user.getFirstName() + " " + user.getLastName());
                            setPrivacy(user.getUserAccountPrivacy());
                        }
                    });
                }
            });
        }
    }

    private void setUserProfileInfo(String profile){
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

    private void getFriendsNum(int uid){
        new ServerRequestMethods(this).getFriends(uid, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                tvFriendsNum.setText(String.valueOf(users.size()));
            }
        });
    }

    private void getEpisodesNum(int uid){
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
        new UserImageRequest(this).getImagesByUidAndPurpose(uid, "Profile", null, new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                if (imageList.size() > 0) {
                    Picasso.with(ProfileActivity.this).load(imageList.get(0).userImagePath).into(ivProfilePicture);
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
