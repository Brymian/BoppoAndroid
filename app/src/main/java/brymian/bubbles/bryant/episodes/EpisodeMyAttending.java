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

public class EpisodeMyAttending extends Fragment{
    RecyclerView rvEpisodeMyAttending;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode_my_attending, container, false);
        rvEpisodeMyAttending = (RecyclerView) rootView.findViewById(R.id.rvEpisodeMyAttending);
        getAttendingEpisodes();
        return rootView;
    }

    private void getAttendingEpisodes(){
        new EventRequest(getActivity()).getEventDataByMember(SaveSharedPreference.getUserUID(getActivity()), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                if(eventList.size() > 0) {
                    List<String> episodeTitleAttending = new ArrayList<>();
                    List<String> episodeHostNameAttending = new ArrayList<>();
                    List<Integer> episodeEidAttending = new ArrayList<>();
                    for (Event event : eventList) {
                        if (event.eventHostUid != SaveSharedPreference.getUserUID(getActivity())) {
                            episodeTitleAttending.add(event.eventName);
                            episodeHostNameAttending.add(event.eventHostFirstName + " " + event.eventHostLastName);
                            episodeEidAttending.add(event.eid);
                            Log.e("Attending title event", event.eventName);
                        }
                    }

                    adapter = new EpisodeAttendingRecyclerAdapter(getActivity(), episodeTitleAttending, episodeHostNameAttending, episodeEidAttending);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvEpisodeMyAttending.setLayoutManager(layoutManager);
                    rvEpisodeMyAttending.setAdapter(adapter);
                }
                else {
                    Log.e("Attending Episodes", "Attending 0 events.");
                }
            }
        });
    }
}
