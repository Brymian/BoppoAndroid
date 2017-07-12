package com.brymian.boppo.damian.fragment.Authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.brymian.boppo.R;
import com.brymian.boppo.damian.nonactivity.DialogMessage;
import com.brymian.boppo.damian.nonactivity.ServerRequestMethods;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.objects.User;
import com.brymian.boppo.damian.nonactivity.UserDataLocal;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.VoidCallback;

import static com.brymian.boppo.damian.nonactivity.DialogMessage.showErrorCustom;
import static com.brymian.boppo.damian.nonactivity.DialogMessage.showMessageRegistration;


public class LaunchFragmentFacebook extends Fragment {

    private CallbackManager callbackManager;
    private TextView textDetails;
    private AccessTokenTracker tokenTracker;
    private ProfileTracker profileTracker;
    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            login(profile);
            //startActivity(new Intent(getActivity(), MapActivity.class));
        }

        @Override
        public void onCancel() {
            System.out.println("CANCELED! Whatever this means.");
        }

        @Override
        public void onError(FacebookException exception) {
            System.out.println("ERROR: ");
            exception.printStackTrace();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        setupTokenTracker();
        setupProfileTracker();

        tokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_launch_facebook, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupLoginButton(view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        tokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setupTokenTracker() {
        tokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };
    }

    private void setupProfileTracker() {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            }
        };
    }

    private void setupLoginButton(View view) {
        LoginButton buttonLogin = (LoginButton) view.findViewById(R.id.login_button);
        buttonLogin.setFragment(this);
        buttonLogin.setReadPermissions("user_friends");
        buttonLogin.registerCallback(callbackManager, facebookCallback);
    }

    private User login(final Profile profile) {

        String facebookUid = profile.getId();

        User user = new User();
        user.initUserFacebook(facebookUid);
        System.out.println("FACEBOOK ID: " + user.getFacebookUid());
        new ServerRequestMethods(getActivity()).authUserFacebook(user, new VoidCallback() {
            @Override
            public void done(Void aVoid) {
                UserDataLocal udl = new UserDataLocal(getActivity());
                User user = udl.getUserData();

                // NO SUCH USER
                if (user == null) {
                    DialogMessage.showErrorLoggedIn(getActivity());
                    udl.resetUserData();
                    udl.setLoggedStatus(false);
                }
                // CONNECTION UNSUCCESSFUL
                else if (user.getFacebookUid().equals(getActivity().getString(R.string.null_user))) {
                    DialogMessage.showErrorConnection(getActivity());
                    udl.resetUserData();
                    udl.setLoggedStatus(false);
                }
                else {
                    // FIRST TIME
                    if (user.getUid() == 0) {
                        System.out.println("WE GOT A NEW FACEBOOK USER HERE!");
                        register(profile);
                    }
                    // REGULAR
                    else {
                        System.out.println("NOW REDIRECTED TO BRYANT'S APP.");
                        //startActivity(new Intent(getActivity(), MapsActivityOLD.class));
                    }
                }
            }
        });

        return null;
    }

    private void register(final Profile profile) {

        String facebook_uid = profile.getId();
        String first_name = profile.getFirstName();
        String last_name = profile.getLastName();
        String email = null;

        User user = new User();
        user.setUserFacebookLogin(facebook_uid, first_name, last_name, email);

        new ServerRequestMethods(getActivity()).createUserFacebook(user, new StringCallback()
        {
            @Override
            public void done(String string)
            {
                if (string.equals("USER CREATED SUCCESSFULLY."))
                {
                    System.out.println(string);
                    showMessageRegistration(getActivity());
                    //login(profile_activity);
                }
                else
                {
                    String error = "Unknown error: \n" + string;
                    showErrorCustom(getActivity(), error);
                }
            }
        });

    }
}
