package brymian.bubbles.bryant.logIn;

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

import brymian.bubbles.R;
import brymian.bubbles.bryant.main.MainActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.DialogMessage;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.VoidCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.UserDataLocal;
import brymian.bubbles.objects.User;

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
                    finish();
                }
            }
        });
    }
}
