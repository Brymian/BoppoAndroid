package brymian.bubbles.bryant.MenuButtons;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.AdvancedButtons.AdvancedButtonTest;
import brymian.bubbles.bryant.MenuButtons.FriendsButtons.FriendsButtonTest;

public class Advanced extends FragmentActivity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_advanced);
    }

    public void onClickAdvancedButtonTest(View view){
        Intent advancedbuttontestIntent = new Intent(this, AdvancedButtonTest.class);
        startActivity(advancedbuttontestIntent);
    }
}
