package brymian.bubbles.bryant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import brymian.bubbles.R;


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
        String username;
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                friendStatusString = null;
                username = null;
                uid = 0;
            } else {
                friendStatusString = extras.getString("Friend_Status");
                username = extras.getString("Friend_Username");
                uid = extras.getInt("Friend_UID");
            }
        } else {
            friendStatusString = (String) savedInstanceState.getSerializable("Friend_Status");
            username = (String) savedInstanceState.getSerializable("Friend_Username");
            uid = (Integer) savedInstanceState.getSerializable("Friend_UID");
        }

        //Checking for output
        System.out.println("THIS IS FROM PROFILE ACTIVITY: "+ friendStatusString);
        System.out.println("THIS IS FROM PROFILE ACTIVITY: " + username);
        System.out.println("THIS IS FROM PROFILE ACTIVITY: " + uid);

        tProfileName.setText(username);


        setBackground(uid, "Profile");

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bAddFriend:

                break;
            case R.id.bMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.bCamera:
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

    }
}
