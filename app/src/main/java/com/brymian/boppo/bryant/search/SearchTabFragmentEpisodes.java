package com.brymian.boppo.bryant.search;

import android.os.Bundle;
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


import com.brymian.boppo.R;
import com.brymian.boppo.damian.nonactivity.Connection.HTTPConnection;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.EventRequest;

public class SearchTabFragmentEpisodes extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_tab_episodes, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            searchEpisodes();
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
                searchEpisodes();
            }
        });
    }

    private void searchEpisodes(){
        new EventRequest(getActivity()).getEventDataByName(SearchActivity.etSearch.getText().toString(), new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeHostUsername = new ArrayList<>();
                    List<Integer> episodeEid = new ArrayList<>();
                    List<String> episodeNum = new ArrayList<>();
                    List<String> episodeImagePath = new ArrayList<>();

                    HTTPConnection httpConnection = new HTTPConnection();

                    JSONObject jsonObject = new JSONObject(string);
                    String episodesString = jsonObject.getString("events");
                    JSONArray episodesArray = new JSONArray(episodesString);

                    for (int i = 0; i < episodesArray.length(); i++){
                        JSONObject episodeObj = episodesArray.getJSONObject(i);
                        String episodeHostString = episodeObj.getString("eventHost");
                        JSONObject episodeHostObj = new JSONObject(episodeHostString);
                        String episodeProfileImage = episodeObj.getString("eventProfileImages");
                        JSONArray episodeProfileImageArray = new JSONArray(episodeProfileImage);
                        String imagePath;
                        if (episodeProfileImageArray.length() > 0){
                            JSONObject episodeProfileImageObj = episodeProfileImageArray.getJSONObject(0);
                            imagePath = episodeProfileImageObj.getString("euiThumbnailPath");
                        }
                        else {
                            imagePath = "empty";
                        }
                        episodeTitle.add(episodeObj.getString("eventName"));
                        episodeHostUsername.add(episodeHostObj.getString("username"));
                        episodeEid.add(Integer.valueOf(episodeObj.getString("eid")));
                        episodeNum.add(episodeObj.getString("eventViewCount") + " views");
                        episodeImagePath.add(httpConnection.getUploadServerString() + imagePath);
                    }

                    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_search_episodes);
                    adapter = new SearchRecyclerAdapterEpisodes(getActivity(), episodeTitle, episodeHostUsername, episodeEid, episodeNum, episodeImagePath);
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
