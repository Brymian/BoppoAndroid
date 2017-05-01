package brymian.bubbles.bryant.friends;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;


public class FriendRequestSentRecyclerAdapter extends RecyclerView.Adapter<FriendRequestSentRecyclerAdapter.RecyclerViewHolder> {

    static Activity activity;
    static List<FriendRequester> friendRequester;

    public FriendRequestSentRecyclerAdapter(Activity activity, List<FriendRequester> friendRequester){
        FriendRequestSentRecyclerAdapter.activity = activity;
        FriendRequestSentRecyclerAdapter.friendRequester = friendRequester;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_request_sent_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvSentFriendRequestFirstLastName.setText(friendRequester.get(position).getFirstLastName());
        holder.tvSentFriendRequestUsername.setText(friendRequester.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return friendRequester.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvSentFriendRequestFirstLastName, tvSentFriendRequestUsername, tvCancel;

        public RecyclerViewHolder(View v){
            super(v);
            tvSentFriendRequestFirstLastName = (TextView) v.findViewById(R.id.tvSentFriendRequestFirstLastName);
            tvSentFriendRequestUsername = (TextView) v.findViewById(R.id.tvSentFriendRequestUsername);
            tvCancel = (TextView) v.findViewById(R.id.tvCancel);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new FriendshipStatusRequest().cancelFriend(SaveSharedPreference.getUserUID(activity), friendRequester.get(getAdapterPosition()).getUid(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            if(string.equals("Friendship request has been successfully canceled.")){
                                friendRequester.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                            }
                        }
                    });
                }
            });
        }
    }
}