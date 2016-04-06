package brymian.bubbles.bryant.episodes.addfriends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;

/**
 * Created by Almanza on 4/5/2016.
 */
public class EpisodeAddFriends extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

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
                if(users.size() != 0){
                    ArrayList<String> friendFirstLastName = new ArrayList<String>();
                    ArrayList<String> friendUsername = new ArrayList<String>();

                    for(int i = 0; i < users.size(); i++){
                        friendFirstLastName.add(i, users.get(i).getFirstName() + " " + users.get(i).getLastName());
                        friendUsername.add(i, users.get(i).getUsername());
                    }

                    recyclerView = (RecyclerView) findViewById(R.id.recyclerView_addFriends);
                    adapter = new EpisodeAddFriendsRecyclerAdapter(friendFirstLastName, friendUsername);
                    layoutManager = new LinearLayoutManager(EpisodeAddFriends.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);

                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
