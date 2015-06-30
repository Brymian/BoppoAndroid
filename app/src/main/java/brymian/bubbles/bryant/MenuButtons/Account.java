package brymian.bubbles.bryant.MenuButtons;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.AccountButtonTest;

public class Account extends FragmentActivity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_account);
    }

    public void onClickAccountTest(View view){
        Intent accountbuttontestIntent = new Intent(this, AccountButtonTest.class);
        startActivity(accountbuttontestIntent);
    }


}
