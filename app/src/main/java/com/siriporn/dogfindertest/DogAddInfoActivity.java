package com.siriporn.dogfindertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class DogAddInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_add_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    public void nextClicked(View view) {

        EditText nameText = (EditText) findViewById(R.id.nameText);

        EditText noticeText = (EditText) findViewById(R.id.noticeText);

        Log.i("Username", nameText.getText().toString());

        Log.i("Password", noticeText.getText().toString());

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
    public void searchClicked(View view) {
        Intent intent = new Intent(this,SearchBreed.class);
        startActivity(intent);

    }

}
