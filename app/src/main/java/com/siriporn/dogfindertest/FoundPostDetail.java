package com.siriporn.dogfindertest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.siriporn.dogfindertest.Models.LostAndFound;

public class FoundPostDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_post_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LostAndFound lostAndFound = (LostAndFound) Cache.getInstance().get("lostAndFound");
        lostAndFound.getDog().getId();

    }

}
