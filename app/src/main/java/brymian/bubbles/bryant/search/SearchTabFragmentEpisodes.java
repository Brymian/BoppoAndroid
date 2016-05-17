package brymian.bubbles.bryant.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.objects.Event;

public class SearchTabFragmentEpisodes extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    static View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_tab_fragment_episodes, container, false);
        SearchActivity.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                new EventRequest(getActivity()).getEventDataByName(SearchActivity.etSearch.getText().toString(), new EventListCallback() {
                    @Override
                    public void done(List<Event> eventList) {
                        List<String> episodeTitle = new ArrayList<>();
                        List<String> episodeHostName = new ArrayList<>();
                        List<Integer> episodeEid = new ArrayList<>();
                        if(eventList.size() > 0){
                            for(int i = 0; i < eventList.size(); i++){
                                episodeEid.add(i, eventList.get(i).eid);
                                episodeTitle.add(i, eventList.get(i).eventName);
                                episodeHostName.add(i, eventList.get(i).eventHostFirstName + " " + eventList.get(i).eventHostLastName);//suppose to be eventHostName
                            }

                            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_search_episodes);
                            adapter = new SearchRecyclerAdapterEpisodes(getActivity(), episodeTitle, episodeHostName, episodeEid);
                            layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });

            }
        });


        return view;
    }
}
