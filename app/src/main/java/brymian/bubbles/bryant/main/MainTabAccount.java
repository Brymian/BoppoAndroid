package brymian.bubbles.bryant.main;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import brymian.bubbles.R;

public class MainTabAccount extends Fragment{
    public static TextView  tvMyProfile, tvMyEpisodes, tvMyMap, tvProfilePictures, tvFriends,
                            tvNotifications, tvPrivacy,
                            tvPassword, tvEmail, tvPhoneNumber, tvBlocking, tvSyncWithOtherMedia, tvLogOut;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_tab_account, container, false);
        /* Profile */
        tvMyProfile = (TextView) rootView.findViewById(R.id.tvMyProfile);
        tvMyEpisodes = (TextView) rootView.findViewById(R.id.tvMyEpisodes);
        tvMyMap = (TextView) rootView.findViewById(R.id.tvMyMap);
        tvProfilePictures = (TextView) rootView.findViewById(R.id.tvProfilePictures);
        tvFriends = (TextView) rootView.findViewById(R.id.tvFriends);

        /* Settings */
        tvNotifications = (TextView) rootView.findViewById(R.id.tvNotifications);
        tvPrivacy = (TextView) rootView.findViewById(R.id.tvPrivacy);

        /* Account */
        tvPassword = (TextView) rootView.findViewById(R.id.tvPassword);
        tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);
        tvPhoneNumber = (TextView) rootView.findViewById(R.id.tvPhoneNumber);
        tvBlocking = (TextView) rootView.findViewById(R.id.tvBlocking);
        tvSyncWithOtherMedia = (TextView) rootView.findViewById(R.id.tvSyncWithOtherMedia);
        tvLogOut = (TextView) rootView.findViewById(R.id.tvLogOut);
        
        return rootView;
    }
}
