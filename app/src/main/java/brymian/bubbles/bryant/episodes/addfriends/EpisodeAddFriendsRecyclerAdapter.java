package brymian.bubbles.bryant.episodes.addfriends;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;

/**
 * Created by Almanza on 4/5/2016.
 */
public class EpisodeAddFriendsRecyclerAdapter extends RecyclerView.Adapter<EpisodeAddFriendsRecyclerAdapter.RecyclerViewHolder> {

    List<String> friendsFirstLastName;
    List<String> friendsUsername;

    public EpisodeAddFriendsRecyclerAdapter(List<String> friendsFirstLastName, List<String> friendsUsername){
        this.friendsFirstLastName = friendsFirstLastName;
        this.friendsUsername = friendsUsername;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_episode_add_friends_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvFriendsFirstLastName.setText(friendsFirstLastName.get(position));
        holder.tvFriendsFirstLastName.setText(friendsFirstLastName.get(position));
    }

    @Override
    public int getItemCount() {
        return friendsFirstLastName.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvFriendsFirstLastName, tvFriendsUsername;

        public RecyclerViewHolder(View v){
            super(v);
            tvFriendsFirstLastName = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvFriendsUsername = (TextView) v.findViewById(R.id.tvFriendsUsername);
        }
    }
}
