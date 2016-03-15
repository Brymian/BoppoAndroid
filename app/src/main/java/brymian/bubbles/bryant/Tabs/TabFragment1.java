package brymian.bubbles.bryant.Tabs;

/**
 * Created by Almanza on 3/3/2016.
 */
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.nonactivity.User;
import brymian.bubbles.damian.nonactivity.UserDataLocal;


public class TabFragment1 extends Fragment implements View.OnClickListener{
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    double longitude;
    double latitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_1, container, false);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            try {
                mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
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
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

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
            SaveSharedPreference.setLatitude(getActivity(), latitude);
            System.out.println("THIS IS FROM setUpMap(): setLatitudeArrayCurrent() is " + getLatitudeCurrent());
            longitude = myLocation.getLongitude();
            setLongitudeCurrent(longitude);
            SaveSharedPreference.setLongitude(getActivity(), longitude);
            System.out.println("THIS IS FROM setUpMap(): setLongitudeArrayCurrent() is + " + getLongitudeCurrent());
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Map Unavailable.", Toast.LENGTH_SHORT).show();
        }
        System.out.println("THIS IS FROM setUpMap(): latitude is " + latitude);
        System.out.println("THIS IS FROM setUpMap(): longitude is " + longitude);
        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5));

        //Add markers here

        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));


        //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.bubbles_no_padding)));
        //.title("You are here!")
        //.snippet("Consider yourself located"));
    }
    public void setLatitudeCurrent(double latitude){
        this.latitude = latitude;
    }
    public void setLongitudeCurrent(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitudeCurrent(){
        return latitude;
    }
    public double getLongitudeCurrent(){
        return longitude;
    }


}