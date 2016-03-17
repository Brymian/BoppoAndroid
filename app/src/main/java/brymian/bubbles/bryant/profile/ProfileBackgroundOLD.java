package brymian.bubbles.bryant.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;

public class ProfileBackgroundOLD extends FragmentActivity implements View.OnClickListener{
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
        downloadImage();
    }

    public void onClick(View view){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        switch (view.getId()){
            case R.id.ibMenu:

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
        int userUID = userPhone.getUid();

        ImageView[] IVarray = {imageView1, imageView2, imageView3, imageView4};
        String[] IVarraySTRING = {"imageViewTOPleft", "imageViewTOPright", "imageViewBOTTOMleft", "imageViewBOTTOMright"};

        for(int i = 0; i < IVarray.length; i++){
            int imageViewID = IVarray[i].getId();
            if(imageViewID != R.mipmap.ic_input_add){
                Bitmap image = ((BitmapDrawable) IVarray[i].getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                new ServerRequestMethods(this).uploadImage(userUID, IVarraySTRING[i]+".jpg", "Profile", "Private", 0, 0, encodedImage, new StringCallback() {
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

    public void downloadImage(){
            new ServerRequestMethods(this).getImages(1, "Profile", new ImageListCallback() {
                @Override
                public void done(List<brymian.bubbles.damian.nonactivity.Image> imageList) {
                    try {
                        System.out.println(imageList.get(0));

                    } catch (IndexOutOfBoundsException aoobe) {
                        aoobe.printStackTrace();
                        Toast.makeText(ProfileBackgroundOLD.this, "New user!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }
}
