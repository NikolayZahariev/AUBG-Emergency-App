package com.example.echo.emergencyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private Button sendMsg;
    private Button btn_logout;
    private String name;
    private String email;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private double loc_lat;
    private double loc_lon;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendMsg = (Button) findViewById(R.id.btn_Emergency);
        btn_logout = (Button) findViewById(R.id.btnLogout);

        session = new SessionManagement(getApplicationContext());

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        name = user.get(SessionManagement.KEY_NAME);

        //used to notify user that his message has been successfully sent with the given information
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View arg0) {
                //Clear the session data
                //This will clear all session data and redirect users to LoginActivity
                session.logoutUser();
            }
        });

        //gets the user location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                loc_lat= location.getLatitude();
                loc_lon= location.getLongitude();
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                       Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_COARSE_LOCATION,
                       Manifest.permission.INTERNET
               }, 10);
                return;
            }
        }
        else {
            configureButton();
        }

        //goes to Get_Help activity when the button "Get Help" is clicked
        View.OnClickListener listnr_get_help=new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, Get_Help.class);
                startActivity(i);
            }
        };
        Button btn_get_help =(Button) findViewById(R.id.btn_Help);
        btn_get_help.setOnClickListener(listnr_get_help);

        configureButton();

    }

    //set up the "Emergency" button so that user must tap and hold it to send his emergency message
    private void configureButton() {
        sendMsg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

                //message is send to the following number and contains the user's name and his location
                String phoneNo = "0889626308";
                String sms = "Please help me! My name is " + name + " , Longitude= " + loc_lon + " , Latitude= " + loc_lat + ".";

                //try-catch block that attempts to send the sms to the security personal at AUBG
                //one of the main reasons this may give an exception is a lack of permission
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS Failed to Send",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    //if all permissions are present then we configure the "Emergency" button
    @Override
    public  void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configureButton();
                    return;
                }
        }
    }
}

