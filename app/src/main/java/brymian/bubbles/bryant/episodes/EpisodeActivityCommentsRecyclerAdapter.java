package brymian.bubbles.bryant.episodes;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import brymian.bubbles.R;


class EpisodeActivityCommentsRecyclerAdapter extends RecyclerView.Adapter<EpisodeActivityCommentsRecyclerAdapter.RecyclerViewHolder> {
    static Activity activity;
    List<Integer> uid;
    List<String> userUsername;
    List<Bitmap> userProfileImageBitmap;
    List<String> userProfileImagePath;
    List<String> userComment;
    List<String> userCommentTimestamp;
    public static ImageView ivUserProfileImage;


    EpisodeActivityCommentsRecyclerAdapter(Activity activity, List<Integer> uid, List<String> userUsername, List<String> userProfileImagePath, List<String> userComment, List<String> userCommentTimestamp){
        EpisodeActivityCommentsRecyclerAdapter.activity = activity;
        this.uid = uid;
        this.userUsername = userUsername;
        this.userProfileImagePath = userProfileImagePath;
        this.userProfileImageBitmap = userProfileImageBitmap;
        this.userComment = userComment;
        this.userCommentTimestamp = userCommentTimestamp;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_activity_comments_recyclerview_row,parent, false );
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvUserUsername.setText(userUsername.get(position));
        holder.tvUserCommentTimestamp.setText(userCommentTimestamp.get(position));
        holder.tvUserComment.setText(userComment.get(position));
        Log.e("onBindViewHolder", "position: " + position);
        //new DownloadImage(userProfileImagePath.get(position)).execute();
        //holder.ivUserProfileImage.setImageBitmap(new DownloadImage(userProfileImagePath.get(position)).execute());
    }

    @Override
    public int getItemCount() {
        return uid.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserUsername, tvUserComment, tvUserCommentTimestamp;
        public RecyclerViewHolder(View v){
            super(v);
            ivUserProfileImage = (ImageView) v.findViewById(R.id.ivUserProfileImage);
            tvUserUsername = (TextView) v.findViewById(R.id.tvUserUsername);
            tvUserComment = (TextView) v.findViewById(R.id.tvUserComment);
            tvUserCommentTimestamp = (TextView) v.findViewById(R.id.tvUserCommentTimestamp);
        }
    }

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String path;

        DownloadImage(String path){
            this.path = path;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URLConnection connection = new URL(path).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);
                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                ivUserProfileImage.setImageBitmap(bitmap);
                //userProfileImageBitmap.add(bitmap);
            }
        }
    }
}
