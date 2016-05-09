package brymian.bubbles.bryant.main;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;

import brymian.bubbles.R;
import brymian.bubbles.bryant.account.Email;


public class MainTabAccount extends Fragment implements View.OnClickListener{
    TextView tvMyProfile, tvMyMap, tvFriends, tvNotifications, tvBlocking, tvLogOut;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_tab_account, container, false);

        tvMyProfile = (TextView) view.findViewById(R.id.tvMyProfile);
        tvMyProfile.setOnClickListener(this);

        tvMyMap = (TextView) view.findViewById(R.id.tvMyMap);
        tvMyMap.setOnClickListener(this);

        tvFriends = (TextView) view.findViewById(R.id.tvFriends);
        tvFriends.setOnClickListener(this);

        tvNotifications = (TextView) view.findViewById(R.id.tvNotifications);
        tvNotifications.setOnClickListener(this);

        tvBlocking = (TextView) view.findViewById(R.id.tvBlocking);
        tvBlocking.setOnClickListener(this);

        tvLogOut = (TextView) view.findViewById(R.id.tvLogOut);
        tvLogOut.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        FragmentManager fm = getActivity().getFragmentManager();

        switch (view.getId()){
            case R.id.tvMyProfile:
                startFragment(fm, R.id.main_tab_account, new Email());

                break;
        }
    }
}
