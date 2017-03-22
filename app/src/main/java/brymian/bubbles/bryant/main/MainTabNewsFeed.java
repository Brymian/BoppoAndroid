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
        return rootView;
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
                    List<String> types = new ArrayList<>();
                    List<MainTabNewsFeedInfo> mainTabNewsFeedInfoList = new ArrayList<>();
                    JSONArray jsonArray  = new JSONArray(string);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String type = jsonObject.getString("newsEventType");
                        types.add(type);
                        Log.e("newsEventType", type);
                        Log.e("jsonString", "" + jsonObject);
                        switch (type){
                            case "FriendsJoinedMutualEvent":
                                String mutualEpisode = jsonObject.getString("friendsJoinedMutualEvent");
                                JSONObject mutualEpisodeObject = new JSONObject(mutualEpisode);

                                String mutualFriend = jsonObject.getString("userList");
                                JSONArray mutualFriendArray = new JSONArray(mutualFriend);

                                List<String> mutualUsernames = new ArrayList<>();
                                List<Integer> mutualUids = new ArrayList<>();
                                List<String> mutualUserProfileImagePaths = new ArrayList<>();
                                for (int j = 0; j < mutualFriendArray.length(); j++){
                                    JSONObject jsonObject1 = mutualFriendArray.getJSONObject(j);
                                    mutualUsernames.add(jsonObject1.getString("username"));
                                    mutualUids.add(Integer.valueOf(jsonObject1.getString("uid")));
                                    String userProfileImage = jsonObject1.getString("userProfileImage");
                                    JSONObject userProfileImageObj = new JSONObject(userProfileImage);
                                    mutualUserProfileImagePaths.add(userProfileImageObj.getString("userImagePath"));
                                    Log.e("j", jsonObject1.getString("username") + " " + jsonObject1.getString("uid") + " " + userProfileImageObj.getString("userImagePath"));
                                }
                                MainTabNewsFeedInfo infoMutual = new MainTabNewsFeedInfo(Integer.valueOf(mutualEpisodeObject.getString("eid")), mutualEpisodeObject.getString("eventName"), mutualEpisodeObject.getString("eventUserInviteStatusActionTimestamp"), mutualUsernames, mutualUids, mutualUserProfileImagePaths);
                                mainTabNewsFeedInfoList.add(infoMutual);
                                break;

                            case "FriendActiveEvent":
                                /*
                                String activeEpisode = jsonObject.getString("event");
                                JSONObject activeEpisodeObject = new JSONObject(activeEpisode);

                                String activeUser = jsonObject.getString("user");
                                JSONObject activeUserObject = new JSONObject(activeUser);

                                String activeUserProfileImage = jsonObject.getString("userProfileImage");
                                JSONObject activeUserProfileImageObj = new JSONObject(activeUserProfileImage);

                                    */
                                //MainTabNewsFeedInfo infoActive = new MainTabNewsFeedInfo(Integer.valueOf(activeEpisodeObject.getString("eid")), activeEpisodeObject.getString("eventName"), activeEpisodeObject.getString("eventEndDatetime"), activeUserObject.getString("username"), Integer.valueOf(activeUserObject.getString("uid")), activeUserProfileImageObj.getString("userProfileImage"));
                                MainTabNewsFeedInfo infoActive = new MainTabNewsFeedInfo(55, "Example Event", "1232", "Example User", 1, "never");
                                mainTabNewsFeedInfoList.add(infoActive);

                                //Log.e("activeEvent", "userProfileImage: " + activeUserProfileImageObj.getString("UserProfileImage"));
                                break;
                            case "FriendsThatBecameFriends":
                                String friendsThatBecameFriends = jsonObject.getString("friendsThatBecameFriends");
                                JSONObject friendsThatBecameFriendsObj = new JSONObject(friendsThatBecameFriends);

                                String user1 = friendsThatBecameFriendsObj.getString("user1");
                                JSONObject user1Obj = new JSONObject(user1);

                                String user1ProfileImage = user1Obj.getString("userProfileImage");
                                JSONObject user1ProfileImageObj = new JSONObject(user1ProfileImage);

                                String user2 = friendsThatBecameFriendsObj.getString("user2");
                                JSONObject user2Obj = new JSONObject(user2);

                                String user2ProfileImage = user2Obj.getString("userProfileImage");
                                JSONObject user2ProfileImageObj = new JSONObject(user2ProfileImage);

                                MainTabNewsFeedInfo infoBecame = new MainTabNewsFeedInfo(user1Obj.getString("username"),  Integer.valueOf(user1Obj.getString("uid")), user1ProfileImageObj.getString("userImagePath"), user2Obj.getString("username"), Integer.valueOf(user2Obj.getString("uid")), user2ProfileImageObj.getString("userImagePath"));
                                mainTabNewsFeedInfoList.add(infoBecame);

                                Log.e("becameFriends", "user1uid: " + user1Obj.getString("uid") + "\tuser1Username: " + user1Obj.getString("username") + "\tuser1ProfileImage: " + user1ProfileImageObj.getString("userImagePath") + "\tuser2Uid: " +user2Obj.getString("uid") + "\tuser2Username: " + user2Obj.getString("username") + "\tuser2ProfileImage: " + user2ProfileImageObj.getString("userImagePath"));
                                break;
                            case "FriendCreatedEvent":
                                String createdEpisode = jsonObject.getString("friendCreatedEvent");
                                JSONObject createdEpisodeObject = new JSONObject(createdEpisode);

                                String createdUser = createdEpisodeObject.getString("eventHostUser");
                                JSONObject createdUserObject = new JSONObject(createdUser);

                                String createdUserProfileImage = createdUserObject.getString("userImage");
                                JSONObject createdUserProfileImageObj = new JSONObject(createdUserProfileImage);

                                MainTabNewsFeedInfo info = new MainTabNewsFeedInfo(Integer.valueOf(createdEpisodeObject.getString("eid")), createdEpisodeObject.getString("eventName"),  createdEpisodeObject.getString("eventCreationTimestamp"), createdUserObject.getString("username"), Integer.valueOf(createdUserObject.getString("uid")), createdUserProfileImageObj.getString("userImagePath"));
                                mainTabNewsFeedInfoList.add(info);
                                Log.e("createdEvent", "eid: " + createdEpisodeObject.getString("eid") + "\ttitle: " + createdEpisodeObject.getString("eventName") + "\ttimestamp: " + createdEpisodeObject.getString("eventCreationTimestamp") + "\tusername: " + createdUserObject.getString("username") + "\tuid: " + createdUserObject.getString("uid") + "\timagePath: " + createdUserProfileImageObj.getString("userImagePath"));
                                break;

                            case "FriendUploadedImage":
                                String friendUploadedImage = jsonObject.getString("friendUploadedImage");
                                JSONObject uploadedImageObj = new JSONObject(friendUploadedImage);

                                String uploadedUser = uploadedImageObj.getString("user");
                                JSONObject uploadedUserObj = new JSONObject(uploadedUser);

                                String uploadedUserProfileImage = uploadedUserObj.getString("userImage");
                                JSONObject uploadedUserProfileImageObj = new JSONObject(uploadedUserProfileImage);

                                MainTabNewsFeedInfo infoUploadImage = new MainTabNewsFeedInfo(Integer.valueOf(uploadedUserObj.getString("uid")), uploadedUserObj.getString("username"), uploadedUserProfileImageObj.getString("userImagePath"), uploadedImageObj.getString("userImagePath"), uploadedImageObj.getString("userImagePurposeLabel"), uploadedImageObj.getString("userImageGpsLatitude"), uploadedImageObj.getString("userImageGpsLongitude"), uploadedImageObj.getString("userImageUploadTimestamp"), uploadedImageObj.getString("userImageLikeCount"), uploadedImageObj.getString("userImageDislikeCount"), uploadedImageObj.getString("userImageCommentCount"));
                                mainTabNewsFeedInfoList.add(infoUploadImage);

                                Log.e("UploadedImages"," uid: " + uploadedUserObj.getString("uid") + " username: " +  uploadedUserObj.getString("username") + " userProfileImagePath: " + uploadedUserProfileImageObj.getString("userImagePath") + " userUploadedImagePath: " + uploadedImageObj.getString("userImagePath") +" purposeLabel: " + uploadedImageObj.getString("userImagePurposeLabel") + " latitude: " + uploadedImageObj.getString("userImageGpsLatitude") + " longitude: " + uploadedImageObj.getString("userImageGpsLongitude") + " timestamp: " + uploadedImageObj.getString("userImageUploadTimestamp") + " likeCount: " + uploadedImageObj.getString("userImageLikeCount") + " dislikeCount: " + uploadedImageObj.getString("userImageDislikeCount") + " commentCount: "  + uploadedImageObj.getString("userImageCommentCount"));
                                break;
                        }
                    }

                    adapter = new MainTabNewsFeedRecyclerAdapter(getActivity(), types, mainTabNewsFeedInfoList);
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