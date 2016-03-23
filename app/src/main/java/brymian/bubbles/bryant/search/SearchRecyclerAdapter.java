package brymian.bubbles.bryant.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brymian.bubbles.R;


public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.RecyclerViewHolder> {

    List<String> search_results;

    public SearchRecyclerAdapter(List search_results){
        this.search_results = search_results;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_search_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.tvSearchResults.setText(search_results.get(position));
    }

    @Override
    public int getItemCount() {
        return search_results.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvSearchResults;

        public RecyclerViewHolder(View v){
            super(v);
            tvSearchResults = (TextView) v.findViewById(R.id.tvSearchResults);
        }
    }
}
