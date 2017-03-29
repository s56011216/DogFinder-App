package com.siriporn.dogfindertest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
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
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siriporn.dogfindertest.MainActivity.context;

public class MyDogDetail extends AppCompatActivity implements OnMapReadyCallback {
    String[] pic;
    ImageView mImageView;
    private static int count = 0;
    double[] latitude,longitude;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dog_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String positions = intent.getStringExtra("SelectRowDog");
        pic = getIntent().getExtras().getStringArray("Pic");
        latitude = getIntent().getExtras().getDoubleArray("lat");
        longitude = getIntent().getExtras().getDoubleArray("lon");

        position = Integer.parseInt(positions);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DogServiceImp.getInstance().getAllMyDogs(1,5, new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){

                    final List<Map<String, Object>> dogs = (List<Map<String, Object>>) response.body().getPayload().get("dogs");
                    String Name = (String) dogs.get(position).get("name");
                    String Breed = (String) dogs.get(position).get("breed");
                    String Note = (String) dogs.get(position).get("note");
                    //latitude = (double) dogs.get(position).get("latitude");
                    //longitude = (double) dogs.get(position).get("longitude");

                    mImageView = (ImageView) findViewById(R.id.imageUser);
                    TextView name = (TextView) findViewById(R.id.nameUser);
                    TextView breed = (TextView) findViewById(R.id.emailUser);
                    TextView note = (TextView) findViewById(R.id.noticeText);
                    //TextView age = (TextView) findViewById(R.id.text_details);

                    name.setText(Name);
                    breed.setText(Breed);
                    note.setText(Note);

                    //age.setText(Age);
                    String uri = "http://161.246.6.240:10100/server" + pic[position];
                    Log.i("ss",uri);
                    Glide.with(context)
                            .load(uri)
                            .override(700, 700)
                            .centerCrop()
                            .into(mImageView);

                }
                else{
                    Log.e("Sucess","Failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {
                Log.e("Sucess","onFailure");
            }
        });

    }

    private GoogleMap mMap;

    LocationManager locationManager;

    LocationListener locationListener;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        LatLng userLocation = new LatLng(latitude[position], longitude[position]);
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Dog's Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location", location.toString());
                //LatLng userLocation = new LatLng(latitude[position], longitude[position]);

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

                userLocation = new LatLng(latitude[position], longitude[position]);

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

            }

        }

    }
/*
    ImageButton button_right,button_left;
    public void BtnRightClicked(View view){
        button_left = (ImageButton) findViewById(R.id.ButtonLeft);
        button_right = (ImageButton) findViewById(R.id.ButtonRight);
        if(count != pic.length-1) {
            incrementCount();
        }

        if(count < pic.length) {
            String uri = "http://161.246.6.240:10100/server" + pic[count];
            Log.i("ss", uri);
            Glide.with(context)
                    .load(uri)
                    .override(700, 700)
                    .centerCrop()
                    .into(mImageView);

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
            Log.i("ss", uri);
            Glide.with(context)
                    .load(uri)
                    .override(700, 700)
                    .centerCrop()
                    .into(mImageView);
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

*/

}
