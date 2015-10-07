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
        switch (v.getId()){
            case R.id.ibMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.tFriend0:

                break;
            case R.id.tFriend1:

                break;
            case R.id.tFriend2:

                break;
            case R.id.tFriend3:

                break;
            case R.id.tFriend4:

                break;
            case R.id.tFriend5:

                break;
            case R.id.tFriend6:

                break;
            case R.id.tFriend7:

                break;
            case R.id.tFriend8:

                break;
            case R.id.tFriend9:

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
                        nameTempHold(i, firstLastName);
                        UIDTempHold(i, friendUID);
                        userNameTempHold(i, friendUsername);
                    }
                }
                else if (friendListSize == 0){

                }
            }
        });
    }

    void userNameTempHold(int i, String userName){
        friendUserNameTempHold[i] = userName;
    }

    void  nameTempHold(int i, String friendName){
        friendNameTempHold[i] = friendName;
    }

    void UIDTempHold(int i, int UID){
        friendUIDTempHold[i] = UID;
    }

    String returnUserName(int location){
        return friendUserNameTempHold[location];
    }

    String returnName(int location){
        return friendNameTempHold[location];
    }

    int returnUID(int location){
        return friendUIDTempHold[location];
    }
}
