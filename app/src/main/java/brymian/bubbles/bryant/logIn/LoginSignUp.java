package brymian.bubbles.bryant.logIn;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import brymian.bubbles.R;

public class LoginSignUp extends Fragment{
    Toolbar toolbar;
    EditText etUsername, etPassword, etConfirmPassword, etFirstName, etLastName, etEmail;
    FloatingActionButton fabDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_sign_up, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.Sign_Up);

        etUsername = (EditText) rootView.findViewById(R.id.etUsername);
        etPassword = (EditText) rootView.findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) rootView.findViewById(R.id.etConfirmPassword);
        etFirstName = (EditText) rootView.findViewById(R.id.etFirstName);
        etLastName = (EditText) rootView.findViewById(R.id.etLastName);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        return rootView;
    }
}
