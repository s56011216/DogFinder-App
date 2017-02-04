package com.siriporn.dogfindertest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.siriporn.dogfindertest.CustomAdapter.CustomAdapterDog;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siriporn.dogfindertest.MainActivity.context;

public class MyDogDetail extends AppCompatActivity {
    String[] pic;
    ImageView mImageView;
    private static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dog_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String positions = intent.getStringExtra("SelectRowDog");
        pic = getIntent().getExtras().getStringArray("Pic");
        final int position = Integer.parseInt(positions);

        DogServiceImp.getInstance().getAllMyDogs(new Callback<ResponseFormat>() {
            @Override
            public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                if(response.body().isSuccess()){

                    final List<Map<String, Object>> dogs = (List<Map<String, Object>>) response.body().getPayload().get("dogs");
                    String Name = (String) dogs.get(position).get("name");
                    String Breed = (String) dogs.get(position).get("breed");
                    String Note = (String) dogs.get(position).get("note");
                    //String Age = (String) dogs.get(position).get("age");

                    mImageView = (ImageView) findViewById(R.id.imageMyDog );
                    TextView name = (TextView) findViewById(R.id.nameText2);
                    TextView breed = (TextView) findViewById(R.id.breedText2);
                    TextView note = (TextView) findViewById(R.id.noticeText);
                    //TextView age = (TextView) findViewById(R.id.text_details);

                    name.setText(Name);
                    breed.setText(Breed);
                    note.setText(Note);

                    //age.setText(Age);
                    String uri = "http://161.246.6.240:10100/server" + pic[0];
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

}
