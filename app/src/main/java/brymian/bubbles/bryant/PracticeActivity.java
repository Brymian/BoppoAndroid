package brymian.bubbles.bryant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.okhttp.internal.http.HttpConnection;

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
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;

/**
 * Created by Almanza on 7/30/2015.
 */
public class PracticeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String SERVER_ADDRESS = "http://73.194.170.63:8080/";
    ImageView imageToUpload, downloadedImage;
    Button bUploadImage, bDownloadedImage;
    EditText uploadedImageName, downloadedImageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);
        downloadedImage = (ImageView) findViewById(R.id.downloadedImage);

        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        bDownloadedImage = (Button) findViewById(R.id.bDownloadImage);

        uploadedImageName = (EditText) findViewById(R.id.eUploadName);
        downloadedImageName = (EditText) findViewById(R.id.eDownloadName);

        imageToUpload.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);
        bDownloadedImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.imageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.bUploadImage:
                Bitmap image = ((BitmapDrawable) imageToUpload.getDrawable()).getBitmap();
                UserDataLocal udl = new UserDataLocal(this);
                User user = udl.getUserData();
                new ServerRequest(this).uploadImage("hey", new StringCallback() {
                    @Override
                    public void done(String string) {
                        System.out.print("THIS IS THE STRING: " +string);
                    }
                });
                //new UploadImage(user.uid(), image, uploadedImageName.getText().toString()).execute();
                break;
            case R.id.bDownloadImage:
                new DownloadImage(downloadedImageName.getText().toString()).execute();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
        }
    }

    /**
    private class UploadImage extends AsyncTask<Void, Void, Void>{

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
            HttpPost post = new HttpPost(SERVER_ADDRESS + "BubblesServer/DBIO/uploadImage.php");

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
    **/

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap>{
       String name;

        public DownloadImage(String name){
            this.name = name;
        }


        @Override
        protected Bitmap doInBackground(Void... params){
            String url = SERVER_ADDRESS + "Uploads/" + name + ".JPG";
            try {
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
                downloadedImage.setImageBitmap(bitmap);
            }
        }
    }

    private HttpParams getHttpRequestParams(){
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000* 30);
        return httpRequestParams;
    }
}
