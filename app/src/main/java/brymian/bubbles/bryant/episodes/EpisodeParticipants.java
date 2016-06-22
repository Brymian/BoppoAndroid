package brymian.bubbles.bryant.episodes;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;

import brymian.bubbles.R;

public class EpisodeParticipants extends Fragment {

    FloatingActionButton fabAddParticipant;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.episode_participants, container, false);

        setFAM();
        return rootView;
    }

    private void setFAM(){
        fabAddParticipant = (FloatingActionButton) rootView.findViewById(R.id.fabAddParticipant);
        fabAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
