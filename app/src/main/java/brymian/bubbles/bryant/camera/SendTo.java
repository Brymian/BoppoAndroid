package brymian.bubbles.bryant.camera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import brymian.bubbles.R;

/**
 * Created by Almanza on 3/18/2016.
 */
public class SendTo extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    Toolbar mToolbar;
    TextView tvProfilePicture, tvCurrentLocation;
    CheckBox cbProfilePicture, cbCurrentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_to);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.Send_To);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvProfilePicture = (TextView) findViewById(R.id.tvProfilePicture);
        tvCurrentLocation = (TextView) findViewById(R.id.tvCurrentLocation);
        cbProfilePicture = (CheckBox) findViewById(R.id.cbProfilePicture);
        cbProfilePicture.setOnCheckedChangeListener(this);
        cbCurrentLocation = (CheckBox) findViewById(R.id.cbCurrentLocation);
        cbCurrentLocation.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
