package brymian.bubbles.bryant.episodes;

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


public class EpisodeMyRecyclerAdapter extends RecyclerView.Adapter<EpisodeMyRecyclerAdapter.RecyclerViewHolder> {
    private Activity activity;
    private List<String> episodeTitle;
    private List<String> episodeImagePath;
    private  List<Integer> episodeEid;

    public EpisodeMyRecyclerAdapter(Activity activity, List<String> episodeTitle, List<String> episodeImagePath, List<Integer> episodeEid){
        this.activity = activity;
        this.episodeTitle = episodeTitle;
        this.episodeImagePath = episodeImagePath;
        this.episodeEid = episodeEid;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_my_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvEpisodeTitle.setText(episodeTitle.get(position));
        Picasso.with(activity).load(episodeImagePath.get(position)).fit().centerCrop().into(holder.ivEpisodeImage);
    }

    @Override
    public int getItemCount() {
        return episodeTitle.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle;
        CardView cardView;
        ImageView ivEpisodeImage;
        public RecyclerViewHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            ivEpisodeImage = (ImageView) v.findViewById(R.id.ivEpisodeImage);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, EpisodeActivity.class).putExtra("eid", episodeEid.get(getAdapterPosition())));
                }
            });
        }
    }
}
