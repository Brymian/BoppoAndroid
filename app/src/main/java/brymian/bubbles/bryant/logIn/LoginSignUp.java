package brymian.bubbles.bryant.logIn;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;

public class LoginSignUp extends Fragment{
    private String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    Toolbar toolbar;
    EditText etUsername, etPassword, etConfirmPassword, etFirstName, etLastName, etEmail;
    FloatingActionButton fabDone;
    TextInputLayout tilUsername, tilPassword, tilConfirmPassword, tilEmail, tilFirstName, tilLastName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_sign_up, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.Sign_Up);

        tilUsername = (TextInputLayout) rootView.findViewById(R.id.tilUsername);
        etUsername = (EditText) rootView.findViewById(R.id.etUsername);

        tilPassword = (TextInputLayout) rootView.findViewById(R.id.tilPassword);
        etPassword = (EditText) rootView.findViewById(R.id.etPassword);

        tilConfirmPassword = (TextInputLayout) rootView.findViewById(R.id.tilConfirmPassword);
        etConfirmPassword = (EditText) rootView.findViewById(R.id.etConfirmPassword);

        tilFirstName = (TextInputLayout) rootView.findViewById(R.id.tilFirstName);
        etFirstName = (EditText) rootView.findViewById(R.id.etFirstName);

        tilLastName = (TextInputLayout) rootView.findViewById(R.id.tilLastName);
        etLastName = (EditText) rootView.findViewById(R.id.etLastName);

        tilEmail = (TextInputLayout) rootView.findViewById(R.id.tilEmail);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);

        fabDone = (FloatingActionButton) rootView.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        return rootView;
    }

    private void register(){
        LoginSignUpPhoneNumber loginSignUpPhoneNumber = new LoginSignUpPhoneNumber();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_activity, loginSignUpPhoneNumber);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (!validatePassword()){
            tilPassword.setError("Must be at least 8 characters");
        }
        else {
            tilPassword.setErrorEnabled(false);
        }

        if (!validateConfirmPassword()){
            tilConfirmPassword.setError("Password doesn't match");
        }
        else {
            tilConfirmPassword.setErrorEnabled(false);
        }

        if (!validateEmail()){
            tilEmail.setError("Invalid email");
        }
        else {
            tilEmail.setErrorEnabled(false);
        }

        if (validatePassword() && validateConfirmPassword() && validateEmail()){
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String namefirst = etFirstName.getText().toString();
                String namelast = etLastName.getText().toString();
                String email = etEmail.getText().toString();

                User user = new User();
                user.setUserNormalLogin(username, password, namefirst, namelast, email);

                new ServerRequestMethods(getActivity()).createUserNormal(user, new StringCallback() {
                    @Override
                    public void done(String string) {
                        if (string.length() <= 1) {
                            /* Successfully created user */

                            // Removing this fragment from stack
                            FragmentManager fm = getFragmentManager();
                            fm.popBackStack();

                            // Transition to next fragment
                            LoginSignUpPhoneNumber loginSignUpPhoneNumber = new LoginSignUpPhoneNumber();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.login_activity, loginSignUpPhoneNumber);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        else {
                            String error;
                            if (string.contains("Duplicate entry")) {
                                String username = string.split("'")[1];
                                tilUsername.setError("Username already taken");
                                error = "Username '" + username + "' is already taken.";
                            }
                            else if (string.contains("java.net.SocketTimeoutException")) {
                                error = "Server is not reachable: it may be offline.";
                            }
                            else {
                                error = "Unknown error: '" + string + "'";
                            }
                            Log.e("error", error);
                        }
                    }
                });
            }
        }

    private boolean validatePassword() {
        return etPassword.length() > 7;
    }

    private boolean validateConfirmPassword(){
        return etPassword.getText().toString().equals(etConfirmPassword.getText().toString());
    }

    private boolean validateEmail() {
        Matcher matcher = pattern.matcher(etEmail.getText().toString());
        return matcher.matches();
    }
}
