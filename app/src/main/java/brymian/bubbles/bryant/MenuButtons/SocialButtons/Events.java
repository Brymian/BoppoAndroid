package brymian.bubbles.bryant.MenuButtons.SocialButtons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;

/**
 * Created by Almanza on 2/9/2016.
 */
public class Events extends FragmentActivity implements View.OnClickListener{
    ImageButton ibMenu;
    TextView tvCreateEvent;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.events);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        tvCreateEvent = (TextView) findViewById(R.id.tvCreateEvent);

        ibMenu.setOnClickListener(this);
        tvCreateEvent.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibMenu:
                startActivity(new Intent(this, MenuActivity.class));
                break;
            case R.id.tvCreateEvent:
                startActivity(new Intent(this, EventsCreate.class));
                break;
        }

    }

}
