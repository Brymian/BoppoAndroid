package brymian.bubbles.bryant.main;


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
import brymian.bubbles.damian.nonactivity.ServerRequest.NewsFeedRequest;

public class MainTabNewsFeed extends Fragment {
    boolean isNewsFeedLoaded = false;
    RecyclerView rvNewsFeed;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_tab_news_feed, container, false);
        rvNewsFeed = (RecyclerView) rootView.findViewById(R.id.rvNewsFeed);
        return rvNewsFeed;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isNewsFeedLoaded){
            setNewsFeed();
        }
    }

    private void setNewsFeed(){
        new NewsFeedRequest(getActivity()).getNewsEvents(SaveSharedPreference.getUserUID(getActivity()), 10, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    //Log.e("getNewsEvents", string);
                    List<String> types = new ArrayList<>();
                    List<MainTabNewsFeedInfo> mainTabNewsFeedInfoList = new ArrayList<>();
                    JSONArray jsonArray  = new JSONArray(string);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String type = jsonObject.getString("newsEventType");
                        types.add(type);
                        Log.e("newsEventType", type);
                        switch (type){
                            case "FriendsJoinedMutualEvent":
                                String mutualEpisode = jsonObject.getString("event");
                                JSONObject mutualEpisodeObject = new JSONObject(mutualEpisode);

                                String mutualFriend = jsonObject.getString("userList");
                                JSONArray mutualFriendArray = new JSONArray(mutualFriend);
                                List<String> mutualUsernames = new ArrayList<>();
                                List<String> mutualUids = new ArrayList<>();
                                for (int j = 0; j < mutualFriendArray.length(); j++){
                                    JSONObject jsonObject1 = mutualFriendArray.getJSONObject(i);
                                    mutualUsernames.add(jsonObject1.getString("username"));
                                    mutualUids.add(jsonObject1.getString("uid"));
                                    Log.e("j", jsonObject1.getString("username") + " " + jsonObject1.getString("uid"));
                                }
                                MainTabNewsFeedInfo infoMutual = new MainTabNewsFeedInfo(mutualEpisodeObject.getString("eid"), mutualEpisodeObject.getString("eventName"), mutualEpisodeObject.getString("eventUserInviteStatusActionTimestamp"), mutualUsernames, mutualUids);
                                mainTabNewsFeedInfoList.add(infoMutual);
                                break;

                            case "FriendActiveEvent":
                                //Log.e("ActiveEvent", "here");

                                break;
                            case "FriendsThatBecameFriends":
                                String user1 = jsonObject.getString("user1");
                                JSONObject user1Obj = new JSONObject(user1);
                                String user1Uid = user1Obj.getString("uid");
                                String user1Username = user1Obj.getString("username");


                                String user2 = jsonObject.getString("user2");
                                JSONObject user2Obj = new JSONObject(user2);
                                String user2Uid = user2Obj.getString("uid");
                                String user2Username = user2Obj.getString("username");
                                //Log.e("becameFriends", "user1uid: " + user1Uid + " user1Username: " + user1Username + " user2Uid: " +user2Uid + " user2Username: " + user2Username);
                                break;
                            case "FriendCreatedEvent":
                                String createdEpisode = jsonObject.getString("event");
                                JSONObject createdEpisodeObject = new JSONObject(createdEpisode);
                                String createdEpisodeEid = createdEpisodeObject.getString("eid");
                                String createdEpisodeTitle = createdEpisodeObject.getString("eventName");
                                String createdEpisodeTimeStamp = createdEpisodeObject.getString("eventCreationTimestamp");

                                //Log.e("createdEvent", createdEpisodeEid + " " + createdEpisodeTitle + " "+ createdEpisodeTimeStamp);
                                break;
                            case "FriendsUploadedImages":
                                String userImage = jsonObject.getString("userImage");

                                //Log.e("UploadedImages", userImage);
                                break;
                        }
                    }

                    //do recyclerview here
                    adapter = new MainTabNewsFeedRecyclerAdapter(types, mainTabNewsFeedInfoList);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvNewsFeed.setLayoutManager(layoutManager);
                    rvNewsFeed.setNestedScrollingEnabled(false);
                    rvNewsFeed.setAdapter(adapter);
                }
                catch (JSONException | NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }
}