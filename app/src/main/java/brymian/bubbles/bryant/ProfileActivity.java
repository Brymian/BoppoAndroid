package brymian.bubbles.bryant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.Image;
import brymian.bubbles.damian.nonactivity.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;


public class ProfileActivity extends FragmentActivity implements View.OnClickListener{
    TextView tProfileName;
    ImageButton bMenu, bLeft, bRight, bMiddle;
    RelativeLayout MainLayout;
    int[] IDhold = new int[1];
    String[] firstLastNameArray = new String[1];
    String[] userNameArray = new String[1];
    String[] friendStatusArray = new String[1];
    int[] loggedInUserUID = new int[1];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Linking xml IDs with java IDs
        tProfileName = (TextView) findViewById(R.id.tProfileName);
        MainLayout = (RelativeLayout) findViewById(R.id.MainLayout);

        bMiddle = (ImageButton) findViewById(R.id.bMiddle);
        bMenu = (ImageButton) findViewById(R.id.bMenu);
        bLeft = (ImageButton) findViewById(R.id.bLeft);
        bRight = (ImageButton) findViewById(R.id.bRight);

        //Getting the information from SearchUsers using putExtra()
        String friendStatusString;
        String firstLastName;
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                friendStatusString = null;
                firstLastName = null;
                uid = 0;
            } else {
                friendStatusString = extras.getString("status");
                firstLastName = extras.getString("firstLastName");
                uid = extras.getInt("uid");
            }
        } else {
            friendStatusString = (String) savedInstanceState.getSerializable("status");
            firstLastName = (String) savedInstanceState.getSerializable("firstLastName");
            uid = savedInstanceState.getInt("uid");
        }

        //Checking for output
        System.out.println("THIS IS FROM PROFILE ACTIVITY (status): " + friendStatusString);
        System.out.println("THIS IS FROM PROFILE ACTIVITY (firstLastName): " + firstLastName);
        System.out.println("THIS IS FROM PROFILE ACTIVITY (uid): " + uid);
        //-------------------------------------------------------------------------------------

        setButtons(friendStatusString);
        setFriendStatus(friendStatusString);
        setID(uid);
        setFirstLastName(firstLastName);
        System.out.println("getFirstLastName(): " + getFirstLastName());

        tProfileName.setText(getFirstLastName());


        bMenu.setOnClickListener(this);
        bMiddle.setOnClickListener(this);
        bLeft.setOnClickListener(this);
        bRight.setOnClickListener(this);

        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        setLoggedInUserUID(userUID);

        new ServerRequest(this).getImages(uid, "Regular", new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                if(imageList.size() == 0){
                    Toast.makeText(ProfileActivity.this, "User has no Regular Public pics.", Toast.LENGTH_SHORT).show();
                }
                else{
                    new DownloadImage(imageList.get(0).getPath()).execute();
                }
            }
        });
    }

    void setButtons(String friendStatus){
        Drawable blockingDrawable = getResources().getDrawable(R.mipmap.blockblack_nopadding);
        Drawable addfriendDrawable = getResources().getDrawable(R.mipmap.addfriend_nopadding);
        Drawable friendslistDrawable = getResources().getDrawable(R.mipmap.friendslist_nopadding);
        Drawable globeDrawable = getResources().getDrawable(R.mipmap.globeblackwhite_nopadding);
        switch(friendStatus){
            case "Not friends.":
                bMiddle.setImageDrawable(addfriendDrawable);
                bLeft.setImageDrawable(blockingDrawable);
                bRight.setImageDrawable(globeDrawable);
                break;
            case "Already sent friend request to user.":
                bMiddle.setImageDrawable(addfriendDrawable);
                //bAddFriend.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                bLeft.setImageDrawable(blockingDrawable);
                bRight.setImageDrawable(globeDrawable);
                break;
            case "Already friends with user.":
                bMiddle.setImageDrawable(friendslistDrawable);
                bLeft.setImageDrawable(blockingDrawable);
                bRight.setImageDrawable(globeDrawable);
                break;
            case "Logged in user.":
                bMiddle.setImageDrawable(friendslistDrawable);
                bLeft.setImageDrawable(blockingDrawable);
                bRight.setImageDrawable(globeDrawable);
                //setFirstLastName("Logged in user.");//Need to change this to logged in user's name.
                break;
            case "User is awaiting confirmation for friend request.":
                bMiddle.setImageDrawable(addfriendDrawable);
                bLeft.setImageDrawable(blockingDrawable);
                bRight.setImageDrawable(globeDrawable);
                break;
            case "User is currently being blocked.":

                break;
            case "Currently being blocked by user.":

                break;
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bMiddle:
                System.out.println("this is return temp user who hold: " + getFirstLastName());
                if(getFriendStatus().equals("Logged in user.")){
                    Intent friendListIntent = new Intent(this, FriendsList.class);
                    startActivity(friendListIntent);
                }
                else {
                    new ServerRequest(this).setFriendStatus(getLoggedInUserUID(), getID(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(ProfileActivity.this, string, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                break;
            case R.id.bMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.bLeft:
                //Intent cameraIntent = new Intent(this, CameraActivity.class);
                //startActivity(cameraIntent);
                break;
            case R.id.bRight:
                Intent mapIntent = new Intent(this, MapsActivity.class);
                String firstLastName = getFirstLastName();
                String username = getUsername();
                int UID = getID();

                if(getFriendStatus().equals("Logged in user.")){
                    mapIntent.putExtra("firstLastName", "Logged in user.");
                    //mapIntent.putExtra("username", "Your Map");
                    mapIntent.putExtra("uid", getID());
                    startActivity(mapIntent);
                }
                else {
                    mapIntent.putExtra("firstLastName", firstLastName);
                    mapIntent.putExtra("username", username);
                    mapIntent.putExtra("uid", UID);
                    startActivity(mapIntent);
                }
                break;
        }
    }
//


    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String name;

        public DownloadImage(String name){
            this.name = name;
        }


        @Override
        protected Bitmap doInBackground(Void... params){


            //String url = SERVER_ADDRESS + "Uploads/" + name + ".JPG";
            try {
                String url = name;
                System.out.println("getPath(): " + name);
                URLConnection connection = new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);

                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            if(bitmap != null){
                //downloadedImage.setImageBitmap(bitmap);
                Drawable drawable = new BitmapDrawable(bitmap);
                MainLayout.setBackgroundDrawable(drawable);
            }
        }
    }

    private HttpParams getHttpRequestParams(){
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;
    }

    void setID(int uid){
        IDhold[0] = uid;
    }

    void setFirstLastName(String input){
        firstLastNameArray[0] = input;
    }

    void setUsername(String input){
        userNameArray[0] = input;
    }

    void setFriendStatus(String input){
        friendStatusArray[0] = input;
    }

    void setLoggedInUserUID(int uid){
        loggedInUserUID[0] = uid;
    }

    int getID(){
        return IDhold[0];
    }

    String getFirstLastName(){
        return firstLastNameArray[0];
    }

    String getUsername(){
        return userNameArray[0];
    }

    String getFriendStatus(){
        return friendStatusArray[0];
    }

    int getLoggedInUserUID(){
        return loggedInUserUID[0];
    }
}
