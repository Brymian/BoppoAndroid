package brymian.bubbles.bryant.episodes;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import brymian.bubbles.R;

import brymian.bubbles.bryant.nonactivity.Misc;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.StringCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest.EventRequest;
import brymian.bubbles.damian.nonactivity.ServerRequest.UserImageRequest;

public class EpisodeEdit extends Fragment implements View.OnClickListener{

    TextView tvUpdateEpisodePhoto, tvUpdateEpisodeType, tvEpisodeTitle, tvStartDate, tvStartTime, tvEndDate, tvEndTime, tvEpisodeLocationAddress, tvEpisodeLocationName;
    Button bDeleteEpisode;
    private ImageView ivEpisodeProfileImage, ivEpisodeType, ivClearLocation;
    private int eid;
    private String episodeImagePath, episodeType, episodeTitle, episodeStartDate, episodeStartTime, episodeEndDate, episodeEndTime,locationName, locationAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.episode_edit, container, false);

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Edit Episode");
        toolbar.setPadding(0, getStatusBarHeight(), 0 ,0);

        ivEpisodeProfileImage = (ImageView) view.findViewById(R.id.ivEpisodeProfileImage);
        ivEpisodeType = (ImageView) view.findViewById(R.id.ivEpisodeType);
        tvUpdateEpisodePhoto = (TextView) view.findViewById(R.id.tvUpdateEpisodePhoto);
        tvUpdateEpisodeType = (TextView) view.findViewById(R.id.tvUpdateEpisodeType);

        tvEpisodeTitle = (TextView) view.findViewById(R.id.tvEpisodeTitle);

        tvStartDate = (TextView) view.findViewById(R.id.tvStartDate);
        tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
        tvEndDate = (TextView) view.findViewById(R.id.tvEndDate);
        tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);

        tvEpisodeLocationName = (TextView) view.findViewById(R.id.tvLocationName);
        tvEpisodeLocationAddress = (TextView) view.findViewById(R.id.tvLocationAddress);
        ivClearLocation = (ImageView) view.findViewById(R.id.ivClearLocation);

        bDeleteEpisode = (Button) view.findViewById(R.id.bDeleteEpisode);
        bDeleteEpisode.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivClearLocation:
                tvEpisodeLocationName.setText("Add Location");
                tvEpisodeLocationAddress.clearComposingText();
                break;
            case R.id.bDeleteEpisode:
                new EventRequest(getActivity()).deleteEvent(eid, new StringCallback() {
                    @Override
                    public void done(String string) {
                        Log.e("delete", string);
                    }
                });
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.episodeOptions);
        item.setVisible(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            eid = bundle.getInt("eid", 0);
            episodeImagePath = bundle.getString("episodeImagePath");
            episodeType = bundle.getString("episodeType");
            episodeTitle = bundle.getString("episodeTitle");
            String episodeStartDateTime = bundle.getString("episodeStartDateTime");
            String episodeEndDateTime = bundle.getString("episodeEndDateTime");
            locationName = bundle.getString("locationName");
            locationAddress = bundle.getString("locationAddress");

            Picasso.with(getActivity()).load(episodeImagePath).fit().centerCrop().into(ivEpisodeProfileImage);
            tvEpisodeTitle.setText(episodeTitle);
            Misc misc = new Misc();
            String[] startDateTimeArr = misc.dateTimeConverterSeparate(episodeStartDateTime);
            episodeStartDate = startDateTimeArr[0];
            episodeStartTime = startDateTimeArr[1];
            tvStartDate.setText(episodeStartDate);
            tvStartTime.setText(episodeStartTime);
            if (!episodeEndDateTime.equals("null")){
                String[] endDateTimeArr = misc.dateTimeConverterSeparate(episodeEndDateTime);
                episodeEndDate = endDateTimeArr[0];
                episodeEndTime = endDateTimeArr[1];
                tvEndDate.setText(episodeEndDate);
                tvEndTime.setText(episodeEndTime);
            }
            else {
                tvEndDate.setText("--- --, ----");
                tvEndTime.setText("--:-- --");
            }
            if (!locationName.equals("null")){
                tvEpisodeLocationName.setText(locationName);
                tvEpisodeLocationAddress.setText(locationAddress);
            }
            else {
                tvEpisodeLocationName.setText("Add Location");
                tvEpisodeLocationAddress.setVisibility(View.GONE);
                ivClearLocation.setVisibility(View.GONE);
            }
        }
    }

    private void getEpisodeProfilePictures(int eid){
        new UserImageRequest(getActivity()).getImagesByEid(eid, true, new StringCallback() {
            @Override
            public void done(String string) {
                try{
                    JSONObject jsonObject = new JSONObject(string);
                    String imagesString = jsonObject.getString("images");
                    JSONArray imagesArray = new JSONArray(imagesString);
                    if (imagesArray.length() > 0){
                        JSONObject imageObj = imagesArray.getJSONObject(0);
                        String imagePath = imageObj.getString("userImagePath");
                        Picasso.with(getActivity()).load(imagePath).fit().centerCrop().into(ivEpisodeProfileImage);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
