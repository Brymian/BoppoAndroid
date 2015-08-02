package brymian.bubbles.bryant;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;

import brymian.bubbles.damian.nonactivity.Post;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserListCallback;
import brymian.bubbles.damian.nonactivity.VoidCallback;


public class FriendsActivity extends ActionBarActivity implements View.OnClickListener{
    Button bAddFriend;
    EditText eInputUser;
    public TextView tShowFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        bAddFriend = (Button) findViewById(R.id.bAddFriend);
        bAddFriend.setText("Add");
        eInputUser  = (EditText) findViewById(R.id.eInputUser);
        //tShowFriends = (TextView) findViewById(R.id.tShowFriends);


        bAddFriend.setOnClickListener(this);
    }

    public void getUsers(String searched_user, UserListCallback userListCallback) {
        //pd.show();
        new GetUsers(searched_user, userListCallback).execute();
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.bAddFriend:
                if(eInputUser.getText().toString() != null)
                {
                    //String userReceiving = eInputUser.getText().toString();
                    //AddFriend(userSending,userReceiving);
                    getUsers(eInputUser.getText().toString(), new UserListCallback() {
                        @Override
                        public void done(List<User> user) {
                            //DAMIAANNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN
                            //
                            // DAMIAN HEREEEEEE
                            //
                            // HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE!!!!!!!!!!!
                            System.out.println("this is the size: " + user.size());
                            int size = user.size();
                            tShowFriends = (TextView) findViewById(R.id.tShowFriends);
                            if (size == 0){
                                tShowFriends.setText("No Results");
                            }
                            else{
                                tShowFriends.setText("");
                            }
                        }
                    });
                    Toast.makeText(getApplicationContext(), "Got it.", Toast.LENGTH_SHORT);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Empty String", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    /**
    public void AddFriend(String userSending, String userReceiving){

    }
     **/


    private class GetUsers extends AsyncTask<Void, Void, List<User>> {

        String searched_user;
        UserListCallback userListCallback;

        private GetUsers(String searched_user, UserListCallback userListCallback) {
            this.searched_user = searched_user;
            this.userListCallback = userListCallback;
        }

        @Override
        protected List<User> doInBackground(Void... params) {
            final String SERVER = "http://73.194.170.63:8080/ProjectWolf/";
            //final String SERVER = "http://192.168.1.12:8080/ProjectWolf/";
            String url = SERVER + "Database/getUsers.php";

            System.out.println("SEARCHED USER: " + searched_user);
            //String searched_user = "";
            String jsonSearchedUser = "{\"searched_username\":\"" + searched_user + "\"}";
            Post request = new Post();
            try {
                String response = request.post(url, jsonSearchedUser);
                System.out.println("RESPONSE: " + response);
                if (response.equals("INCORRECT STRING.")) {
                    System.out.println(response);
                    return null;
                } else {
                    JSONArray j2dArray = new JSONArray(response);
                    List<User> userList = new ArrayList<User>();
                    for (int i = 0; i < j2dArray.length(); i++) {
                        JSONObject jUser = (JSONObject) j2dArray.get(i);
                        int uid = jUser.getInt("uid");
                        String username = jUser.getString("username");
                        String namefirst = jUser.getString("namefirst");
                        String namelast = jUser.getString("namelast");
                        User user = new User();
                        user.setUser(uid, null, -1, username, null, namefirst, namelast, null);
                        userList.add(user);
                    }
                    // The set will be null if nothing matched in the database
                    return userList;
                }
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
                return null;
            } catch (JSONException jsone) {
                jsone.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<User> users) {
            //pd.dismiss();
            userListCallback.done(users);

            super.onPostExecute(users);
        }

    }
}
