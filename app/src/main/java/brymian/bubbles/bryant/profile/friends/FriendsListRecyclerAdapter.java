package brymian.bubbles.bryant.profile.friends;

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
public class FriendsListRecyclerAdapter  extends RecyclerView.Adapter<FriendsListRecyclerAdapter.RecyclerViewHolder> {

    List<String> friendsFirstLastName;
    List<String> friendsUsername;

    public FriendsListRecyclerAdapter(List friendsFirstLastName, List friendsUsername){
        this.friendsFirstLastName = friendsFirstLastName;
        this.friendsUsername = friendsUsername;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_friendslist_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvFriendsFirstLastName.setText(friendsFirstLastName.get(position));
        holder.tvFriendsUsername.setText(friendsUsername.get(position));
    }

    @Override
    public int getItemCount() {
        return friendsFirstLastName.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvFriendsFirstLastName, tvFriendsUsername;

        public RecyclerViewHolder(View v){
            super(v);
            tvFriendsFirstLastName = (TextView) v.findViewById(R.id.tvFriendsFirstLastName);
            tvFriendsUsername = (TextView) v.findViewById(R.id.tvFriendsUsername);
        }
    }
}