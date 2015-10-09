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
import com.google.android.gms.maps.model.MarkerOptions;

import brymian.bubbles.R;

public class MapsActivity extends FragmentActivity implements View.OnClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    ImageButton bCamera, bMenu, bSearch, bLeftButton;
    TextView tvTitle;
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

        //Making Drawables
        Drawable cameraDrawable = getResources().getDrawable(R.mipmap.camera_nopadding);
        Drawable searchDrawable = getResources().getDrawable(R.mipmap.search_nopadding);
        Drawable playDrawable = getResources().getDrawable(R.mipmap.play_nopadding);

        //Making sure if there are any putExtras() coming in from ProfileActivity
        String userWho, userName;
        int userID;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userWho = null;
                userName = null;
                userID = 0;
            } else {
                userWho = extras.getString("userWho");
                userName = extras.getString("userName");
                userID = extras.getInt("userID");
            }
        } else {
            userWho = (String) savedInstanceState.getSerializable("userWho");
            userName = (String) savedInstanceState.getSerializable("userName");
            userID = (Integer) savedInstanceState.getSerializable("userID");
        }

        //Checking for output
        System.out.println("THIS IS FROM MAPSACTIVITY (userWho): " + userWho);
        System.out.println("THIS IS FROM MAPSACTIVITY (userID}: " + userID);
        System.out.println("THIS IS FROM MAPSACTIVITY (userName): " + userName);
        //--------------------------------------------------------

        if(userWho.equals("Logged in user.")){
            tvTitle.setText("Your Map");
            bCamera.setImageDrawable(cameraDrawable);
            bSearch.setImageDrawable(searchDrawable);
            bLeftButton.setImageDrawable(playDrawable);
        }
        else if(userWho.equals("Everyone.")){
            tvTitle.setText("Visit Anywhere!");
            bCamera.setImageDrawable(cameraDrawable);
            bSearch.setImageDrawable(searchDrawable);
            bLeftButton.setImageDrawable(playDrawable);
        }
        else if(userWho == null){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        else{
            tvTitle.setText(userName + "'s Map");
            bCamera.setImageDrawable(cameraDrawable);
            bSearch.setImageDrawable(searchDrawable);
            bLeftButton.setImageDrawable(playDrawable);
        }

        //Setting onClickListeners for all buttons
        bLeftButton.setOnClickListener(this);
        bCamera.setOnClickListener(this);
        bSearch.setOnClickListener(this);
        bMenu.setOnClickListener(this);
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
            longitude = myLocation.getLongitude();
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
}
