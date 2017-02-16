package brymian.bubbles.bryant.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.EpisodeMy;
import brymian.bubbles.bryant.friends.FriendsActivity;
import brymian.bubbles.bryant.map.MapsActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.pictures.ProfilePicturesActivity2;
import brymian.bubbles.bryant.profile.ProfileActivity;
import brymian.bubbles.bryant.settings.About;
import brymian.bubbles.bryant.settings.Notifications;
import brymian.bubbles.bryant.settings.Privacy;
import brymian.bubbles.bryant.settings.blocking.Blocking;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Event;
import brymian.bubbles.objects.User;


public class MainTabPersonal extends Fragment implements View.OnClickListener{
    CardView  cvMyProfile, cvMyEpisodes, cvMyMap, cvProfilePictures, cvFriends,
                            cvNotifications, cvPrivacy, cvBlocking, cvAbout,
                            cvPassword, cvEmail, cvPhoneNumber, cvSyncWithOtherMedia, cvLogOut;
    TextView tvUserUsername, tvUserFirstLastName, tvNumberOfEpisodes, tvNumberOfFriends, tvEmail, tvPhoneNumber;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_tab_personal, container, false);
        /* Profile */
        cvMyProfile = (CardView) rootView.findViewById(R.id.cvMyProfile);
        cvMyProfile.setOnClickListener(this);
        tvUserUsername = (TextView) rootView.findViewById(R.id.tvUserUsername);
        tvUserFirstLastName = (TextView) rootView.findViewById(R.id.tvUserFirstLastName);

        cvMyEpisodes = (CardView) rootView.findViewById(R.id.cvMyEpisodes);
        cvMyEpisodes.setOnClickListener(this);
        tvNumberOfEpisodes = (TextView) rootView.findViewById(R.id.tvNumberOfEpisodes);

        cvMyMap = (CardView) rootView.findViewById(R.id.cvMyMap);
        cvMyMap.setOnClickListener(this);

        cvProfilePictures = (CardView) rootView.findViewById(R.id.cvProfilePictures);
        cvProfilePictures.setOnClickListener(this);

        cvFriends = (CardView) rootView.findViewById(R.id.cvFriends);
        cvFriends.setOnClickListener(this);
        tvNumberOfFriends = (TextView) rootView.findViewById(R.id.tvNumberOfFriends);

        /* Settings */
        cvNotifications = (CardView) rootView.findViewById(R.id.cvNotifications);
        cvNotifications.setOnClickListener(this);

        cvPrivacy = (CardView) rootView.findViewById(R.id.cvPrivacy);
        cvPrivacy.setOnClickListener(this);

        cvBlocking = (CardView) rootView.findViewById(R.id.cvBlocking);
        cvBlocking.setOnClickListener(this);

        cvAbout = (CardView) rootView.findViewById(R.id.cvAbout);
        cvAbout.setOnClickListener(this);

        /* Account */
        cvPassword = (CardView) rootView.findViewById(R.id.cvPassword);

        cvEmail = (CardView) rootView.findViewById(R.id.cvEmail);
        tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);

        cvPhoneNumber = (CardView) rootView.findViewById(R.id.cvPhoneNumber);
        tvPhoneNumber = (TextView) rootView.findViewById(R.id.tvPhoneNumber);

        cvSyncWithOtherMedia = (CardView) rootView.findViewById(R.id.cvSyncWithOtherMedia);

        cvLogOut = (CardView) rootView.findViewById(R.id.cvLogOut);

        getPersonalInfo();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /* Profile */
            case R.id.cvMyProfile:
                startActivity(new Intent(getActivity(), ProfileActivity.class).putExtra("profile", "logged in user").putExtra("username", SaveSharedPreference.getUsername(getActivity())).putExtra("uid", SaveSharedPreference.getUserUID(getActivity())));
                break;

            case R.id.cvMyEpisodes:
                startActivity(new Intent(getActivity(), EpisodeMy.class));
                break;

            case R.id.cvMyMap:
                startActivity(new Intent(getActivity(), MapsActivity.class).putExtra("profile", "logged in user"));
                break;

            case R.id.cvProfilePictures:
                startActivity(new Intent(getActivity(), ProfilePicturesActivity2.class));
                break;

            case R.id.cvFriends:
                startActivity(new Intent(getActivity(), FriendsActivity.class).putExtra("profile", "logged in user").putExtra("uid", SaveSharedPreference.getUserUID(getActivity())));
                break;
            /* Settings */
            case R.id.cvNotifications:
                startActivity(new Intent(getActivity(), Notifications.class));
                break;

            case R.id.cvPrivacy:
                break;

            case R.id.cvBlocking:
                break;

            case R.id.cvAbout:
                break;
        }
    }

    private void getPersonalInfo(){
        tvUserUsername.setText(SaveSharedPreference.getUsername(getActivity()));
        tvUserFirstLastName.setText(SaveSharedPreference.getUserFirstName(getActivity()) + " " + SaveSharedPreference.getUserLastName(getActivity()));

        new EventRequest(getActivity()).getEventDataByMember(SaveSharedPreference.getUserUID(getActivity()), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                try{
                    tvNumberOfEpisodes.setText(String.valueOf(eventList.size()));

                }catch (NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        });

        new ServerRequestMethods(getActivity()).getFriends(SaveSharedPreference.getUserUID(getActivity()), new UserListCallback() {
            @Override
            public void done(List<User> users) {
                try{
                    tvNumberOfFriends.setText(String.valueOf(users.size()));
                }catch (NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        });

        //tvEmail.setText();
        //tvPhoneNumber.setText();
    }
}
