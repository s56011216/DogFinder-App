package com.siriporn.dogfindertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterDog;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDogDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dog_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String positions = intent.getStringExtra("SelectRowDog");
        final int position = Integer.parseInt(positions);

        DogServiceImp.getInstance().getAllMyDogs(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){

                    final List<Map<String, Object>> dogs = (List<Map<String, Object>>) response.body().getPayload().get("dogs");
                    String Name = (String) dogs.get(position).get("name");
                    String Breed = (String) dogs.get(position).get("breed");
                    String Note = (String) dogs.get(position).get("note");
                    String Age = (String) dogs.get(position).get("age");

                    TextView name = (TextView) findViewById(R.id.text_details);
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

}
