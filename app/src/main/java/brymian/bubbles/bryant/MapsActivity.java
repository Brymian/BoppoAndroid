package brymian.bubbles.bryant;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

import brymian.bubbles.R;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Uri fileUri;
    private static int TAKE_PICTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    //----------------BUTTONS-----------------------------
    public void onClickCamera(View v){
        //takePhoto();
        Intent cameraIntent = new Intent(this, CameraActivity.class);
        startActivity(cameraIntent);
    }

    public void onClickMenu(View view){
        Intent menuIntent = new Intent(this, MenuActivity.class);
        startActivity(menuIntent);
    }

    public void onClickFilter(View view){
        Intent filterIntent = new Intent(this, FilterActivity.class);
        startActivity(filterIntent);
    }

    public void onClickFriends(View view){
        Intent friendsIntent = new Intent(this, FriendsActivity.class);
        startActivity(friendsIntent);
    }
    //----------------END OF BUTTONS-------------------------
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    //---------------------CAMERA------------------------------
    /**
    private void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "picture.jpg");
        fileUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
     super.onActivityResult(requestCode, resultCode, intent);

     if (resultCode == Activity.RESULT_OK) {
     Uri selectedImage = imageUri;
     getContentResolver().notifyChange(selectedImage, null);

     ImageView imageView = (ImageView) findViewById(R.id.image_camera);
     ContentResolver cr = getContentResolver();
     Bitmap bitmap;

     try {
     bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
     imageView.setImageBitmap(bitmap);
     Toast.makeText(CameraActivity.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
     } catch (Exception e) {
     Log.e(logtag, e.toString());
     }
     }
     }
     **/
    //-------------------END OF CAMERA---------------------------

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

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

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
