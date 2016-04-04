package com.example.echo.emergencyapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class emergency_map extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = emergency_map.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private double longt;
    private double lat;

    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    /* Position */
    private static final int MINIMUM_TIME = 10000;  // 10s
    private static final int MINIMUM_DISTANCE = 50; // 50m

    /* GPS */
    private String mProviderName;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            //get the altitude and latitude of the device each time there is a location update
            @Override
            public void onLocationChanged(Location location) {
                lat= location.getLatitude();
                longt= location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };

        //check to see if we have the needed permissions. If not then we request them by the user.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }
        else {
            configureButton();
        }
    }

    //if we have the necessary permissions then we call configureButton() function which initiates a location update
    @Override
    public void onRequestPermissionsResult(int requestCode, String [] permissions, int[] grantResult) {
        switch (requestCode) {
            case 10:
                if (grantResult.length>0 && grantResult[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    private void configureButton() {
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }

    //each time a new location is received then we display it on the map with the caption "I am here!"
    //the previous location of the user is removed from the map and we zoom into the new location
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");

        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }

    //every time the activity is launched we create several markers on the map with apropriate tags
    //these markers signify evacuation points for AUBG students
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker
        LatLng Skapto_evac_1 = new LatLng(42.014703, 23.096305);
        mMap.addMarker(new MarkerOptions().position(Skapto_evac_1).title("Skaptopara Evacuation Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Skapto_evac_1));

        LatLng Skapto_evac_2 = new LatLng(42.012412, 23.094974);
        mMap.addMarker(new MarkerOptions().position(Skapto_evac_2).title("Skaptopara Evacuation Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Skapto_evac_2));

        LatLng Skapto_evac_3 = new LatLng(42.015164, 23.096740);
        mMap.addMarker(new MarkerOptions().position(Skapto_evac_3).title("Skaptopara Evacuation Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Skapto_evac_3));

        LatLng Main_evac_1 = new LatLng(42.021310, 23.095546);
        mMap.addMarker(new MarkerOptions().position(Main_evac_1).title("Main Building Evacuation Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Main_evac_1));

        LatLng Main_evac_2 = new LatLng(42.020841, 23.095056);
        mMap.addMarker(new MarkerOptions().position(Main_evac_2).title("Main Building Evacuation Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Main_evac_2));
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                   MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }

    //fired of when we lose connection
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    //called when the activity runs into a problem with the connection
    //it will try to resolve the issue and if it cannot will display helpfull info to the user
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    //each time the activty is resumed we request another update for the user's location
    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }

    //when the activity is paused we release he google api
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    //updates the user's location on the map everytime that location changes
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}
