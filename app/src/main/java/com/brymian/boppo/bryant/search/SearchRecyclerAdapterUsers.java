package com.brymian.boppo.bryant.search;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.profile.ProfileActivity;


public class SearchRecyclerAdapterUsers extends RecyclerView.Adapter<SearchRecyclerAdapterUsers.RecyclerViewHolder> {

    private List<String> searchResultsFirstLastName;
    private List<String> searchResultsUsername;
    private List<String> searchResultUserImagePath;
    private static List<Integer> searchResultsUid;
    private static Activity activity;

    public SearchRecyclerAdapterUsers(Activity activity, List<String> searchResultsFirstLastName, List<String> searchResultsUsername, List<Integer> searchResultsUid,List<String> searchResultUserImagePath){
        this.searchResultsFirstLastName = searchResultsFirstLastName;
        this.searchResultsUsername = searchResultsUsername;
        this.searchResultUserImagePath = searchResultUserImagePath;
        SearchRecyclerAdapterUsers.searchResultsUid = searchResultsUid;
        SearchRecyclerAdapterUsers.activity = activity;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_tab_users_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvSearchResultsFirstLastName.setText(searchResultsFirstLastName.get(position));
        holder.tvSearchUsername.setText(searchResultsUsername.get(position));
        Picasso.with(activity).load(searchResultUserImagePath.get(position)).fit().centerCrop().into(holder.ivUserProfileImage);
    }

    @Override
    public int getItemCount() {
        return searchResultsFirstLastName.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvSearchResultsFirstLastName, tvSearchUsername;
        ImageView ivUserProfileImage;
        CardView cardView;
        public RecyclerViewHolder(View v){
            super(v);
            tvSearchResultsFirstLastName = (TextView) v.findViewById(R.id.tvSearchResultsFirstLastName);
            tvSearchUsername = (TextView) v.findViewById(R.id.tvSearchResultsUsername);
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("uid", searchResultsUid.get(getAdapterPosition())));
                }
            });
        }
    }
}
