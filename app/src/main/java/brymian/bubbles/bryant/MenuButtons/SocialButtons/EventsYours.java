package brymian.bubbles.bryant.MenuButtons.SocialButtons;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import brymian.bubbles.R;

/**
 * Created by Almanza on 2/10/2016.
 */
public class EventsYours extends FragmentActivity implements View.OnClickListener {

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.events_yours);

    }

    public void onClick(View v){
        switch (v.getId()){

        }
    }
}
