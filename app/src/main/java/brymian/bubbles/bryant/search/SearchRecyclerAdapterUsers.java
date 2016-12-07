package brymian.bubbles.bryant.search;

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
import brymian.bubbles.bryant.profile.ProfileActivity;


public class SearchRecyclerAdapterUsers extends RecyclerView.Adapter<SearchRecyclerAdapterUsers.RecyclerViewHolder> {

    List<String> searchResultsFirstLastName;
    static List<String> searchResultsUsername;
    static List<String> searchResultsFriendStatus;
    static List<Integer> searchResultsUid;
    static Activity activity;

    public SearchRecyclerAdapterUsers(Activity activity, List<String> searchResultsFirstLastName, List<String> searchResultsUsername, List<Integer> searchResultsUid,List<String> searchResultsFriendStatus){
        this.searchResultsFirstLastName = searchResultsFirstLastName;
        SearchRecyclerAdapterUsers.searchResultsUsername = searchResultsUsername;
        SearchRecyclerAdapterUsers.searchResultsFriendStatus = searchResultsFriendStatus;
        SearchRecyclerAdapterUsers.searchResultsUid = searchResultsUid;
        SearchRecyclerAdapterUsers.activity = activity;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_tab_fragment_users_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvSearchResultsFirstLastName.setText(searchResultsFirstLastName.get(position));
        holder.tvSearchUsername.setText(searchResultsUsername.get(position));
    }

    @Override
    public int getItemCount() {
        return searchResultsFirstLastName.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvSearchResultsFirstLastName, tvSearchUsername;
        CardView cardView;
        public RecyclerViewHolder(View v){
            super(v);
            tvSearchResultsFirstLastName = (TextView) v.findViewById(R.id.tvSearchResultsFirstLastName);
            tvSearchUsername = (TextView) v.findViewById(R.id.tvSearchResultsUsername);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class).putExtra("username", searchResultsUsername.get(getAdapterPosition())).putExtra("uid", searchResultsUid.get(getAdapterPosition())).putExtra("profile", searchResultsFriendStatus.get(getAdapterPosition())));
                }
            });
        }
    }
}
