package brymian.bubbles.bryant.MenuButtons.AccountButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;


public class VerifyEmail extends FragmentActivity implements View.OnClickListener{
    ImageButton ibMenu;
    EditText etVerifyEmail;
    Button bVerifyEmail;
    int[] profileUserUID = new int[1];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_email);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        etVerifyEmail = (EditText) findViewById(R.id.etVerifyEmail);
        bVerifyEmail = (Button) findViewById(R.id.bVerifyEmail);

        ibMenu.setOnClickListener(this);
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
            case R.id.ibMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
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

    void setProfileUserUID(int uid){
        profileUserUID[0] = uid;
    }
    int getProfileUserUID(){
        return profileUserUID[0];
    }
}
