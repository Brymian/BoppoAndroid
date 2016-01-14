package brymian.bubbles.damian.fragment.Authenticate;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;

import static brymian.bubbles.damian.nonactivity.DialogMessage.showErrorCustom;
import static brymian.bubbles.damian.nonactivity.DialogMessage.showMessageRegistration;

/**
 * Created by Ziomster on 7/2/2015.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    Button bRegister;
    EditText etUsername, etPassword, etConfirmPassword, etNamefirst, etNamelast, etEmail;
    SharedPreferences sp;

    public RegisterFragment() {
    }

    /*
     * FRAGMENT METHODS
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        etUsername = (EditText) rootView.findViewById(R.id.etUsername);
        etPassword = (EditText) rootView.findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) rootView.findViewById(R.id.etConfirmPassword);
        etNamefirst = (EditText) rootView.findViewById(R.id.etNamefirst);
        etNamelast = (EditText) rootView.findViewById(R.id.etNamelast);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        bRegister = (Button) rootView.findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);

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

        if (v.getId() == R.id.bRegister) {
            if(confirmPassword.equals(password)){
                String username = etUsername.getText().toString();
                String namefirst = etNamefirst.getText().toString();
                String namelast = etNamelast.getText().toString();
                String email = etEmail.getText().toString();

                User user = new User();
                user.setUserNormalLogin(username, password, namefirst, namelast, email);

                new ServerRequest(getActivity()).createUserNormal(user, new StringCallback() {
                    @Override
                    public void done(String string) {
                        if (string.length() <= 1) {
                            showMessageRegistration(getActivity());
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
                            showErrorCustom(getActivity(), error);
                        }
                    }
                });
            }
            else {
                System.out.println("PASSWORDS DON'T MATCH!!");
            }
        }
    }
}
