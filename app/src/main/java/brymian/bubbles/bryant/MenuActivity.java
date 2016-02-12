package brymian.bubbles.bryant;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.VerifyEmail;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangePassword;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.SyncFacebook;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.Blocking;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.Privacy;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.ProfileActivity;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.ProfileBackground;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.ProfileName;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.About;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.Notifications;
import brymian.bubbles.bryant.MenuButtons.SocialButtons.Events;
import brymian.bubbles.bryant.MenuButtons.SocialButtons.FriendsList;
import brymian.bubbles.bryant.MenuButtons.SocialButtons.SearchUsers;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.activity.AuthenticateActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserCallback;
import brymian.bubbles.damian.nonactivity.UserDataLocal;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{
    TextView bChangePassword, bChangeEmail, bLogOut, bSyncWIthDFacebook;
    TextView bNotifications, bAbout, bBlocking;
    TextView bProfileBackground, bProfileName, bProfilePrivacy, bYourProfile, bSearchUser, bFriends, tEvents;
    ImageButton ibMap;
    String[] profileUserUsername = new String[1];
    String[] profileUserFirstLastName = new String[1];
    int[] profileUserUID = new int[1];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //Go to personal Profile
        ibMap = (ImageButton) findViewById(R.id.ibMap);

        //Account Buttons
        bChangePassword = (TextView) findViewById(R.id.bChangePassword);
        bChangeEmail = (TextView) findViewById(R.id.bChangeEmail);
        bLogOut = (TextView) findViewById(R.id.bLogOut);
        bSyncWIthDFacebook = (TextView) findViewById(R.id.bSyncWithFacebook);

        //Settings Buttons
        bNotifications = (TextView) findViewById(R.id.bNotifications);
        bAbout = (TextView) findViewById(R.id.bAbout);
        bBlocking = (TextView) findViewById(R.id.bBlocking);

        //Profile Buttons
        bYourProfile = (TextView) findViewById(R.id.bYourProfile);
        bProfileBackground = (TextView) findViewById(R.id.bProfileBackground);
        bProfileName = (TextView) findViewById(R.id.bProfileName);
        bProfilePrivacy = (TextView) findViewById(R.id.bProfilePrivacy);
        bSearchUser = (TextView) findViewById(R.id.bSearchUser);
        bFriends = (TextView) findViewById(R.id.bFriends);
        tEvents = (TextView) findViewById(R.id.tEvents);

        //Account onClickListeners
        bChangePassword.setOnClickListener(this);
        bChangeEmail.setOnClickListener(this);
        bLogOut.setOnClickListener(this);
        bSyncWIthDFacebook.setOnClickListener(this);

        //Settings onClickListeners
        bNotifications.setOnClickListener(this);
        bAbout.setOnClickListener(this);
        bBlocking.setOnClickListener(this);

        //Profile onClickListeners
        bProfileBackground.setOnClickListener(this);
        bProfileName.setOnClickListener(this);
        bProfilePrivacy.setOnClickListener(this);
        bYourProfile.setOnClickListener(this);
        bSearchUser.setOnClickListener(this);
        bFriends.setOnClickListener(this);
        tEvents.setOnClickListener(this);

        //Map button
        ibMap.setOnClickListener(this);

        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        setProfileUserUID(userUID);

        new ServerRequest(this).getUserData(userUID, new UserCallback() {
            @Override
            public void done(User user) {
                String userFirstLastName = user.getFirstName() + " " + user.getLastName();
                setProfileUserFirstLastName(userFirstLastName);

                String userUsername = user.getUsername();
                setProfileUserUsername(userUsername);
            }
        });

        try{
            FileInputStream fis = this.openFileInput("login_info.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //sb.append(line);
                System.out.print("sb.append(line)"+sb.append(line));
            }
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
            System.out.println("FileNotFoundException thrown.");
        }catch (IOException ioe){
            ioe.printStackTrace();
            System.out.println("IOException thrown.");
        }


    }

    public void onClick(View v){
        switch(v.getId()){
            //Account
            case R.id.bChangePassword:
                Intent changePassword = new Intent(this, ChangePassword.class);
                changePassword.putExtra("uid", getProfileUserUID());
                startActivity(changePassword);
                break;
            case R.id.bChangeEmail:
                Intent changeEmail = new Intent(this, VerifyEmail.class);
                startActivity(changeEmail);
                break;
            case R.id.bLogOut:
                displayAlertDialog();
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
            case R.id.bBlocking:
                Intent blockingIntent = new Intent(this, Blocking.class);
                startActivity(blockingIntent);
                break;
            //Profile
            case R.id.bYourProfile:

                Intent yourProfileIntent = new Intent(this, ProfileActivity.class);
                yourProfileIntent.putExtra("status", "Logged in user.");
                yourProfileIntent.putExtra("firstLastName", getProfileUserFirstLastName());
                yourProfileIntent.putExtra("username", getProfileUserUsername());
                yourProfileIntent.putExtra("uid", getProfileUserUID());
                startActivity(yourProfileIntent);

                break;
            case R.id.bSearchUser:
                Intent searchUserIntent = new Intent(this, SearchUsers.class);
                startActivity(searchUserIntent);
                break;
            case R.id.bFriends:
                Intent friendsList = new Intent(this, FriendsList.class);
                startActivity(friendsList);
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
            case R.id.tEvents:
                startActivity(new Intent(this, Events.class));
                break;
            //Home button on the top left
            case R.id.ibMap:
                Intent mapsIntent = new Intent(this, MapsActivity.class);
                mapsIntent.putExtra("firstLastName", "Everyone.");
                mapsIntent.putExtra("uid", "0");
                mapsIntent.putExtra("username", "Everyone.");
                startActivity(mapsIntent);
                break;
        }
    }
    public void displayAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.logout, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //alert.setTitle("Login");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MenuActivity.this, AuthenticateActivity.class));
                SaveSharedPreference.clearUserNameAndPassword(getApplicationContext());
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    void setProfileUserFirstLastName(String firstLastName){
        profileUserFirstLastName[0] = firstLastName;
    }
    void setProfileUserUsername(String username){
        profileUserUsername[0] = username;
    }
    void setProfileUserUID(int uid){
        profileUserUID[0] = uid;
    }
    String getProfileUserFirstLastName(){
        return profileUserFirstLastName[0];
    }
    String getProfileUserUsername(){
        return profileUserUsername[0];
    }
    int getProfileUserUID(){
        return profileUserUID[0];
    }

}
