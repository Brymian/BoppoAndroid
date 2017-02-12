package brymian.bubbles.bryant.friends;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
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

    public static List<Integer> friendsUID = new ArrayList<>();
    public static List<String> friendsStatus = new ArrayList<>();
    /* Checks and displays if use has any friends */
    private void getFriends(int uid){
        new ServerRequestMethods(this).getFriends(uid, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                try {
                    setFriendListSize(users.size());
                    List<String> friendsFirstLastName = new ArrayList<>();
                    List<String> friendsUsername = new ArrayList<>();
                    for (int i = 0; i < users.size(); i++) {
                        friendsFirstLastName.add(i, users.get(i).getFirstName() + " " + users.get(i).getLastName());
                        friendsUsername.add(i, users.get(i).getUsername());
                        friendsUID.add(i, users.get(i).getUid());
                        final int j = i;
                        new ServerRequestMethods(FriendsActivity.this).getFriendStatus(SaveSharedPreference.getUserUID(FriendsActivity.this), users.get(i).getUid(), new StringCallback() {
                            @Override
                            public void done(String string) {
                                friendsStatus.add(j, string);
                            }
                        });
                    }
                    adapter = new FriendsRecyclerAdapter(FriendsActivity.this, friendsFirstLastName, friendsUsername, friendsUID, friendsStatus);
                    layoutManager = new LinearLayoutManager(FriendsActivity.this);
                    recyclerViewFriends.setLayoutManager(layoutManager);
                    recyclerViewFriends.setNestedScrollingEnabled(false);
                    recyclerViewFriends.setAdapter(adapter);
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
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

    /**
    //@Override
    public void onBackPressed() {
        if (profile.equals("logged in user")){
            super.onBackPressed();
        }else if(getFriendListSize() !=0) {
            for(int i = 0; i < getFriendListSize(); i++){
                friendsStatus.remove(i);
                friendsUID.remove(i);
            }
            super.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }
     **/

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            if (profile.equals("logged in user")){
                super.onBackPressed();
            }else if(getFriendListSize() !=0) {
                for(int i = 0; i < getFriendListSize(); i++){
                    friendsStatus.remove(i);
                    friendsUID.remove(i);
                }
                super.onBackPressed();
            }else{
                super.onBackPressed();
            }        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.friends_activity_menu_inflater, menu);
        return true;
    }

    int size;
    private void setFriendListSize(int size){
        this.size = size;
    }

    private int getFriendListSize(){
        return size;
    }
}
