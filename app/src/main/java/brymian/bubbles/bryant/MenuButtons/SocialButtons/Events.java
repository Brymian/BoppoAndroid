package brymian.bubbles.bryant.MenuButtons.SocialButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;

/**
 * Created by Almanza on 2/9/2016.
 */
public class Events extends FragmentActivity implements View.OnClickListener{
    ImageButton ibMenu, ibSearchEvents;
    TextView tvCreateEvent, tvYourEvents, tvMoreTopEvents;
    LinearLayout llTopEvents, llYourEvents, llEventRequests;
    LinearLayout llTopEvent1, llTopEvent2, llTopEvent3, llTopEvent4, llTopEvent5;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.events);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        ibSearchEvents = (ImageButton) findViewById(R.id.ibSearchEvents);

        tvYourEvents = (TextView) findViewById(R.id.tvYourEvents);
        tvMoreTopEvents = (TextView) findViewById(R.id.tvMoreTopEvents);
        tvCreateEvent = (TextView) findViewById(R.id.tvCreateEvent);


        llEventRequests = (LinearLayout) findViewById(R.id.llEventRequests);
        llTopEvents = (LinearLayout) findViewById(R.id.llTopEvents);
        llYourEvents = (LinearLayout) findViewById(R.id.llYourEvents);
        llTopEvent1 = (LinearLayout) findViewById(R.id.llTopEvent1);
        llTopEvent2 = (LinearLayout) findViewById(R.id.llTopEvent2);
        llTopEvent3 =(LinearLayout) findViewById(R.id.llTopEvent3);
        llTopEvent4 = (LinearLayout) findViewById(R.id.llTopEvent4);
        llTopEvent5 = (LinearLayout) findViewById(R.id.llTopEvent5);


        ibMenu.setOnClickListener(this);
        ibSearchEvents.setOnClickListener(this);
        tvCreateEvent.setOnClickListener(this);
        llTopEvent1.setOnClickListener(this);
        llTopEvent2.setOnClickListener(this);
        llTopEvent3.setOnClickListener(this);
        llTopEvent4.setOnClickListener(this);
        llTopEvent5.setOnClickListener(this);
        tvYourEvents.setOnClickListener(this);
        tvMoreTopEvents.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibMenu:
                startActivity(new Intent(this, MenuActivity.class));
                break;
            case R.id.ibSearchEvents:
                startActivity(new Intent(this, EventsSearch.class));
                break;
            case R.id.tvCreateEvent:
                startActivity(new Intent(this, EventsCreate.class));
                break;
            case R.id.tvYourEvents:
                startActivity(new Intent(this, EventsYours.class));
                break;
            case R.id.tvMoreTopEvents:
                startActivity(new Intent(this, EventsTop.class));
                break;
        }

    }

}
