package brymian.bubbles.bryant.MenuButtons;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.bryant.MenuButtons.FriendsButtons.AddFriend;

public class Friends extends FragmentActivity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_friends);
    }
    public void onClickMenu(View v){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
    }
    public void onClickAddFriend(View view){
        Intent AddFriendIntent = new Intent(this, AddFriend.class);
        startActivity(AddFriendIntent);
    }
}
