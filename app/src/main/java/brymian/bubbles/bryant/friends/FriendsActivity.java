package brymian.bubbles.bryant.friends;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;

public class FriendsActivity extends AppCompatActivity{

    RecyclerView recyclerViewFriends;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar mToolbar;
    String profile;
    FloatingActionButton fabAddFriend, fabFriendRequestReceived, fabFriendRequestSent;
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

        recyclerViewFriends = (RecyclerView) findViewById(R.id.recyclerView_friends);


        if (profile != null) {
            if(profile.equals("logged in user")){
                mToolbar.setTitle(R.string.Friends);
                //checkForReceivedFriendRequests();
            }
            else{
                mToolbar.setTitle(profile + "'s Friends");
            }
        }
        getFriends(uid);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setFAB();
    }

    private void setFAB(){
        fabAddFriend = (FloatingActionButton) findViewById(R.id.fabAddFriend);

        fabFriendRequestReceived = (FloatingActionButton) findViewById(R.id.fabFriendRequestReceived);
        fabFriendRequestReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                startFragment(fm, R.id.friends_activity, new FriendRequestReceived());
            }
        });

        fabFriendRequestSent = (FloatingActionButton) findViewById(R.id.fabFriendRequestSent);
        fabFriendRequestSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                startFragment(fm, R.id.friends_activity, new FriendRequestSent());
            }
        });
    }


    /* Checks and displays for any friend requests for logged in user */
    /**
    private void checkForReceivedFriendRequests(){
        new FriendshipStatusRequest(this).getFriendshipStatusRequestReceivedUsers(SaveSharedPreference.getUserUID(this), "Friendship Pending", new UserListCallback() {
            //@Override
            public void done(List<User> users) {
                try {
                    if (users.size() != 0) {
                        ArrayList<FriendRequester> friendRequesterArrayList = new ArrayList<>();

                        for (User user : users) {
                            FriendRequester friendRequester = new FriendRequester(user.getUsername(), user.getFirstName() + " " + user.getLastName(), user.getUid());
                            friendRequesterArrayList.add(friendRequester);
                        }
                        vDivider.setVisibility(View.VISIBLE);
                        //recyclerViewFriendRequests = (RecyclerView) findViewById(R.id.recyclerView_friendRequest);
                        recyclerViewFriendRequests.setVisibility(View.VISIBLE);
                        adapter = new FriendRequestReceivedRecyclerAdapter(FriendsActivity.this, friendRequesterArrayList);
                        layoutManager = new LinearLayoutManager(FriendsActivity.this);
                        recyclerViewFriendRequests.setLayoutManager(layoutManager);
                        recyclerViewFriendRequests.setAdapter(adapter);
                    }
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        });
    }
**/

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
                    recyclerViewFriends.setVisibility(View.VISIBLE);
                    //recyclerView = (RecyclerView) findViewById(R.id.recyclerView_friends);
                    adapter = new FriendsRecyclerAdapter(FriendsActivity.this, friendsFirstLastName, friendsUsername, friendsUID, friendsStatus);
                    layoutManager = new LinearLayoutManager(FriendsActivity.this);
                    recyclerViewFriends.setLayoutManager(layoutManager);
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
            case R.id.home:
                Log.e("help", "it worksssss");
                onBackPressed();
                return true;
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
    int size;
    private void setFriendListSize(int size){
        this.size = size;
    }

    private int getFriendListSize(){
        return size;
    }
}
