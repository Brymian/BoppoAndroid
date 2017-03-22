package brymian.bubbles.bryant.episodes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class EpisodeMyHosting extends Fragment{
    RecyclerView rvEpisodeMyHosting;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode_my_hosting, container, false);
        rvEpisodeMyHosting = (RecyclerView) rootView.findViewById(R.id.rvEpisodeMyHosting);
        getHostingEpisodes();
        return rootView;
    }

    private void getHostingEpisodes(){
        new EventRequest(getActivity()).getEventDataByMember(SaveSharedPreference.getUserUID(getActivity()), new StringCallback() {
            @Override
            public void done(String string) {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String eventsString = jsonObject.getString("events");
                    JSONArray eventsArray = new JSONArray(eventsString);
                    List<String> eventTitleHosting = new ArrayList<>();
                    List<Integer> eventEidHosting = new ArrayList<>();
                    for (int i = 0; i < eventsArray.length(); i++){
                        JSONObject eventsObj = eventsArray.getJSONObject(i);
                        String eventHostString = eventsObj.getString("eventHost");
                        JSONObject eventHostObj = new JSONObject(eventHostString);
                        if (eventHostObj.getString("uid").equals(String.valueOf(SaveSharedPreference.getUserUID(getActivity())))){
                            eventTitleHosting.add(eventsObj.getString("eventName"));
                            eventEidHosting.add(Integer.valueOf(eventsObj.getString("eid")));
                        }
                    }
                    adapter = new EpisodeMyHostingRecyclerAdapter(getActivity(), eventTitleHosting, eventEidHosting);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvEpisodeMyHosting.setLayoutManager(layoutManager);
                    rvEpisodeMyHosting.setAdapter(adapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
