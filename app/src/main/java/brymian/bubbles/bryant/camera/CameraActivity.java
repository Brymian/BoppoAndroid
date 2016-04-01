package brymian.bubbles.bryant.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

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
    LinearLayout llPictureTakenButtons;
    FloatingActionButton floatingActionButton;
    private Camera mCamera;
    private CameraPreview mPreview;
    String imagePurpose;


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

        setContentView(R.layout.activity_camera);

        /* Create an instance of Camera */
        mCamera = getCameraInstance();

        /* Create our Preview view and set it as the content of our activity. */
        mPreview = new CameraPreview(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        llPictureTakenButtons = (LinearLayout) findViewById(R.id.llPictureTakenButtons);
        ibCapture = (ImageButton) findViewById(R.id.ibCapture);
        ibCheck = (ImageButton) findViewById(R.id.ibCheck);
        ibCancel = (ImageButton) findViewById(R.id.ibCancel);

        llPictureTakenButtons.setVisibility(View.GONE);
        ibCapture.setOnClickListener(this);
        ibCheck.setOnClickListener(this);
        ibCancel.setOnClickListener(this);
        ibCapture.bringToFront();


        /*--------------------------------FloatingActionButton------------------------------------*/
        /* FloatingActionButton main button */
        ImageView icon = new ImageView(this);
        icon.setImageResource(R.mipmap.add);
        floatingActionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        /* sub menu buttons */
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.mipmap.switch_camera);

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(android.R.drawable.ic_menu_delete);

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageResource(android.R.drawable.ic_menu_info_details);


        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .setRadius(400)
                .attachTo(floatingActionButton)
                .build();
        /*----------------------------------------------------------------------------------------*/
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ibCapture:
                mCamera.takePicture(mShutter, mRaw, mPicture);
                break;
            case R.id.ibCheck:
                if(imagePurpose.equals("Regular")) {
                    startActivity(new Intent(this, SendTo.class)   /* starting SendTo.java */
                            .putExtra("encodedImage",                /* sending the image in String form */
                                    Base64.encodeToString(getImageDataByte(), Base64.DEFAULT)));
                }
                else if(imagePurpose.equals("Profile")){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("encodedImage", getImageDataByte());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
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
            llPictureTakenButtons.setVisibility(View.VISIBLE);
            llPictureTakenButtons.bringToFront();
            floatingActionButton.setVisibility(View.GONE);
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
