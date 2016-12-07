package brymian.bubbles.bryant.main.mainTabEpisodesRecyclerAdapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.EpisodeActivity;


public class MainTabEpisodesAllTimeMostDislikesRecyclerAdapter extends RecyclerView.Adapter<MainTabEpisodesAllTimeMostDislikesRecyclerAdapter.RecyclerViewHolder> {
    static Activity activity;
    static List<String> episodeTitle;
    List<String> episodeHostUsername;
    static List<Integer> episodeEid;
    List<String> episodeNum;

    public MainTabEpisodesAllTimeMostDislikesRecyclerAdapter(Activity activity, List<String> episodeTitle, List<String> episodeHostUsername, List<Integer> episodeEid, List<String> episodeNum){
        MainTabEpisodesAllTimeMostDislikesRecyclerAdapter.activity = activity;
        MainTabEpisodesAllTimeMostDislikesRecyclerAdapter.episodeTitle = episodeTitle;
        this.episodeHostUsername = episodeHostUsername;
        MainTabEpisodesAllTimeMostDislikesRecyclerAdapter.episodeEid = episodeEid;
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
        holder.tvEpisodeNum.setText(episodeNum.get(position) + " dislikes");
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

