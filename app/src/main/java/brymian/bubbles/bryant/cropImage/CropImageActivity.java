package brymian.bubbles.bryant.cropImage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.edmodo.cropper.CropImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.ProfileEdit;
import brymian.bubbles.damian.nonactivity.CustomException.SetOrNotException;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Image;

public class CropImageActivity extends AppCompatActivity {
    Toolbar toolbar;
    CropImageView cropImageView;
    FloatingActionButton fabDone;
    private final int GALLERY_CODE = 1;
    private final int CAMERA_CODE = 2;
    private String from;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkProfileImageExists();

        String from = getIntent().getStringExtra("from");
        setFrom(from);
        if (from.equals("profileGallery") || from.equals("editGallery") || from.equals("gallery")){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);
        }
        else if(from.equals("profileCamera") || from.equals("editCamera")){
            startActivityForResult(new Intent(this, CameraActivity.class), CAMERA_CODE);
        }

        setContentView(R.layout.crop_image_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.Crop_Image);

        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        fabDone = (FloatingActionButton) findViewById(R.id.fabDone);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap croppedImage = cropImageView.getCroppedImage();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent = getIntent();
                intent.putExtra("image", byteArray);
                setResult(RESULT_OK, intent);
                if (getFrom().equals("profileGallery") || getFrom().equals("profileCamera")){
                    //uploadProfileImage(Base64.encodeToString(byteArray, Base64.DEFAULT));
                }
            }
        });
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    private void checkProfileImageExists(){
        new UserImageRequest(this).getImagesByUidAndPurpose(SaveSharedPreference.getUserUID(CropImageActivity.this), "Profile", null, new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                if (imageList.size() > 0){
                    for (int i = 0; i < imageList.size(); i++){
                        Log.e("check", "position: " + i + "\tuiid: "+imageList.get(i).uiid +"\tpath: " + imageList.get(i).userImagePath);
                        setProfileSequenceNull(imageList.get(0).uiid.intValue());
                    }
                }
            }
        });

    }

    private void setProfileSequenceNull(int uiid){
        try {
            new UserImageRequest(this).setImage(
                    uiid, /* uiid */
                    null, /* userImageProfileSequence */
                    null, /* userImageName */
                    null, /* userImagePurposeLabel */
                    null, /* userImagePrivacyLabel */
                    null, /* userImageGpsLatitude */
                    null, /* userImageGpsLongitude */
                    new Boolean[]{null, true, false, false, false, false, false},
                    new StringCallback() {
                        @Override
                        public void done(String string) {
                            Log.e("profileSeq", string);
                        }
                    }
            );
        } catch (SetOrNotException e) {
            e.printStackTrace();
        }
        deleteProfileImage(uiid);
    }

    private void deleteProfileImage(int uiid){
        new ServerRequestMethods(this).deleteImage(SaveSharedPreference.getUserUID(this), uiid, new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("delete", string);
            }
        });
    }
    private void uploadProfileImage(String encodedImage){
        final String image = encodedImage;
        new UserImageRequest(this).getImagesByUidAndPurpose(SaveSharedPreference.getUserUID(this), "Profile", null, new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                if (imageList.size() > 0) {
                    Log.e("uiid", imageList.get(0).uiid + "");
                    new ServerRequestMethods(CropImageActivity.this).deleteImage(SaveSharedPreference.getUserUID(CropImageActivity.this), imageList.get(0).uiid.intValue(), new StringCallback() {
                        @Override
                        public void done(String string) {
                            Log.e("delete", string);
                            new UserImageRequest(CropImageActivity.this).uploadImage(
                                    SaveSharedPreference.getUserUID(CropImageActivity.this),
                                    0,
                                    imageName(),
                                    "Profile",
                                    "Public",
                                    0.0,
                                    0.0,
                                    image, new StringCallback() {
                                        @Override
                                        public void done(String string) {
                                            Log.e("upProfImage", string);
                                        }
                                    });
                        }
                    });
                    /*
                    new UserImageRequest(CropImageActivity.this).setImage(imageList.get(0).uiid.intValue(), 100, null, null, null, null, null, new StringCallback() {
                        @Override
                        public void done(String string) {
                            Log.e("setImage", string);
                            new UserImageRequest(CropImageActivity.this).uploadImage(
                                    SaveSharedPreference.getUserUID(CropImageActivity.this),
                                    0,
                                    imageName(),
                                    "Profile",
                                    "Public",
                                    0.0,
                                    0.0,
                                    image, new StringCallback() {
                                        @Override
                                        public void done(String string) {
                                            Log.e("upProfImage", string);
                                        }
                                    });
                        }
                    });
                    */
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE){
            if(resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Log.e("data", data.getData() + " ");
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        cropImageView.setImageBitmap(bitmap);
                        cropImageView.setFixedAspectRatio(true);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cropImageView.setAspectRatio(10, 10);
                            }
                        }, 100);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (resultCode == RESULT_CANCELED){
                finish();
            }
        }
        else if (requestCode == CAMERA_CODE){
            if (resultCode == RESULT_OK){
                Log.e("camera", "works");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.episode_create_crop_image_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.rotate:
                cropImageView.rotateImage(90);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String imageName(){
        String charSequenceName = (String) android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", new java.util.Date());
        return SaveSharedPreference.getUserUID(this) + "_" + charSequenceName;
    }

    private void setFrom(String from){
        this.from = from;
    }

    private String getFrom(){
        return this.from;
    }
}
