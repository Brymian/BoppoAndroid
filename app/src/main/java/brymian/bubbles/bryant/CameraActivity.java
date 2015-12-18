package brymian.bubbles.bryant;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
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
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CameraActivity extends Activity implements View.OnClickListener{
    private static int TAKE_PICTURE = 1;
    private Uri imageUri;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    double[] setLatitudeArray = new double[1];
    double[] setLongitudeArray = new double[1];

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

        double latitude = 0;
        double longitude = 0;
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                latitude =0;
                longitude = 0;
            }
            else{
                latitude = extras.getDouble("latitude");
                longitude = extras.getDouble("longitude");
            }
        }

        setLatitude(latitude);
        setLongitude(longitude);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bUploadImage:
                UserDataLocal udl = new UserDataLocal(this);
                User user = udl.getUserData();
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                new ServerRequest(this).uploadImage(1, "TEST IMAGE 2", "Regular", "Public", getLatitude(), getLongitude(), encodedImage, new StringCallback() {
                    @Override
                    public void done(String string) {
                        System.out.println("THIS IS FROM THE StringCallBack: " + string);
                    }
                });
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

    public void setLatitude(double l){
        setLatitudeArray[0] = l;
    }
    public double getLatitude(){
        return setLatitudeArray[0];
    }
    public void setLongitude(double l){
        setLongitudeArray[0] = l;
    }
    public double getLongitude(){
        return setLongitudeArray[0];
    }
}