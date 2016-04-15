package brymian.bubbles.bryant.profile.friends.friendrequests;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.friends.FriendsList;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;

public class FriendRequestRecyclerAdapter extends RecyclerView.Adapter<FriendRequestRecyclerAdapter.RecyclerViewHolder> {

    static List<FriendRequester> friendRequester;
    static Activity activity;

    public FriendRequestRecyclerAdapter(Activity activity, List<FriendRequester> friendRequester){
        FriendRequestRecyclerAdapter.friendRequester = friendRequester;
        FriendRequestRecyclerAdapter.activity = activity;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_friendrequest_row, parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvFriendRequestFirstLastName.setText(friendRequester.get(position).getFirstLastName());
        holder.tvFriendRequestUsername.setText(friendRequester.get(position).getUsername());
        holder.tvAccept.setText(R.string.Accept);
        holder.tvDecline.setText(R.string.Decline);

    }

    @Override
    public int getItemCount() {
        return friendRequester.size();
    }

    /* removes rows */
    /**
    static void delete(int position) {
        friendRequester.remove(position);
        notifyItemRemoved(position);
    }
     **/

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvFriendRequestFirstLastName, tvFriendRequestUsername;
        TextView tvAccept, tvDecline;

        public RecyclerViewHolder(View v){
            super(v);
            tvFriendRequestFirstLastName = (TextView) v.findViewById(R.id.tvFriendRequestFirstLastName);
            tvFriendRequestUsername = (TextView) v.findViewById(R.id.tvFriendRequestUsername);

            tvAccept = (TextView) v.findViewById(R.id.tvAccept);
            tvAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ServerRequestMethods(activity).setFriendStatus(SaveSharedPreference.getUserUID(activity), friendRequester.get(getAdapterPosition()).getUid(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            System.out.println("accept string: " + string);
                            if(string.equals("Friend request accepted.")){
                                friendRequester.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                            }
                        }
                    });
                }
            });
            tvDecline = (TextView) v.findViewById(R.id.tvDecline);
            tvDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //System.out.println("friendRequestUid.get(getAdapterPosition()): " + friendRequester.get(getAdapterPosition()));
                }
            });
        }
    }
}