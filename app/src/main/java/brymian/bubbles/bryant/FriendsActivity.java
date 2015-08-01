package brymian.bubbles.bryant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.FriendsButtons.AddFriend;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.User;

public class FriendsActivity extends FragmentActivity{
    Button mButton;
    EditText mEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        mButton = (Button)findViewById(R.id.AddAFriend);
        mEdit  = (EditText)findViewById(R.id.SearchUsers);
        final String searched_user = mEdit.getText().toString();
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        /**
                        if(searched_user == null){
                            System.out.println("you fucked up");
                        }
                        else{
                            new ServerRequest(this).getUsers(searched_user, new UserCallback() {

                                        @Override
                                        public void done(Set<User> users) {

                                            // In here, the request was made to get a list of users

                                            // that match the string. The "users" variable contains

                                            // a Java set of users matching the searched query.

                                            // Write your code here to handle the rest.

                                        }

                                    });

                        }
                         **/
                    }
                });
    }

    //public void onClickAddFriend(View view){
        //Intent AddFriendIntent = new Intent(this, AddFriend.class);
        //startActivity(AddFriendIntent);
        /**EditText searchUsers = (EditText) findViewById(R.id.SearchUsers);
        String string = searchUsers.getText().toString();
        if (searchUsers.getText().toString() == "."){
            Context context = getApplicationContext();
            CharSequence text = "String is empty";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        }
         **/


   // }
}
