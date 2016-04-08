package brymian.bubbles.bryant.episodes.addfriends;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import brymian.bubbles.R;

/**
 * Created by Almanza on 4/5/2016.
 */
public class EpisodeAddFriendsRecyclerAdapter extends RecyclerView.Adapter<EpisodeAddFriendsRecyclerAdapter.RecyclerViewHolder> {

    List<Friend> friendList;

    public EpisodeAddFriendsRecyclerAdapter(List<Friend> friendList){
        this.friendList = friendList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_episode_add_friends_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
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

                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvFriendsFirstLastName, tvFriendsUsername;
        CheckBox cbSelected;

        public RecyclerViewHolder(View v){
            super(v);
            tvFriendsFirstLastName = (TextView) v.findViewById(R.id.tvFriendsFirstLastName);
            tvFriendsUsername = (TextView) v.findViewById(R.id.tvFriendsUsername);
            cbSelected = (CheckBox) v.findViewById(R.id.cbSelected);
        }
    }

    // method to access in activity after updating selection
    public List<Friend> getStudentist() {
        return friendList;
    }

}
