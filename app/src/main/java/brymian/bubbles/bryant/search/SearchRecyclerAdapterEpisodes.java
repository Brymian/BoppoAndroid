package brymian.bubbles.bryant.search;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;


public class SearchRecyclerAdapterEpisodes extends RecyclerView.Adapter<SearchRecyclerAdapterEpisodes.RecyclerViewHolder> {

    List<String> episodeTitle;
    List<String> episodeHostName;
    List<Integer> episodeEid;
    Activity activity;

    public SearchRecyclerAdapterEpisodes(Activity activity, List<String> episodeTitle, List<String> episodeHostName, List<Integer> episodeEid){
        this.activity = activity;
        this.episodeTitle = episodeTitle;
        this.episodeHostName = episodeHostName;
        this.episodeEid = episodeEid;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_tab_fragment_episodes_recyclervoew_row,parent, false );
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
        LinearLayout row;

        public RecyclerViewHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvEpisodeHostName = (TextView) v.findViewById(R.id.tvEpisodeHostName);
            row = (LinearLayout) v.findViewById(R.id.row);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}