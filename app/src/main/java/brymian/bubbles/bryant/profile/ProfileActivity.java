package brymian.bubbles.bryant.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MapsActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;
import brymian.bubbles.damian.nonactivity.UserDataLocal;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar mToolbar;
    int userUID;
    LinearLayout llMain;
    ImageButton ibLeft, ibMiddle, ibRight;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        /* Checking for putExtras() */
        String profile;
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                profile = null;
                uid = 0;
            }
            else {
                profile = extras.getString("profile");
                uid = extras.getInt("uid");
            }
        }
        else {
            profile= savedInstanceState.getString("profile");
            uid = savedInstanceState.getInt("uid");
        }


        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        if (profile.equals("logged in user")) {
            setUID(SaveSharedPreference.getUserUID(this));
            mToolbar.setTitle(SaveSharedPreference.getUserFirstName(this) + " " +
                    SaveSharedPreference.getUserLastName(this));
        }
        else {
            setUID(uid);
            new ServerRequestMethods(this).getUserData(uid, new UserCallback() {
                    @Override
                    public void done(User user) {
                        mToolbar.setTitle(user.getFirstName() + " " + user.getLastName());
                    }
            });
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        llMain = (LinearLayout) findViewById(R.id.llMain);

        ibLeft = (ImageButton) findViewById(R.id.ibLeft); /* left button will be users map */
        ibMiddle = (ImageButton) findViewById(R.id.ibMiddle); /* middle button will handle friend requests */
        ibRight = (ImageButton) findViewById(R.id.ibRight);

        //ibMiddle.setBackgroundResource(R.mipmap.ic_menu_invite);

        ibLeft.setOnClickListener(this);
        ibMiddle.setOnClickListener(this);
        ibRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibLeft:
                startActivity(new Intent(this, MapsActivity.class).putExtra("uid", getUID()));
                break;
            case R.id.ibMiddle:

                break;

            case R.id.ibRight:

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUID(int uid){
        this.userUID = uid;
    }

    private int getUID(){
        return userUID;
    }
}
