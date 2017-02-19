package brymian.bubbles.bryant.settings.blocking;


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

public class BlockingRecyclerAdapter extends RecyclerView.Adapter<BlockingRecyclerAdapter.RecyclerViewHolder> {

    private static Activity activity;
    private static List<BlockedUser> blockedUser;

    public BlockingRecyclerAdapter(Activity activity, List<BlockedUser> blockedUser){
        BlockingRecyclerAdapter.activity = activity;
        BlockingRecyclerAdapter.blockedUser = blockedUser;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blocking_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvBlockedUserUsername.setText(blockedUser.get(position).getUsername());
        holder.tvBlockedUserFirstLastName.setText(blockedUser.get(position).getFirstLastName());
    }

    @Override
    public int getItemCount() {
        return blockedUser.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvBlockedUserUsername, tvBlockedUserFirstLastName, tvUnblock;

        public RecyclerViewHolder(View v){
            super(v);
            tvBlockedUserUsername = (TextView) v.findViewById(R.id.tvBlockedUserUsername);
            tvBlockedUserFirstLastName = (TextView) v.findViewById(R.id.tvBlockedUserFirstLastName);
            tvUnblock = (TextView) v.findViewById(R.id.tvUnblock);
            tvUnblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new FriendshipStatusRequest(activity).unblockUser(SaveSharedPreference.getUserUID(activity), blockedUser.get(getAdapterPosition()).getUid(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            if(string.equals("User has been successfully unblocked.")){
                                blockedUser.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                            }
                        }
                    });
                }
            });
        }
    }
}