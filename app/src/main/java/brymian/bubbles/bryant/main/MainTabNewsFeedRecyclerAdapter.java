package brymian.bubbles.bryant.main;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;

public class MainTabNewsFeedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> episodeType;
    private List<MainTabNewsFeedInfo> mainTabNewsFeedInfoList;
    private static final int TYPE_JOINED_MUTUAL_EPISODE = 1;
    private static final int TYPE_ACTIVE_EPISODE = 2;
    private static final int TYPE_BECAME_FRIENDS = 3;
    private static final int TYPE_CREATED_EPISODE = 4;
    private static final int TYPE_UPLOAD_IMAGE = 5;

    public MainTabNewsFeedRecyclerAdapter(List<String> episodeType, List<MainTabNewsFeedInfo> mainTabNewsFeedInfoList){
        this.episodeType = episodeType;
        this.mainTabNewsFeedInfoList = mainTabNewsFeedInfoList;
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
                break;
            case TYPE_ACTIVE_EPISODE:

                break;
            case TYPE_BECAME_FRIENDS:

                break;

            case TYPE_CREATED_EPISODE:

                break;
            case TYPE_UPLOAD_IMAGE:

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
            case "FriendsUploadedImages":
                return TYPE_UPLOAD_IMAGE;
        }
        return 6;
    }


    public static class JoinedMutualEpisodeHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle, tvUserJoined;
        public JoinedMutualEpisodeHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvUserJoined = (TextView) v.findViewById(R.id.tvUserJoined);

        }
    }
    public static class ActiveEpisodeHolder extends RecyclerView.ViewHolder{

        public ActiveEpisodeHolder(View v){
            super(v);

        }
    }
    public static class BecameFriendsHolder extends RecyclerView.ViewHolder{

        public BecameFriendsHolder(View v){
            super(v);

        }
    }
    public static class CreatedEpisodeHolder extends RecyclerView.ViewHolder{

        public CreatedEpisodeHolder(View v){
            super(v);

        }
    }

    public static class UploadImageHolder extends RecyclerView.ViewHolder{

        public UploadImageHolder(View v){
            super(v);

        }
    }




    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public RecyclerViewHolder(View v){
            super(v);

        }
    }
}
