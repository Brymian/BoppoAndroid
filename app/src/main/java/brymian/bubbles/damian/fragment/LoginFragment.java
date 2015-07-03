package brymian.bubbles.damian.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import brymian.bubbles.R;
import brymian.bubbles.damian.activity.RegisterActivity;
import brymian.bubbles.damian.nonactivity.GetUserCallback;
import brymian.bubbles.damian.nonactivity.ServerRequests;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserStoreLocal;

/**
 * Created by Ziomster on 7/2/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public LoginFragment() {
    }

    /* FRAGMENT SPECIFIC METHODS */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = (EditText) rootView.findViewById(R.id.etUsername);
        etPassword = (EditText) rootView.findViewById(R.id.etPassword);
        bLogin = (Button) rootView.findViewById(R.id.bLogin);
        //tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        ibRegisterLink = (ImageButton) rootView.findViewById(R.id.ibRegisterLink);

        bLogin.setOnClickListener(this);
        //tvRegisterLink.setOnClickListener(this);
        ibRegisterLink.setOnClickListener(this);

        userStoreLocal = new UserStoreLocal(getActivity());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    /* END OF FRAGMENT SPECIFIC METHODS */

    /* CUSTOM METHODS */

    Button bLogin;
    EditText etUsername, etPassword;
    //TextView tvRegisterLink;
    ImageButton ibRegisterLink;
    UserStoreLocal userStoreLocal;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bLogin) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            User user = new User(username, password);

            authenticate(user);
        }
        //if (v.getId() == R.id.tvRegisterLink) {
        if (v.getId() == R.id.ibRegisterLink) {
            startActivity(new Intent(getActivity(), RegisterActivity.class));
        }
    }

    private void authenticate(User user) {
        ServerRequests serverRequest = new ServerRequests(getActivity());
        serverRequest.getUserDataAsyncTask(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(("Incorrect user details."));
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser) {
        userStoreLocal.setUserData(returnedUser);
        userStoreLocal.setUserLoggedStatus(true);

        // startActivity(new Intent(getActivity(), MapsActivity.class));
    }

    /* END OF CUSTOM METHODS */
}
