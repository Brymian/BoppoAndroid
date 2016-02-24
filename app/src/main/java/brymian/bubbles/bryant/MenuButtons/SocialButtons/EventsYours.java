package brymian.bubbles.bryant.MenuButtons.SocialButtons;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import brymian.bubbles.R;

/**
 * Created by Almanza on 2/10/2016.
 */
public class EventsYours extends FragmentActivity implements View.OnClickListener {
    ImageButton ibGoBack;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.events_yours);

        ibGoBack = (ImageButton) findViewById(R.id.ibGoBack);

        ibGoBack.setOnClickListener(this);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibGoBack:
                finish();
                break;
        }
    }
}
