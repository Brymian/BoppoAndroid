package brymian.bubbles.bryant.episodes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.EventCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.objects.Event;

/**
 * Created by Almanza on 4/6/2016.
 */
public class EpisodeActivity extends AppCompatActivity {
    Toolbar mToolbar;
    TextView tvDeleteEvent;
    int eid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_activity);

        /*--------------------------------Checking for putExtras()--------------------------------*/
        String eventName;
        int eid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventName = null;
                eid = 0;
            }
            else {
                eventName = extras.getString("eventName");
                eid = extras.getInt("eid");
            }
        }
        else {
            eventName= savedInstanceState.getString("eventName");
            eid = savedInstanceState.getInt("eid");
        }
        /*----------------------------------------------------------------------------------------*/
        setEid(eid);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(eventName);
        tvDeleteEvent = (TextView) findViewById(R.id.tvDeleteEvent);
        tvDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EventRequest(EpisodeActivity.this).deleteEvent(getEid(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        System.out.println(string);
                    }
                });
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new EventRequest(this).getEventData(eid, new EventCallback() {
            @Override
            public void done(Event event) {
                /* code below is for reference */
                /*
                System.out.println("THE FOLLOWING EVENT DATA HAS BEEN RETURNED: ");
                System.out.println();
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
