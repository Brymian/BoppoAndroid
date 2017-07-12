package com.brymian.boppo.bryant.logIn;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.cropImage.CropImageActivity;
import com.brymian.boppo.bryant.main.MainActivity;
import com.brymian.boppo.bryant.nonactivity.SaveSharedPreference;
import com.brymian.boppo.damian.nonactivity.DialogMessage;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.StringCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.Callback.VoidCallback;
import com.brymian.boppo.damian.nonactivity.ServerRequest.UserImageRequest;
import com.brymian.boppo.damian.nonactivity.ServerRequestMethods;
import com.brymian.boppo.damian.nonactivity.UserDataLocal;
import com.brymian.boppo.damian.objects.User;

import static android.app.Activity.RESULT_OK;


public class LoginSignUpProfilePicture extends Fragment implements View.OnClickListener{
    Toolbar toolbar;
    TextView tvHeader;
    FloatingActionButton fabDone;
    LinearLayout llUploadButtons;
    ImageView ivUserProfileImage;
    ImageButton ibCamera, ibGallery, ibDelete;
    ProgressBar pbDelete;
    RelativeLayout rlDelete;
    private int GALLERY_CODE = 1, CAMERA_CODE = 2;
    private String userImageSequence;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_sign_up_profile_picture, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(R.string.Profile_picture);

        tvHeader = (TextView) view.findViewById(R.id.tvHeader);
        tvHeader.setText(R.string.Upload_a_profile_picture);

        llUploadButtons = (LinearLayout) view.findViewById(R.id.llUploadButtons);

        ivUserProfileImage = (ImageView) view.findViewById(R.id.ivUserProfileImage);

        ibCamera = (ImageButton) view.findViewById(R.id.ibCamera);
        ibCamera.setOnClickListener(this);

        ibGallery = (ImageButton) view.findViewById(R.id.ibGallery);
        ibGallery.setOnClickListener(this);

        rlDelete = (RelativeLayout) view.findViewById(R.id.rlDelete);
        ibDelete = (ImageButton) view.findViewById(R.id.ibDelete);
        ibDelete.setOnClickListener(this);

        pbDelete = (ProgressBar) view.findViewById(R.id.pbDelete);

        fabDone = (FloatingActionButton) view.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibCamera:
                startActivityForResult(new Intent(getActivity(), CropImageActivity.class).putExtra("from", "profileCamera"), CAMERA_CODE);
                break;

            case R.id.ibGallery:
                startActivityForResult(new Intent(getActivity(), CropImageActivity.class).putExtra("from", "profileGallery"), GALLERY_CODE);
                break;

            case R.id.ibDelete:
                //ibDelete.setImageResource(0);
                pbDelete.setVisibility(View.VISIBLE);
                new ServerRequestMethods(getActivity()).deleteImage(SaveSharedPreference.getUserUID(getActivity()), getUserImageSequence(), new StringCallback() {
                    @Override
                    public void done(String string) {
                        if (string.contains("User image deleted successfully.")){
                            ivUserProfileImage.setImageResource(0);
                            rlDelete.setVisibility(View.GONE);
                            llUploadButtons.setVisibility(View.VISIBLE);
                            tvHeader.setText(R.string.Photo_deleted);
                        }
                        else {
                            ibDelete.setImageResource(R.mipmap.ic_delete_white_24dp);
                            pbDelete.setVisibility(View.GONE);
                        }
                    }
                });
                break;

            case R.id.fabDone:
                User user = new User();
                user.initUserNormal(SaveSharedPreference.getUsername(getActivity()), SaveSharedPreference.getUserPassword(getActivity()));
                new ServerRequestMethods(getActivity()).authUserNormal(user, new VoidCallback() {
                    @Override
                    public void done(Void aVoid) {
                        UserDataLocal udl = new UserDataLocal(getActivity());
                        if (!udl.getLoggedStatus()) {
                            DialogMessage.showErrorLoggedIn(getActivity());
                        }
                        else {
                            User user = udl.getUserData();
                            udl.setLoggedStatus(true);
                            udl.setUserData(user);

                            SaveSharedPreference.setUsername(getActivity(), user.getUsername());
                            SaveSharedPreference.setUserPassword(getActivity(), user.getPassword());
                            SaveSharedPreference.setUserUID(getActivity(), user.getUid());
                            SaveSharedPreference.setUserFirstName(getActivity(), user.getFirstName());
                            SaveSharedPreference.setUserLastName(getActivity(), user.getLastName());
                            SaveSharedPreference.setEmail(getActivity(), user.getEmail());
                            SaveSharedPreference.setUserPhoneNumber(getActivity(), user.getPhone());

                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE){
            if(resultCode == RESULT_OK) {
                if (data != null) {
                    new UserImageRequest(getActivity()).getImagesByUid(SaveSharedPreference.getUserUID(getActivity()), true, new StringCallback() {
                        @Override
                        public void done(String string) {
                            Log.e("json", string);
                            try{
                                tvHeader.setText(R.string.Profile_photo_uploaded);
                                llUploadButtons.setVisibility(View.GONE);
                                rlDelete.setVisibility(View.VISIBLE);
                                ibDelete.setImageResource(R.mipmap.ic_delete_white_24dp);
                                pbDelete.setVisibility(View.GONE);
                                JSONObject jsonObject = new JSONObject(string);
                                String images = jsonObject.getString("images");
                                JSONArray imagesArr = new JSONArray(images);
                                JSONObject imagesObj = imagesArr.getJSONObject(0);
                                String userImagePath = imagesObj.getString("userImagePath");
                                String userImageSequence = imagesObj.getString("userImageSequence");
                                setUserImageSequence(userImageSequence);
                                Picasso.with(getActivity()).load(userImagePath).into(ivUserProfileImage);
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        }
        else if (requestCode == CAMERA_CODE){
            if (resultCode == RESULT_OK){
                Log.e("camera", "works");
            }
        }
    }

    private void setUserImageSequence(String userImageSequence){
        this.userImageSequence = userImageSequence;
    }

    private int getUserImageSequence(){
        return Integer.valueOf(userImageSequence);
    }
}
