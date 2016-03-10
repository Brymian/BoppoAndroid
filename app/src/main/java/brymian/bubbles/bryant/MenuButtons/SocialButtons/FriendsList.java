package brymian.bubbles.bryant.MenuButtons.SocialButtons;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.ProfileActivityOLD;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserCallback;
import brymian.bubbles.damian.nonactivity.UserDataLocal;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;


public class FriendsList extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;

    //------------ Accept/Decline TextView, TextView arrays, and int array of R.id.(accept/decline) FOR FRIEND REQUESTS ------------------------
    TextView ibAccept0, ibAccept1,ibAccept2, ibAccept3, ibAccept4, ibAccept5, ibAccept6, ibAccept7, ibAccept8, ibAccept9;
    TextView ibDecline0, ibDecline1, ibDecline2,ibDecline3, ibDecline4, ibDecline5, ibDecline6, ibDecline7, ibDecline8, ibDecline9;
    TextView[] acceptIDS = {ibAccept0, ibAccept1,ibAccept2, ibAccept3, ibAccept4, ibAccept5, ibAccept6, ibAccept7, ibAccept8, ibAccept9};
    TextView[] declineIDS = {ibDecline0, ibDecline1, ibDecline2,ibDecline3, ibDecline4, ibDecline5, ibDecline6, ibDecline7, ibDecline8, ibDecline9};
    int[] acceptRIDS = {R.id.ibAccept0, R.id.ibAccept1, R.id.ibAccept2, R.id.ibAccept3, R.id.ibAccept4,  R.id.ibAccept5,  R.id.ibAccept6,  R.id.ibAccept7, R.id.ibAccept8, R.id.ibAccept8, R.id.ibAccept9};
    int[] declineRIDS = {R.id.ibDecline0, R.id.ibDecline1, R.id.ibDecline2, R.id.ibDecline3,R.id.ibDecline4, R.id.ibDecline5, R.id.ibDecline6, R.id.ibDecline7, R.id.ibDecline8, R.id.ibDecline9};


    View vMainSeparator;
    LinearLayout llFriendRequestsTitle, llFriendRequestListMAIN, llFriendsListTitle, llFriendsListMAIN;

    //----------------MAIN LINEAR LAYOUT FOR EACH FRIEND REQUEST ------------------------------------------------
    LinearLayout llFriendRequest0, llFriendRequest1, llFriendRequest2, llFriendRequest3, llFriendRequest4, llFriendRequest5, llFriendRequest6, llFriendRequest7, llFriendRequest8, llFriendRequest9;
    LinearLayout[] llFriendRequests = {llFriendRequest0, llFriendRequest1, llFriendRequest2, llFriendRequest3, llFriendRequest4, llFriendRequest5, llFriendRequest6, llFriendRequest7, llFriendRequest8, llFriendRequest9};
    int[] llFriendRequestsRIDS = {R.id.llFriendRequest0, R.id.llFriendRequest1, R.id.llFriendRequest2, R.id.llFriendRequest3, R.id.llFriendRequest4, R.id.llFriendRequest5, R.id.llFriendRequest6, R.id.llFriendRequest7, R.id.llFriendRequest8, R.id.llFriendRequest9};

    TextView tvFriendRequestName0, tvFriendRequestName1, tvFriendRequestName2, tvFriendRequestName3, tvFriendRequestName4, tvFriendRequestName5, tvFriendRequestName6, tvFriendRequestName7, tvFriendRequestName8, tvFriendRequestName9;
    TextView[] tvFriendRequestNames = {tvFriendRequestName0, tvFriendRequestName1, tvFriendRequestName2, tvFriendRequestName3, tvFriendRequestName4, tvFriendRequestName5, tvFriendRequestName6, tvFriendRequestName7, tvFriendRequestName8, tvFriendRequestName9};
    int[] tvFriendRequestNamesRIDS = {R.id.tvFriendRequestName0, R.id.tvFriendRequestName1, R.id.tvFriendRequestName2, R.id.tvFriendRequestName3, R.id.tvFriendRequestName4, R.id.tvFriendRequestName5, R.id.tvFriendRequestName6, R.id.tvFriendRequestName7, R.id.tvFriendRequestName8, R.id.tvFriendRequestName9};

    TextView tFriend0, tFriend1, tFriend2, tFriend3, tFriend4,tFriend5,tFriend6,tFriend7,tFriend8,tFriend9;
    TextView[] TVIDS = {tFriend0, tFriend1, tFriend2, tFriend3, tFriend4,tFriend5,tFriend6,tFriend7,tFriend8,tFriend9};
    int[] TVRIDS = {R.id.tFriend0,R.id.tFriend1,R.id.tFriend2,R.id.tFriend3,R.id.tFriend4,R.id.tFriend5,R.id.tFriend6,R.id.tFriend7,R.id.tFriend8,R.id.tFriend9};

    int[] profileUserUID = new int[1];


    //String[] friendUserNameTempHold = new String[10];
    //String[] friendNameTempHold = new String[10];
    //int[] friendUIDTempHold = new int[10];

    ArrayList<Integer> profileUserFriendUID = new ArrayList<>();
    ArrayList<String> profileUserFriendFirstLastName = new ArrayList<>();
    ArrayList<Integer> friendRequestUID = new ArrayList<>();
    ArrayList<String> friendRequestFirstLastName = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendslist);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("Friends");
        mToolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        llFriendRequestsTitle = (LinearLayout) findViewById(R.id.llFriendRequestsTitle);
        llFriendRequestListMAIN = (LinearLayout) findViewById(R.id.llFriendRequestsListMAIN);
        llFriendsListTitle = (LinearLayout) findViewById(R.id.llFriendsListTitle);
        llFriendsListMAIN = (LinearLayout) findViewById(R.id.llFriendsListMAIN);
        vMainSeparator = findViewById(R.id.vMainSeparator);

        //Friend request Linear Layout, Friend Request name, Accept/Decline buttons will always be the same amount
        for(int i = 0; i < llFriendRequests.length; i++){
            llFriendRequests[i] = (LinearLayout) findViewById(llFriendRequestsRIDS[i]);
            tvFriendRequestNames[i] = (TextView) findViewById(tvFriendRequestNamesRIDS[i]);
            acceptIDS[i] = (TextView) findViewById(acceptRIDS[i]);
            declineIDS[i] = (TextView) findViewById(declineRIDS[i]);

            llFriendRequests[i].setVisibility(View.GONE);
            tvFriendRequestNames[i].setVisibility(View.GONE);
            acceptIDS[i].setVisibility(View.GONE);
            declineIDS[i].setVisibility(View.GONE);
        }

        for(int i = 0; i < TVIDS.length; i++){
            TVIDS[i] = (TextView) findViewById(TVRIDS[i]);
            TVIDS[i].setVisibility(View.GONE);
        }

        llFriendRequestsTitle.setVisibility(View.GONE);
        llFriendRequestListMAIN.setVisibility(View.GONE);
        vMainSeparator.setVisibility(View.GONE);
        llFriendsListTitle.setVisibility(View.GONE);
        llFriendsListMAIN.setVisibility(View.GONE);


        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();

        setProfileUserUID(userUID);
        profileUserFriendsList(userUID);
        profileUserFriendRequests(userUID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    public void onClick(View v){
        final Intent profileIntent = new Intent(this, ProfileActivityOLD.class);
        final String friendStatus = "Already friends with user.";
        switch (v.getId()){
            case R.id.ibMenu:
                startActivity(new Intent(this, MenuActivity.class));
                break;
            case R.id.tvFriendRequestName0:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(0));
                profileIntent.putExtra("uid", friendRequestUID.get(0));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName1:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(1));
                profileIntent.putExtra("uid", friendRequestUID.get(1));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName2:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(2));
                profileIntent.putExtra("uid", friendRequestUID.get(2));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName3:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(3));
                profileIntent.putExtra("uid", friendRequestUID.get(3));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName4:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(4));
                profileIntent.putExtra("uid", friendRequestUID.get(4));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName5:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(5));
                profileIntent.putExtra("uid", friendRequestUID.get(5));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName6:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(6));
                profileIntent.putExtra("uid", friendRequestUID.get(6));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName7:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(7));
                profileIntent.putExtra("uid", friendRequestUID.get(7));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName8:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(18));
                profileIntent.putExtra("uid", friendRequestUID.get(8));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName9:
                profileIntent.putExtra("firstLastName", friendRequestFirstLastName.get(9));
                profileIntent.putExtra("uid", friendRequestUID.get(9));
                profileIntent.putExtra("status","User is awaiting confirmation for friend request.");
                startActivity(profileIntent);
                break;
            case R.id.ibAccept0:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(0), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[0].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibAccept1:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(1), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[1].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibAccept2:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(2), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[2].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibAccept3:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(3), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[3].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibAccept4:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(4), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[4].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibAccept5:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(5), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[5].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibAccept6:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(6), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[6].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibAccept7:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(7), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[7].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibAccept8:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(8), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[8].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibAccept9:
                new ServerRequestMethods(this).setFriendStatus(getProfileUserUID(), friendRequestUID.get(9), new StringCallback() {
                    @Override
                    public void done(String string) {
                        llFriendRequests[9].setVisibility(View.GONE);
                        Toast.makeText(FriendsList.this, string, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.ibDecline0:

                break;
            case R.id.tFriend0:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(0));
                //profileIntent.putExtra("username", profileUserFriendUsername.get(0));
                profileIntent.putExtra("uid", profileUserFriendUID.get(0));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

            case R.id.tFriend1:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(1));
                //profileIntent.putExtra("username", profileUserFriendUsername.get(1));
                profileIntent.putExtra("uid", profileUserFriendUID.get(1));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

            case R.id.tFriend2:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(2));
                //profileIntent.putExtra("username", profileUserFriendUsername.get(2));
                profileIntent.putExtra("uid", profileUserFriendUID.get(2));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

            case R.id.tFriend3:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(3));
                //profileIntent.putExtra("username", profileUserFriendUsername.get(3));
                profileIntent.putExtra("uid", profileUserFriendUID.get(3));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

            case R.id.tFriend4:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(4));
               // profileIntent.putExtra("username", profileUserFriendUsername.get(4));
                profileIntent.putExtra("uid", profileUserFriendUID.get(4));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

            case R.id.tFriend5:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(5));
                //profileIntent.putExtra("username", profileUserFriendUsername.get(5));
                profileIntent.putExtra("uid", profileUserFriendUID.get(5));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

            case R.id.tFriend6:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(6));
                //profileIntent.putExtra("username", profileUserFriendUsername.get(6));
                profileIntent.putExtra("uid", profileUserFriendUID.get(6));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

            case R.id.tFriend7:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(7));
                //profileIntent.putExtra("username", profileUserFriendUsername.get(7));
                profileIntent.putExtra("uid", profileUserFriendUID.get(7));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

            case R.id.tFriend8:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(8));
               // profileIntent.putExtra("username", profileUserFriendUsername.get(8));
                profileIntent.putExtra("uid", profileUserFriendUID.get(8));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

            case R.id.tFriend9:
                profileIntent.putExtra("firstLastName", profileUserFriendFirstLastName.get(9));
                //profileIntent.putExtra("username", profileUserFriendUsername.get(9));
                profileIntent.putExtra("uid", profileUserFriendUID.get(9));
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
        }
    }

    void profileUserFriendsList(int uid){
        new ServerRequestMethods(this).getFriends(uid, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                try {
                    System.out.println("FriendsList size: " + users.size());
                    int friendListSize = users.size();
                    if (friendListSize > 0) {
                        for (int i = 0; i < friendListSize; i++) {
                            profileUserFriendUID.add(i, users.get(i).getUid());
                            profileUserFriendFirstLastName.add(i, users.get(i).getFirstName() + " " + users.get(i).getLastName());
                            llFriendsListMAIN.setVisibility(View.VISIBLE);
                            TVIDS[i] = (TextView) findViewById(TVRIDS[i]);
                            TVIDS[i].setVisibility(View.VISIBLE);
                            TVIDS[i].setText(users.get(i).getFirstName() + " " + users.get(i).getLastName());
                            TVIDS[i].setClickable(true);
                            TVIDS[i].setOnClickListener(FriendsList.this);
                            TVIDS[i].setTextSize(20);
                            TVIDS[i].setTextColor(Color.BLACK);
                        }
                    }
                    else if (friendListSize == 0) {
                        Toast.makeText(FriendsList.this, "No friends", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void profileUserFriendRequests(int uid){
        new ServerRequestMethods(this).getUserFriendRequestUsers(uid, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                try { //users.size() may throw exception
                    if (users.size() > 0) { //if the size of friend requests is greater then 0, loop through them
                        for (int i = 0; i < users.size(); i++) { //loop through all the friend requests users UID
                            if (users.get(i).getUid() != getProfileUserUID()) { //if the friend request user UID is NOT equal to the logged in user, proceed to set layout and text
                                llFriendRequestsTitle.setVisibility(View.VISIBLE);
                                llFriendRequestListMAIN.setVisibility(View.VISIBLE);
                                vMainSeparator.setVisibility(View.VISIBLE);
                                llFriendsListTitle.setVisibility(View.VISIBLE);
                                friendRequestUID.add(i, users.get(i).getUid());
                                //setLocationArrayList(i);
                                System.out.println("printing i before final: "+i);
                                final int j = i;
                                System.out.println("printing j: " + j);
                                new ServerRequestMethods(FriendsList.this).getUserData(users.get(i).getUid(), new UserCallback() {
                                    @Override
                                    public void done(User user) {
                                        friendRequestFirstLastName.add(j, user.getFirstName() + " " + user.getLastName());
                                        llFriendRequests[j].setVisibility(View.VISIBLE);
                                        acceptIDS[j] = (TextView) findViewById(acceptRIDS[j]);
                                        declineIDS[j] = (TextView) findViewById(declineRIDS[j]);
                                        tvFriendRequestNames[j] = (TextView) findViewById(tvFriendRequestNamesRIDS[j]);
                                        acceptIDS[j].setVisibility(View.VISIBLE);
                                        declineIDS[j].setVisibility(View.VISIBLE);
                                        tvFriendRequestNames[j].setVisibility(View.VISIBLE);
                                        tvFriendRequestNames[j].setText(user.getFirstName() + " " + user.getLastName());
                                        tvFriendRequestNames[j].setTextSize(20);
                                        tvFriendRequestNames[j].setTextColor(Color.BLACK);
                                        tvFriendRequestNames[j].setOnClickListener(FriendsList.this);
                                    }
                                });
                            }
                        }

                    }
                } catch (IndexOutOfBoundsException ioob) {
                    ioob.printStackTrace();
                    Toast.makeText(FriendsList.this, "No friend requests", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    int[] locationArrayList = new int[1];
    void setLocationArrayList(int i){
        locationArrayList[0] = i;
    }
    int getLocationArrayList(){
        return locationArrayList[0];
    }

    void setProfileUserUID(int uid){
        profileUserUID[0] = uid;
    }

    int getProfileUserUID(){
        return profileUserUID[0];
    }
}
