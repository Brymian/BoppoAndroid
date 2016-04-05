package brymian.bubbles.bryant.profile;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.objects.Image;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    Toolbar mToolbar;
    int uid;
    ArrayList<String> imageListPath = new ArrayList<>();
    ArrayList<Double> longitudeImageArrayList = new ArrayList<>();
    ArrayList<Double> latitudeImageArrayList = new ArrayList<>();
    ArrayList<String> privacyImageArrayList = new ArrayList<>();
    ArrayList<String> imagePurposeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        /*--------------------------------Checking for putExtras()--------------------------------*/
        String profile;
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                profile = null;
                uid = 0;
            }
            else {
                profile = extras.getString("profile");
                uid = extras.getInt("uid");
            }
        }
        else {
            profile= savedInstanceState.getString("profile");
            uid = savedInstanceState.getInt("uid");
        }
        /*----------------------------------------------------------------------------------------*/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if(profile != null){
            if(profile.equals("logged in user")){
                mToolbar.setTitle(R.string.My_Map);
                setUID(SaveSharedPreference.getUserUID(this));
            }
            else{
                mToolbar.setTitle(profile + "'s Map");
                setUID(uid);
            }
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getImages();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void getImages(){
        new ServerRequestMethods(this).getImagesByUid(getUID(), "Regular", new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                if (imageList.size() != 0) {
                    for (int i = 0; i < imageList.size(); i++) {
                        System.out.println("Path: " + imageList.get(i).getPath());
                        imageListPath.add(i, imageList.get(i).getPath());
                        longitudeImageArrayList.add(i, imageList.get(i).getUserImageGpsLongitude());
                        latitudeImageArrayList.add(i, imageList.get(i).getUserImageGpsLatitude());
                        privacyImageArrayList.add(i, imageList.get(i).getUserImagePrivacyLabel());
                    }
                } else {
                    Toast.makeText(MapsActivity.this, "User has no images uploaded", Toast.LENGTH_SHORT).show();
                }

                System.out.println("latitudeImageArrayList.size(): " + latitudeImageArrayList.size());
                for (int i = 0; i < latitudeImageArrayList.size(); i++) {
                    mMap.addMarker(
                            (new MarkerOptions()
                                    .position(new LatLng(latitudeImageArrayList.get(i), longitudeImageArrayList.get(i)))
                                    .title(imageListPath.get(i))
                            )
                    );
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            System.out.println("getTitle(): " + marker.getTitle() + "\t getPosition(): " + marker.getPosition());
                            Toast.makeText(MapsActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();

                            return false;
                        }
                    });
                }
            }
        });
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    void setUpMap() {
        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        double latitude = 0;
        double longitude = 0;

        try {
            // Get latitude of the current location
            latitude = myLocation.getLatitude();
            longitude = myLocation.getLongitude();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        //Add markers here
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
    }

    private void setUID(int uid){
        this.uid = uid;
    }

    private int getUID(){
        return uid;
    }
}