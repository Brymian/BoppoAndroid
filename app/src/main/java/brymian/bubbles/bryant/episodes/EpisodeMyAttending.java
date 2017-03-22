package brymian.bubbles.bryant.episodes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;

public class EpisodeMyAttending extends Fragment{
    RecyclerView rvEpisodeMyAttending;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private boolean isLoaded = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode_my_attending, container, false);
        rvEpisodeMyAttending = (RecyclerView) rootView.findViewById(R.id.rvEpisodeMyAttending);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isLoaded){
            getAttendingEpisodes();
            isLoaded = true;
        }
    }

    private void getAttendingEpisodes(){
        new EventRequest(getActivity()).getEventDataByMember(SaveSharedPreference.getUserUID(getActivity()), new StringCallback() {
            @Override
            public void done(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String eventsString = jsonObject.getString("events");
                    JSONArray eventsArray = new JSONArray(eventsString);
                    List<String> episodeTitleAttending = new ArrayList<>();
                    List<String> episodeHostNameAttending = new ArrayList<>();
                    List<Integer> episodeEidAttending = new ArrayList<>();
                    for (int i = 0; i < eventsArray.length(); i++){
                        JSONObject eventsObj = eventsArray.getJSONObject(i);
                        String eventHostString = eventsObj.getString("eventHost");
                        JSONObject eventHostObj = new JSONObject(eventHostString);
                        if (!eventHostObj.getString("uid").equals(String.valueOf(SaveSharedPreference.getUserUID(getActivity())))){
                            episodeTitleAttending.add(eventsObj.getString("eventName"));
                            episodeHostNameAttending.add(eventHostObj.getString("username"));
                            episodeEidAttending.add(Integer.valueOf(eventsObj.getString("eid")));
                        }
                    }
                    adapter = new EpisodeMyAttendingRecyclerAdapter(getActivity(), episodeTitleAttending, episodeHostNameAttending, episodeEidAttending);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvEpisodeMyAttending.setLayoutManager(layoutManager);
                    rvEpisodeMyAttending.setAdapter(adapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
