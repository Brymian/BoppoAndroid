package brymian.bubbles.bryant.MenuButtons.ProfileButtons;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MenuActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest;
import brymian.bubbles.damian.nonactivity.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;

public class ProfileBackground extends FragmentActivity implements View.OnClickListener{
    private static final int RESULT_LOAD_IMAGE_1 = 1;
    private static final int RESULT_LOAD_IMAGE_2 = 2;
    private static final int RESULT_LOAD_IMAGE_3 = 3;
    private static final int RESULT_LOAD_IMAGE_4 = 4;

    ImageView imageView1, imageView2, imageView3, imageView4;
    Button bSave;
    ImageButton ibMenu;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_background);

        imageView1 = (ImageView) findViewById(R.id.ivImageView1);
        imageView2 = (ImageView) findViewById(R.id.ivImageView2);
        imageView3 = (ImageView) findViewById(R.id.ivImageView3);
        imageView4 = (ImageView) findViewById(R.id.ivImageView4);

        bSave = (Button) findViewById(R.id.bSave);

        ibMenu = (ImageButton) findViewById(R.id.ibMenu);

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_input_add);
        imageView1.setImageDrawable(drawable);
        imageView2.setImageDrawable(drawable);
        imageView3.setImageDrawable(drawable);
        imageView4.setImageDrawable(drawable);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

        bSave.setOnClickListener(this);
        ibMenu.setOnClickListener(this);
    }

    public void onClick(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        switch (view.getId()){
            case R.id.ibMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;
            case R.id.ivImageView1:
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE_1);;
                break;
            case R.id.ivImageView2:
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE_2);
                break;
            case R.id.ivImageView3:
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE_3);
                break;
            case R.id.ivImageView4:
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE_4);
                break;
            case R.id.bSave:
                saveButton();
                break;
        }
    }

    public void saveButton(){
        UserDataLocal udl = new UserDataLocal(this);
        User userPhone = udl.getUserData();
        int userUID = userPhone.getUid();//CHANGE 1 to userUID in uploadImage 1st parameter
        ImageView[] IVarray = {imageView1, imageView2, imageView3, imageView4};
        String[] IVarraySTRING = {"imageView1", "imageView2", "imageView3", "imageView4"};
        for(int i = 0; i < IVarray.length; i++){
            int imageViewID = IVarray[i].getId();
            if(imageViewID != R.mipmap.ic_input_add){
                Bitmap image = ((BitmapDrawable) IVarray[i].getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                new ServerRequest(this).uploadImage(1, IVarraySTRING[i], "Profile", "Private", 0, 0, encodedImage, new StringCallback() {
                    @Override
                    public void done(String string) {
                        System.out.println(string);
                    }
                });
            }
            else {
                //do nothing
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == RESULT_LOAD_IMAGE_1 && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            imageView1.setImageURI(selectedImage);
        }
        if (requestCode == RESULT_LOAD_IMAGE_2 && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            imageView2.setImageURI(selectedImage);
        }
        if (requestCode == RESULT_LOAD_IMAGE_3 && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            imageView3.setImageURI(selectedImage);
        }
        if (requestCode == RESULT_LOAD_IMAGE_4 && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            imageView4.setImageURI(selectedImage);
        }
    }
}
