package brymian.bubbles.bryant.camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;

/**
 * Created by Almanza on 3/14/2016.
 */
public class CameraActivity extends Activity implements View.OnClickListener{
    ImageButton ibCapture, ibCheck;
    FrameLayout preview;
    private Camera mCamera;
    private CameraPreview mPreview;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**-------------------------Checks if the device has a camera----------------------------**/
        if(!checkCameraHardware(this)){
            Toast.makeText(CameraActivity.this, "No camera found", Toast.LENGTH_SHORT).show();
            finish();
        }
        /**--------------------------------------------------------------------------------------**/

        /* Makes the activity fullscreen */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        ibCapture = (ImageButton) findViewById(R.id.ibCapture);
        ibCheck = (ImageButton) findViewById(R.id.ibCheck);
        ibCapture.setOnClickListener(this);
        ibCheck.setOnClickListener(this);
        ibCheck.setVisibility(View.GONE);
        ibCapture.bringToFront();


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ibCapture:
                mCamera.takePicture(mShutter, mRaw, mPicture);
                break;
            case R.id.ibCheck:
                Toast.makeText(CameraActivity.this, "works", Toast.LENGTH_SHORT).show();
                String encodedImage = Base64.encodeToString(getImageDataByte(), Base64.DEFAULT);
                new ServerRequestMethods(this).uploadImage(
                        getUidUserDataLocal(),
                        imageName() + ".jpg", "Regular", "Public",
                        SaveSharedPreference.getLatitude(getApplicationContext()),
                        SaveSharedPreference.getLongitude(getApplicationContext()),
                        encodedImage,
                        new StringCallback() {
                            @Override
                            public void done(String string) {
                                System.out.println("From ServerRequestMethods: "+string);
                            }
                        }
                );

                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    private Camera.ShutterCallback mShutter = new Camera.ShutterCallback(){
        @Override
        public void onShutter() {

        }
    };

    private Camera.PictureCallback mRaw = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            ibCapture.setVisibility(View.GONE);
            ibCheck.setVisibility(View.VISIBLE);
            ibCheck.bringToFront();
            //String string = new String(data);
            //System.out.println("String: "+string);
            setImageDataByte(data);

            /** original code
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                //Log.d(TAG, "Error creating media file, check storage permissions: " + e.getMessage());
                Toast.makeText(CameraActivity.this, "Error creating media file, check storage permissions", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                //Log.d(TAG, "File not found: " + e.getMessage());
                Toast.makeText(CameraActivity.this, "File not found", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                //Log.d(TAG, "Error accessing file: " + e.getMessage());
                Toast.makeText(CameraActivity.this, "Error accessing file", Toast.LENGTH_SHORT).show();
            }
             **/
        }


    };
    byte[] data;
    private void setImageDataByte(byte[] data){
        this.data = data;
    }

    private byte[] getImageDataByte(){
        return data;
    }


    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
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
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
        }
        else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_"+ timeStamp + ".mp4");
        }
        else {
            return null;
        }

        return mediaFile;
    }

    /** Checks if the device has a camera **/
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
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

    private int getUidUserDataLocal(){
        UserDataLocal udl = new UserDataLocal(this);
        User user = udl.getUserData();
        return user.getUid();
    }

    String imageName(){
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", new java.util.Date());
        String name = getUidUserDataLocal() + "_" + charSequenceName;
        System.out.println(name);
        return name;
    }



}
