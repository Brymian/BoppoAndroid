package brymian.bubbles.bryant.episodes;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import brymian.bubbles.R;

public class EpisodeParticipantsFab extends Fragment implements View.OnClickListener {
    LinearLayout llTransparent;
    RelativeLayout rlAddFriends, rlRemoveFriends;
    FloatingActionButton fabClose;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode_participants_fab, container, false);

        llTransparent = (LinearLayout) rootView.findViewById(R.id.llTransparent);
        llTransparent.setOnClickListener(this);

        rlAddFriends = (RelativeLayout) rootView.findViewById(R.id.rlAddFriends);
        rlAddFriends.setOnClickListener(this);

        rlRemoveFriends = (RelativeLayout) rootView.findViewById(R.id.rlRemoveFriends);
        rlRemoveFriends.setOnClickListener(this);

        fabClose = (FloatingActionButton) rootView.findViewById(R.id.fabClose);
        fabClose.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llTransparent:
                getFragmentManager().popBackStack();
                break;

            case R.id.rlAddFriends:
                startActivity(new Intent(getActivity(), EpisodeParticipantsAddRemove.class).putExtra("purpose", "add"));
                break;

            case R.id.rlRemoveFriends:
                startActivity(new Intent(getActivity(), EpisodeParticipantsAddRemove.class).putExtra("purpose", "remove"));
                break;

            case R.id.fabClose:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
