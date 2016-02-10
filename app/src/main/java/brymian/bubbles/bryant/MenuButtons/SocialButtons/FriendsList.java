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
import brymian.bubbles.damian.nonactivity.UserDataLocal;
import brymian.bubbles.damian.nonactivity.UserListCallback;


public class FriendsList extends FragmentActivity implements View.OnClickListener {
    ImageButton ibMenu;
    LinearLayout llFriendRequestsTitle, llFriendRequestListMAIN, llFriendsListTitle;
    TextView tUsersName,tFriend0, tFriend1, tFriend2, tFriend3, tFriend4,tFriend5,tFriend6,tFriend7,tFriend8,tFriend9;
    TextView[] TVIDS = {tFriend0, tFriend1, tFriend2, tFriend3, tFriend4,tFriend5,tFriend6,tFriend7,tFriend8,tFriend9};
    int[] TVRIDS = {R.id.tFriend0,R.id.tFriend1,R.id.tFriend2,R.id.tFriend3,R.id.tFriend4,R.id.tFriend5,R.id.tFriend6,R.id.tFriend7,R.id.tFriend8,R.id.tFriend9};

    int[] profileUserUID = new int[1];


    //String[] friendUserNameTempHold = new String[10];
    //String[] friendNameTempHold = new String[10];
    //int[] friendUIDTempHold = new int[10];

    ArrayList<Integer> profileUserFriendUID = new ArrayList<>();
    ArrayList<String> profileUserFriendFirstLastName = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendslist);


        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        llFriendRequestsTitle = (LinearLayout) findViewById(R.id.llFriendRequestsTitle);
        llFriendRequestListMAIN = (LinearLayout) findViewById(R.id.llFriendRequestsListMAIN);
        llFriendsListTitle = (LinearLayout) findViewById(R.id.llFriendsListTitle);

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
        llFriendsListTitle.setVisibility(View.GONE);

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
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
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
                System.out.println(users.size());
                try {
                    int friendListSize = users.size();
                    if (friendListSize > 0) {
                        for (int i = 0; i < friendListSize; i++) {
                            profileUserFriendUID.add(i, users.get(i).getUid());
                            profileUserFriendFirstLastName.add(i, users.get(i).getFirstName() + " " + users.get(i).getLastName());

                            TVIDS[i] = (TextView) findViewById(TVRIDS[i]);
                            TVIDS[i].setVisibility(View.VISIBLE);
                            System.out.println(users.get(i).getFirstName());
                            TVIDS[i].setText(users.get(i).getFirstName() + " " + users.get(i).getLastName());
                            TVIDS[i].setClickable(true);
                            TVIDS[i].setOnClickListener(FriendsList.this);
                            TVIDS[i].setTextSize(20);


                        }
                    } else if (friendListSize == 0) {
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
                try {
                    System.out.println("users.size(): " + users.size() + ", users.get(0).getUid(): " + users.get(0).getUid());
                    if(users.size() > 0){
                        llFriendRequestsTitle.setVisibility(View.VISIBLE);

                    }
                } catch (IndexOutOfBoundsException ioob) {
                    ioob.printStackTrace();
                    Toast.makeText(FriendsList.this, "No friend requests", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void setProfileUserUID(int uid){
        profileUserUID[0] = uid;
    }

    int getProfileUserUID(){
        return profileUserUID[0];
    }
}
