package brymian.bubbles.bryant.account;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.objects.User;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;

public class Password extends AppCompatActivity implements View.OnClickListener{
    Toolbar mToolbar;
    EditText etOldPassword, etNewPassword, etNewPasswordAgain;
    TextView bChangePassword;
    ImageView ivCurrentPassword, ivConfirmNewPassword;
    boolean isEquals;

    TextWatcher oldPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new ServerRequestMethods(Password.this).getUserData(SaveSharedPreference.getUserUID(Password.this), new UserCallback() {
                @Override
                public void done(User user) {
                    String password = user.getPassword();
                    System.out.println("PASSWORD IS: " + password + ", USERNAME IS: " + user.getUsername());
                    if(etOldPassword.getText().toString().equals(password)){
                        ivCurrentPassword.setImageResource(R.mipmap.ic_done_black_24dp);
                    }
                    else{
                        ivCurrentPassword.setImageResource(R.mipmap.ic_close_black_24dp);
                    }
                }
            });
        }
    };

    TextWatcher newPasswordAgainWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (etNewPasswordAgain.getText().toString().equals(etNewPassword.getText().toString())) {
                ivConfirmNewPassword.setImageResource(R.mipmap.ic_done_black_24dp);
                bChangePassword.setVisibility(View.VISIBLE);
                isEquals = true;
            }
            else{
                ivConfirmNewPassword.setImageResource(R.mipmap.ic_close_black_24dp);
                isEquals = false;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Password);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etOldPassword.setHint(R.string.Current_Password);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewPasswordAgain = (EditText) findViewById(R.id.etNewPasswordAgain);
        bChangePassword = (TextView) findViewById(R.id.bChangePassword);
        bChangePassword.setText(R.string.Done);
        ivCurrentPassword = (ImageView) findViewById(R.id.ivCurrentPassword);
        ivConfirmNewPassword = (ImageView) findViewById(R.id.ivConfirmNewPassword);

        bChangePassword.setOnClickListener(this);
        bChangePassword.setVisibility(View.GONE);

        etOldPassword.addTextChangedListener(oldPasswordWatcher);
        etNewPasswordAgain.addTextChangedListener(newPasswordAgainWatcher);
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.bChangePassword:
                if(isEquals){
                    new ServerRequestMethods(this).changePassword(SaveSharedPreference.getUserUID(this), etNewPasswordAgain.getText().toString(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            if(string.equals("Password changed successfully.")){
                                getFragmentManager().popBackStack();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(this, "New Password needs to confirm", Toast.LENGTH_SHORT).show();
                }

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
