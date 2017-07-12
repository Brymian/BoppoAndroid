package com.brymian.boppo.bryant.main;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.episodes.EpisodeActivity;
import com.brymian.boppo.bryant.profile.ProfileActivity;

public class MainTabNewsFeedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> episodeType;
    private static Activity activity;
    private static List<MainTabNewsFeedInfo> mainTabNewsFeedInfoList;
    private static final int TYPE_JOINED_MUTUAL_EPISODE = 1;
    private static final int TYPE_ACTIVE_EPISODE = 2;
    private static final int TYPE_BECAME_FRIENDS = 3;
    private static final int TYPE_CREATED_EPISODE = 4;
    private static final int TYPE_UPLOAD_IMAGE = 5;

    public MainTabNewsFeedRecyclerAdapter(Activity activity, List<String> episodeType, List<MainTabNewsFeedInfo> mainTabNewsFeedInfoList){
        MainTabNewsFeedRecyclerAdapter.activity = activity;
        this.episodeType = episodeType;
        MainTabNewsFeedRecyclerAdapter.mainTabNewsFeedInfoList = mainTabNewsFeedInfoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_JOINED_MUTUAL_EPISODE){
            View joinedMutualEpisode = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_tab_news_feed_recyclerview_joined_mutual_episode, parent, false);
            return new JoinedMutualEpisodeHolder(joinedMutualEpisode);
        }
        else if (viewType == TYPE_ACTIVE_EPISODE){
            View activeEpisode = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_tab_news_feed_recyclerview_active_episode, parent, false);
            return new ActiveEpisodeHolder(activeEpisode);
        }
        else if (viewType == TYPE_BECAME_FRIENDS){
            View becameFriends = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_tab_news_feed_recyclerview_became_friends, parent, false);
            return new BecameFriendsHolder(becameFriends);
        }
        else if (viewType == TYPE_CREATED_EPISODE){
            View createdEpisode = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_tab_news_feed_recyclerview_created_episode, parent, false);
            return new CreatedEpisodeHolder(createdEpisode);
        }
        else if (viewType == TYPE_UPLOAD_IMAGE){
            View uploadImage = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_tab_news_feed_recyclerview_upload_image, parent, false);
            return new UploadImageHolder(uploadImage);
        }
        return new RecyclerViewHolder(new View(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_JOINED_MUTUAL_EPISODE:
                ((JoinedMutualEpisodeHolder)holder).tvEpisodeTitle.setText(mainTabNewsFeedInfoList.get(position).getEpisodeTitle());
                ((JoinedMutualEpisodeHolder)holder).tvUserJoined.setText(mainTabNewsFeedInfoList.get(position).getUsernames().get(0));
                Picasso.with(activity).load(mainTabNewsFeedInfoList.get(position).getPaths().get(0)).fit().centerCrop().into(((JoinedMutualEpisodeHolder)holder).ivUserProfileImage);
                Picasso.with(activity).load(mainTabNewsFeedInfoList.get(position).getEpisodeProfileImagePath()).fit().centerCrop().into(((JoinedMutualEpisodeHolder)holder).ivEpisodeImage);
                break;
            case TYPE_ACTIVE_EPISODE:
                ((ActiveEpisodeHolder)holder).tvEpisodeTitle.setText(mainTabNewsFeedInfoList.get(position).getEpisodeTitle());
                ((ActiveEpisodeHolder)holder).tvUserActive.setText(mainTabNewsFeedInfoList.get(position).getUsername());
                break;
            case TYPE_BECAME_FRIENDS:
                ((BecameFriendsHolder)holder).tvUser1Username.setText(mainTabNewsFeedInfoList.get(position).getUser1Username());
                ((BecameFriendsHolder)holder).tvUser2Username.setText(mainTabNewsFeedInfoList.get(position).getUser2Username());
                Picasso.with(activity).load(mainTabNewsFeedInfoList.get(position).getUser1ProfileImage()).fit().centerCrop().into(((BecameFriendsHolder)holder).ivUser1ProfileImage);
                Picasso.with(activity).load(mainTabNewsFeedInfoList.get(position).getUser2ProfileImage()).fit().centerCrop().into(((BecameFriendsHolder)holder).ivUser2ProfileImage);
                break;
            case TYPE_CREATED_EPISODE:
                ((CreatedEpisodeHolder)holder).tvEpisodeTitle.setText(mainTabNewsFeedInfoList.get(position).getEpisodeTitle());
                ((CreatedEpisodeHolder)holder).tvUserCreated.setText(mainTabNewsFeedInfoList.get(position).getUsername());
                Picasso.with(activity).load(mainTabNewsFeedInfoList.get(position).getUserProfileImage()).fit().centerCrop().into(((CreatedEpisodeHolder)holder).ivUserProfileImage);
                Picasso.with(activity).load(mainTabNewsFeedInfoList.get(position).getEpisodeProfileImagePath()).fit().centerCrop().into(((CreatedEpisodeHolder)holder).ivEpisodeImage);
                break;
            case TYPE_UPLOAD_IMAGE:
                ((UploadImageHolder)holder).tvUserUsername.setText(mainTabNewsFeedInfoList.get(position).getUsername());
                Picasso.with(activity).load(mainTabNewsFeedInfoList.get(position).getUserProfileImage()).fit().centerCrop().into(((UploadImageHolder)holder).ivUserProfileImage);
                Picasso.with(activity).load(mainTabNewsFeedInfoList.get(position).getUploadedImage()).fit().centerCrop().into(((UploadImageHolder)holder).ivUploadedImage);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return episodeType.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (episodeType.get(position)){
            case "FriendsJoinedMutualEvent":
                return TYPE_JOINED_MUTUAL_EPISODE;
            case "FriendActiveEvent":
                return TYPE_ACTIVE_EPISODE;
            case "FriendsThatBecameFriends":
                return TYPE_BECAME_FRIENDS;
            case "FriendCreatedEvent":
                return TYPE_CREATED_EPISODE;
            case "FriendUploadedImage":
                return TYPE_UPLOAD_IMAGE;
        }
        return 6;
    }

    private static class JoinedMutualEpisodeHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle, tvUserJoined;
        ImageView ivUserProfileImage, ivEpisodeImage;
        LinearLayout llEpisode;
        public JoinedMutualEpisodeHolder(View v){
            super(v);
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivProfilePicture);
            ivUserProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getUids().get(0)));
                }
            });
            tvUserJoined = (TextView) v.findViewById(R.id.tvUserJoined);
            tvUserJoined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getUids().get(0)));
                }
            });
            ivEpisodeImage = (ImageView) v.findViewById(R.id.ivEpisodeImage);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            llEpisode = (LinearLayout) v.findViewById(R.id.llEpisode);
            llEpisode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, EpisodeActivity.class).putExtra("eid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getEid()).putExtra("episodeTitle", mainTabNewsFeedInfoList.get(getAdapterPosition()).getEpisodeTitle()));
                }
            });
        }
    }

    private static class ActiveEpisodeHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle, tvUserActive;
        public ActiveEpisodeHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvUserActive = (TextView) v.findViewById(R.id.tvUserActive);
        }
    }

    private static class BecameFriendsHolder extends RecyclerView.ViewHolder{
        TextView tvUser1Username, tvUser2Username;
        ImageView ivUser1ProfileImage, ivUser2ProfileImage;
        LinearLayout llUser2;
        public BecameFriendsHolder(View v){
            super(v);
            ivUser1ProfileImage = (ImageView) v.findViewById(R.id.ivUser1ProfileImage);
            ivUser1ProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getUser1Uid()));
                }
            });
            tvUser1Username = (TextView) v.findViewById(R.id.tvUser1Username);
            tvUser1Username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getUser1Uid()));
                }
            });
            tvUser2Username = (TextView) v.findViewById(R.id.tvUser2Username);
            ivUser2ProfileImage = (ImageView) v.findViewById(R.id.ivUser2ProfileImage);
            llUser2 = (LinearLayout) v.findViewById(R.id.llUser2);
            llUser2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getUser2Uid()));
                }
            });
        }
    }

    private static class CreatedEpisodeHolder extends RecyclerView.ViewHolder{
        TextView tvUserCreated, tvEpisodeTitle;
        ImageView ivUserProfileImage, ivEpisodeImage;
        LinearLayout llEpisode;
        public CreatedEpisodeHolder(View v){
            super(v);
            tvUserCreated = (TextView) v.findViewById(R.id.tvUserCreated);
            tvUserCreated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getUid()));
                }
            });
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
            ivUserProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getUid()));
                }
            });
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            ivEpisodeImage = (ImageView) v.findViewById(R.id.ivEpisodeImage);
            llEpisode = (LinearLayout) v.findViewById(R.id.llEpisode);
            llEpisode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, EpisodeActivity.class).putExtra("eid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getEid()).putExtra("episodeTitle", mainTabNewsFeedInfoList.get(getAdapterPosition()).getEpisodeTitle()));
                }
            });
        }
    }

    private static class UploadImageHolder extends RecyclerView.ViewHolder{
        TextView tvUserUsername;
        ImageView ivUserProfileImage, ivUploadedImage;
        public UploadImageHolder(View v){
            super(v);
            tvUserUsername = (TextView) v.findViewById(R.id.tvUserUsername);
            tvUserUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getUid()));
                }
            });
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
            ivUserProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", mainTabNewsFeedInfoList.get(getAdapterPosition()).getUid()));
                }
            });
            ivUploadedImage = (ImageView) v.findViewById(R.id.ivUploadedImage);
        }
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public RecyclerViewHolder(View v){
            super(v);

        }
    }
}
