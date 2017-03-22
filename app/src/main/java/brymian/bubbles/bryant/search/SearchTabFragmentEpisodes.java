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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;

public class SearchTabFragmentEpisodes extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_tab_fragment_episodes, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            searchUsers();
            setTextChangedListener();
        }
    }

    private void setTextChangedListener(){
        SearchActivity.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchUsers();
            }
        });
    }

    private void searchUsers(){
        new EventRequest(getActivity()).getEventDataByName(SearchActivity.etSearch.getText().toString(), new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeHostUsername = new ArrayList<>();
                    List<Integer> episodeEid = new ArrayList<>();
                    List<String> episodeNum = new ArrayList<>();

                    JSONObject jsonObject = new JSONObject(string);
                    String episodesString = jsonObject.getString("events");
                    JSONArray episodesArray = new JSONArray(episodesString);

                    for (int i = 0; i < episodesArray.length(); i++){
                        JSONObject episodeObj = episodesArray.getJSONObject(i);
                        String episodeHostString = episodeObj.getString("eventHost");
                        JSONObject episodeHostObj = new JSONObject(episodeHostString);

                        episodeTitle.add(episodeObj.getString("eventName"));
                        episodeHostUsername.add(episodeHostObj.getString("username"));
                        episodeEid.add(Integer.valueOf(episodeObj.getString("eid")));
                        episodeNum.add(episodeObj.getString("eventViewCount") + " views");
                    }

                    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_search_episodes);
                    adapter = new SearchRecyclerAdapterEpisodes(getActivity(), episodeTitle, episodeHostUsername, episodeEid, episodeNum);
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
