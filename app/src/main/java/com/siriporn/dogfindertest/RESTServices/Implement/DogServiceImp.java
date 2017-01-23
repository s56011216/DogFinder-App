package com.siriporn.dogfindertest.RESTServices.Implement;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.siriporn.dogfindertest.Models.Dog;
import com.siriporn.dogfindertest.NetworkConfigs;
import com.siriporn.dogfindertest.RESTServices.Interface.DogService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Moobi on 12-Jan-17.
 */

public class DogServiceImp {
    private static Retrofit retrofit;
    private static DogService service;
    private static DogServiceImp dogService;
    public static final String BASE_URL = "http://161.246.6.240:10100";

    private DogServiceImp(){};

    public static DogServiceImp getInstance() {
        if(retrofit == null) {
            retrofit = NetworkConfigs.getRestAdapter(BASE_URL);
            service = retrofit.create(DogService.class);
            dogService = new DogServiceImp();
        }

        return dogService;
    }

    public void newDog(Dog dog, Callback callback) {
        Call call = service.newDog(dog);
        call.enqueue(callback);
    }

    public void uploadImage(final Dog dog, File file, final Callback callback) {
        FileServiceImp.getInstance().uploadImage(null, file, new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Boolean success = Boolean.valueOf("" + response.body().get("success"));
                if(success) {
                    Map<String, Object> payload = (Map<String, Object>) response.body().get("payload");
                    Call addImageCall = service.addImage("application/json", dog, Integer.parseInt(payload.get("image_id").toString()));
                    addImageCall.enqueue(callback);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }
}
