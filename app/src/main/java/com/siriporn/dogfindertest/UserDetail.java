package com.siriporn.dogfindertest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.RESTServices.Implement.DogServiceImp;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siriporn.dogfindertest.MainActivity.context;

public class UserDetail extends AppCompatActivity {
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

                    Profile profile = Profile.getCurrentProfile();
                    User user = new User();
                    ImageView pic = (ImageView) findViewById(R.id.imageUser);
                    TextView name = (TextView) findViewById(R.id.nameUser);
                    TextView email = (TextView) findViewById(R.id.emailUser);

                    name.setText(profile.getName());
                    email.setText(user.getEmail());

                    Uri picUri = profile.getProfilePictureUri(200,200);
                    Glide.with(context)
                            .load(picUri)
                            .override(200, 200)
                            .centerCrop()
                            .into(pic);
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
