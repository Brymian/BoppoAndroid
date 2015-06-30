package brymian.bubbles.damian.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import brymian.bubbles.R;

public class MainActivity extends AppCompatActivity {

    // private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}