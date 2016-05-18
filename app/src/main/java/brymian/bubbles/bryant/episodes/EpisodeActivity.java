package brymian.bubbles.bryant.episodes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.objects.Event;


public class EpisodeActivity extends Activity {
    TextView tvEpisodeTitle, tvEpisodeHostName;
    FloatingActionButton fabGoBack, fabPlay, fabMap, fabComment, fabParticipants, fabSettings;
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

        tvEpisodeTitle = (TextView) findViewById(R.id.tvEpisodeTitle);
        tvEpisodeHostName = (TextView) findViewById(R.id.tvEpisodeHostName);
        incrementViewCount(eid);
        getEpisodeInfo(eid);
        setFloatingActionButtons();
    }

    private void setFloatingActionButtons(){
        fabGoBack = (FloatingActionButton) findViewById(R.id.fabGoBack);
        fabGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);

        fabSettings = (FloatingActionButton) findViewById(R.id.fabSettings);
        fabComment = (FloatingActionButton) findViewById(R.id.fabComment);
        fabParticipants = (FloatingActionButton) findViewById(R.id.fabParticipants);
        fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
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
