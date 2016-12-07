package brymian.bubbles.bryant.profile;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.map.MapsActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.friends.FriendsList;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Image;
import brymian.bubbles.objects.User;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    public static ImageView ivProfilePicture;
    FloatingActionButton fabStatus;
    TextView tvProfileFirstLastName, tvFriendsNum, tvFriendStatus;
    CardView cvUserFriends;
    int userUID;
    String profile;
    String firstName, lastName, username, privacy;
    Toolbar mToolbar;

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
        cvUserFriends.setOnClickListener(this);
        tvFriendsNum = (TextView) findViewById(R.id.tvFriendsNum);
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
        setUserProfileInfo(profile);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cvUserFriends:
                startActivity(new Intent(this, FriendsList.class).putExtra("uid", SaveSharedPreference.getUserUID(this)).putExtra("profile", getUsername()));
            break;

            default:

        }
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
                break;
            case "User is awaiting confirmation for friend request.":
                fabStatus.setImageResource(R.mipmap.ic_dots_horizontal_white_24dp);
                tvFriendStatus.setText(profile);
                tvFriendStatus.setVisibility(View.VISIBLE);
                break;
            case "Not friends.":
                fabStatus.setImageResource(R.mipmap.ic_person_add_white_24dp);
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
    /**

    private void setFloatingActionButtons(){
        fabGoBack = (FloatingActionButton) findViewById(R.id.fabGoBack);
        fabGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fabStatusAction = (FloatingActionButton) findViewById(R.id.fabStatusAction);
        fabStatusAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (getProfile()) {
                    case "logged in user":
                        startActivity(new Intent(ProfileActivity.this, FriendsList.class)
                                .putExtra("uid", getUID())
                                .putExtra("profile", "logged in user"));
                        break;
                    case "Already friends with user.":
                        startActivity(new Intent(ProfileActivity.this, FriendsList.class)
                                .putExtra("uid", getUID())
                                .putExtra("profile", getFirstName() + " " + getLastName()));
                        break;
                    case "Not friends.":
                        new ServerRequestMethods(ProfileActivity.this).setFriendStatus(
                                SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(),
                                new StringCallback() {
                                    @Override
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
                                    @Override
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
            @Override
            public void onClick(View view) {
                new FriendshipStatusRequest(ProfileActivity.this).unFriend(SaveSharedPreference.getUserUID(ProfileActivity.this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        fabMessage = (FloatingActionButton) findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Under construction: Message", Toast.LENGTH_SHORT).show();
            }
        });

        fabBlock = (FloatingActionButton) findViewById(R.id.fabBlock);
        fabBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FriendshipStatusRequest(ProfileActivity.this).blockUser(
                        SaveSharedPreference.getUserUID(ProfileActivity.this),
                        getUID(),
                        new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        fabEpisodes = (FloatingActionButton) findViewById(R.id.fabEpisodes);
        fabEpisodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
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

    private void getProfilePictures(int uid){
        new UserImageRequest(this).getImagesByUidAndPurpose(uid, "Profile", null, new ImageListCallback() {
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

        public DownloadImage(String path){
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
