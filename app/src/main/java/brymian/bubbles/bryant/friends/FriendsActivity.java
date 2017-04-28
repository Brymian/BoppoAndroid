package brymian.bubbles.bryant.friends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserRequest;
import brymian.bubbles.objects.User;

public class FriendsActivity extends AppCompatActivity{
    LinearLayout llFriendRequests;
    RecyclerView recyclerViewFriends, rvFriendRequests;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar mToolbar;
    String profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_activity);

        /*--------------------------------Checking for putExtras()--------------------------------*/
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                profile = null;
                uid = 0;
            }
            else {
                uid = extras.getInt("uid");
                profile = extras.getString("profile");
            }
        }
        else {
            uid = savedInstanceState.getInt("uid");
            profile = savedInstanceState.getString("profile");
        }
        /*----------------------------------------------------------------------------------------*/

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        llFriendRequests = (LinearLayout) findViewById(R.id.llFriendRequests);
        llFriendRequests.setVisibility(View.GONE);
        recyclerViewFriends = (RecyclerView) findViewById(R.id.recyclerView_friends);
        rvFriendRequests = (RecyclerView) findViewById(R.id.rvFriendRequests);

        if (profile != null) {
            if(profile.equals("logged in user")){
                mToolbar.setTitle(R.string.Friends);
                checkForReceivedFriendRequests();
            }
            else{
                mToolbar.setTitle(profile + "'s Friends");
            }
        }
        //checkForReceivedFriendRequests();
        getFriends(uid);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /* Checks and displays for any friend requests for logged in user */
    private void checkForReceivedFriendRequests(){
        new FriendshipStatusRequest(this).getFriendshipStatusRequestReceivedUsers(SaveSharedPreference.getUserUID(this), "Friendship Pending", new UserListCallback() {
            //@Override
            public void done(List<User> users) {
                try {
                    if (users.size() > 0) {
                        llFriendRequests.setVisibility(View.VISIBLE);
                        ArrayList<FriendRequester> friendRequesterArrayList = new ArrayList<>();

                        for (User user : users) {
                            FriendRequester friendRequester = new FriendRequester(user.getUsername(), user.getFirstName() + " " + user.getLastName(), user.getUid());
                            friendRequesterArrayList.add(friendRequester);
                        }
                        adapter = new FriendRequestReceivedRecyclerAdapter(FriendsActivity.this, friendRequesterArrayList);
                        layoutManager = new LinearLayoutManager(FriendsActivity.this);
                        rvFriendRequests.setLayoutManager(layoutManager);
                        rvFriendRequests.setNestedScrollingEnabled(false);
                        rvFriendRequests.setAdapter(adapter);
                    }
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        });
    }

    /* Checks and displays if use has any friends */
    private void getFriends(int uid){
        new UserRequest(this).getFriends(uid, new StringCallback() {
            @Override
            public void done(String string) {
                List<String> friendsFirstLastName = new ArrayList<>();
                List<String> friendsUsername = new ArrayList<>();
                List<Integer> friendsUid = new ArrayList<>();
                List<String> friendsUserImagePath = new ArrayList<>();
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

                        friendsFirstLastName.add(fullName);
                        friendsUsername.add(username);
                        friendsUid.add(Integer.valueOf(uid));
                        friendsUserImagePath.add(userImagePath);

                        adapter = new FriendsRecyclerAdapter(FriendsActivity.this, "vertical", friendsFirstLastName, friendsUsername, friendsUid, friendsUserImagePath);
                        layoutManager = new LinearLayoutManager(FriendsActivity.this);
                        recyclerViewFriends.setLayoutManager(layoutManager);
                        recyclerViewFriends.setNestedScrollingEnabled(false);
                        recyclerViewFriends.setAdapter(adapter);

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.search:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.friends_activity_menu_inflater, menu);
        return true;
    }
}
