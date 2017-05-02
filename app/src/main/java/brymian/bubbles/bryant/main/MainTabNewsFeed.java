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
    RecyclerView rvNewsFeed;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_tab_news_feed, container, false);
        rvNewsFeed = (RecyclerView) rootView.findViewById(R.id.rvNewsFeed);
        setNewsFeed();
        return rootView;
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

                                String mutualEpisodeProfileImage = mutualEpisodeObject.getString("eventProfileImages");
                                JSONArray mutualEpisodeProfileImageArray = new JSONArray(mutualEpisodeProfileImage);
                                String mutualEpisodeProfileImagePath;
                                if (mutualEpisodeProfileImageArray.length() > 0){
                                    JSONObject mutualEpisodeProfileObject = mutualEpisodeProfileImageArray.getJSONObject(0);
                                    mutualEpisodeProfileImagePath = mutualEpisodeProfileObject.getString("euiPath");
                                }
                                else {
                                    mutualEpisodeProfileImagePath = "empty";
                                }

                                String mutualFriend = jsonObject.getString("userList");
                                JSONArray mutualFriendArray = new JSONArray(mutualFriend);

                                List<String> mutualUsernames = new ArrayList<>();
                                List<Integer> mutualUids = new ArrayList<>();
                                List<String> mutualUserProfileImagePaths = new ArrayList<>();
                                for (int j = 0; j < mutualFriendArray.length(); j++){
                                    JSONObject jsonObject1 = mutualFriendArray.getJSONObject(j);
                                    mutualUsernames.add(jsonObject1.getString("username"));
                                    mutualUids.add(Integer.valueOf(jsonObject1.getString("uid")));
                                    String userProfileImage = jsonObject1.getString("userProfileImages");
                                    JSONArray userProfileImageArray = new JSONArray(userProfileImage);
                                    String userProfileImagePath;
                                    if (userProfileImageArray.length() > 0){
                                        JSONObject userProfileImageObj = userProfileImageArray.getJSONObject(0);
                                        userProfileImagePath = userProfileImageObj.getString("userImagePath");
                                    }
                                    else {
                                        userProfileImagePath = "empty";
                                    }
                                    mutualUserProfileImagePaths.add(userProfileImagePath);
                                }
                                MainTabNewsFeedInfo infoMutual = new MainTabNewsFeedInfo(Integer.valueOf(mutualEpisodeObject.getString("eid")), mutualEpisodeObject.getString("eventName"), mutualEpisodeObject.getString("eventUserInviteStatusActionTimestamp"), mutualUsernames, mutualUids, mutualUserProfileImagePaths, mutualEpisodeProfileImagePath);
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
                                MainTabNewsFeedInfo infoActive = new MainTabNewsFeedInfo(55, "Example Event", "1232", "Example User", 1, "never", "empty");
                                mainTabNewsFeedInfoList.add(infoActive);

                                //Log.e("activeEvent", "userProfileImage: " + activeUserProfileImageObj.getString("UserProfileImage"));
                                break;
                            case "FriendsThatBecameFriends":
                                String friendsThatBecameFriends = jsonObject.getString("friendsThatBecameFriends");
                                JSONObject friendsThatBecameFriendsObj = new JSONObject(friendsThatBecameFriends);

                                String user1 = friendsThatBecameFriendsObj.getString("user1");
                                JSONObject user1Obj = new JSONObject(user1);

                                String user1ProfileImage = user1Obj.getString("user1ProfileImages");
                                JSONArray user1ProfileImageArray = new JSONArray(user1ProfileImage);
                                String user1ImagePath;
                                if (user1ProfileImageArray.length() > 0){
                                    JSONObject user1ProfileImageObj = user1ProfileImageArray.getJSONObject(0);
                                    user1ImagePath = user1ProfileImageObj.getString("userImagePath");
                                }
                                else {
                                    user1ImagePath = "empty";
                                }

                                String user2 = friendsThatBecameFriendsObj.getString("user2");
                                JSONObject user2Obj = new JSONObject(user2);

                                String user2ProfileImage = user2Obj.getString("user2ProfileImages");
                                JSONArray user2ProfileImageArray = new JSONArray(user2ProfileImage);
                                String user2ImagePath;
                                if (user2ProfileImageArray.length() > 0) {
                                    JSONObject user2ProfileImageObj = user2ProfileImageArray.getJSONObject(0);
                                    user2ImagePath = user2ProfileImageObj.getString("userImagePath");
                                }
                                else {
                                    user2ImagePath = "empty";
                                }

                                MainTabNewsFeedInfo infoBecame = new MainTabNewsFeedInfo(user1Obj.getString("username"),  Integer.valueOf(user1Obj.getString("uid")), user1ImagePath, user2Obj.getString("username"), Integer.valueOf(user2Obj.getString("uid")), user2ImagePath);
                                mainTabNewsFeedInfoList.add(infoBecame);
                                break;
                            case "FriendCreatedEvent":
                                String createdEpisode = jsonObject.getString("friendCreatedEvent");
                                JSONObject createdEpisodeObject = new JSONObject(createdEpisode);

                                String createdUser = createdEpisodeObject.getString("eventHostUser");
                                JSONObject createdUserObject = new JSONObject(createdUser);

                                String createdUserProfileImageString = createdUserObject.getString("userProfileImages");
                                JSONArray createdUserProfileImageArray = new JSONArray(createdUserProfileImageString);
                                JSONObject createdUserProfileImageObj = createdUserProfileImageArray.getJSONObject(0);

                                String createdProfileImages = createdEpisodeObject.getString("eventProfileImages");
                                JSONArray createdProfileImagesArray = new JSONArray(createdProfileImages);
                                String createdProfileImagePath;
                                if (createdProfileImagesArray.length() > 0){
                                    JSONObject createdProfileImageObject = createdProfileImagesArray.getJSONObject(0);
                                    createdProfileImagePath = createdProfileImageObject.getString("euiPath");
                                }
                                else {
                                    createdProfileImagePath = "empty";
                                }


                                MainTabNewsFeedInfo info = new MainTabNewsFeedInfo(Integer.valueOf(createdEpisodeObject.getString("eid")), createdEpisodeObject.getString("eventName"),  createdEpisodeObject.getString("eventInsertTimestamp"), createdUserObject.getString("username"), Integer.valueOf(createdUserObject.getString("uid")), createdUserProfileImageObj.getString("userImagePath"), createdProfileImagePath);
                                mainTabNewsFeedInfoList.add(info);
                                break;

                            case "FriendUploadedImage":
                                String friendUploadedImage = jsonObject.getString("friendUploadedImage");
                                JSONObject uploadedImageObj = new JSONObject(friendUploadedImage);

                                String uploadedUser = uploadedImageObj.getString("user");
                                JSONObject uploadedUserObj = new JSONObject(uploadedUser);

                                String uploadedUserProfileImage = uploadedUserObj.getString("userProfileImages");
                                JSONArray uploadedUserProfileImageArray = new JSONArray(uploadedUserProfileImage);
                                JSONObject uploadedUserProfileImageObj = uploadedUserProfileImageArray.getJSONObject(0);

                                MainTabNewsFeedInfo infoUploadImage = new MainTabNewsFeedInfo(Integer.valueOf(uploadedUserObj.getString("uid")), uploadedUserObj.getString("username"), uploadedUserProfileImageObj.getString("userImagePath"), uploadedImageObj.getString("userImagePath"), uploadedImageObj.getString("userImagePurposeLabel"), uploadedImageObj.getString("userImageGpsLatitude"), uploadedImageObj.getString("userImageGpsLongitude"), uploadedImageObj.getString("userImageUploadTimestamp"), uploadedImageObj.getString("userImageLikeCount"), uploadedImageObj.getString("userImageDislikeCount"), uploadedImageObj.getString("userImageCommentCount"));
                                mainTabNewsFeedInfoList.add(infoUploadImage);
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