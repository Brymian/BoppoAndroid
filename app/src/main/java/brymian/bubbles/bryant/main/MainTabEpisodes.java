package brymian.bubbles.bryant.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.objects.Event;

public class MainTabEpisodes extends Fragment implements View.OnClickListener {

    TextView    tvLiveInYourNeighborhoodEpisode1, tvLiveInYourNeighborhoodEpisode2,
                tvLiveInYourNeighborhoodEpisode3,
                tvLiveMostViewsEpisode1, tvLiveMostViewsEpisode2, tvLiveMostViewsEpisode3,
                tvLiveTopRatedEpisode1, tvLiveTopRatedEpisode2, tvLiveTopRatedEpisode3,
                tvAllTimeMostViewsEpisode1, tvAllTimeMostViewsEpisode2, tvAllTimeMostViewsEpisode3,
                tvAllTimeTopRatedEpisode1, tvAllTimeTopRatedEpisode2, tvAllTimeTopRatedEpisode3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_tab_episodes, container, false);
        tvLiveInYourNeighborhoodEpisode1 = (TextView) rootView.findViewById(R.id.tvLiveInYourNeighborhoodEpisode1);
        tvLiveInYourNeighborhoodEpisode1.setOnClickListener(this);

        tvLiveInYourNeighborhoodEpisode2 = (TextView) rootView.findViewById(R.id.tvLiveInYourNeighborhoodEpisode2);
        tvLiveInYourNeighborhoodEpisode2.setOnClickListener(this);

        tvLiveInYourNeighborhoodEpisode3 = (TextView) rootView.findViewById(R.id.tvLiveInYourNeighborhoodEpisode3);
        tvLiveInYourNeighborhoodEpisode3.setOnClickListener(this);

        tvLiveMostViewsEpisode1 = (TextView) rootView.findViewById(R.id.tvLiveMostViewsEpisode1);
        tvLiveMostViewsEpisode1.setOnClickListener(this);

        tvLiveMostViewsEpisode2 = (TextView) rootView.findViewById(R.id.tvLiveMostViewsEpisode2);
        tvLiveMostViewsEpisode2.setOnClickListener(this);

        tvLiveMostViewsEpisode3 = (TextView) rootView.findViewById(R.id.tvLiveMostViewsEpisode3);
        tvLiveMostViewsEpisode3.setOnClickListener(this);

        tvLiveTopRatedEpisode1 = (TextView) rootView.findViewById(R.id.tvLiveTopRatedEpisode1);
        tvLiveTopRatedEpisode1.setOnClickListener(this);

        tvLiveTopRatedEpisode2 = (TextView) rootView.findViewById(R.id.tvLiveTopRatedEpisode2);
        tvLiveTopRatedEpisode2.setOnClickListener(this);

        tvLiveTopRatedEpisode3 = (TextView) rootView.findViewById(R.id.tvLiveTopRatedEpisode3);
        tvLiveTopRatedEpisode3.setOnClickListener(this);

        tvAllTimeMostViewsEpisode1 = (TextView) rootView.findViewById(R.id.tvAllTimeMostViewsEpisode1);
        tvAllTimeMostViewsEpisode1.setOnClickListener(this);

        tvAllTimeMostViewsEpisode2 = (TextView) rootView.findViewById(R.id.tvAllTimeMostViewsEpisode2);
        tvAllTimeMostViewsEpisode2.setOnClickListener(this);

        tvAllTimeMostViewsEpisode3 = (TextView) rootView.findViewById(R.id.tvAllTimeMostViewsEpisode3);
        tvAllTimeMostViewsEpisode3.setOnClickListener(this);

        tvAllTimeTopRatedEpisode1 = (TextView) rootView.findViewById(R.id.tvAllTimeTopRatedEpisode1);
        tvAllTimeTopRatedEpisode1.setOnClickListener(this);

        tvAllTimeTopRatedEpisode2 = (TextView) rootView.findViewById(R.id.tvAllTimeTopRatedEpisode2);
        tvAllTimeTopRatedEpisode2.setOnClickListener(this);

        tvAllTimeTopRatedEpisode3 = (TextView) rootView.findViewById(R.id.tvAllTimeTopRatedEpisode3);
        tvAllTimeTopRatedEpisode3.setOnClickListener(this);

        //setMostViewsEpisodes();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvLiveInYourNeighborhoodEpisode1:

                break;
            case R.id.tvLiveInYourNeighborhoodEpisode2:

                break;
            case R.id.tvLiveInYourNeighborhoodEpisode3:

                break;
            case R.id.tvLiveMostViewsEpisode1:

                break;
            case R.id.tvLiveMostViewsEpisode2:

                break;
            case R.id.tvLiveMostViewsEpisode3:

                break;
            case R.id.tvLiveTopRatedEpisode1:

                break;
            case R.id.tvLiveTopRatedEpisode2:

                break;
            case R.id.tvLiveTopRatedEpisode3:

                break;
            case R.id.tvAllTimeMostViewsEpisode1:

                break;
            case R.id.tvAllTimeMostViewsEpisode2:

                break;
            case R.id.tvAllTimeMostViewsEpisode3:

                break;
            case R.id.tvAllTimeTopRatedEpisode1:

                break;
            case R.id.tvAllTimeTopRatedEpisode2:

                break;
            case R.id.tvAllTimeTopRatedEpisode3:

                break;
        }
    }

    private void setMostViewsEpisodes(){
        new EventRequest(getActivity()).getEventDataByTopNLikes(3, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                for (int i = 0; i < eventList.size(); i++){
                    System.out.println("Event name: " + eventList.get(i).eventName);
                }
            }
        });
    }
}