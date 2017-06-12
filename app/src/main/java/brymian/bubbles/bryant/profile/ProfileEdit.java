package brymian.bubbles.bryant.profile;


import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.cropImage.CropImageActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;

public class ProfileEdit extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    TextView tvChangeProfilePicture ,tvFirstLastName, tvUsername;
    ImageView ivProfilePicture;

    int GALLERY_CODE = 1;
    int CAMERA_CODE = 2;
    int DONE_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.Edit_profile);

        ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);
        //ivProfilePicture.setOnClickListener(this);
        
        tvChangeProfilePicture = (TextView) findViewById(R.id.tvChangeProfilePicture);
        tvChangeProfilePicture.setOnClickListener(this);

        tvFirstLastName = (TextView) findViewById(R.id.tvFirstLastName);
        tvFirstLastName.setOnClickListener(this);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setOnClickListener(this);

        getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivProfilePicture:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    View statusBar = findViewById(android.R.id.statusBarBackground);
                    View navigationBar = findViewById(android.R.id.navigationBarBackground);
                    View image = findViewById(R.id.ivProfilePicture);

                    List<Pair<View, String>> pairs = new ArrayList<>();
                    pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
                    pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
                    pairs.add(Pair.create(image, image.getTransitionName()));

                    Bundle options = ActivityOptions.makeSceneTransitionAnimation(this, pairs.toArray(new Pair[pairs.size()])).toBundle();
                    startActivity(new Intent(this, ProfileEditViewImage.class), options);
                }
                else {
                    startActivity(new Intent(this, ProfileEditViewImage.class));
                }
                break;

            case R.id.tvChangeProfilePicture:
                changeProfilePictureAlertDialog();
                break;

            case R.id.tvFirstLastName:

                break;

            case R.id.tvUsername:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DONE_CODE) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
        else if (requestCode == GALLERY_CODE){
            if(resultCode == RESULT_OK) {
                if (data != null) {
                    setProfileImage();
                }
            }
        }
        else if (requestCode == CAMERA_CODE){
            if (resultCode == RESULT_OK){
                Log.e("camera", "works");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUserInfo(){
        tvFirstLastName.setText(SaveSharedPreference.getUserFirstName(this) + " " + SaveSharedPreference.getUserLastName(this));
        tvUsername.setText(SaveSharedPreference.getUsername(this));
        setProfileImage();
    }

    private void setProfileImage(){
        new UserImageRequest(this).getImagesByUid(SaveSharedPreference.getUserUID(this), true, new StringCallback() {
            @Override
            public void done(String string) {
                Log.e("String", string);
                try{
                    JSONObject jsonObject = new JSONObject(string);
                    String imagesString = jsonObject.getString("images");
                    JSONArray imagesArray = new JSONArray(imagesString);
                    if (imagesArray.length() > 0){
                        JSONObject imageObj = imagesArray.getJSONObject(0);
                        String imagePath = imageObj.getString("userImageThumbnailPath");
                        Picasso.with(ProfileEdit.this).load(imagePath).fit().centerCrop().into(ivProfilePicture);
                        SaveSharedPreference.setUserProfileImagePath(ProfileEdit.this, imagePath);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeProfilePictureAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.profile_edit_alertdialog_profile_picture, null);

        TextView tvCamera =  (TextView) alertLayout.findViewById(R.id.tvCamera);
        TextView tvGallery = (TextView) alertLayout.findViewById(R.id.tvGallery);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();

        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ProfileEdit.this, CameraActivity.class), CAMERA_CODE);
                dialog.dismiss();
            }
        });

        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivityForResult(new Intent(ProfileEdit.this, CropImageActivity.class).putExtra("from", "profileGallery"), GALLERY_CODE);

            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
