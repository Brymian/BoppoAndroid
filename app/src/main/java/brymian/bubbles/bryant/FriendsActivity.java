package brymian.bubbles.bryant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;
import brymian.bubbles.damian.nonactivity.UserListCallback;


public class FriendsActivity extends FragmentActivity implements View.OnClickListener{
    ImageButton bMenu;
    Button bSearchFriend;
    EditText eInputUser;
    TextView tShowFriends0, tShowFriends1, tShowFriends2, tShowFriends3, tShowFriends4, tShowFriends5;
    TextView[] TVIDs = {tShowFriends0, tShowFriends1, tShowFriends2, tShowFriends3, tShowFriends4, tShowFriends5};
    int[] TVRIDs = {R.id.tShowFriends0, R.id.tShowFriends1, R.id.tShowFriends2, R.id.tShowFriends3, R.id.tShowFriends4, R.id.tShowFriends5};
    int[] searchedUsersID = new int[6];
    String[] searchedUsersFirstLastName = new String[6];
    String[] searchedUsersUsername = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        bSearchFriend = (Button) findViewById(R.id.bSearchFriend);

        eInputUser  = (EditText) findViewById(R.id.eInputUser);

        bMenu = (ImageButton) findViewById(R.id.bMenu);

        tShowFriends0 = (TextView) findViewById(R.id.tShowFriends0);
        tShowFriends1 = (TextView) findViewById(R.id.tShowFriends1);
        tShowFriends2 = (TextView) findViewById(R.id.tShowFriends2);
        tShowFriends3 = (TextView) findViewById(R.id.tShowFriends3);
        tShowFriends4 = (TextView) findViewById(R.id.tShowFriends4);
        tShowFriends5 = (TextView) findViewById(R.id.tShowFriends5);

        tShowFriends0.setClickable(true);
        tShowFriends1.setClickable(true);
        tShowFriends2.setClickable(true);
        tShowFriends3.setClickable(true);
        tShowFriends4.setClickable(true);
        tShowFriends5.setClickable(true);

        bSearchFriend.setOnClickListener(this);

        bMenu.setOnClickListener(this);

        tShowFriends0.setOnClickListener(this);
        tShowFriends1.setOnClickListener(this);
        tShowFriends2.setOnClickListener(this);
        tShowFriends3.setOnClickListener(this);
        tShowFriends4.setOnClickListener(this);
        tShowFriends5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        final Intent intent = new Intent(this, ProfileActivity.class);
        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        switch(view.getId()){
            case R.id.bSearchFriend:

                if(eInputUser.getText().toString().isEmpty()){
                    Toast.makeText(this, "Empty Search.", Toast.LENGTH_SHORT).show();
                }
                else if(eInputUser.getText().toString() != null)
                {
                    new ServerRequest(this).getUsers(eInputUser.getText().toString(), new UserListCallback() {
                        @Override
                        public void done(List<User> userList) {
                            int size = userList.size();
                            if(size > 0) {
                                for (int i = 0; i < size; i++) {
                                    TVIDs[i] = (TextView) findViewById(TVRIDs[i]);
                                    String userNameList = userList.get(i).getUsername();
                                    String firstNameList = userList.get(i).getFirstName();
                                    String lastNameList = userList.get(i).getLastName();
                                    int IDList = userList.get(i).getUid();

                                    TVIDs[i].setText(userNameList + "\n" + firstNameList + " " + lastNameList);

                                    System.out.println("THIS IS THE FIRST NAME: " + firstNameList);
                                    System.out.println("THIS IS THE LAST NAME: " + lastNameList);
                                    System.out.println("This IS THE USERLIST ID: " + IDList);

                                    String name = firstNameList + " " + lastNameList;
                                    tempIDHold(IDList, i);
                                    tempFirstLastNameHold(name, i);
                                    tempUsernameHold(userNameList, i);
                                }
                            }
                            if(size == 0){
                                tShowFriends0 = (TextView) findViewById(R.id.tShowFriends0);
                                tShowFriends0.setText("No Results.");
                            }
                        }
                    });
                    Toast.makeText(this, "Search Complete.", Toast.LENGTH_SHORT).show();
                }
                else{
                    tShowFriends0 = (TextView) findViewById(R.id.tShowFriends0);
                    tShowFriends0.setText("No Users Found!");
                }
                break;


            case R.id.tShowFriends0:
                if(tShowFriends0.getText().toString() != "No Results."){
                    final int ID = returnTempIDHold(0);
                    final String name = returnTempFirstLastName(0);
                    final String username = returnTempUsername(0);
                    new ServerRequest(this).getFriendStatus(1,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("Friend_Status", string);
                            intent.putExtra("Friend_FirstLastName", name);
                            intent.putExtra("Friend_UID", ID);
                            intent.putExtra("Friend_Username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends1:
                if(tShowFriends1.getText().toString() != null){
                    final int ID = returnTempIDHold(1);
                    final String name = returnTempFirstLastName(1);
                    final String username = returnTempUsername(1);
                    new ServerRequest(this).getFriendStatus(1,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("Friend_Status", string);
                            intent.putExtra("Friend_FirstLastName", name);
                            intent.putExtra("Friend_UID", ID);
                            intent.putExtra("Friend_Username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends2:
                if(tShowFriends2.getText().toString() != null){
                    final int ID = returnTempIDHold(2);
                    final String name = returnTempFirstLastName(2);
                    final String username = returnTempUsername(2);
                    new ServerRequest(this).getFriendStatus(1,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("Friend_Status", string);
                            intent.putExtra("Friend_Username", name);
                            intent.putExtra("Friend_UID", ID);
                            intent.putExtra("Friend_Username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends3:
                if(tShowFriends3.getText().toString() != null){
                    final int ID = returnTempIDHold(3);
                    final String name = returnTempFirstLastName(3);
                    final String username = returnTempUsername(3);
                    new ServerRequest(this).getFriendStatus(1,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("Friend_Status", string);
                            intent.putExtra("Friend_Username", name);
                            intent.putExtra("Friend_UID", ID);
                            intent.putExtra("Friend_Username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends4:
                if(tShowFriends4.getText().toString() != null){
                    final int ID = returnTempIDHold(4);
                    final String name = returnTempFirstLastName(4);
                    final String username = returnTempUsername(4);
                    new ServerRequest(this).getFriendStatus(1,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("Friend_Status", string);
                            intent.putExtra("Friend_Username", name);
                            intent.putExtra("Friend_UID", ID);
                            intent.putExtra("Friend_Username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends5:
                if(tShowFriends5.getText().toString() != null){
                    final int ID = returnTempIDHold(5);
                    final String name = returnTempFirstLastName(5);
                    final String username = returnTempUsername(5);
                    new ServerRequest(this).getFriendStatus(1,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("Friend_Status", string);
                            intent.putExtra("Friend_Username", name);
                            intent.putExtra("Friend_UID", ID);
                            intent.putExtra("Friend_Username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.bMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
        }
    }

    void tempIDHold(int ID, int i){
        searchedUsersID[i] = ID;
    }

    void tempFirstLastNameHold(String name, int i){
        searchedUsersFirstLastName[i] = name;
    }

    void tempUsernameHold(String name, int i){
        searchedUsersUsername[i] = name;
    }

    int returnTempIDHold(int location){
        return searchedUsersID[location];
    }

    String returnTempFirstLastName(int location){
        return searchedUsersFirstLastName[location];
    }

    String returnTempUsername(int location){
        return searchedUsersUsername[location];
    }
}
