package brymian.bubbles.bryant.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MapsActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.friends.FriendsList;
import brymian.bubbles.bryant.profile.friends.FriendsListOLD;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar mToolbar;
    int userUID;
    LinearLayout llMain;
    ImageButton ibLeft, ibMiddle, ibRight;
    String profile;
    String firstName, lastName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        /*--------------------------------Checking for putExtras()--------------------------------*/
        String profile;
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                profile = null;
                uid = 0;
            }
            else {
                profile = extras.getString("profile");
                uid = extras.getInt("uid");
            }
        }
        else {
            profile= savedInstanceState.getString("profile");
            uid = savedInstanceState.getInt("uid");
        }
        /*----------------------------------------------------------------------------------------*/


        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        ibLeft = (ImageButton) findViewById(R.id.ibLeft); /* left button will be users map */
        ibMiddle = (ImageButton) findViewById(R.id.ibMiddle); /* middle button will handle friend requests */
        ibRight = (ImageButton) findViewById(R.id.ibRight);

        if (profile != null) {
            if (profile.equals("logged in user")) {
                setUID(SaveSharedPreference.getUserUID(this));
                mToolbar.setTitle(SaveSharedPreference.getUserFirstName(this) + " " +
                        SaveSharedPreference.getUserLastName(this));
                setButtons(profile);
                setProfile(profile);
            }
            else {
                setUID(uid);
                setButtons(profile);
                setProfile(profile);
                Toast.makeText(ProfileActivity.this, profile, Toast.LENGTH_SHORT).show();

                new ServerRequestMethods(this).getUserData(uid, new UserCallback() {
                    @Override
                    public void done(User user) {
                        setFirstName(user.getFirstName());
                        setLastName(user.getLastName());
                        mToolbar.setTitle(user.getFirstName() + " " + user.getLastName());
                    }
                });

            }
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        llMain = (LinearLayout) findViewById(R.id.llMain);

        ibLeft.setOnClickListener(this);
        ibMiddle.setOnClickListener(this);
        ibRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibLeft:
                startActivity(new Intent(this, MapsActivity.class).putExtra("uid", getUID()));
                break;
            case R.id.ibMiddle:
                if(getProfile().equals("logged in user")){
                    startActivity(new Intent(this, FriendsList.class)
                            .putExtra("uid", getUID())
                            .putExtra("profile", "logged in user"));
                }
                else if(getProfile().equals("Already friends with user.")){
                    startActivity(new Intent(this, FriendsList.class)
                            .putExtra("uid", getUID())
                            .putExtra("profile", getFirstName() + " " + getLastName()));
                }
                else if(getProfile().equals("Not friends.")){
                    new ServerRequestMethods(this).setFriendStatus(SaveSharedPreference.getUserUID(this), getUID(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if(getProfile().equals("Already sent friend request to user.")){
                    Toast.makeText(ProfileActivity.this, "Already sent friend request to user.", Toast.LENGTH_SHORT).show();
                }
                else if (getProfile().equals("User is awaiting confirmation for friend request.")){
                    new ServerRequestMethods(this).setFriendStatus(SaveSharedPreference.getUserUID(this), getUID(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;


            case R.id.ibRight:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setButtons(String friendStatus) {
        switch (friendStatus){
            case "logged in user":
                ibLeft.setImageResource(android.R.drawable.ic_menu_mapmode);
                ibMiddle.setImageResource(R.mipmap.ic_menu_allfriends);
                ibRight.setImageResource(android.R.drawable.ic_menu_myplaces);
                break;

            case "Already friends with user.":
                ibLeft.setImageResource(android.R.drawable.ic_menu_mapmode);
                ibMiddle.setImageResource(R.mipmap.ic_menu_allfriends);
                ibRight.setImageResource(android.R.drawable.ic_menu_myplaces);
                break;

            case "Already sent friend request to user.":
                ibLeft.setImageResource(android.R.drawable.ic_menu_mapmode);
                ibMiddle.setImageResource(android.R.drawable.ic_menu_delete);
                //ibMiddle.setImageResource(android.R.drawable.ic_menu_add);
                ibRight.setImageResource(android.R.drawable.ic_menu_myplaces);
                break;

            case "User is awaiting confirmation for friend request.":
                ibLeft.setImageResource(android.R.drawable.ic_menu_mapmode);
                //ibMiddle.setImageResource(android.R.drawable.ic_menu_delete);
                ibMiddle.setImageResource(android.R.drawable.ic_menu_add);
                ibRight.setImageResource(android.R.drawable.ic_menu_myplaces);
                break;

            case "Not friends.":
                ibLeft.setImageResource(android.R.drawable.ic_menu_mapmode);
                ibMiddle.setImageResource(android.R.drawable.ic_menu_add);
                ibRight.setImageResource(android.R.drawable.ic_menu_myplaces);
                break;

            case "User is currently being blocked.":

                break;

            case "Currently being blocked by user.":

                break;
            default:


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

    private void setFirstName(String firstName){
        this.firstName = firstName;
    }

    private String getFirstName(){
        return firstName;
    }

    private void setLastName(String lastName){
        this.lastName = lastName;
    }

    private String getLastName(){
        return lastName;
    }
}
