package com.brymian.boppo.bryant.settings.blocking;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.nonactivity.SaveSharedPreference;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.FriendshipStatusRequest;

public class Blocking extends Fragment {

    Toolbar mToolbar;
    RecyclerView rvBlockedUsers;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blocking, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Blocking);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvBlockedUsers = (RecyclerView) view.findViewById(R.id.rvBlockedUsers);

        getFriends();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.search);
        item.setVisible(false);
    }

    private void getFriends(){
        new FriendshipStatusRequest().getFriendshipStatusRequestSentUsers(SaveSharedPreference.getUserUID(getActivity()), "Blocked", new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("string", string);
                try{
                    JSONArray jsonArray = new JSONArray(string);
                    List<String> blockedUsername = new ArrayList<>();
                    List<String> blockedFullname = new ArrayList<>();
                    List<Integer> blockedUid = new ArrayList<>();
                    List<String> blockedImagePath = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject blockedUserObj = jsonArray.getJSONObject(i);

                        String fullname = blockedUserObj.getString("firstName") + " " + blockedUserObj.getString("lastName");
                        String username = blockedUserObj.getString("username");
                        String uid = blockedUserObj.getString("uid");

                        String blockedImagePathString = blockedUserObj.getString("userProfileImages");
                        JSONArray blockedImagePathArray = new JSONArray(blockedImagePathString);
                        String imagePath;
                        if (blockedImagePathArray.length() > 0){
                            JSONObject blockedImagePathObj = blockedImagePathArray.getJSONObject(0);
                            imagePath = blockedImagePathObj.getString("userImageThumbnailPath");
                        }
                        else {
                            imagePath = "empty";
                        }

                        blockedUsername.add(username);
                        blockedUid.add(Integer.valueOf(uid));
                        blockedFullname.add(fullname);
                        blockedImagePath.add(imagePath);
                    }

                    adapter = new BlockingRecyclerAdapter(getActivity(), blockedUid, blockedUsername, blockedFullname, blockedImagePath);
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvBlockedUsers.setLayoutManager(layoutManager);
                    rvBlockedUsers.setNestedScrollingEnabled(false);
                    rvBlockedUsers.setAdapter(adapter);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
