package brymian.bubbles.bryant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import brymian.bubbles.R;


public class FriendsList extends FragmentActivity implements View.OnClickListener {
    ImageButton ibMenu;
    TextView tUsersName;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendslist);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        tUsersName = (TextView) findViewById(R.id.tUsersName);

        ibMenu.setOnClickListener(this);
        tUsersName.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
        }
    }
}
