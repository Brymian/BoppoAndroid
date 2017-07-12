package com.brymian.boppo.bryant.addLocation;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.brymian.boppo.R;

import static android.app.Activity.RESULT_OK;

public class AddLocationRecyclerAdapter extends RecyclerView.Adapter<AddLocationRecyclerAdapter.RecyclerViewHolder> {

    private Activity activity;
    private List<String> locationName;
    private List<String> locationAddress;
    private List<Double> locationLat;
    private List<Double> locationLng;

    public AddLocationRecyclerAdapter(Activity activity, List<String> locationName, List<String> locationAddress, List<Double> locationLat, List<Double> locationLng){
        this.activity = activity;
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
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
                    Intent intent = activity.getIntent();
                    intent.putExtra("locationName", locationName.get(getAdapterPosition()));
                    intent.putExtra("locationAddress", locationAddress.get(getAdapterPosition()));
                    intent.putExtra("locationLat", locationLat.get(getAdapterPosition()));
                    intent.putExtra("locationLng", locationLng.get(getAdapterPosition()));
                    activity.setResult(RESULT_OK, intent);
                    activity.finish();
                }
            });
        }
    }
}
