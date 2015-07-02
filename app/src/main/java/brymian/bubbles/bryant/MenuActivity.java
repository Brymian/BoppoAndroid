package brymian.bubbles.bryant;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.*;

public class MenuActivity extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClickSettings(View view){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
    }

    public void onClickLogOut(View view){
        Intent logoutIntent = new Intent(this, LogOut.class);
        startActivity(logoutIntent);
    }
    public void onClickFriends(View view){
        Intent friendsIntent = new Intent(this, Friends.class);
        startActivity(friendsIntent);
    }
    public void onClickAccount(View view){
        Intent accountIntent = new Intent(this, Account.class);
        startActivity(accountIntent);
    }
}
