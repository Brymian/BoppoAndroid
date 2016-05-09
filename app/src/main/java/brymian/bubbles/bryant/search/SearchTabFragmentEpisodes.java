package brymian.bubbles.bryant.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import brymian.bubbles.R;


/**
 * Created by almanza1112 on 5/8/16.
 */
public class SearchTabFragmentEpisodes extends Fragment {

    public static TextView tvSomething;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_tab_fragment_episodes, container, false);
        tvSomething = (TextView) view.findViewById(R.id.tvSomething);
        return view;
    }
}
