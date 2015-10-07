package brymian.bubbles.bryant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
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


public class FriendsActivity extends FragmentActivity implements View.OnClickListener, TextWatcher{
    ImageButton bMenu;
    EditText eInputUser;
    TextView tShowFriends0, tShowFriends1, tShowFriends2, tShowFriends3, tShowFriends4, tShowFriends5, tShowFriends6, tShowFriends7, tShowFriends8, tShowFriends9;
    TextView[] TVIDs = {tShowFriends0, tShowFriends1, tShowFriends2, tShowFriends3, tShowFriends4, tShowFriends5, tShowFriends6, tShowFriends7, tShowFriends8, tShowFriends9};
    int[] TVRIDs = {R.id.tShowFriends0, R.id.tShowFriends1, R.id.tShowFriends2, R.id.tShowFriends3, R.id.tShowFriends4, R.id.tShowFriends5, R.id.tShowFriends6, R.id.tShowFriends7, R.id.tShowFriends8, R.id.tShowFriends9};
    int[] searchedUsersID = new int[10];
    String[] searchedUsersFirstLastName = new String[10];
    String[] searchedUsersUsername = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        eInputUser  = (EditText) findViewById(R.id.eInputUser);
        eInputUser.addTextChangedListener(this);

        bMenu = (ImageButton) findViewById(R.id.bMenu);

        tShowFriends0 = (TextView) findViewById(R.id.tShowFriends0);
        tShowFriends1 = (TextView) findViewById(R.id.tShowFriends1);
        tShowFriends2 = (TextView) findViewById(R.id.tShowFriends2);
        tShowFriends3 = (TextView) findViewById(R.id.tShowFriends3);
        tShowFriends4 = (TextView) findViewById(R.id.tShowFriends4);
        tShowFriends5 = (TextView) findViewById(R.id.tShowFriends5);
        tShowFriends6 = (TextView) findViewById(R.id.tShowFriends6);
        tShowFriends7 = (TextView) findViewById(R.id.tShowFriends7);
        tShowFriends8 = (TextView) findViewById(R.id.tShowFriends8);
        tShowFriends9 = (TextView) findViewById(R.id.tShowFriends9);

        tShowFriends0.setClickable(false);
        tShowFriends1.setClickable(false);
        tShowFriends2.setClickable(false);
        tShowFriends3.setClickable(false);
        tShowFriends4.setClickable(false);
        tShowFriends5.setClickable(false);
        tShowFriends6.setClickable(false);
        tShowFriends7.setClickable(false);
        tShowFriends8.setClickable(false);
        tShowFriends9.setClickable(false);

        bMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        final Intent intent = new Intent(this, ProfileActivity.class);
        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        switch(view.getId()){
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

            case R.id.tShowFriends6:
                if(tShowFriends6.getText().toString() != null){
                    final int ID = returnTempIDHold(6);
                    final String name = returnTempFirstLastName(6);
                    final String username = returnTempUsername(6);
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

            case R.id.tShowFriends7:
                if(tShowFriends7.getText().toString() != null){
                    final int ID = returnTempIDHold(7);
                    final String name = returnTempFirstLastName(7);
                    final String username = returnTempUsername(7);
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

            case R.id.tShowFriends8:
                if(tShowFriends5.getText().toString() != null){
                    final int ID = returnTempIDHold(8);
                    final String name = returnTempFirstLastName(8);
                    final String username = returnTempUsername(8);
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

            case R.id.tShowFriends9:
                if(tShowFriends9.getText().toString() != null){
                    final int ID = returnTempIDHold(9);
                    final String name = returnTempFirstLastName(9);
                    final String username = returnTempUsername(9);
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

    @Override
    public void afterTextChanged(Editable s){
        new ServerRequest(this).getUsers(eInputUser.getText().toString(), new UserListCallback() {
            @Override
            public void done(List<User> users) {
                int size = 0;
                try {
                    size = users.size();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                if(size > 0) {
                    for (int i = 0; i < size; i++) {
                        //TVIDs[i] = (TextView) findViewById(TVRIDs[i]);
                        String userNameList = users.get(i).getUsername();
                        String firstNameList = users.get(i).getFirstName();
                        String lastNameList = users.get(i).getLastName();
                        int IDList = users.get(i).getUid();

                        TVIDs[i].setText(userNameList);
                        TVIDs[i].setClickable(true);
                        TVIDs[i].setOnClickListener(FriendsActivity.this);

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
                int remSize = TVIDs.length - size;
                if(remSize < 10){
                    TVIDs[TVIDs.length - remSize] = (TextView) findViewById(TVRIDs[TVIDs.length - remSize]);
                    TVIDs[TVIDs.length - remSize].setText(" ");
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence sequence, int start, int count, int after){

    }

    @Override
    public void onTextChanged(CharSequence sequence, int start, int before, int count){

    }
}

