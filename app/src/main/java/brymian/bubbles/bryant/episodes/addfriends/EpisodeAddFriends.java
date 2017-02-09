package brymian.bubbles.bryant.episodes.addfriends;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

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
    FloatingActionButton fabDone;

    String title, privacy, inviteType;
    boolean imageAllowed;
    double latitude, longitude;
    int eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_add_friends);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle(R.string.Add_Friends_to_Episode);
        setSupportActionBar(mToolbar);

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

        getFriends();
        setFAB();
    }

    private void getFriends(){
        new ServerRequestMethods(this).getFriends(SaveSharedPreference.getUserUID(this), new UserListCallback() {
            @Override
            public void done(List<User> users) {
                if (users.size() != 0) {
                    ArrayList<Friend> friendList = new ArrayList<>();
                    for (User user : users) {
                        Friend friend = new Friend(user.getUsername(), user.getFirstName() + " " + user.getLastName(), user.getUid(), false);
                        friendList.add(friend);
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.rvAddFriends);
                    adapter = new EpisodeAddFriendsRecyclerAdapter(friendList);
                    layoutManager = new LinearLayoutManager(EpisodeAddFriends.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    private void setFAB(){
        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Friend> singleFriendList = ((EpisodeAddFriendsRecyclerAdapter) adapter).getFriendList();
                for (int i = 0; i < singleFriendList.size(); i++) {
                    Friend singleFriend = singleFriendList.get(i);
                    if (singleFriend.getIsSelected()) {
                        new EventUserRequest(EpisodeAddFriends.this).addUserToEvent(getEid(), SaveSharedPreference.getUserUID(EpisodeAddFriends.this), singleFriend.getUid(),
                                new StringCallback() {
                                    @Override
                                    public void done(String string) {
                                    }
                                });
                    }
                }
                inviteSentAD();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBackToEpisodeMy();
    }

    private void goBackToEpisodeMy(){
        setResultOkSoSecondActivityWontBeShown();
        finish();
    }

    private void setResultOkSoSecondActivityWontBeShown() {
        Intent intent = new Intent();
        if (getParent() == null) {
            setResult(Activity.RESULT_OK, intent);
        } else {
            getParent().setResult(Activity.RESULT_OK, intent);
        }
    }

    private void inviteSentAD() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_add_friends_alertdialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goBackToEpisodeMy();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
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
