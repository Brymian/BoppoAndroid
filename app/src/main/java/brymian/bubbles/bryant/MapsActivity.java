package brymian.bubbles.bryant;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.damian.nonactivity.Image;
import brymian.bubbles.damian.nonactivity.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequest;

public class MapsActivity extends FragmentActivity implements View.OnClickListener
        {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    ImageButton bCamera, bMenu, bSearch, bLeftButton;
    TextView tvTitle;
    double[] longitudeArray = new double[1];
    double[] latitudeArray = new double[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();


        tvTitle = (TextView) findViewById(R.id.tvTitle);
        bCamera = (ImageButton) findViewById(R.id.bCamera);
        bSearch =(ImageButton) findViewById(R.id.bSearch);
        bMenu = (ImageButton) findViewById(R.id.bMenu);
        bLeftButton = (ImageButton) findViewById(R.id.bLeftButton);


        //Making sure if there are any putExtras() coming in from ProfileActivity
        String firstLastName, username;
        int UID;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                firstLastName = null;
                username = null;
                UID = 0;
            } else {
                firstLastName = extras.getString("firstLastName");
                username = extras.getString("username");
                UID = extras.getInt("uid");
            }
        } else {
            firstLastName = (String) savedInstanceState.getSerializable("firstLastName");
            username = (String) savedInstanceState.getSerializable("username");
            UID = savedInstanceState.getInt("uid");
        }

        //Checking for output
        System.out.println("THIS IS FROM MAPSACTIVITY (firstLastName): " + firstLastName);
        System.out.println("THIS IS FROM MAPSACTIVITY (uid}: " + UID);
        System.out.println("THIS IS FROM MAPSACTIVITY (username): " + username);
        //--------------------------------------------------------

        setButtons(firstLastName, username, UID);

        //Setting onClickListeners for all buttons
        bLeftButton.setOnClickListener(this);
        bCamera.setOnClickListener(this);
        bSearch.setOnClickListener(this);
        bMenu.setOnClickListener(this);
    }

    void setButtons(String firstLastName, String username, int UID){
        //Making Drawables
        Drawable cameraDrawable = getResources().getDrawable(R.mipmap.camera_nopadding);
        Drawable searchDrawable = getResources().getDrawable(R.mipmap.search_nopadding);
        Drawable playDrawable = getResources().getDrawable(R.mipmap.play_nopadding);

        switch (firstLastName){
            case "Logged in user.":
                tvTitle.setText("Your Map");
                bCamera.setImageDrawable(cameraDrawable);
                bSearch.setImageDrawable(searchDrawable);
                bLeftButton.setImageDrawable(playDrawable);
                break;
            case "Everyone.":
                tvTitle.setText("Visit Anywhere!");
                bCamera.setImageDrawable(cameraDrawable);
                bSearch.setImageDrawable(searchDrawable);
                bLeftButton.setImageDrawable(playDrawable);
                break;
            default:
                tvTitle.setText(username + "'s Map");
                bCamera.setImageDrawable(cameraDrawable);
                bSearch.setImageDrawable(searchDrawable);
                bLeftButton.setImageDrawable(playDrawable);
                break;

        }
    }


    //----------------BUTTONS-----------------------------

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bMenu:
                Intent menuIntent = new Intent(this, MenuActivity.class);
                startActivity(menuIntent);
                break;

            case R.id.bLeftButton:
                Intent friendsIntent = new Intent(this, FriendsActivity.class);
                startActivity(friendsIntent);
                break;
            case R.id.bCamera:
                Intent cameraIntent = new Intent(this, CameraActivity.class);
                cameraIntent.putExtra("latitude", getLatitudeArray());
                cameraIntent.putExtra("longitude", getLongitudeArray());
                startActivity(cameraIntent);
                break;
            case R.id.bSearch:
                Intent filterIntent = new Intent(this, FilterActivity.class);
                startActivity(filterIntent);
                break;

        }
    }

    //----------------END OF BUTTONS-------------------------
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    //-----------------MAPS METHODS-------------------------------
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

    private void setMarkers(){
        new ServerRequest(this).getImages(1, "Regular", new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                System.out.println(imageList.get(1));//this needs to be changed so i only get back the Lat and Long
            }
        });
    }

    private void setUpMap() {
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
            setLatitudeArray(latitude);
            longitude = myLocation.getLongitude();
            setLongitudeArray(longitude);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "Map Unavailable.", Toast.LENGTH_SHORT).show();
        }
        // Get longitude of the current location

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.bubbles_no_padding)));
                //.title("You are here!")
                //.snippet("Consider yourself located"));
    }

    public void setLatitudeArray(double l){
        latitudeArray[0] = l;
    }
    public double getLatitudeArray(){
        return latitudeArray[0];
    }
    public void setLongitudeArray(double l){
        longitudeArray[0] = l;
    }
    public double getLongitudeArray(){
        return longitudeArray[0];
    }
}
