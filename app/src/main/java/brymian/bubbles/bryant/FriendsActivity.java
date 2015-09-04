package brymian.bubbles.bryant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button bSearchFriend;
    EditText eInputUser;
    TextView tShowFriends0, tShowFriends1, tShowFriends2, tShowFriends3, tShowFriends4, tShowFriends5;
    TextView[] TVIDs = {tShowFriends0, tShowFriends1, tShowFriends2, tShowFriends3, tShowFriends4, tShowFriends5};
    int[] TVRIDs = {R.id.tShowFriends0, R.id.tShowFriends1, R.id.tShowFriends2, R.id.tShowFriends3, R.id.tShowFriends4, R.id.tShowFriends5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        bSearchFriend = (Button) findViewById(R.id.bSearchFriend);
        //bSearchFriend.setText("Search");

        eInputUser  = (EditText) findViewById(R.id.eInputUser);

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
        User user = udl.getUserData();
        int userUID = user.getUid();
        switch(view.getId()){
            case R.id.bSearchFriend:
                if(eInputUser.getText().toString() != null)
                {
                    new ServerRequest(this).getUsers(eInputUser.getText().toString(), new UserListCallback() {
                        @Override
                        public void done(List<User> userList) {
                            // The userList is a list of User objects, as can be seen
                            // in the User class. Below are just some examples of what
                            // user methods
                            int size = userList.size();


                            if(size > 0) {
                                for (int i = 0; i < size; i++) {
                                    TVIDs[i] = (TextView) findViewById(TVRIDs[i]);
                                    TVIDs[i].setText(userList.get(i).getUsername());
                                    int intentUID = userList.get(i).getUid();
                                    intent.putExtra("Friend_UID", intentUID);
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
                    Toast.makeText(this, "you did this right", Toast.LENGTH_SHORT).show();

                    new ServerRequest(this).getFriendStatus(1, 2, new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println(string);
                            intent.putExtra("Friend_Status", string);
                            intent.putExtra("Friend_Username", tShowFriends0.getText().toString());
                            startActivity(intent);
                        }
                    });
                }
                break;
            case R.id.tShowFriends1:
                if(tShowFriends1.getText().toString() != null){

                }
                break;
            case R.id.tShowFriends2:
                if(tShowFriends2.getText().toString() != null){

                }
                break;
            case R.id.tShowFriends3:
                if(tShowFriends3.getText().toString() != null){

                }
                break;
            case R.id.tShowFriends4:
                if(tShowFriends4.getText().toString() != null){

                }
                break;
            case R.id.tShowFriends5:
                if(tShowFriends5.getText().toString() != null){

                }
                break;
        }
    }



}
