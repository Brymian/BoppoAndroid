package brymian.bubbles.bryant.MenuButtons.AccountButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import java.util.Set;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserCallback;
import brymian.bubbles.damian.nonactivity.UserListCallback;

public class ChangePassword extends FragmentActivity implements View.OnClickListener{
    ImageButton ibMenu;
    EditText etOldPassword, etNewPassword, etNewPasswordAgain;
    TextWatcher oldPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new ServerRequest(ChangePassword.this).getUserData(1, new UserCallback() {
                @Override
                public void done(User user) {
                    if(etOldPassword.getText().toString().equals(user.getPassword())){
                        ///have a check if it matches, have an X if it doesnt
                    }
                }
            });
        }
    };
    TextWatcher newPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new ServerRequest(ChangePassword.this).getUserData(1, new UserCallback() {
                @Override
                public void done(User user) {
                    if(etNewPassword.getText().toString().equals(user.getPassword())){
                        ///have a X if it matches, have a check if it doesnt

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
            new ServerRequest(ChangePassword.this).getUserData(1, new UserCallback() {
                @Override
                public void done(User user) {
                    if(etNewPassword.getText().toString().equals(etNewPasswordAgain.getText().toString())){
                        //have a check if it matches, a X if it doesnt
                    }
                }
            });
        }
    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_account_changepassword);

        ibMenu =(ImageButton) findViewById(R.id.ibMenu);
        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewPasswordAgain = (EditText) findViewById(R.id.etNewPasswordAgain);

        ibMenu.setOnClickListener(this);

        etOldPassword.addTextChangedListener(oldPasswordWatcher);
        etNewPassword.addTextChangedListener(newPasswordWatcher);
        etNewPasswordAgain.addTextChangedListener(newPasswordAgainWatcher);

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
