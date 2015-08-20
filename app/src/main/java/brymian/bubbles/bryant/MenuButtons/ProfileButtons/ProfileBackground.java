package brymian.bubbles.bryant.MenuButtons.ProfileButtons;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import brymian.bubbles.R;

public class ProfileBackground extends FragmentActivity implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String SERVER_ADDRESS = "http://73.194.170.63:8080/ProjectWolf/";

    ImageView imageView1, imageView2, imageView3, imageView4;
    Button bSave;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_background);

        imageView1 = (ImageView) findViewById(R.id.ivImageView1);
        imageView2 = (ImageView) findViewById(R.id.ivImageView2);
        imageView3 = (ImageView) findViewById(R.id.ivImageView3);
        imageView4 = (ImageView) findViewById(R.id.ivImageView4);

        bSave = (Button) findViewById(R.id.bSave);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

        bSave.setOnClickListener(this);
    }

    public void onClick(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        switch (view.getId()){
            case R.id.ivImageView1:
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.ivImageView2:
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.ivImageView3:
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.ivImageView4:
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.bSave:

                break;
        }
    }
    private class UploadImage extends AsyncTask<Void, Void, Void> {

        Bitmap image;
        String name;
        int uid;
        public UploadImage(int uid, Bitmap image, String name){
            this.image = image;
            this.name = name;
            this.uid = uid;
        }
        @Override
        protected Void doInBackground(Void... params){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name", name));
            dataToSend.add(new BasicNameValuePair("uid", Integer.toString(uid)));

            HttpParams httpRequestParams = getHttpRequestParams();

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Bubbles/DBIO/uploadImage.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
        }
    }
    private HttpParams getHttpRequestParams(){
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000* 30);
        return httpRequestParams;
    }
}
