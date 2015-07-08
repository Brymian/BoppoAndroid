package brymian.bubbles.bryant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangeEmail;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangePassword;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangeProfilePicture;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.LogOut;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.SyncFacebook;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.About;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.Notifications;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.PrivacySettings;

public class MenuActivity extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    //This button is to return to MapsActivity---ImageButton
    public void onClickMapsActivity(View view){
        Intent mapsActivityIntent = new Intent(this, MapsActivity.class);
        startActivity(mapsActivityIntent);
    }
    //----------Acount Buttons---------------------------------------------
    public void onClickChangePassword(View view){
        Intent changepasswordIntent = new Intent(this, ChangePassword.class);
        startActivity(changepasswordIntent);
    }
    public void onClickChangeEmail(View view){
        Intent changeemailIntent = new Intent(this, ChangeEmail.class);
        startActivity(changeemailIntent);
    }
    public void onClickChangeProfilePicture(View view){
        Intent changeprofilepictureIntent = new Intent(this, ChangeProfilePicture.class);
        startActivity(changeprofilepictureIntent);
    }

    public void onClickLogOut(View view){
        Intent logoutIntent = new Intent(this, LogOut.class);
        startActivity(logoutIntent);
    }

    public void onClickSyncFacebook(View view){
        Intent syncFBIntent = new Intent(this, SyncFacebook.class);
        startActivity(syncFBIntent);
    }
    //-----------------------------------------------------------------------

    //----------Settings Buttons---------------------------------------------
    public void onClickNotifications(View view){
        Intent notificationsIntent = new Intent(this, Notifications.class);
        startActivity(notificationsIntent);
    }
    public void onClickAbout(View view){
        Intent aboutIntent = new Intent(this, About.class);
        startActivity(aboutIntent);
    }
    public void onClickPrivacySettings(View view){
        Intent PrivacySettingsIntent = new Intent(this, PrivacySettings.class);
        startActivity(PrivacySettingsIntent);
    }


}
