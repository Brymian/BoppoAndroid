package brymian.bubbles.bryant.episodes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.ProfileActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;

public class EpisodeParticipantsRecyclerAdapter extends RecyclerView.Adapter<EpisodeParticipantsRecyclerAdapter.RecyclerViewHolder> {
    private static Activity activity;
    private List<String> participantFirstLastName;
    private static List<String> participantUsername;
    private static List<Integer> participantUid;

    public EpisodeParticipantsRecyclerAdapter(Activity activity, List<String> participantFirstLastName, List<String> participantUsername, List<Integer> participantUid){
        EpisodeParticipantsRecyclerAdapter.activity = activity;
        this.participantFirstLastName = participantFirstLastName;
        EpisodeParticipantsRecyclerAdapter.participantUsername = participantUsername;
        EpisodeParticipantsRecyclerAdapter.participantUid = participantUid;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_participants_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvParticipantUsername.setText(participantUsername.get(position));
        holder.tvParticipantFirstLastName.setText(participantFirstLastName.get(position));
    }

    @Override
    public int getItemCount() {
        return participantFirstLastName.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvParticipantUsername, tvParticipantFirstLastName;
        CardView cardView;
        public RecyclerViewHolder(View v){
            super(v);
            tvParticipantUsername = (TextView) v.findViewById(R.id.tvParticipantUsername);
            tvParticipantFirstLastName = (TextView) v.findViewById(R.id.tvParticipantFirstLastName);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ServerRequestMethods(activity).getFriendStatus(SaveSharedPreference.getUserUID(activity), participantUid.get(getAdapterPosition()), new StringCallback() {
                        @Override
                        public void done(String string) {
                            activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", participantUid.get(getAdapterPosition())).putExtra("profile", string).putExtra("username", participantUsername.get(getAdapterPosition())));
                        }
                    });
                }
            });
        }
    }
}
