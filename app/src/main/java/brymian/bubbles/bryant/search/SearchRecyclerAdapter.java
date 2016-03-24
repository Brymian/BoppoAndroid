package brymian.bubbles.bryant.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;


public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.RecyclerViewHolder> {

    List<String> searchResultsFirstLastName;
    List<String> searchResultsUsername;

    public SearchRecyclerAdapter(List searchResultsFirstLastName, List searchResultsUsername){
        this.searchResultsFirstLastName = searchResultsFirstLastName;
        this.searchResultsUsername = searchResultsUsername;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_search_row,parent, false );
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

        public RecyclerViewHolder(View v){
            super(v);
            tvSearchResultsFirstLastName = (TextView) v.findViewById(R.id.tvSearchResultsFirstLastName);
            tvSearchUsername = (TextView) v.findViewById(R.id.tvSearchResultsUsername);
        }
    }
}
