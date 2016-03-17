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

import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.Tabs.SearchUsers;
import brymian.bubbles.damian.nonactivity.Image;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.ImageListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;

public class MapsActivity extends FragmentActivity implements View.OnClickListener
        {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    ImageButton bCamera, bMenu, bRight, bLeftButton;
    TextView tvTitle;
    double[] longitudeCurrent = new double[1];
    double[] latitudeCurrent = new double[1];
    String[] firstLastNameArray = new String[1];
    int[] uidArray = new int[1];


    ArrayList<Double> longitudeImageArrayList = new ArrayList<>();
    ArrayList<Double> latitudeImageArrayList = new ArrayList<>();
    ArrayList<String> privacyImageArrayList = new ArrayList<>();
    ArrayList<String> imagePurposeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();


        tvTitle = (TextView) findViewById(R.id.tvTitle);
        bCamera = (ImageButton) findViewById(R.id.bCamera);
        bRight =(ImageButton) findViewById(R.id.bRight);
        bMenu = (ImageButton) findViewById(R.id.bMenu);
        bLeftButton = (ImageButton) findViewById(R.id.bLeftButton);


        //Making sure if there are any putExtras() coming in from ProfileActivityOLD
        String firstLastName, username;
        int uid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                firstLastName = null;
                username = null;
                uid = 0;
            } else {
                firstLastName = extras.getString("firstLastName");
                username = extras.getString("username");
                uid = extras.getInt("uid");
            }
        } else {
            firstLastName = (String) savedInstanceState.getSerializable("firstLastName");
            username = (String) savedInstanceState.getSerializable("username");
            uid = savedInstanceState.getInt("uid");
        }

        //Checking for output
        System.out.println("THIS IS FROM MAPSACTIVITY (firstLastName): " + firstLastName);
        System.out.println("THIS IS FROM MAPSACTIVITY (uid}: " + uid);
        System.out.println("THIS IS FROM MAPSACTIVITY (username): " + username);
        //--------------------------------------------------------

        setButtons(firstLastName, username, uid);
        setUID(uid);
        setFirstLastName(firstLastName);

        //Setting onClickListeners for all buttons
        bLeftButton.setOnClickListener(this);
        bCamera.setOnClickListener(this);
        bRight.setOnClickListener(this);
        bMenu.setOnClickListener(this);

        setArrays();
    }

    void setButtons(String firstLastName, String username, int uid){
        //Making Drawables
        Drawable cameraDrawable = getResources().getDrawable(R.mipmap.camera_nopadding);
        Drawable searchDrawable = getResources().getDrawable(R.mipmap.search_nopadding);
        Drawable playDrawable = getResources().getDrawable(R.mipmap.play_nopadding);

        try {
            switch (firstLastName) {
                case "Logged in user.":
                    tvTitle.setText("Your Map");
                    bCamera.setImageDrawable(cameraDrawable);
                    bRight.setImageDrawable(searchDrawable);
                    bLeftButton.setImageDrawable(playDrawable);
                    break;
                case "Everyone.":
                    tvTitle.setText("Visit Anywhere!");
                    bCamera.setImageDrawable(cameraDrawable);
                    bRight.setImageDrawable(searchDrawable);
                    bLeftButton.setImageDrawable(playDrawable);
                    break;
                default:
                    tvTitle.setText(firstLastName + "'s Map");
                    bCamera.setImageDrawable(cameraDrawable);
                    bRight.setImageDrawable(searchDrawable);
                    bLeftButton.setImageDrawable(playDrawable);
                    break;

            }
        }
        catch (NullPointerException npe){
            npe.printStackTrace();
        }
    }


    //----------------BUTTONS-----------------------------

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bMenu:
                //Intent menuIntent = new Intent(this, MenuActivity.class);
                //startActivity(menuIntent);
                break;

            case R.id.bLeftButton:
                Intent friendsIntent = new Intent(this, SearchUsers.class);
                startActivity(friendsIntent);
                break;
            case R.id.bCamera:
                //Intent cameraIntent = new Intent(this, CameraActivityOLD.class);
                //cameraIntent.putExtra("latitude", getLatitudeCurrent());
                //cameraIntent.putExtra("longitude", getLongitudeCurrent());
                //startActivity(cameraIntent);
                break;
            case R.id.bRight:
                Intent imageDisplay = new Intent(this, ImagesDisplay.class);
                imageDisplay.putExtra("uid", getUID());
                startActivity(imageDisplay);
                //startActivity(new Intent(this, ImagesDisplay.class).putExtra("uid", getUID()));
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

    private void setArrays(){
        new ServerRequestMethods(this).getImages(getUID(), "Regular", new ImageListCallback() {
            @Override
            public void done(List<Image> imageList) {
                try{
                    for(int i = 0; i < imageList.size(); i++) {
                        String getPath = imageList.get(i).getPath();
                        double imageLatitude = imageList.get(i).getUserImageGpsLatitude();
                        double imageLongitude = imageList.get(i).getUserImageGpsLongitude();
                        String imagePrivacyLabel = imageList.get(i).getUserImagePrivacyLabel();
                        String imagePurpose = imageList.get(i).getUserImagePurposeLabel();

                        longitudeImageArrayList.add(i, imageLongitude);
                        latitudeImageArrayList.add(i, imageLatitude);
                        privacyImageArrayList.add(i, imagePrivacyLabel);
                        imagePurposeArrayList.add(i, imagePurpose);

                        System.out.println("THIS IS FROM imageList.get(" + i + ").getPath(): " + getPath);
                        System.out.println("THIS IS FROM imageList.get(" + i + ").getLatitude(): " + imageLatitude);
                        System.out.println("THIS IS FROM imageList.get(" + i + ").getLongitude():" + imageLongitude);
                        System.out.println("THIS IS FROM imageList.get(" + i + ").getUserImagePrivacyLabel():" + imagePrivacyLabel);
                        System.out.println("THIS IS FROM imageList.get(" + i + ").getUserImagePurposeLabel():" + imagePurpose);
                    }
                }
                catch(NullPointerException npe){
                    npe.printStackTrace();
                    System.out.println("THIS IS FROM setMarkers(): server seems down possibly NullPointerException.");
                }
                catch(IndexOutOfBoundsException ioob){
                    ioob.printStackTrace();
                    System.out.println("THIS IS FROM setMarkers(): there seems to be nothing in the table IndexOutOfBoundsException");
                }

                for(int i = 0; i < latitudeImageArrayList.size(); i++){
                    System.out.println("THIS IS FROM setUpMap for loop: " + getLongitudeImage(i) + ", " + getLatitudeImage(i));
                    mMap.addMarker((new MarkerOptions()
                            .position(new LatLng(getLatitudeImage(i), getLongitudeImage(i)))
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.bubbles_no_padding))));
                    //mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) MapsActivity.this);

                }

            }
        });
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
            setLatitudeCurrent(latitude);
            System.out.println("THIS IS FROM setUpMap(): setLatitudeArrayCurrent() is " + getLatitudeCurrent());
            longitude = myLocation.getLongitude();
            setLongitudeCurrent(longitude);
            System.out.println("THIS IS FROM setUpMap(): setLongitudeArrayCurrent() is + " + getLongitudeCurrent());
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

        //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));


                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.bubbles_no_padding)));
                //.title("You are here!")
                //.snippet("Consider yourself located"));
    }

    public void setLatitudeCurrent(double l){
        latitudeCurrent[0] = l;
    }
    public void setLongitudeCurrent(double l) {
        longitudeCurrent[0] = l;
    }
    public void setUID(int uid){
        uidArray[0] = uid;
    }
    public void setFirstLastName(String firstLastName){
        firstLastNameArray[0] = firstLastName;
    }
    public double getLatitudeCurrent(){
        return latitudeCurrent[0];
    }
    public double getLongitudeCurrent(){
        return longitudeCurrent[0];
    }
    public double getLongitudeImage(int i){
        return longitudeImageArrayList.get(i);
    }
    public double getLatitudeImage(int i){
        return latitudeImageArrayList.get(i);
    }
    public int getUID(){
        return uidArray[0];
    }
    public String getFirstLastName(){
        return firstLastNameArray[0];
    }
    public String getPrivacy(int i){
        return privacyImageArrayList.get(i);
    }
}
