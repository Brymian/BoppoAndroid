package brymian.bubbles.bryant.episodes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.Connection.HTTPConnection;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;

public class EpisodeView extends Fragment {
    public ImageView ivEpisode;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        ivEpisode = (ImageView) rootView.findViewById(R.id.ivEpisode);

        downloadEpisodePictures();
        return rootView;
    }

    private void downloadEpisodePictures(){
        EpisodeActivity episodeActivity = new EpisodeActivity();
        new UserImageRequest(getActivity()).getImagesByEid(EpisodeActivity.getEid(), new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    List<String> episodeImagePath = new ArrayList<>();
                    HTTPConnection httpConnection = new HTTPConnection();
                    JSONArray jsonArray = new JSONArray(string);
                    Log.e("try","length: " + jsonArray.length());
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jArray_jObject = jsonArray.getJSONObject(i);
                        JSONObject jImage = jArray_jObject.getJSONObject("image");
                        episodeImagePath.add(httpConnection.getUploadServerString() + jImage.getString("userImagePath").replaceAll(" ", "%20"));
                        Log.e("image path", httpConnection.getUploadServerString() + jImage.getString("userImagePath").replaceAll(" ", "%20"));
                        new DownloadImage(episodeImagePath.get(i)).execute();
                    }
                }catch (JSONException jsone){
                    jsone.printStackTrace();
                }
            }
        });
    }

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String path;
        public DownloadImage(String path){
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
                Log.e("bitmap", "bitmap here" );
                ivEpisode.setImageBitmap(bitmap);
            }
            else{
                Log.e("bitmap", "bitmap not here");
            }
        }
    }
}


