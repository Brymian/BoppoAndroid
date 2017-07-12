package com.brymian.boppo.bryant.map;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import com.brymian.boppo.R;
import com.brymian.boppo.bryant.nonactivity.SaveSharedPreference;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    Toolbar mToolbar;
    int uid;
    ArrayList<String> imageListPath = new ArrayList<>();
    ArrayList<Double> longitudeImageArrayList = new ArrayList<>();
    ArrayList<Double> latitudeImageArrayList = new ArrayList<>();
    ArrayList<String> privacyImageArrayList = new ArrayList<>();
    ArrayList<String> imagePurposeArrayList = new ArrayList<>();
    public static String imagePath;

    private boolean isClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.map_activity);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        mToolbar.bringToFront();
        if(profile != null){
            switch (profile) {
                case "logged in user":
                    mToolbar.setTitle(R.string.My_Map);
                    setUID(SaveSharedPreference.getUserUID(this));
                    getUserImages(SaveSharedPreference.getUserUID(this));
                    break;

                case "all":
                    mToolbar.setTitle(R.string.Explore);
                    getAllImages();
                    break;

                default:
                    mToolbar.setTitle(profile + "'s Map");
                    getUserImages(uid);
                    setUID(uid);
                    break;
            }
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onBackPressed() {
        if (isClicked){
            getFragmentManager().popBackStack();
            isClicked = false;
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.hideInfoWindow();
        return false;
    }

    private void getUserImages(int uid){
        /** BRYANT UPDATE THIS **/
        /*
        new UserImageRequest(this).getImagesByUidAndPurpose(uid, "Regular", null, new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                if (imageList.size() != 0) {
                    for (int i = 0; i < imageList.size(); i++) {
                        System.out.println("Path: " + imageList.get(i).userImagePath);
                        imageListPath.add(i, imageList.get(i).userImagePath);
                        longitudeImageArrayList.add(i, imageList.get(i).userImageGpsLongitude);
                        latitudeImageArrayList.add(i, imageList.get(i).userImageGpsLatitude);
                        privacyImageArrayList.add(i, imageList.get(i).userImagePrivacyLabel);
                    }
                } else {
                    Toast.makeText(MapActivity.this, "User has no images uploaded", Toast.LENGTH_SHORT).show();
                }

                try{
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
                                marker.hideInfoWindow();
                                viewImage(marker.getTitle());
                                return false;
                            }
                        });
                        mMap.getUiSettings().setMapToolbarEnabled(false); //disables the bottom right buttons that appear when you click on a marker
                    }
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        });
        */
    }

    private void viewImage(String imageListPath){
        setImagePath(imageListPath);
        isClicked = true;
        MapViewImage mapViewImage = new MapViewImage();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map_activity, mapViewImage);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setImagePath(String imagePath){
        MapActivity.imagePath = imagePath;
    }

    public static String getImagePath(){
        return imagePath;
    }

    private void getAllImages(){
        /** BRYANT UPDATE THIS **/
        /*
        new UserImageRequest(this).getImagesByPrivacyAndPurpose("Public", "Regular", null, new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                try {
                    if (imageList.size() != 0) {
                        for (Image image : imageList) {
                            Log.e("ExploreTabFragment", "image path: " + image.userImagePath);
                            imageListPath.add(image.userImagePath);
                            latitudeImageArrayList.add(image.userImageGpsLatitude);
                            longitudeImageArrayList.add(image.userImageGpsLongitude);
                        }

                        for (int i = 0; i < imageListPath.size(); i++) {
                            mMap.addMarker(
                                    new MarkerOptions()
                                            .position(new LatLng(latitudeImageArrayList.get(i), longitudeImageArrayList.get(i)))
                                            .title(imageListPath.get(i))
                            );
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    Toast.makeText(MapActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                            });
                        }
                    }
                }
                catch (NullPointerException npe){
                    npe.printStackTrace();
                }
            }
        });
        */
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.getUiSettings().setRotateGesturesEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                //
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        // Check for permissions and acquire if necessary

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
        //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        mMap.getUiSettings().setMapToolbarEnabled(false); //disables the bottom right buttons that appear when you click on a marker

    }

    private void setUID(int uid){
        this.uid = uid;
    }

    private int getUID(){
        return uid;
    }
}
