package com.brymian.boppo.bryant.main.mainTabEpisodesRecyclerAdapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.episodes.EpisodeActivity;


public class MainTabEpisodesAllTimeMostLikesRecyclerAdapter extends RecyclerView.Adapter<MainTabEpisodesAllTimeMostLikesRecyclerAdapter.RecyclerViewHolder> {
    private static Activity activity;
    private static List<String> episodeTitle;
    private List<String> episodeHostUsername;
    private static List<Integer> episodeEid;
    private List<String> episodeNum;

    public MainTabEpisodesAllTimeMostLikesRecyclerAdapter(Activity activity, List<Integer> episodeEid, List<String> episodeTitle, List<String> episodeHostUsername, List<String> episodeNum){
        MainTabEpisodesAllTimeMostLikesRecyclerAdapter.activity = activity;
        MainTabEpisodesAllTimeMostLikesRecyclerAdapter.episodeTitle = episodeTitle;
        this.episodeHostUsername = episodeHostUsername;
        MainTabEpisodesAllTimeMostLikesRecyclerAdapter.episodeEid = episodeEid;
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
        holder.tvEpisodeHostUsername.setText("By " + episodeHostUsername.get(position));
        holder.tvEpisodeNum.setText(episodeNum.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeTitle.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle, tvEpisodeHostUsername, tvEpisodeNum;
        CardView cardView;
        public RecyclerViewHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvEpisodeHostUsername = (TextView) v.findViewById(R.id.tvEpisodeHostUsername);
            tvEpisodeNum = (TextView) v.findViewById(R.id.tvEpisodeNum);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, EpisodeActivity.class).putExtra("eid", episodeEid.get(getAdapterPosition())).putExtra("episodeTitle", episodeTitle.get(getAdapterPosition())));
                }
            });

        }
    }
}
