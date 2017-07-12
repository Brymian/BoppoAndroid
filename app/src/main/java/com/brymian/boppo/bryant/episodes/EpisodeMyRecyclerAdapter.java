package com.brymian.boppo.bryant.episodes;

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

import com.brymian.boppo.R;


public class EpisodeMyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private String orientation;
    private List<String> episodeTitle;
    private List<String> episodeImagePath;
    private List<Integer> episodeEid;
    private List<String> episodeType;
    private List<String> episodeHostUsername;
    private List<String> episodeViews;
    private List<String> episodeLikes;
    private List<String> episodeLocationName;
    private int TYPE_VERTICAL = 1;
    private int TYPE_HORIZONTAL = 2;

    public EpisodeMyRecyclerAdapter(Activity activity, String orientation, List<String> episodeTitle, List<String> episodeImagePath, List<Integer> episodeEid, List<String> episodeType){
        this.activity = activity;
        this.orientation = orientation;
        this.episodeTitle = episodeTitle;
        this.episodeImagePath = episodeImagePath;
        this.episodeEid = episodeEid;
        this.episodeType = episodeType;
    }

    public EpisodeMyRecyclerAdapter(Activity activity, String orientation, List<String> episodeTitle, List<String> episodeImagePath, List<Integer> episodeEid, List<String> episodeType, List<String> episodeHostUsername, List<String> episodeViews, List<String> episodeLikes ,List<String> episodeLocationName){
        this.activity = activity;
        this.orientation = orientation;
        this.episodeTitle = episodeTitle;
        this.episodeImagePath = episodeImagePath;
        this.episodeEid = episodeEid;
        this.episodeType = episodeType;
        this.episodeHostUsername = episodeHostUsername;
        this.episodeViews = episodeViews;
        this.episodeLikes = episodeLikes;
        this.episodeLocationName = episodeLocationName;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VERTICAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_my_recyclerview_row_vertical,parent, false );
            return new VerticalHolder(view);
        }
        else if (viewType == TYPE_HORIZONTAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_my_recyclerview_row_horizontal, parent, false);
            return new HorizontalHolder(view);
        }
        return new RecyclerViewHolder(new View(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_VERTICAL){
            Picasso.with(activity).load(episodeImagePath.get(position)).into(((VerticalHolder)holder).ivEpisodeImage);
            ((VerticalHolder)holder).tvEpisodeTitle.setText(episodeTitle.get(position));
            ((VerticalHolder)holder).tvEpisodeHostUsername.setText(episodeHostUsername.get(position));
            ((VerticalHolder)holder).tvEpisodeViews.setText(episodeViews.get(position));
            ((VerticalHolder)holder).tvEpisodeLikes.setText(episodeLikes.get(position));
            ((VerticalHolder)holder).tvEpisodeLocationName.setText(episodeLocationName.get(position));
        }
        else if (holder.getItemViewType() == TYPE_HORIZONTAL){
            Picasso.with(activity).load(episodeImagePath.get(position)).into(((HorizontalHolder)holder).ivEpisodeImage);
            ((HorizontalHolder)holder).tvEpisodeTitle.setText(episodeTitle.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return episodeTitle.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (orientation.equals("vertical")){
            return TYPE_VERTICAL;
        }
        else if (orientation.equals("horizontal")){
            return TYPE_HORIZONTAL;
        }
        return 0;
    }

    private class VerticalHolder extends RecyclerView.ViewHolder{
        ImageView ivEpisodeImage;
        TextView tvEpisodeTitle, tvEpisodeHostUsername, tvEpisodeViews, tvEpisodeLikes, tvEpisodeLocationName;
        CardView cardView;

        public VerticalHolder(View v){
            super(v);
            ivEpisodeImage = (ImageView) v.findViewById(R.id.ivEpisodeImage);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvEpisodeHostUsername = (TextView) v.findViewById(R.id.tvEpisodeHostUsername);
            tvEpisodeViews = (TextView) v.findViewById(R.id.tvEpisodeViews);
            tvEpisodeLikes = (TextView) v.findViewById(R.id.tvEpisodeLikes);
            tvEpisodeLocationName = (TextView) v.findViewById(R.id.tvEpisodeLocationName);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, EpisodeActivity.class).putExtra("eid", episodeEid.get(getAdapterPosition())));
                }
            });
        }
    }

    private class HorizontalHolder extends RecyclerView.ViewHolder{
        ImageView ivEpisodeImage;
        TextView tvEpisodeTitle;
        CardView cardView;

        public HorizontalHolder(View v){
            super(v);
            ivEpisodeImage = (ImageView) v.findViewById(R.id.ivEpisodeImage);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, EpisodeActivity.class).putExtra("eid", episodeEid.get(getAdapterPosition())));
                }
            });
        }
    }

    public  class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public RecyclerViewHolder(View v){
            super(v);

        }
    }
}
