package brymian.bubbles.bryant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserListCallback;


public class FriendsList extends FragmentActivity implements View.OnClickListener {
    ImageButton ibMenu;
    TextView tUsersName,tFriend0, tFriend1, tFriend2, tFriend3, tFriend4,tFriend5,tFriend6,tFriend7,tFriend8,tFriend9;
    TextView[] TVIDS = {tFriend0, tFriend1, tFriend2, tFriend3, tFriend4,tFriend5,tFriend6,tFriend7,tFriend8,tFriend9};
    int[] TVRIDS = {R.id.tFriend0,R.id.tFriend1,R.id.tFriend2,R.id.tFriend3,R.id.tFriend4,R.id.tFriend5,R.id.tFriend6,R.id.tFriend7,R.id.tFriend8,R.id.tFriend9};
    String[] friendUserNameTempHold = new String[10];
    String[] friendNameTempHold = new String[10];
    int[] friendUIDTempHold = new int[10];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendslist);


        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        tUsersName = (TextView) findViewById(R.id.tUsersName);

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

        ibMenu.setOnClickListener(this);
        tUsersName.setOnClickListener(this);

        returnFriendsListFromServer(1);
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
                final int uid0 = getUID(0);
                final String firstLastName0 = getFirstLastName(0);
                final String username0 = getUserName(0);

                profileIntent.putExtra("firstLastName", firstLastName0);
                profileIntent.putExtra("username", username0);
                profileIntent.putExtra("UID", uid0);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
            case R.id.tFriend1:
                final int uid1 = getUID(1);
                final String firstLastName1 = getFirstLastName(1);
                final String username1 = getUserName(1);

                profileIntent.putExtra("firstLastName", firstLastName1);
                profileIntent.putExtra("username", username1);
                profileIntent.putExtra("UID", uid1);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
            case R.id.tFriend2:
                final int uid2 = getUID(2);
                final String firstLastName2 = getFirstLastName(2);
                final String username2 = getUserName(2);

                profileIntent.putExtra("firstLastName", firstLastName2);
                profileIntent.putExtra("username", username2);
                profileIntent.putExtra("UID", uid2);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
            case R.id.tFriend3:
                final int uid3 = getUID(3);
                final String firstLastName3 = getFirstLastName(3);
                final String username3 = getUserName(3);

                profileIntent.putExtra("firstLastName", firstLastName3);
                profileIntent.putExtra("username", username3);
                profileIntent.putExtra("UID", uid3);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
            case R.id.tFriend4:
                final int uid4 = getUID(4);
                final String firstLastName4 = getFirstLastName(4);
                final String username4 = getUserName(4);

                profileIntent.putExtra("firstLastName", firstLastName4);
                profileIntent.putExtra("username", username4);
                profileIntent.putExtra("UID", uid4);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
            case R.id.tFriend5:
                final int uid5 = getUID(5);
                final String firstLastName5 = getFirstLastName(5);
                final String username5 = getUserName(5);

                profileIntent.putExtra("firstLastName", firstLastName5);
                profileIntent.putExtra("username", username5);
                profileIntent.putExtra("UID", uid5);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
            case R.id.tFriend6:
                final int uid6 = getUID(6);
                final String firstLastName6 = getFirstLastName(6);
                final String username6 = getUserName(6);

                profileIntent.putExtra("firstLastName", firstLastName6);
                profileIntent.putExtra("username", username6);
                profileIntent.putExtra("UID", uid6);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
            case R.id.tFriend7:
                final int uid7 = getUID(7);
                final String firstLastName7 = getFirstLastName(7);
                final String username7 = getUserName(7);

                profileIntent.putExtra("firstLastName", firstLastName7);
                profileIntent.putExtra("username", username7);
                profileIntent.putExtra("UID", uid7);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
            case R.id.tFriend8:
                final int uid8 = getUID(8);
                final String firstLastName8 = getFirstLastName(8);
                final String username8 = getUserName(8);

                profileIntent.putExtra("firstLastName", firstLastName8);
                profileIntent.putExtra("username", username8);
                profileIntent.putExtra("UID", uid8);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;
            case R.id.tFriend9:
                final int uid9 = getUID(9);
                final String firstLastName9 = getFirstLastName(9);
                final String username9 = getUserName(9);

                profileIntent.putExtra("firstLastName", firstLastName9);
                profileIntent.putExtra("username", username9);
                profileIntent.putExtra("UID", uid9);
                profileIntent.putExtra("status", friendStatus);
                startActivity(profileIntent);
                break;

        }
    }

    public void returnFriendsListFromServer(int uid){
        new ServerRequest(this).getFriends(uid, new UserListCallback() {
            @Override
            public void done(List<User> users) {
                int friendListSize = 0;
                try{
                    friendListSize = users.size();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                if (friendListSize > 0){
                    for(int i = 0; i < friendListSize; i++){
                        String friendUsername = users.get(i).getUsername();
                        String friendFirstName = users.get(i).getFirstName();
                        String friendLastName = users.get(i).getLastName();
                        int friendUID = users.get(i).getUid();

                        TVIDS[i].setVisibility(View.VISIBLE);
                        TVIDS[i].setText(friendUsername);
                        TVIDS[i].setOnClickListener(FriendsList.this);

                        String firstLastName = friendFirstName + " " + friendLastName;
                        setFirstLastName(i, firstLastName);
                        setUID(i, friendUID);
                        setUsername(i, friendUsername);
                    }
                }
                else if (friendListSize == 0){

                }
            }
        });
    }

    void setUsername(int i, String userName){
        friendUserNameTempHold[i] = userName;
    }

    void  setFirstLastName(int i, String friendName){
        friendNameTempHold[i] = friendName;
    }

    void setUID(int i, int UID){
        friendUIDTempHold[i] = UID;
    }

    String getUserName(int location){
        return friendUserNameTempHold[location];
    }

    String getFirstLastName(int location){
        return friendNameTempHold[location];
    }

    int getUID(int location){
        return friendUIDTempHold[location];
    }
}
