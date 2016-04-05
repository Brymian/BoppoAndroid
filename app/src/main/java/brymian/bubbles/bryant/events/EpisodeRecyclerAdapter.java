package brymian.bubbles.bryant.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;

/**
 * Created by Almanza on 4/1/2016.
 */
public class EpisodeRecyclerAdapter extends RecyclerView.Adapter<EpisodeRecyclerAdapter.RecyclerViewHolder> {

    List<String> episodeTitle;

    public EpisodeRecyclerAdapter(List<String> episodeTitle){
        this.episodeTitle = episodeTitle;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_episode_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvEpisodeTitle.setText(episodeTitle.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeTitle.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle;

        public RecyclerViewHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
        }
    }
}
