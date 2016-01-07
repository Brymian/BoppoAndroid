package brymian.bubbles.bryant.MenuButtons.ProfileButtons;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;

/**
 * Created by Almanza on 9/21/2015.
 */
public class Privacy extends FragmentActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    ImageButton ibMenu;
    Switch sMap, sProfilePictures;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_privacy);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);

        sMap = (Switch) findViewById(R.id.MapSwitch);
        sProfilePictures = (Switch) findViewById(R.id.ProfilePicturesSwitch);

        sMap.setClickable(true);
        sProfilePictures.setClickable(true);

        ibMenu.setOnClickListener(this);
        sMap.setOnCheckedChangeListener(this);
        sProfilePictures.setOnCheckedChangeListener(this);

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b){
        switch (compoundButton.getId()){
            case R.id.MapSwitch:
                if(b){
                    new ServerRequest(this).setUserAccountPrivacy(1, "Private", new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(Privacy.this, string, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    new ServerRequest(this).setUserAccountPrivacy(1, "Public", new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(Privacy.this, string, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.ProfilePicturesSwitch:
                if(b){
                    new ServerRequest(this).setUserAccountPrivacy(1, "Private", new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(Privacy.this, string, Toast.LENGTH_SHORT).show();
                            System.out.println("THIS IS FROM ");
                        }
                    });
                }
                else{
                    new ServerRequest(this).setUserAccountPrivacy(1, "Public", new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(Privacy.this, string, Toast.LENGTH_SHORT).show();                        }
                    });
                }
                break;
        }

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
