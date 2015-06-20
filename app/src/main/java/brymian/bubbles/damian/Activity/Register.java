package brymian.bubbles.damian.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import brymian.bubbles.damian.NonActivity.GetUserCallback;
import brymian.bubbles.damian.NonActivity.ServerRequests;
import brymian.bubbles.damian.NonActivity.User;
import brymian.bubbles.R;

public class Register extends ActionBarActivity implements View.OnClickListener {

    Button bRegister;
    EditText etUsername, etPassword;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bRegister) {

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            User user = new User(username, password);

            registerUser(user);
        }
    }

    private void registerUser(User user) {

        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {

                System.out.println("Now going to Register...");
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }
}
