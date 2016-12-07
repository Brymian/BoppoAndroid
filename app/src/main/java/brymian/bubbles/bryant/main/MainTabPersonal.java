package brymian.bubbles.bryant.main;

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
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Event;
import brymian.bubbles.objects.User;

public class MainTabPersonal extends Fragment{
    public static CardView  cvMyProfile, cvMyEpisodes, cvMyMap, cvProfilePictures, cvFriends,
                            cvNotifications, cvPrivacy, cvBlocking, cvAbout,
                            cvPassword, cvEmail, cvPhoneNumber, cvSyncWithOtherMedia, cvLogOut;
    TextView tvUserUsername, tvUserFirstLastName, tvNumberOfEpisodes, tvNumberOfFriends, tvEmail, tvPhoneNumber;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_tab_personal, container, false);
        /* Profile */
        cvMyProfile = (CardView) rootView.findViewById(R.id.cvMyProfile);
        tvUserUsername = (TextView) rootView.findViewById(R.id.tvUserUsername);
        tvUserFirstLastName = (TextView) rootView.findViewById(R.id.tvUserFirstLastName);

        cvMyEpisodes = (CardView) rootView.findViewById(R.id.cvMyEpisodes);
        tvNumberOfEpisodes = (TextView) rootView.findViewById(R.id.tvNumberOfEpisodes);

        cvMyMap = (CardView) rootView.findViewById(R.id.cvMyMap);

        cvProfilePictures = (CardView) rootView.findViewById(R.id.cvProfilePictures);

        cvFriends = (CardView) rootView.findViewById(R.id.cvFriends);
        tvNumberOfFriends = (TextView) rootView.findViewById(R.id.tvNumberOfFriends);

        /* Settings */
        cvNotifications = (CardView) rootView.findViewById(R.id.cvNotifications);
        cvPrivacy = (CardView) rootView.findViewById(R.id.cvPrivacy);
        cvBlocking = (CardView) rootView.findViewById(R.id.cvBlocking);
        cvAbout = (CardView) rootView.findViewById(R.id.cvAbout);

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

    private void getPersonalInfo(){
        tvUserUsername.setText(SaveSharedPreference.getUsername(getActivity()));
        tvUserFirstLastName.setText(SaveSharedPreference.getUserFirstName(getActivity()) + " " + SaveSharedPreference.getUserLastName(getActivity()));

        new EventRequest(getActivity()).getEventDataByMember(SaveSharedPreference.getUserUID(getActivity()), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                tvNumberOfEpisodes.setText(String.valueOf(eventList.size()));
            }
        });

        new ServerRequestMethods(getActivity()).getFriends(SaveSharedPreference.getUserUID(getActivity()), new UserListCallback() {
            @Override
            public void done(List<User> users) {
                tvNumberOfFriends.setText(String.valueOf(users.size()));
            }
        });

        //tvEmail.setText();
        //tvPhoneNumber.setText();
    }
}
