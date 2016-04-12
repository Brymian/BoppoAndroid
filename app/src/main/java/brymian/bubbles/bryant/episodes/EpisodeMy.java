package brymian.bubbles.bryant.episodes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> eventNameListHosting = new ArrayList<>();
    ArrayList<Integer> eventEidListHosting = new ArrayList<>();
    ArrayList<String> eventNameListAttending = new ArrayList<>();
    ArrayList<Integer> eventEidListAttending = new ArrayList<>();

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.episode_my);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.My_Episodes);

        tvHosting = (TextView) findViewById(R.id.tvHosting);
        tvHosting.setVisibility(View.GONE);
        tvAttending = (TextView) findViewById(R.id.tvAttending);
        tvAttending.setVisibility(View.GONE);
        vDivider = findViewById(R.id.vDivider);
        vDivider.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new EventRequest(this).getEventDataByMember(SaveSharedPreference.getUserUID(this), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {

                for (Event event : eventList) {
                    /* code below is for reference */
                    /*
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Name = " + event.eventName);
                    System.out.println("Event Invite Type Label = " + event.eventInviteTypeLabel);
                    System.out.println("Event Privacy Label = " + event.eventPrivacyLabel);
                    System.out.println("Event Image Upload Allowed Indicator = " + event.eventImageUploadAllowedIndicator);
                    System.out.println("Event Start Datetime = " + event.eventStartDatetime);
                    System.out.println("Event End Datetime = " + event.eventEndDatetime);
                    System.out.println("Event GPS Latitude = " + event.eventGpsLatitude);
                    System.out.println("Event GPS Longitude = " + event.eventGpsLongitude);
                    System.out.println("Event Like Count = " + event.eventLikeCount);
                    System.out.println("Event Dislike Count = " + event.eventDislikeCount);
                    System.out.println("Event View Count = " + event.eventViewCount);
                    */

                    if (event.eventHostUid == SaveSharedPreference.getUserUID(EpisodeMy.this)) {
                        tvHosting.setVisibility(View.VISIBLE);
                        eventNameListHosting.add(event.eventName);
                        eventEidListHosting.add(event.eid);
                        vDivider.setVisibility(View.VISIBLE);
                        tvAttending.setVisibility(View.VISIBLE);
                    }
                }
                recyclerView = (RecyclerView) findViewById(R.id.recyclerView_episode_my_hosted);
                adapter = new EpisodeRecyclerAdapter(eventNameListHosting);
                layoutManager = new LinearLayoutManager(EpisodeMy.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new EpisodeRecyclerItemClickListener(EpisodeMy.this, new EpisodeRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startActivity(new Intent(EpisodeMy.this, EpisodeActivity.class).putExtra("eid", eventEidListHosting.get(position)).putExtra("eventName", eventNameListHosting.get(position)));
                    }
                }));
            }
        });

        new EventRequest(this).getEventDataByMember(SaveSharedPreference.getUserUID(this), new EventListCallback() {
            @Override
            public void done(List<Event> eventList) {

                for (Event event : eventList) {
                    /* code below is for reference */
                    /*
                    System.out.println("EVENT #" + eventList.indexOf(event) + ": ");
                    System.out.println("EID = " + event.eid);
                    System.out.println("Event Host User Identifier = " + event.eventHostUid);
                    System.out.println("Event Name = " + event.eventName);
                    System.out.println("Event Invite Type Label = " + event.eventInviteTypeLabel);
                    System.out.println("Event Privacy Label = " + event.eventPrivacyLabel);
                    System.out.println("Event Image Upload Allowed Indicator = " + event.eventImageUploadAllowedIndicator);
                    System.out.println("Event Start Datetime = " + event.eventStartDatetime);
                    System.out.println("Event End Datetime = " + event.eventEndDatetime);
                    System.out.println("Event GPS Latitude = " + event.eventGpsLatitude);
                    System.out.println("Event GPS Longitude = " + event.eventGpsLongitude);
                    System.out.println("Event Like Count = " + event.eventLikeCount);
                    System.out.println("Event Dislike Count = " + event.eventDislikeCount);
                    System.out.println("Event View Count = " + event.eventViewCount);
                    */

                    if(event.eventHostUid != SaveSharedPreference.getUserUID(EpisodeMy.this)){
                        eventNameListAttending.add(event.eventName);
                        eventEidListAttending.add(event.eid);
                    }

                }


                recyclerView = (RecyclerView) findViewById(R.id.recyclerView_episode_my_attending);
                adapter = new EpisodeRecyclerAdapter(eventNameListAttending);
                layoutManager = new LinearLayoutManager(EpisodeMy.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new EpisodeRecyclerItemClickListener(EpisodeMy.this, new EpisodeRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startActivity(new Intent(EpisodeMy.this, EpisodeActivity.class).putExtra("eid", eventEidListAttending.get(position)).putExtra("eventName", eventNameListAttending.get(position)));
                    }
                }));
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
