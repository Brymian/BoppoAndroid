package brymian.bubbles.bryant.episodes;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import brymian.bubbles.R;
import brymian.bubbles.bryant.cropImage.CropImageActivity;

import static android.app.Activity.RESULT_OK;


public class EpisodeCreateUploadImage extends Fragment implements View.OnClickListener{

    private final int CAMERA_CODE = 2;
    private final int GALLERY_CODE = 1;
    private int eid;

    String category, type;

    private ImageView ivEpisodeImage;
    AlertDialog uploadDialog = null;
    AlertDialog mainDialog = null;
    AlertDialog travelDialog = null;
    AlertDialog socialDialog = null;
    AlertDialog sportDialog = null;
    AlertDialog musicDialog = null;
    AlertDialog miscDialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.episode_create_upload_image, container, false);

        final FloatingActionButton fabDone = (FloatingActionButton) view.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(this);

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Add Episode Photo and Type");

        final TextView tvUploadImage = (TextView) view.findViewById(R.id.tvUploadImage);
        tvUploadImage.setOnClickListener(this);

        final TextView tvChooseLogo = (TextView) view.findViewById(R.id.tvChooseLogo);
        tvChooseLogo.setOnClickListener(this);

        ivEpisodeImage = (ImageView) view.findViewById(R.id.ivEpisodeImage);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            eid = bundle.getInt("eid", 0);
            Log.e("ecui", eid + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabDone:
                break;

            case R.id.tvUploadImage:
                uploadImageDialog();
                break;

            case R.id.tvChooseLogo:
                chooseLogoDialog();
                break;

            /* upload image dialog */
            case R.id.tvCamera:
                //startActivityForResult(new Intent(this, CameraActivity.class), CAMERA_CODE);
                break;

            case R.id.tvGallery:
                startActivityForResult(new Intent(getActivity(), CropImageActivity.class).putExtra("from", "episodeCreateGallery"), GALLERY_CODE);
                uploadDialog.dismiss();
                break;
             /* main logo dialog */
            case R.id.tvTravelCategory:
                chooseLogoTravelTypeDialog();
                break;

            case R.id.tvSocialCategory:
                chooseLogoSocialTypeDialog();
                break;

            case R.id.tvSportCategory:
                chooseLogoSportTypeDialog();
                break;

            case R.id.tvMusicCategory:
                chooseLogoMusicTypeDialog();
                break;

            case R.id.tvMiscCategory:
                chooseLogoMiscTypeDialog();
                break;

            /* travel logo dialog */
            case R.id.tvTravel:
                setCategory("Travel");
                setType("Travel");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvVacation:
                setCategory("Travel");
                setType("Vacation");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvHike:
                setCategory("Travel");
                setType("Hike");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvCruise:
                setCategory("Travel");
                setType("Cruise");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvAdventure:
                setCategory("Travel");
                setType("Adventure");
                travelDialog.dismiss();
                mainDialog.dismiss();
                break;

            /* social logo dialog */
            case R.id.tvSocial:
                setCategory("Social");
                setType("Social");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvParty:
                setCategory("Social");
                setType("Party");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvGetTogether:
                setCategory("Social");
                setType("Get-together");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvWedding:
                setCategory("Social");
                setType("Wedding");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvMovie:
                setCategory("Social");
                setType("Movie");
                socialDialog.dismiss();
                mainDialog.dismiss();
                break;

            /* sport logo dialog */
            case R.id.tvSport:
                setCategory("Sport");
                setType("Sport");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvSoccer:
                setCategory("Sport");
                setType("Soccer");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvBasketball:
                setCategory("Sport");
                setType("Basketball");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvFootball:
                setCategory("Sport");
                setType("Football");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvBaseball:
                setCategory("Sport");
                setType("Baseball");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvTennis:
                setCategory("Sport");
                setType("Tennis");
                sportDialog.dismiss();
                mainDialog.dismiss();
                break;

            /* music logo dialog */
            case R.id.tvMusic:
                setCategory("Music");
                setType("Music");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvConcert:
                setCategory("Music");
                setType("Concert");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvFestival:
                setCategory("Music");
                setType("Festival");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvMusical:
                setCategory("Music");
                setType("Musical");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvOpera:
                setCategory("Music");
                setType("Opera");
                musicDialog.dismiss();
                mainDialog.dismiss();
                break;

            /* misc logo dialog */
            case R.id.tvMisc:
                setCategory("Miscellaneous");
                setType("Miscellaneous");
                miscDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvCarnival:
                setCategory("Miscellaneous");
                setType("Carnival");
                miscDialog.dismiss();
                mainDialog.dismiss();
                break;

            case R.id.tvReview:
                setCategory("Miscellaneous");
                setType("Review");
                miscDialog.dismiss();
                mainDialog.dismiss();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE){
            if(resultCode == RESULT_OK) {
                if (data != null) {

                }
            }
        }
    }

    private void uploadImageDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_upload_image_alertdialog, null);

        TextView tvCamera = (TextView) alertLayout.findViewById(R.id.tvCamera);
        TextView tvGallery = (TextView) alertLayout.findViewById(R.id.tvGallery);

        tvCamera.setOnClickListener(this);
        tvGallery.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        uploadDialog = alert.create();
        uploadDialog.setCanceledOnTouchOutside(true);
        uploadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        uploadDialog.show();
    }

    private void chooseLogoDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_alertdialog, null);

        TextView tvTravel = (TextView) alertLayout.findViewById(R.id.tvTravelCategory);
        TextView tvSocial = (TextView) alertLayout.findViewById(R.id.tvSocialCategory);
        TextView tvSport = (TextView) alertLayout.findViewById(R.id.tvSportCategory);
        TextView tvMusic = (TextView) alertLayout.findViewById(R.id.tvMusicCategory);
        TextView tvMisc = (TextView) alertLayout.findViewById(R.id.tvMiscCategory);

        tvTravel.setOnClickListener(this);
        tvSocial.setOnClickListener(this);
        tvSport.setOnClickListener(this);
        tvMusic.setOnClickListener(this);
        tvMisc.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        mainDialog = alert.create();
        mainDialog.setCanceledOnTouchOutside(true);
        mainDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mainDialog.show();
    }

    private void chooseLogoTravelTypeDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_travel_alertdialog, null);

        TextView tvTravel = (TextView) alertLayout.findViewById(R.id.tvTravel);
        TextView tvVacation = (TextView) alertLayout.findViewById(R.id.tvVacation);
        TextView tvHike = (TextView) alertLayout.findViewById(R.id.tvHike);
        TextView tvCruise = (TextView) alertLayout.findViewById(R.id.tvCruise);
        TextView tvAdventure = (TextView) alertLayout.findViewById(R.id.tvAdventure);

        tvTravel.setOnClickListener(this);
        tvVacation.setOnClickListener(this);
        tvHike.setOnClickListener(this);
        tvCruise.setOnClickListener(this);
        tvAdventure.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        travelDialog = alert.create();
        travelDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        travelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        travelDialog.show();
    }

    private void chooseLogoSocialTypeDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_social_alertdialog, null);

        TextView tvSocial = (TextView) alertLayout.findViewById(R.id.tvSocial);
        TextView tvParty = (TextView) alertLayout.findViewById(R.id.tvParty);
        TextView tvGetTogether = (TextView) alertLayout.findViewById(R.id.tvGetTogether);
        TextView tvWedding = (TextView) alertLayout.findViewById(R.id.tvWedding);
        TextView tvMovie = (TextView) alertLayout.findViewById(R.id.tvMovie);

        tvSocial.setOnClickListener(this);
        tvParty.setOnClickListener(this);
        tvGetTogether.setOnClickListener(this);
        tvWedding.setOnClickListener(this);
        tvMovie.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        socialDialog = alert.create();
        socialDialog.setCanceledOnTouchOutside(true);
        socialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        socialDialog.show();
    }

    private void chooseLogoSportTypeDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_sport_alertdialog, null);

        TextView tvSport = (TextView) alertLayout.findViewById(R.id.tvSport);
        TextView tvSoccer = (TextView) alertLayout.findViewById(R.id.tvSoccer);
        TextView tvBasketball = (TextView) alertLayout.findViewById(R.id.tvBasketball);
        TextView tvFootball = (TextView) alertLayout.findViewById(R.id.tvFootball);
        TextView tvBaseball = (TextView) alertLayout.findViewById(R.id.tvBaseball);
        TextView tvTennis = (TextView) alertLayout.findViewById(R.id.tvTennis);

        tvSport.setOnClickListener(this);
        tvSoccer.setOnClickListener(this);
        tvBasketball.setOnClickListener(this);
        tvFootball.setOnClickListener(this);
        tvBaseball.setOnClickListener(this);
        tvTennis.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        sportDialog = alert.create();
        sportDialog.setCanceledOnTouchOutside(true);
        sportDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sportDialog.show();
    }

    private void chooseLogoMusicTypeDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_music_alertdialog, null);

        TextView tvMusic = (TextView) alertLayout.findViewById(R.id.tvMusic);
        TextView tvConcert = (TextView) alertLayout.findViewById(R.id.tvConcert);
        TextView tvFestival = (TextView) alertLayout.findViewById(R.id.tvFestival);
        TextView tvMusical = (TextView) alertLayout.findViewById(R.id.tvMusical);
        TextView tvOpera = (TextView) alertLayout.findViewById(R.id.tvOpera);

        tvMusic.setOnClickListener(this);
        tvConcert.setOnClickListener(this);
        tvFestival.setOnClickListener(this);
        tvMusical.setOnClickListener(this);
        tvOpera.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        musicDialog = alert.create();
        musicDialog.setCanceledOnTouchOutside(true);
        musicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        musicDialog.show();
    }

    private void chooseLogoMiscTypeDialog(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.episode_create_choose_logo_misc_alertdialog, null);

        TextView tvMisc = (TextView) alertLayout.findViewById(R.id.tvMisc);
        TextView tvCarnival = (TextView) alertLayout.findViewById(R.id.tvCarnival);
        TextView tvReview = (TextView) alertLayout.findViewById(R.id.tvReview);

        tvMisc.setOnClickListener(this);
        tvCarnival.setOnClickListener(this);
        tvReview.setOnClickListener(this);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);
        miscDialog = alert.create();
        miscDialog.setCanceledOnTouchOutside(true);
        miscDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        miscDialog.show();
    }

    private void setCategory(String category){
        this.category = category;
    }

    private String getCategory(){
        return this.category;
    }

    private void setType(String type){
        this.type = type;
    }

    private String getType(){
        return this.type;
    }

}
