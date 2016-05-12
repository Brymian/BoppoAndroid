package brymian.bubbles.bryant.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.EpisodeTop;
import brymian.bubbles.bryant.episodes.EpisodeMy;

public class MainTabEpisodes extends Fragment implements View.OnClickListener{

    TextView tvMoreTopEvents, tvYourEvents;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_tab_episodes, container, false);

        tvMoreTopEvents = (TextView) rootView.findViewById(R.id.tvMoreTopEvents);
        tvYourEvents = (TextView) rootView.findViewById(R.id.tvYourEvents);

        tvMoreTopEvents.setOnClickListener(this);
        tvYourEvents.setOnClickListener(this);
        return rootView;
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvMoreTopEvents:
                startActivity(new Intent(getActivity(), EpisodeTop.class));
                break;

            case R.id.tvYourEvents:
                startActivity(new Intent(getActivity(), EpisodeMy.class));
                break;
        }

    }
}