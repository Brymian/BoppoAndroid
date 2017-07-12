package com.brymian.boppo.bryant.main;

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

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.main.mainTabEpisodesRecyclerAdapter.MainTabEpisodesRecyclerAdapter;
import com.brymian.boppo.damian.nonactivity.Connection.HTTPConnection;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.EventRequest;

public class MainTabEpisodes extends Fragment {
    RecyclerView rvAllTimeMostViews, rvAllTimeTopRated, rvAllTimeMostLikes;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    boolean isEpisodesLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_tab_episodes, container, false);

        rvAllTimeMostViews = (RecyclerView) rootView.findViewById(R.id.rvAllTimeMostViews);
        rvAllTimeTopRated = (RecyclerView) rootView.findViewById(R.id.rvAllTimeTopRated);
        rvAllTimeMostLikes = (RecyclerView) rootView.findViewById(R.id.rvAllTimeMostLikes);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isEpisodesLoaded){
            setLiveMostViewsEpisodes();
            setAllTimeMostViewsEpisodes();
            setALlTimeTopRated();
            setAllTimeMostLikesEpisodes();
            isEpisodesLoaded = true;
        }
    }

    private void setLiveMostViewsEpisodes(){
        /* BRYANT, REVISIT THIS */
        /*
        new EventRequest(getActivity()).getLiveEventDataByTopNViews(5, new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                try {
                    //if (eventList.size() > 0) {
                        List<String> episodeTitle = new ArrayList<>();
                        List<String> episodeHostName = new ArrayList<>();
                        List<Integer> episodeEid = new ArrayList<>();
                        List<Long> episodeViewCount = new ArrayList<>();
                        for (int i = 0; i < eventList.size(); i++) {
                            episodeTitle.add(eventList.get(i).eventName);
                            episodeHostName.add(eventList.get(i).eventHostFirstName + " " + eventList.get(i).eventHostLastName);
                            Log.e("live views", "eid: " + eventList.get(i).eid);
                            episodeEid.add(eventList.get(i).eid);
                            episodeViewCount.add(eventList.get(i).eventViewCount);
                        }

                        adapter = new MainTabEpisodesLiveMostViewsRecyclerAdapter(getActivity(), episodeTitle, episodeHostName, episodeEid, episodeViewCount);
                        layoutManager = new LinearLayoutManager(getActivity());
                        rvLiveMostViews.setLayoutManager(layoutManager);
                        rvLiveMostViews.setAdapter(adapter);
                    //}
                    //else{
                    //    Log.e("Live views", "0");
                    //    layoutManager = new LinearLayoutManager(getActivity());
                    //    rvLiveMostViews.setLayoutManager(layoutManager);
                    //}
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
        */
    }

    private void setAllTimeMostViewsEpisodes(){
        new EventRequest(getActivity()).getEventDataByTopNViews(5, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    List<Integer> episodeEid = new ArrayList<>();
                    List<String> episodeImagePath = new ArrayList<>();
                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeHostUsername = new ArrayList<>();
                    List<String> episodeNum = new ArrayList<>();

                    HTTPConnection httpConnection = new HTTPConnection();

                    JSONObject jsonObject = new JSONObject(string);
                    String episodeString = jsonObject.getString("events");
                    JSONArray episodesArray = new JSONArray(episodeString);
                    for (int i = 0; i < episodesArray.length(); i++){
                        JSONObject episodeObj = episodesArray.getJSONObject(i);

                        String episodeHostString = episodeObj.getString("eventHost");
                        JSONObject episodeHostObj = new JSONObject(episodeHostString);

                        String eventProfileImageString = episodeObj.getString("eventProfileImages");
                        JSONArray eventProfileImageArray = new JSONArray(eventProfileImageString);
                        String eventProfileImagePath;
                        if (eventProfileImageArray.length() > 0){
                            JSONObject eventProfileImageObj = eventProfileImageArray.getJSONObject(0);
                            eventProfileImagePath = httpConnection.getUploadServerString() + eventProfileImageObj.getString("euiThumbnailPath");
                        }
                        else {
                            eventProfileImagePath = "empty";
                        }

                        episodeEid.add(Integer.valueOf(episodeObj.getString("eid")));
                        episodeTitle.add(episodeObj.getString("eventName"));
                        episodeImagePath.add(eventProfileImagePath);
                        episodeHostUsername.add(episodeHostObj.getString("username"));
                        episodeNum.add(episodeObj.getString("eventViewCount") + " views");
                    }
                    adapter = new MainTabEpisodesRecyclerAdapter(getActivity(), episodeEid,  episodeTitle, episodeImagePath, episodeHostUsername, episodeNum);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvAllTimeMostViews.setLayoutManager(layoutManager);
                    rvAllTimeMostViews.setNestedScrollingEnabled(false);
                    rvAllTimeMostViews.setAdapter(adapter);

                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void setALlTimeTopRated(){
        new EventRequest(getActivity()).getEventDataByTopNRatings(5, new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("topRated", string);
                try{
                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeHostUsername = new ArrayList<>();
                    List<Integer> episodeEid = new ArrayList<>();
                    List<String> episodeImagePath = new ArrayList<>();
                    List<String> episodeRating = new ArrayList<>();

                    HTTPConnection httpConnection = new HTTPConnection();

                    JSONObject jsonObject = new JSONObject(string);
                    String episodeString = jsonObject.getString("events");
                    JSONArray episodesArray = new JSONArray(episodeString);
                    for (int i = 0; i < episodesArray.length(); i++) {
                        JSONObject episodeObj = episodesArray.getJSONObject(i);
                        String episodeHostString = episodeObj.getString("eventHost");
                        JSONObject episodeHostObj = new JSONObject(episodeHostString);

                        String episodeProfileImages = episodeObj.getString("eventProfileImages");
                        JSONArray episodeProfileImagesArray = new JSONArray(episodeProfileImages);
                        String imagePath;
                        if (episodeProfileImagesArray.length() > 0){
                            JSONObject imagePathObj = episodeProfileImagesArray.getJSONObject(0);
                            imagePath = httpConnection.getUploadServerString() + imagePathObj.getString("euiThumbnailPath");
                        }
                        else {
                            imagePath = "empty";
                        }

                        Log.e("rated", "100");

                        episodeEid.add(Integer.valueOf(episodeObj.getString("eid")));
                        episodeTitle.add(episodeObj.getString("eventName"));
                        episodeImagePath.add(imagePath);
                        episodeHostUsername.add(episodeHostObj.getString("username"));
                        episodeRating.add(episodeObj.getString("eventRatingRatio"));
                    }
                    adapter = new MainTabEpisodesRecyclerAdapter(getActivity(), episodeEid, episodeTitle, episodeImagePath,episodeHostUsername, episodeRating);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvAllTimeTopRated.setLayoutManager(layoutManager);
                    rvAllTimeTopRated.setNestedScrollingEnabled(false);
                    rvAllTimeTopRated.setAdapter(adapter);
                }
                catch (JSONException | NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setAllTimeMostLikesEpisodes(){
        new EventRequest(getActivity()).getEventDataByTopNLikes(5, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    List<Integer> episodeEid = new ArrayList<>();
                    List<String> episodeTitle = new ArrayList<>();
                    List<String> episodeImagePath = new ArrayList<>();
                    List<String> episodeHostUsername = new ArrayList<>();
                    List<String> episodeNum = new ArrayList<>();

                    HTTPConnection httpConnection = new HTTPConnection();

                    JSONObject jsonObject = new JSONObject(string);
                    String episodeString = jsonObject.getString("events");
                    JSONArray episodesArray = new JSONArray(episodeString);
                    for (int i = 0; i < episodesArray.length(); i++){
                        JSONObject episodeObj = episodesArray.getJSONObject(i);

                        String episodeHostString = episodeObj.getString("eventHost");
                        JSONObject episodeHostObj = new JSONObject(episodeHostString);

                        String episodeProfileImagesString = episodeObj.getString("eventProfileImages");
                        JSONArray episodeProfileImagesArray = new JSONArray(episodeProfileImagesString);
                        String imagePath;
                        if (episodeProfileImagesArray.length() > 0){
                            JSONObject episodeProfileImageObj = episodeProfileImagesArray.getJSONObject(0);
                            imagePath = httpConnection.getUploadServerString() + episodeProfileImageObj.getString("euiThumbnailPath");
                        }
                        else {
                            imagePath = "empty";
                        }

                        episodeEid.add(Integer.valueOf(episodeObj.getString("eid")));
                        episodeTitle.add(episodeObj.getString("eventName"));
                        episodeImagePath.add(imagePath);
                        episodeHostUsername.add(episodeHostObj.getString("username"));
                        episodeNum.add(episodeObj.getString("eventLikeCount") + " likes");
                    }
                    adapter = new MainTabEpisodesRecyclerAdapter(getActivity(), episodeEid,  episodeTitle, episodeImagePath, episodeHostUsername, episodeNum);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvAllTimeMostLikes.setLayoutManager(layoutManager);
                    rvAllTimeMostLikes.setNestedScrollingEnabled(false);
                    rvAllTimeMostLikes.setAdapter(adapter);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}