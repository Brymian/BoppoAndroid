package brymian.bubbles.bryant.sendTo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brymian.bubbles.R;

public class SendToEpisodesRecyclerAdapter extends RecyclerView.Adapter<SendToEpisodesRecyclerAdapter.RecyclerViewHolder> {
    private Activity activity;
    private List<Episode> episodeList;

    public SendToEpisodesRecyclerAdapter(Activity activity, List<Episode> episodeList){
        this.activity = activity;
        this.episodeList = episodeList;
    }

@Override
public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_to_episode_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
}

@Override
public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
    Picasso.with(activity).load(episodeList.get(position).getImagePath()).fit().into(holder.ivEpisodeImage);
        holder.tvEpisodeTitle.setText(episodeList.get(position).getEpisodeTitle());
        holder.tvEpisodeHostUsername.setText(episodeList.get(position).getEpisodeHostName());
        holder.cbSelected.setChecked(episodeList.get(position).getIsSelected());
        holder.cbSelected.setTag(episodeList.get(position));
        holder.cbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Episode contact = (Episode) cb.getTag();
                contact.setIsSelected(cb.isChecked());
                episodeList.get(position).setIsSelected(cb.isChecked());
            }
        });
}

@Override
public int getItemCount() {
        return episodeList.size();
        }

public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
    TextView tvEpisodeTitle, tvEpisodeHostUsername;
    ImageView ivEpisodeImage;
    CheckBox cbSelected;

    public RecyclerViewHolder(View v){
        super(v);
        ivEpisodeImage = (ImageView) v.findViewById(R.id.ivEpisodeImage);
        tvEpisodeTitle = (TextView) v.findViewById(R.id.tvEpisodeTitle);
        tvEpisodeHostUsername = (TextView) v.findViewById(R.id.tvEpisodeHostUsername);
        cbSelected = (CheckBox) v.findViewById(R.id.cbSelected);
    }
}
    // method to access in activity after updating selection
    public List<Episode> getEpisodeList() {
        return episodeList;
    }

}

