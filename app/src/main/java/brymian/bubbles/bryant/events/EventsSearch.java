package brymian.bubbles.bryant.events;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import brymian.bubbles.R;

/**
 * Created by Almanza on 2/10/2016.
 */
public class EventsSearch extends FragmentActivity implements View.OnClickListener{

    ImageButton ibGoBack;
    EditText etSearchEvents;
    TextWatcher searchEventsTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.events_search);

        ibGoBack = (ImageButton) findViewById(R.id.ibGoBack);
        etSearchEvents = (EditText) findViewById(R.id.etSearchEvents);

        ibGoBack.setOnClickListener(this);
        etSearchEvents.addTextChangedListener(searchEventsTextWatcher);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibGoBack:
                startActivity(new Intent(this, Events.class));
                break;
        }
    }
}
