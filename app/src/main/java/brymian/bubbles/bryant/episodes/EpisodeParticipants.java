package brymian.bubbles.bryant.episodes;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventUserRequest;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;

public class EpisodeParticipants extends AppCompatActivity {
    Toolbar mToolbar;
    FloatingActionButton fabMenu;
    RecyclerView rvParticipants;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_participants);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Participants);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*--------------------------------Checking for putExtras()--------------------------------*/
        int eid;
        boolean isHost;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eid = 0;
                isHost = false;
            }
            else {
                eid = extras.getInt("eid");
                isHost = extras.getBoolean("isHost");
            }
        }
        else {
            eid = savedInstanceState.getInt("eid");
            isHost = savedInstanceState.getBoolean("isHost");
        }
        /*----------------------------------------------------------------------------------------*/

        Log.e("isHost", ""+ isHost);
        getParticipants(eid);
        if (isHost) {
            setFab();
        }
    }

    private void setFab(){
        fabMenu = (FloatingActionButton) findViewById(R.id.fabMenu);
        fabMenu.setVisibility(View.VISIBLE);
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                startFragment(fm, R.id.episode_participants, new EpisodeParticipantsFab());
            }
        });
    }

    private void getParticipants(int eid){
        rvParticipants = (RecyclerView) findViewById(R.id.rvParticipants);
        new EventUserRequest(this).getEventUsersData("Joined", eid, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    if (string.length() > 0){
                        List<String> participantFirstLastName = new ArrayList<>();
                        List<String> participantUsername = new ArrayList<>();
                        List<Integer> participantsUid = new ArrayList<>();

                        JSONArray jArray = new JSONArray(string);
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject jArray_jObject = jArray.getJSONObject(i);
                            JSONObject jEvent = jArray_jObject.getJSONObject("user");
                            participantFirstLastName.add(jEvent.getString("firstName") + " " + jEvent.getString("lastName"));
                            participantUsername.add(jEvent.getString("username"));
                            participantsUid.add(jEvent.getInt("uid"));
                        }

                        adapter = new EpisodeParticipantsRecyclerAdapter(EpisodeParticipants.this, participantFirstLastName, participantUsername, participantsUid);
                        layoutManager = new LinearLayoutManager(EpisodeParticipants.this);
                        rvParticipants.setLayoutManager(layoutManager);
                        rvParticipants.setAdapter(adapter);
                    }
                }
                catch (JSONException | NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }

}
