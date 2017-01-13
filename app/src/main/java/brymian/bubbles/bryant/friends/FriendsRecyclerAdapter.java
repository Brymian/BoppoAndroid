package brymian.bubbles.bryant.friends;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.profile.ProfileActivity;


class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.RecyclerViewHolder> {
    private static Activity activity;
    private List<String> friendFirstLastName;
    private static List<String> friendUsername;
    private static List<Integer> friendUid;
    private static List<String> friendStatus;

    FriendsRecyclerAdapter(Activity activity, List<String> friendFirstLastName, List<String> friendUsername, List<Integer> friendUid, List<String> friendStatus){
        FriendsRecyclerAdapter.activity = activity;
        this.friendFirstLastName = friendFirstLastName;
        FriendsRecyclerAdapter.friendUsername = friendUsername;
        FriendsRecyclerAdapter.friendUid = friendUid;
        FriendsRecyclerAdapter.friendStatus = friendStatus;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_recyclerview_row,parent, false );
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
        CardView cardView;
        public RecyclerViewHolder(View v){
            super(v);
            tvFriendsFirstLastName = (TextView) v.findViewById(R.id.tvFriendsFirstLastName);
            tvFriendsUsername = (TextView) v.findViewById(R.id.tvFriendsUsername);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(FriendsRecyclerAdapter.activity, ProfileActivity.class).putExtra("username", friendUsername.get(getAdapterPosition())).putExtra("uid", friendUid.get(getAdapterPosition())).putExtra("profile", friendStatus.get(getAdapterPosition())));
                }
            });
        }
    }
}