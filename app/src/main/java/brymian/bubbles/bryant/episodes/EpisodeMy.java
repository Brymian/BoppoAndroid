package brymian.bubbles.bryant.episodes;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.objects.Event;


public class EpisodeMy extends AppCompatActivity {
    Toolbar mToolbar;
    View vDivider;
    TextView tvHosting, tvAttending;
    RecyclerView rvEpisodesHosting, rvEpisodesAttending;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;


    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.episode_my);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.My_Episodes);

        rvEpisodesHosting = (RecyclerView) findViewById(R.id.rvEpisodesHosting);
        rvEpisodesAttending = (RecyclerView) findViewById(R.id.rvEpisodesAttending);


        tvHosting = (TextView) findViewById(R.id.tvHosting);
        tvHosting.setVisibility(View.GONE);
        tvAttending = (TextView) findViewById(R.id.tvAttending);
        tvAttending.setVisibility(View.GONE);
        vDivider = findViewById(R.id.vDivider);
        vDivider.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getHostingEpisodes();
        getAttendingEpisodes();
    }

    private void getHostingEpisodes(){
        new EventRequest(this).getEventDataByMember(SaveSharedPreference.getUserUID(this), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                Log.e("Hosting", String.valueOf(eventList.size()));
                if(eventList.size() > 0) {
                    ArrayList<String> eventTitleHosting = new ArrayList<>();
                    ArrayList<Integer> eventEidHosting = new ArrayList<>();
                    for (Event event : eventList) {

                        if (event.eventHostUid == SaveSharedPreference.getUserUID(EpisodeMy.this)) {
                            tvHosting.setVisibility(View.VISIBLE);
                            eventTitleHosting.add(event.eventName);
                            eventEidHosting.add(event.eid);
                            vDivider.setVisibility(View.VISIBLE);
                            tvAttending.setVisibility(View.VISIBLE);
                        }
                    }
                    adapter = new EpisodeHostingRecyclerAdapter(EpisodeMy.this, eventTitleHosting, eventEidHosting);
                    layoutManager = new LinearLayoutManager(EpisodeMy.this);
                    rvEpisodesHosting.setLayoutManager(layoutManager);
                    rvEpisodesHosting.setAdapter(adapter);
                }
            }
        });
    }

    private void getAttendingEpisodes(){
        new EventRequest(this).getEventDataByMember(SaveSharedPreference.getUserUID(this), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {
                if(eventList.size() > 0) {
                    List<String> episodeTitleAttending = new ArrayList<>();
                    List<String> episodeHostNameAttending = new ArrayList<>();
                    List<Integer> episodeEidAttending = new ArrayList<>();
                    for (Event event : eventList) {
                        if (event.eventHostUid != SaveSharedPreference.getUserUID(EpisodeMy.this)) {
                            episodeTitleAttending.add(event.eventName);
                            episodeHostNameAttending.add(event.eventHostFirstName + " " + event.eventHostLastName);
                            episodeEidAttending.add(event.eid);
                            Log.e("Attending title event", event.eventName);
                        }
                    }

                    adapter = new EpisodeAttendingRecyclerAdapter(EpisodeMy.this, episodeTitleAttending, episodeHostNameAttending, episodeEidAttending);
                    layoutManager = new LinearLayoutManager(EpisodeMy.this);
                    rvEpisodesAttending.setLayoutManager(layoutManager);
                    rvEpisodesAttending.setAdapter(adapter);
                }
            }
        });
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
