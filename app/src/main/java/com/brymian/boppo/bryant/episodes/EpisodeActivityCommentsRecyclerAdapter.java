package com.brymian.boppo.bryant.episodes;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.profile.ProfileActivity;


class EpisodeActivityCommentsRecyclerAdapter extends RecyclerView.Adapter<EpisodeActivityCommentsRecyclerAdapter.RecyclerViewHolder> {
    private static Activity activity;
    private static List<Integer> uid;
    private static List<String> userUsername;
    private List<String> userProfileImagePath;
    private List<String> userComment;
    private List<String> userCommentTimestamp;
    private static ImageView ivUserProfileImage;

    EpisodeActivityCommentsRecyclerAdapter(Activity activity, List<Integer> uid, List<String> userUsername, List<String> userProfileImagePath, List<String> userComment, List<String> userCommentTimestamp){
        EpisodeActivityCommentsRecyclerAdapter.activity = activity;
        EpisodeActivityCommentsRecyclerAdapter.uid = uid;
        EpisodeActivityCommentsRecyclerAdapter.userUsername = userUsername;
        this.userProfileImagePath = userProfileImagePath;
        this.userComment = userComment;
        this.userCommentTimestamp = userCommentTimestamp;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_activity_comments_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvUserUsername.setText(userUsername.get(position));
        holder.tvUserCommentTimestamp.setText(userCommentTimestamp.get(position));
        holder.tvUserComment.setText(userComment.get(position));
        Picasso.with(activity).load(userProfileImagePath.get(position)).fit().centerCrop().into(ivUserProfileImage);
    }

    @Override
    public int getItemCount() {
        return uid.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserUsername, tvUserComment, tvUserCommentTimestamp;
        public RecyclerViewHolder(View v){
            super(v);
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
            tvUserUsername = (TextView) v.findViewById(R.id.tvUserUsername);
            tvUserComment = (TextView) v.findViewById(R.id.tvUserComment);
            tvUserCommentTimestamp = (TextView) v.findViewById(R.id.tvUserCommentTimestamp);
            ivUserProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", uid.get(getAdapterPosition())).putExtra("username", userUsername.get(getAdapterPosition())));
                }
            });
        }
    }
}
