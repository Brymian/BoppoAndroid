package brymian.bubbles.bryant.MenuButtons.AccountButtons;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;


public class VerifyEmail extends AppCompatActivity implements View.OnClickListener{
    Toolbar mToolbar;
    EditText etVerifyEmail;
    TextView bVerifyEmail;
    int[] profileUserUID = new int[1];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_email);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("Email");
        mToolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etVerifyEmail = (EditText) findViewById(R.id.etVerifyEmail);
        bVerifyEmail = (TextView) findViewById(R.id.bVerifyEmail);
        bVerifyEmail.setOnClickListener(this);


        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        String userEmail = userPhone.getEmail();
        System.out.println("getEmail(): " + userEmail);
        etVerifyEmail.setText(userEmail);
        setProfileUserUID(userUID);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.bVerifyEmail:
                new ServerRequest(this).changeEmail(getProfileUserUID(), etVerifyEmail.getText().toString(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Toast.makeText(VerifyEmail.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    void setProfileUserUID(int uid){
        profileUserUID[0] = uid;
    }
    int getProfileUserUID(){
        return profileUserUID[0];
    }
}
