package brymian.bubbles.bryant.episodes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.MiscellaneousRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserLikeRequest;
import brymian.bubbles.objects.Event;


public class EpisodeActivity extends Activity implements View.OnClickListener{
    TextView tvEpisodeTitle, tvEpisodeHostName, tvLikeCount, tvDislikeCount, tvViewCount;
    FloatingActionButton fabLike, fabDislike, fabGoBack, fabPlay, fabMap, fabComment, fabParticipants, fabSettings;
    int eid;
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
        tvLikeCount = (TextView) findViewById(R.id.tvLikeCount);
        tvDislikeCount = (TextView) findViewById(R.id.tvDislikeCount);
        tvViewCount = (TextView) findViewById(R.id.tvViewCount);
        setEid(eid);
        incrementViewCount(eid);
        getEpisodeInfo(eid);
        setFloatingActionButtons();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabLike:
                new UserLikeRequest(EpisodeActivity.this).setObjectLikeOrDislike(SaveSharedPreference.getUserUID(EpisodeActivity.this), "Event", getEid(), true, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("Like", string);
                    }
                });

                break;

            case R.id.fabDislike:
                new UserLikeRequest(EpisodeActivity.this).setObjectLikeOrDislike(SaveSharedPreference.getUserUID(EpisodeActivity.this), "Event", getEid(), false, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("Dislike", string);
                    }
                });
                break;

            case R.id.fabGoBack:
                onBackPressed();
                break;
        }
    }

    private void setFloatingActionButtons(){
        fabLike = (FloatingActionButton) findViewById(R.id.fabLike);
        fabLike.setOnClickListener(this);
        fabDislike = (FloatingActionButton) findViewById(R.id.fabDislike);
        fabDislike.setOnClickListener(this);
        fabGoBack = (FloatingActionButton) findViewById(R.id.fabGoBack);
        fabGoBack.setOnClickListener(this);
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
                tvEpisodeTitle.setText(event.eventName);
                tvEpisodeHostName.setText(event.eventHostFirstName + " " + event.eventHostLastName);
                tvLikeCount.setText(String.valueOf(event.eventLikeCount));
                tvDislikeCount.setText(String.valueOf(event.eventDislikeCount));
                tvViewCount.setText(String.valueOf(event.eventViewCount));
                Log.e("View Count", String.valueOf(event.eventViewCount));//returning null
            }
        });
    }

    private void incrementViewCount(int eid){
        new MiscellaneousRequest(this).incrementObjectViewCount(eid, "Event", new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("View Count", string);
            }
        });
    }

    private void setEid(int eid){
        this.eid = eid;
    }

    private int getEid(){
        return eid;
    }

}
