package brymian.bubbles.bryant.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import brymian.bubbles.R;

/**
 * Created by Almanza on 2/9/2016.
 */
public class EventsCreate extends FragmentActivity implements View.OnClickListener {

    ImageButton ibGoBack;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.events_create);

        ibGoBack = (ImageButton) findViewById(R.id.ibGoBack);

        ibGoBack.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibGoBack:
                startActivity(new Intent(this, Events.class));
                break;
        }
    }
}
