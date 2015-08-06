package brymian.bubbles.damian.activity;

import android.app.Activity;
import android.os.Bundle;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;

/**
 * Created by Ziomster on 7/29/2015.
 */
public class TESTActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
    }

    private void test() {

        /*
        new ServerRequest(this).getUsers("Damian", new UserListCallback() {
            @Override
            public void done(List<User> userList) {

            }
        });
        */
        new ServerRequest(this).getFriendStatus(1, 5, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        new ServerRequest(this).getFriendStatus(1, 4, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        new ServerRequest(this).setFriendStatus(1, 4, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
        new ServerRequest(this).getFriendStatus(1, 4, new StringCallback() {
            @Override
            public void done(String string) {
                System.out.println(string);
            }
        });
    }

}