package brymian.bubbles.bryant.episodes;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import brymian.bubbles.R;

public class EpisodeView extends Fragment {
    public ImageView ivEpisode;
    int count = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode_view, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        ivEpisode = (ImageView) rootView.findViewById(R.id.ivEpisode);
        ivEpisode.setImageBitmap(EpisodeActivity.episodeImage.get(count));
        ivEpisode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count+1;
                try{
                    ivEpisode.setImageBitmap(EpisodeActivity.episodeImage.get(count));
                }catch (IndexOutOfBoundsException e){
                    getFragmentManager().popBackStack();
                }
            }
        });
        return rootView;
    }
}


