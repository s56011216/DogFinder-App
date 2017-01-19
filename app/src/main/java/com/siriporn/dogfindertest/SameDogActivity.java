package com.siriporn.dogfindertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterSameDog;

public class SameDogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_dog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CustomAdapterSameDog(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(SameDogActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
