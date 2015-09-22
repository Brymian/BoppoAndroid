package brymian.bubbles.bryant;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.Image;
import brymian.bubbles.damian.nonactivity.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.UserListCallback;


public class ProfileActivity extends FragmentActivity implements View.OnClickListener{
    TextView tProfileName;
    ImageButton bMenu, bBlockUser, bMap, bAddFriend;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Linking xml IDs with java IDs
        tProfileName = (TextView) findViewById(R.id.tProfileName);

        bAddFriend = (ImageButton) findViewById(R.id.bAddFriend);
        bMenu = (ImageButton) findViewById(R.id.bMenu);
        bBlockUser = (ImageButton) findViewById(R.id.bBlockUser);
        bMap = (ImageButton) findViewById(R.id.bMap);

        //Setting the onClickListeners for the buttons
        bMenu.setOnClickListener(this);
        bAddFriend.setOnClickListener(this);
        bBlockUser.setOnClickListener(this);
        bMap.setOnClickListener(this);

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
                friendStatusString = extras.getString("Friend_Status");
                first_lastName = extras.getString("Friend_FirstLastName");
                username = extras.getString("Friend_Username");
                uid = extras.getInt("Friend_UID");
            }
        } else {
            friendStatusString = (String) savedInstanceState.getSerializable("Friend_Status");
            first_lastName = (String) savedInstanceState.getSerializable("Friend_FirstLastName");
            username = (String) savedInstanceState.getSerializable("Friend_Username");
            uid = (Integer) savedInstanceState.getSerializable("Friend_UID");
        }

        //Checking for output
        System.out.println("THIS IS FROM PROFILE ACTIVITY: " + friendStatusString);
        System.out.println("THIS IS FROM PROFILE ACTIVITY: " + first_lastName);
        System.out.println("THIS IS FROM PROFILE ACTIVITY: " + username);
        System.out.println("THIS IS FROM PROFILE ACTIVITY: " + uid);

        Drawable blockingDrawable = getResources().getDrawable(R.mipmap.blockred_nopadding);
        Drawable addfriendDrawable = getResources().getDrawable(R.mipmap.addfriend_nopadding);
        Drawable friendslistDrawable = getResources().getDrawable(R.mipmap.friendslist_nopadding);
        Drawable globeDrawable = getResources().getDrawable(R.mipmap.globeblackwhite_nopadding);

        bBlockUser.setImageDrawable(blockingDrawable);
        bMap.setImageDrawable(globeDrawable);

        if(friendStatusString.toString().equals("Not Friends.")){
            bAddFriend.setImageDrawable(addfriendDrawable);
        }
        else if(friendStatusString.toString().equals("Already sent friend request to user.")){
            bAddFriend.setImageDrawable(addfriendDrawable);
            bAddFriend.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }
        else if(friendStatusString.toString().equals("Already friends with user.")){
            bAddFriend.setImageDrawable(friendslistDrawable);
        }

        tProfileName.setText(first_lastName);

        setBackground(uid, "Profile");
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bAddFriend:
                //need to change first two paramaters-----------------------
                new ServerRequest(this).setFriendStatus(1, 2, new StringCallback() {
                    @Override
                    public void done(String string) {
                        System.out.println("ADD FRIEND BUTTON OUTPUT: " + string);
                        if(string == "Friend request sent successfully."){
                            //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.bMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.bBlockUser:
                Intent cameraIntent = new Intent(this, CameraActivity.class);
                startActivity(cameraIntent);
                break;
            case R.id.bMap:
                Intent mapIntent = new Intent(this, MapsActivity.class);
                startActivity(mapIntent);
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
}
