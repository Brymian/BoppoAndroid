package brymian.bubbles.bryant.episodes.addfriends;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brymian.bubbles.R;


class EpisodeAddFriendsRecyclerAdapter extends RecyclerView.Adapter<EpisodeAddFriendsRecyclerAdapter.RecyclerViewHolder> {

    private List<Friend> friendList;
    private Activity activity;

    EpisodeAddFriendsRecyclerAdapter(Activity activity, List<Friend> friendList){
        this.activity = activity;
        this.friendList = friendList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_add_friends_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        Picasso.with(activity).load(friendList.get(position).getUserImagePath()).fit().centerCrop().into(holder.ivUserProfileImage);
        holder.tvFriendsUsername.setText(friendList.get(position).getUsername());
        holder.tvFriendsFirstLastName.setText(friendList.get(position).getFirstLastName());
        holder.cbSelected.setChecked(friendList.get(position).getIsSelected());
        holder.cbSelected.setTag(friendList.get(position));
        holder.cbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Friend contact = (Friend) cb.getTag();

                contact.setIsSelected(cb.isChecked());
                friendList.get(position).setIsSelected(cb.isChecked());

            }
        });
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvFriendsFirstLastName, tvFriendsUsername;
        ImageView ivUserProfileImage;
        CheckBox cbSelected;
        CardView cardView;
        public RecyclerViewHolder(View v){
            super(v);
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
            tvFriendsFirstLastName = (TextView) v.findViewById(R.id.tvFriendsFirstLastName);
            tvFriendsUsername = (TextView) v.findViewById(R.id.tvFriendsUsername);
            cbSelected = (CheckBox) v.findViewById(R.id.cbSelected);
            cardView  =(CardView) v.findViewById(R.id.card_view);
        }
    }

    // method to access in activity after updating selection
    public List<Friend> getFriendList() {
        return friendList;
    }

}
