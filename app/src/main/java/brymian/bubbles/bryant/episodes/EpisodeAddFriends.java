package brymian.bubbles.bryant.episodes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

import brymian.bubbles.R;

/**
 * Created by Almanza on 4/5/2016.
 */
public class EpisodeAddFriends extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_add_friends);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
