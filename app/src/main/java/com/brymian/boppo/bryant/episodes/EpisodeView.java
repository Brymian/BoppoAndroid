package com.brymian.boppo.bryant.episodes;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.brymian.boppo.R;

public class EpisodeView extends Fragment {
    public ImageView ivEpisode;
    int count = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode_view, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        ivEpisode = (ImageView) rootView.findViewById(R.id.ivEpisode);
        ivEpisode.setImageBitmap(((EpisodeActivity)getActivity()).loadedEpisodeImages.get(count));
        ivEpisode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count+1;
                try{
                    ivEpisode.setImageBitmap(((EpisodeActivity)getActivity()).loadedEpisodeImages.get(count));
                }
                catch (IndexOutOfBoundsException e){
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                    getFragmentManager().popBackStack();
                }
            }
        });
        return rootView;
    }
}


