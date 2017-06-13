package brymian.bubbles.bryant.settings.blocking;


import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;

public class BlockingRecyclerAdapter extends RecyclerView.Adapter<BlockingRecyclerAdapter.RecyclerViewHolder> {

    private Activity activity;
    private List<String> blockedUsername = new ArrayList<>();
    private List<String> blockedFullname = new ArrayList<>();
    private List<Integer> blockedUid = new ArrayList<>();
    private List<String> blockedImagePath = new ArrayList<>();

    public BlockingRecyclerAdapter(Activity activity, List<Integer> blockedUid, List<String> blockedUsername, List<String> blockedFullname, List<String> blockedImagePath){
        this.activity = activity;
        this.blockedUid = blockedUid;
        this.blockedUsername = blockedUsername;
        this.blockedFullname = blockedFullname;
        this.blockedImagePath = blockedImagePath;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blocking_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Picasso.with(activity).load(blockedImagePath.get(position)).fit().into(holder.ivUserProfileImage);
        holder.tvBlockedUserFirstLastName.setText(blockedFullname.get(position));
        holder.tvBlockedUserUsername.setText(blockedUsername.get(position));
    }

    @Override
    public int getItemCount() {
        return blockedUid.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvBlockedUserUsername, tvBlockedUserFirstLastName;
        Button bUnblock;
        ImageView ivUserProfileImage;

        public RecyclerViewHolder(View v){
            super(v);
            tvBlockedUserUsername = (TextView) v.findViewById(R.id.tvBlockedUserUsername);
            tvBlockedUserFirstLastName = (TextView) v.findViewById(R.id.tvBlockedUserFirstLastName);
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);

            final Handler handler = new Handler();

            bUnblock = (Button) v.findViewById(R.id.bUnblock);
            bUnblock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new FriendshipStatusRequest().unblockUser(SaveSharedPreference.getUserUID(activity), blockedUid.get(getAdapterPosition()), new StringCallback() {
                        @Override
                        public void done(String string) {
                            if(string.equals("User has been successfully unblocked.")){
                                bUnblock.setBackgroundColor(activity.getResources().getColor(android.R.color.white));
                                bUnblock.setTextColor(activity.getResources().getColor(R.color.AppColorDark));
                                bUnblock.setText("UNBLOCKED");
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 3s = 3000ms
                                        blockedUid.remove(getAdapterPosition());
                                        notifyItemRemoved(getAdapterPosition());
                                    }
                                }, 3000);
                            }
                        }
                    });
                }
            });
        }
    }
}