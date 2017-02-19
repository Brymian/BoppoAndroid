package brymian.bubbles.bryant.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;

public class Email extends AppCompatActivity implements View.OnClickListener{
    Toolbar mToolbar;
    EditText etVerifyEmail;
    FloatingActionButton fabDone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Email);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etVerifyEmail = (EditText) findViewById(R.id.etVerifyEmail);
        etVerifyEmail.setHint(R.string.Email);
        etVerifyEmail.setText(SaveSharedPreference.getEmail(this));

        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.fabDone:
                new ServerRequestMethods(this).changeEmail(SaveSharedPreference.getUserUID(this), etVerifyEmail.getText().toString(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("changeEmail", string);
                        if(string.equals("Email changed successfully.")){
                            SaveSharedPreference.clearEmail(Email.this);
                            SaveSharedPreference.setEmail(Email.this, etVerifyEmail.getText().toString());
                        }
                    }
                });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
