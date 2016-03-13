package brymian.bubbles.bryant.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;

/**
 * Created by Almanza on 9/21/2015.
 */
public class Privacy extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    Switch sMap, sProfilePictures;
    Toolbar mToolbar;
    int[] profileUserUID = new int[1];
    boolean[] profileUserAccountPrivacy = new boolean[1];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_privacy);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Privacy);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //sMap = (Switch) findViewById(R.id.MapSwitch);
        sProfilePictures = (Switch) findViewById(R.id.ProfilePicturesSwitch);

        //sMap.setClickable(true);
        sProfilePictures.setClickable(true);
        //sMap.setOnCheckedChangeListener(this);
        sProfilePictures.setOnCheckedChangeListener(this);

        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        setProfileUserUID(userUID);

        /**
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
         **/

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b){
        switch (compoundButton.getId()){
            case R.id.MapSwitch:

                break;
            case R.id.ProfilePicturesSwitch:
                if(b){

                    /**
                    new ServerRequest(this).setUserAccountPrivacy(getProfileUserUID(), "Private", new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(Privacy.this, string, Toast.LENGTH_SHORT).show();
                        }
                    });

                     **/
                }
                else{
                    /**
                    new ServerRequestMethods(this).setUserAccountPrivacy(getProfileUserUID(), "Public", new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(Privacy.this, string, Toast.LENGTH_SHORT).show();                        }
                    });
                     **/
                }
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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