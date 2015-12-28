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
    double[] longitudeArrayCurrent = new double[1];
    double[] latitudeArrayCurrent = new double[1];
    double[] longitudeArrayServer;
    double[] latitudeArrayServer;
    String[] firstLastNameArray = new String[1];
    String[] purposeArray = new String[1];
    String[] privacyArray = new String[1];
    int[] uidArray = new int[1];

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
        setUID(UID);
        setFirstLastName(firstLastName);

        //Setting onClickListeners for all buttons
        bLeftButton.setOnClickListener(this);
        bCamera.setOnClickListener(this);
        bSearch.setOnClickListener(this);
        bMenu.setOnClickListener(this);

        setMarkers();
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
                cameraIntent.putExtra("latitude", getLatitudeArrayCurrent());
                cameraIntent.putExtra("longitude", getLongitudeArrayCurrent());
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
                try{
                    String getPath = imageList.get(0).getPath();
                    double getUserImageGPSLatitude = imageList.get(0).getUserImageGpsLatitude();
                    double getUserImageGpsLongitude = imageList.get(0).getUserImageGpsLongitude();
                    String getUserImagePrivacyLabel = imageList.get(0).getUserImagePrivacyLabel();
                    String getUserImagePurposeLabel = imageList.get(0).getUserImagePurposeLabel();

                    setLatitudeArrayImage(getUserImageGPSLatitude);
                    setLongitudeArrayCurrent(getUserImageGpsLongitude);
                    setPrivacy(getUserImagePrivacyLabel);
                    setPurpose(getUserImagePurposeLabel);

                    System.out.println("THIS IS FROM imageList.get(0).getPath(): " + getPath);
                    System.out.println("THIS IS FROM imageList.get(0).getLatitude(): " + getUserImageGPSLatitude);
                    System.out.println("THIS IS FROM imageList.get(0).getLongitude():" + getUserImageGpsLongitude);
                    System.out.println("THIS IS FROM imageList.get(0).getUserImagePrivacyLabel():" + getUserImagePrivacyLabel);
                    System.out.println("THIS IS FROM imageList.get(0).getUserImagePurposeLabel():" + getUserImagePurposeLabel);
                }
                catch(NullPointerException e){
                    e.printStackTrace();
                    System.out.println("THIS IS FROM setMarkers(): server seems down possibly NullPointerException.");
                }
                catch(IndexOutOfBoundsException i){
                    i.printStackTrace();
                    System.out.println("THIS IS FROM setMarkers(): there seems to be nothing in the table IndexOutOfBoundsException");
                }

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
            setLatitudeArrayCurrent(latitude);
            System.out.println("THIS IS FROM setUpMap(): setLatitudeArrayCurrent() is " + getLatitudeArrayCurrent());
            longitude = myLocation.getLongitude();
            setLongitudeArrayCurrent(longitude);
            System.out.println("THIS IS FROM setUpMap(): setLongitudeArrayCurrent() is + " + getLongitudeArrayCurrent());
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, "Map Unavailable.", Toast.LENGTH_SHORT).show();
        }
        System.out.println("THIS IS FROM setUpMap(): latitude is " + latitude);
        System.out.println("THIS IS FROM setUpMap(): longitude is " + longitude);
        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        //Add markers here
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude)));
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.bubbles_no_padding)));
                //.title("You are here!")
                //.snippet("Consider yourself located"));
    }

    public void setLatitudeArrayCurrent(double l){
        latitudeArrayCurrent[0] = l;
    }
    public void setLongitudeArrayCurrent(double l) {
        longitudeArrayCurrent[0] = l;
    }
    public void setLatitudeArrayImage(double l){
        latitudeArrayServer[0] = l;
    }
    public void setLongtitudeArrayImage(double l){
        longitudeArrayServer[0] = l;
    }
    public void setUID(int uid){
        uidArray[0] = uid;
    }
    public void setFirstLastName(String firstLastName){
        firstLastNameArray[0] = firstLastName;
    }
    public void setPrivacy(String p){
        privacyArray[0] = p;
    }
    public void setPurpose(String p){
        purposeArray[0] = p;
    }
    public double getLatitudeArrayCurrent(){
        return latitudeArrayCurrent[0];
    }
    public double getLongitudeArrayCurrent(){
        return longitudeArrayCurrent[0];
    }
    public double getLongitudeArrayImage(){
        return longitudeArrayServer[0];
    }
    public double getLatitudeArrayImage(){
        return latitudeArrayServer[0];
    }
    public int getUID(){
        return uidArray[0];
    }
    public String getFirstLastName(){
        return firstLastNameArray[0];
    }
    public String getPurpose(){
        return purposeArray[0];
    }
    public String getPrivacy(){
        return privacyArray[0];
    }
}
