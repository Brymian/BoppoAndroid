package brymian.bubbles.bryant.episodes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.objects.Event;

public class EpisodeMyHosting extends Fragment{
    RecyclerView rvEpisodeMyHosting;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode_my_hosting, container, false);
        rvEpisodeMyHosting = (RecyclerView) rootView.findViewById(R.id.rvEpisodeMyHosting);
        getHostingEpisodes();
        return rootView;
    }
    private void getHostingEpisodes(){
        new EventRequest(getActivity()).getEventDataByMember(SaveSharedPreference.getUserUID(getActivity()), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                Log.e("Hosting", String.valueOf(eventList.size()));
                if(eventList.size() > 0) {
                    List<String> eventTitleHosting = new ArrayList<>();
                    List<Integer> eventEidHosting = new ArrayList<>();
                    //List<Integer> episodeParticipants = new ArrayList<>();
                    for (Event event : eventList) {
                        if (event.eventHostUid == SaveSharedPreference.getUserUID(getActivity())) {
                            eventTitleHosting.add(event.eventName);
                            eventEidHosting.add(event.eid);
                        }
                    }
                    adapter = new EpisodeMyHostingRecyclerAdapter(getActivity(), eventTitleHosting, eventEidHosting);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvEpisodeMyHosting.setLayoutManager(layoutManager);
                    rvEpisodeMyHosting.setAdapter(adapter);
                }
            }
        });
    }
}
