package brymian.bubbles.bryant.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;

/**
 * Created by Almanza on 3/14/2016.
 */
public class CameraActivity extends Activity implements View.OnClickListener{
    FrameLayout preview;
    FloatingActionMenu fabMenu, fabFilterMenu;
    FloatingActionButton fabCapture, fabGoBack, fabConfirm, fabCancel;
    /* sub fab buttons for fabMenu */
    FloatingActionButton fabFlash, fabSwitchCamera;
    /* sub fab buttons for fabFilterMenu */
    FloatingActionButton fabItem1, fabItem2;
    private Camera mCamera;
    CameraPreview mPreview;
    SurfaceHolder mHolder;

    String imagePurpose;

    int currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

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
        setContentView(R.layout.camera_activity);

        /* Create an instance of Camera */
        mCamera = getCameraInstance();

        /* Create our Preview view and set it as the content of our activity. */
        mPreview = new CameraPreview(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        mHolder = mPreview.getHolder();
        setButtonsCameraPreview();
        setAutoFocus();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fabGoBack:
                NavUtils.navigateUpFromSameTask(this);
                releaseCamera();
                break;

            case R.id.fabCapture:
                takeImage();
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
                flipCamera();
                break;

            case R.id.fabConfirm:
                if(imagePurpose.equals("Regular")) {
                    startActivity(new Intent(this, SendTo.class)
                            .putExtra("encodedImage", Base64.encodeToString(getImageDataByte(), Base64.DEFAULT)));
                }
                else if(imagePurpose.equals("Profile")){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("encodedImage", getImageDataByte());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
                break;
            case R.id.fabCancel:
                mCamera.startPreview();
                hideButtonsPictureTaken();
                showButtonsCameraPreview();
                break;
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

    private void flipCamera() {
        if( mCamera != null){
            mCamera.stopPreview();
            mCamera.release();
        }
        //swap the id of the camera to be used
        if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else {
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        mCamera = Camera.open(currentCameraId);

        /*
        setCameraDisplayOrientation(CameraActivity.this, currentCameraId, camera);
        try {

            camera.setPreviewDisplay(previewHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

       //mPreview = new CameraPreview(this, mCamera);
        //preview.addView(mPreview);
        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCamera.startPreview();



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
    private void takeImage() {
        /* Taking the actual picture */
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //setImageDataByte(data);
                String timeStamp = new SimpleDateFormat( "yyyyMMdd_HHmmss").format( new Date( ));
                String output_file_name = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + timeStamp + ".jpeg";

                File pictureFile = new File(output_file_name);
                if (pictureFile.exists()) {
                    pictureFile.delete();
                }

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);

                    Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);

                    ExifInterface exif=new ExifInterface(pictureFile.toString());

                    Log.d("EXIF value", exif.getAttribute(ExifInterface.TAG_ORIENTATION));
                    if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")){
                        realImage= rotate(realImage, 90);
                    } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")){
                        realImage= rotate(realImage, 270);
                    } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")){
                        realImage= rotate(realImage, 180);
                    } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")){
                        realImage= rotate(realImage, 90);
                    }

                    boolean bo = realImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                    fos.close();

                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    realImage.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                    setImageDataByte(bs.toByteArray());
                    System.out.println("getImageByteArray length: " + getImageDataByte().length);

                    Log.d("Info", bo + "");

                } catch (FileNotFoundException e) {
                    Log.d("Info", "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d("TAG", "Error accessing file: " + e.getMessage());
                }
            }
        });
        hideButtonsCameraPreview();
        setButtonsPictureTaken();
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        //       mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    private void showButtonsPictureTaken(){
        fabCancel.show(true);
        fabConfirm.show(true);
        fabFilterMenu.showMenu(true);
    }

    private void showButtonsCameraPreview(){
        fabGoBack.show(true);
        fabCapture.show(true);
        fabMenu.showMenu(true);
    }

    private void hideButtonsCameraPreview(){
        /* hiding the buttons that appeared on camera preview */
        fabGoBack.hide(true);
        fabCapture.hide(true);
        fabMenu.hideMenu(true);
    }

    private void hideButtonsPictureTaken(){
        fabCancel.hide(true);
        fabConfirm.hide(true);
        fabFilterMenu.hideMenu(true);
    }


    private void setButtonsPictureTaken(){
        /* showing the buttons that appear when picture is taken */
        /* cancel button - bottom left */
        fabCancel = (FloatingActionButton) findViewById(R.id.fabCancel);
        fabCancel.setImageResource(R.mipmap.ic_close_black_24dp);
        fabCancel.bringToFront();
        fabCancel.setOnClickListener(this);

        /* confirm button - bottom middle */
        fabConfirm = (FloatingActionButton) findViewById(R.id.fabConfirm);
        fabConfirm.setImageResource(R.mipmap.ic_done_black_24dp);
        fabConfirm.bringToFront();
        fabConfirm.setOnClickListener(this);

        /* filter menu button - bottom right */
        fabFilterMenu = (FloatingActionMenu) findViewById(R.id.fabFilterMenu);
        fabFilterMenu.bringToFront();

        fabItem1 = (FloatingActionButton) findViewById(R.id.fabItem1);
        fabItem1.setImageResource(R.mipmap.ic_filter_list_black_24dp);

        fabItem2 = (FloatingActionButton) findViewById(R.id.fabItem2);
        fabItem2.setImageResource(R.mipmap.ic_person_add_black_24dp);
    }

    private void setButtonsCameraPreview(){
        /*-----------------------buttons that appear when in camera preview-----------------------*/
        /* menu button - bottom right*/
        fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);
        fabMenu.bringToFront();

        /* sub menu buttons */
        fabFlash = (FloatingActionButton) findViewById(R.id.fabFlash);
        /* checking for previous setting if user kept flash on/off */
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

        /* capture button - bottom middle */
        fabCapture = (FloatingActionButton) findViewById(R.id.fabCapture);
        fabCapture.setImageResource(R.mipmap.ic_photo_camera_black_24dp);
        fabCapture.bringToFront();
        fabCapture.setOnClickListener(CameraActivity.this);

        /* go back to MainActivity button - bottom left */
        fabGoBack = (FloatingActionButton) findViewById(R.id.fabGoBack);
        fabGoBack.setImageResource(R.mipmap.ic_arrow_back_black_24dp);
        fabGoBack.bringToFront();
        fabGoBack.setOnClickListener(CameraActivity.this);
        /*----------------------------------------------------------------------------------------*/
    }

    private void setImageDataByte(byte[] data){this.data = data;}

    private byte[] getImageDataByte(){return data;}

    Bitmap bitmap;
    private void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    private Bitmap getBitmap(){
        return bitmap;
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
