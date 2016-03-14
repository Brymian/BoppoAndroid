package brymian.bubbles.bryant;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.provider.MediaStore;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;


import brymian.bubbles.R;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivityOLD extends Activity {
    //implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
    private static int TAKE_PICTURE = 1;
    private Uri imageUri;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    double[] setLatitudeArray = new double[1];
    double[] setLongitudeArray = new double[1];
    String[] userImagePrivacyLabel = new String[1];

    Button bUploadImage;
    ImageView imageView;
    Switch sUserImagePrivacyLabel;

    private Camera mCamera;
    private CameraPreviewOLD mPreview;

    private static int number = Camera.CameraInfo.CAMERA_FACING_FRONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //takePhoto();
        if(!checkCameraHardware(this)){
            finish();
        }

        //checkCameraHardware(this);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreviewOLD(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        // Add a listener to the Capture button
        /**
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );


        // Add a listener to the Change button
        ImageButton changeButton = (ImageButton) findViewById(R.id.button_change);
        changeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (number == Camera.CameraInfo.CAMERA_FACING_FRONT)
                        {
                            number = Camera.CameraInfo.CAMERA_FACING_BACK;
                        }
                        else
                        {
                            number = Camera.CameraInfo.CAMERA_FACING_FRONT;

                        }
                        //HERE SHOULD BE THE STEPS TO SWITCH
                        Toast.makeText(getApplicationContext(), "Camera changed!", Toast.LENGTH_SHORT).show();
                        // get an image from the camera
                        mCamera = Camera.open(number);

                    }
                }
        );
         **/


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

    /** Check if this device has a camera */
    boolean checkCameraHardware(Context context) {
        /**
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
         **/
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    //deprecated code, use CameraCaptureSession.CaptureCallback in the future for min sdk 21
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                //Log.d(TAG, "Error creating media file, check storage permissions: " +e.getMessage());
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                //Log.d(TAG, "File not found: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                //Log.d(TAG, "Error accessing file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    };

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }






    /**
     *
     * //Setting layout
    public void setLayoutAndButtons(){
        setContentView(R.layout.activity_camera);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        imageView = (ImageView) findViewById(R.id.image_camera);
        sUserImagePrivacyLabel = (Switch) findViewById(R.id.sUserImagePrivacyLabel);

        bUploadImage.setOnClickListener(this);
        sUserImagePrivacyLabel.setOnCheckedChangeListener(this);
        imageView.setOnClickListener(this);
    }

     **/

    /**

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

                new ServerRequestMethods(this).uploadImage(userUID, imageName() + ".jpg", "Regular", getUserImagePrivacyLabel(), getLatitude(), getLongitude(), encodedImage, new StringCallback() {
                    @Override
                    public void done(String string) {
                        System.out.println("THIS IS FROM THE StringCallBack: " + string);
                        if (string.isEmpty()) {
                            Toast.makeText(CameraActivityOLD.this, "Error: Image not uploaded", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(CameraActivityOLD.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                            Intent menuIntent = new Intent(CameraActivityOLD.this, MenuActivity.class);// this needs to change later instead of being sent to MenuActivity.class
                            startActivity(menuIntent);
                        }
                    }
                });
                break;
        }
    }

     **/


    /**
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
    **/

    /**
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

     **/



    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //setLayoutAndButtons();
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