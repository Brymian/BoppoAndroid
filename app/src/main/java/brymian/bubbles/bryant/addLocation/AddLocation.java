package brymian.bubbles.bryant.addLocation;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import brymian.bubbles.R;

public class AddLocation extends AppCompatActivity {
    double lat, lng;
    EditText etSearchLocation;
    RecyclerView rvLocationResults;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Add Location");
        rvLocationResults = (RecyclerView) findViewById(R.id.rvLocationResults);
        setLocationByRadius();
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                setLat(location.getLatitude());
                setLng(location.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        etSearchLocation = (EditText) findViewById(R.id.etSearchLocation);
        etSearchLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = "query=" + etSearchLocation.getText().toString();
                String apiKey = "key=" + getResources().getString(R.string.google_maps_key);
                String location = "location=" + getLat() + "," + getLng();
                String radius = "radius=300";
                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?" + query + "&"+ location + "&" + radius + "&" + apiKey;
                new GetPlaces(url).execute();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setLocationByRadius(){
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLastKnownLocation = locationManager.getLastKnownLocation(provider);

        double latitude = myLastKnownLocation.getLatitude();
        double longitude = myLastKnownLocation.getLongitude();
        String apiKey = getResources().getString(R.string.google_maps_key);
        String radius = "radius=200";
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&" + radius + "&key=" + apiKey;
        new GetPlaces(url).execute();
    }

    private void parsePlaces(String string){
        try {
            JSONObject jsonObject = new JSONObject(string);
            String results = jsonObject.getString("results");
            JSONArray jsonArray = new JSONArray(results);
            List<String> locationAddress = new ArrayList<>();
            List<String> locationName = new ArrayList<>();
            List<Double> locationLat = new ArrayList<>();
            List<Double> locationLng = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject resultObj = jsonArray.getJSONObject(i);
                String name = resultObj.getString("name");
                String address;
                if (resultObj.has("vicinity")){
                    address = resultObj.getString("vicinity");
                }
                else if (resultObj.has("formatted_address")){
                    address = resultObj.getString("formatted_address");
                }
                else {
                    address = "";
                }
                String geometry = resultObj.getString("geometry");
                JSONObject geometryObj = new JSONObject(geometry);
                String location = geometryObj.getString("location");
                JSONObject locationObj = new JSONObject(location);
                String lat = locationObj.getString("lat");
                String lng = locationObj.getString("lng");

                locationLat.add(Double.valueOf(lat));
                locationLng.add(Double.valueOf(lng));
                locationName.add(name);
                locationAddress.add(address);
            }

            adapter = new AddLocationRecyclerAdapter(this, locationName, locationAddress, locationLat, locationLng);
            layoutManager = new LinearLayoutManager(this);
            rvLocationResults.setLayoutManager(layoutManager);
            rvLocationResults.setAdapter(adapter);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void setLat(double lat){
        this.lat = lat;
    }

    private double getLat(){
        return this.lat;
    }

    private void setLng(double lng){
        this.lng = lng;
    }

    private double getLng(){
        return this.lng;
    }

    private class GetPlaces extends AsyncTask<Void, Void, String> {
        String url;
        private GetPlaces(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... params) {
            StringBuilder placesBuilder = new StringBuilder();
            try {
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection)requestUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader;
                    InputStream inputStream = connection.getInputStream();
                    if (inputStream == null) {
                        return "";
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        placesBuilder.append(line).append("\n");
                    }
                    if (placesBuilder.length() == 0) {
                        return "";
                    }
                    Log.e("test", placesBuilder.toString());
                }
                else {
                    Log.i("test", "Unsuccessful HTTP Response Code: " + responseCode);
                }
            }
            catch (IOException | NetworkOnMainThreadException e ){
                e.printStackTrace();
            }
            return placesBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parsePlaces(s);
        }
    }
}
