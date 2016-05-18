package brymian.bubbles.bryant.episodes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;


public class EpisodeHostingRecyclerAdapter extends RecyclerView.Adapter<EpisodeHostingRecyclerAdapter.RecyclerViewHolder> {
    static Activity activity;
    List<String> episodeTitle;
    static List<Integer> episodeEid;

    public EpisodeHostingRecyclerAdapter(Activity activity, List<String> episodeTitle, List<Integer> episodeEid){
        EpisodeHostingRecyclerAdapter.activity = activity;
        this.episodeTitle = episodeTitle;
        EpisodeHostingRecyclerAdapter.episodeEid = episodeEid;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_my_hosting_recyclerview_row,parent, false );
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
        LinearLayout row;
        public RecyclerViewHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            row = (LinearLayout) v.findViewById(R.id.row);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, EpisodeActivity.class).putExtra("eid", episodeEid.get(getAdapterPosition())));
                }
            });
        }
    }
}
