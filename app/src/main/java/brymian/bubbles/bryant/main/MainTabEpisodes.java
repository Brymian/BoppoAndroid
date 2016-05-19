package brymian.bubbles.bryant.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.objects.Event;

public class MainTabEpisodes extends Fragment {
    RecyclerView rvLiveInYourNeighborhood, rvLiveMostViews, rvLiveTopRated, rvAllTimeMostViews, rvAllTimeTopRated, rvAllTimeMostLikes, rvAllTimeMostDislikes;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_tab_episodes, container, false);
        rvLiveInYourNeighborhood = (RecyclerView) rootView.findViewById(R.id.rvLiveInYourNeighborhood);
        rvLiveMostViews = (RecyclerView) rootView.findViewById(R.id.rvLiveMostViews);
        rvLiveTopRated = (RecyclerView) rootView.findViewById(R.id.rvLiveTopRated);
        rvAllTimeMostViews = (RecyclerView) rootView.findViewById(R.id.rvAllTimeMostViews);
        rvAllTimeTopRated = (RecyclerView) rootView.findViewById(R.id.rvAllTimeTopRated);
        rvAllTimeMostLikes = (RecyclerView) rootView.findViewById(R.id.rvAllTimeMostLikes);
        rvAllTimeMostDislikes = (RecyclerView) rootView.findViewById(R.id.rvAllTimeMostDislikes);
        setAllTimeMostLikesEpisodes();
        setAllTimeMostDislikes();
        setAllTimeMostViewsEpisodes();
        return rootView;
    }

    private void setAllTimeMostLikesEpisodes(){
        new EventRequest(getActivity()).getEventDataByTopNLikes(5, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                if(eventList.size() > 0) {
                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeHostName = new ArrayList<>();
                    List<Integer> episodeEid = new ArrayList<>();
                    List<String> episodeLikeCount = new ArrayList<>();
                    for (int i = 0; i < eventList.size(); i++) {
                        episodeTitle.add(eventList.get(i).eventName);
                        episodeHostName.add(eventList.get(i).eventHostFirstName + " " + eventList.get(i).eventHostLastName);
                        episodeEid.add(eventList.get(i).eid);
                        episodeLikeCount.add(String.valueOf(eventList.get(i).eventLikeCount));
                    }

                    adapter = new MainTabEpisodesAllTimeMostLikesRecyclerAdapter(getActivity(), episodeTitle, episodeHostName, episodeEid, episodeLikeCount);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvAllTimeMostLikes.setLayoutManager(layoutManager);
                    rvAllTimeMostLikes.setAdapter(adapter);
                }
            }
        });
    }

    private void setAllTimeMostViewsEpisodes(){
        new EventRequest(getActivity()).getEventDataByTopNViews(5, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                if (eventList.size() > 0){
                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeHostName = new ArrayList<>();
                    List<Integer> episodeEid = new ArrayList<>();
                    List<String> episodeViewCount = new ArrayList<>();
                    for (int i = 0; i < eventList.size(); i++) {
                        episodeTitle.add(eventList.get(i).eventName);
                        episodeHostName.add(eventList.get(i).eventHostFirstName + " " + eventList.get(i).eventHostLastName);
                        episodeEid.add(eventList.get(i).eid);
                        episodeViewCount.add(String.valueOf(eventList.get(i).eventViewCount));
                    }

                    adapter = new MainTabEpisodesAllTimeMostViewsRecyclerAdapter(getActivity(), episodeTitle, episodeHostName, episodeEid, episodeViewCount);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvAllTimeMostViews.setLayoutManager(layoutManager);
                    rvAllTimeMostViews.setAdapter(adapter);
                }
            }
        });
    }

    private void setAllTimeMostDislikes(){
        new EventRequest(getActivity()).getEventDataByTopNDislikes(5, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                if (eventList.size() > 0){
                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeHostName = new ArrayList<>();
                    List<Integer> episodeEid = new ArrayList<>();
                    List<String> episodeDislikeCount = new ArrayList<>();
                    for (int i = 0; i < eventList.size(); i++) {
                        episodeTitle.add(eventList.get(i).eventName);
                        episodeHostName.add(eventList.get(i).eventHostFirstName + " " + eventList.get(i).eventHostLastName);
                        episodeEid.add(eventList.get(i).eid);
                        episodeDislikeCount.add(String.valueOf(eventList.get(i).eventDislikeCount));
                    }

                    adapter = new MainTabEpisodesAllTimeMostDislikesRecyclerAdapter(getActivity(), episodeTitle, episodeHostName, episodeEid, episodeDislikeCount);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvAllTimeMostDislikes.setLayoutManager(layoutManager);
                    rvAllTimeMostDislikes.setAdapter(adapter);
                }
            }
        });
    }
}