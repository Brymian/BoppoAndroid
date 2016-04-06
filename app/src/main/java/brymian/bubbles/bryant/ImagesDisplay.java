package brymian.bubbles.bryant;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.objects.Image;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;

/**
 * Created by Almanza on 2/1/2016.
 */
public class ImagesDisplay extends FragmentActivity implements View.OnClickListener{

    TextView tUserName;
    ImageButton ibMenu;
    ImageView ivImage0, ivImage1, ivImage2, ivImage3, ivImage4, ivImage5, ivImage6, ivImage7, ivImage8, ivImage9, ivImage10, ivImage11, ivImage12, ivImage13, ivImage14, ivImage15, ivImage16, ivImage17, ivImage18, ivImage19, ivImage20, ivImage21, ivImage22, ivImage23, ivImage24, ivImage25, ivImage26, ivImage27, ivImage28, ivImage29;
    ImageView[] imageViewArray = {ivImage0, ivImage1, ivImage2, ivImage3, ivImage4, ivImage5, ivImage6, ivImage7, ivImage8, ivImage9, ivImage10, ivImage11, ivImage12, ivImage13, ivImage14, ivImage15, ivImage16, ivImage17, ivImage18, ivImage19, ivImage20, ivImage21, ivImage22, ivImage23, ivImage24, ivImage25, ivImage26, ivImage27, ivImage28, ivImage29};
    int[] imageViewRIDS = {R.id.ivImage0, R.id.ivImage1, R.id.ivImage2, R.id.ivImage3, R.id.ivImage4, R.id.ivImage5, R.id.ivImage6, R.id.ivImage7, R.id.ivImage8, R.id.ivImage9, R.id.ivImage10, R.id.ivImage11, R.id.ivImage12, R.id.ivImage13, R.id.ivImage14, R.id.ivImage15, R.id.ivImage16, R.id.ivImage17, R.id.ivImage18, R.id.ivImage19, R.id.ivImage19, R.id.ivImage20, R.id.ivImage21, R.id.ivImage22, R.id.ivImage23, R.id.ivImage24, R.id.ivImage25, R.id.ivImage26, R.id.ivImage27, R.id.ivImage28, R.id.ivImage29};
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
        //llFirstRow = (LinearLayout) findViewById(R.id.llFirstRow);
        //llSecondRow = (LinearLayout) findViewById(R.id.llSecondRow);

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
                //startActivity(new Intent(this, MenuActivity.class));
                break;
        }
    }

    void getImages(int uid){
        //new ServerRequestMethods(this).getImagesByUid(uid, "Regular", new ImageListCallback() {

        /**
        new ServerRequestMethods(this).getImages(uid, "Regular", new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                try {
                    System.out.println("imageList.size(): " + imageList.size());
                    if (imageList.size() != 0) {
                        //llFirstRow.setVisibility(View.VISIBLE);
                        //llSecondRow.setVisibility(View.VISIBLE);
                        for (int i = 0; i < imageList.size(); i++) {
                            new DownloadImage(imageList.get(i).getPath(), i).execute();
                        }
                    } else {
                        Toast.makeText(ImagesDisplay.this, "User has no images", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }
        });

         **/
    }

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String path;
        int location;

        public DownloadImage(String path, int location){
            this.path = path;
            this.location = location;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        Bitmap ShrinkBitmap(String file, int width, int height){

            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

            int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
            int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);

            if (heightRatio > 1 || widthRatio > 1)
            {
                if (heightRatio > widthRatio)
                {
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }

            bmpFactoryOptions.inJustDecodeBounds = false;
            try(InputStream is = new URL(file).openStream() ){
                Bitmap bitmapTRY = BitmapFactory.decodeStream( is );
                return bitmapTRY;
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
            //bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
            //return bitmap;
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

                //return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
                return ShrinkBitmap(path, 100, 100);
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

}
