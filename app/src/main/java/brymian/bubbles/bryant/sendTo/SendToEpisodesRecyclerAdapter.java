package brymian.bubbles.bryant.sendTo;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.addfriends.Friend;

public class SendToEpisodesRecyclerAdapter extends RecyclerView.Adapter<SendToEpisodesRecyclerAdapter.RecyclerViewHolder> {
    static Activity activity;
    static List<Episode> episodeList;

    public SendToEpisodesRecyclerAdapter(Activity activity, List<Episode> episodeList){
        SendToEpisodesRecyclerAdapter.activity = activity;
        SendToEpisodesRecyclerAdapter.episodeList = episodeList;
    }

@Override
public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_to_episode_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
        }

@Override
public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.tvEpisodeTitle.setText(episodeList.get(position).getEpisodeTitle());
        holder.tvEpisodeHostName.setText(episodeList.get(position).getEpisodeHostName());
        holder.cbSelected.setChecked(episodeList.get(position).getIsSelected());
        holder.cbSelected.setTag(episodeList.get(position));
        holder.cbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Friend contact = (Friend) cb.getTag();

                contact.setIsSelected(cb.isChecked());
                episodeList.get(position).setIsSelected(cb.isChecked());

                 Toast.makeText(
                 v.getContext(),
                 "Clicked on Checkbox: " + cb.getText() + " is "
                 + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
}

@Override
public int getItemCount() {
        return episodeList.size();
        }

public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
    TextView tvEpisodeTitle, tvEpisodeHostName;
    LinearLayout row;
    CheckBox cbSelected;

    public RecyclerViewHolder(View v){
        super(v);
        tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
        tvEpisodeHostName = (TextView) v.findViewById(R.id.tvEpisodeHostName);
        row = (LinearLayout) v.findViewById(R.id.row);
        cbSelected = (CheckBox) v.findViewById(R.id.cbSelected);

    }
}
    // method to access in activity after updating selection
    public List<Episode> getEpisodeList() {
        return episodeList;
    }

}

