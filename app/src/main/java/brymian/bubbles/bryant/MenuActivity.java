package brymian.bubbles.bryant;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangeEmail;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangePassword;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangeProfilePicture;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.LogOut;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.SyncFacebook;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.About;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.Notifications;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.PrivacySettings;

public class MenuActivity extends FragmentActivity implements View.OnClickListener{
    Button bChangePassword, bChangeEmail, bChangeProfilePicture, bLogOut, bSyncWIthDFacebook, bNotifications, bAbout, bPrivacySettings;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Account Buttons
        bChangePassword = (Button) findViewById(R.id.bChangePassword);
        bChangeEmail = (Button) findViewById(R.id.bChangeEmail);
        bChangeProfilePicture = (Button) findViewById(R.id.bChangeProfilePicture);
        bLogOut = (Button) findViewById(R.id.bLogOut);
        bSyncWIthDFacebook = (Button) findViewById(R.id.bSyncWithFacebook);

        //Settings Buttons
        bNotifications = (Button) findViewById(R.id.bNotifications);
        bAbout = (Button) findViewById(R.id.bAbout);
        bPrivacySettings = (Button) findViewById(R.id.bPrivacySettings);

        //Account onClickListeners
        bChangePassword.setOnClickListener(this);
        bChangeEmail.setOnClickListener(this);
        bChangeProfilePicture.setOnClickListener(this);
        bLogOut.setOnClickListener(this);
        bSyncWIthDFacebook.setOnClickListener(this);

        //Settings onClickListeners
        bNotifications.setOnClickListener(this);
        bAbout.setOnClickListener(this);
        bPrivacySettings.setOnClickListener(this);
    }
    //This button is to return to MapsActivity---ImageButton
    public void onClickMapsActivity(View view){
        Intent mapsActivityIntent = new Intent(this, MapsActivity.class);
        startActivity(mapsActivityIntent);
    }

    //----------Acount Buttons---------------------------------------------
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bChangePassword:
                Intent changePassword = new Intent(this, ChangePassword.class);
                startActivity(changePassword);
                break;
            case R.id.bChangeEmail:
                Intent changeEmail = new Intent(this, ChangeEmail.class);
                startActivity(changeEmail);
                break;
            case R.id.bChangeProfilePicture:
                Intent changeProfilePicture = new Intent(this, ChangeProfilePicture.class);
                startActivity(changeProfilePicture);
                break;
            case R.id.bLogOut:
                Intent logOut = new Intent(this, LogOut.class);
                startActivity(logOut);
                break;
            case R.id.bSyncWithFacebook:
                Intent syncWithFacebook = new Intent(this, SyncFacebook.class);
                startActivity(syncWithFacebook);
                break;
            case R.id.bNotifications:
                Intent notificationsIntent = new Intent(this, Notifications.class);
                startActivity(notificationsIntent);
                break;
            case R.id.bAbout:
                Intent aboutIntent = new Intent(this, About.class);
                startActivity(aboutIntent);
                break;
            case R.id.bPrivacySettings:
                Intent privacySettingsIntent = new Intent(this, PrivacySettings.class);
                startActivity(privacySettingsIntent);
                break;
        }
    }
}
