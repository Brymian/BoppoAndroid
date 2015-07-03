package brymian.bubbles.damian.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import brymian.bubbles.R;

public class NewLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
