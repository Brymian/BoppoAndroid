package brymian.bubbles.bryant.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.ProfileActivity;
import brymian.bubbles.bryant.friends.friendrequests.FriendRequestRecyclerAdapter;
import brymian.bubbles.bryant.friends.friendrequests.FriendRequester;
import brymian.bubbles.bryant.friends.sent.SentFriendRequests;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;


public class FriendsList extends AppCompatActivity{

    RecyclerView recyclerViewFriendRequests, recyclerViewFriends;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar mToolbar;
    View vDivider;
    TextView tvPendingFriendRequestsNumber, tvPendingRequests;
    public static List<Integer> friendsUID = new ArrayList<>();
    public static List<String> friendsStatus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);

        /*--------------------------------Checking for putExtras()--------------------------------*/
        int uid;
        String profile;
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
        tvPendingFriendRequestsNumber = (TextView) findViewById(R.id.tvPendingFriendRequestsNumber);
        tvPendingFriendRequestsNumber.setVisibility(View.GONE);
        tvPendingRequests = (TextView) findViewById(R.id.tvPendingRequests);
        tvPendingRequests.setVisibility(View.GONE);

        recyclerViewFriendRequests = (RecyclerView) findViewById(R.id.recyclerView_friendRequest);
        recyclerViewFriendRequests.setVisibility(View.GONE);

        recyclerViewFriends = (RecyclerView) findViewById(R.id.recyclerView_friends);


        vDivider = findViewById(R.id.vDivider);
        vDivider.setVisibility(View.GONE);

        if (profile != null) {
            if(profile.equals("logged in user")){
                mToolbar.setTitle(R.string.Friends);
                checkForSentFriendRequests();
                checkForReceivedFriendRequests();
            }
            else{
                mToolbar.setTitle(profile + "'s Friends");
            }
        }
        getFriends(uid);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /* Checks for any pending friend requests that were sent from logged in user */
    private void checkForSentFriendRequests(){
            new FriendshipStatusRequest(this).getFriendshipStatusRequestSentUsers(SaveSharedPreference.getUserUID(this), "Friendship Pending", new UserListCallback() {
                @Override
                public void done(List<User> users) {
                    try{
                        if (users.size() != 0) {
                            tvPendingRequests.setVisibility(View.VISIBLE);
                            tvPendingFriendRequestsNumber.setVisibility(View.VISIBLE);
                            tvPendingFriendRequestsNumber.setText(" " + users.size());
                            tvPendingRequests.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(FriendsList.this, SentFriendRequests.class));
                                }
                            });
                            tvPendingFriendRequestsNumber.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(FriendsList.this, SentFriendRequests.class));
                                }
                            });
                        }
                    }
                    catch (NullPointerException npe){
                        System.out.println("getFriendshipStatusRequestSentUsers() null pointer exception");
                        npe.printStackTrace();
                    }
                }
            });

    }

    /* Checks and displays for any friend requests for logged in user */
    private void checkForReceivedFriendRequests(){
        new FriendshipStatusRequest(this).getFriendshipStatusRequestReceivedUsers(SaveSharedPreference.getUserUID(this), "Friendship Pending", new UserListCallback() {
            @Override
            public void done(List<User> users) {
                try {
                    if (users.size() != 0) {
                        ArrayList<FriendRequester> friendRequesterArrayList = new ArrayList<>();

                        for (User user : users) {
                            FriendRequester friendRequester = new FriendRequester(user.getUsername(), user.getFirstName() + " " + user.getLastName(), user.getUid());
                            friendRequesterArrayList.add(friendRequester);
                        }
                        vDivider.setVisibility(View.VISIBLE);
                        recyclerViewFriendRequests = (RecyclerView) findViewById(R.id.recyclerView_friendRequest);
                        recyclerViewFriendRequests.setVisibility(View.VISIBLE);
                        adapter = new FriendRequestRecyclerAdapter(FriendsList.this, friendRequesterArrayList);
                        layoutManager = new LinearLayoutManager(FriendsList.this);
                        recyclerViewFriendRequests.setLayoutManager(layoutManager);
                        recyclerViewFriendRequests.setAdapter(adapter);
                    }
                }
                catch (NullPointerException npe){
                    System.out.println("getFriendshipStatusRequestReceivedUsers()");
                    npe.printStackTrace();
                }
            }
        });
    }


    /* Checks and displays if use has any friends */
    private void getFriends(int uid){
        new ServerRequestMethods(this).getFriends(uid, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                List<String> friendsFirstLastName = new ArrayList<>();
                List<String> friendsUsername = new ArrayList<>();
                try {
                    System.out.println("users.size(): "+users.size());
                    for (int i = 0; i < users.size(); i++) {
                        friendsFirstLastName.add(i, users.get(i).getFirstName() + " " + users.get(i).getLastName());
                        friendsUsername.add(i, users.get(i).getUsername());
                        friendsUID.add(i, users.get(i).getUid());
                        final int j = i;
                        new ServerRequestMethods(FriendsList.this).getFriendStatus(SaveSharedPreference.getUserUID(FriendsList.this), users.get(i).getUid(), new StringCallback() {
                            @Override
                            public void done(String string) {
                                friendsStatus.add(j, string);
                            }
                        });
                    }
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                    System.out.println("getFriends null pointer exception");
                }
                recyclerViewFriends.setVisibility(View.VISIBLE);
                //recyclerView = (RecyclerView) findViewById(R.id.recyclerView_friends);
                adapter = new FriendsListRecyclerAdapter(friendsFirstLastName, friendsUsername);
                layoutManager = new LinearLayoutManager(FriendsList.this);
                recyclerViewFriends.setLayoutManager(layoutManager);
                recyclerViewFriends.setAdapter(adapter);
                recyclerViewFriends.addOnItemTouchListener(new FriendsListRecyclerItemClickListener(FriendsList.this, new FriendsListRecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                startActivity(new Intent(FriendsList.this, ProfileActivity.class).putExtra("uid", friendsUID.get(position)).putExtra("profile", friendsStatus.get(position)));
                            }
                        })
                );
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
