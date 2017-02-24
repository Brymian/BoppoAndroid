package brymian.bubbles.bryant.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Event;
import brymian.bubbles.objects.Image;
import brymian.bubbles.objects.User;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;


public class ProfileActivity extends AppCompatActivity{
    public static ImageView ivProfilePicture;
    FloatingActionButton fabStatus;
    TextView tvProfileFirstLastName, tvFriendsNum, tvFriendStatus, tvEpisodesNum;
    CardView cvUserFriends;
    int userUID;
    String username, privacy;
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
            getEpisodesNum(uid);
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
            case R.id.removeAsFriend:
                new FriendshipStatusRequest(this).unFriend(SaveSharedPreference.getUserUID(this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.Block:
                new FriendshipStatusRequest(this).blockUser(SaveSharedPreference.getUserUID(this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.Report_user:
                Toast.makeText(this, "Report under construction", Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        switch (profile){
            case "logged in user":
                fabStatus.hide();
                break;
            case "Already friends with user.":
                fabStatus.hide();
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

    private String getPrivacy(){
        return privacy;
    }
}
