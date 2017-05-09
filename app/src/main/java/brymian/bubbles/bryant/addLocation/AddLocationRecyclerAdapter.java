package brymian.bubbles.bryant.addLocation;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.episodes.EpisodeActivity;
import brymian.bubbles.bryant.episodes.EpisodeCreate;

public class AddLocationRecyclerAdapter extends RecyclerView.Adapter<AddLocationRecyclerAdapter.RecyclerViewHolder> {

    private Activity activity;
    private List<String> locationName;
    private List<String> locationAddress;

    public AddLocationRecyclerAdapter(Activity activity, List<String> locationName, List<String> locationAddress){
        this.activity = activity;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_location_recyclerview_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvLocationName.setText(locationName.get(position));
        holder.tvLocationAddress.setText(locationAddress.get(position));
    }

    @Override
    public int getItemCount() {
        return locationName.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvLocationName, tvLocationAddress;
        LinearLayout llRow;
        public RecyclerViewHolder(View v){
            super(v);
            tvLocationName = (TextView) v.findViewById(R.id.tvLocationName);
            tvLocationAddress = (TextView) v.findViewById(R.id.tvLocationAddress);
            llRow = (LinearLayout) v.findViewById(R.id.row);
            llRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EpisodeCreate.setLocation(locationName.get(getAdapterPosition()));
                    EpisodeCreate.tvAddLocation.setText(locationName.get(getAdapterPosition()));
                    activity.onBackPressed();
                }
            });
        }
    }
}
