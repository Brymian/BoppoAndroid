package brymian.bubbles.bryant.MenuButtons.SettingsButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.PrivacySettingsButtons.Blocking;

public class PrivacySettings extends FragmentActivity{
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_settings_privacy);
    }
    // This button returns to the MenuActivity---ImageButton
    public void onClickMenu(View v){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
    }
    //----------Privacy Buttons
    public void onClickBlocking(View v){
        Intent blockingIntent = new Intent(this, Blocking.class);
        startActivity(blockingIntent);
    }
    public void onClickWhoCan(View v){
        Intent Intent = new Intent(this, Blocking.class);
        startActivity(Intent);
    }


}
