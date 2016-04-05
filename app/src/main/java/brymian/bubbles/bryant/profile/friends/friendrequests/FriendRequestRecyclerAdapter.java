package brymian.bubbles.bryant.profile.friends.friendrequests;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;

/**
 * Created by Almanza on 3/24/2016.
 */
public class FriendRequestRecyclerAdapter extends RecyclerView.Adapter<FriendRequestRecyclerAdapter.RecyclerViewHolder> {

    List<String> friendRequestFirstLastName;
    List<String> friendRequestUsername;

    public FriendRequestRecyclerAdapter(List friendsFirstLastName, List friendsUsername){
        this.friendRequestFirstLastName = friendsFirstLastName;
        this.friendRequestUsername = friendsUsername;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_friendrequest_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvFriendRequestFirstLastName.setText(friendRequestFirstLastName.get(position));
        holder.tvFriendRequestUsername.setText(friendRequestUsername.get(position));
    }

    @Override
    public int getItemCount() {
        return friendRequestFirstLastName.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvFriendRequestFirstLastName, tvFriendRequestUsername;

        public RecyclerViewHolder(View v){
            super(v);
            tvFriendRequestFirstLastName = (TextView) v.findViewById(R.id.tvFriendRequestFirstLastName);
            tvFriendRequestUsername = (TextView) v.findViewById(R.id.tvFriendRequestUsername);
        }
    }
}