package com.brymian.boppo.bryant.photos;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.brymian.boppo.R;

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private String orientation;
    private List<String> imagePath;
    private int TYPE_VERTICAL = 1;
    private int TYPE_HORIZONTAL = 2;

    public PhotosRecyclerAdapter(Activity activity, String orientation,List<String> imagePath){
        this.activity = activity;
        this.orientation = orientation;
        this.imagePath = imagePath;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VERTICAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_recyclerview_row_vertical,parent, false );
            return new VerticalHolder(view);
        }
        else if (viewType == TYPE_HORIZONTAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_recyclerview_column_horizontal, parent, false);
            return new HorizontalHolder(view);
        }
        return new RecyclerViewHolder(new View(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_VERTICAL){

        }
        else if (holder.getItemViewType() == TYPE_HORIZONTAL){
            Picasso.with(activity).load(imagePath.get(position)).fit().into(((HorizontalHolder)holder).ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return imagePath.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (orientation.equals("vertical")){
            return TYPE_VERTICAL;
        }
        else if (orientation.equals("horizontal")){
            return TYPE_HORIZONTAL;
        }
        return 3;
    }

    private class VerticalHolder extends RecyclerView.ViewHolder{

        public VerticalHolder(View v){
            super(v);

        }
    }

    private class HorizontalHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        public HorizontalHolder(View v){
            super(v);
            ivImage = (ImageView) v.findViewById(R.id.ivImage);
        }
    }

    public  class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public RecyclerViewHolder(View v){
            super(v);

        }
    }
}
