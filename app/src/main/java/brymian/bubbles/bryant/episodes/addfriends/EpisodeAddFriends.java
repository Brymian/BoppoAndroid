package brymian.bubbles.bryant.episodes.addfriends;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserRequest;

public class EpisodeAddFriends extends AppCompatActivity{
    Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fabDone;
    TextView tvAllFriends;


    String title, privacy, inviteType;
    boolean imageAllowed;
    double latitude, longitude;
    int eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_add_friends);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Add_Friends_to_Episode);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.rvAddFriends);

        tvAllFriends = (TextView) findViewById(R.id.tvAllFriends);

        Intent intent = getIntent();
        int eid = intent.getIntExtra("eid", 0);
        setEid(eid);
        getParticipants(eid);
        setFAB();
    }

    List<Integer> participantsUid = new ArrayList<>();
    private void getParticipants(int eid){
        new EventUserRequest(this).getEventUsersData("Joined", eid, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    if (string.length() > 0){
                        JSONArray jArray = new JSONArray(string);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jArray_jObject = jArray.getJSONObject(i);
                            JSONObject jEvent = jArray_jObject.getJSONObject("user");
                            participantsUid.add(jEvent.getInt("uid"));
                        }
                    }
                }
                catch (JSONException | NullPointerException e){
                    e.printStackTrace();
                }
                getFriends();
            }
        });
    }

    List<Integer> friendsUid = new ArrayList<>();
    List<String> friendsFullName = new ArrayList<>();
    List<String> friendsUsername = new ArrayList<>();
    List<String> friendsImagePath = new ArrayList<>();
    private void getFriends(){
        new UserRequest(this).getFriends(SaveSharedPreference.getUserUID(this), new StringCallback() {
            @Override
            public void done(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String friends = jsonObject.getString("friends");
                    JSONArray jsonArray = new JSONArray(friends);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject friendObject = jsonArray.getJSONObject(i);
                        String friendsProfileImage = friendObject.getString("userProfileImages");
                        JSONArray friendsProfileImageArray = new JSONArray(friendsProfileImage);
                        String userImagePath;
                        if (friendsProfileImageArray.length() > 0){
                            JSONObject friendsProfileImageObject = friendsProfileImageArray.getJSONObject(0);
                            userImagePath = friendsProfileImageObject.getString("userImagePath");
                        }
                        else {
                            userImagePath = "empty";
                        }

                        String uid = friendObject.getString("uid");
                        String fullName = friendObject.getString("firstName") + " " + friendObject.getString("lastName");
                        String username = friendObject.getString("username");

                        friendsUid.add(Integer.valueOf(uid));
                        friendsFullName.add(fullName);
                        friendsUsername.add(username);
                        friendsImagePath.add(userImagePath);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                setNonParticipatingFriends();
            }
        });
    }

    private void setNonParticipatingFriends(){
        for (int i = 0; i < friendsUid.size(); i++){
            for (int j = 0; j < participantsUid.size(); j++){
                if (friendsUid.get(i).equals(participantsUid.get(j))){
                    friendsUid.remove(i);
                    friendsUsername.remove(i);
                    friendsFullName.remove(i);
                    friendsImagePath.remove(i);
                }
            }
        }
        if (friendsUid.size() > 0){
            ArrayList<Friend> friendList = new ArrayList<>();
            for (int i = 0; i < friendsUid.size(); i++){
                Friend friend = new Friend(friendsUsername.get(i), friendsFullName.get(i), friendsUid.get(i), friendsImagePath.get(i), false);
                friendList.add(friend);
            }
            adapter = new EpisodeAddFriendsRecyclerAdapter(EpisodeAddFriends.this, friendList);
            layoutManager = new LinearLayoutManager(EpisodeAddFriends.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            tvAllFriends.setVisibility(View.VISIBLE);
            fabDone.hide();
        }

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
                                        Log.e("add", string);
                                    }
                                });
                    }
                }
                //inviteSentAD();
            }
        });
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
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
