package com.brymian.boppo.bryant.episodes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.profile.ProfileActivity;
import com.brymian.boppo.damian.nonactivity.CustomException.SetOrNotException;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.EventUserRequest;

public class EpisodeParticipantsRecyclerAdapter extends RecyclerView.Adapter<EpisodeParticipantsRecyclerAdapter.RecyclerViewHolder> {
    private static Activity activity;
    private int eid;
    private List<String> participantFirstLastName;
    private static List<String> participantUsername;
    private static List<Integer> participantUid;
    private List<String> participantsProfileImage;
    private static List<String> participantType;
    private static boolean showRemove;

    public EpisodeParticipantsRecyclerAdapter(Activity activity, int eid, List<String> participantFirstLastName, List<String> participantUsername, List<Integer> participantUid, List<String> participantsProfileImage, List<String> participantType, boolean showRemove){
        EpisodeParticipantsRecyclerAdapter.activity = activity;
        this.eid = eid;
        this.participantFirstLastName = participantFirstLastName;
        EpisodeParticipantsRecyclerAdapter.participantUsername = participantUsername;
        EpisodeParticipantsRecyclerAdapter.participantUid = participantUid;
        this.participantsProfileImage = participantsProfileImage;
        EpisodeParticipantsRecyclerAdapter.participantType = participantType;
        EpisodeParticipantsRecyclerAdapter.showRemove = showRemove;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_participants_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.tvParticipantUsername.setText(participantUsername.get(position));
        holder.tvParticipantFirstLastName.setText(participantFirstLastName.get(position));
        Picasso.with(activity).load(participantsProfileImage.get(position)).fit().centerCrop().into(holder.ivUserProfileImage);
        if (showRemove && !participantType.get(position).equals("Administrator")) {
            holder.bRemoveParticipant.setVisibility(View.VISIBLE);
            holder.bRemoveParticipant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        new EventUserRequest().setEventUser(eid, participantUid.get(position), null, "Removed", new Boolean[]{null, null, false, true}, new StringCallback() {
                            @Override
                            public void done(String string) {
                                Log.e("string", string);
                                if (string.equals("Event user has been successfully updated.")){
                                    holder.bRemoveParticipant.setText("REMOVED");
                                    holder.bRemoveParticipant.setTextColor(activity.getResources().getColor(R.color.AppColorDark));
                                    holder.bRemoveParticipant.setBackgroundColor(activity.getResources().getColor(android.R.color.white));
                                }
                            }
                        });
                    }
                    catch (SetOrNotException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return participantFirstLastName.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView ivUserProfileImage;
        TextView tvParticipantUsername, tvParticipantFirstLastName;
        Button bRemoveParticipant;
        CardView cardView;
        public RecyclerViewHolder(View v){
            super(v);
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
            tvParticipantUsername = (TextView) v.findViewById(R.id.tvParticipantUsername);
            tvParticipantFirstLastName = (TextView) v.findViewById(R.id.tvParticipantFirstLastName);
            bRemoveParticipant = (Button) v.findViewById(R.id.bRemoveParticipant);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", participantUid.get(getAdapterPosition())));
                }
            });
        }
    }
}
