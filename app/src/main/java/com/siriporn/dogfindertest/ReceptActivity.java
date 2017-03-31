package com.siriporn.dogfindertest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.siriporn.dogfindertest.Models.ResponseFormat;
import com.siriporn.dogfindertest.Models.User;
import com.siriporn.dogfindertest.RESTServices.Implement.UserServiceImp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recept);

        SharedPreferences sp = DogFinderApplication.getContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        if(sp.getString("token", null) != null) {
            UserServiceImp.getInstance().getSelfInfo(new Callback<ResponseFormat>() {
                @Override
                public void onResponse(Call<ResponseFormat> call, Response<ResponseFormat> response) {
                    User user = Converter.toPOJO(response.body().getPayload().get("user_data"), User.class);
                    DogFinderApplication.setUser(user);

                    Intent intent = new Intent(ReceptActivity.this,MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<ResponseFormat> call, Throwable t) {

                }
            });
        } else {
            Intent intent = new Intent(ReceptActivity.this,Login.class);
            startActivity(intent);
        }
    }
}
