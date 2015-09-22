package brymian.bubbles.bryant.MenuButtons.ProfileButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;

/**
 * Created by Almanza on 9/21/2015.
 */
public class Privacy extends FragmentActivity implements View.OnClickListener{
    ImageButton ibMenu;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_privacy);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);

        ibMenu.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
        }
    }
}
