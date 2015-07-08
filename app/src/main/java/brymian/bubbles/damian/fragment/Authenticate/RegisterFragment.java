package brymian.bubbles.damian.fragment.Authenticate;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.GetUserCallback;
import brymian.bubbles.damian.nonactivity.ServerRequests;
import brymian.bubbles.damian.nonactivity.User;

/**
 * Created by Ziomster on 7/2/2015.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    Button bRegister;
    EditText etUsername, etPassword;
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
        if (v.getId() == R.id.bRegister) {

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            User user = new User(username, password);

            registerUser(user);
        }
    }

    private void registerUser(User user) {

        ServerRequests serverRequest = new ServerRequests(getActivity());
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                showConfirmationMessage();
            }
        });
    }

    private void showConfirmationMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(("Registration successful."));
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                //startActivity(new Intent(getActivity(), LoginActivity.class));
                FragmentManager fm = getActivity().getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.AuthenticateFragment, new LoginFragment());
                ft.commit();
            }
        });
        dialogBuilder.show();
    }
    /*
     * END OF CUSTOM METHODS
     */
}
