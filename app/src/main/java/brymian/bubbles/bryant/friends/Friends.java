package brymian.bubbles.bryant.friends;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.User;

public class Friends extends Fragment{
    Toolbar toolbar;
    LinearLayout llFriendRequests;
    TextView tvFriendRequestNum, tvHideShow;
    TextInputLayout tilSearchFriends;
    TextInputEditText tietSearchFriends;
    RecyclerView rvFriends, rvFriendRequests;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    boolean isVisible = false;
    private String from;
    private List<Integer> searchFriendsUid = new ArrayList<>();
    private List<String> searchFriendsUsername = new ArrayList<>();
    private List<String> searchFriendsFullName = new ArrayList<>();
    private List<String> searchFriendsUserImagePath = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        llFriendRequests = (LinearLayout) view.findViewById(R.id.llFriendRequests);
        tvFriendRequestNum = (TextView) view.findViewById(R.id.tvFriendRequestNum);
        tvHideShow = (TextView) view.findViewById(R.id.tvHideShow);
        tvHideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible){
                    isVisible = false;
                    tvHideShow.setText("SHOW");
                    rvFriendRequests.setVisibility(View.GONE);
                }
                else if (!isVisible){
                    isVisible = true;
                    tvHideShow.setText("HIDE");
                    rvFriendRequests.setVisibility(View.VISIBLE);
                }
            }
        });

        tilSearchFriends = (TextInputLayout) view.findViewById(R.id.tilSearchFriends);
        tietSearchFriends = (TextInputEditText) view.findViewById(R.id.tietSearchFriends);
        //for the search/enter button
        tietSearchFriends.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
        tietSearchFriends.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchFriends(tietSearchFriends.getText().toString());
            }
        });
        tietSearchFriends.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && isVisible){
                    isVisible = false;
                    tvHideShow.setText("SHOW");
                    rvFriendRequests.setVisibility(View.GONE);
                }
            }
        });
        rvFriendRequests = (RecyclerView) view.findViewById(R.id.rvFriendRequests);
        rvFriends = (RecyclerView) view.findViewById(R.id.recyclerView_friends);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int uid = bundle.getInt("uid", 0);
            from = bundle.getString("from");
            getFriends(uid);
            getUsername(uid);
            if (from.equals("profile")){
                toolbar.setPadding(0, getStatusBarHeight(), 0 ,0);
            }
            if (uid == SaveSharedPreference.getUserUID(getActivity())){
                checkForReceivedFriendRequests(uid);
            }
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void getUsername(int uid){
        if (uid == SaveSharedPreference.getUserUID(getActivity())){
            String username = SaveSharedPreference.getUsername(getActivity());
            toolbar.setTitle(username + "'s Friends");
            tilSearchFriends.setHint("Search " + username + "'s friends");

        }
        new ServerRequestMethods(getActivity()).getUserData(uid, new UserCallback() {
            @Override
            public void done(User user) {
                toolbar.setTitle(user.getUsername() + "'s Friends");
                tilSearchFriends.setHint("Search " + user.getUsername() + "'s friends");
            }
        });
    }

    /* Checks and displays for any friend requests for logged in user */
    private void checkForReceivedFriendRequests(int uid){
        new FriendshipStatusRequest().getFriendshipStatusRequestReceivedUsers(uid, "Friendship Pending", new StringCallback() {
            @Override
            public void done(String string) {
                try {
                    List<Integer> receivedUid = new ArrayList<>();
                    List<String> receivedUsername = new ArrayList<>();
                    List<String> receivedFullname = new ArrayList<>();
                    List<String> receivedImagePath = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(string);
                    if (jsonArray.length() > 0){
                        tvFriendRequestNum.setText(String.valueOf(jsonArray.length()));

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject userObj = jsonArray.getJSONObject(i);
                            String uid = userObj.getString("uid");
                            String username = userObj.getString("username");
                            String fullname = userObj.getString("firstName") + " " + userObj.getString("lastName");

                            String userImage = userObj.getString("userProfileImages");
                            JSONArray userImageArray = new JSONArray(userImage);
                            String imagePath;
                            if (userImageArray.length() > 0){
                                JSONObject userImageObj = userImageArray.getJSONObject(0);
                                imagePath = userImageObj.getString("userImageThumbnailPath");
                            }
                            else {
                                imagePath = "empty";
                            }
                            receivedUid.add(Integer.valueOf(uid));
                            receivedUsername.add(username);
                            receivedFullname.add(fullname);
                            receivedImagePath.add(imagePath);
                        }
                        llFriendRequests.setVisibility(View.VISIBLE);
                        rvFriendRequests.setVisibility(View.VISIBLE);
                        isVisible = true;

                        adapter = new FriendRequestReceivedRecyclerAdapter(getActivity(), receivedUid, receivedUsername, receivedFullname, receivedImagePath);
                        layoutManager = new LinearLayoutManager(getActivity());
                        rvFriendRequests.setLayoutManager(layoutManager);
                        rvFriendRequests.setNestedScrollingEnabled(false);
                        rvFriendRequests.setAdapter(adapter);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    /* Checks and displays if use has any friends */
    private void getFriends(int uid){
        new UserRequest(getActivity()).getFriends(uid, new StringCallback() {
            @Override
            public void done(String string) {
                List<String> friendsFirstLastName = new ArrayList<>();
                List<String> friendsUsername = new ArrayList<>();
                List<Integer> friendsUid = new ArrayList<>();
                List<String> friendsUserImagePath = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String friends = jsonObject.getString("friends");
                    JSONArray jsonArray = new JSONArray(friends);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject friendObject = jsonArray.getJSONObject(i);
                        String friendsProfileImage = friendObject.getString("userProfileImages");
                        JSONArray friendsProfileImageArray = new JSONArray(friendsProfileImage);
                        String userImagePath;
                        if (friendsProfileImageArray.length() > 0){
                            JSONObject friendsProfileImageObject = friendsProfileImageArray.getJSONObject(0);
                            userImagePath = friendsProfileImageObject.getString("userImageThumbnailPath");
                        }
                        else {
                            userImagePath = "empty";
                        }

                        String uid = friendObject.getString("uid");
                        String fullName = friendObject.getString("firstName") + " " + friendObject.getString("lastName");
                        String username = friendObject.getString("username");

                        friendsFirstLastName.add(fullName);
                        friendsUsername.add(username);
                        friendsUid.add(Integer.valueOf(uid));
                        friendsUserImagePath.add(userImagePath);

                        searchFriendsUid.add(Integer.valueOf(uid));
                        searchFriendsFullName.add(fullName);
                        searchFriendsUserImagePath.add(userImagePath);
                        searchFriendsUsername.add(username);

                        adapter = new FriendsRecyclerAdapter(getActivity(), "vertical", friendsFirstLastName, friendsUsername, friendsUid, friendsUserImagePath);
                        layoutManager = new LinearLayoutManager(getActivity());
                        rvFriends.setLayoutManager(layoutManager);
                        rvFriends.setNestedScrollingEnabled(false);
                        rvFriends.setAdapter(adapter);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void searchFriends(String s){
        List<Integer> resultsUid = new ArrayList<>();
        List<String> resultsUsername = new ArrayList<>();
        List<String> resultsFullName = new ArrayList<>();
        List<String> resultsUserImagePath = new ArrayList<>();
        for (int i = 0; i < searchFriendsUsername.size(); i++){
            if (searchFriendsUsername.get(i).toLowerCase().contains(s.toLowerCase()) || searchFriendsFullName.get(i).toLowerCase().contains(s.toLowerCase())){
                resultsUid.add(searchFriendsUid.get(i));
                resultsUsername.add(searchFriendsUsername.get(i));
                resultsFullName.add(searchFriendsFullName.get(i));
                resultsUserImagePath.add(searchFriendsUserImagePath.get(i));
            }
        }
        adapter = new FriendsRecyclerAdapter(getActivity(), "vertical", resultsFullName, resultsUsername, resultsUid, resultsUserImagePath);
        layoutManager = new LinearLayoutManager(getActivity());
        rvFriends.setLayoutManager(layoutManager);
        rvFriends.setNestedScrollingEnabled(false);
        rvFriends.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (from.equals("maintab")){
            MenuItem item = menu.findItem(R.id.search);
            item.setVisible(false);
        }
    }

}
