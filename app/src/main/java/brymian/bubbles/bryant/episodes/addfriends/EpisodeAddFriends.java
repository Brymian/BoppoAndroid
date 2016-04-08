package brymian.bubbles.bryant.episodes.addfriends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;

public class EpisodeAddFriends extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView tvTesting;
    ArrayList<Friend> friendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_add_friends);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.Add_Friends);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        new ServerRequestMethods(this).getFriends(SaveSharedPreference.getUserUID(this), new UserListCallback() {
            @Override
            public void done(List<User> users) {
                if (users.size() != 0) {
                    for (User user : users) {
                        Friend friend = new Friend(user.getUsername(), user.getFirstName() + " " + user.getLastName(), user.getUid(), false);
                        friendList.add(friend);
                    }

                    recyclerView = (RecyclerView) findViewById(R.id.recyclerView_addFriends);
                    adapter = new EpisodeAddFriendsRecyclerAdapter(friendList);
                    layoutManager = new LinearLayoutManager(EpisodeAddFriends.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                }
            }
        });

        tvTesting = (TextView) findViewById(R.id.tvTesting);
        tvTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                List<Friend> stList = ((EpisodeAddFriendsRecyclerAdapter) adapter)
                        .getStudentist();

                for (int i = 0; i < stList.size(); i++) {
                    Friend singleStudent = stList.get(i);
                    if (singleStudent.getIsSelected()) {

                        data = data + "\n" + singleStudent.getFirstLastName();
                    }
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
