package brymian.bubbles.damian.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import brymian.bubbles.R;

public class AuthenticateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
