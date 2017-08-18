package com.brymian.boppo.bryant.logIn;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.main.MainActivity;
import com.brymian.boppo.bryant.nonactivity.SaveSharedPreference;
import com.brymian.boppo.damian.nonactivity.DialogMessage;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.VoidCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.FirebaseRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequestMethods;
import com.brymian.boppo.damian.nonactivity.UserDataLocal;
import com.brymian.boppo.damian.objects.User;

import static com.brymian.boppo.damian.nonactivity.Miscellaneous.printLongString;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etUsername, etPassword;
    TextView tvSignUp;
    Button bLogIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SaveSharedPreference.getUserPassword(this).length() != 0){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.login_activity);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bLogIn = (Button) findViewById(R.id.bLogIn);
        bLogIn.setOnClickListener(this);

        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogIn:
                logInAuthenticate();
                break;

            case R.id.tvSignUp:
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                LoginSignUp loginSignUp = new LoginSignUp();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_activity, loginSignUp);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            SaveSharedPreference.clearAll(this);
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void logInAuthenticate(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User();
        user.initUserNormal(username, password);
        new ServerRequestMethods(this).authUserNormal(user, new VoidCallback() {
            @Override
            public void done(Void aVoid) {
                UserDataLocal udl = new UserDataLocal(LoginActivity.this);
                if (!udl.getLoggedStatus()) {
                    DialogMessage.showErrorLoggedIn(LoginActivity.this);
                }
                else {
                    User user = udl.getUserData();
                    udl.setLoggedStatus(true);
                    udl.setUserData(user);

                    SaveSharedPreference.setUsername(LoginActivity.this, user.getUsername());
                    SaveSharedPreference.setUserPassword(LoginActivity.this, etPassword.getText().toString());
                    SaveSharedPreference.setUserUID(LoginActivity.this, user.getUid());
                    SaveSharedPreference.setUserFirstName(LoginActivity.this, user.getFirstName());
                    SaveSharedPreference.setUserLastName(LoginActivity.this, user.getLastName());
                    SaveSharedPreference.setEmail(LoginActivity.this, user.getEmail());
                    SaveSharedPreference.setUserPhoneNumber(LoginActivity.this, user.getPhone());

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    // ADDED BY DAMIAN
                    new FirebaseRequest().addDeviceToFirebaseAndDb(user.getUid(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            printLongString(string);
                        }
                    });
                    //

                    finish();
                }
            }
        });
    }
}
