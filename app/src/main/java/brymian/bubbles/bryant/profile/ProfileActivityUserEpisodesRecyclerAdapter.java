package brymian.bubbles.bryant.profile;

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
        import brymian.bubbles.bryant.episodes.EpisodeActivity;


public class ProfileActivityUserEpisodesRecyclerAdapter extends RecyclerView.Adapter<ProfileActivityUserEpisodesRecyclerAdapter.RecyclerViewHolder> {
    static Activity activity;
    static List<String> episodeTitle;
    List< String> episodeHostName;
    static List<Integer> episodeEid;

    public ProfileActivityUserEpisodesRecyclerAdapter(Activity activity, List<String> episodeTitle, List<String> episodeHostName, List<Integer> episodeEid){
        ProfileActivityUserEpisodesRecyclerAdapter.activity = activity;
        ProfileActivityUserEpisodesRecyclerAdapter.episodeTitle = episodeTitle;
        this.episodeHostName = episodeHostName;
        ProfileActivityUserEpisodesRecyclerAdapter.episodeEid = episodeEid;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_activity_user_episodes,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvEpisodeTitle.setText(episodeTitle.get(position));
        holder.tvEpisodeHostUsername.setText(episodeHostName.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeTitle.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvEpisodeTitle, tvEpisodeHostUsername, tvEpisodeNum;
        CardView cardView;
        public RecyclerViewHolder(View v){
            super(v);
            tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
            tvEpisodeHostUsername = (TextView) v.findViewById(R.id.tvEpisodeHostUsername);
            tvEpisodeNum = (TextView) v.findViewById(R.id.tvEpisodeNum);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, EpisodeActivity.class).putExtra("episodeTitle", episodeTitle.get(getAdapterPosition())).putExtra("eid", episodeEid.get(getAdapterPosition())));
                }
            });
        }
    }
}