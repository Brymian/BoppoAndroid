package brymian.bubbles.bryant.friends;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.ProfileActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.FriendshipStatusRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;

public class FriendRequestReceivedRecyclerAdapter extends RecyclerView.Adapter<FriendRequestReceivedRecyclerAdapter.RecyclerViewHolder> {

    private Activity activity;
    private List<Integer> requestUid;
    private List<String> requestUsername, requestFullname, requestImagePath;
    public FriendRequestReceivedRecyclerAdapter(Activity activity, List<Integer> requestUid, List<String> requestUsername, List<String> requestFullname, List<String> requestImagePath){
        this.activity = activity;
        this.requestUid = requestUid;
        this.requestUsername = requestUsername;
        this.requestFullname = requestFullname;
        this.requestImagePath = requestImagePath;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_request_received_recyclerview_row, parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Picasso.with(activity).load(requestImagePath.get(position)).fit().into(holder.ivUserProfileImage);
        holder.tvFriendRequestFirstLastName.setText(requestFullname.get(position));
        holder.tvFriendRequestUsername.setText(requestUsername.get(position));
    }

    @Override
    public int getItemCount() {
        return requestUid.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvFriendRequestFirstLastName, tvFriendRequestUsername;
        TextView tvAccept, tvDecline;
        ImageView ivUserProfileImage;

        public RecyclerViewHolder(View v){
            super(v);

            final CardView cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", requestUid.get(getAdapterPosition())));
                }
            });

            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);

            tvFriendRequestFirstLastName = (TextView) v.findViewById(R.id.tvFriendRequestFirstLastName);
            tvFriendRequestUsername = (TextView) v.findViewById(R.id.tvFriendRequestUsername);

            final LinearLayout llAcceptDecline = (LinearLayout) v.findViewById(R.id.llAcceptDecline);
            final TextView tvFriendRequestResult = (TextView) v.findViewById(R.id.tvFriendRequestResult);
            final Handler handler = new Handler();

            tvAccept = (TextView) v.findViewById(R.id.tvAccept);
            tvAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ServerRequestMethods(activity).setFriendStatus(SaveSharedPreference.getUserUID(activity), requestUid.get(getAdapterPosition()), new StringCallback() {
                        @Override
                        public void done(String string) {
                            Log.e("accepted", string +"\nusername: " + requestUsername.get(getAdapterPosition()) + "\tuid: " + requestUid.get(getAdapterPosition()));
                            if(string.equals("Friend request accepted.")){
                                llAcceptDecline.setVisibility(View.GONE);
                                tvFriendRequestResult.setText("Friends");
                                tvFriendRequestResult.setVisibility(View.VISIBLE);
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 3s = 3000ms
                                        requestUsername.remove(getAdapterPosition());
                                        requestUid.remove(getAdapterPosition());
                                        notifyItemRemoved(getAdapterPosition());
                                    }
                                }, 3000);
                            }
                        }
                    });
                }
            });
            tvDecline = (TextView) v.findViewById(R.id.tvDecline);
            tvDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new FriendshipStatusRequest().rejectFriend(SaveSharedPreference.getUserUID(activity), requestUid.get(getAdapterPosition()), new StringCallback() {
                        @Override
                        public void done(String string) {
                            Log.e("decline", string +"\nusername: " + requestUsername.get(getAdapterPosition()) + "\tuid: " + requestUid.get(getAdapterPosition()));
                            if(string.equals("Friendship request has been successfully rejected.")){
                                llAcceptDecline.setVisibility(View.GONE);
                                tvFriendRequestResult.setText("Declined");
                                tvFriendRequestResult.setVisibility(View.VISIBLE);
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 3s = 3000ms
                                        requestUsername.remove(getAdapterPosition());
                                        requestUid.remove(getAdapterPosition());
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