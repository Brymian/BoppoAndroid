package brymian.bubbles.bryant.MenuButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.About;

public class Settings extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_settings);
    }
    public void onClickMenu(View v){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
    }
    public void onClickAbout(View view){
        Intent aboutIntent = new Intent(this, About.class);
        startActivity(aboutIntent);
    }


}
