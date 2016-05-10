package brymian.bubbles.bryant.pictures;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import brymian.bubbles.R;
import brymian.bubbles.bryant.camera.CameraActivity;

public class ProfilePicturesActivity2 extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    ImageView ivProfilePicture1, ivProfilePicture2, ivProfilePicture3, ivProfilePicture4;
    FloatingActionMenu famProfilePictures;
    FloatingActionButton fabAddProfilePicture, fabDeleteProfilePicture;
    final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_pictures_activity2);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Profile_Pictures);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivProfilePicture1 = (ImageView) findViewById(R.id.ivProfilePicture1);
        ivProfilePicture2 = (ImageView) findViewById(R.id.ivProfilePicture2);
        ivProfilePicture3 = (ImageView) findViewById(R.id.ivProfilePicture3);
        ivProfilePicture4 = (ImageView) findViewById(R.id.ivProfilePicture4);

        famProfilePictures = (FloatingActionMenu) findViewById(R.id.famProfilePictures);
        famProfilePictures.showMenu(true);

        fabAddProfilePicture = (FloatingActionButton) findViewById(R.id.fabAddProfilePicture);
        fabAddProfilePicture.show(true);
        fabAddProfilePicture.setOnClickListener(this);
        fabDeleteProfilePicture = (FloatingActionButton) findViewById(R.id.fabDeleteProfilePicture);
        fabDeleteProfilePicture.show(true);
        fabDeleteProfilePicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivProfilePicture1:

                break;

            case R.id.ivProfilePicture2:

                break;

            case R.id.ivProfilePicture3:

                break;

            case R.id.ivProfilePicture4:

                break;

            case R.id.fabAddProfilePicture:
                    uploadAlert();
                break;

            case R.id.fabDeleteProfilePicture:

                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_pictures_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            byte[] byteArrayImage =  data.getExtras().getByteArray("encodedImage");
            System.out.println("Byte array length: " + byteArrayImage.length);

            ImageView[] ivProfilePicture = {ivProfilePicture1, ivProfilePicture2, ivProfilePicture3, ivProfilePicture4};

            for(int i = 0; i < ivProfilePicture.length; i++){
                if(ivProfilePicture[i].getTag() == null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage.length);
                    ivProfilePicture[i].setImageBitmap(bitmap);
                    ivProfilePicture[i].setTag("Profile picture " + i);
                    break;
                }
            }
        }
    }

    private void uploadAlert() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_profile_picture);
        dialog.setTitle(R.string.Select);

        TextView tvCamera = (TextView) dialog.findViewById(R.id.tvCamera);
        TextView tvGallery = (TextView) dialog.findViewById(R.id.tvGallery);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ProfilePicturesActivity2.this, CameraActivity.class).putExtra("imagePurpose", "Profile"), REQUEST_CODE);
            }
        });
        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilePicturesActivity2.this, "Gallery", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


}
