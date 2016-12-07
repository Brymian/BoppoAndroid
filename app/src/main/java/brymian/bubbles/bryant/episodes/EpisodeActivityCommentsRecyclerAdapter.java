package brymian.bubbles.bryant.episodes;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;

public class EpisodeActivityCommentsRecyclerAdapter extends RecyclerView.Adapter<EpisodeActivityCommentsRecyclerAdapter.RecyclerViewHolder> {
    static Activity activity;
    List<Integer> uid;
    List<String> userComment;
    List<String> userCommentTimestamp;

    public EpisodeActivityCommentsRecyclerAdapter(Activity activity, List<Integer> uid, List<String> userComment, List<String> userCommentTimestamp){
        EpisodeActivityCommentsRecyclerAdapter.activity = activity;
        this.uid = uid;
        this.userComment = userComment;
        this.userCommentTimestamp = userCommentTimestamp;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_activity_comments_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvUserUsername.setText("User username");
        holder.tvUserCommentTimestamp.setText(userCommentTimestamp.get(position));
        holder.tvUserComment.setText(userComment.get(position));
    }

    @Override
    public int getItemCount() {
        return uid.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserUsername, tvUserComment, tvUserCommentTimestamp;
        public RecyclerViewHolder(View v){
            super(v);
            tvUserUsername = (TextView) v.findViewById(R.id.tvUserUsername);
            tvUserComment = (TextView) v.findViewById(R.id.tvUserComment);
            tvUserCommentTimestamp = (TextView) v.findViewById(R.id.tvUserCommentTimestamp);

        }
    }
}
