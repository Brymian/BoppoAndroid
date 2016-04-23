package brymian.bubbles.bryant.friends.sent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;

/**
 * Created by Almanza on 4/7/2016.
 */
public class SentFriendRequestsRecyclerAdapter  extends RecyclerView.Adapter<SentFriendRequestsRecyclerAdapter.RecyclerViewHolder> {

    List<String> sentFriendRequestFirstLastName;
    List<String> sentFriendRequestUsername;

    public SentFriendRequestsRecyclerAdapter(List<String> sentFriendRequestFirstLastName, List<String> sentFriendRequestUsername){
        this.sentFriendRequestFirstLastName = sentFriendRequestFirstLastName;
        this.sentFriendRequestUsername = sentFriendRequestUsername;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_sent_requests_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvSentFriendRequestFirstLastName.setText(sentFriendRequestFirstLastName.get(position));
        holder.tvSentFriendRequestUsername.setText(sentFriendRequestUsername.get(position));
    }

    @Override
    public int getItemCount() {
        return sentFriendRequestFirstLastName.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvSentFriendRequestFirstLastName, tvSentFriendRequestUsername;

        public RecyclerViewHolder(View v){
            super(v);
            tvSentFriendRequestFirstLastName = (TextView) v.findViewById(R.id.tvSentFriendRequestFirstLastName);
            tvSentFriendRequestUsername = (TextView) v.findViewById(R.id.tvSentFriendRequestUsername);
        }
    }
}