package brymian.bubbles.bryant.events;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import brymian.bubbles.R;


public class EventsYours extends AppCompatActivity {
    Toolbar mToolbar;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.events_yours);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle(R.string.My_Episodes);
        mToolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
