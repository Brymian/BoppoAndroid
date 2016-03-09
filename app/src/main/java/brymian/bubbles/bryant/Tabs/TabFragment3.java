package brymian.bubbles.bryant.Tabs;

/**
 * Created by Almanza on 3/3/2016.
 */
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import brymian.bubbles.R;
import brymian.bubbles.bryant.events.EventsTop;
import brymian.bubbles.bryant.events.EventsYours;

public class TabFragment3 extends Fragment implements View.OnClickListener{

    TextView tvMoreTopEvents, tvYourEvents;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_fragment_3, container, false);

        tvMoreTopEvents = (TextView) rootView.findViewById(R.id.tvMoreTopEvents);
        tvYourEvents = (TextView) rootView.findViewById(R.id.tvYourEvents);

        tvMoreTopEvents.setOnClickListener(this);
        tvYourEvents.setOnClickListener(this);
        return rootView;
    }

    public void onClick(View v){
        FragmentManager fm = getActivity().getFragmentManager();

        switch (v.getId()){
            case R.id.tvMoreTopEvents:
                startActivity(new Intent(getActivity(), EventsTop.class));
                break;

            case R.id.tvYourEvents:
                startActivity(new Intent(getActivity(), EventsYours.class));
                break;
        }

    }
}