package brymian.bubbles.bryant.main.mainTabEpisodesRecyclerAdapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.EpisodeActivity;


public class MainTabEpisodesRecyclerAdapter extends RecyclerView.Adapter<MainTabEpisodesRecyclerAdapter.RecyclerViewHolder> {
    private Activity activity;
    private List<String> episodeTitle;
    private List<String> episodeImagePath;
    private List<String> episodeHostUsername;
    private List<Integer> episodeEid;
    private List<String> episodeNum;

    public MainTabEpisodesRecyclerAdapter(Activity activity, List<Integer> episodeEid, List<String> episodeTitle, List<String> episodeImagePath, List<String> episodeHostUsername, List<String> episodeNum){
        this.activity = activity;
        this.episodeTitle = episodeTitle;
        this.episodeImagePath = episodeImagePath;
        this.episodeHostUsername = episodeHostUsername;
        this.episodeEid = episodeEid;
        this.episodeNum = episodeNum;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_tab_episodes_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Picasso.with(activity).load(episodeImagePath.get(position)).into(holder.ivEpisodeImage);
        holder.tvEpisodeTitle.setText(episodeTitle.get(position));
        holder.tvEpisodeHostUsername.setText("By "+episodeHostUsername.get(position));
        holder.tvEpisodeNum.setText(episodeNum.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeTitle.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle, tvEpisodeHostUsername, tvEpisodeNum;
        ImageView ivEpisodeImage;
        CardView cardView;
        public RecyclerViewHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvEpisodeHostUsername = (TextView) v.findViewById(R.id.tvEpisodeHostUsername);
            tvEpisodeNum = (TextView) v.findViewById(R.id.tvEpisodeNum);
            ivEpisodeImage =(ImageView) v.findViewById(R.id.ivEpisodeImage);
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
