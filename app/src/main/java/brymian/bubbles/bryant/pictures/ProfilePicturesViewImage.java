package brymian.bubbles.bryant.pictures;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;

/**
 * Created by almanza1112 on 5/12/16.
 */
public class ProfilePicturesViewImage extends Fragment {

    Toolbar mToolbar;
    ImageView ivProfilePictureViewImage, ivDelete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_pictures_view_image, container, false);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbar.bringToFront();

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ivProfilePictureViewImage = (ImageView) rootView.findViewById(R.id.ivProfilePictureViewImage);
        if(ProfilePicturesActivity2.getImageNumber() == 0){
            Bitmap bitmap =((BitmapDrawable)ProfilePicturesActivity2.ivProfilePicture1.getDrawable()).getBitmap();
            ivProfilePictureViewImage.setImageBitmap(bitmap);
        }else if (ProfilePicturesActivity2.getImageNumber() == 1){
            Bitmap bitmap =((BitmapDrawable)ProfilePicturesActivity2.ivProfilePicture2.getDrawable()).getBitmap();
            ivProfilePictureViewImage.setImageBitmap(bitmap);
        }else if(ProfilePicturesActivity2.getImageNumber() == 2){
            Bitmap bitmap =((BitmapDrawable)ProfilePicturesActivity2.ivProfilePicture3.getDrawable()).getBitmap();
            ivProfilePictureViewImage.setImageBitmap(bitmap);
        }else if(ProfilePicturesActivity2.getImageNumber() == 3){
            Bitmap bitmap =((BitmapDrawable)ProfilePicturesActivity2.ivProfilePicture4.getDrawable()).getBitmap();
            ivProfilePictureViewImage.setImageBitmap(bitmap);
        }


        ivDelete = (ImageView) rootView.findViewById(R.id.ivDelete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new ServerRequestMethods(getActivity()).deleteImage();
            }
        });
        return rootView;
    }
}
