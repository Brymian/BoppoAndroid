package com.brymian.boppo.bryant.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.account.Email;
import com.brymian.boppo.bryant.account.Password;
import com.brymian.boppo.bryant.account.PhoneNumber;
import com.brymian.boppo.bryant.episodes.EpisodeMy2;
import com.brymian.boppo.bryant.friends.Friends;
import com.brymian.boppo.bryant.logIn.LoginActivity;
import com.brymian.boppo.bryant.map.MapActivity;
import com.brymian.boppo.bryant.nonactivity.SaveSharedPreference;
import com.brymian.boppo.bryant.profile.ProfileActivity;
import com.brymian.boppo.bryant.settings.Notifications;
import com.brymian.boppo.bryant.settings.Privacy;
import com.brymian.boppo.bryant.settings.blocking.Blocking;
import com.brymian.boppo.damian.activity.TESTActivity;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.UserImageRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequest.UserRequest;
import static com.facebook.FacebookSdk.getApplicationContext;


public class MainTabPersonal extends Fragment implements View.OnClickListener{
    RelativeLayout rlMyProfile, rlMyEpisodes, rlMyMap, rlMyPhotos, rlMyFriends, rlNotifications, rlPrivacy, rlBlocking, rlAbout, rlPassword, rlEmail, rlPhoneNumber, rlSyncWithOtherMedia, rlLogOut;
    TextView tvUserUsername, tvUserFirstLastName, tvEpisodesNum, tvFriendsNum, tvPhotosNum, tvEmail, tvPhoneNumber;
    ImageView ivProfilePicture;
    boolean isPersonalLoaded = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_tab_personal, container, false);
        /* Profile */
        rlMyProfile = (RelativeLayout) rootView.findViewById(R.id.rlMyProfile);
        rlMyProfile.setOnClickListener(this);
        ivProfilePicture = (ImageView) rootView.findViewById(R.id.ivProfilePicture);
        tvUserUsername = (TextView) rootView.findViewById(R.id.tvUserUsername);
        tvUserFirstLastName = (TextView) rootView.findViewById(R.id.tvUserFirstLastName);

        rlMyEpisodes = (RelativeLayout) rootView.findViewById(R.id.rlMyEpisodes);
        rlMyEpisodes.setOnClickListener(this);
        tvEpisodesNum = (TextView) rootView.findViewById(R.id.tvNumberOfEpisodes);

        rlMyMap = (RelativeLayout) rootView.findViewById(R.id.rlMyMap);
        rlMyMap.setOnClickListener(this);

        rlMyPhotos = (RelativeLayout) rootView.findViewById(R.id.rlMyPhotos);
        rlMyPhotos.setOnClickListener(this);
        tvPhotosNum = (TextView) rootView.findViewById(R.id.tvPhotosNum);

        rlMyFriends = (RelativeLayout) rootView.findViewById(R.id.rlMyFriends);
        rlMyFriends.setOnClickListener(this);
        tvFriendsNum = (TextView) rootView.findViewById(R.id.tvNumberOfFriends);

        /* Settings */
        rlNotifications = (RelativeLayout) rootView.findViewById(R.id.rlNotifications);
        rlNotifications.setOnClickListener(this);

        rlPrivacy = (RelativeLayout) rootView.findViewById(R.id.rlPrivacy);
        rlPrivacy.setOnClickListener(this);

        rlBlocking = (RelativeLayout) rootView.findViewById(R.id.rlBlocking);
        rlBlocking.setOnClickListener(this);

        rlAbout = (RelativeLayout) rootView.findViewById(R.id.rlAbout);
        rlAbout.setOnClickListener(this);

        /* Account */
        rlPassword = (RelativeLayout) rootView.findViewById(R.id.rlPassword);
        rlPassword.setOnClickListener(this);

        rlEmail = (RelativeLayout) rootView.findViewById(R.id.rlEmail);
        rlEmail.setOnClickListener(this);
        tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);

        rlPhoneNumber = (RelativeLayout) rootView.findViewById(R.id.rlPhoneNumber);
        rlPhoneNumber.setOnClickListener(this);
        tvPhoneNumber = (TextView) rootView.findViewById(R.id.tvPhoneNumber);

        rlSyncWithOtherMedia = (RelativeLayout) rootView.findViewById(R.id.rlSyncWithOtherMedia);

        final TextView tvDamiansTestActivity = (TextView) rootView.findViewById(R.id.tvDamiansTestActivity);
        if (SaveSharedPreference.getUserUID(getActivity()) == 1){
            tvDamiansTestActivity.setVisibility(View.VISIBLE);
        }
        tvDamiansTestActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TESTActivity.class));
            }
        });

        rlLogOut = (RelativeLayout) rootView.findViewById(R.id.rlLogOut);
        rlLogOut.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isPersonalLoaded){
            getPersonalInfo();
            isPersonalLoaded = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /* Profile */
            case R.id.rlMyProfile:
                /*
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    View statusBar = getActivity().findViewById(android.R.id.statusBarBackground);
                    View navigationBar = getActivity().findViewById(android.R.id.navigationBarBackground);
                    View image = getActivity().findViewById(R.id.ivProfilePicture);

                    List<Pair<View, String>> pairs = new ArrayList<>();
                    pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
                    pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
                    pairs.add(Pair.create(image, image.getTransitionName()));

                    Bundle options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs.toArray(new Pair[pairs.size()])).toBundle();
                    startActivity(new Intent(getContext(), ProfileActivity.class).putExtra("uid", SaveSharedPreference.getUserUID(getActivity())), options);
                }
                else {
                    startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("uid", SaveSharedPreference.getUserUID(getActivity())));
                }*/
                startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("uid", SaveSharedPreference.getUserUID(getActivity())));

                break;

            case R.id.rlMyEpisodes:
                EpisodeMy2 episodeMy2 = new EpisodeMy2();
                Bundle b = new Bundle();
                b.putInt("uid", SaveSharedPreference.getUserUID(getActivity()));
                b.putString("from", "maintab");
                episodeMy2.setArguments(b);
                startFragment(episodeMy2);
                break;

            case R.id.rlMyMap:
                startActivity(new Intent(getActivity(), MapActivity.class).putExtra("profile", "logged in user"));
                break;

            case R.id.rlMyPhotos:
                break;

            case R.id.rlMyFriends:
                Friends friends = new Friends();
                Bundle bundle = new Bundle();
                bundle.putInt("uid", SaveSharedPreference.getUserUID(getActivity()));
                bundle.putString("from", "maintab");
                friends.setArguments(bundle);
                startFragment(friends);
                break;
            /* Settings */
            case R.id.rlNotifications:
                startActivity(new Intent(getActivity(), Notifications.class));
                break;

            case R.id.rlPrivacy:
                startActivity(new Intent(getActivity(), Privacy.class));
                break;

            case R.id.rlBlocking:
                startFragment(new Blocking());
                break;

            case R.id.rlAbout:
                break;
            /* Account */
            case R.id.rlPassword:
                startFragment(new Password());
                break;

            case R.id.rlEmail:
                startFragment(new Email());
                break;

            case R.id.rlPhoneNumber:
                startFragment(new PhoneNumber());
                break;

            case R.id.rlSyncWithOtherMedia:

                break;

            case R.id.rlLogOut:
                logOut();
                break;
        }
    }

    private void startFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void getPersonalInfo(){
        tvUserUsername.setText(SaveSharedPreference.getUsername(getActivity()));
        tvUserFirstLastName.setText(SaveSharedPreference.getUserFirstName(getActivity()) + " " + SaveSharedPreference.getUserLastName(getActivity()));
        new UserImageRequest(getActivity()).getImagesByUid(SaveSharedPreference.getUserUID(getActivity()), true, new StringCallback() {
                @Override
                public void done(String string) {
                    try{
                        JSONObject jsonObject = new JSONObject(string);
                        String images = jsonObject.getString("images");
                        JSONArray jsonArray = new JSONArray(images);
                        if (jsonArray.length() > 0){
                            JSONObject imageObj = jsonArray.getJSONObject(0);
                            String userImagePath = imageObj.getString("userImageThumbnailPath");
                            Picasso.with(getActivity()).load(userImagePath).into(ivProfilePicture);
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            });

        new UserRequest(getActivity()).getUserProfileData(SaveSharedPreference.getUserUID(getActivity()), new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    JSONObject jsonObject = new JSONObject(string);
                    String user = jsonObject.getString("user");
                    JSONObject userObj = new JSONObject(user);
                    tvFriendsNum.setText(userObj.getString("countFriends"));
                    tvEpisodesNum.setText(userObj.getString("countJoinedEvents"));
                    tvPhotosNum.setText(userObj.getString("countImages"));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });

        tvEmail.setText(SaveSharedPreference.getEmail(getActivity()));
        tvPhoneNumber.setText(SaveSharedPreference.getUserPhoneNumber(getActivity()));
    }

    private void logOut() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.main_tab_personal_logout_alertdialog, null);

        Button bCancel = (Button) alertLayout.findViewById(R.id.bCancel);
        Button bYes = (Button) alertLayout.findViewById(R.id.bYes);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);

        final AlertDialog dialog = alert.create();

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SaveSharedPreference.clearAll(getApplicationContext());
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
