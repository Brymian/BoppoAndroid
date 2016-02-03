package brymian.bubbles.bryant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.Image;
import brymian.bubbles.damian.nonactivity.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest;

/**
 * Created by Almanza on 2/1/2016.
 */
public class ImagesDisplay extends FragmentActivity implements View.OnClickListener{

    TextView tUserName;
    ImageButton ibMenu;
    ImageView ivImage0, ivImage1, ivImage2, ivImage3, ivImage4, ivImage5;
    ImageView[] imageViewArray = {ivImage0, ivImage1, ivImage2, ivImage3, ivImage4, ivImage5};
    int[] imageViewRIDS = {R.id.ivImage0, R.id.ivImage1, R.id.ivImage2, R.id.ivImage2, R.id.ivImage3, R.id.ivImage4, R.id.ivImage5};
    LinearLayout llFirstRow, llSecondRow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_display);

        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                uid = 0;
            } else {

                uid = extras.getInt("uid");
            }
        } else {
            uid = savedInstanceState.getInt("uid");
        }

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);
        tUserName = (TextView) findViewById(R.id.tUserName);
        llFirstRow = (LinearLayout) findViewById(R.id.llFirstRow);
        llSecondRow = (LinearLayout) findViewById(R.id.llSecondRow);

        for(int i = 0; i < imageViewArray.length; i++){
            imageViewArray[i] = (ImageView) findViewById(imageViewRIDS[i]);
            //imageViewArray[i].setVisibility(View.GONE);
        }

        ibMenu.setOnClickListener(this);

        System.out.println("uid: " + uid);
        getImages(uid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibMenu:
                startActivity(new Intent(this, MenuActivity.class));
                break;
        }
    }

    void getImages(int uid){
        new ServerRequest(this).getImages(uid, "Regular", new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                try{
                    System.out.println("imageList.size(): " + imageList.size());
                    if (imageList.size() != 0){
                        llFirstRow.setVisibility(View.VISIBLE);
                        llSecondRow.setVisibility(View.VISIBLE);
                        for(int i = 0; i < imageList.size(); i++){
                            new DownloadImage(imageList.get(i).getPath(), i);
                        }
                    }
                    else{
                        Toast.makeText(ImagesDisplay.this, "User has no images", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        });
    }

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String path;
        int location;

        public DownloadImage(String path, int location){
            this.path = path;
            this.location = location;
        }


        @Override
        protected Bitmap doInBackground(Void... params){


            //String url = SERVER_ADDRESS + "Uploads/" + name + ".JPG";
            try {
                String url = path;
                System.out.println("getPath(): " + path);
                URLConnection connection = new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);

                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            if(bitmap != null){
                //downloadedImage.setImageBitmap(bitmap);
                //Drawable drawable = new BitmapDrawable(bitmap);
                //MainLayout.setBackgroundDrawable(drawable);
                imageViewArray[location].setImageBitmap(bitmap);
            }
        }
    }

    private HttpParams getHttpRequestParams(){
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;
    }
}
