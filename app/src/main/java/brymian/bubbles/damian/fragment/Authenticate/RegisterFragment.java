package brymian.bubbles.damian.fragment.Authenticate;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.damian.activity.AuthenticateActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;

import static brymian.bubbles.damian.nonactivity.DialogMessage.showErrorCustom;
import static brymian.bubbles.damian.nonactivity.DialogMessage.showMessageRegistration;

/**
 * Created by Ziomster on 7/2/2015.
 */
public class RegisterFragment extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView bRegister;
    EditText etUsername, etPassword, etConfirmPassword, etNamefirst, etNamelast, etEmail;
    SharedPreferences sp;

    TextWatcher usernameTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher passwordTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher confirmPasswordTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher firstLastNameTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher emailTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public RegisterFragment() {
    }

    /*
     * FRAGMENT METHODS
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("Register");
        mToolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etNamefirst = (EditText) findViewById(R.id.etNamefirst);
        etNamelast = (EditText) findViewById(R.id.etNamelast);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bRegister = (TextView) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);

    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.tool_bar);



        etUsername = (EditText) rootView.findViewById(R.id.etUsername);
        etPassword = (EditText) rootView.findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) rootView.findViewById(R.id.etConfirmPassword);
        etNamefirst = (EditText) rootView.findViewById(R.id.etNamefirst);
        etNamelast = (EditText) rootView.findViewById(R.id.etNamelast);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        bRegister = (TextView) rootView.findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);

        return rootView;
    }
    */

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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    /*
     * END OF FRAGMENT METHODS
     */

    /*
     * CUSTOM METHODS
     */
    @Override
    public void onClick(View v) {
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        switch (v.getId()){
            case R.id.bRegister:
                if(confirmPassword.equals(password)){
                    String username = etUsername.getText().toString();
                    String namefirst = etNamefirst.getText().toString();
                    String namelast = etNamelast.getText().toString();
                    String email = etEmail.getText().toString();

                    User user = new User();
                    user.setUserNormalLogin(username, password, namefirst, namelast, email);

                    new ServerRequest(this).createUserNormal(user, new StringCallback() {
                        @Override
                        public void done(String string) {
                            if (string.length() <= 1) {
                                showMessageRegistration(RegisterFragment.this);
                            } else {
                                String error = "";
                                if (string.contains("Duplicate entry")) {
                                    String username = string.split("'")[1];
                                    error = "Username '" + username + "' is already taken.";
                                } else if (string.contains("java.net.SocketTimeoutException")) {
                                    error = "Server is not reachable: it may be offline.";
                                } else {
                                    error = "Unknown error: '" + string + "'";
                                }
                                showErrorCustom(RegisterFragment.this, error);
                            }
                        }
                    });
                }
                else {
                    System.out.println("PASSWORDS DON'T MATCH!!");
                }
                break;
        }

    }
}
