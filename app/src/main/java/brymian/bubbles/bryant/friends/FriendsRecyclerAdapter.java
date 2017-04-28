package brymian.bubbles.bryant.friends;

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

import brymian.bubbles.R;
import brymian.bubbles.bryant.profile.ProfileActivity;


public class FriendsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private String orientation;
    private List<String> friendFirstLastName;
    private List<String> friendUsername;
    private List<Integer> friendUid;
    private List<String> friendUserImagePath;
    private int TYPE_VERTICAL = 1;
    private int TYPE_HORIZONTAL = 2;

    public FriendsRecyclerAdapter(Activity activity, String orientation,List<String> friendFirstLastName, List<String> friendUsername, List<Integer> friendUid, List<String> friendUserImagePath){
        this.activity = activity;
        this.orientation = orientation;
        this.friendFirstLastName = friendFirstLastName;
        this.friendUsername = friendUsername;
        this.friendUid = friendUid;
        this.friendUserImagePath = friendUserImagePath;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VERTICAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_recyclerview_row_vertical,parent, false );
            return new VerticalHolder(view);
        }
        else if (viewType == TYPE_HORIZONTAL){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_recyclerview_row_horizontal, parent, false);
            return new HorizontalHolder(view);
        }
        return new RecyclerViewHolder(new View(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_VERTICAL){
            ((VerticalHolder)holder).tvFriendsFirstLastName.setText(friendFirstLastName.get(position));
            ((VerticalHolder)holder).tvFriendsUsername.setText(friendUsername.get(position));
            Picasso.with(activity).load(friendUserImagePath.get(position)).fit().centerCrop().into(((VerticalHolder)holder).ivUserProfileImage);
        }
        else if (holder.getItemViewType() == TYPE_HORIZONTAL){
            ((HorizontalHolder)holder).tvFriendsUsername.setText(friendUsername.get(position));
            Picasso.with(activity).load(friendUserImagePath.get(position)).fit().centerCrop().into(((HorizontalHolder)holder).ivUserProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return friendFirstLastName.size();
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
        TextView tvFriendsFirstLastName, tvFriendsUsername;
        ImageView ivUserProfileImage;
        CardView cardView;
        public VerticalHolder(View v){
            super(v);
            tvFriendsFirstLastName = (TextView) v.findViewById(R.id.tvFriendsFirstLastName);
            tvFriendsUsername = (TextView) v.findViewById(R.id.tvFriendsUsername);
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", friendUid.get(getAdapterPosition())));
                }
            });
        }
    }

    private class HorizontalHolder extends RecyclerView.ViewHolder{
        TextView tvFriendsUsername;
        ImageView ivUserProfileImage;
        CardView cardView;
        public HorizontalHolder(View v){
            super(v);
            tvFriendsUsername = (TextView) v.findViewById(R.id.tvFriendsUsername);
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", friendUid.get(getAdapterPosition())));
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