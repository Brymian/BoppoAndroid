package brymian.bubbles.bryant.pictures;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.objects.Image;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;

public class ProfilePicturesActivity2 extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    public static ImageView ivProfilePicture1, ivProfilePicture2, ivProfilePicture3, ivProfilePicture4;
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
        ivProfilePicture1.setOnClickListener(this);
        ivProfilePicture2 = (ImageView) findViewById(R.id.ivProfilePicture2);
        ivProfilePicture2.setOnClickListener(this);
        ivProfilePicture3 = (ImageView) findViewById(R.id.ivProfilePicture3);
        ivProfilePicture3.setOnClickListener(this);
        ivProfilePicture4 = (ImageView) findViewById(R.id.ivProfilePicture4);
        ivProfilePicture4.setOnClickListener(this);

        famProfilePictures = (FloatingActionMenu) findViewById(R.id.famProfilePictures);
        famProfilePictures.showMenu(true);

        fabAddProfilePicture = (FloatingActionButton) findViewById(R.id.fabAddProfilePicture);
        fabAddProfilePicture.show(true);
        fabAddProfilePicture.setOnClickListener(this);
        fabDeleteProfilePicture = (FloatingActionButton) findViewById(R.id.fabDeleteProfilePicture);
        fabDeleteProfilePicture.show(true);
        fabDeleteProfilePicture.setOnClickListener(this);

        getProfileImages();
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();

        switch (view.getId()){
            case R.id.ivProfilePicture1:
                setImageNumber(0);
                startFragment(fm, R.id.profile_pictures_activity2, new ProfilePicturesViewImage());
                break;

            case R.id.ivProfilePicture2:
                setImageNumber(1);
                startFragment(fm, R.id.profile_pictures_activity2, new ProfilePicturesViewImage());
                break;

            case R.id.ivProfilePicture3:
                setImageNumber(2);
                startFragment(fm, R.id.profile_pictures_activity2, new ProfilePicturesViewImage());
                break;

            case R.id.ivProfilePicture4:
                setImageNumber(3);
                startFragment(fm, R.id.profile_pictures_activity2, new ProfilePicturesViewImage());
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
            case android.R.id.home:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public static ImageView[] ivProfilePicture = {ivProfilePicture1, ivProfilePicture2, ivProfilePicture3, ivProfilePicture4};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            byte[] byteArrayImage =  data.getExtras().getByteArray("encodedImage");
            String imageName = data.getExtras().getString("imageName");
            ImageView[] ivProfilePicture = {ivProfilePicture1, ivProfilePicture2, ivProfilePicture3, ivProfilePicture4};

            for(int i = 0; i < ivProfilePicture.length; i++){
                if(ivProfilePicture[i].getTag() == null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage.length);
                    ivProfilePicture[i].setImageBitmap(bitmap);
                    ivProfilePicture[i].setTag(i);

                    String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                    new ServerRequestMethods(this).uploadImage(SaveSharedPreference.getUserUID(this), Integer.toString(i), "Profile", "Public", SaveSharedPreference.getLatitude(this), SaveSharedPreference.getLongitude(this), encodedImage, new StringCallback() {
                        @Override
                        public void done(String string) {
                            Toast.makeText(ProfilePicturesActivity2.this, string, Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                }
            }
        }
    }

    private void getProfileImages() {
        new ServerRequestMethods(this).getImagesByUid(SaveSharedPreference.getUserUID(this), "Profile", new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                if(imageList.size() > 0){
                    for (int i =0; i < imageList.size(); i++){
                        //setImageUiid(imageList.get(i).imageUiid, i);
                        new DownloadImage(imageList.get(i).path, i).execute();
                    }
                }
            }
        });

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

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap>{
        String path;
        int location;
        public DownloadImage(String path, int location){
            this.path = path;
            this.location = location;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URLConnection connection = new URL(path).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);
                return BitmapFactory.decodeStream((InputStream) connection.getContent(), null, null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap!=null){
                if(location == 0){
                    ivProfilePicture1.setImageBitmap(bitmap);
                }else if (location == 1){
                    ivProfilePicture2.setImageBitmap(bitmap);
                }else if (location == 2){
                    ivProfilePicture3.setImageBitmap(bitmap);
                }else if(location == 3){
                    ivProfilePicture4.setImageBitmap(bitmap);
                }
            }
        }
    }


    public static int num;
    public static int[] uiid;
    private void setImageNumber(int n){num = n;}
    public static int getImageNumber(){return num;}
    private void setImageUiid(int n, int i){uiid[i] = n;}
    public static int getImageUiid(int i){return uiid[i];}
}
