package com.brymian.boppo.bryant.pictures;



import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.nonactivity.SaveSharedPreference;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequestMethods;

import static com.brymian.boppo.bryant.pictures.ProfilePicturesActivity2.*;

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
        if(getImageNumber() == 0){
            Bitmap bitmap =((BitmapDrawable) ivProfilePicture1.getDrawable()).getBitmap();
            ivProfilePictureViewImage.setImageBitmap(bitmap);
        }else if (getImageNumber() == 1){
            Bitmap bitmap =((BitmapDrawable) ivProfilePicture2.getDrawable()).getBitmap();
            ivProfilePictureViewImage.setImageBitmap(bitmap);
        }else if(getImageNumber() == 2){
            Bitmap bitmap =((BitmapDrawable) ivProfilePicture3.getDrawable()).getBitmap();
            ivProfilePictureViewImage.setImageBitmap(bitmap);
        }else if(getImageNumber() == 3){
            Bitmap bitmap =((BitmapDrawable) ivProfilePicture4.getDrawable()).getBitmap();
            ivProfilePictureViewImage.setImageBitmap(bitmap);
        }


        ivDelete = (ImageView) rootView.findViewById(R.id.ivDelete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ServerRequestMethods(getActivity()).deleteImage(SaveSharedPreference.getUserUID(getActivity()), getImageSequence(getImageNumber()), new StringCallback() {
                    @Override
                    public void done(String string) {
                        if(string.equals("User image deleted successfully.")){
                            getFragmentManager().popBackStack();
                            getActivity().recreate();
                        }
                    }
                });
            }
        });
        return rootView;
    }
}
