package brymian.bubbles.bryant;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.Image;
import brymian.bubbles.damian.nonactivity.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;


public class ProfileActivity extends FragmentActivity implements View.OnClickListener{
    TextView tProfileName;
    ImageButton bMenu, bLeft, bRight, bMiddle;
    int[] IDhold = new int[1];
    String[] firstLastNameArray = new String[1];
    String[] userNameArray = new String[1];
    String[] friendStatusArray = new String[1];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Linking xml IDs with java IDs
        tProfileName = (TextView) findViewById(R.id.tProfileName);

        bMiddle = (ImageButton) findViewById(R.id.bMiddle);
        bMenu = (ImageButton) findViewById(R.id.bMenu);
        bLeft = (ImageButton) findViewById(R.id.bLeft);
        bRight = (ImageButton) findViewById(R.id.bRight);

        //Getting the information from FriendsActivity using putExtra()
        String friendStatusString;
        String first_lastName;
        String username;
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                friendStatusString = null;
                first_lastName = null;
                username = null;
                uid = 0;
            } else {
                friendStatusString = extras.getString("status");
                first_lastName = extras.getString("firstLastName");
                username = extras.getString("username");
                uid = extras.getInt("uid");
            }
        } else {
            friendStatusString = (String) savedInstanceState.getSerializable("status");
            first_lastName = (String) savedInstanceState.getSerializable("firstLastName");
            username = (String) savedInstanceState.getSerializable("username");
            uid = savedInstanceState.getInt("uid");
        }

        //Checking for output
        System.out.println("THIS IS FROM PROFILE ACTIVITY (friendStatus): " + friendStatusString);
        System.out.println("THIS IS FROM PROFILE ACTIVITY (first_lastname): " + first_lastName);
        System.out.println("THIS IS FROM PROFILE ACTIVITY (username): " + username);
        System.out.println("THIS IS FROM PROFILE ACTIVITY (uid): " + uid);
        //-------------------------------------------------------------------------------------

        setButtons(friendStatusString);
        setFriendStatus(friendStatusString);
        setID(uid);
        setUsername(username);
        setBackground(uid, "Profile");

        tProfileName.setText(first_lastName);
        //Setting the onClickListeners for the buttons
        bMenu.setOnClickListener(this);     
        bMiddle.setOnClickListener(this);
        bLeft.setOnClickListener(this);
        bRight.setOnClickListener(this);
    }

    void setButtons(String friendStatus){
        Drawable blockingDrawable = getResources().getDrawable(R.mipmap.blockblack_nopadding);
        Drawable addfriendDrawable = getResources().getDrawable(R.mipmap.addfriend_nopadding);
        Drawable friendslistDrawable = getResources().getDrawable(R.mipmap.friendslist_nopadding);
        Drawable globeDrawable = getResources().getDrawable(R.mipmap.globeblackwhite_nopadding);
        switch(friendStatus){
            case "Not friends.":
                bMiddle.setImageDrawable(addfriendDrawable);
                bLeft.setImageDrawable(blockingDrawable);
                bRight.setImageDrawable(globeDrawable);
                break;
            case "Already sent friend request to user.":
                bMiddle.setImageDrawable(addfriendDrawable);
                //bAddFriend.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                bLeft.setImageDrawable(blockingDrawable);
                bRight.setImageDrawable(globeDrawable);
                break;
            case "Already friends with user.":
                bMiddle.setImageDrawable(friendslistDrawable);
                bLeft.setImageDrawable(blockingDrawable);
                bRight.setImageDrawable(globeDrawable);
                break;
            case "Logged in user.":
                bMiddle.setImageDrawable(friendslistDrawable);
                bLeft.setImageDrawable(blockingDrawable);
                bRight.setImageDrawable(globeDrawable);
                setFirstLastName("Logged in user.");//Need to change this to logged in user's name.
                break;
            case "User is awaiting confirmation for friend request.":

                break;
            case "User is currently being blocked.":

                break;
            case "Currently being blocked by user.":

                break;
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bMiddle:
                System.out.println("this is return temp user who hold: " + getFirstLastName());
                if(getFirstLastName().equals("Logged in user.")){
                    Intent friendListIntent = new Intent(this, FriendsActivity.class);
                    startActivity(friendListIntent);
                }
                else {
                    //need to change first two paramaters-----------------------
                    new ServerRequest(this).setFriendStatus(1, getID(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println("ADD FRIEND BUTTON OUTPUT: " + string);
                            if(string == "Friend request sent successfully."){
                                //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                break;
            case R.id.bMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.bLeft:
                //Intent cameraIntent = new Intent(this, CameraActivity.class);
                //startActivity(cameraIntent);
                break;
            case R.id.bRight:
                Intent mapIntent = new Intent(this, MapsActivity.class);
                String userWho = getFirstLastName();
                String userName = getUsername();
                int userID = getID();

                if(getFriendStatus().equals("Logged in user.")){
                    mapIntent.putExtra("userWho", userWho);
                    mapIntent.putExtra("userName", userName);
                    startActivity(mapIntent);
                }
                else {
                    mapIntent.putExtra("userWho", userWho);
                    mapIntent.putExtra("userName", userName);
                    startActivity(mapIntent);
                }

                break;
        }
    }

    protected void setBackground(int uid, String purpose){
        new ServerRequest(this).getImages(uid, purpose, new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                System.out.println("THIS IS FROM IMAGELIST: " + imageList);
            }
        });
    }

    void setID(int uid){
        IDhold[0] = uid;
    }

    void setFirstLastName(String input){
        firstLastNameArray[0] = input;
    }

    void setUsername(String input){
        userNameArray[0] = input;
    }

    void setFriendStatus(String input){
        friendStatusArray[0] = input;
    }

    int getID(){
        return IDhold[0];
    }

    String getFirstLastName(){
        return firstLastNameArray[0];
    }

    String getUsername(){
        return userNameArray[0];
    }

    String getFriendStatus(){
        return friendStatusArray[0];
    }
}
