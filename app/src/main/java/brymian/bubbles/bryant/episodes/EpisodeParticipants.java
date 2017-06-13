package brymian.bubbles.bryant.episodes;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class EpisodeParticipants extends Fragment {
    Toolbar mToolbar;
    FloatingActionButton fabMenu;
    RecyclerView rvParticipants;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private int eid;
    private boolean showRemove;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.episode_participants, container, false);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Participants);
        mToolbar.setPadding(0, getStatusBarHeight(),0, 0);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabMenu = (FloatingActionButton) view.findViewById(R.id.fabMenu);
        rvParticipants = (RecyclerView) view.findViewById(R.id.rvParticipants);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            eid = bundle.getInt("eid", 0);
            boolean isHost = bundle.getBoolean("isHost");
            boolean isEnded = bundle.getBoolean("isEnded");

            setEid(eid);
            getParticipants(eid);
            if (isHost && !isEnded) {
                setFab();
                setShowRemove(true);
            }
        }
    }

    private void setFab(){
        fabMenu.setVisibility(View.VISIBLE);
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EpisodeAddFriends episodeAddFriends = new EpisodeAddFriends();
                Bundle bundle = new Bundle();
                bundle.putInt("eid", eid);
                bundle.putString("from", "participants");
                episodeAddFriends.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.episode_activity, episodeAddFriends);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void getParticipants(int eid){
        Log.e("eid", eid + "");
        new EventUserRequest().getEventUsersData("Joined", eid, new StringCallback() {
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
                                userImagePath = httpConnection.getUploadServerString() + userProfileImageObject.getString("userImageThumbnailPath");
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

                        adapter = new EpisodeParticipantsRecyclerAdapter(getActivity(), getEid(), participantFirstLastName, participantUsername, participantsUid, participantsProfileImage, participantType, getShowRemove());
                        layoutManager = new LinearLayoutManager(getActivity());
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
