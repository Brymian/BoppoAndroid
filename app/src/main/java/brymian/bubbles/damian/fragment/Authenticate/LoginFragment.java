package brymian.bubbles.damian.fragment.Authenticate;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.main.MainActivity;
import brymian.bubbles.damian.nonactivity.DialogMessage;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.VoidCallback;

/**
 * Created by Ziomster on 7/2/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    TextView bLogin;
    EditText etUsername, etPassword;
    TextView ibRegisterLink;
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
        bLogin = (TextView) rootView.findViewById(R.id.bLogin);
        ibRegisterLink = (TextView) rootView.findViewById(R.id.ibRegisterLink);

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
            new ServerRequestMethods(getActivity()).authUserNormal(user, new VoidCallback() {
                @Override
                public void done(Void aVoid) {
                    UserDataLocal udl = new UserDataLocal(getActivity());
                    //User user = udl.getUserData();
                    if (!udl.getLoggedStatus()) {
                        DialogMessage.showErrorLoggedIn(getActivity());
                    }
                    /*
                    else if (user.getUsername() == getActivity().getString(R.string.null_user))
                    {
                        DialogMessage.showErrorConnection(getActivity());
                    }
                    */
                    else {
                        User user = udl.getUserData();
                        udl.setLoggedStatus(true);
                        udl.setUserData(user);
                        System.out.println("PASSWORD CHECK: " + user.getPassword());
                        System.out.println("NOW REDIRECTED TO BRYANT'S APP.");

                        SaveSharedPreference.setUsername(getActivity(), etUsername.getText().toString());
                        SaveSharedPreference.setUserPassword(getActivity(), etPassword.getText().toString());
                        SaveSharedPreference.setUserUID(getActivity(), user.getUid());
                        SaveSharedPreference.setUserFirstName(getActivity(), user.getFirstName());
                        SaveSharedPreference.setUserLastName(getActivity(), user.getLastName());

                        User loggedInUser = udl.getUserData();
                        int loggedInUserUID = loggedInUser.getUid();

                        String FILENAME = "login_info.txt";
                        //String login_info = etUsername.getText().toString();
                        String login_info = etUsername.getText().toString();


                        try {
                            FileOutputStream fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
                            fos.write(login_info.getBytes());
                            fos.close();
                        }catch(FileNotFoundException fnfe){
                            fnfe.printStackTrace();
                            System.out.println("FileNowFoundException thrown.");
                        }
                        catch (IOException ioe) {
                            ioe.printStackTrace();
                            System.out.println("IOException thrown.");
                        }


                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                }
            });
        }
        if (v.getId() == R.id.ibRegisterLink) {
            //startFragment(fm, R.id.fragment_authenticate, new RegisterFragment());
            startActivity(new Intent(getActivity(), RegisterFragment.class));
        }
    }
}
