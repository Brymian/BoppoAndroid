package brymian.bubbles.bryant.episodes;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.addfriends.EpisodeAddFriends;
import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserRequest;


public class EpisodeParticipants extends AppCompatActivity {
    Toolbar mToolbar;
    FloatingActionButton fabMenu;
    RecyclerView rvParticipants;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private int eid;
    private boolean showRemove = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_participants);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Participants);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*--------------------------------Checking for putExtras()--------------------------------*/
        int eid;
        boolean isHost, isEnded;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eid = 0;
                isHost = false;
                isEnded = false;
            }
            else {
                eid = extras.getInt("eid");
                isHost = extras.getBoolean("isHost");
                isEnded = extras.getBoolean("isEnded");
            }
        }
        else {
            eid = savedInstanceState.getInt("eid");
            isHost = savedInstanceState.getBoolean("isHost");
            isEnded = savedInstanceState.getBoolean("isEnded");
        }
        /*----------------------------------------------------------------------------------------*/

        setEid(eid);
        getParticipants(eid);
        if (isHost && !isEnded) {
            setFab();
            setShowRemove(true);
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

    private void setFab(){
        fabMenu = (FloatingActionButton) findViewById(R.id.fabMenu);
        fabMenu.setVisibility(View.VISIBLE);
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EpisodeParticipants.this, EpisodeAddFriends.class).putExtra("eid", getEid()));
            }
        });
    }

    private void getParticipants(int eid){
        Log.e("eid", eid + "");
        /*
        new EventUserRequest(this).getEventUsersData("Joined", eid, new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("participants", string);
                try{
                    if (string.length() > 0){
                        List<String> participantFirstLastName = new ArrayList<>();
                        List<String> participantUsername = new ArrayList<>();
                        List<Integer> participantsUid = new ArrayList<>();
                        List<String> participantsProfileImage = new ArrayList<>();
                        List<String> participantType = new ArrayList<>();

                        HTTPConnection httpConnection = new HTTPConnection();
                        JSONArray jArray = new JSONArray(string);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jArray_jObject = jArray.getJSONObject(i);
                            JSONObject jUser = jArray_jObject.getJSONObject("user");
                            String eventUserDataString = jUser.getString("eventUserData");
                            JSONObject eventUserDataObject = new JSONObject(eventUserDataString);
                            String userProfileImageString = jUser.getString("userProfileImages");
                            JSONArray userProfileImagesArray = new JSONArray(userProfileImageString);

                            String userImagePath;
                            if (userProfileImagesArray.length() > 0){
                                JSONObject userProfileImageObject = userProfileImagesArray.getJSONObject(0);
                                userImagePath = httpConnection.getUploadServerString() + userProfileImageObject.getString("userImagePath");
                            }
                            else {
                                userImagePath = "empty";
                            }

                            Log.e("string", eventUserDataObject.getString("eventUserTypeLabel"));
                            participantType.add(eventUserDataObject.getString("eventUserTypeLabel"));
                            participantsProfileImage.add(userImagePath);
                            participantFirstLastName.add(jUser.getString("firstName") + " " + jUser.getString("lastName"));
                            participantUsername.add(jUser.getString("username"));
                            participantsUid.add(jUser.getInt("uid"));
                        }

                        rvParticipants = (RecyclerView) findViewById(R.id.rvParticipants);
                        adapter = new EpisodeParticipantsRecyclerAdapter(EpisodeParticipants.this, getEid(), participantFirstLastName, participantUsername, participantsUid, participantsProfileImage, participantType, getShowRemove());
                        layoutManager = new LinearLayoutManager(EpisodeParticipants.this);
                        rvParticipants.setLayoutManager(layoutManager);
                        rvParticipants.setNestedScrollingEnabled(false);
                        rvParticipants.setAdapter(adapter);
                    }
                }
                catch (JSONException | NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
        */
    }

    private void setEid(int eid){
        this.eid = eid;
    }

    public int getEid(){
        return this.eid;
    }

    private void setShowRemove(boolean showRemove){
        this.showRemove = showRemove;
    }

    private boolean getShowRemove(){
        return this.showRemove;
    }
}
