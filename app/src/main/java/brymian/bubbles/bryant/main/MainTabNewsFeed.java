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
            isNewsFeedLoaded = true;
        }
    }

    private void setNewsFeed(){
        new NewsFeedRequest(getActivity()).getNewsEvents(SaveSharedPreference.getUserUID(getActivity()), 10, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    Log.e("getNewsEvents", string);
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
                                String activeEpisode = jsonObject.getString("event");
                                JSONObject activeEpisodeObject = new JSONObject(activeEpisode);

                                String activeUser = jsonObject.getString("user");
                                JSONObject activeUserObject = new JSONObject(activeUser);
                                MainTabNewsFeedInfo infoActive = new MainTabNewsFeedInfo(activeEpisodeObject.getString("eid"), activeEpisodeObject.getString("eventName"), activeEpisodeObject.getString("eventEndDatetime"), activeUserObject.getString("username"), activeUserObject.getString("uid"));
                                mainTabNewsFeedInfoList.add(infoActive);
                                Log.e("activeEvent", activeEpisode + " " + activeUserObject);
                                break;
                            case "FriendsThatBecameFriends":
                                String user1 = jsonObject.getString("user1");
                                JSONObject user1Obj = new JSONObject(user1);

                                String user2 = jsonObject.getString("user2");
                                JSONObject user2Obj = new JSONObject(user2);
                                MainTabNewsFeedInfo infoBecame = new MainTabNewsFeedInfo(user1Obj.getString("username"),  user1Obj.getString("uid"), user2Obj.getString("username"), user2Obj.getString("uid"));
                                mainTabNewsFeedInfoList.add(infoBecame);
                                Log.e("becameFriends", "user1uid: " + user1Obj.getString("uid") + " user1Username: " + user1Obj.getString("username") + " user2Uid: " +user2Obj.getString("uid") + " user2Username: " + user2Obj.getString("username"));
                                break;
                            case "FriendCreatedEvent":
                                String createdEpisode = jsonObject.getString("event");
                                JSONObject createdEpisodeObject = new JSONObject(createdEpisode);

                                String createdUser = jsonObject.getString("eventHostUser");
                                JSONObject createdUserObject = new JSONObject(createdUser);

                                MainTabNewsFeedInfo info = new MainTabNewsFeedInfo(createdEpisodeObject.getString("eid"), createdEpisodeObject.getString("eventName"),  createdEpisodeObject.getString("eventCreationTimestamp"), createdUserObject.getString("username"), createdUserObject.getString("uid"));
                                mainTabNewsFeedInfoList.add(info);
                                Log.e("createdEvent", "eid: " + createdEpisodeObject.getString("eid") + " title: " + createdEpisodeObject.getString("eventName") + " timestamp: " + createdEpisodeObject.getString("eventCreationTimestamp") + " username: " + createdUserObject.getString("username") + " uid: " + createdUserObject.getString("uid"));
                                break;
                                case "FriendsUploadedImages":
                                String userImage = jsonObject.getString("userImage");
                                JSONObject userImageObject = new JSONObject(userImage);

                                MainTabNewsFeedInfo infoUploadImage = new MainTabNewsFeedInfo(userImageObject.getString("uiid"), userImageObject.getString("uid"), userImageObject.getString("userImagePurposeLabel"), userImageObject.getString("userImageGpsLatitude"), userImageObject.getString("userImageGpsLongitude"), userImageObject.getString("userImageUploadTimestamp"), userImageObject.getString("userImageLikeCount"), userImageObject.getString("userImageDislikeCount"), userImageObject.getString("userImageCommentCount"));
                                mainTabNewsFeedInfoList.add(infoUploadImage);

                                Log.e("UploadedImages", " uiid: " + userImageObject.getString("uiid") + " uid: " + userImageObject.getString("uid") + " purposeLabel: " + userImageObject.getString("userImagePurposeLabel") + " latitude: " + userImageObject.getString("userImageGpsLatitude") + " longitude: " + userImageObject.getString("userImageGpsLongitude") + " timestamp: " + userImageObject.getString("userImageUploadTimestamp") + " likeCount: " + userImageObject.getString("userImageLikeCount") + " dislikeCount: " + userImageObject.getString("userImageDislikeCount") + " commentCount: "  + userImageObject.getString("userImageCommentCount"));
                                break;
                        }
                    }

                    adapter = new MainTabNewsFeedRecyclerAdapter(types, mainTabNewsFeedInfoList);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvNewsFeed.setLayoutManager(layoutManager);
                    rvNewsFeed.setAdapter(adapter);
                }
                catch (JSONException | NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }
}