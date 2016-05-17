package brymian.bubbles.bryant.friends;

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
import brymian.bubbles.bryant.profile.ProfileActivity;


public class FriendsListRecyclerAdapter  extends RecyclerView.Adapter<FriendsListRecyclerAdapter.RecyclerViewHolder> {
    static Activity activity;
    List<String> friendFirstLastName;
    List<String> friendUsername;
    static List<Integer> friendUid;
    static List<String> friendStatus;

    public FriendsListRecyclerAdapter(Activity activity, List<String> friendFirstLastName, List<String> friendUsername, List<Integer> friendUid, List<String> friendStatus){
        FriendsListRecyclerAdapter.activity = activity;
        this.friendFirstLastName = friendFirstLastName;
        this.friendUsername = friendUsername;
        FriendsListRecyclerAdapter.friendUid = friendUid;
        FriendsListRecyclerAdapter.friendStatus = friendStatus;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvFriendsFirstLastName.setText(friendFirstLastName.get(position));
        holder.tvFriendsUsername.setText(friendUsername.get(position));
    }

    @Override
    public int getItemCount() {
        return friendFirstLastName.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvFriendsFirstLastName, tvFriendsUsername;
        LinearLayout row;
        public RecyclerViewHolder(View v){
            super(v);
            tvFriendsFirstLastName = (TextView) v.findViewById(R.id.tvFriendsFirstLastName);
            tvFriendsUsername = (TextView) v.findViewById(R.id.tvFriendsUsername);
            row = (LinearLayout) v.findViewById(R.id.row);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(FriendsListRecyclerAdapter.activity, ProfileActivity.class).putExtra("uid", FriendsListRecyclerAdapter.friendUid.get(getAdapterPosition())).putExtra("profile", friendStatus.get(getAdapterPosition())));
                }
            });
        }
    }
}