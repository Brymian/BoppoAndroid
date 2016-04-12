package brymian.bubbles.bryant.camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;

/**
 * Created by Almanza on 3/14/2016.
 */
public class CameraActivity extends Activity implements View.OnClickListener{
    ImageButton ibCapture, ibCheck, ibCancel;
    FrameLayout preview;
    FloatingActionMenu fabMenu;
    FloatingActionButton fabCapture, fabGoBack;
    FloatingActionButton fabFlash, fabSwitchCamera;
    private Camera mCamera;
    CameraPreview mPreview;
    String imagePurpose;

    private Handler mUiHandler = new Handler();


    byte[] data;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*---------------------------Checks if the device has a camera----------------------------*/
        if(!checkCameraHardware(this)){
            Toast.makeText(CameraActivity.this, "No camera found", Toast.LENGTH_SHORT).show();
            finish();
        }
        /*----------------------------------------------------------------------------------------*/

        /*--------------------------------Checking for putExtras()--------------------------------*/
        String imagePurpose;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                imagePurpose = null;
            }
            else {
                imagePurpose = extras.getString("imagePurpose");
            }
        }
        else {
            imagePurpose = savedInstanceState.getString("imagePurpose");
        }
        /*----------------------------------------------------------------------------------------*/
        if(imagePurpose != null){
            this.imagePurpose = imagePurpose;
            System.out.println("imagePurpose: " + imagePurpose);
        }

        /* Makes the activity fullscreen */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* Setting xml layout */
        setContentView(R.layout.activity_camera);

        /* Create an instance of Camera */
        mCamera = getCameraInstance();

        /* Create our Preview view and set it as the content of our activity. */
        mPreview = new CameraPreview(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);


        /*------------------------------Floating Action Buttons-----------------------------------*/
        fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);
        fabCapture = (FloatingActionButton) findViewById(R.id.fabCapture);
        fabGoBack = (FloatingActionButton) findViewById(R.id.fabGoBack);
        fabMenu.hideMenuButton(false);
        fabCapture.hide(false);
        fabGoBack.hide(false);
        int delay = 400;
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fabMenu.showMenuButton(true);
                fabMenu.bringToFront();

                fabCapture.show(true);
                fabCapture.bringToFront();
                fabCapture.setOnClickListener(CameraActivity.this);

                fabGoBack.show(true);
                fabGoBack.bringToFront();

            }
        }, delay);

        fabFlash = (FloatingActionButton) findViewById(R.id.fabFlash);
        if(SaveSharedPreference.getFlashOn(this).length()  != 0){
            fabFlash.setImageResource(R.mipmap.ic_flash_off_black_24dp);
            flashLightOn();
        }
        else{
            fabFlash.setImageResource(R.mipmap.ic_flash_on_black_24dp);
            flashLightOff();
        }

        fabFlash.setOnClickListener(this);

        fabSwitchCamera = (FloatingActionButton) findViewById(R.id.fabSwitchCamera);
        fabSwitchCamera.setImageResource(R.mipmap.ic_switch_camera_black_24dp);
        fabSwitchCamera.setOnClickListener(this);

        setAutoFocus();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.fabCapture:
                mCamera.takePicture(mShutter, mRaw, mPicture);
                break;
            case R.id.fabFlash:
                if(SaveSharedPreference.getFlashOn(this).length() == 0){
                    flashLightOn();
                    fabFlash.setImageResource(R.mipmap.ic_flash_off_black_24dp);
                    SaveSharedPreference.setFlashOn(this);
                }
                else if(SaveSharedPreference.getFlashOn(this).length() != 0){
                    flashLightOff();
                    fabFlash.setImageResource(R.mipmap.ic_flash_on_black_24dp);
                    SaveSharedPreference.clearFlashOn(this);
                }
                break;
            case R.id.fabSwitchCamera:
                switchToFrontCamera();
                break;
            /*
            case R.id.ibCheck:
                if(imagePurpose.equals("Regular")) {
                    startActivity(new Intent(this, SendTo.class)
                            .putExtra("encodedImage",
                                    Base64.encodeToString(getImageDataByte(), Base64.DEFAULT)));
                }
                else if(imagePurpose.equals("Profile")){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("encodedImage", getImageDataByte());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
                break;
                */
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mCamera != null){
            mCamera.release();
            mCamera = null;
        }
        //originally just mCamera.release();
    }

    private void setAutoFocus(){
        Camera.Parameters autoFocusParams = mCamera.getParameters();
        autoFocusParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(autoFocusParams);
        Toast.makeText(this, "AutoFocus on", Toast.LENGTH_SHORT).show();
    }

    private void switchToFrontCamera(){
        System.out.println("Number of cameras: " + mCamera.getNumberOfCameras());

        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        /*
        if(mCamera != null){
            mCamera.stopPreview();
        }

        mCamera.release();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        */

    }
    private void flashLightOn() {
        try {
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                Camera.Parameters p = mCamera.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                mCamera.setParameters(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception flashLightOn()", Toast.LENGTH_SHORT).show();
        }
    }

    private void flashLightOff() {
        try {
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                Camera.Parameters p = mCamera.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception flashLightOn()", Toast.LENGTH_SHORT).show();
        }
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
            setImageDataByte(data);
        }
    };

    private Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {

        }
    };

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


    String imageName(){
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", new java.util.Date());
        String name = SaveSharedPreference.getUserUID(this) + "_" + charSequenceName;
        System.out.println(name);
        return name;
    }



}
