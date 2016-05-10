package brymian.bubbles.bryant.search;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;


public class SearchRecyclerAdapterEpisodes extends RecyclerView.Adapter<SearchRecyclerAdapterEpisodes.RecyclerViewHolder> {

    List<String> episodeTitle;
    List<String> episodeHostName;

    public SearchRecyclerAdapterEpisodes(List<String> searchResultsFirstLastName, List<String> searchResultsUsername){
        this.episodeTitle = searchResultsFirstLastName;
        this.episodeHostName = searchResultsUsername;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_search_episodes_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvEpisodeTitle.setText(episodeTitle.get(position));
        holder.tvEpisodeHostName.setText(episodeHostName.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeTitle.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle, tvEpisodeHostName;

        public RecyclerViewHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvEpisodeHostName = (TextView) v.findViewById(R.id.tvEpisodeHostName);
        }
    }
}