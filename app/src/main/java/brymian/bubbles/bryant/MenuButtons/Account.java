package brymian.bubbles.bryant.MenuButtons;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangeEmail;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangePassword;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangeProfilePicture;

public class Account extends FragmentActivity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_account);
    }

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

}
