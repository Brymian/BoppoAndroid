package brymian.bubbles.bryant.MenuButtons.ProfileButtons;

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
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserCallback;
import brymian.bubbles.damian.nonactivity.UserDataLocal;

/**
 * Created by Almanza on 9/21/2015.
 */
public class Privacy extends FragmentActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    ImageButton ibMenu;
    Switch sMap, sProfilePictures;
    int[] profileUserUID = new int[1];
    boolean[] profileUserAccountPrivacy = new boolean[1];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_privacy);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);

        //sMap = (Switch) findViewById(R.id.MapSwitch);
        sProfilePictures = (Switch) findViewById(R.id.ProfilePicturesSwitch);

        //sMap.setClickable(true);
        sProfilePictures.setClickable(true);

        ibMenu.setOnClickListener(this);
        //sMap.setOnCheckedChangeListener(this);
        sProfilePictures.setOnCheckedChangeListener(this);

        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        setProfileUserUID(userUID);
        //System.out.println("getProfileUserAccountPrivacy(): " + getProfileUserAccountPrivacy());
        //sProfilePictures.setChecked(getProfileUserAccountPrivacy());

        new ServerRequest(this).getUserData(userUID, new UserCallback() {
            @Override
            public void done(User user) {
                System.out.println("user.getUserAccountPrivacy(): " + user.getUserAccountPrivacy());
                if (user.getUserAccountPrivacy().equals("Private")) {
                    setProfileUserAccountPrivacy(true);
                    System.out.println("getProfileUserAccountPrivacy(): " + getProfileUserAccountPrivacy());
                    sProfilePictures.setChecked(getProfileUserAccountPrivacy());
                } else if (user.getUserAccountPrivacy().equals("Public")) {
                    setProfileUserAccountPrivacy(false);
                    System.out.println("getProfileUserAccountPrivacy(): " + getProfileUserAccountPrivacy());
                    sProfilePictures.setChecked(getProfileUserAccountPrivacy());
                }

            }
        });

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b){
        switch (compoundButton.getId()){
            case R.id.MapSwitch:
                /**
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
                 **/
                break;
            case R.id.ProfilePicturesSwitch:
                if(b){
                    new ServerRequest(this).setUserAccountPrivacy(getProfileUserUID(), "Private", new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(Privacy.this, string, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    new ServerRequest(this).setUserAccountPrivacy(getProfileUserUID(), "Public", new StringCallback() {
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

    void setProfileUserUID(int uid){
        profileUserUID[0] = uid;
    }
    void setProfileUserAccountPrivacy(boolean input){
        profileUserAccountPrivacy[0] = input;
    }
    int getProfileUserUID(){
        return profileUserUID[0];
    }

    boolean getProfileUserAccountPrivacy(){
        return profileUserAccountPrivacy[0];
    }
}
