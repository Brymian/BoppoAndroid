package brymian.bubbles.bryant.profile;


import android.app.ActivityOptions;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;
import brymian.bubbles.objects.Image;

public class ProfileEdit extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    TextView tvChangeProfilePicture ,tvFirstLastName, tvUsername;
    ImageView ivProfilePicture;

    int DONE_CODE = 1;
    int CAMERA_CODE = 2;
    int GALLERY_CODE = 3;

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
        ivProfilePicture.setOnClickListener(this);
        
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
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        ivProfilePicture.setVisibility(View.VISIBLE);
                        ivProfilePicture.setImageBitmap(bitmap);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
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
        if (SaveSharedPreference.getUserProfileImagePath(this).isEmpty()){
            new UserImageRequest(this).getImagesByUidAndPurpose(SaveSharedPreference.getUserUID(this), "Profile", null, new ImageListCallback() {
                @Override
                public void done(List<Image> imageList) {
                    if (imageList.size() > 0) {
                        Picasso.with(ProfileEdit.this).load(imageList.get(0).userImagePath).into(ivProfilePicture);
                        SaveSharedPreference.setUserProfileImagePath(ProfileEdit.this,imageList.get(0).userImagePath);
                    }
                }
            });
        }
        else {
            Picasso.with(this).load(SaveSharedPreference.getUserProfileImagePath(this)).fit().centerCrop().into(ivProfilePicture);
        }
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
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_CODE);
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
