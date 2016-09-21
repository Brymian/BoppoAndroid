package brymian.bubbles.bryant.episodes;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;

import brymian.bubbles.R;

public class EpisodeParticipants extends Fragment {
    FloatingActionButton fabAddParticipant;
    View rootView;
    Toolbar mToolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.episode_participants, container, false);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Participants);
        mToolbar.setTitleTextColor(Color.BLACK);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
