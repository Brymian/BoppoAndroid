package brymian.bubbles.bryant;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import java.util.*;

import android.provider.ContactsContract;
import android.provider.MediaStore;
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

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CameraActivity extends Activity implements View.OnClickListener{
    private static final String SERVER_ADDRESS = "http://73.194.170.63:8080/";

    private static int TAKE_PICTURE = 1;
    private Uri imageUri;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;

    Button bUploadImage;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        takePhoto();
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        imageView = (ImageView) findViewById(R.id.image_camera);

        bUploadImage.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bUploadImage:
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                UserDataLocal udl = new UserDataLocal(this);
                User user = udl.getUserData();
                new UploadImage(user.getUid(), image, imageName()).execute();
                break;
        }
    }
    private void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "picture.jpg");
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    String imageName(){
        UserDataLocal udl = new UserDataLocal(this);
        User user = udl.getUserData();
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm", new java.util.Date());
        String name = user + charSequenceName;
        System.out.println(name);
        return name;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImage = imageUri;
            getContentResolver().notifyChange(selectedImage, null);

            ContentResolver cr = getContentResolver();
            Bitmap bitmap;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    private HttpParams getHttpRequestParams(){
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000* 30);
        return httpRequestParams;
    }

}