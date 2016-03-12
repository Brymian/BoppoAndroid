package brymian.bubbles.bryant.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.damian.fragment.Authenticate.LaunchFragmentFacebook;

/**
 * Created by almanza1112 on 7/2/2015.
 */
public class SyncFacebook extends FragmentActivity implements View.OnClickListener{
    ImageButton ibMenu;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_with_facebook);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_authenticate_facebook, new LaunchFragmentFacebook());
        ft.commit();

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);

        ibMenu.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.ibMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
        }
    }
}
