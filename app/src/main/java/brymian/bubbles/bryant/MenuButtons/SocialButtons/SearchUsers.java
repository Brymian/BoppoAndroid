package brymian.bubbles.bryant.MenuButtons.SocialButtons;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.ProfileActivityOLD;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;
import brymian.bubbles.damian.nonactivity.UserListCallback;


public class SearchUsers extends FragmentActivity implements View.OnClickListener, TextWatcher{
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
        setContentView(R.layout.search_users);

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
        final Intent intent = new Intent(this, ProfileActivityOLD.class);
        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        System.out.print("THIS IS FROM onClick(): userUID is " + userUID);
        switch(view.getId()){
            case R.id.tShowFriends0:
                if(tShowFriends0.getText().toString() != "No Results."){
                    final int ID = getSearchedID(0);
                    final String name = getSearchedFirstLastName(0);
                    final String username = getSearchedUsername(0);
                    System.out.println("THIS is tShowFriends0: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(userUID,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("uid", ID);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;


            case R.id.tShowFriends1:
                if(tShowFriends1.getText().toString() != null){
                    final int ID = getSearchedID(1);
                    final String name = getSearchedFirstLastName(1);
                    final String username = getSearchedUsername(1);
                    System.out.println("THIS is tShowFriends1: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(userUID,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("uid", ID);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends2:
                if(tShowFriends2.getText().toString() != null){
                    final int ID = getSearchedID(2);
                    final String name = getSearchedFirstLastName(2);
                    final String username = getSearchedUsername(2);
                    System.out.println("THIS is tShowFriends2: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(userUID,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("uid", ID);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends3:
                if(tShowFriends3.getText().toString() != null){
                    final int ID = getSearchedID(3);
                    final String name = getSearchedFirstLastName(3);
                    final String username = getSearchedUsername(3);
                    System.out.println("THIS is tShowFriends3: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(userUID,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("uid", ID);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends4:
                if(tShowFriends4.getText().toString() != null){
                    final int ID = getSearchedID(4);
                    final String name = getSearchedFirstLastName(4);
                    final String username = getSearchedUsername(4);
                    System.out.println("THIS is tShowFriends4: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(1,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("uid", ID);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends5:
                if(tShowFriends5.getText().toString() != null){
                    final int ID = getSearchedID(5);
                    final String name = getSearchedFirstLastName(5);
                    final String username = getSearchedUsername(5);
                    System.out.println("THIS is tShowFriends5: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(userUID,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("uid", ID);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends6:
                if(tShowFriends6.getText().toString() != null){
                    final int ID = getSearchedID(6);
                    final String name = getSearchedFirstLastName(6);
                    final String username = getSearchedUsername(6);
                    System.out.println("THIS is tShowFriends6: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(userUID,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("uid", ID);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends7:
                if(tShowFriends7.getText().toString() != null){
                    final int ID = getSearchedID(7);
                    final String name = getSearchedFirstLastName(7);
                    final String username = getSearchedUsername(7);
                    System.out.println("THIS is tShowFriends7: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(userUID,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("uid", ID);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends8:
                if(tShowFriends5.getText().toString() != null){
                    final int ID = getSearchedID(8);
                    final String name = getSearchedFirstLastName(8);
                    final String username = getSearchedUsername(8);
                    System.out.println("THIS is tShowFriends8: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(userUID,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("uid", ID);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    });
                }
                break;

            case R.id.tShowFriends9:
                if(tShowFriends9.getText().toString() != null){
                    final int ID = getSearchedID(9);
                    final String name = getSearchedFirstLastName(9);
                    final String username = getSearchedUsername(9);
                    System.out.println("THIS is tShowFriends9: ID is " + ID + ", name is " + name + ", username is " + username);
                    new ServerRequest(this).getFriendStatus(1,ID, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("status", string);
                            intent.putExtra("firstLastName", name);
                            intent.putExtra("uid", ID);
                            intent.putExtra("username", username);
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

    void setSearchedID(int ID, int i){
        searchedUsersID[i] = ID;
    }

    void setSearchedFirstLastName(String name, int i){
        searchedUsersFirstLastName[i] = name;
    }

    void setSearchedUsername(String name, int i){
        searchedUsersUsername[i] = name;
    }

    int getSearchedID(int location){
        return searchedUsersID[location];
    }

    String getSearchedFirstLastName(int location){
        return searchedUsersFirstLastName[location];
    }

    String getSearchedUsername(int location){
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
                        TVIDs[i] = (TextView) findViewById(TVRIDs[i]);
                        String userNameList = users.get(i).getUsername();
                        String firstNameList = users.get(i).getFirstName();
                        String lastNameList = users.get(i).getLastName();
                        int IDList = users.get(i).getUid();

                        TVIDs[i].setText(userNameList);
                        TVIDs[i].setTextSize(15);
                        TVIDs[i].setPadding(0, 5, 0, 5); //left, top, right, bottom
                        TVIDs[i].setTypeface(null, Typeface.BOLD);
                        TVIDs[i].setClickable(true);
                        TVIDs[i].setOnClickListener(SearchUsers.this);

                        System.out.println("THIS IS THE FIRST NAME: " + firstNameList);
                        System.out.println("THIS IS THE LAST NAME: " + lastNameList);
                        System.out.println("This IS THE USERLIST ID: " + IDList);

                        String name = firstNameList + " " + lastNameList;
                        setSearchedID(IDList, i);
                        setSearchedFirstLastName(name, i);
                        setSearchedUsername(userNameList, i);

                    }
                }
                if(size == 0){
                    tShowFriends0 = (TextView) findViewById(R.id.tShowFriends0);
                    tShowFriends0.setText("No Results.");
                }
                /**
                int remSize = TVIDs.length - size;
                if(remSize < 10){
                    TVIDs[TVIDs.length - remSize] = (TextView) findViewById(TVRIDs[TVIDs.length - remSize]);
                    TVIDs[TVIDs.length - remSize].setText(" ");
                }
                 **/
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

