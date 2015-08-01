package brymian.bubbles.damian.fragment.Authenticate;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MapsActivity;
import brymian.bubbles.damian.nonactivity.DialogMessage;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;
import brymian.bubbles.damian.nonactivity.VoidCallback;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;

/**
 * Created by Ziomster on 7/2/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    Button bLogin;
    EditText etUsername, etPassword;
    ImageButton ibRegisterLink;
    UserDataLocal udl;

    public LoginFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = (EditText) rootView.findViewById(R.id.etUsername);
        etPassword = (EditText) rootView.findViewById(R.id.etPassword);
        bLogin = (Button) rootView.findViewById(R.id.bLogin);
        ibRegisterLink = (ImageButton) rootView.findViewById(R.id.ibRegisterLink);

        bLogin.setOnClickListener(this);
        ibRegisterLink.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {

        FragmentManager fm = getActivity().getFragmentManager();

        if (v.getId() == R.id.bLogin) {

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            User user = new User();
            user.initUserNormal(username, password);
            new ServerRequest(getActivity()).authUserNormal(user, new VoidCallback() {
                @Override
                public void done(Void aVoid) {
                    UserDataLocal udl = new UserDataLocal(getActivity());
                    User user = udl.getUserData();
                    if (user == null) {
                        DialogMessage.showErrorLoggedIn(getActivity());
                    } else if (user.username() == getActivity().getString(R.string.null_user)) {
                        DialogMessage.showErrorConnection(getActivity());
                    } else {
                        udl.setLoggedStatus(true);
                        udl.setUserData(user);
                        startActivity(new Intent(getActivity(), MapsActivity.class));
                        System.out.println("NOW REDIRECTED TO BRYANT'S APP.");
                    }
                }
            });
        }
        if (v.getId() == R.id.ibRegisterLink) {
            startFragment(fm, R.id.fragment_authenticate, new RegisterFragment());
        }
    }
}
