package brymian.bubbles.damian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import brymian.bubbles.bryant.MapsActivity;
import brymian.bubbles.damian.NonActivity.User;
import brymian.bubbles.damian.NonActivity.UserStoreLocal;
import brymian.bubbles.R;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button bLogout;
    EditText etUsername;
    UserStoreLocal userStoreLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.etUsername);
        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);

        userStoreLocal = new UserStoreLocal(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (authenticate()) {
            //displayUserDetails();
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }
        else {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }

    private boolean authenticate() {
        return userStoreLocal.getUserLoggedStatus();
    }

    private void displayUserDetails() {
        User user = userStoreLocal.getUserData();
        etUsername.setText(user.username);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bLogout) {
            userStoreLocal.resetUserData();
            userStoreLocal.setUserLoggedStatus(false);
            startActivity(new Intent(this, Login.class));
        }
    }
}
