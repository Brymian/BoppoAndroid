package brymian.bubbles.bryant;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import brymian.bubbles.R;


public class ProfileActivity extends FragmentActivity implements View.OnClickListener{
    RelativeLayout linearLayout;
    TextView tProfileName;
    ImageButton bMenu, bCamera, bMap, bAddFriend;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        linearLayout = (RelativeLayout) findViewById(R.id.lMainLayout);

        tProfileName = (TextView) findViewById(R.id.tProfileName);

        bAddFriend = (ImageButton) findViewById(R.id.bAddFriend);
        bMenu = (ImageButton) findViewById(R.id.bMenu);
        bCamera = (ImageButton) findViewById(R.id.bCamera);
        bMap = (ImageButton) findViewById(R.id.bMap);

        bMenu.setOnClickListener(this);
        bAddFriend.setOnClickListener(this);
        bCamera.setOnClickListener(this);
        bMap.setOnClickListener(this);

        setBackground();

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bAddFriend:

                break;
            case R.id.bMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.bCamera:
                Intent cameraIntent = new Intent(this, CameraActivity.class);
                startActivity(cameraIntent);
                break;
            case R.id.bMap:
                Intent mapIntent = new Intent(this, MapsActivity.class);
                startActivity(mapIntent);
                break;
        }
    }

    protected void setBackground(){

    }
}
