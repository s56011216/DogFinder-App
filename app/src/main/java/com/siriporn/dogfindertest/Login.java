package com.siriporn.dogfindertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class Login extends AppCompatActivity
    {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       //fb
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

    }
    public void TemLoginClicked(View view){

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
