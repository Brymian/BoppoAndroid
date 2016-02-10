package brymian.bubbles.bryant.MenuButtons.AccountButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserCallback;
import brymian.bubbles.damian.nonactivity.UserDataLocal;

public class ChangePassword extends FragmentActivity implements View.OnClickListener{
    ImageButton ibMenu;
    EditText etOldPassword, etNewPassword, etNewPasswordAgain;
    TextView bChangePassword;
    ImageView ivCurrentPassword, ivConfirmNewPassword;
    boolean isEquals;
    int[] profileUserUID = new int[1];

    TextWatcher oldPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new ServerRequest(ChangePassword.this).getUserData(getProfileUserUID(), new UserCallback() {
                @Override
                public void done(User user) {
                    String password = user.getPassword();
                    System.out.println("PASSWORD IS: " + password + ", USERNAME IS: " + user.getUsername());
                    if(etOldPassword.getText().toString().equals(password)){
                        ivCurrentPassword.setImageResource(R.mipmap.green_checkmark_nopadding);
                    }
                    else{
                        ivCurrentPassword.setImageResource(R.mipmap.red_x_nopadding);
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
                ivConfirmNewPassword.setImageResource(R.mipmap.green_checkmark_nopadding);
                bChangePassword.setVisibility(View.VISIBLE);
                isEquals = true;
            }
            else{
                ivConfirmNewPassword.setImageResource(R.mipmap.red_x_nopadding);
                isEquals = false;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        ibMenu =(ImageButton) findViewById(R.id.ibMenu);
        etOldPassword = (EditText) findViewById(R.id.etOldPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewPasswordAgain = (EditText) findViewById(R.id.etNewPasswordAgain);
        bChangePassword = (TextView) findViewById(R.id.bChangePassword);
        ivCurrentPassword = (ImageView) findViewById(R.id.ivCurrentPassword);
        ivConfirmNewPassword = (ImageView) findViewById(R.id.ivConfirmNewPassword);

        ibMenu.setOnClickListener(this);
        bChangePassword.setOnClickListener(this);
        bChangePassword.setVisibility(View.GONE);

        etOldPassword.addTextChangedListener(oldPasswordWatcher);
        etNewPasswordAgain.addTextChangedListener(newPasswordAgainWatcher);

        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        setProfileUserUID(userUID);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.bChangePassword:
                if(isEquals){
                    new ServerRequest(this).changePassword(getProfileUserUID(), etNewPasswordAgain.getText().toString(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println("THIS IS FROM onClick(R.id.bChangePassword): " + string);
                            Toast.makeText(ChangePassword.this, string, Toast.LENGTH_SHORT).show();
                            if(string.equals("Password changed successfully.")){
                                startActivity(new Intent(ChangePassword.this, MenuActivity.class));
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

    void setProfileUserUID(int uid){
        profileUserUID[0] = uid;
    }
    int getProfileUserUID(){
        return  profileUserUID[0];
    }
}
