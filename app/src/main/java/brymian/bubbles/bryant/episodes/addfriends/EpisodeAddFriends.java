package brymian.bubbles.bryant.episodes.addfriends;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class EpisodeAddFriends extends Fragment{
    Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fabDone;
    TextView tvAllFriends;

    private int eid;
    private String from;

    List<Integer> participantsUid = new ArrayList<>();
    List<Integer> friendsUid = new ArrayList<>();
    List<String> friendsFullName = new ArrayList<>();
    List<String> friendsUsername = new ArrayList<>();
    List<String> friendsImagePath = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.episode_add_friends, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Add_Friends_to_Episode);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvAddFriends);

        tvAllFriends = (TextView) view.findViewById(R.id.tvAllFriends);

        fabDone = (FloatingActionButton) view.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Friend> singleFriendList = ((EpisodeAddFriendsRecyclerAdapter) adapter).getFriendList();
                for (int i = 0; i < singleFriendList.size(); i++) {
                    Friend singleFriend = singleFriendList.get(i);
                    if (singleFriend.getIsSelected()) {
                        new EventUserRequest().addUserToEvent(eid, SaveSharedPreference.getUserUID(getActivity()), singleFriend.getUid(),
                                new StringCallback() {
                                    @Override
                                    public void done(String string) {
                                        Log.e("add", string);
                                        if (string.equals("The user has been successfully invited to the event.")){
                                            Toast.makeText(getActivity(), "User(s) added to episode", Toast.LENGTH_SHORT).show();
                                            getActivity().onBackPressed();
                                        }
                                    }
                                });
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            eid = bundle.getInt("eid", 0);
            from = bundle.getString("from", null);
            if (from.equals("participants")){
                mToolbar.setPadding(0, getStatusBarHeight(),0, 0);
            }
            getParticipants(eid);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (from.equals("participants")){
            MenuItem item = menu.findItem(R.id.popUpMenu);
            item.setVisible(false);
        }
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
        new EventUserRequest().getEventUsersData("Joined", eid, new StringCallback() {
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

    private void getFriends(){
        new UserRequest(getActivity()).getFriends(SaveSharedPreference.getUserUID(getActivity()), new StringCallback() {
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
            adapter = new EpisodeAddFriendsRecyclerAdapter(getActivity(), friendList);
            layoutManager = new LinearLayoutManager(getActivity());
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
}
