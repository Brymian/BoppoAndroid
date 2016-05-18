package brymian.bubbles.bryant.episodes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.objects.Event;


public class EpisodeActivity extends AppCompatActivity {
    Toolbar mToolbar;
    TextView tvEpisodeTitle, tvEpisodeHostName;
    FloatingActionButton fabComment, fabPlay, fabMap, fabParticipants, fabSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_activity);

        /*--------------------------------Checking for putExtras()--------------------------------*/
        int eid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eid = 0;
            }
            else {
                eid = extras.getInt("eid");
            }
        }
        else {
            eid = savedInstanceState.getInt("eid");
        }
        /*----------------------------------------------------------------------------------------*/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        tvEpisodeTitle = (TextView) findViewById(R.id.tvEpisodeTitle);
        tvEpisodeHostName = (TextView) findViewById(R.id.tvEpisodeHostName);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        incrementViewCount(eid);
        getEpisodeInfo(eid);
        fabComment = (FloatingActionButton) findViewById(R.id.fabComment);
        fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);
        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabParticipants = (FloatingActionButton) findViewById(R.id.fabParticipants);
        fabSettings = (FloatingActionButton) findViewById(R.id.fabSettings);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getEpisodeInfo(int eid){
        new EventRequest(this).getEventData(eid, new EventCallback() {
            @Override
            public void done(Event event) {
                //tvEpisodeTitle.setText(event.eventName);
                tvEpisodeHostName.setText(event.eventHostFirstName + " " + event.eventHostLastName);
                Log.e("Host", event.eventHostFirstName + " " + event.eventHostLastName);
                //Log.e("Title", event.eventName);
            }
        });
    }

    private void incrementViewCount(int eid){
        new EventRequest(this).incrementEventViewCount(eid, new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("View Count", string);
            }
        });
    }


}
