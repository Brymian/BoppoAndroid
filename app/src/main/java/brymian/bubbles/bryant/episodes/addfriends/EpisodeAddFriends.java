package brymian.bubbles.bryant.episodes.addfriends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.IntegerCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;

public class EpisodeAddFriends extends AppCompatActivity{
    Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView tvTesting;
    ArrayList<Friend> friendList = new ArrayList<>();

    String title, privacy, inviteType;
    boolean imageAllowed;
    double latitude, longitude;
    int eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_add_friends);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.Add_Friends);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*--------------------------------Checking for putExtras()--------------------------------*/

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                title = null;
            }
            else{
                title = extras.getString("episodeTitle");
            }
        }
        else {
            title = savedInstanceState.getString("episodeTitle");
        }

        setEpisodeTitle(title);

        /* code below is if we decide to create the episode AND add the friends at the same time */
        /**
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                title = null;
                privacy = null;
                inviteType = null;
                imageAllowed = false;
                latitude = 0;
                longitude = 0;
            }
            else {
                title = extras.getString("title");
                privacy = extras.getString("privacy");
                inviteType = extras.getString("inviteType");
                imageAllowed = extras.getBoolean("imageAllowed");
                latitude = extras.getDouble("latitude");
                longitude = extras.getDouble("longitude");
            }
        }
        else {
            title = savedInstanceState.getString("title");
            privacy = savedInstanceState.getString("privacy");
            inviteType = savedInstanceState.getString("inviteType");
            imageAllowed = savedInstanceState.getBoolean("imageAllowed");
            latitude = savedInstanceState.getDouble("latitude");
            longitude = savedInstanceState.getDouble("longitude");
        }

         **/
        /*----------------------------------------------------------------------------------------*/


        new EventRequest(this).getEid(SaveSharedPreference.getUserUID(this), getEpisodeTitle(), new IntegerCallback() {
            @Override
            public void done(Integer integer) {
                System.out.println("integer: " + integer);
                /* according to documentation: > 0  - the integer that is the Event Identifier */
                                            /* 0    - the server did not find a matching eid */
                                            /* -1   - server response is something other then the eid */
                                            /* -11  - IOException */
                                            /* -111 - JSON exception */
                if(integer > 0){
                    setEid(integer);
                }
            }
        });


        new ServerRequestMethods(this).getFriends(SaveSharedPreference.getUserUID(this), new UserListCallback() {
            @Override
            public void done(List<User> users) {
                if (users.size() != 0) {
                    for (User user : users) {
                        Friend friend = new Friend(user.getUsername(), user.getFirstName() + " " + user.getLastName(), user.getUid(), false);
                        friendList.add(friend);
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerView_addFriends);
                    adapter = new EpisodeAddFriendsRecyclerAdapter(friendList);
                    layoutManager = new LinearLayoutManager(EpisodeAddFriends.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                }
            }
        });

        tvTesting = (TextView) findViewById(R.id.tvTesting);
        tvTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Friend> singleFriendList = ((EpisodeAddFriendsRecyclerAdapter) adapter).getFriendList();
                for (int i = 0; i < singleFriendList.size(); i++) {
                    Friend singleFriend = singleFriendList.get(i);
                    if (singleFriend.getIsSelected()) {
                        new EventUserRequest(EpisodeAddFriends.this).addUserToEvent(
                                getEid(),
                                SaveSharedPreference.getUserUID(EpisodeAddFriends.this),
                                singleFriend.getUid(),
                                new StringCallback() {
                                    @Override
                                    public void done(String string) {
                                        System.out.println("String from adding friends: " + string);
                                    }
                                });
                    }
                }

                /* code below is if we decide to create the episode AND add the friends at the same time */
                /**
                 new EventRequest(EpisodeAddFriends.this).createEvent(
                 SaveSharedPreference.getUserUID(EpisodeAddFriends.this),
                 getEpisodeTitle(),
                 getEpisodePrivacy(),
                 getInviteType(),
                 getImageAllowed(),
                 null,
                 null,
                 getLatitude(),
                 getLongitude(),
                 new StringCallback() {
                @Override public void done(String string) {
                System.out.println("String call back: " + string);
                }
                });

                 **/
            }
        });
    }

    private void setEid(int eid){
        this.eid = eid;
    }

    private int getEid(){
        return eid;
    }

    private void setEpisodeTitle(String title){
        this.title = title;
    }

    private String getEpisodeTitle(){
        return title;
    }

    private void setEpisodePrivacy(String privacy){
        this.privacy = privacy;
    }

    private String getEpisodePrivacy(){
        return privacy;
    }

    private void setInviteType(String inviteType){
        this.inviteType = inviteType;
    }

    private String getInviteType(){
        return inviteType;
    }

    private void setImageAllowed(boolean imageAllowed){
        this.imageAllowed = imageAllowed;
    }

    private boolean getImageAllowed(){
        return imageAllowed;
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
