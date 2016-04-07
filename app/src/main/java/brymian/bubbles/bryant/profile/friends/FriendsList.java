package brymian.bubbles.bryant.profile.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.ProfileActivity;
import brymian.bubbles.bryant.profile.friends.friendrequests.FriendRequestRecyclerAdapter;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;


public class FriendsList extends AppCompatActivity{

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar mToolbar;
    View vDivider;
    TextView tvPendingFriendRequestsNumber, tvPendingRequests;
    public static List<Integer> friendsUID = new ArrayList<Integer>();
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

        vDivider = findViewById(R.id.vDivider);
        vDivider.setVisibility(View.GONE);

        System.out.println("profile: " + profile);
        if (profile != null) {
            if(profile.equals("logged in user")){
                mToolbar.setTitle(R.string.Friends);

                /* Check for any friend requests for logged in user */
                new ServerRequestMethods(this).getUserFriendRequestUsers(SaveSharedPreference.getUserUID(this), new UserListCallback() {
                    @Override
                    public void done(List<User> users) {

                        if(users.size() != 0){
                            System.out.println("friend request size: " + users.size());
                            ArrayList<String> friendRequestUsername = new ArrayList<String>();
                            ArrayList<String> friendRequestFirstLastName = new ArrayList<String>();

                            for(int i = 0; i < users.size(); i++){
                                friendRequestUsername.add(i, users.get(i).getUsername());
                                friendRequestFirstLastName.add(i, users.get(i).getFirstName() + " " + users.get(i).getLastName());
                            }

                            vDivider.setVisibility(View.VISIBLE);
                            recyclerView = (RecyclerView) findViewById(R.id.recyclerView_friendRequest);
                            adapter = new FriendRequestRecyclerAdapter(friendRequestFirstLastName, friendRequestUsername);
                            layoutManager = new LinearLayoutManager(FriendsList.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });

                /* Check for any pending friend requests that were sent from logged in user */
                new ServerRequestMethods(this).getUserSentFriendRequestUsers(SaveSharedPreference.getUserUID(this), new UserListCallback() {
                    @Override
                    public void done(List<User> users) {
                        if (users.size() != 0){
                            tvPendingRequests.setVisibility(View.VISIBLE);
                            tvPendingFriendRequestsNumber.setVisibility(View.VISIBLE);
                            tvPendingFriendRequestsNumber.setText(" " + users.size());

                            System.out.println("name + username + uid: " + users.get(0).getFirstName() + " " + users.get(0).getLastName() + " " + users.get(0).getUsername() + " " + users.get(0).getUid());
                        }
                    }
                });
            }
            else{
                mToolbar.setTitle(profile + "'s Friends");
            }
        }




        new ServerRequestMethods(this).getFriends(uid, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                Toast.makeText(FriendsList.this, "size: " + users.size(), Toast.LENGTH_SHORT).show();
                List<String> friendsFirstLastName = new ArrayList<String>();
                List<String> friendsUsername = new ArrayList<String>();


                for (int i = 0; i < users.size(); i++) {
                    System.out.println("username: " + users.get(i).getUsername() +
                            "\t firstLastName: " + users.get(i).getFirstName() + " " + users.get(i).getLastName() +
                            "\t uid: " + users.get(i).getUid());
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

                recyclerView = (RecyclerView) findViewById(R.id.recyclerView_friends);
                adapter = new FriendsListRecyclerAdapter(friendsFirstLastName, friendsUsername);
                layoutManager = new LinearLayoutManager(FriendsList.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new FriendsListRecyclerItemClickListener(FriendsList.this, new FriendsListRecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                startActivity(new Intent(FriendsList.this, ProfileActivity.class).putExtra("uid", friendsUID.get(position)).putExtra("profile", friendsStatus.get(position)));
                            }
                        })
                );
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
