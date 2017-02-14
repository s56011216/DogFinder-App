package com.siriporn.dogfindertest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Text;
import com.siriporn.dogfindertest.Models.LostAndFound;

import static com.siriporn.dogfindertest.MainActivity.context;

public class FoundPostDetail extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_post_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Parcelable mBoard =  intent.getParcelableExtra("test");


        LostAndFound lostAndFound = (LostAndFound) Cache.getInstance().get("lostAndFound");

        TextView name = (TextView) findViewById(R.id.nameFB);
        name.setText(lostAndFound.getUser().getFb_name().toString());

        ImageView pictureFB = (ImageView)findViewById(R.id.foundUserPicWritePost);
        String picUri = lostAndFound.getUser().getFb_profile_image();
        Glide.with(context)
                .load(picUri)
                .override(100, 100)
                .centerCrop()
                .into(pictureFB);

        TextView date = (TextView) findViewById(R.id.DatePost);
        date.setText(lostAndFound.getCreated_at().toString());

        TextView note = (TextView) findViewById(R.id.noticeFoundPost);
        note.setText(lostAndFound.getNote());

        TextView phone = (TextView) findViewById(R.id.PhoneText);
        phone.setText(lostAndFound.getUser().getTelephone());

        TextView email = (TextView) findViewById(R.id.EmailText);
        email.setText(lostAndFound.getUser().getEmail());

        //dog pic String[] pic
        pic = lostAndFound.getDog().getImages();
        String uri = "http://161.246.6.240:10100/server" + pic[0];
        Log.i("ss",uri);
        Glide.with(context)
                .load(uri)
                .override(700, 700)
                .centerCrop()
                .into(picture);
    }

    ImageView picture = (ImageView)findViewById(R.id.picFoundPost);
    String[] pic;
    private static int count = 0;
    ImageButton button_right,button_left;
    public void BtnRightClicked(View view){
        button_left = (ImageButton) findViewById(R.id.ButtonLeft);
        button_right = (ImageButton) findViewById(R.id.ButtonRight);
        if(count != pic.length-1) {
            incrementCount();
        }
        if(count < pic.length) {
            String uri = "http://161.246.6.240:10100/server" + pic[count];
            Glide.with(context)
                    .load(uri)
                    .override(100, 100)
                    .centerCrop()
                    .into(picture);

            if(count == pic.length-1) {
                button_right .setVisibility(View.INVISIBLE);
            }
            button_left .setVisibility(View.VISIBLE);

        }else{
            button_right .setVisibility(View.INVISIBLE);
        }
    }

    public void BtnLeftClicked(View view){
        button_left = (ImageButton) findViewById(R.id.ButtonLeft);
        button_right = (ImageButton) findViewById(R.id.ButtonRight);
        if(count != -1) {
            decrementCount();
        }
        if(count >= 0) {
            String uri = "http://161.246.6.240:10100/server" + pic[count];
            Glide.with(context)
                    .load(uri)
                    .override(700, 700)
                    .centerCrop()
                    .into(picture);
            if(count == 0) {

                button_left .setVisibility(View.INVISIBLE);
            }
            button_right .setVisibility(View.VISIBLE);
        }else{
            button_left .setVisibility(View.INVISIBLE);
        }
    }
    public static synchronized void incrementCount() {
        count++;
    }
    public static synchronized void decrementCount() {
        count--;
    }


    //map fragment
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    float lat,lon;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    {

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    }

                }

            }

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                Log.i("Location", location.toString());

                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                lat = (float)location.getLatitude();
                lon = (float) location.getLongitude();

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

            }

        }

    }
}
