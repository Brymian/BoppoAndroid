package brymian.bubbles.bryant.MenuButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.SettingsButtonTest;

public class Settings extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_settings);
    }

    public void onClickSettingsButtonTest(View view){
        Intent settingsbuttontestIntent = new Intent(this, SettingsButtonTest.class);
        startActivity(settingsbuttontestIntent);
    }


}
