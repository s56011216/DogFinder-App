package com.siriporn.dogfindertest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.siriporn.dogfindertest.Fragments.AdoptFragment;
import com.siriporn.dogfindertest.Fragments.FindFragment;
import com.siriporn.dogfindertest.Fragments.FoundFragment;
import com.siriporn.dogfindertest.Fragments.LostFragment;
import com.siriporn.dogfindertest.Fragments.MyDogFragment;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;
import com.siriporn.dogfindertest.RESTServices.Interface.DogService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public static Context context;
    //location user google map
    LocationManager locationManager;
    LocationListener locationListener;
    private NavigationView navigationView;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_my_dog, 0);
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        DogServiceImp.getInstance().getAllMyDogs(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){
                    Log.i("Success","OK");
                    List<Dog> dogs = (List<Dog>) response.body().getPayload().get("dogs");
                    dogs.get(0).getName();
                }
                else{
                    Log.e("Sucess","Failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseFormat> call, Throwable t) {

            }
        });
        /*//location user google map ---------------------------------------------------
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location", location.toString());

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

        // If device is running SDK

        if (Build.VERSION.SDK_INT < 23) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // ask for permission

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                // we have permission!

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }

        }
        */// end user location google map --------------
    }
/*//ActionBar Title dynamically change with fragment
    private void displayFragment(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        String title = "";
        switch (position) {
            case 0:
                fragment = new MyDogFragment();
                title = "My Dog";
                break;
            case 1:
                fragment = new LostFragment();
                title = "Lost Post ";
                break;
            case 2:
                fragment = new FoundFragment();
                title = "Found Post";
                break;
            case 3:
                fragment = new FindFragment();
                title = "Find My Dog";
                break;

            default:
                break;
        }

        // update selected fragment and title
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_all, fragment).commit();
            getSupportActionBar().setTitle(title);
            // change icon to arrow drawable
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);
        }
    }
    */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_my_dog) {
            // Handle the camera action
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MyDogFragment()).commit();
        } else if (id == R.id.nav_lost) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new LostFragment()).commit();
        } else if (id == R.id.nav_found) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FoundFragment()).commit();
        } else if (id == R.id.nav_find) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new FindFragment()).commit();
        } else if (id == R.id.nav_adopt) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AdoptFragment()).commit();
        } else if (id == R.id.nav_map) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MapFragment()).commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void AddMyDogClicked(View view){
        Intent intent = new Intent(this,DogAddInfoActivity.class);
        startActivity(intent);
    }

    public void GotoMapClicked(View view){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
    public void AddFoundPostClicked(View view){
        Intent intent = new Intent(this,FoundPostActivity.class);
        startActivity(intent);
    }
    public static Context getContext() {return context;}
}
