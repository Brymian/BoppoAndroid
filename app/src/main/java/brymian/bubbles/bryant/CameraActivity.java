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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
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

public class CameraActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
    private static int TAKE_PICTURE = 1;
    private Uri imageUri;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    double[] setLatitudeArray = new double[1];
    double[] setLongitudeArray = new double[1];
    String[] userImagePrivacyLabel = new String[1];

    Button bUploadImage;
    ImageView imageView;
    Switch sUserImagePrivacyLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhoto();
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

    public void setLayoutAndButtons(){
        setContentView(R.layout.activity_camera);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        imageView = (ImageView) findViewById(R.id.image_camera);
        sUserImagePrivacyLabel = (Switch) findViewById(R.id.sUserImagePrivacyLabel);

        bUploadImage.setOnClickListener(this);
        sUserImagePrivacyLabel.setOnCheckedChangeListener(this);
        imageView.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bUploadImage:
                UserDataLocal udl = new UserDataLocal(this);
                User userPhone = udl.getUserData();
                int userUID = userPhone.getUid();
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                System.out.println("getUserImagePrivacyLabel(): " + getUserImagePrivacyLabel());

                new ServerRequest(this).uploadImage(userUID, imageName() + ".jpg", "Regular", getUserImagePrivacyLabel(), getLatitude(), getLongitude(), encodedImage, new StringCallback() {
                    @Override
                    public void done(String string) {
                        System.out.println("THIS IS FROM THE StringCallBack: " + string);
                        if (string.isEmpty()) {
                            Toast.makeText(CameraActivity.this, "Error: Image not uploaded", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(CameraActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                            Intent menuIntent = new Intent(CameraActivity.this, MenuActivity.class);// this needs to change later instead of being sent to MenuActivity.class
                            startActivity(menuIntent);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b){
        switch(compoundButton.getId()){
            case R.id.sUserImagePrivacyLabel:
                if(b){
                    setUserImagePrivacyLabel("Public");
                }
                else{
                    setUserImagePrivacyLabel("Private");
                }
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
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", new java.util.Date());
        String name = userUID + "_" +charSequenceName;
        System.out.println(name);
        return name;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            setLayoutAndButtons();
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
        else{
            finish();
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
    void setUserImagePrivacyLabel(String privacyLabel){
        userImagePrivacyLabel[0] = privacyLabel;
    }

    String getUserImagePrivacyLabel(){
        return userImagePrivacyLabel[0];
    }
}