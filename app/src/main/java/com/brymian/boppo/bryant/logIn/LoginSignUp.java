package com.brymian.boppo.bryant.logIn;

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

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.nonactivity.RegularExpressions;
import com.brymian.boppo.bryant.nonactivity.SaveSharedPreference;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequestMethods;
import com.brymian.boppo.damian.objects.User;

public class LoginSignUp extends Fragment{
    private Pattern pattern = Pattern.compile(RegularExpressions.EMAIL_PATTERN);

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
        boolean isUsernameEmpty = etUsername.getText().toString().isEmpty();

        if (isUsernameEmpty){
            tilUsername.setError("Field can't be empty");
        }
        else {
            tilUsername.setErrorEnabled(false);
        }

        if (etPassword.getText().toString().isEmpty()){
            tilPassword.setError("Field can't be empty");
        }
        else {
            if (!validatePassword()){
                tilPassword.setError("Must be at least 8 characters");
            }
            else {
                tilPassword.setErrorEnabled(false);
            }
        }

        if (etConfirmPassword.getText().toString().isEmpty()){
            tilConfirmPassword.setError("Field can't be empty");
        }
        else {
            if (!validateConfirmPassword()){
                tilConfirmPassword.setError("Password doesn't match");
            }
            else {
                tilConfirmPassword.setErrorEnabled(false);
            }
        }

        boolean isFirstNameEmpty = etFirstName.getText().toString().isEmpty();

        if (isFirstNameEmpty){
            tilFirstName.setError("Field can't empty");
        }
        else {
            tilFirstName.setErrorEnabled(false);
        }

        boolean isLastNameEmpty = etLastName.getText().toString().isEmpty();

        if (isLastNameEmpty){
            tilLastName.setError("Field can't empty");
        }
        else {
            tilLastName.setErrorEnabled(false);
        }

        if (etEmail.getText().toString().isEmpty()){
            tilEmail.setError("Field can't be empty");
        }
        else {
            if (!validateEmail()){
                tilEmail.setError("Invalid email");
            }
            else {
                tilEmail.setErrorEnabled(false);
            }
        }

        if (!isUsernameEmpty && validatePassword() && validateConfirmPassword() && !isFirstNameEmpty && !isLastNameEmpty && validateEmail()){
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String namefirst = etFirstName.getText().toString();
                String namelast = etLastName.getText().toString();
                String email = etEmail.getText().toString();

                User user = new User();
                user.setUserNormalLogin(username, password, namefirst, namelast, email);
                user.setPhone("0000000000");

                new ServerRequestMethods(getActivity()).createUserNormal(user, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("string", string);
                        String error = "";
                        if (string.contains("Success:")){
                            /* Successfully created user */
                            String[] arr = string.split(" ");
                            String uid = arr[1];
                            SaveSharedPreference.setUserUID(getActivity(), Integer.valueOf(uid));
                            SaveSharedPreference.setUserPassword(getActivity(), etPassword.getText().toString());
                            SaveSharedPreference.setUsername(getActivity(), etUsername.getText().toString());

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
                        else if (string.contains("Duplicate entry")) {
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
