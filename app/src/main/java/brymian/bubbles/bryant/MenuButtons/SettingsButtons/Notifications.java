package brymian.bubbles.bryant.MenuButtons.SettingsButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;

/**
 * Created by Almanza on 7/6/2015.
 */
public class Notifications extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
    }
    public void onClickMenu(View v){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
    }
}
