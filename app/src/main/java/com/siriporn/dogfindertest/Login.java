package com.siriporn.dogfindertest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //fb ติดตามโทเค็นการเข้าถึง
        Button regisBut = (Button)findViewById(R.id.register);
        final Intent intent = new Intent(this,RegisterActivity.class);
        regisBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.siriporn.dogfindertest",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void TemLoginClicked(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    //end ติดตามโทเค็นการเข้าถึง

    public void goMenu(View view){

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
