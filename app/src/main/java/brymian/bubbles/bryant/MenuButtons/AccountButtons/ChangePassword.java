package brymian.bubbles.bryant.MenuButtons.AccountButtons;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.Set;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserCallback;
import brymian.bubbles.damian.nonactivity.UserListCallback;

public class ChangePassword extends FragmentActivity implements View.OnClickListener{
    ImageButton ibMenu;
    EditText etOldPassword, etNewPassword, etNewPasswordAgain;
    Button bChangePassword;
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
            new ServerRequest(ChangePassword.this).getUserData(1, new UserCallback() {
                @Override
                public void done(User user) {
                    String password = user.getPassword();
                    System.out.println("PASSWORD IS: " + password + ", USERNAME IS: " + user.getUsername());
                    if(etOldPassword.getText().toString().equals(password)){
                        etOldPassword.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    }
                    else{
                        etOldPassword.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
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
                etNewPasswordAgain.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                isEquals = true;
            }
            else{
                etNewPasswordAgain.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                isEquals = false;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_account_changepassword);

        ibMenu =(ImageButton) findViewById(R.id.ibMenu);
        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewPasswordAgain = (EditText) findViewById(R.id.etNewPasswordAgain);
        bChangePassword = (Button) findViewById(R.id.bChangePassword);

        ibMenu.setOnClickListener(this);
        bChangePassword.setOnClickListener(this);

        etOldPassword.addTextChangedListener(oldPasswordWatcher);
        etNewPasswordAgain.addTextChangedListener(newPasswordAgainWatcher);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.bChangePassword:
                if(isEquals){
                    new ServerRequest(this).changePassword(1, etNewPasswordAgain.getText().toString(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println("THIS IS FROM onClick(R.id.bChangePassword): " + string);
                            Toast.makeText(ChangePassword.this, "FROM onClick(R.id,bChangePassword): " + string, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(this, "New Password needs to confirm", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


}
