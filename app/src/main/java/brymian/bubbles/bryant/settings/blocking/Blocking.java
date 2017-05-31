package brymian.bubbles.bryant.settings.blocking;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.objects.User;

public class Blocking extends AppCompatActivity {

    Toolbar mToolbar;
    RecyclerView rvBlockedUsers;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blocking);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Blocking);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvBlockedUsers = (RecyclerView) findViewById(R.id.rvBlockedUsers);

        getFriends();
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

    private void getFriends(){
        /** BRYANT, UPDATE THIS **/
        /*
        new FriendshipStatusRequest().getFriendshipStatusRequestSentUsers(SaveSharedPreference.getUserUID(this), "Blocked", new UserListCallback() {
            @Override
            public void done(List<User> users) {
                List<BlockedUser> blockedUserArrayList = new ArrayList<>();

                for (User user : users) {
                    BlockedUser friendRequester = new BlockedUser(user.getUsername(), user.getFirstName() + " " + user.getLastName(), user.getUid());
                    blockedUserArrayList.add(friendRequester);
                }
                adapter = new BlockingRecyclerAdapter(Blocking.this, blockedUserArrayList);
                layoutManager = new LinearLayoutManager(Blocking.this);
                rvBlockedUsers.setLayoutManager(layoutManager);
                rvBlockedUsers.setAdapter(adapter);
            }
        });
        */
    }

}
