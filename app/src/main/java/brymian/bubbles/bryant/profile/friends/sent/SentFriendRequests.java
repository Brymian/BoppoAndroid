package brymian.bubbles.bryant.profile.friends.sent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.objects.User;

/**
 * Created by Almanza on 4/7/2016.
 */
public class SentFriendRequests extends AppCompatActivity{

    Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sent_requests);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Sent_Requests);

        new FriendshipStatusRequest(this).getFriendshipStatusRequestSentUsers(SaveSharedPreference.getUserUID(this), "Friendship Pending", new UserListCallback() {
            @Override
            public void done(List<User> users) {
                ArrayList<String> sentFriendRequestFirstLastName = new ArrayList<String>();
                ArrayList<String> sentFriendRequestUsername = new ArrayList<String>();

                for (User user : users) {
                    sentFriendRequestFirstLastName.add(user.getFirstName() + " " + user.getLastName());
                    sentFriendRequestUsername.add(user.getUsername());
                }

                recyclerView = (RecyclerView) findViewById(R.id.recyclerView_sentFriendRequest);
                adapter = new SentFriendRequestsRecyclerAdapter(sentFriendRequestFirstLastName, sentFriendRequestUsername);
                layoutManager = new LinearLayoutManager(SentFriendRequests.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
