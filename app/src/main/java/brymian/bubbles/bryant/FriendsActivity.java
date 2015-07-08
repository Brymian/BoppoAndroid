package brymian.bubbles.bryant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuButtons.FriendsButtons.AddFriend;

public class FriendsActivity extends FragmentActivity{

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_friends);
        }

    public void onClickAddFriend(View view){
        Intent AddFriendIntent = new Intent(this, AddFriend.class);
        startActivity(AddFriendIntent);
    }
}
