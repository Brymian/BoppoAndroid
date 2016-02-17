package brymian.bubbles.bryant.MenuButtons.SocialButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.ProfileActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserCallback;
import brymian.bubbles.damian.nonactivity.UserDataLocal;
import brymian.bubbles.damian.nonactivity.UserListCallback;


public class FriendsList extends FragmentActivity implements View.OnClickListener {
    ImageButton ibMenu;

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


        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        llFriendRequestsTitle = (LinearLayout) findViewById(R.id.llFriendRequestsTitle);
        llFriendRequestListMAIN = (LinearLayout) findViewById(R.id.llFriendRequestsListMAIN);
        llFriendsListTitle = (LinearLayout) findViewById(R.id.llFriendsListTitle);
        llFriendsListMAIN = (LinearLayout) findViewById(R.id.llFriendsListMAIN);
        vMainSeparator = findViewById(R.id.vMainSeparator);

        llFriendRequest0 = (LinearLayout) findViewById(R.id.llFriendRequest0);
        llFriendRequest1 = (LinearLayout) findViewById(R.id.llFriendRequest1);

        tvFriendRequestName0 = (TextView) findViewById(R.id.tvFriendRequestName0);
        tvFriendRequestName1 = (TextView) findViewById(R.id.tvFriendRequestName1);

        ibAccept0 = (TextView) findViewById(R.id.ibAccept0);
        ibAccept1 = (TextView) findViewById(R.id.ibAccept1);
        ibDecline0 = (TextView) findViewById(R.id.ibDecline0);
        ibDecline1 = (TextView) findViewById(R.id.ibDecline1);


        tFriend0 = (TextView) findViewById(R.id.tFriend0);
        tFriend1 = (TextView) findViewById(R.id.tFriend1);
        tFriend2 = (TextView) findViewById(R.id.tFriend2);
        tFriend3 = (TextView) findViewById(R.id.tFriend3);
        tFriend4 = (TextView) findViewById(R.id.tFriend4);
        tFriend5 = (TextView) findViewById(R.id.tFriend5);
        tFriend6 = (TextView) findViewById(R.id.tFriend6);
        tFriend7 = (TextView) findViewById(R.id.tFriend7);
        tFriend8 = (TextView) findViewById(R.id.tFriend8);
        tFriend9 = (TextView) findViewById(R.id.tFriend9);

        tFriend0.setVisibility(View.GONE);
        tFriend1.setVisibility(View.GONE);
        tFriend2.setVisibility(View.GONE);
        tFriend3.setVisibility(View.GONE);
        tFriend4.setVisibility(View.GONE);
        tFriend5.setVisibility(View.GONE);
        tFriend6.setVisibility(View.GONE);
        tFriend7.setVisibility(View.GONE);
        tFriend8.setVisibility(View.GONE);
        tFriend9.setVisibility(View.GONE);

        llFriendRequestsTitle.setVisibility(View.GONE);
        llFriendRequestListMAIN.setVisibility(View.GONE);
        vMainSeparator.setVisibility(View.GONE);
        llFriendsListTitle.setVisibility(View.GONE);
        llFriendsListMAIN.setVisibility(View.GONE);

        ibAccept0.setVisibility(View.GONE);
        ibDecline0.setVisibility(View.GONE);
        ibAccept1.setVisibility(View.GONE);
        ibDecline1.setVisibility(View.GONE);

        ibMenu.setOnClickListener(this);

        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();

        setProfileUserUID(userUID);
        profileUserFriendsList(userUID);
        profileUserFriendRequests(userUID);
    }

    public void onClick(View v){
        final Intent profileIntent = new Intent(this, ProfileActivity.class);
        final String friendStatus = "Already friends with user.";
        switch (v.getId()){
            case R.id.ibMenu:
                startActivity(new Intent(this, MenuActivity.class));
                break;
            case R.id.tvFriendRequestName0:

                startActivity(profileIntent);
                break;
            case R.id.tvFriendRequestName1:
                startActivity(profileIntent);
                break;
            case R.id.ibAccept0:

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
        new ServerRequest(this).getFriends(uid, new UserListCallback() {
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
        new ServerRequest(this).getUserFriendRequestUsers(uid, new UserListCallback() {
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
                                new ServerRequest(FriendsList.this).getUserData(users.get(i).getUid(), new UserCallback() {
                                    @Override
                                    public void done(User user) {
                                        friendRequestFirstLastName.add(j, user.getFirstName() + " " + user.getLastName());
                                        acceptIDS[j] = (TextView) findViewById(acceptRIDS[j]);
                                        declineIDS[j] = (TextView) findViewById(declineRIDS[j]);
                                        tvFriendRequestNames[j] = (TextView) findViewById(tvFriendRequestNamesRIDS[j]);
                                        acceptIDS[j].setVisibility(View.VISIBLE);
                                        declineIDS[j].setVisibility(View.VISIBLE);
                                        tvFriendRequestNames[j].setVisibility(View.VISIBLE);
                                        tvFriendRequestNames[j].setText(user.getFirstName() + " " + user.getLastName());
                                        tvFriendRequestNames[j].setTextSize(20);
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
