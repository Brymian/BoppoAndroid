package brymian.bubbles.bryant.camera;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.sendTo.SendTo;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener{
    SurfaceView svPreview;
    FrameLayout preview;
    Toolbar toolbar;
    private Camera mCamera;
    CameraPreview mPreview;
    SurfaceHolder mHolder;
    String imagePurpose;
    RelativeLayout rlCamera;
    ImageView ivFlash;
    MenuItem switchCamera;
    FloatingActionButton fabDone;
    int currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    int order = 1;
    private String encodedImage, thumbnailEncodedImage;

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
            Log.e("imagePurpose", imagePurpose);
        }
        /* Makes the activity fullscreen */
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        /* Setting xml layout */
        setContentView(R.layout.camera_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(0, getStatusBarHeight(),0, 0);
        toolbar.bringToFront();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivFlash = (ImageView) findViewById(R.id.ivFlash);
        ivFlash.setOnClickListener(this);

        rlCamera = (RelativeLayout) findViewById(R.id.rlCamera);
        rlCamera.setOnClickListener(this);

        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.hide();
        fabDone.setOnClickListener(this);

        /* Create an instance of Camera */
        mCamera = getCameraInstance();

        /* Create our Preview view and set it as the content of our activity. */
        mPreview = new CameraPreview(this, mCamera);
        //svPreview = (SurfaceView) findViewById(R.id.svPreview);
        preview = (FrameLayout) findViewById(R.id.camera_activity);
        preview.addView(mPreview);
        mHolder = mPreview.getHolder();

        if(SaveSharedPreference.getFlashOn(this).length()  != 0){
            ivFlash.setImageResource(R.mipmap.ic_flash_off_white_24dp);
            flashLightOn();
        }
        else{
            ivFlash.setImageResource(R.mipmap.ic_flash_on_white_24dp);
            flashLightOff();
        }
        setAutoFocus();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rlCamera:
                takeImage();
                break;

            case R.id.ivFlash:
                if(SaveSharedPreference.getFlashOn(this).length() == 0){
                    flashLightOn();
                    ivFlash.setImageResource(R.mipmap.ic_flash_off_white_24dp);
                    SaveSharedPreference.setFlashOn(this);
                }
                else if(SaveSharedPreference.getFlashOn(this).length() != 0){
                    flashLightOff();
                    ivFlash.setImageResource(R.mipmap.ic_flash_on_white_24dp);
                    SaveSharedPreference.clearFlashOn(this);
                }
                break;
            case R.id.fabDone:
                switch(imagePurpose){
                    case "Regular":
                        SendTo sendTo = new SendTo();
                        Bundle bundle = new Bundle();
                        bundle.putString("encodedImage" , encodedImage);
                        bundle.putString("thumbnailEncodedImage", thumbnailEncodedImage);
                        sendTo.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.camera_activity, sendTo);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (order == 1){
            super.onBackPressed();
        } else if (order == 2){
            order = 1;
            mCamera.startPreview();
            ivFlash.setVisibility(View.VISIBLE);
            switchCamera.setVisible(true);
            rlCamera.setVisibility(View.VISIBLE);
            fabDone.hide();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mCamera != null){
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            //mPreview.getHolder().removeCallback(mPreview);
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null){
            preview.removeAllViews();
            mCamera = getCameraInstance();
            mPreview = new CameraPreview(this, mCamera);
            preview = (FrameLayout) findViewById(R.id.camera_activity);
            preview.addView(mPreview);
            mHolder = mPreview.getHolder();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.camera_activity_menu, menu);
        switchCamera = menu.findItem(R.id.switch_cam);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.switch_cam:
                flipCamera();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setAutoFocus(){
        Camera.Parameters autoFocusParams = mCamera.getParameters();
        autoFocusParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(autoFocusParams);
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
        setCameraDisplayOrientation(CameraActivity.this, currentCameraId, mCamera);
        try {
            mCamera.setPreviewDisplay(mHolder);
        }
        catch (IOException e) {
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

                String timeStamp = new SimpleDateFormat( "yyyyMMdd_HHmmss").format( new Date( ));
                String output_file_name = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + timeStamp + ".jpeg";

                File pictureFile = new File(output_file_name);
                if (pictureFile.exists()) {
                    pictureFile.delete();
                }

                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);

                    Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Bitmap thumbnailImage = getResizedBitmap(realImage, 300);

                    ExifInterface exif=new ExifInterface(pictureFile.toString());

                    Log.d("EXIF value", exif.getAttribute(ExifInterface.TAG_ORIENTATION));
                    if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")){
                        realImage= rotate(realImage, 90);
                    }
                    else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")){
                        realImage= rotate(realImage, 270);
                    }
                    else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")){
                        realImage= rotate(realImage, 180);
                    }
                    else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")){
                        if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
                            realImage = rotate(realImage, 90);
                        }
                        else if (currentCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT){
                            realImage = rotate(realImage, 270);
                        }
                    }

                    boolean bo = realImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                    fos.close();

                    Log.d("Info", bo + "");
                    Log.e("BitmapInfo", "Normal size: " + realImage.getByteCount()+"\nThumbnail size: " +thumbnailImage.getAllocationByteCount());

                    ByteArrayOutputStream bsRealImage = new ByteArrayOutputStream();
                    realImage.compress(Bitmap.CompressFormat.JPEG, 100, bsRealImage);
                    byte[] realImageByteArray = bsRealImage.toByteArray();
                    encodedImage = Base64.encodeToString(realImageByteArray, Base64.DEFAULT);
                    ByteArrayOutputStream bsThumbnailImage = new ByteArrayOutputStream();
                    thumbnailImage.compress(Bitmap.CompressFormat.JPEG, 100, bsThumbnailImage);
                    byte[] thumbnailByteArray = bsThumbnailImage.toByteArray();
                    thumbnailEncodedImage = Base64.encodeToString(thumbnailByteArray, Base64.DEFAULT);
                }
                catch (FileNotFoundException e) {
                    Log.d("Info", "File not found: " + e.getMessage());
                }
                catch (IOException e) {
                    Log.d("TAG", "Error accessing file: " + e.getMessage());
                }
            }
        });
        order = 2;
        ivFlash.setVisibility(View.GONE);
        switchCamera.setVisible(false);
        rlCamera.setVisibility(View.GONE);
        fabDone.show();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        // mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
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
            c = Camera.open();//attempt to get a Camera instance
        }
        catch (Exception e){
            //Camera is not available (in use or does not exist)
        }
        return c;//returns null if camera is unavailable
    }

    public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
            Log.e("CameraActivity", "front: "+result);
        }
        else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
            Log.e("CameraActivity", "back: "+result);
        }
        camera.setDisplayOrientation(result);
    }

}
