package brymian.bubbles.bryant.main.mainTabEpisodesRecyclerAdapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.EpisodeActivity;


public class MainTabEpisodesAllTimeMostViewsRecyclerAdapter extends RecyclerView.Adapter<MainTabEpisodesAllTimeMostViewsRecyclerAdapter.RecyclerViewHolder> {
    static Activity activity;
    List<String> episodeTitle;
    List<String> episodeHostName;
    static List<Integer> episodeEid;
    List<String> episodeNum;

    public MainTabEpisodesAllTimeMostViewsRecyclerAdapter(Activity activity, List<String> episodeTitle, List<String> episodeHostName, List<Integer> episodeEid, List<String> episodeNum){
        MainTabEpisodesAllTimeMostViewsRecyclerAdapter.activity = activity;
        this.episodeTitle = episodeTitle;
        this.episodeHostName = episodeHostName;
        MainTabEpisodesAllTimeMostViewsRecyclerAdapter.episodeEid = episodeEid;
        this.episodeNum = episodeNum;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_tab_episodes_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvEpisodeTitle.setText(episodeTitle.get(position));
        holder.tvEpisodeHostName.setText(episodeHostName.get(position));
        holder.tvEpisodeNum.setText(episodeNum.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeTitle.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle, tvEpisodeHostName, tvEpisodeNum;
        LinearLayout row;
        public RecyclerViewHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvEpisodeHostName = (TextView) v.findViewById(R.id.tvEpisodeHostName);
            tvEpisodeNum = (TextView) v.findViewById(R.id.tvEpisodeNum);
            row = (LinearLayout) v.findViewById(R.id.row);
        }
    }
}
