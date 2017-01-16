package brymian.bubbles.bryant.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.friends.FriendsActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Event;
import brymian.bubbles.objects.Image;
import brymian.bubbles.objects.User;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;


public class ProfileActivity extends AppCompatActivity{
    public static ImageView ivProfilePicture;
    FloatingActionButton fabStatus;
    TextView tvProfileFirstLastName, tvFriendsNum, tvFriendStatus, tvEpisodesNum;
    CardView cvUserFriends;
    int userUID;
    String profile;
    String firstName, lastName, username, privacy;
    Toolbar mToolbar;

    RecyclerView rvUserEpisodes;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        /*--------------------------------Checking for putExtras()--------------------------------*/
        String profile;
        String username;
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                profile = null;
                username = null;
                uid = 0;
            }
            else {
                profile = extras.getString("profile");
                username = extras.getString("username");
                uid = extras.getInt("uid");
            }
        }
        else {
            profile= savedInstanceState.getString("profile");
            username = savedInstanceState.getString("username");
            uid = savedInstanceState.getInt("uid");
        }
        /*----------------------------------------------------------------------------------------*/
        ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);
        fabStatus = (FloatingActionButton) findViewById(R.id.fabStatus);
        tvFriendStatus = (TextView) findViewById(R.id.tvFriendStatus);
        tvFriendStatus.setVisibility(View.GONE);
        tvProfileFirstLastName = (TextView) findViewById(R.id.tvUserFirstLastName);
        cvUserFriends = (CardView) findViewById(R.id.cvUserFriends);
        cvUserFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, FriendsActivity.class).putExtra("uid", SaveSharedPreference.getUserUID(ProfileActivity.this)).putExtra("profile", getUsername()));
            }
        });
        tvFriendsNum = (TextView) findViewById(R.id.tvFriendsNum);
        tvEpisodesNum = (TextView) findViewById(R.id.tvEpisodesNum);
        rvUserEpisodes = (RecyclerView) findViewById(R.id.rvUserEpisodes);
        getProfilePictures(uid);
        //setFloatingActionButtons();

        Toast.makeText(ProfileActivity.this, profile, Toast.LENGTH_SHORT).show();

        if (profile != null) {
            if (profile.equals("logged in user")) {
                setUID(SaveSharedPreference.getUserUID(this));
                mToolbar.setTitle(SaveSharedPreference.getUsername(this));
                tvProfileFirstLastName.setText(SaveSharedPreference.getUserFirstName(this) + " " + SaveSharedPreference.getUserLastName(this));
                //setButtons(profile);
                setProfile(profile);
            }
            else {
                setUID(uid);
                //setButtons(profile);
                setProfile(profile);
                mToolbar.setTitle(username);
                new ServerRequestMethods(this).getUserData(uid, new UserCallback() {
                    @Override
                    public void done(User user) {
                        setFirstLastName(user.getFirstName(), user.getLastName());
                        tvProfileFirstLastName.setText(user.getFirstName() + " " + user.getLastName());
                        setPrivacy(user.getUserAccountPrivacy());
                    }
                });
            }
        }
        getFriendsNum();
        getEpisodesNum();
        setUserProfileInfo(profile);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_activity_menu_inflater, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.removeAsFriend:
                new FriendshipStatusRequest(this).unFriend(SaveSharedPreference.getUserUID(this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.Block:
                new FriendshipStatusRequest(this).blockUser(SaveSharedPreference.getUserUID(this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.Report_user:
                Toast.makeText(this, "Report under construction", Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUserProfileInfo(String profile){
        switch (profile){
            case "logged in user":
                fabStatus.hide();
                break;
            case "Already friends with user.":
                fabStatus.hide();
                break;
            case "Already sent friend request to user.":
                fabStatus.setImageResource(R.mipmap.ic_dots_horizontal_white_24dp);
                tvFriendStatus.setText(profile);
                tvFriendStatus.setVisibility(View.VISIBLE);
                fabStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogFriendRequestSent();
                    }
                });
                break;
            case "User is awaiting confirmation for friend request.":
                fabStatus.setImageResource(R.mipmap.ic_dots_horizontal_white_24dp);
                tvFriendStatus.setText(profile);
                tvFriendStatus.setVisibility(View.VISIBLE);
                fabStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogFriendRequestReceived();
                    }
                });
                break;
            case "Not friends.":
                fabStatus.setImageResource(R.mipmap.ic_person_add_white_24dp);
                fabStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new ServerRequestMethods(ProfileActivity.this).setFriendStatus(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(),
                                new StringCallback() {
                                    public void done(String string) {
                                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }
                });
                break;
            case "User is currently being blocked.":
                /* hide everything, show user doesnt exist like facebook? */
                fabStatus.hide();
                break;
            case "Currently being blocked by user.":
                /* hide everything, show user doesnt exist like facebook? */
                fabStatus.hide();
                break;
            default:
        }
    }

    private void getFriendsNum(){
        new ServerRequestMethods(this).getFriends(getUID(), new UserListCallback() {
            @Override
            public void done(List<User> users) {
                tvFriendsNum.setText(String.valueOf(users.size()));
            }
        });
    }

    private void getEpisodesNum(){
        new EventRequest(this).getEventDataByMember(getUID(), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                if(eventList.size() > 0){
                    tvEpisodesNum.setText(String.valueOf(eventList.size()));

                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeHostUsername = new ArrayList<>();
                    List<Integer> episodeEid = new ArrayList<>();

                    if(eventList.size() <= 3){
                        for (Event event: eventList){
                            episodeTitle.add(event.eventName);
                            episodeHostUsername.add(event.eventHostUsername);
                            episodeEid.add(event.eid);
                        }

                        adapter = new ProfileActivityUserEpisodesRecyclerAdapter(ProfileActivity.this, episodeTitle, episodeHostUsername, episodeEid);
                        layoutManager = new LinearLayoutManager(ProfileActivity.this);
                        rvUserEpisodes.setLayoutManager(layoutManager);
                        rvUserEpisodes.setNestedScrollingEnabled(false);
                        rvUserEpisodes.setAdapter(adapter);
                    }
                    else{
                        ArrayList<Integer> list = new ArrayList<>();
                        for (int i = 0; i < eventList.size() - 1; i++) {
                            list.add(i, i);
                        }
                        Collections.shuffle(list);
                        for (int i = 0; i < 3; i++) {
                            episodeTitle.add(eventList.get(list.get(i)).eventName);
                            episodeHostUsername.add(eventList.get(list.get(i)).eventHostUsername);
                            episodeEid.add(eventList.get(list.get(i)).eid);
                        }
                        adapter = new ProfileActivityUserEpisodesRecyclerAdapter(ProfileActivity.this, episodeTitle, episodeHostUsername, episodeEid);
                        layoutManager = new LinearLayoutManager(ProfileActivity.this);
                        rvUserEpisodes.setLayoutManager(layoutManager);
                        rvUserEpisodes.setNestedScrollingEnabled(false);
                        rvUserEpisodes.setAdapter(adapter);
                    }
                }
                else {
                    tvEpisodesNum.setText("0");
                }
            }
        });
    }
    /**

    private void setFloatingActionButtons(){
        fabGoBack = (FloatingActionButton) findViewById(R.id.fabGoBack);
        fabGoBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fabStatusAction = (FloatingActionButton) findViewById(R.id.fabStatusAction);
        fabStatusAction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switch (getProfile()) {
                    case "logged in user":
                        startActivity(new Intent(ProfileActivity.this, FriendsActivity.class)
                                .putExtra("uid", getUID())
                                .putExtra("profile", "logged in user"));
                        break;
                    case "Already friends with user.":
                        startActivity(new Intent(ProfileActivity.this, FriendsActivity.class)
                                .putExtra("uid", getUID())
                                .putExtra("profile", getFirstName() + " " + getLastName()));
                        break;
                    case "Not friends.":
                        new ServerRequestMethods(ProfileActivity.this).setFriendStatus(
                                SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(),
                                new StringCallback() {
                                    public void done(String string) {
                                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                                    }
                                });
                        break;
                    case "Already sent friend request to user.":
                        Toast.makeText(ProfileActivity.this, "Already sent friend request to user.", Toast.LENGTH_SHORT).show();
                        break;
                    case "User is awaiting confirmation for friend request.":
                        new ServerRequestMethods(ProfileActivity.this).setFriendStatus(
                                SaveSharedPreference.getUserUID(ProfileActivity.this),
                                getUID(),
                                new StringCallback() {
                                    public void done(String string) {
                                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                                    }
                                });
                        break;
                }
            }
        });

        fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);
        fabRemove = (FloatingActionButton) findViewById(R.id.fabRemove);
        fabRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new FriendshipStatusRequest(ProfileActivity.this).unFriend(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(), new StringCallback() {
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        fabMessage = (FloatingActionButton) findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Under construction: Message", Toast.LENGTH_SHORT).show();
            }
        });

        fabBlock = (FloatingActionButton) findViewById(R.id.fabBlock);
        fabBlock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new FriendshipStatusRequest(ProfileActivity.this).blockUser(
                        SaveSharedPreference.getUserUID(ProfileActivity.this),
                        getUID(),
                        new StringCallback() {
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        fabEpisodes = (FloatingActionButton) findViewById(R.id.fabEpisodes);
        fabEpisodes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(getProfile().equals("logged in user")){
                    startActivity(new Intent(ProfileActivity.this, MapsActivity.class)
                            .putExtra("profile", "logged in user"));
                }
                else if(getPrivacy().equals("Private")){
                    Toast.makeText(ProfileActivity.this, "Private account", Toast.LENGTH_SHORT).show();
                }
                else if(getPrivacy().equals("Public")){
                    startActivity(new Intent(ProfileActivity.this, MapsActivity.class)
                            .putExtra("uid", getUID())
                            .putExtra("profile", getFirstName() + " " + getLastName()));
                }
            }
        });

    }

    private void setButtons(String friendStatus) {
        switch (friendStatus){
            case "logged in user":
                fabStatusAction.setImageResource(R.mipmap.ic_people_outline_black_24dp);
                fabRemove.hide(true);
                break;
            case "Already friends with user.":
                fabStatusAction.setImageResource(R.mipmap.ic_people_outline_black_24dp);
                break;
            case "Already sent friend request to user.":
                fabStatusAction.setImageResource(R.mipmap.ic_more_horiz_black_24dp);
                break;
            case "User is awaiting confirmation for friend request.":
                fabStatusAction.setImageResource(R.mipmap.ic_more_horiz_black_24dp);
                break;
            case "Not friends.":
                fabStatusAction.setImageResource(R.mipmap.ic_person_add_black_24dp);
                fabRemove.hide(true);
                break;
            case "User is currently being blocked.":

                break;
            case "Currently being blocked by user.":

                break;
            default:
        }
    }
**/

    private void alertDialogFriendRequestReceived(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_dialog_friend_request_received, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new FriendshipStatusRequest(ProfileActivity.this).rejectFriend(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ServerRequestMethods(ProfileActivity.this).setFriendStatus(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void alertDialogFriendRequestSent(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_dialog_friend_request_sent, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new FriendshipStatusRequest(ProfileActivity.this).cancelFriend(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void getProfilePictures(int uid){
        new UserImageRequest(this).getImagesByUidAndPurpose(uid, "Public", null, new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                if (imageList.size() > 0) {
                    new DownloadImage(imageList.get(0).userImagePath).execute();
                }
            }
        });
    }


    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String path;

        DownloadImage(String path){
            this.path = path;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URLConnection connection = new URL(path).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);
                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                ivProfilePicture.setImageBitmap(bitmap);
            }
        }
    }

    private void setUID(int uid){
        this.userUID = uid;
    }

    private int getUID(){
        return userUID;
    }

    private void setProfile(String profile){
        this.profile = profile;
    }

    private String getProfile(){
        return profile;
    }

    private void setFirstLastName(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private String getFirstLastName(){
        return firstName + " " + lastName;
    }

    private void setUsername(String username){
        this.username = username;
    }

    private String getUsername(){
        return username;
    }
    private void setPrivacy(String privacy){
        this.privacy = privacy;
    }

    private String getPrivacy(){
        return privacy;
    }
}
