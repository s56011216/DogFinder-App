package com.siriporn.dogfindertest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.siriporn.dogfindertest.Models.LostAndFound;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoundPostDetail extends AppCompatActivity implements OnMapReadyCallback {
    ImageView picture;
    String[] pic;
    double latitude,longitude;
    Context context = DogFinderApplication.getContext();

    @Override
    //Show information of dog which is found when clicked the list
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_post_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LostAndFound lostAndFound = Cache.getInstance().get("lostAndFound");

        if (lostAndFound == null) {
            DogServiceImp.getInstance().getLastNotification(new Callback<ResponseFormat>() {
                @Override
                public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                    LostAndFound lostAndFound = Converter.toPOJO(response.body().getPayload().get("notification"), LostAndFound.class);
                    picture = (ImageView)findViewById(R.id.picFoundPost);

                    TextView name = (TextView) findViewById(R.id.nameFB);
                    String fb_name = lostAndFound.getDog().getUser().getFb_name();
                    name.setText(fb_name);

                    TextView nameDog = (TextView) findViewById(R.id.NameText);
                    String Dogname = lostAndFound.getDog().getName();
                    nameDog.setText(Dogname);

                    TextView breedDog = (TextView) findViewById(R.id.BreedText);
                    String Dogbreed = lostAndFound.getDog().getBreed();
                    breedDog.setText(Dogbreed);

                    ImageView pictureFB = (ImageView)findViewById(R.id.foundUserPicWritePost);
                    String picUri = lostAndFound.getDog().getUser().getFb_profile_image();
                    Glide.with(context)
                            .load(picUri)
                            .override(100, 100)
                            .centerCrop()
                            .into(pictureFB);

                    TextView date = (TextView) findViewById(R.id.DatePost);
                    date.setText(lostAndFound.getCreated_at().toString());

                    TextView note = (TextView) findViewById(R.id.noticeDog);
                    note.setText(lostAndFound.getNote());

                    TextView phone = (TextView) findViewById(R.id.PhoneText);
                    phone.setText(lostAndFound.getDog().getUser().getTelephone());

                    TextView email = (TextView) findViewById(R.id.EmailText);
                    email.setText(lostAndFound.getDog().getUser().getEmail());

                    pic = lostAndFound.getDog().getImages();
                    String uri = "http://161.246.6.240:10100/server" + pic[0];
                    Log.i("ss",uri);
                    Glide.with(context)
                            .load(uri)
                            .override(700, 700)
                            .centerCrop()
                            .into(picture);

                    latitude = lostAndFound.getDog().getLatitude();
                    longitude = lostAndFound.getDog().getLongitude();
                }

                @Override
                public void onFailure(Call<ResponseFormat> call, Throwable t) {
                    Log.e("error", "FoundPostDetail");
                }
            });
        }
        else {
            picture = (ImageView)findViewById(R.id.picFoundPost);

            TextView name = (TextView) findViewById(R.id.nameFB);
            String fb_name = lostAndFound.getDog().getUser().getFb_name();
            name.setText(fb_name);

            TextView nameDog = (TextView) findViewById(R.id.NameText);
            String Dogname = lostAndFound.getDog().getName();
            nameDog.setText(Dogname);

            TextView breedDog = (TextView) findViewById(R.id.BreedText);
            String Dogbreed = lostAndFound.getDog().getBreed();
            breedDog.setText(Dogbreed);

            ImageView pictureFB = (ImageView)findViewById(R.id.foundUserPicWritePost);
            String picUri = lostAndFound.getDog().getUser().getFb_profile_image();
            Glide.with(context)
                    .load(picUri)
                    .override(100, 100)
                    .centerCrop()
                    .into(pictureFB);

            TextView date = (TextView) findViewById(R.id.DatePost);
            date.setText(lostAndFound.getCreated_at().toString());

            TextView note = (TextView) findViewById(R.id.noticeDog);
            note.setText(lostAndFound.getNote());

            TextView phone = (TextView) findViewById(R.id.PhoneText);
            phone.setText(lostAndFound.getDog().getUser().getTelephone());

            TextView email = (TextView) findViewById(R.id.EmailText);
            email.setText(lostAndFound.getDog().getUser().getEmail());

            //dog pic String[] pic
            pic = lostAndFound.getDog().getImages();
            String uri = "http://161.246.6.240:10100/server" + pic[0];
            Log.i("ss",uri);
            Glide.with(context)
                    .load(uri)
                    .override(700, 700)
                    .centerCrop()
                    .into(picture);

            latitude = lostAndFound.getDog().getLatitude();
            longitude = lostAndFound.getDog().getLongitude();
        }
    }

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

    private GoogleMap mMap;

    LocationManager locationManager;

    LocationListener locationListener;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        LatLng userLocation = new LatLng(latitude, longitude);
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

                userLocation = new LatLng(latitude, longitude);

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

            }

        }

    }

}
