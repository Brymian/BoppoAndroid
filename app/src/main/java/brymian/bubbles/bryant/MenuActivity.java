package brymian.bubbles.bryant;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangeEmail;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangePassword;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.LogOut;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.SyncFacebook;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.Privacy;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.ProfileBackground;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.ProfileName;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.About;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.Notifications;
import brymian.bubbles.damian.fragment.Authenticate.LaunchFragmentFacebook;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{
    Button bChangePassword, bChangeEmail, bLogOut, bSyncWIthDFacebook;
    Button bNotifications, bAbout;
    Button bProfileBackground, bProfileName, bProfilePrivacy, bYourProfile;
    ImageButton ibMap;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Go to personal Profile
        ibMap = (ImageButton) findViewById(R.id.ibMap);

        //Account Buttons
        bChangePassword = (Button) findViewById(R.id.bChangePassword);
        bChangeEmail = (Button) findViewById(R.id.bChangeEmail);
        bLogOut = (Button) findViewById(R.id.bLogOut);
        bSyncWIthDFacebook = (Button) findViewById(R.id.bSyncWithFacebook);

        //Settings Buttons
        bNotifications = (Button) findViewById(R.id.bNotifications);
        bAbout = (Button) findViewById(R.id.bAbout);

        //Profile Buttons
        bYourProfile = (Button) findViewById(R.id.bYourProfile);
        bProfileBackground = (Button) findViewById(R.id.bProfileBackground);
        bProfileName = (Button) findViewById(R.id.bProfileName);
        bProfilePrivacy = (Button) findViewById(R.id.bProfilePrivacy);

        //Account onClickListeners
        bChangePassword.setOnClickListener(this);
        bChangeEmail.setOnClickListener(this);
        bLogOut.setOnClickListener(this);
        bSyncWIthDFacebook.setOnClickListener(this);

        //Settings onClickListeners
        bNotifications.setOnClickListener(this);
        bAbout.setOnClickListener(this);

        //Profile onClickListeners
        bProfileBackground.setOnClickListener(this);
        bProfileName.setOnClickListener(this);
        bProfilePrivacy.setOnClickListener(this);
        bYourProfile.setOnClickListener(this);

        //Map button
        ibMap.setOnClickListener(this);
    }

    public void onClick(View v){
        switch(v.getId()){
            //Account
            case R.id.bChangePassword:
                Intent changePassword = new Intent(this, ChangePassword.class);
                startActivity(changePassword);
                break;
            case R.id.bChangeEmail:
                Intent changeEmail = new Intent(this, ChangeEmail.class);
                startActivity(changeEmail);
                break;
            case R.id.bLogOut:
                Intent logOut = new Intent(this, LogOut.class);
                startActivity(logOut);
                break;
            case R.id.bSyncWithFacebook:
                Intent syncWithFacebook = new Intent(this, SyncFacebook.class);
                startActivity(syncWithFacebook);
                break;
            //Settings
            case R.id.bNotifications:
                Intent notificationsIntent = new Intent(this, Notifications.class);
                startActivity(notificationsIntent);
                break;
            case R.id.bAbout:
                Intent aboutIntent = new Intent(this, About.class);
                startActivity(aboutIntent);
                break;
            //Profile
            case R.id.bYourProfile:
                Intent yourProfileIntent = new Intent(this, ProfileActivity.class);
                yourProfileIntent.putExtra("Friend_Status", "Logged in user.");
                startActivity(yourProfileIntent);
                break;
            case R.id.bProfileBackground:
                Intent profileBackgroundIntent = new Intent(this, ProfileBackground.class);
                startActivity(profileBackgroundIntent);
                break;
            case R.id.bProfileName:
                Intent profileNameIntent = new Intent(this, ProfileName.class);
                startActivity(profileNameIntent);
                break;
            case R.id.bProfilePrivacy:
                Intent profilePrivacyIntent = new Intent(this, Privacy.class);
                startActivity(profilePrivacyIntent);
                break;
            //Home button on the top left
            case R.id.ibMap:
                Intent mapsIntent = new Intent(this, MapsActivity.class);
                mapsIntent.putExtra("user", "Everyone.");
                startActivity(mapsIntent);
                break;
        }
    }
}
